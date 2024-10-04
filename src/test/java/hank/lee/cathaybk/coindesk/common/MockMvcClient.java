package hank.lee.cathaybk.coindesk.common;

import com.fasterxml.jackson.core.type.TypeReference;
import hank.lee.cathaybk.coindesk.utils.JsonUtils;
import io.vavr.Tuple2;
import org.json.JSONObject;
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
    private final MockMvc mockMvc;

    public MockMvcClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public <T> Tuple2<Integer, T> doJsonRequest(
            Map<String, String> params, String url, HttpMethod httpMethod, TypeReference<T> typeReference) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = getJsonRequestBuilder(params, url, httpMethod);
        return sendRequest(requestBuilder, typeReference);
    }

    public <T> Tuple2<Integer, T> doQueryStringRequest(
            Map<String, String> params, String url, HttpMethod httpMethod, TypeReference<T> typeReference) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = getQueryStringRequestBuilder(params, url, httpMethod);
        return sendRequest(requestBuilder, typeReference);
    }

    private <T> Tuple2<Integer, T> sendRequest(
            MockHttpServletRequestBuilder requestBuilder, TypeReference<T> typeReference) throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseBodyJson = response.getContentAsString();
        return new Tuple2<>(response.getStatus(), JsonUtils.parseToPojo(typeReference, responseBodyJson));
    }

    private MockHttpServletRequestBuilder getJsonRequestBuilder(
            Map<String, String> params, String url, HttpMethod httpMethod) {
        JSONObject jsonObject = new JSONObject(params == null ? new HashMap<>() : params);
        MockHttpServletRequestBuilder rqBuilder;
        if (httpMethod == HttpMethod.PUT) {
            rqBuilder = MockMvcRequestBuilders.put(url);
        } else if (httpMethod == HttpMethod.POST) {
            rqBuilder = MockMvcRequestBuilders.post(url);
        } else {
            rqBuilder = MockMvcRequestBuilders.get(url);
        }
        return rqBuilder
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }

    private MockHttpServletRequestBuilder getQueryStringRequestBuilder(
            Map<String, String> params, String url, HttpMethod httpMethod) {
        MockHttpServletRequestBuilder rqBuilder;
        if (!params.isEmpty()) {
            url += "?";
            StringBuilder urlBuilder = new StringBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = urlBuilder.toString();
            url = url.substring(0, url.length() - 1);
        }
        if (httpMethod == HttpMethod.DELETE) {
            rqBuilder = MockMvcRequestBuilders.delete(url);
        } else {
            rqBuilder = MockMvcRequestBuilders.get(url);
        }
        return rqBuilder
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }
}
