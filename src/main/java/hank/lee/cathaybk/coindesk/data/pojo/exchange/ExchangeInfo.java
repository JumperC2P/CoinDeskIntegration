package hank.lee.cathaybk.coindesk.data.pojo.exchange;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hank.lee.cathaybk.coindesk.data.deserializer.BigDecimalDeserializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonDeserialize(as = ExchangeInfo.class)
public class ExchangeInfo {
    private String code;
    private String symbol;
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal rate;
    private String description;
    @JsonAlias("rate_float")
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal rateFloat;
}
