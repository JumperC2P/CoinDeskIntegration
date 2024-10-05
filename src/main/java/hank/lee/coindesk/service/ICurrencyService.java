package hank.lee.coindesk.service;

import hank.lee.coindesk.data.modal.Currency;
import hank.lee.coindesk.data.pojo.currency.AddCurrencyRequest;
import hank.lee.coindesk.data.pojo.currency.UpdateCurrencyRequest;

import java.util.List;

public interface ICurrencyService {
    List<Currency> findAll();
    List<Currency> addCurrency(AddCurrencyRequest request);
    List<Currency> updateCurrency(UpdateCurrencyRequest request);
    List<Currency> deleteCurrency(String shortName);
}
