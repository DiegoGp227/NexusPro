package com.erp.compras.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.erp.compras.model.Proveedor;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByCodigo(String codigo);

    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);

    List<Proveedor> findByCategoria(String categoria);

    List<Proveedor> findByActivo(Boolean activo);

    List<Proveedor> findByCiudadContainingIgnoreCase(String ciudad);

    List<Proveedor> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrContactoContainingIgnoreCase(
            String nombre, String codigo, String contacto);

}
