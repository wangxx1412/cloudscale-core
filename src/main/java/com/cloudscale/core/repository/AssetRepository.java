package com.cloudscale.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudscale.core.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    /**
     * Finds all assets for a specific ticker symbol.
     * Use case: Check if the user already owns this ETF.
     */
    List<Asset> findBySymbol(String symbol);
}