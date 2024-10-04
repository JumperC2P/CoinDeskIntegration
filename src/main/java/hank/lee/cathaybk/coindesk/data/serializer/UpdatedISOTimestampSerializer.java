package hank.lee.cathaybk.coindesk.data.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UpdatedISOTimestampSerializer extends JsonSerializer<Timestamp> {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    static {
        FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            String formattedDate = FORMAT.format(value);
            gen.writeString(formattedDate);
        }
    }
}
