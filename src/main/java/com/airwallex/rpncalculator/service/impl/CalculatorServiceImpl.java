package com.airwallex.rpncalculator.service.impl;

import com.airwallex.rpncalculator.entity.*;
import com.airwallex.rpncalculator.exception.InsufficientParametersException;
import com.airwallex.rpncalculator.service.CalculatorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Deque;
import java.util.Iterator;


/**
 * Calculator implementation.
 *
 * @author Yongfeng.Zhang
 * @date 2020/5/31 12:19
 * @since V1.0
 */
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {

    @Getter
    private final Element.ElementFactory elementFactory = new Element.ElementFactory();

    @Override
    public void run (String input) {
        if (StringUtils.isEmpty(input)) {
            return;
        }
        try {
            int position = 1;
            for (String c : input.trim().split("\\s+")) {
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
            log.warn("Input: {}, error: {}", input, e.getMessage());
        } catch (ArithmeticException e) {
            log.warn("Illegal arithmetic, input: {}, error: {}", input, e.getMessage());
        } catch (Exception e) {
            log.error("Unknown error, input: {}", input, e);
        }

        output(elementFactory.evaluateElements());
    }

    private void output(Deque<String> evalStack) {
        System.out.print("stack: ");

        Iterator<String> itr = evalStack.descendingIterator();

        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println();
    }
}
