package com.cloudscale.core.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudscale.core.dto.CreateAssetRequest;
import com.cloudscale.core.model.Asset;
import com.cloudscale.core.service.AssetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor // Injects AssetService via constructor
public class AssetController {

    private final AssetService assetService;

    /**
     * Create (Buy) a new asset record.
     */
    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody CreateAssetRequest request) {
        Asset asset = new Asset();
        asset.setSymbol(request.getSymbol().toUpperCase());
        asset.setQuantity(request.getQuantity());
        asset.setAveragePrice(request.getPricePaid());

        Asset savedAsset = assetService.saveAsset(asset);
        return ResponseEntity.ok(savedAsset);
    }

    /**
     * Get all assets in the user's portfolio.
     */
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }
}