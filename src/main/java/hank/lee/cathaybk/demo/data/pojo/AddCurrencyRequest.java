package hank.lee.cathaybk.demo.data.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCurrencyRequest {
    @NotNull
    private String name;
    @NotNull
    private String shortName;
    @NotNull
    private String zhTwName;
}
