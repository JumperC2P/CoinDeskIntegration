package hank.lee.coindesk.data.pojo.currency;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCurrencyRequest {
    @NotNull
    private String name;
    @NotNull
    private String shortName;
    @NotNull
    private String zhTwName;
}
