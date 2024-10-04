package hank.lee.cathaybk.demo.service.impl;

import hank.lee.cathaybk.demo.data.enums.MessageEnum;
import hank.lee.cathaybk.demo.data.modal.Currency;
import hank.lee.cathaybk.demo.data.pojo.AddCurrencyRequest;
import hank.lee.cathaybk.demo.data.pojo.UpdateCurrencyRequest;
import hank.lee.cathaybk.demo.exceptions.DemoException;
import hank.lee.cathaybk.demo.repository.CurrencyRepository;
import hank.lee.cathaybk.demo.service.ICurrencyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    @Transactional
    public List<Currency> addCurrency(AddCurrencyRequest request) {

        Currency existedCurrency = currencyRepository.findById(request.getShortName()).orElse(null);
        if (existedCurrency != null) {
            throw new DemoException(MessageEnum.CURRENCY_DUPLICATED);
        }

        Currency currency = Currency.builder()
                .name(request.getName())
                .shortName(request.getShortName())
                .zhTwName(request.getZhTwName())
                .build();
        currencyRepository.save(currency);
        return findAll();
    }

    @Override
    public List<Currency> updateCurrency(UpdateCurrencyRequest request) {
        Currency currency = currencyRepository.findById(request.getShortName()).orElse(null);
        if (currency == null) {
            throw new DemoException(MessageEnum.NO_CURRENCY_FOUND);
        }

        String name = request.getCurrencyName();
        String zhTwName = request.getZhTwName();
        if (StringUtils.isNotEmpty(name)) {
            currency.setName(name);
        }
        if (StringUtils.isNotEmpty(zhTwName)) {
            currency.setZhTwName(zhTwName);
        }
        currencyRepository.save(currency);
        return findAll();
    }

    @Override
    public List<Currency> deleteCurrency(String shortName) {
        currencyRepository.deleteById(shortName);
        return findAll();
    }
}
