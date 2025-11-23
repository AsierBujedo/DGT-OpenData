package com.dgt.opendata.service;

import java.util.List;
import java.util.ArrayList;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.service.queries.IQueries;
import com.dgt.opendata.service.queries.Queries;

public class ApplicationService {
    
    private final IQueries queries;

    public ApplicationService() {
        queries = new Queries();
    }

    public List<Autoescuela> getAutoescuelas() {
        return new ArrayList<Autoescuela>();
    }
}
