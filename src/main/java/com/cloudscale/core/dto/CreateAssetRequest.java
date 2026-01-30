package com.cloudscale.core.dto;

import lombok.Data;

/**
 * DTO for recording a new asset purchase.
 */
@Data
public class CreateAssetRequest {
    private String symbol;      // e.g., "VFV.TO"
    private Double quantity;    // e.g., 10.5
    private Double pricePaid;   // The price at which the asset was bought
}