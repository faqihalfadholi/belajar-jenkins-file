package com.example.calculator.service;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.repository.CalculationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorHistoryServiceTest {

    @Mock
    private CalculationHistoryRepository repository;

    private CalculationHistoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CalculationHistoryService(repository);
    }

    @Test
    void testSaveCalculation() {
        CalculationHistory history = new CalculationHistory();
        service.saveCalculation(history);
        verify(repository).save(history);
    }

    @Test
    void testGetAllCalculations() {
        List<CalculationHistory> expectedList = Arrays.asList(
                new CalculationHistory(),
                new CalculationHistory()
        );
        when(repository.findAll()).thenReturn(expectedList);

        List<CalculationHistory> actualList = service.getAllCalculations();

        assertEquals(expectedList, actualList);
        verify(repository).findAll();
    }
}