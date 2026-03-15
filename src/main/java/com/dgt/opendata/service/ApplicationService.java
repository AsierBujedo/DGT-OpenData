package com.dgt.opendata.service;

import java.util.List;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Date;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.models.Centro;
import com.dgt.opendata.service.queries.IQueries;
import com.dgt.opendata.service.queries.Queries;
import com.dgt.opendata.service.utils.Common;

public class ApplicationService {
    
    private final IQueries queries;

    public ApplicationService() {
        queries = new Queries();
    }

    public List<Autoescuela> getAutoescuelas(String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) throws Exception {
        List<Autoescuela> autoescuelas = new ArrayList<>();

        try (var rs = queries.executeQueryGetAutoescuelas(codigo_autoescuela, nombre_autoescuela, provincia, seccion)) {
            while (rs.next()) {
                autoescuelas.add(new Autoescuela(
                    rs.getString("id"),
                    rs.getString("codigo_autoescuela"),
                    rs.getString("nombre_autoescuela"),
                    rs.getString("desc_provincia"),
                    rs.getString("codigo_seccion")
                ));
            }
        }

        return autoescuelas;
    }

    public Date getLastUpdate() throws Exception {
        try (var rs = queries.executeQueryGetLastUpdate()) {
            if (rs.next()) {
                return rs.getDate("last_update");
            } else {
                throw new Exception("No se pudo obtener la fecha de última actualización");
            }
        }
    }

    public List<String> getPermisosAutoescuela(String id_autoescuela) throws Exception {
        List<String> permisos = new ArrayList<>();

        try (var rs = queries.executeQueryGetPermisosAutoescuela(id_autoescuela)) {
            while (rs.next()) {
                permisos.add(rs.getString("nombre_permiso").trim());
            }
        }

        return permisos;
    }

    public List<Centro> getCentrosByAutoescuela(String id_autoescuela) throws Exception {
        List<Centro> centros = new ArrayList<>();

        try (var rs = queries.executeQueryGetJefaturasPorAutoescuela(id_autoescuela)) {
            while (rs.next()) {
                centros.add(new Centro(
                    rs.getString("provincia"),
                    rs.getString("centro")
                ));
            }
        }

        return centros;
    }

    public Response<Object> loadData(int year, int month) {
        try {
            String url = Common.buildUrl(year, month);
            URI uri = URI.create(url);
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/zip")
                    .build();
            
            java.nio.file.Path tmpDir = java.nio.file.Paths.get("tmp");
            java.nio.file.Files.createDirectories(tmpDir);
            java.nio.file.Path zipPath = java.nio.file.Files.createTempFile(tmpDir, "data", ".zip");
            
            client.sendAsync(request, java.net.http.HttpResponse.BodyHandlers.ofFile(zipPath))
                .thenApply(response -> {
                    unzipFile(zipPath.toString(), zipPath.getParent().toString());
                    return zipPath;
                })
                .thenAccept(path -> queries.loadDataToDatabase(path.getParent().toString()))
                .join();

            return new Response<>(1, "Datos cargados exitosamente");
        } catch (Exception e) {
            return new Response<>(0, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void unzipFile(String zipFilePath, String extractPath) {
        try (java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(
                new java.io.FileInputStream(zipFilePath))) {
            java.util.zip.ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                java.nio.file.Path filePath = java.nio.file.Paths.get(extractPath, entry.getName());
                if (entry.isDirectory()) {
                    java.nio.file.Files.createDirectories(filePath);
                } else {
                    java.nio.file.Files.createDirectories(filePath.getParent());
                    java.nio.file.Files.copy(zis, filePath);
                }
            }
        } catch (Exception e) {
            System.err.println("Error descomprimiendo archivo: " + e.getMessage());
        }
    }
}
