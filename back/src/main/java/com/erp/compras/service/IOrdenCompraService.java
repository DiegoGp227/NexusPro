package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import com.erp.compras.model.OrdenCompra;

public interface IOrdenCompraService {

    OrdenCompra save(OrdenCompra ordenCompra);

    List<OrdenCompra> getAll();

    Optional<OrdenCompra> findById(Long id);

    Optional<OrdenCompra> findByNumero(String numero);

    List<OrdenCompra> findByEstado(String estado);

    List<OrdenCompra> findByProveedorId(Long proveedorId);

    List<OrdenCompra> search(String query);

    void delete(Long id);

    String generateNumero();

}
