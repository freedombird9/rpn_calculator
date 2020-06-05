package com.airwallex.rpncalculator.entity;

import lombok.AllArgsConstructor;

/**
 * Operand (single-value expression).
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/01 14:26
 * @since V1.0
 */
@AllArgsConstructor
public class Operand implements Expression {
    private double value;

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public Type getType() {
        return Type.OPERAND;
    }
}
