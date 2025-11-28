package com.dgt.opendata.service.utils;

import com.dgt.opendata.service.Constants;

public class Common {

    public static int getLastDayOfMonth(int year, int month) {
            if (month == 2) { // February
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                return 29; // Leap year
            } else {
                return 28; // Common year
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30; // April, June, September, November
        } else {
            return 31; // January, March, May, July, August, October, December
        }
    }

    public static String buildUrl(int year, int month) {
     return Constants.DGT_BASE_URL
            .replace("{YYYY}", String.format("%04d", year))
            .replace("{MM}", String.format("%02d", month))
            .replace("{LD}", String.format("%02d", getLastDayOfMonth(year, month)));   
    }
}
