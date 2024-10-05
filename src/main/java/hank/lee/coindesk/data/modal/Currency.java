package hank.lee.coindesk.data.modal;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
