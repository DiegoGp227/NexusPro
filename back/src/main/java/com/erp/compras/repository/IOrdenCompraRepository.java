package com.erp.compras.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.compras.model.OrdenCompra;

@Repository
public interface IOrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    Optional<OrdenCompra> findByNumero(String numero);

    List<OrdenCompra> findByEstado(String estado);

    List<OrdenCompra> findByProveedorId(Long proveedorId);

    List<OrdenCompra> findByNumeroContainingIgnoreCaseOrProveedorNombreContainingIgnoreCase(
            String numero, String proveedorNombre);

}
