package hank.lee.cathaybk.demo.exceptions;

import hank.lee.cathaybk.demo.data.enums.MessageEnum;

public class DatabaseOperationException extends DemoException{
    private static final MessageEnum messageEnum = MessageEnum.DATABASE_ERROR;

    public DatabaseOperationException() {
        super(messageEnum);
    }

    public DatabaseOperationException(String message) {
        super(messageEnum, message);
    }
}
