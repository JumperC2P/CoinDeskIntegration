package hank.lee.cathaybk.coindesk.exceptions;

import hank.lee.cathaybk.coindesk.data.enums.MessageEnum;

public class DemoException extends RuntimeException {
    private final MessageEnum messageEnum;

    public DemoException(MessageEnum messageEnum) {
        super(messageEnum.getMsg());
        this.messageEnum = messageEnum;
    }
    public DemoException(MessageEnum messageEnum, String message) {
      super(message);
      this.messageEnum = messageEnum;
    }

    public MessageEnum getMessageEnum() {
        return messageEnum;
    }
}
