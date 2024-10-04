package hank.lee.cathaybk.coindesk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class JsonUtils {
    public static final ObjectMapper objectMapper = (new ObjectMapper()).findAndRegisterModules();
    private static final TypeReference<Map<String, Double>> STRING_DOUBLE_MAP_TYPE = new TypeReference<Map<String, Double>>() {
    };

    public static String toString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            log.error(ExceptionUtils.getStackTrace(var2));
            return object.toString();
        }
    }

    public static Map<String, Double> parseExRates(String rateString) {
        if (rateString == null) {
            return Collections.emptyMap();
        } else {
            try {
                Map<String, Double> map = (Map)objectMapper.readerFor(STRING_DOUBLE_MAP_TYPE).readValue(rateString);
                return map;
            } catch (IOException var2) {
                log.error("parseExRates failed: {}", rateString);
                return Collections.emptyMap();
            }
        }
    }

    public static <T> Optional<T> parse(Class<T> clazz, String string) {
        try {
            T pojo = objectMapper.readerFor(clazz).readValue(string);
            return Optional.of(pojo);
        } catch (IOException var3) {
            log.error("parse failed, class:{}, string:{}", clazz.getName(), string);
            return Optional.empty();
        }
    }

    public static <T> List<T> parseToPojoList(String string, Class<T> clazz) {
        if (StringUtils.isBlank(string)) {
            return Collections.emptyList();
        } else {
            try {
                CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
                return (List)objectMapper.readValue(string, javaType);
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static <T> T parseToPojo(Class<T> clazz, String string) {
        try {
            T pojo = objectMapper.readerFor(clazz).readValue(string);
            return pojo;
        } catch (IOException var3) {
            return null;
        }
    }

    public static <T> T parseToPojo(TypeReference<T> type, String string) {
        try {
            T pojo = objectMapper.readerFor(type).readValue(string);
            return pojo;
        } catch (IOException var3) {
            return null;
        }
    }

    public static <T> T convertPojoToAny(Object source, TypeReference<T> dest) {
        try {
            return objectMapper.convertValue(source, dest);
        } catch (Exception var3) {
            log.error("Parse POJO Failed");
            return null;
        }
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
