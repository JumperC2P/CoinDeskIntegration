package hank.lee.coindesk.exceptions;

import hank.lee.coindesk.data.enums.MessageEnum;
import hank.lee.coindesk.data.pojo.ResponsePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponsePayload<String>> hanldeHttpMessageNotReadableException(MethodArgumentNotValidException e) {
        String field = extractNonNullField(e.getMessage());
        return generateParameterRequiredResponse(field);
    }

    private ResponseEntity<ResponsePayload<String>> generateParameterRequiredResponse(String field) {
        return ResponseEntity.badRequest()
                .body(createFailedResponse(MessageEnum.REQUEST_PARAM_ERROR, String.format("%s is required", field)));
    }

    @ExceptionHandler(DemoException.class)
    public ResponseEntity<ResponsePayload<String>> hanldeDemoException(DemoException e) {
        return ResponseEntity.badRequest()
                .body(createFailedResponse(e.getMessageEnum(), e.getMessage()));
    }

    private String extractNonNullField(String errorMessage) {
        String pattern = "on field '(\\w+)'";
        return findField(errorMessage, pattern);
    }

    private static String findField(String errorMessage, String pattern) {
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(errorMessage);

        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }

    protected ResponsePayload<String> createFailedResponse(MessageEnum messageEnum, String message) {
        return ResponsePayload.<String>builder()
                .code(messageEnum.getCode())
                .message(message)
                .build();
    }
}
