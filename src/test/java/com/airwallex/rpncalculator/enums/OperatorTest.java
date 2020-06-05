package com.airwallex.rpncalculator.enums;

import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import org.junit.Assert;
import org.junit.Test;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 21:35
 * @since V1.0
 */
public class OperatorTest {

    @Test
    public void testIsOperatorPositive() {
        Assert.assertTrue(Operator.isOperator("+"));
        Assert.assertTrue(Operator.isOperator("-"));
        Assert.assertTrue(Operator.isOperator("*"));
        Assert.assertTrue(Operator.isOperator("sqrt"));
        Assert.assertTrue(Operator.isOperator("/"));
    }

    @Test
    public void testIsOperatorNegative() {
        Assert.assertTrue(!Operator.isOperator("5"));
        Assert.assertTrue(!Operator.isOperator("a"));
        Assert.assertTrue(!Operator.isOperator("b5"));
        Assert.assertTrue(!Operator.isOperator("/*"));
        Assert.assertTrue(!Operator.isOperator(""));
        Assert.assertTrue(!Operator.isOperator(" "));
        Assert.assertTrue(!Operator.isOperator("\\"));
        Assert.assertTrue(!Operator.isOperator("\n"));
    }

    @Test
    public void testParseOperator() {
        Assert.assertEquals(Operator.PLUS, Operator.parseOperator("+"));
        Assert.assertEquals(Operator.MINUS, Operator.parseOperator("-"));
        Assert.assertEquals(Operator.MULTIPLY, Operator.parseOperator("*"));
        Assert.assertEquals(Operator.DIVIDE, Operator.parseOperator("/"));
        Assert.assertEquals(Operator.SQRT, Operator.parseOperator("sqrt"));
    }

    @Test
    public void testOperatorType() {
        Assert.assertTrue(Operator.SQRT.isUnary());
        Assert.assertTrue(!Operator.SQRT.isBinary());
        Assert.assertTrue(!Operator.PLUS.isUnary());
        Assert.assertTrue(Operator.PLUS.isBinary());
        Assert.assertTrue(Operator.DIVIDE.isBinary());
    }

    @Test(expected = UnknownOperatorException.class)
    public void testParseOperatorException() {
        Operator.parseOperator("sqrv");
        Operator.parseOperator("5");
        Operator.parseOperator("*+");

        Operator.parseOperator("");
        Operator.parseOperator(" ");
    }
}