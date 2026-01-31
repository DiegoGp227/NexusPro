package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import com.erp.compras.model.Producto;

public interface IProductoService {

    Producto saveProducto(Producto producto);

    List<Producto> getProductos();

    Optional<Producto> findProducto(Long id);

    Optional<Producto> findByCodigo(String codigo);

    List<Producto> findByCategoria(String categoria);

    List<Producto> search(String query);

    void deleteProducto(Long id);

}
