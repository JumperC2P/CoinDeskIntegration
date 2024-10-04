package hank.lee.cathaybk.coindesk.service;

import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskTransformResponse;

import java.util.List;

public interface ICoinDeskService {
    CoinDeskResponse getBitcoinPriceIndexByCurrency(String currency);
    List<CoinDeskTransformResponse> getExchangeRateFromCoinDesk(String currency);
}
