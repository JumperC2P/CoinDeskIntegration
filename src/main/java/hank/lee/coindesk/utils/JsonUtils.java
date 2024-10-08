package hank.lee.coindesk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import hank.lee.coindesk.data.deserializer.UpdatedISOTimestampDeserializer;
import hank.lee.coindesk.data.deserializer.UpdatedTimestampDeserializer;
import hank.lee.coindesk.data.deserializer.UpdatedUKTimestampDeserializer;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;

@Log4j2
public class JsonUtils {
    public static final ObjectMapper objectMapper = (new ObjectMapper()).findAndRegisterModules();

    @PostConstruct
    public void init() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Timestamp.class, new UpdatedTimestampDeserializer());
        module.addDeserializer(Timestamp.class, new UpdatedUKTimestampDeserializer());
        module.addDeserializer(Timestamp.class, new UpdatedISOTimestampDeserializer());
        objectMapper.registerModule(module);
    }

    public static String toPrettyString(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            log.error(ExceptionUtils.getStackTrace(var2));
            return object.toString();
        }
    }

    public static <T> T parseToPojo(TypeReference<T> type, String string) {
        try {
            return objectMapper.readerFor(type).readValue(string);
        } catch (Exception var3) {
            return null;
        }
    }

    public static <T> T parseToPojo(Class<T> clazz, String string) {
        try {
            return objectMapper.readValue(string, clazz);
        } catch (IOException var3) {
            log.error(ExceptionUtils.getStackTrace(var3));
            return null;
        }
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
