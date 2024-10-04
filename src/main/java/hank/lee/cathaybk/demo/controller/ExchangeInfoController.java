package hank.lee.cathaybk.demo.controller;

import hank.lee.cathaybk.demo.data.pojo.CoinDeskResponse;
import hank.lee.cathaybk.demo.data.pojo.ResponsePayload;
import hank.lee.cathaybk.demo.feign.CoinDeskClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeInfoController {

    private final CoinDeskClient coinDeskClient;

    @GetMapping("/coindesk")
    public ResponsePayload<CoinDeskResponse> getExchangeInfoFromCoinDesck() {
        CoinDeskResponse response = coinDeskClient.getBitcoinPriceIndex();
        return ResponsePayload.createSuccessResponse(response);
    }
}
