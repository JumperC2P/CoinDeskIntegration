package hank.lee.cathaybk.demo.exceptions;

import hank.lee.cathaybk.demo.data.enums.MessageEnum;

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
