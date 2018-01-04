package by.psu.gui;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Converter {

    private static final Logger LOGGER = Logger.getLogger(Converter.class);

    public static LocalDate dateToLocalDate(Date date){
        return LocalDate.from(Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault()));
    }

    public static String dateToString(Date date){
        String dateFormat = "YYYY-MM-DD";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, new Locale("en_US"));
        return sdf.format(date);
    }

    public static LocalDate localDateToString(Date date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        return LocalDate.parse(date.toString(), formatter);
    }



    public static Date localDateToDate(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
