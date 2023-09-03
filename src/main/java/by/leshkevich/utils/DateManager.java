package by.leshkevich.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is intended for convenience of working with LocalDateTime and Timestamp objects
 */
@Getter
@Setter
@ToString
public class DateManager {
    /**
     * DateTimeFormatter FORMATTER_DATE
     */
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTimeFormatter FORMATTER_DATE
     */
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

    private LocalDateTime ldt;


    public DateManager() {
        this.ldt = LocalDateTime.now();
    }

    public DateManager(Timestamp timestamp) {
        this.ldt = DateManager.convert(timestamp);
    }

    /**
     * the method is to convert the Timestamp object to a string according to the DateTimeFormatter
     * and output the date
     * @return  String
     */
    public String dateFormat() {
        return ldt.format(FORMATTER_DATE);
    }
    /**
     * the method is to convert the Timestamp object to a string according to the DateTimeFormatter
     * and output the time
     * @return  String
     */
    public String timeFormat() {
        return ldt.format(FORMATTER_TIME);
    }

    /**
     * the method to get Timestamp object from this object
     * @return  Timestamp
     */
    public Timestamp getDateForDB() {
        return Timestamp.valueOf(ldt);
    }

    /**
     * this method will convert Timestamp object to LocalDateTime object
     * @return  LocalDateTime
     */
    public static LocalDateTime convert(Timestamp timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp.getTime());
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDateTime ldt = zdt.toLocalDateTime();

        return ldt;
    }

    /**
     * the method to get Gson object from this object
     */
    public static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerFndDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson;
    }

    /**
     * method for getting the LocalDateTime of the start of the selection.
     * Used in services for generating account statements and transactions
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getDateFor(String date) {
        String[] strings = date.split("-");
        return LocalDateTime.of(Integer.parseInt(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2]),
                0, 0);
    }

    /**
     * method for getting the LocalDateTime of the end of the selection.
     * Used in services for generating account statements and transactions
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime getDateBefore(String date) {
        String[] strings = date.split("-");
        return LocalDateTime.of(Integer.parseInt(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2]),
                23, 59);
    }

}
