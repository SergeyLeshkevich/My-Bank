package by.leshkevich.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author S.Leshkevich
 * @version 1.0
 *this class is for serializing and deserializing LocalDateTime objects
 * */
public class LocalDateTimeSerializerFndDeserializer implements JsonSerializer<LocalDateTime>, JsonDeserializer< LocalDateTime > {
    /**
     *this method is for deserializing LocalDateTime objects
     * */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss").withLocale(Locale.ENGLISH));
    }

    /**
     *this method is for serializing LocalDateTime objects
     * */
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
        return new JsonPrimitive(formatter.format(localDateTime));
    }

}
