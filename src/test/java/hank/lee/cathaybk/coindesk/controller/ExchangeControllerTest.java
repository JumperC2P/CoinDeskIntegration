package hank.lee.cathaybk.coindesk.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import hank.lee.cathaybk.coindesk.TestApplication;
import hank.lee.cathaybk.coindesk.common.MockMvcClient;
import hank.lee.cathaybk.coindesk.common.Watcher;
import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;
import hank.lee.cathaybk.coindesk.data.pojo.ResponsePayload;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskTransformResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.ExchangeInfo;
import hank.lee.cathaybk.coindesk.utils.JsonUtils;
import io.vavr.Tuple2;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class ExchangeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Rule
    public TestWatcher watchman;

    private MockMvcClient mockMvcClient;
    private static final String BASE_URL = "/exchange";
    private static final String COIN_DESK = BASE_URL + "/coindesk";
    private static final String COIN_DESK_TRANSFORM = BASE_URL + "/coindesk/transform";

    // Sample Data Object
    private static final Map<String, String> getExchangeRateParams = new HashMap<>();

    @PostConstruct
    public void init() {
        mockMvcClient = new MockMvcClient(mockMvc);
        watchman =  new Watcher();
        dataClear();
        dataInitiation();
    }

    private void dataClear() {
        getExchangeRateParams.clear();
    }

    private void dataInitiation() {
        getExchangeRateParams.put("fiatCurrency", "USD");
    }

    @Test
    public void testGetExchangeInfoFromCoinDesck() throws Exception {
        Tuple2<Integer, ResponsePayload<CoinDeskResponse>> response = mockMvcClient.doQueryStringRequest(Collections.emptyMap(), COIN_DESK, HttpMethod.GET, new TypeReference<ResponsePayload<CoinDeskResponse>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<CoinDeskResponse> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        CoinDeskResponse coinDeskResponse = responsePayload.getData();
        Map<String, ExchangeInfo> exchangeInfoMap = coinDeskResponse.getBpi();
        Assert.assertFalse(exchangeInfoMap.isEmpty());
        log.info("Rate from CoinDesk: {}", coinDeskResponse);
    }

    @Test
    public void testGetExchangeInfoFromCoinDesckWithCurrency() throws Exception {
        Tuple2<Integer, ResponsePayload<CoinDeskResponse>> response = mockMvcClient.doQueryStringRequest(getExchangeRateParams, COIN_DESK, HttpMethod.GET, new TypeReference<ResponsePayload<CoinDeskResponse>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<CoinDeskResponse> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        CoinDeskResponse coinDeskResponse = responsePayload.getData();
        Assert.assertNotNull(coinDeskResponse);
        Map<String, ExchangeInfo> exchangeInfoMap = coinDeskResponse.getBpi();
        Assert.assertFalse(exchangeInfoMap.isEmpty());
        log.info("Rate from CoinDesk with currency({}): {}", getExchangeRateParams.get("fiatCurrency"),  JsonUtils.toPrettyString(coinDeskResponse));
    }

    @Test
    public void testTransformCoinDeskResponse() throws Exception {
        Tuple2<Integer, ResponsePayload<List<CoinDeskTransformResponse>>> response = mockMvcClient.doQueryStringRequest(Collections.emptyMap(), COIN_DESK_TRANSFORM, HttpMethod.GET, new TypeReference<ResponsePayload<List<CoinDeskTransformResponse>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<CoinDeskTransformResponse>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<CoinDeskTransformResponse> transformResponses = responsePayload.getData();
        Assert.assertNotNull(transformResponses);
        Assert.assertFalse(transformResponses.isEmpty());
        log.info("Transformed CoinDesk Response: {}", JsonUtils.toPrettyString(transformResponses));
    }

    @Test
    public void testTransformCoinDeskResponseWithCurrency() throws Exception {
        Tuple2<Integer, ResponsePayload<List<CoinDeskTransformResponse>>> response = mockMvcClient.doQueryStringRequest(getExchangeRateParams, COIN_DESK_TRANSFORM, HttpMethod.GET, new TypeReference<ResponsePayload<List<CoinDeskTransformResponse>>>() {});
        Assert.assertEquals(200, response._1.intValue());

        ResponsePayload<List<CoinDeskTransformResponse>> responsePayload = response._2;
        Assert.assertNotNull(responsePayload);
        Assert.assertEquals(MessageEnum.SUCCESS.getCode(), responsePayload.getCode());

        List<CoinDeskTransformResponse> transformResponses = responsePayload.getData();
        Assert.assertNotNull(transformResponses);
        Assert.assertFalse(transformResponses.isEmpty());
        log.info("Transformed CoinDesk Response with currency({}): {}", getExchangeRateParams.get("fiatCurrency"), JsonUtils.toPrettyString(transformResponses));
    }

}
