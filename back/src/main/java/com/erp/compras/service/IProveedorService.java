package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import com.erp.compras.model.Proveedor;

public interface IProveedorService {

    Proveedor saveProveedor(Proveedor proveedor);

    List<Proveedor> getProveedores();

    Optional<Proveedor> findProveedor(Long id);

    Optional<Proveedor> findByCodigo(String codigo);

    List<Proveedor> search(String query);

    void deleteProveedor(Long id);

}
