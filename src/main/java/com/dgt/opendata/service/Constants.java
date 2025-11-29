package com.dgt.opendata.service;

public class Constants {

    // Request constants
    public static final String DGT_BASE_URL = "https://www.dgt.es/microdatos/salida/{YYYY}/{MM}/conductores/autoescuela/export_auto_{YYYY}{MM}01_{YYYY}{MM}{LD}.zip";

    // Stored procedures
    public static final String SP_INSERT_AUTOESCUELA = "[DGTOpendata].[dbo].[Autoescuelas_InsertAutoescuela]";
    public static final String SP_INSERT_EXAMEN = "[DGTOpendata].[dbo].[Examenes_InsertExamen]";

}
