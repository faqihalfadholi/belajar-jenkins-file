package com.example.calculator.controller;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.service.CalculationHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorControllerTest {

    @Mock
    private CalculationHistoryService calculationHistoryService;

    @Mock
    private Model model;

    private CalculatorController calculatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        calculatorController = new CalculatorController(calculationHistoryService);
    }

    @Test
    void testShowCalculator() {
        List<CalculationHistory> historyList = new ArrayList<>();
        when(calculationHistoryService.getAllCalculations()).thenReturn(historyList);

        String viewName = calculatorController.showCalculator(model);

        assertEquals("calculator", viewName);
        verify(model).addAttribute("history", historyList);
    }

    @Test
    void testCalculate() {
        String expression = "2 + 2";
        when(calculationHistoryService.getAllCalculations()).thenReturn(new ArrayList<>());

        String viewName = calculatorController.calculate(expression, model);

        assertEquals("calculator", viewName);
        verify(model).addAttribute("result", 4.0);
        verify(model).addAttribute("expression", expression);
        verify(calculationHistoryService).saveCalculation(any(CalculationHistory.class));
    }

    @Test
    void testCalculateWithInvalidExpression() {
        String expression = "2 +";
        when(calculationHistoryService.getAllCalculations()).thenReturn(new ArrayList<>());

        String viewName = calculatorController.calculate(expression, model);

        assertEquals("calculator", viewName);
        verify(model).addAttribute(eq("error"), anyString());
    }
}