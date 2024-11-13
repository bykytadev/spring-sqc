package com.sqc.academy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    @GetMapping
    public ResponseEntity<String> calculate(
            @RequestParam(value = "firstNumber", required = false, defaultValue = "0") Double firstNumber,
            @RequestParam(value = "secondNumber", required = false, defaultValue = "0") Double secondNumber,
            @RequestParam(value = "operator", required = true) String operator) {

        if (operator == null)
            return new ResponseEntity<>("Please provide operator.", HttpStatus.BAD_REQUEST);

        // Replace space with + for the operator
        operator = operator.replace(" ", "+");

        double result;
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    return new ResponseEntity<>("Cannot divide by zero.", HttpStatus.BAD_REQUEST);
                }
                result = firstNumber / secondNumber;
                break;
            default:
                return new ResponseEntity<>("Invalid operator. Please use +, -, *, or /.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The result is: " + result, HttpStatus.OK);
    }
}