package br.gov.sp.fatec.presensor.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateTimeServices {

    public static Integer getDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return localDateTime.getDayOfWeek().getValue();
    }

    public static LocalTime getDateTimeFormatted() {
        return LocalTime.now(ZoneId.of("America/Sao_Paulo"));
    }

}
