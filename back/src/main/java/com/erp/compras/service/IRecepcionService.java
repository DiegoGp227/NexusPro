package com.erp.compras.service;

import java.util.List;
import java.util.Optional;
import com.erp.compras.model.Recepcion;

public interface IRecepcionService {

    Recepcion save(Recepcion recepcion);

    List<Recepcion> getAll();

    Optional<Recepcion> findById(Long id);

    Optional<Recepcion> findByNumero(String numero);

    List<Recepcion> findByEstado(String estado);

    List<Recepcion> findByOrdenCompraId(Long ordenCompraId);

    List<Recepcion> search(String query);

    String generateNumero();

}
