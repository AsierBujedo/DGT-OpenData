package com.dgt.opendata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.dgt.opendata.service.utils.Common;

public class CommonTest {

    @Test
    public void getLastDayOfMonth_OK() {
        int expected = 31;
        int lastDay = Common.getLastDayOfMonth(2025, 10);
        assertEquals(expected, lastDay);
    }

    @Test
    public void getUrl_OK() {
        String expected = "https://www.dgt.es/microdatos/salida/2025/10/conductores/autoescuela/export_auto_20251001_20251031.zip";
        String builtUrl = Common.buildUrl(2025, 10);
        assertEquals(expected, builtUrl);
    }
}
