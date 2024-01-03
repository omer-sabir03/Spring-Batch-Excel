package com.omer.spring.batch.entity;

import lombok.Data;

@Data
public class MaterialDTO {
    private String materialNumber;
    private String plant;
    private String baseUom;
    private String unitRate;
    private String currency;
    private String quantityConsumed;
    private String materialType;
    private String materialGroup;
    private String purchasingGroup;
    private String storageLocation;
    private String unspsc;
    private String industry;
}
