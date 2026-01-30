package com.cloudscale.core.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "assets")
@Data // Generates getters, setters, etc.
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The ticker symbol, e.g., "VFV.TO" or "QQC.TO"
    @Column(nullable = false)
    private String symbol;

    // Total quantity owned
    @Column(nullable = false)
    private Double quantity;

    // Average cost per share (to calculate profit later)
    @Column(nullable = false)
    private Double averagePrice;

    // Record creation timestamp
    private LocalDateTime createdAt;

    // Automatically set timestamp before saving
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}