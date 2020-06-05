package com.airwallex.rpncalculator.enums;

import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import org.apache.commons.lang3.StringUtils;

/**
 * Arithmetic operator
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/01 14:36
 * @since V1.0
 */
public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    SQRT("sqrt");

    private String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getValue() {
        return symbol;
    }


    public boolean isUnary() {
        return SQRT.equals(this);
    }

    public boolean isBinary() {
        return PLUS.equals(this) || MINUS.equals(this)
                || MULTIPLY.equals(this)
                || DIVIDE.equals(this);
    }

    public static boolean isOperator(String c) {
        return PLUS.getValue().equals(c) || MINUS.getValue().equals(c)
                || MULTIPLY.getValue().equals(c)
                || DIVIDE.getValue().equals(c)
                || SQRT.getValue().equalsIgnoreCase(c);
    }

    public static Operator parseOperator(String c) {
        switch (StringUtils.lowerCase(c)) {
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "sqrt":
                return SQRT;
            default:
                throw new UnknownOperatorException("Unknown operator: " + c);
        }
    }
}
