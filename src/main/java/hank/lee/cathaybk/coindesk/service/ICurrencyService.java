package hank.lee.cathaybk.coindesk.service;

import hank.lee.cathaybk.coindesk.data.modal.Currency;
import hank.lee.cathaybk.coindesk.data.pojo.currency.AddCurrencyRequest;
import hank.lee.cathaybk.coindesk.data.pojo.currency.UpdateCurrencyRequest;

import java.util.List;

public interface ICurrencyService {
    List<Currency> findAll();
    List<Currency> addCurrency(AddCurrencyRequest request);
    List<Currency> updateCurrency(UpdateCurrencyRequest request);
    List<Currency> deleteCurrency(String shortName);
}
