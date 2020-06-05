package com.airwallex.rpncalculator.entity;

import com.airwallex.rpncalculator.enums.Operator;
import org.junit.Assert;
import org.junit.Test;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 12:45
 * @since V1.0
 */
public class ArithmeticExpressionTest {

    // Expected calculator precision
    private static final double DELTA = 1e-15;

    @Test
    public void testEvaluateUnaryOps() {
        ArithmeticExpression expression = new ArithmeticExpression(Operator.SQRT, new Operand(4));

        Assert.assertEquals(2, expression.evaluate(), DELTA);

        expression = new ArithmeticExpression(Operator.SQRT, new Operand(2));

        Assert.assertEquals(Math.sqrt(2), expression.evaluate(), DELTA);
    }

    @Test
    public void testEvaluateBinaryOps() {
        ArithmeticExpression expression =
                new ArithmeticExpression(Operator.PLUS, new Operand(4)
                        , new Operand(2));

        Assert.assertEquals(6, expression.evaluate(), DELTA);

        expression =
                new ArithmeticExpression(Operator.MINUS, new Operand(4)
                        , new Operand(2));

        Assert.assertEquals(2, expression.evaluate(), DELTA);

        expression =
                new ArithmeticExpression(Operator.MULTIPLY, new Operand(4.222)
                        , new Operand(2.12893));

        Assert.assertEquals(2.12893 * 4.222, expression.evaluate(), DELTA);

        expression =
                new ArithmeticExpression(Operator.DIVIDE, new Operand(4.222)
                        , new Operand(2.12893));

        Assert.assertEquals(4.222 / 2.12893, expression.evaluate(), DELTA);

    }

    @Test
    public void testEvalCompoundExpression() {
        Expression sub1 = new ArithmeticExpression(Operator.PLUS, new Operand(4)
                , new Operand(2));

        Expression sub2 = new ArithmeticExpression(Operator.MULTIPLY, new Operand(2)
                , new Operand(3));

        Expression expression = new ArithmeticExpression(Operator.DIVIDE, sub1, sub2);

        Assert.assertEquals(1, expression.evaluate(), DELTA);

        sub1 = new ArithmeticExpression(Operator.SQRT, new Operand(2));

        expression = new ArithmeticExpression(Operator.DIVIDE, sub1, sub2);

        Assert.assertEquals(Math.sqrt(2) / 6, expression.evaluate(), DELTA);
    }
}
