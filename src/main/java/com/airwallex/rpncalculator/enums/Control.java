package com.airwallex.rpncalculator.enums;

import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import org.apache.commons.lang3.StringUtils;

/**
 * Control type.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/02 11:41
 * @since V1.0
 */
public enum Control {
    UNDO("undo"),
    CLEAR("clear");

    private String description;

    Control(String description) {
        this.description = description;
    }

    public String getValue() {
        return description;
    }

    public static boolean isControl(String c) {
        return UNDO.getValue().equalsIgnoreCase(c)
                || CLEAR.getValue().equalsIgnoreCase(c);
    }

    public static Control parseControl(String c) {
        switch (StringUtils.lowerCase(c)) {
            case "clear":
                return CLEAR;
            case "undo":
                return UNDO;
            default:
                throw new UnknownOperatorException("Unknown operator: " + c);
        }
    }
}
