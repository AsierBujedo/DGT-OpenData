package com.dgt.opendata.webapi;

import org.springframework.web.bind.annotation.RestController;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.models.Centro;
import com.dgt.opendata.models.Examen;
import com.dgt.opendata.response.Response;
import com.dgt.opendata.service.ApplicationService;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class WebApiController {

    private final ApplicationService applicationService;

    public WebApiController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    //#region Autoescuelas

    @GetMapping("/api/autoescuelas")
    public ResponseEntity<Response<List<Autoescuela>>> getAutoescuelas(
            @RequestParam(value = "codigo_autoescuela", required = false) String codigoAutoescuela,
            @RequestParam(value = "nombre_autoescuela", required = false) String nombreAutoescuela,
            @RequestParam(value = "provincia", required = false) String provincia,
            @RequestParam(value = "seccion", required = false) String seccion
    ) {
        try {
            return new Response<>(applicationService.getAutoescuelas(
                codigoAutoescuela, nombreAutoescuela, provincia, seccion
            )).toResponseEntity();
        } catch (Exception e) {
            return new Response<List<Autoescuela>>(500, "Error al obtener las autoescuelas; " + e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping("/api/last-update")
    public ResponseEntity<Response<Date>> getLastUpdate() {
        try {
            return new Response<>(applicationService.getLastUpdate()).toResponseEntity();
        } catch (Exception e) {
            return new Response<Date>(500, "Error al obtener la fecha de última actualización; " + e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping("/api/autoescuelas/{id}/permisos")
    public ResponseEntity<Response<List<String>>> getPermisosByAutoescuela(@PathVariable String id) {
        try {
            return new Response<>(applicationService.getPermisosAutoescuela(id)).toResponseEntity();
        } catch (Exception e) {
            return new Response<List<String>>(500, "Error al obtener los permisos de la autoescuela; " + e.getMessage()).toResponseEntity();
        }
    }

    @GetMapping("/api/autoescuelas/{id}/centros")
    public ResponseEntity<Response<List<Centro>>> getCentrosByAutoescuela(@PathVariable String id) {
        try {
            return new Response<>(applicationService.getCentrosByAutoescuela(id)).toResponseEntity();
        } catch (Exception e) {
            return new Response<List<Centro>>(500, "Error al obtener los centros de la autoescuela; " + e.getMessage()).toResponseEntity();
        }
    }

    //#endregion
    
    @GetMapping("/api/examenes")
    public ResponseEntity<Response<List<Examen>>> getExamenes(
            @RequestParam(value = "codigo_autoescuela", required = false) String codigoAutoescuela,
            @RequestParam(value = "codigo_seccion", required = false) String codigoSeccion,
            @RequestParam(value = "provincia", required = false) String provincia,
            @RequestParam(value = "centro_examen", required = false) String centroExamen,
            @RequestParam(value = "permiso", required = false) String permiso,
            @RequestParam(value = "tipo_examen", required = false) String tipoExamen,
            @RequestParam(value = "mes", required = false) String mes,
            @RequestParam(value = "anyo", required = false) String anyo,
            @RequestParam(value = "isAddition", required = false) Boolean isAddition
    ) {
        try {
            return new Response<>(applicationService.getExamenes(
                codigoAutoescuela, codigoSeccion, provincia, centroExamen, permiso, tipoExamen, mes, anyo, isAddition
            )).toResponseEntity();
        } catch (Exception e) {
            return new Response<List<Examen>>(500, "Error al obtener los exámenes; " + e.getMessage()).toResponseEntity();
        }
    }
    
    //#endregion

    //#region Consultas de datos

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    //#endregion
}
