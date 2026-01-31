package com.erp.compras.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erp.compras.dto.SupplierQuoteDTO;
import com.erp.compras.model.ProveedorProducto;
import com.erp.compras.service.IProveedorProductoService;

@RestController
@RequestMapping("/api/suppliers/quotes")
public class SupplierQuoteController {

    @Autowired
    private IProveedorProductoService serv;

    @GetMapping("/search")
    public ResponseEntity<List<SupplierQuoteDTO>> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "all") String sourceType,
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) Integer maxDeliveryDays,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false, defaultValue = "false") boolean verifiedOnly,
            @RequestParam(required = false, defaultValue = "false") boolean freeShippingOnly) {

        List<ProveedorProducto> results = serv.searchWithFilters(
                query, sourceType, category, minRating, maxDeliveryDays, maxPrice, verifiedOnly, freeShippingOnly);

        List<SupplierQuoteDTO> dtos = results.stream()
                .map(this::toDTO)
                .limit(6)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<SupplierQuoteDTO>> getAll() {
        List<ProveedorProducto> results = serv.getAll();
        List<SupplierQuoteDTO> dtos = results.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private SupplierQuoteDTO toDTO(ProveedorProducto pp) {
        SupplierQuoteDTO dto = new SupplierQuoteDTO();
        dto.setId(pp.getId().toString());
        dto.setSupplierId(pp.getProveedor().getId().toString());
        dto.setSupplierName(pp.getProveedor().getNombre());
        dto.setSupplierLogo(pp.getProveedor().getLogo());
        dto.setSupplierCity(pp.getSupplierCity() != null ? pp.getSupplierCity() : pp.getProveedor().getCiudad());
        dto.setSupplierRating(pp.getSupplierRating() != null ? pp.getSupplierRating() :
                (pp.getProveedor().getRating() != null ? pp.getProveedor().getRating() : BigDecimal.valueOf(4.5)));
        dto.setReviewCount(pp.getReviewCount() != null ? pp.getReviewCount() :
                (pp.getProveedor().getReviewCount() != null ? pp.getProveedor().getReviewCount() : 0));

        dto.setProductName(pp.getProducto().getNombre());
        dto.setProductDescription(pp.getProducto().getDescripcion());
        dto.setProductCategory(pp.getProducto().getCategoria());
        dto.setUnitPrice(pp.getPrecio());
        dto.setCurrency(pp.getCurrency() != null ? pp.getCurrency() : "USD");
        dto.setMinQuantity(pp.getMinQuantity() != null ? pp.getMinQuantity() : 1);
        dto.setAvailableStock(pp.getStock());
        dto.setUnit(pp.getUnit() != null ? pp.getUnit() : "pieza");

        SupplierQuoteDTO.DeliveryDays deliveryDays = new SupplierQuoteDTO.DeliveryDays();
        deliveryDays.setMin(pp.getDiasEntregaMin() != null ? pp.getDiasEntregaMin() : 1);
        deliveryDays.setMax(pp.getDiasEntregaMax() != null ? pp.getDiasEntregaMax() : 3);
        dto.setDeliveryDays(deliveryDays);

        dto.setShippingCost(pp.getShippingCost() != null ? pp.getShippingCost() : BigDecimal.ZERO);
        dto.setFreeShippingMinimum(pp.getFreeShippingMinimum());

        // Parse comma-separated values
        dto.setPaymentTerms(parseCommaSeparated(pp.getPaymentTerms()));
        dto.setConditions(parseCommaSeparated(pp.getConditions()));

        dto.setVerified(pp.getVerified() != null ? pp.getVerified() : false);
        dto.setHighlight(pp.getHighlight());
        dto.setSourceType(pp.getSourceType() != null ? pp.getSourceType() : "local");

        return dto;
    }

    private List<String> parseCommaSeparated(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
