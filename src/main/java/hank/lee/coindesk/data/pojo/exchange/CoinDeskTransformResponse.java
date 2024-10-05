package hank.lee.coindesk.data.pojo.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskTransformResponse {
    private String updateTime;
    private String currency;
    private String currencyInZhTw;
    private String rate; // for accuracy, use String to present the rate
}
