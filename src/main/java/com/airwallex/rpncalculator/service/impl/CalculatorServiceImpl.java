package com.airwallex.rpncalculator.service.impl;

import com.airwallex.rpncalculator.entity.*;
import com.airwallex.rpncalculator.enums.Control;
import com.airwallex.rpncalculator.enums.Operator;
import com.airwallex.rpncalculator.exception.InsufficientParametersException;
import com.airwallex.rpncalculator.formatter.PrettyPrint;
import com.airwallex.rpncalculator.service.CalculatorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Scanner;

import static com.airwallex.rpncalculator.enums.Control.UNDO;

/**
 * Calculator implementation.
 *
 * @author Yongfeng.Zhang
 * @date 2020/5/31 12:19
 * @since V1.0
 */
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {

    private Deque<Expression> operationLog = new ArrayDeque<>();

    @Getter
    private Deque<Double> evaluationStack = new ArrayDeque<>();

    private Element.ElementFactory elementFactory = new Element.ElementFactory();

    @Override
    public void getUserInput(String input) {

        if (StringUtils.isEmpty(input)) {
            return;
        }
        try {
            int position = 1;
            for (String c : input.split(" ")) {
                if (NumberUtils.isParsable(c)) {
                    // Input is number
                    pushNumber(Double.parseDouble(c));
                } else if (Operator.isOperator(c)) {
                    try {
                        pushOperator(Operator.parseOperator(c));
                    } catch (InsufficientParametersException e) {
                        e.setPosition(position);
                        throw e;
                    }
                } else if (Control.isControl(c)) {
                    Control control = Control.parseControl(c);
                    if (UNDO.equals(control)) {
                        undo();
                    } else {
                        clear();
                    }
                } else {
                    throw new IllegalArgumentException("Invalid symbol: " + c);
                }
                ++position;
            }
        } catch (InsufficientParametersException e) {
            log.warn("operator {} (position: {}): insufficient parameters"
                    , e.getOperator().getValue()
                    , e.getPosition());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid input", e);
        } catch (Exception e) {
            log.error("Unknown error", e);
        }
    }

    public void run(String input) {
        if (StringUtils.isEmpty(input)) {
            return;
        }
        try {
            int position = 1;
            for (String c : input.split(" ")) {
                try {
                    elementFactory.create(c);
                } catch (InsufficientParametersException e) {
                    e.setPosition(position);
                    throw e;
                }
                ++position;
            }

        } catch (InsufficientParametersException e) {
            log.warn("operator {} (position: {}): insufficient parameters"
                    , e.getOperator().getValue()
                    , e.getPosition());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid input", e);
        } catch (Exception e) {
            log.error("Unknown error", e);
        }

        output(elementFactory.evaluateElements());
    }

    private void output(Deque<String> evalStack) {
        evaluateStack();
        Iterator<String> itr = evalStack.descendingIterator();

        System.out.print("stack: ");
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println();
    }

    private void pushNumber(double input) {
        operationLog.push(new Operand(input));
    }

    private void pushOperator(Operator operator) throws InsufficientParametersException {

        Expression newExp = null;
        // Validate first expression
        if (operationLog.peek() == null || Control.CLEAR.equals(operationLog.peek())) {
            throw new InsufficientParametersException(operator);
        }
        Expression preExpression = operationLog.pop();

        if (operator.isUnary()) {
            newExp = new ArithmeticExpression(operator, preExpression);
        } else if (operator.isBinary()) {
            // Validate second expression
            if (operationLog.peek() == null || Control.CLEAR.equals(operationLog.peek())) {
                // Recover the stack
                operationLog.push(preExpression);
                throw new InsufficientParametersException(operator);
            }
            Expression preExpression0 = operationLog.pop();
            newExp = new ArithmeticExpression(operator, preExpression0, preExpression);
        }

        // Cache the evaluation result
        newExp.evaluate();
        operationLog.push(newExp);
    }

    private void undo() {
        if (operationLog.isEmpty()) {
            return;
        }

        Expression expression = operationLog.pop();
        if (expression instanceof Operand || expression instanceof Control) {
            return;
        }

        if (expression instanceof ArithmeticExpression) {
            // Undo the operator
            ArithmeticExpression arithmeticExpression = (ArithmeticExpression) expression;
            Operator operator = arithmeticExpression.getOperator();
            operationLog.push(arithmeticExpression.getSubExpressions()[0]);
            if (operator.isUnary()) {
                return;
            }
            if (operator.isBinary()) {
                operationLog.push(arithmeticExpression.getSubExpressions()[1]);
            }
        }
    }

    private void clear() {
        // Empty stack does not need clear
        if (operationLog.isEmpty()) {
            return;
        }

        // Record clear operation for possible undo later
        operationLog.push(Control.CLEAR);
    }

    private void evaluateStack() {
        Iterator<Expression> itr = operationLog.descendingIterator();

        evaluationStack.clear();

        while (itr.hasNext()) {
            Expression expression = itr.next();
            if (Type.CONTROL.equals(expression.getType())
                    && Control.CLEAR.equals(expression)) {
                // Clear in this case
                evaluationStack.clear();
            } else {
                evaluationStack.push(expression.evaluate());
            }
        }
    }
}
