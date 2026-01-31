package com.erp.compras.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierQuoteDTO {

    private String id;
    private String supplierId;
    private String supplierName;
    private String supplierLogo;
    private String supplierCity;
    private BigDecimal supplierRating;
    private Integer reviewCount;

    private String productName;
    private String productDescription;
    private String productCategory;
    private BigDecimal unitPrice;
    private String currency;
    private Integer minQuantity;
    private Integer availableStock;
    private String unit;

    private DeliveryDays deliveryDays;
    private BigDecimal shippingCost;
    private BigDecimal freeShippingMinimum;

    private List<String> paymentTerms;
    private List<String> conditions;

    private Boolean verified;
    private String highlight;
    private String sourceType;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryDays {
        private Integer min;
        private Integer max;
    }

}
