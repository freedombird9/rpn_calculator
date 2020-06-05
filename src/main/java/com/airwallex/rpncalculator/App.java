package com.airwallex.rpncalculator;

import com.airwallex.rpncalculator.service.CalculatorService;
import com.airwallex.rpncalculator.service.impl.CalculatorServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;


@Slf4j
public class App {

    public static void main(String[] args) {
        CalculatorServiceImpl calculatorService = new CalculatorServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            calculatorService.run(input);
        }
    }
}
