package hank.lee.cathaybk.demo.data.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hank.lee.cathaybk.demo.data.deserializer.BigDecimalDeserializer;
import hank.lee.cathaybk.demo.data.deserializer.UpdatedISOTimestampDeserializer;
import hank.lee.cathaybk.demo.data.deserializer.UpdatedTimestampDeserializer;
import hank.lee.cathaybk.demo.data.deserializer.UpdatedUKTimestampDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinDeskResponse {
    private TimeInfo time;
    private String disclaimer;
    private String chartName;
    private Map<String, ExchangeInfo> bpi;

    @Data
    public static class TimeInfo {
        @JsonDeserialize(using = UpdatedTimestampDeserializer.class)
        private Timestamp updated;
        @JsonDeserialize(using = UpdatedISOTimestampDeserializer.class)
        private Timestamp updatedISO;
        @JsonDeserialize(using = UpdatedUKTimestampDeserializer.class)
        private Timestamp updateduk;
    }

    @Data
    public static class ExchangeInfo {
        private String code;
        private String symbol;
        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal rate;
        private String description;
        @JsonProperty("rate_float")
        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal rateFloat;
    }
}
