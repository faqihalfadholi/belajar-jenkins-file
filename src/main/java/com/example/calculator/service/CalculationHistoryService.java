package com.example.calculator.service;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.repository.CalculationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationHistoryService {

    private final CalculationHistoryRepository calculationHistoryRepository;

    @Autowired
    public CalculationHistoryService(CalculationHistoryRepository calculationHistoryRepository) {
        this.calculationHistoryRepository = calculationHistoryRepository;
    }

    public CalculationHistory saveCalculation(CalculationHistory calculationHistory) {
        return calculationHistoryRepository.save(calculationHistory);
    }

    public List<CalculationHistory> getAllCalculations() {
        return calculationHistoryRepository.findAll();
    }

    public CalculationHistory getCalculationById(Long id) {
        return calculationHistoryRepository.findById(id).orElse(null);
    }

    public void deleteCalculation(Long id) {
        calculationHistoryRepository.deleteById(id);
    }
}
