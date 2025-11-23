package com.dgt.opendata.webapi;

import org.springframework.web.bind.annotation.RestController;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.response.Response;
import com.dgt.opendata.service.ApplicationService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class WebApiController {

    //#region Autoescuelas

    @GetMapping("/api/autoescuelas")
    public ResponseEntity<Response<List<Autoescuela>>> getAutoescuelas() {
        List<Autoescuela> as = new ApplicationService().getAutoescuelas();
        return new Response<>(as).toResponseEntity();
    }
    
    @GetMapping("/api/autoescuelas/{id}")
    public ResponseEntity<Response<Object>> getAutoescuela(String id) {
        return new Response<>(1, "No implementado").toResponseEntity();
    }

    //#endregion

    //#region Consultas de datos

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

}
