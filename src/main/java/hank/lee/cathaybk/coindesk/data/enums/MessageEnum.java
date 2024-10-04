package hank.lee.cathaybk.coindesk.data.enums;

public enum MessageEnum {
    SUCCESS(1, "Success"),
    // Common
    REQUEST_PARAM_ERROR(2, "Please check your request payload."),
    DATABASE_ERROR(3, "Failed to operate database."),

    // CoinDesk
    COINDESK_API_ERROR(4, "Failed to get data from CoinDesk API."),

    // Currency
    NO_CURRENCY_FOUND(11, "No currency is found."),
    CURRENCY_DUPLICATED(12, "duplicated currency."),

    // Exchange Rate
    CURRENCY_NOT_SUPPORTED(21, "Sorry, your requested currency is not supported or is invalid."),
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
