package hank.lee.cathaybk.coindesk.feign.config;

import feign.codec.ErrorDecoder;
import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;
import hank.lee.cathaybk.coindesk.exceptions.CoinDeskException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinDeskConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CoinDeskErrorDecoder();
    }

    public class CoinDeskErrorDecoder implements ErrorDecoder {
            @Override
            public Exception decode(String methodKey, feign.Response response) {
                if (response.status() == 404) {
                    return new CoinDeskException(MessageEnum.CURRENCY_NOT_SUPPORTED);
                }
                return new CoinDeskException(response.body().toString());
            }
    }
}
