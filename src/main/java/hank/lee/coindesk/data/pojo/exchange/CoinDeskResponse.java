package hank.lee.coindesk.data.pojo.exchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
