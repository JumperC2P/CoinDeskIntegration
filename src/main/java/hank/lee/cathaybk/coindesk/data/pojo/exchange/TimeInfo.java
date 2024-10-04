package hank.lee.cathaybk.coindesk.data.pojo.exchange;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedISOTimestampDeserializer;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedTimestampDeserializer;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedUKTimestampDeserializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeInfo {
    @JsonDeserialize(using = UpdatedTimestampDeserializer.class)
    private Timestamp updated;
    @JsonDeserialize(using = UpdatedISOTimestampDeserializer.class)
    private Timestamp updatedISO;
    @JsonDeserialize(using = UpdatedUKTimestampDeserializer.class)
    private Timestamp updateduk;
}
