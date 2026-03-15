import os
import requests
import zipfile
import io
import pyodbc
import calendar
import pandas as pd
from datetime import datetime
from dotenv import load_dotenv

# 1. Cargar variables de entorno desde el .env
load_dotenv()

DB_SERVER = os.getenv('DB_SERVER')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASS = os.getenv('DB_PASS')

directorio_destino = 'datos_dgt_csv'
os.makedirs(directorio_destino, exist_ok=True)

# Cadena de conexión
conn_str = f'DRIVER={{ODBC Driver 18 for SQL Server}};SERVER={DB_SERVER};DATABASE={DB_NAME};UID={DB_USER};PWD={DB_PASS};TrustServerCertificate=yes;Encrypt=yes;'

def main():
    try:
        conn = pyodbc.connect(conn_str)
        cursor = conn.cursor()
        
        # Para que las inserciones masivas sean muchísimo más rápidas
        cursor.fast_executemany = True 

        print("1. Sincronizando inventario histórico de archivos...")
        
        fecha_actual = datetime.now()
        anyo_actual = fecha_actual.year
        mes_actual = fecha_actual.month

        # Generar inventario desde 2010 hasta hoy
        for year in range(2010, anyo_actual + 1):
            mes_fin = 12 if year < anyo_actual else mes_actual
            for month in range(1, mes_fin + 1):
                _, last_day = calendar.monthrange(year, month)
                month_str = f"{month:02d}"
                file_name = f"export_auto_{year}{month_str}01_{year}{month_str}{last_day}.zip"
                release_date = f"{year}-{month_str}-{last_day}"
                
                cursor.execute("{CALL dbo.RegistrarArchivoEsperado (?, ?)}", (file_name, release_date))
        conn.commit()

        print("2. Buscando archivos pendientes de descarga...")
        cursor.execute("{CALL dbo.GetArchivosPendientes}")
        archivos_pendientes = cursor.fetchall()

        if not archivos_pendientes:
            print("¡Todo está al día! No hay archivos pendientes.")
            return

        for fila in archivos_pendientes:
            file_id = fila[0]
            file_name = fila[1].strip()
            release_date = fila[2]
            
            year = release_date.year
            month = release_date.month
            
            url = f"https://www.dgt.es/microdatos/salida/{year}/{month}/conductores/autoescuela/{file_name}"
            print(f"\nProcesando mes {month}/{year}: {file_name}")
            
            response = requests.get(url)
            
            if response.status_code == 200:
                # Extraemos el ZIP
                with zipfile.ZipFile(io.BytesIO(response.content)) as zip_ref:
                    # Obtenemos el nombre del archivo CSV dentro del ZIP
                    csv_filename = zip_ref.namelist()[0]
                    zip_ref.extractall(directorio_destino)
                
                ruta_csv = os.path.join(directorio_destino, csv_filename)
                
                # --- PROCESAMIENTO CON PANDAS ---
                print("  -> Leyendo CSV y limpiando datos...")
                df = pd.read_csv(ruta_csv, sep=';', encoding='latin1', dtype=str)
                
                # Limpiar espacios en blanco de las columnas de texto
                for col in df.columns:
                    if df[col].dtype == 'object':
                        df[col] = df[col].str.strip()

                # --- 1. PROCESAR AUTOESCUELAS ÚNICAS ---
                print("  -> Insertando Autoescuelas...")
                autoescuelas_unicas = df[['CODIGO_AUTOESCUELA', 'CODIGO_SECCION', 'NOMBRE_AUTOESCUELA', 'DESC_PROVINCIA']].drop_duplicates()
                
                # Diccionario para guardar en memoria la relación (codigo, seccion) -> id_autoescuela
                diccionario_ids_autoescuela = {}
                
                for _, row in autoescuelas_unicas.iterrows():
                    # Usamos un bloque T-SQL anónimo para recuperar el OUTPUT del SP en pyodbc
                    sql_autoescuela = """
                        DECLARE @nuevo_id BIGINT;
                        EXEC dbo.InsertarAutoescuela ?, ?, ?, ?, @nuevo_id OUTPUT;
                        SELECT @nuevo_id;
                    """
                    cursor.execute(sql_autoescuela, (row['CODIGO_AUTOESCUELA'], row['CODIGO_SECCION'], row['NOMBRE_AUTOESCUELA'], row['DESC_PROVINCIA']))
                    id_generado = cursor.fetchone()[0]
                    
                    # Guardamos el ID en nuestro diccionario
                    clave = (row['CODIGO_AUTOESCUELA'], row['CODIGO_SECCION'])
                    diccionario_ids_autoescuela[clave] = id_generado
                
                # --- 2. PROCESAR EXÁMENES ---
                print("  -> Insertando Exámenes...")
                
                # Mapeamos los IDs a nuestro DataFrame de exámenes usando el diccionario
                df['id_autoescuela'] = df.apply(lambda r: diccionario_ids_autoescuela[(r['CODIGO_AUTOESCUELA'], r['CODIGO_SECCION'])], axis=1)
                
                # Preparamos los datos exactos para el SP de exámenes
                datos_examenes = df[[
                    'id_autoescuela', 'CENTRO_EXAMEN', 'MES', 'ANYO', 'TIPO_EXAMEN', 'NOMBRE_PERMISO',
                    'NUM_APTOS', 'NUM_APTOS_1conv', 'NUM_APTOS_2conv', 'NUM_APTOS_3o4conv', 'NUM_APTOS_5_o_mas_conv', 'NUM_NO_APTOS'
                ]].copy()
                
                # Aseguramos que los números sean enteros y rellenamos nulos con 0
                cols_numericas = ['MES', 'ANYO', 'NUM_APTOS', 'NUM_APTOS_1conv', 'NUM_APTOS_2conv', 'NUM_APTOS_3o4conv', 'NUM_APTOS_5_o_mas_conv', 'NUM_NO_APTOS']
                datos_examenes[cols_numericas] = datos_examenes[cols_numericas].apply(pd.to_numeric, errors='coerce').fillna(0).astype(int)
                
                # Convertimos el DataFrame a lista de tuplas para el executemany
                registros_examenes = [tuple(x) for x in datos_examenes.to_numpy()]
                
                # Llamamos al SP de forma masiva
                sql_examen = "{CALL dbo.InsertarExamen (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"
                cursor.executemany(sql_examen, registros_examenes)
                
                # --- 3. ACTUALIZAR CONTROL DE DESCARGAS ---
                file_size = len(response.content)
                cursor.execute("{CALL dbo.ActualizarEstadoDescarga (?, ?)}", (file_id, file_size))
                
                conn.commit()
                print("  -> ¡Mes procesado y guardado en la base de datos con éxito!")
                
                # Opcional: Borrar el CSV descargado para no ocupar espacio
                os.remove(ruta_csv)
                
            elif response.status_code == 404:
                print("  -> Archivo no publicado aún (404). Se intentará en la próxima ejecución.")
            else:
                print(f"  -> Error {response.status_code} al descargar.")

    except Exception as e:
        print(f"Error general durante la ejecución: {e}")
        if 'conn' in locals():
            conn.rollback() # Deshace los cambios si algo falla a medias
    finally:
        if 'conn' in locals():
            conn.close()
            print("Conexión cerrada.")

if __name__ == "__main__":
    main()