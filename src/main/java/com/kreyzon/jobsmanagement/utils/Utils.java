package com.kreyzon.jobsmanagement.utils;

import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static void setPageTitle(Model model, String title) {
        model.addAttribute("title", title);
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try { date = formatter.parse(dateStr); } catch (Exception e) {}
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
