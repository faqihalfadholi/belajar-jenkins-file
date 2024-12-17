package com.example.calculator.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CalculationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private Double result;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public CalculationHistory() {
        this.timestamp = LocalDateTime.now();
    }

    public CalculationHistory(String expression, Double result) {
        this.expression = expression;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

    // toString method untuk debugging
    @Override
    public String toString() {
        return "CalculationHistory{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", result=" + result +
                ", timestamp=" + timestamp +
                '}';
    }
}
