package hank.lee.cathaybk.demo.data.modal.idclass;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExchangeRateId implements Serializable {
    private LocalDateTime updateTime;
    private String baseCurrency;
    private String targetCurrency;

    public ExchangeRateId(LocalDateTime updateTime, String baseCurrency, String targetCurrency) {
        this.updateTime = updateTime;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }
}
