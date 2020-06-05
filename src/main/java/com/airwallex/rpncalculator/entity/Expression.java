package com.airwallex.rpncalculator.entity;

/**
 * A general expression that
 * can be evaluated.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/01 14:06
 * @since V1.0
 */
public interface Expression {

    double evaluate();

    Type getType();
}
