package com.airwallex.rpncalculator.entity;

import com.airwallex.rpncalculator.enums.Control;
import com.airwallex.rpncalculator.enums.Operator;
import com.airwallex.rpncalculator.enums.Type;
import com.airwallex.rpncalculator.exception.InsufficientParametersException;
import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import com.airwallex.rpncalculator.formatter.PrettyPrint;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Stack element.
 * Can be operand, expression or control.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/5 20:11
 * @since V1.0
 */
public class Element {

    @Getter
    @Setter
    private Type type;
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private Element child1;
    @Getter
    @Setter
    private Element child2;

    private Double evalCache = null;

    private Element() {}

    public static class ElementFactory {
        private static final Deque<Element> operationLog = new ArrayDeque<>();
        private static final Deque<String> evalStack = new ArrayDeque<>();

        public void create(String token) throws InsufficientParametersException {
            Element element = new Element();

            element.setToken(token);

            // Operand
            if (NumberUtils.isParsable(token)) {
                element.setType(Type.OPERAND);
                operationLog.push(element);
            } else if (Operator.isOperator(token)) {
                buildExpression(element);
                operationLog.push(element);
            } else if (Control.isControl(token)) {
                element.setType(Type.CONTROL);
                Control control = Control.parseControl(token);
                switch (control) {
                    case UNDO:
                        if (operationLog.isEmpty()) {
                            break;
                        }
                        Element undoElement = operationLog.pop();
                        if (Type.ARITHMETIC_EXPRESSION.equals(undoElement.getType())) {
                            Operator operator = Operator.parseOperator(undoElement.getToken());
                            operationLog.push(undoElement.getChild1());
                            if (operator.isBinary()) {
                                operationLog.push(undoElement.getChild2());
                            }
                        }
                        break;
                    default:
                        operationLog.push(element);
                }
            } else {
                throw new IllegalArgumentException("Invalid input: " + token);
            }

        }

        public Deque<String> evaluateElements() {
            Iterator<Element> itr = operationLog.descendingIterator();

            evalStack.clear();

            while (itr.hasNext()) {
                Element element = itr.next();
                if (Type.CONTROL.equals(element.getType())
                        && Control.CLEAR.equals(Control.parseControl(element.getToken()))) {
                    evalStack.clear();
                } else {
                    evalStack.push(PrettyPrint.format(element.evaluate()));
                }
            }

            return evalStack;
        }

        public void reset() {
            operationLog.clear();
        }

        private void validateExpression(Operator operator) throws InsufficientParametersException {
            if (operationLog.peek() == null
                    || Control.isControl(operationLog.peek().getToken())) {
                throw new InsufficientParametersException(operator);
            }

            if (Operator.SQRT.equals(operator) && operationLog.peek().evaluate() < 0) {
                throw new ArithmeticException("sqrt on negative number");
            }
        }

        private void buildExpression(Element element) throws InsufficientParametersException {
            element.setType(Type.ARITHMETIC_EXPRESSION);
            Operator operator = Operator.parseOperator(element.getToken());

            validateExpression(operator);

            Element prevElement1 = operationLog.pop();
            element.setChild1(prevElement1);

            if (operator.isBinary()) {
                // Binary operator
                // Validate second element
                if (operationLog.peek() == null
                        || Control.isControl(operationLog.peek().getToken())) {
                    // Recover the stack
                    operationLog.push(prevElement1);
                    throw new InsufficientParametersException(operator);
                }

                if (Operator.DIVIDE.equals(operator) && prevElement1.evaluate() == 0) {
                    operationLog.push(prevElement1);
                    throw new ArithmeticException("/ 0");
                }

                element.setChild1(operationLog.pop());
                element.setChild2(prevElement1);
            }
        }
    }

    private double evaluate() {
        if (evalCache != null) {
            return evalCache;
        }

        if (Type.OPERAND.equals(type)) {
            evalCache = Double.parseDouble(token);
            return evalCache;
        }
        if (Type.CONTROL.equals(type)) {
            throw new UnsupportedOperationException("Control cannot be evaluated");
        }

        // Expression
        Operator operator = Operator.parseOperator(token);
        switch (operator) {
            case SQRT:
                evalCache = Math.sqrt((child1.evaluate()));
                break;
            case PLUS:
                evalCache = child1.evaluate() + child2.evaluate();
                break;
            case MINUS:
                evalCache = child1.evaluate() - child2.evaluate();
                break;
            case MULTIPLY:
                evalCache = child1.evaluate() * child2.evaluate();
                break;
            case DIVIDE:
                evalCache = child1.evaluate() / child2.evaluate();
                break;
            default:
                throw new UnknownOperatorException("Unknown operator: " + token);
        }

        return evalCache;
    }
}
