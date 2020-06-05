//package com.airwallex.rpncalculator.service;
//
//import com.airwallex.rpncalculator.service.impl.CalculatorServiceImpl;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * description.
// *
// * @author Yongfeng.Zhang
// * @date 2020/6/3 11:25
// * @since V1.0
// */
//public class CalculatorServiceTest {
//
//    @Test
//    public void testPushBiOperator() {
//        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
//        calculatorService.getUserInput("1 2 +");
//
//        // Evaluate stack
//        calculatorService.output();
//
//        Assert.assertTrue(calculatorService.getEvaluationStack().size() == 1);
//        Assert.assertEquals(3, calculatorService.getEvaluationStack().peek(), 0);
//    }
//
//    @Test
//    public void testPushCompoundOps() {
//        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
//
//        calculatorService.getUserInput("1 2 + 5 -");
//
//        calculatorService.output();
//
//        Assert.assertTrue(calculatorService.getEvaluationStack().size() == 1);
//        Assert.assertEquals(-2, calculatorService.getEvaluationStack().peek(), 0);
//
//        calculatorService.getUserInput("4 *");
//        calculatorService.output();
//        Assert.assertEquals(-8, calculatorService.getEvaluationStack().peek(), 0);
//    }
//
//    @Test
//    public void testUndo() {
//        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
//        // Undo operands
//        calculatorService.getUserInput("4 undo");
//        calculatorService.output();
//        Assert.assertTrue(calculatorService.getEvaluationStack().isEmpty());
//
//        calculatorService.getUserInput("4 3 undo undo");
//        calculatorService.output();
//        Assert.assertTrue(calculatorService.getEvaluationStack().isEmpty());
//
//        // Undo operator
//        calculatorService.getUserInput("4 3 +");
//
//        calculatorService.output();
//        Assert.assertEquals(7, calculatorService.getEvaluationStack().peek(), 0);
//        calculatorService.getUserInput("undo");
//        calculatorService.output();
//
//        Assert.assertTrue(calculatorService.getEvaluationStack().size() == 2);
//        Assert.assertEquals(3, calculatorService.getEvaluationStack().pop(), 0);
//        Assert.assertEquals(4, calculatorService.getEvaluationStack().pop(), 0);
//    }
//
//    @Test
//    public void testUndoClear() {
//        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
//
//        calculatorService.getUserInput("3 4 clear");
//        calculatorService.output();
//        Assert.assertTrue(calculatorService.getEvaluationStack().isEmpty());
//
//        calculatorService.getUserInput("undo");
//        calculatorService.output();
//        Assert.assertTrue(calculatorService.getEvaluationStack().size() == 2);
//        Assert.assertEquals(4, calculatorService.getEvaluationStack().pop(), 0);
//        Assert.assertEquals(3, calculatorService.getEvaluationStack().pop(), 0);
//    }
//
//    @Test
//    public void testClear() {
//        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
//        calculatorService.getUserInput("3 4 * clear");
//        calculatorService.output();
//        Assert.assertTrue(calculatorService.getEvaluationStack().isEmpty());
//    }
//}
