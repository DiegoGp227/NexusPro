package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.compras.model.Proveedor;
import com.erp.compras.repository.IProveedorRepository;

@Service
public class ProveedorService implements IProveedorService {

    @Autowired
    private IProveedorRepository repo;

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        return repo.save(proveedor);
    }

    @Override
    public List<Proveedor> getProveedores() {
        return repo.findAll();
    }

    @Override
    public Optional<Proveedor> findProveedor(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Proveedor> findByCodigo(String codigo) {
        return repo.findByCodigo(codigo);
    }

    @Override
    public List<Proveedor> search(String query) {
        return repo.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrContactoContainingIgnoreCase(
                query, query, query);
    }

    @Override
    public void deleteProveedor(Long id) {
        repo.deleteById(id);
    }

}
