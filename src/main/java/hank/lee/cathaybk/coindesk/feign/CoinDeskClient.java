package hank.lee.cathaybk.coindesk.feign;

import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.cathaybk.coindesk.feign.config.CoinDeskConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bitcoinPriceIndex", url = "${coindesck.url}", configuration = CoinDeskConfig.class)
public interface CoinDeskClient {

    @GetMapping("/bpi/currentprice.json")
    CoinDeskResponse getBitcoinPriceIndex();

    @GetMapping("/bpi/currentprice/{currency}.json")
    CoinDeskResponse getBitcoinPriceIndexByCurrency(@PathVariable String currency);

}
