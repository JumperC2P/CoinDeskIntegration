package hank.lee.coindesk.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hank.lee.coindesk.TestApplication;
import hank.lee.coindesk.common.MockMvcClient;
import hank.lee.coindesk.common.Watcher;
import hank.lee.coindesk.data.enums.MessageEnum;
import hank.lee.coindesk.data.modal.Currency;
import hank.lee.coindesk.data.pojo.ResponsePayload;
import hank.lee.coindesk.repository.CurrencyRepository;
import hank.lee.coindesk.utils.JsonUtils;
import io.vavr.Tuple2;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Rule
    public TestWatcher watchman;

    private MockMvcClient mockMvcClient;
    private static final String BASE_URL = "/currency";

    // Sample Data Object
    private static final Map<String, String> newCurrencyParams = new HashMap<>();
    private static final Map<String, String> duplicatedCurrencyParams = new HashMap<>();
    private static final Map<String, String> updateCurrencyParams = new HashMap<>();
    private static final Map<String, String> updateFailedCurrencyParams = new HashMap<>();
    private static final Map<String, String> deleteCurrencyParams = new HashMap<>();
    private static final Map<String, String> deleteFailedCurrencyParams = new HashMap<>();


    @PostConstruct
    public void init() {
        mockMvcClient = new MockMvcClient(mockMvc);
        watchman =  new Watcher();
        dataClear();
        dataInitiation();
    }

    private void dataClear() {
        newCurrencyParams.clear();
        updateCurrencyParams.clear();
        deleteCurrencyParams.clear();
    }

    private void dataInitiation() {
        newCurrencyParams.put("name", "New Taiwan Dollar");
        newCurrencyParams.put("shortName", "NTD");
        newCurrencyParams.put("zhTwName", "台幣");

        duplicatedCurrencyParams.put("name", "US Dollar");
        duplicatedCurrencyParams.put("shortName", "USD");
        duplicatedCurrencyParams.put("zhTwName", "美金");

        updateCurrencyParams.put("shortName", "USD");
        updateCurrencyParams.put("zhTwName", "美元");

        updateFailedCurrencyParams.put("shortName", "AUD");
        updateFailedCurrencyParams.put("zhTwName", "澳幣");

        deleteCurrencyParams.put("shortName", "EUR");
        deleteFailedCurrencyParams.put("shortName", "JPY");
    }

    @Test
    @Order(1)
    public void testGetCurrencies() throws Exception {
        Tuple2<Integer, ResponsePayload<List<Currency>>> response = mockMvcClient.doQueryStringRequest(Collections.emptyMap(), BASE_URL, HttpMethod.GET, new TypeReference<ResponsePayload<List<Currency>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<Currency>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<Currency> currencies = responsePayload.getData();
        Assert.assertNotNull(currencies);
        Assert.assertFalse(currencies.isEmpty());
        log.info("Currency List: {}", JsonUtils.toPrettyString(currencies));
    }


    @Test
    @Order(2)
    public void testAddCurrency() throws Exception {
        Tuple2<Integer, ResponsePayload<List<Currency>>> response = mockMvcClient.doJsonRequest(newCurrencyParams, BASE_URL, HttpMethod.POST, new TypeReference<ResponsePayload<List<Currency>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<Currency>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<Currency> currencies = responsePayload.getData();
        Assert.assertNotNull(currencies);
        Assert.assertFalse(currencies.isEmpty());
        Assert.assertNotNull(currencies.stream().filter(currency -> currency.getShortName().equals(newCurrencyParams.get("shortName"))).findFirst().orElse(null));
    }

    @Test
    public void testAddCurrencyButDuplicated() throws Exception {
        Tuple2<Integer, ResponsePayload<String>> response = mockMvcClient.doJsonRequest(duplicatedCurrencyParams, BASE_URL, HttpMethod.POST, new TypeReference<ResponsePayload<String>>() {});
        Assert.assertEquals(400, response._1.intValue());

        ResponsePayload<String> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.CURRENCY_DUPLICATED.getCode(), responsePayload.getCode());
    }

    @Test
    @Order(3)
    public void testUpdateCurrency() throws Exception {
        Tuple2<Integer, ResponsePayload<List<Currency>>> response = mockMvcClient.doJsonRequest(updateCurrencyParams, BASE_URL, HttpMethod.PUT, new TypeReference<ResponsePayload<List<Currency>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<Currency>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<Currency> currencies = responsePayload.getData();
        Assert.assertNotNull(currencies);
        Assert.assertFalse(currencies.isEmpty());
        Assert.assertNotNull(currencies.stream().filter(this::validateCurrencyUpdated).findFirst().orElse(null));
        log.info("Updated Currency List: {}", JsonUtils.toPrettyString(currencies));

        Currency currencyInDb = currencyRepository.findById(updateCurrencyParams.get("shortName")).orElse(null);
        Assert.assertNotNull(currencyInDb);
        Assert.assertTrue(validateCurrencyUpdated(currencyInDb));
    }

    @Test
    public void testUpdateCurrencyButCurrencyNotFound() throws Exception {
        Tuple2<Integer, ResponsePayload<String>> response = mockMvcClient.doJsonRequest(updateFailedCurrencyParams, BASE_URL, HttpMethod.PUT, new TypeReference<ResponsePayload<String>>() {});
        Assert.assertEquals(400, response._1.intValue());

        ResponsePayload<String> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.NO_CURRENCY_FOUND.getCode(), responsePayload.getCode());
    }

    @Test
    @Order(4)
    public void testDeleteCurrency() throws Exception {
        Tuple2<Integer, ResponsePayload<List<Currency>>> response = mockMvcClient.doQueryStringRequest(deleteCurrencyParams, BASE_URL, HttpMethod.DELETE, new TypeReference<ResponsePayload<List<Currency>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<Currency>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<Currency> currencies = responsePayload.getData();
        Assert.assertNotNull(currencies);
        Assert.assertFalse(currencies.isEmpty());
        Assert.assertNull(currencies.stream().filter(currency -> currency.getShortName().equals(deleteCurrencyParams.get("shortName"))).findFirst().orElse(null));

        Assert.assertFalse(currencyRepository.existsById(deleteCurrencyParams.get("shortName")));
    }

    @Test
    public void testDeleteCurrencyButCurrencyNotFound() throws Exception {
        Tuple2<Integer, ResponsePayload<String>> response = mockMvcClient.doQueryStringRequest(deleteFailedCurrencyParams, BASE_URL, HttpMethod.DELETE, new TypeReference<ResponsePayload<String>>() {});
        Assert.assertEquals(400, response._1.intValue());

        ResponsePayload<String> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.NO_CURRENCY_FOUND.getCode(), responsePayload.getCode());
    }

    private boolean validateCurrencyUpdated(Currency currency) {
        return currency.getShortName().equals(updateCurrencyParams.get("shortName")) && currency.getZhTwName().equals(updateCurrencyParams.get("zhTwName"));
    }
}
