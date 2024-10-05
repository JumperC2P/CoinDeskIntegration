package hank.lee.cathaybk.coindesk.service.impl;

import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;
import hank.lee.cathaybk.coindesk.data.modal.Currency;
import hank.lee.cathaybk.coindesk.data.pojo.currency.AddCurrencyRequest;
import hank.lee.cathaybk.coindesk.data.pojo.currency.UpdateCurrencyRequest;
import hank.lee.cathaybk.coindesk.exceptions.DemoException;
import hank.lee.cathaybk.coindesk.repository.CurrencyRepository;
import hank.lee.cathaybk.coindesk.service.ICurrencyService;
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
        Currency currency = currencyRepository.findById(shortName).orElse(null);
        if (currency == null) {
            throw new DemoException(MessageEnum.NO_CURRENCY_FOUND);
        }

        currencyRepository.deleteById(shortName);
        return findAll();
    }
}
