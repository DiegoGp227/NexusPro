package com.erp.compras.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.erp.compras.model.ProveedorProducto;

@Repository
public interface IProveedorProductoRepository extends JpaRepository<ProveedorProducto, Long> {

    List<ProveedorProducto> findByProductoNombreContainingIgnoreCase(String nombre);

    List<ProveedorProducto> findByProveedorId(Long proveedorId);

    List<ProveedorProducto> findByProductoId(Long productoId);

    List<ProveedorProducto> findBySourceType(String sourceType);

    List<ProveedorProducto> findByVerified(Boolean verified);

    @Query("SELECT pp FROM ProveedorProducto pp WHERE " +
           "(LOWER(pp.producto.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(pp.producto.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(pp.proveedor.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(pp.producto.categoria) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<ProveedorProducto> searchByQuery(@Param("query") String query);

    @Query("SELECT pp FROM ProveedorProducto pp WHERE " +
           "(LOWER(pp.producto.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(pp.producto.descripcion) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(pp.proveedor.nombre) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:sourceType IS NULL OR pp.sourceType = :sourceType) AND " +
           "(:category IS NULL OR LOWER(pp.producto.categoria) = LOWER(:category)) AND " +
           "(:minRating IS NULL OR pp.supplierRating >= :minRating) AND " +
           "(:maxDeliveryDays IS NULL OR pp.diasEntregaMax <= :maxDeliveryDays) AND " +
           "(:maxPrice IS NULL OR pp.precio <= :maxPrice) AND " +
           "(:verifiedOnly = false OR pp.verified = true) AND " +
           "(:freeShippingOnly = false OR pp.shippingCost = 0)")
    List<ProveedorProducto> searchWithFilters(
            @Param("query") String query,
            @Param("sourceType") String sourceType,
            @Param("category") String category,
            @Param("minRating") BigDecimal minRating,
            @Param("maxDeliveryDays") Integer maxDeliveryDays,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("verifiedOnly") boolean verifiedOnly,
            @Param("freeShippingOnly") boolean freeShippingOnly);

}
