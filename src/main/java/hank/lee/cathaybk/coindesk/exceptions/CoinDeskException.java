package hank.lee.cathaybk.coindesk.exceptions;

import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;

public class CoinDeskException extends DemoException{
    private static final MessageEnum messageEnum = MessageEnum.COINDESK_API_ERROR;

    public CoinDeskException() {
        super(messageEnum);
    }

    public CoinDeskException(MessageEnum messageEnum) {
        super(messageEnum, messageEnum.getMsg());
    }

    public CoinDeskException(String message) {
        super(messageEnum, message);
    }
}
