package hank.lee.cathaybk.demo.feign;

import hank.lee.cathaybk.demo.data.pojo.CoinDeskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bitcoinPriceIndex", url = "${coindesck.url}")
public interface CoinDeskClient {

    @GetMapping("/bpi/currentprice.json")
    CoinDeskResponse getBitcoinPriceIndex();

    /** Failed
     * {
     *   "statusCode": 404,
     *   "error": "Not Found",
     *   "message": "Sorry, your requested currency NTD is not supported or is invalid"
     * }
     */
}
