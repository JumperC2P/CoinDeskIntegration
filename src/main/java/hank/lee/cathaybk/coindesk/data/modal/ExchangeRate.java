package hank.lee.cathaybk.coindesk.data.modal;

import hank.lee.cathaybk.coindesk.data.modal.idclass.ExchangeRateId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate")
@IdClass(ExchangeRateId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    @Id
    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;
    @Id
    @Column(name = "target_currency", nullable = false, length = 20)
    private String targetCurrency;
    @Column(name = "rate", nullable = false, precision = 12, scale = 10)
    private BigDecimal rate;
}
