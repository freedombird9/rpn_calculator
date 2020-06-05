package com.airwallex.rpncalculator.exception;

import com.airwallex.rpncalculator.enums.Operator;
import lombok.Getter;
import lombok.Setter;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 13:04
 * @since V1.0
 */
public class InsufficientParametersException extends Exception {

    // The operator that causes this exception
    @Getter
    @Setter
    private Operator operator;

    //Exception position
    @Getter
    @Setter
    private int position;

    public InsufficientParametersException(Operator operator) {
        this.operator = operator;
    }

    public InsufficientParametersException(String msg) {
        super(msg);
    }

    public InsufficientParametersException(Throwable cause) {
        super(cause);
    }

    public InsufficientParametersException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
