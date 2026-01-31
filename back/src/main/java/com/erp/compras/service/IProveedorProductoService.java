package com.erp.compras.service;

import java.math.BigDecimal;
import java.util.List;
import com.erp.compras.model.ProveedorProducto;

public interface IProveedorProductoService {

    List<ProveedorProducto> buscarCatalogo(String nombreProducto);

    List<ProveedorProducto> searchWithFilters(
            String query,
            String sourceType,
            String category,
            BigDecimal minRating,
            Integer maxDeliveryDays,
            BigDecimal maxPrice,
            boolean verifiedOnly,
            boolean freeShippingOnly);

    List<ProveedorProducto> getAll();

    ProveedorProducto save(ProveedorProducto proveedorProducto);

}
