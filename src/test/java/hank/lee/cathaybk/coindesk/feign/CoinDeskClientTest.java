package hank.lee.cathaybk.coindesk.feign;

import hank.lee.cathaybk.coindesk.TestApplication;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.CoinDeskResponse;
import hank.lee.cathaybk.coindesk.data.pojo.exchange.ExchangeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@SpringBootTest(classes= TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class CoinDeskClientTest {

    @Autowired
    private CoinDeskClient coinDeskClient;

    @Test
    public void successFetchBitcoinPriceIndex() {
        CoinDeskResponse response = coinDeskClient.getBitcoinPriceIndex();
        Assert.assertEquals("Bitcoin", response.getChartName());

        Map<String, ExchangeInfo> exchangeInfoMap = response.getBpi();
        Assert.assertNotNull(exchangeInfoMap);
        Assert.assertFalse(exchangeInfoMap.isEmpty());
        Assert.assertNotNull(exchangeInfoMap.get("USD"));
        Assert.assertNotNull(exchangeInfoMap.get("GBP"));
        Assert.assertNotNull(exchangeInfoMap.get("EUR"));
    }
}
