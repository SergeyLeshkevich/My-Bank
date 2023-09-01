package by.leshkevich.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@Setter
@ToString
public class DateManager {

    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

    private LocalDateTime ldt;


    public DateManager() {
        this.ldt = LocalDateTime.now();
    }

    public DateManager(Timestamp timestamp) {
        this.ldt = DateManager.parse(timestamp);
    }

    public String dateFormat() {
        return ldt.format(FORMATTER_DATE);
    }

    public String timeFormat() {
        return ldt.format(FORMATTER_TIME);
    }

    public Timestamp getDateForDB() {
        return Timestamp.valueOf(ldt);
    }

    public static LocalDateTime parse(Timestamp timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp.getTime());
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDateTime ldt = zdt.toLocalDateTime();

        return ldt;
    }

    public static Timestamp parse(String str) {
        Timestamp timestamp = new Timestamp(Timestamp.parse(str));

        return timestamp;
    }

    public static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerFndDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerFndDeserializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson;
    }

    public static boolean checkIfLastDayOfMonth() {

        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int lastDayOfMonth = currentDate.lengthOfMonth();

        if (currentDay == lastDayOfMonth) {
            return true;
        } else {
            return false;
        }
    }
}
