package hank.lee.cathaybk.demo.data.enums;

public enum MessageEnum {
    SUCCESS(1, "Success"),
    REQUEST_PARAM_ERROR(2, "Please check your request payload."),
    DATABASE_ERROR(3, "Failed to operate database."),

    NO_CURRENCY_FOUND(4, "No currency is found."),
    CURRENCY_DUPLICATED(5, "duplicated currency."),
    ;

    private final int code;
    private final String msg;
    MessageEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
