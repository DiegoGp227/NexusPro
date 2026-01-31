package com.erp.compras.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erp.compras.model.Recepcion;
import com.erp.compras.repository.IRecepcionRepository;

@Service
public class RecepcionService implements IRecepcionService {

    @Autowired
    private IRecepcionRepository repo;

    @Override
    public Recepcion save(Recepcion recepcion) {
        if (recepcion.getNumero() == null || recepcion.getNumero().isEmpty()) {
            recepcion.setNumero(generateNumero());
        }

        if (recepcion.getFecha() == null) {
            recepcion.setFecha(LocalDate.now());
        }

        if (recepcion.getEstado() == null || recepcion.getEstado().isEmpty()) {
            recepcion.setEstado("pendiente");
        }

        // Update estado based on items received
        if (recepcion.getItemsRecibidos() != null && recepcion.getItemsTotales() != null) {
            if (recepcion.getItemsRecibidos().equals(recepcion.getItemsTotales())) {
                recepcion.setEstado("completa");
            } else if (recepcion.getItemsRecibidos() > 0) {
                recepcion.setEstado("parcial");
            }
        }

        return repo.save(recepcion);
    }

    @Override
    public List<Recepcion> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Recepcion> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Recepcion> findByNumero(String numero) {
        return repo.findByNumero(numero);
    }

    @Override
    public List<Recepcion> findByEstado(String estado) {
        return repo.findByEstado(estado);
    }

    @Override
    public List<Recepcion> findByOrdenCompraId(Long ordenCompraId) {
        return repo.findByOrdenCompraId(ordenCompraId);
    }

    @Override
    public List<Recepcion> search(String query) {
        return repo.findByNumeroContainingIgnoreCaseOrOrdenCompraNumeroContainingIgnoreCase(query, query);
    }

    @Override
    public String generateNumero() {
        int year = LocalDate.now().getYear();
        long count = repo.count() + 1;
        return String.format("REC-%d-%03d", year, count);
    }

}
