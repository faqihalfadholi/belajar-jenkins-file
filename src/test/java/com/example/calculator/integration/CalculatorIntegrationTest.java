package com.example.calculator.integration;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.repository.CalculationHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculationHistoryRepository repository;

    @Test
    void testCalculatorIntegration() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("expression", "2 + 2"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attribute("result", 4.0));

        CalculationHistory savedCalculation = repository.findAll().get(0);
        assertEquals("2 + 2", savedCalculation.getExpression());
        assertEquals(4.0, savedCalculation.getResult());
    }
}