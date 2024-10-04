package hank.lee.cathaybk.demo.controller;

import hank.lee.cathaybk.demo.TestApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeControllerTest {
}
