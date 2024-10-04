package hank.lee.cathaybk.demo.data.pojo;

import hank.lee.cathaybk.demo.data.enums.MessageEnum;
import hank.lee.cathaybk.demo.exceptions.DemoException;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class UpdateCurrencyRequest {
    private String currencyName;
    @NotNull
    private String shortName;
    private String zhTwName;

    public void validate() {
        if (StringUtils.isAllBlank(currencyName, zhTwName)) {
            throw new DemoException(MessageEnum.REQUEST_PARAM_ERROR, "at least one currencyName and zhTwName is required");
        }
    }
}
