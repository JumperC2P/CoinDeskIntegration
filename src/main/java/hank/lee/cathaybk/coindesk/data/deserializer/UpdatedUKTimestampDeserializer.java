package hank.lee.cathaybk.coindesk.data.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UpdatedUKTimestampDeserializer extends JsonDeserializer<Timestamp> {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MMM d, yyyy 'at' HH:mm z");

    static {
        FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/London"));
    }

    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            return new Timestamp(FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
