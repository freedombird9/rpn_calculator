package com.airwallex.rpncalculator.entity;


import com.airwallex.rpncalculator.enums.Operator;
import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

/**
 * Arithmetic expression
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/01 14:09
 * @since V1.0
 */
public class ArithmeticExpression implements Expression {

    @Getter
    private Operator operator;
    @Getter
    private Expression[] subExpressions = new Expression[2];
    private Double evaluationResult;


    public ArithmeticExpression(Operator operator, Expression e) {
        Validate.isTrue(operator.isUnary());

        this.operator = operator;
        this.subExpressions[0] = e;
    }

    public ArithmeticExpression(Operator operator, Expression e1, Expression e2) {
        Validate.isTrue(operator.isBinary());

        this.operator = operator;
        this.subExpressions[0] = e1;
        this.subExpressions[1] = e2;
    }

    @Override
    public Type getType() {
        return Type.ARITHMETIC_EXPRESSION;
    }

    @Override
    public double evaluate() throws UnknownOperatorException {

        Validate.notNull(subExpressions[0], "Expression is null", null);

        if (operator.isUnary()) {
            evaluationResult = evaluateUnaryExpression(operator, subExpressions[0].evaluate());
            return evaluationResult;
        }

        Validate.notNull(subExpressions[1], "The second expression is null", null);
        evaluationResult = evaluateBinaryExpression(operator, subExpressions[0].evaluate(),
                subExpressions[1].evaluate());
        return evaluationResult;
    }

    private double evaluateUnaryExpression(Operator operator, double prevResult)
            throws UnknownOperatorException {
        switch (operator) {
            case SQRT:
                return Math.sqrt(prevResult);
            default:
                throw new UnknownOperatorException("Unknown operator: " + operator);
        }
    }

    private double evaluateBinaryExpression(Operator operator, double preResult1, double prevResult2)
            throws UnknownOperatorException {
        switch (operator) {
            case PLUS:
                return preResult1 + prevResult2;
            case MINUS:
                return preResult1 - prevResult2;
            case MULTIPLY:
                return preResult1 * prevResult2;
            case DIVIDE:
                return preResult1 / prevResult2;
            default:
                throw new UnknownOperatorException("Unknown operator: " + operator);
        }
    }
}
