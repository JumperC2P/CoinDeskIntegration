package hank.lee.coindesk.data.pojo;

import hank.lee.coindesk.data.enums.MessageEnum;
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
