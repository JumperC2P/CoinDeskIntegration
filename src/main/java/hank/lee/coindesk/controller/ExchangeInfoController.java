package hank.lee.coindesk.controller;

import hank.lee.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.coindesk.data.pojo.ResponsePayload;
import hank.lee.coindesk.data.pojo.exchange.CoinDeskTransformResponse;
import hank.lee.coindesk.service.ICoinDeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeInfoController {

    private final ICoinDeskService coinDeskService;

    @GetMapping("/coindesk")
    public ResponsePayload<CoinDeskResponse> getExchangeInfoFromCoinDesck(@RequestParam(required = false) String fiatCurrency) {
        return ResponsePayload.createSuccessResponse(coinDeskService.getBitcoinPriceIndexByCurrency(fiatCurrency));
    }

    @GetMapping("/coindesk/transform")
    public ResponsePayload<List<CoinDeskTransformResponse>> getExchangeRateFromCoinDesk(@RequestParam(required = false) String fiatCurrency) {
        return ResponsePayload.createSuccessResponse(coinDeskService.getExchangeRateFromCoinDesk(fiatCurrency));
    }
}
