package com.erp.compras.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.compras.model.Recepcion;

@Repository
public interface IRecepcionRepository extends JpaRepository<Recepcion, Long> {

    Optional<Recepcion> findByNumero(String numero);

    List<Recepcion> findByEstado(String estado);

    List<Recepcion> findByOrdenCompraId(Long ordenCompraId);

    List<Recepcion> findByNumeroContainingIgnoreCaseOrOrdenCompraNumeroContainingIgnoreCase(
            String numero, String ordenCompraNumero);

}
