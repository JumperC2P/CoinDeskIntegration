package hank.lee.cathaybk.coindesk.data.pojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayload<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ResponsePayload<T> createSuccessResponse(T data) {
        return ResponsePayload.<T>builder()
                .code(MessageEnum.SUCCESS.getCode())
                .data(data)
                .build();
    }
}
