package hank.lee.cathaybk.coindesk.controller;

import hank.lee.cathaybk.coindesk.data.modal.Currency;
import hank.lee.cathaybk.coindesk.data.pojo.currency.AddCurrencyRequest;
import hank.lee.cathaybk.coindesk.data.pojo.ResponsePayload;
import hank.lee.cathaybk.coindesk.data.pojo.currency.UpdateCurrencyRequest;
import hank.lee.cathaybk.coindesk.service.ICurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final ICurrencyService currencyService;

    @GetMapping
    public ResponsePayload<List<Currency>> getCurrencies() {
        return ResponsePayload.createSuccessResponse(currencyService.findAll());
    }

    @PostMapping
    public ResponsePayload<List<Currency>> addCurrency(@RequestBody @Valid AddCurrencyRequest request) {
        return ResponsePayload.createSuccessResponse(currencyService.addCurrency(request));
    }

    @PutMapping
    public ResponsePayload<List<Currency>> updateCurrency(@RequestBody @Valid UpdateCurrencyRequest request) {
        request.validate();
        return ResponsePayload.createSuccessResponse(currencyService.updateCurrency(request));
    }

    @DeleteMapping
    public ResponsePayload<List<Currency>> deleteCurrency(@RequestParam("shortName") String shortName) {
        return ResponsePayload.createSuccessResponse(currencyService.deleteCurrency(shortName));
    }
}
