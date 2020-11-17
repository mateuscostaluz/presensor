package br.gov.sp.fatec.presensor.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeServices {

    public static Integer getDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return localDateTime.getDayOfWeek().getValue();
    }

    public static String getDateTimeFormatted() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
