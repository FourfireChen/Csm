package com.chuansongmen.exception;

public class NotInitException extends Exception {
    public NotInitException() {
        super("Worker还没有初始化");
    }
}
