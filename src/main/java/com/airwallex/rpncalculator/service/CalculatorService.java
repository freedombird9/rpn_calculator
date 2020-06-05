package com.airwallex.rpncalculator.service;

import com.airwallex.rpncalculator.enums.Operator;
import com.airwallex.rpncalculator.exception.InsufficientParametersException;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 21:52
 * @since V1.0
 */
public interface CalculatorService {

    /**
     * Get input from user
     */
    void getUserInput(String input);

//    /**
//     * Input a number into calculator.
//     *
//     * @param input
//     */
//    void pushNumber(double input);
//
//
//    /**
//     * Input an operator (+ - * / sqrt) into calculator.
//     * throws InsufficientParametersException if the operator
//     * cannot find enough operands/expressions.
//     *
//     * @param operator
//     * @throws InsufficientParametersException
//     */
//    void pushOperator(Operator operator) throws InsufficientParametersException;
//
//    /**
//     *  Undoes the previous operation, including inputting numbers, operators and
//     *  clear operation.
//     */
//    void undo();
//
//    /**
//     * Removes all items from the stack.
//     * Can be undone.
//     *
//     */
//    void clear();

//
//    /**
//     * Output the calculation result.
//     */
//    void output();

}

