package br.gov.sp.fatec.presensor.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateTimeServices {

    public static Integer getDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Integer dayOfWeek = localDateTime.getDayOfWeek().getValue();
        return dayOfWeek;
    }

    public static LocalTime getLocalTime() {
        LocalTime localTime = LocalTime.now(ZoneId.of("America/Sao_Paulo"));
        return localTime;
    }

    public static LocalDate getLocalDate() {
        LocalDate localDate = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        return localDate;
    }

}
