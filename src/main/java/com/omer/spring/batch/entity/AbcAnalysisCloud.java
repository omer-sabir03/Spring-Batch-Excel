package com.omer.spring.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ABC_ANALYSIS_CLOUD")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbcAnalysisCloud {
    @Id
    @Column(name = "AUDIT_ID", length = 191)
    private String auditId;

    @Column(name = "material_no")
    private String materialNo;

    @Column(name = "plant")
    private String plant;

    @Column(name = "base_uom")
    private String baseUom;

    @Column(name = "unit_rate")
    private String unitRate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "quantity_consumed")
    private String quantityConsumed;

    @Column(name = "material_type")
    private String materialType;

    @Column(name = "material_group")
    private String materialGroup;

    @Column(name = "purchasing_group")
    private String purchasingGroup;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "unspsc")
    private String unspsc;

    @Column(name = "industry")
    private String industry;

    @Column(name = "usage_per_year")
    private String usagePerYear;

    @Column(name = "cost_per_year")
    private String costPerYear;

    @Column(name = "cumulative_percentage")
    private String cumulativePercentage;

    @Column(name = "category")
    private char category;
}
