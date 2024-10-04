package hank.lee.cathaybk.demo.service;

import hank.lee.cathaybk.demo.data.modal.Currency;
import hank.lee.cathaybk.demo.data.pojo.AddCurrencyRequest;
import hank.lee.cathaybk.demo.data.pojo.UpdateCurrencyRequest;

import java.util.List;

public interface ICurrencyService {
    List<Currency> findAll();
    List<Currency> addCurrency(AddCurrencyRequest request);
    List<Currency> updateCurrency(UpdateCurrencyRequest request);
    List<Currency> deleteCurrency(String shortName);
}
