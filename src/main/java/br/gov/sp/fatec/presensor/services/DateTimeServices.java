package br.gov.sp.fatec.presensor.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateTimeServices {

    public static Integer getDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return localDateTime.getDayOfWeek().getValue();
    }

    public static LocalTime getLocalTime() {
        LocalTime localTime = LocalTime.now(ZoneId.of("America/Sao_Paulo"));
        return localTime;
    }

    public static Timestamp geTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

}
