package hank.lee.cathaybk.coindesk.data.pojo.exchange;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedISOTimestampDeserializer;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedTimestampDeserializer;
import hank.lee.cathaybk.coindesk.data.deserializer.UpdatedUKTimestampDeserializer;
import hank.lee.cathaybk.coindesk.data.serializer.UpdatedISOTimestampSerializer;
import hank.lee.cathaybk.coindesk.data.serializer.UpdatedTimestampSerializer;
import hank.lee.cathaybk.coindesk.data.serializer.UpdatedUKTimestampSerializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonDeserialize(as = TimeInfo.class)
public class TimeInfo {
    @JsonDeserialize(using = UpdatedTimestampDeserializer.class)
    @JsonSerialize(using = UpdatedTimestampSerializer.class)
    private Timestamp updated;
    @JsonDeserialize(using = UpdatedISOTimestampDeserializer.class)
    @JsonSerialize(using = UpdatedISOTimestampSerializer.class)
    private Timestamp updatedISO;
    @JsonDeserialize(using = UpdatedUKTimestampDeserializer.class)
    @JsonSerialize(using = UpdatedUKTimestampSerializer.class)
    private Timestamp updateduk;
}
