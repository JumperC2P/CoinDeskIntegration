package hank.lee.cathaybk.coindesk.service.impl;

import hank.lee.cathaybk.coindesk.data.modal.Currency;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskTransformResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.ExchangeInfo;
import hank.lee.cathaybk.coindesk.feign.CoinDeskClient;
import hank.lee.cathaybk.coindesk.service.ICoinDeskService;
import hank.lee.cathaybk.coindesk.service.ICurrencyService;
import hank.lee.cathaybk.coindesk.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CoinDeskService implements ICoinDeskService {

    private final CoinDeskClient coinDeskClient;
    private final ICurrencyService currencyService;

    @Override
    public CoinDeskResponse getBitcoinPriceIndexByCurrency(String currency) {
        CoinDeskResponse response;
        if (StringUtils.isNotBlank(currency)) {
            response = coinDeskClient.getBitcoinPriceIndexByCurrency(currency);
        } else {
            response = coinDeskClient.getBitcoinPriceIndex();
        }
        return response;
    }

    @Override
    public List<CoinDeskTransformResponse> getExchangeRateFromCoinDesk(String currency) {
        List<Currency> currencies = currencyService.findAll();
        if (currencies.isEmpty()) {
            log.info("No currency found in database. No mandarin name of currency will be shown");
            return Collections.emptyList();
        }
        Map<String, String> currencyNamingMap = currencies.stream().collect(Collectors.toMap(Currency::getShortName, Currency::getZhTwName));
        CoinDeskResponse coinDeskResponse = getBitcoinPriceIndexByCurrency(currency);
        if (coinDeskResponse == null) {
            log.info("No exchange rate found from coin desk. currency: {}", currency);
            return Collections.emptyList();
        }

        String updateTime = DateUtils.convertTimestampToString(coinDeskResponse.getTime().getUpdated());
        return coinDeskResponse.getBpi().values().stream()
                .map(exchangeInfo -> transformCoinDeskResponse(updateTime, exchangeInfo, currencyNamingMap))
                .collect(Collectors.toList());
    }


    private CoinDeskTransformResponse transformCoinDeskResponse(String updateTime, ExchangeInfo exchangeInfo, Map<String, String> currencyNamingMap) {
        return CoinDeskTransformResponse.builder()
                .updateTime(updateTime)
                .currency(exchangeInfo.getCode())
                .currencyInZhTw(currencyNamingMap.get(exchangeInfo.getCode()))
                .rate(exchangeInfo.getRateFloat().toEngineeringString())
                .build();
    }
}
