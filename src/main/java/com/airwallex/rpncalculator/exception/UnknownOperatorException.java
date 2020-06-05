package com.airwallex.rpncalculator.exception;

/**
 * Exception.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 12:01
 * @since V1.0
 */
public class UnknownOperatorException extends RuntimeException {
    public UnknownOperatorException(String msg) {
        super(msg);
    }

    public UnknownOperatorException(Throwable cause) {
        super(cause);
    }

    public UnknownOperatorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
