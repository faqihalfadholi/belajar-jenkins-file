package com.example.calculator.controller;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.service.CalculationHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
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
    void setUp() throws Exception {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            calculatorController = new CalculatorController(calculationHistoryService);
        }
    }

    @Test
    void testCalculate() {
        String expression = "2 + 2";
        CalculationHistory expectedHistory = new CalculationHistory(expression, 4.0);
        when(calculationHistoryService.saveCalculation(any(CalculationHistory.class))).thenReturn(expectedHistory);

        CalculationHistory result = calculatorController.calculate(expression);

        assertNotNull(result);
        assertEquals(expression, result.getExpression());
        assertEquals(4.0, result.getResult(), 0.001);
        verify(calculationHistoryService).saveCalculation(any(CalculationHistory.class));
    }

    @Test
    void testCalculateWithInvalidExpression() {
        String expression = "2 +";

        CalculationHistory result = calculatorController.calculate(expression);

        assertNull(result);
        verify(calculationHistoryService, never()).saveCalculation(any(CalculationHistory.class));
    }

    @Test
    void testShowCalculator() {
        List<CalculationHistory> mockHistory = Arrays.asList(
                new CalculationHistory("1 + 1", 2.0),
                new CalculationHistory("2 * 3", 6.0)
        );
        when(calculationHistoryService.getAllCalculations()).thenReturn(mockHistory);

        String viewName = calculatorController.showCalculator(model);

        assertEquals("calculator", viewName);
        verify(model).addAttribute("history", mockHistory);
    }

    @Test
    void testGetHistory() {
        List<CalculationHistory> mockHistory = Arrays.asList(
                new CalculationHistory("1 + 1", 2.0),
                new CalculationHistory("2 * 3", 6.0)
        );
        when(calculationHistoryService.getAllCalculations()).thenReturn(mockHistory);

        List<CalculationHistory> result = calculatorController.getHistory();

        assertEquals(mockHistory, result);
        verify(calculationHistoryService).getAllCalculations();
    }
}