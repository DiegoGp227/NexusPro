package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.compras.model.Producto;
import com.erp.compras.repository.IProductoRepository;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository repo;

    @Override
    public Producto saveProducto(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public List<Producto> getProductos() {
        return repo.findAll();
    }

    @Override
    public Optional<Producto> findProducto(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        return repo.findByCodigo(codigo);
    }

    @Override
    public List<Producto> findByCategoria(String categoria) {
        return repo.findByCategoria(categoria);
    }

    @Override
    public List<Producto> search(String query) {
        return repo.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
                query, query, query);
    }

    @Override
    public void deleteProducto(Long id) {
        repo.deleteById(id);
    }

}
