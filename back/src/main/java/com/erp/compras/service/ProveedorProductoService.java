package com.erp.compras.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.compras.model.ProveedorProducto;
import com.erp.compras.repository.IProveedorProductoRepository;

@Service
public class ProveedorProductoService implements IProveedorProductoService {

    @Autowired
    private IProveedorProductoRepository repo;

    @Override
    public List<ProveedorProducto> buscarCatalogo(String nombreProducto) {
        return repo.findByProductoNombreContainingIgnoreCase(nombreProducto);
    }

    @Override
    public List<ProveedorProducto> searchWithFilters(
            String query,
            String sourceType,
            String category,
            BigDecimal minRating,
            Integer maxDeliveryDays,
            BigDecimal maxPrice,
            boolean verifiedOnly,
            boolean freeShippingOnly) {

        String sourceTypeParam = (sourceType != null && !sourceType.equals("all")) ? sourceType : null;
        String categoryParam = (category != null && !category.equals("all")) ? category : null;

        return repo.searchWithFilters(
                query,
                sourceTypeParam,
                categoryParam,
                minRating,
                maxDeliveryDays,
                maxPrice,
                verifiedOnly,
                freeShippingOnly);
    }

    @Override
    public List<ProveedorProducto> getAll() {
        return repo.findAll();
    }

    @Override
    public ProveedorProducto save(ProveedorProducto proveedorProducto) {
        return repo.save(proveedorProducto);
    }

}
