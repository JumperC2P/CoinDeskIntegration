package hank.lee.cathaybk.demo.data.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Id
    @Column(name = "short_name", nullable = false, length = 3)
    private String shortName;
    @Column(name = "zh_tw_name", nullable = false, length = 10)
    private String zhTwName;
    @Column(name = "latest_rate", precision = 12, scale = 10)
    private BigDecimal latestRate;
    @Column(name = "latest_update_time")
    private LocalDateTime latestUpdateTime;
}
