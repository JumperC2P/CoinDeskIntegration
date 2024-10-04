package hank.lee.cathaybk.demo.common;

import com.fasterxml.jackson.core.type.TypeReference;
import hank.lee.cathaybk.demo.utils.JsonUtils;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

public class MockMvcClient {
    private MockMvc mockMvc;

    public MockMvcClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public <T> T doJsonRequest(
            Map<String, Object> params, String url, HttpMethod httpMethod, TypeReference<T> typeReference) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = getJsonRequestBuilder(params, url, httpMethod);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
//        response.getStatus()
        String responseBodyJson = response.getContentAsString();
        return JsonUtils.parseToPojo(typeReference, responseBodyJson);
    }

    public MockHttpServletRequestBuilder getJsonRequestBuilder(
            Map<String, Object> params, String url, HttpMethod httpMethod) {
        JSONObject jsonObject = new JSONObject(params == null ? new HashMap<>() : params);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        MockHttpServletRequestBuilder rqBuilder;
        if (httpMethod == HttpMethod.DELETE) {
            rqBuilder = MockMvcRequestBuilders.delete(url);
        } else if (httpMethod == HttpMethod.PUT) {
            rqBuilder = MockMvcRequestBuilders.put(url);
        } else if (httpMethod == HttpMethod.POST) {
            rqBuilder = MockMvcRequestBuilders.post(url);
        } else {
            rqBuilder = MockMvcRequestBuilders.get(url);
        }
        return rqBuilder
                .content(jsonObject.toString())
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }
}
