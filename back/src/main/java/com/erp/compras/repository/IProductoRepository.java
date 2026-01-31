package com.erp.compras.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.compras.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigo(String codigo);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByProveedorId(Long proveedorId);

    List<Producto> findByStockLessThanEqual(Integer stockMinimo);

    List<Producto> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
            String nombre, String codigo, String descripcion);

}
