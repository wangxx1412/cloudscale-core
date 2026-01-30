package com.cloudscale.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudscale.core.model.Asset;
import com.cloudscale.core.repository.AssetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Automatically generates constructor for dependency injection
public class AssetService {

    private final AssetRepository assetRepository;

    /**
     * Records a new asset purchase to the database.
     * This is what you meant by "buying" an asset.
     */
    public Asset saveAsset(Asset asset) {
        // You can add business logic here, e.g., validation
        return assetRepository.save(asset);
    }

    /**
     * Retrieves all assets owned by the user.
     */
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
}