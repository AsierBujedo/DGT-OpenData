package com.dgt.opendata.service;

import java.util.List;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.response.Response;
import com.dgt.opendata.service.queries.IQueries;
import com.dgt.opendata.service.queries.Queries;
import com.dgt.opendata.service.utils.Common;

public class ApplicationService {
    
    private final IQueries queries;

    public ApplicationService() {
        queries = new Queries();
    }

    public List<Autoescuela> getAutoescuelas() {
        return new ArrayList<Autoescuela>();
    }

    public Response<Object> loadData(int year, int month) {
        String url = Common.buildUrl(year, month);

        URI uri = URI.create(url);

        return new Response<>(1, "Datos cargados");
    }
}
