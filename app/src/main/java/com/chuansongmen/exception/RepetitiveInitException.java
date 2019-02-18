package com.chuansongmen.exception;

public class RepetitiveInitException extends Exception {

    public RepetitiveInitException() {
        super("重复初始化异常");
    }
}
