package com.example.calculator.controller;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.service.CalculationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class CalculatorController {

    private static final Logger LOGGER = Logger.getLogger(CalculatorController.class.getName());
    private final CalculationHistoryService calculationHistoryService;
    private final ScriptEngine scriptEngine;

    @Autowired
    public CalculatorController(CalculationHistoryService calculationHistoryService) {
        this.calculationHistoryService = calculationHistoryService;
        ScriptEngineManager manager = new ScriptEngineManager();
        this.scriptEngine = manager.getEngineByName("JavaScript");
        if (this.scriptEngine == null) {
            throw new IllegalStateException("JavaScript engine not found");
        }
    }

    @GetMapping("/")
    public String showCalculator(Model model) {
        List<CalculationHistory> history = calculationHistoryService.getAllCalculations();
        model.addAttribute("history", history);
        return "calculator";
    }

    @PostMapping("/calculate")
    @ResponseBody
    public CalculationHistory calculate(@RequestParam("expression") String expression) {
        try {
            double result = evaluateExpression(expression);
            return saveCalculation(expression, result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error during calculation", e);
            return null;
        }
    }

    private double evaluateExpression(String expression) throws ScriptException {
        Object result = scriptEngine.eval(expression);
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        }
        throw new ScriptException("Invalid expression result");
    }

    private CalculationHistory saveCalculation(String expression, double result) {
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setExpression(expression);
        calculationHistory.setResult(result);
        calculationHistory.setTimestamp(LocalDateTime.now());
        return calculationHistoryService.saveCalculation(calculationHistory);
    }

    @GetMapping("/history")
    @ResponseBody
    public List<CalculationHistory> getHistory() {
        return calculationHistoryService.getAllCalculations();
    }
}