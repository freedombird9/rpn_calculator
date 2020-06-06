package com.airwallex.rpncalculator.service;

import com.airwallex.rpncalculator.service.impl.CalculatorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/3 11:25
 * @since V1.0
 */
public class CalculatorServiceTest {

    CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();

    @Before
    public void init() {
        calculatorService.getElementFactory().reset();
    }

    @Test
    public void testPushBiOperator() {
        calculatorService.run("1 2 +");

        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals(1, evalStack.size());
        Assert.assertEquals("3", evalStack.peek());
    }

    @Test
    public void testInsufficientParams() {
        calculatorService.run("sqrt");
        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();

        Assert.assertTrue(evalStack.isEmpty());

        calculatorService.run("1 +");
        evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals(1, evalStack.size());
        Assert.assertEquals("1", evalStack.peek());
    }

    @Test
    public void testIllegalArithmetic() {
        calculatorService.run("1 2 3 0 / 8 9 +");

        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals(4, evalStack.size());
        Assert.assertEquals("0", evalStack.peek());
    }

    @Test
    public void testPushCompoundOps() {

        calculatorService.run("1 2 + 5 -");

        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();

        Assert.assertEquals(1, evalStack.size());
        Assert.assertEquals("-2", evalStack.peek());

        calculatorService.run("4 *");
        evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals("-8", evalStack.peek());
    }

    @Test
    public void testUndo() {
        // Undo operands
        calculatorService.run("4 undo");
        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertTrue(evalStack.isEmpty());

        calculatorService.run("4 3 undo undo");
        evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertTrue(evalStack.isEmpty());

        // Undo operator
        calculatorService.run("4 3 +");

        evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals("7", evalStack.peek());
        calculatorService.run("undo");
        evalStack = calculatorService.getElementFactory().evaluateElements();

        Assert.assertEquals(2, evalStack.size());
        Assert.assertEquals("3", evalStack.pop());
        Assert.assertEquals("4", evalStack.pop());
    }

    @Test
    public void testUndoClear() {

        calculatorService.run("3 4 clear");
        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();

        Assert.assertTrue(evalStack.isEmpty());

        calculatorService.run("undo");
        evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertEquals(2, evalStack.size());
        Assert.assertEquals("4", evalStack.pop());
        Assert.assertEquals("3", evalStack.pop());
    }

    @Test
    public void testClear() {
        calculatorService.run("3 4 * clear");
        Deque<String> evalStack = calculatorService.getElementFactory().evaluateElements();
        Assert.assertTrue(evalStack.isEmpty());
    }
}
