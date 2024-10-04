package hank.lee.cathaybk.coindesk.common;

import org.junit.runner.Description;

public class Watcher extends org.junit.rules.TestWatcher {

    @Override
    protected void starting(Description description) {
        System.out.println("Starting test: " + description.getMethodName());
    }

    @Override
    protected void succeeded(Description description) {
        System.out.println("Test succeeded: " + description.getMethodName());
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("Test failed: " + description.getMethodName());
    }
}
