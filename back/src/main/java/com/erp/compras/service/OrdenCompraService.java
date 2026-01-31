package com.erp.compras.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.erp.compras.model.OrdenCompra;
import com.erp.compras.model.OrdenCompraItem;
import com.erp.compras.repository.IOrdenCompraRepository;

@Service
public class OrdenCompraService implements IOrdenCompraService {

    @Autowired
    private IOrdenCompraRepository repo;

    private static final BigDecimal IVA_RATE = new BigDecimal("0.16");

    @Override
    @Transactional
    public OrdenCompra save(OrdenCompra ordenCompra) {
        // Calculate totals
        BigDecimal subtotal = BigDecimal.ZERO;

        if (ordenCompra.getItems() != null) {
            for (OrdenCompraItem item : ordenCompra.getItems()) {
                item.setOrdenCompra(ordenCompra);
                BigDecimal itemSubtotal = item.getPrecioUnitario()
                        .multiply(new BigDecimal(item.getCantidad()));
                item.setSubtotal(itemSubtotal);
                subtotal = subtotal.add(itemSubtotal);
            }
        }

        ordenCompra.setSubtotal(subtotal);
        ordenCompra.setIva(subtotal.multiply(IVA_RATE));
        ordenCompra.setTotal(subtotal.add(ordenCompra.getIva()));

        if (ordenCompra.getNumero() == null || ordenCompra.getNumero().isEmpty()) {
            ordenCompra.setNumero(generateNumero());
        }

        if (ordenCompra.getFecha() == null) {
            ordenCompra.setFecha(LocalDate.now());
        }

        if (ordenCompra.getEstado() == null || ordenCompra.getEstado().isEmpty()) {
            ordenCompra.setEstado("borrador");
        }

        return repo.save(ordenCompra);
    }

    @Override
    public List<OrdenCompra> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<OrdenCompra> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<OrdenCompra> findByNumero(String numero) {
        return repo.findByNumero(numero);
    }

    @Override
    public List<OrdenCompra> findByEstado(String estado) {
        return repo.findByEstado(estado);
    }

    @Override
    public List<OrdenCompra> findByProveedorId(Long proveedorId) {
        return repo.findByProveedorId(proveedorId);
    }

    @Override
    public List<OrdenCompra> search(String query) {
        return repo.findByNumeroContainingIgnoreCaseOrProveedorNombreContainingIgnoreCase(query, query);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public String generateNumero() {
        int year = LocalDate.now().getYear();
        long count = repo.count() + 1;
        return String.format("OC-%d-%03d", year, count);
    }

}
