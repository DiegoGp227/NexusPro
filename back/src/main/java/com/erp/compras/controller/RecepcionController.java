package com.erp.compras.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erp.compras.dto.RecepcionDTO;
import com.erp.compras.model.Recepcion;
import com.erp.compras.service.IRecepcionService;

@RestController
@RequestMapping("/api/receptions")
public class RecepcionController {

    @Autowired
    private IRecepcionService serv;

    @GetMapping
    public ResponseEntity<List<RecepcionDTO>> getAll() {
        List<Recepcion> lista = serv.getAll();
        List<RecepcionDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return serv.findById(id)
                .map(recepcion -> ResponseEntity.ok(toDTO(recepcion)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecepcionDTO>> search(@RequestParam String query) {
        List<Recepcion> lista = serv.search(query);
        List<RecepcionDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<RecepcionDTO>> getByEstado(@PathVariable String estado) {
        List<Recepcion> lista = serv.findByEstado(estado);
        List<RecepcionDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/order/{ordenCompraId}")
    public ResponseEntity<List<RecepcionDTO>> getByOrdenCompra(@PathVariable Long ordenCompraId) {
        List<Recepcion> lista = serv.findByOrdenCompraId(ordenCompraId);
        List<RecepcionDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Recepcion recepcion) {
        try {
            Recepcion saved = serv.save(recepcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Recepcion recepcion) {
        return serv.findById(id)
                .map(existing -> {
                    recepcion.setId(id);
                    Recepcion updated = serv.save(recepcion);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private RecepcionDTO toDTO(Recepcion recepcion) {
        RecepcionDTO dto = new RecepcionDTO();
        dto.setId(recepcion.getId().toString());
        dto.setNumero(recepcion.getNumero());
        dto.setFecha(recepcion.getFecha().toString());
        dto.setOrdenCompraId(recepcion.getOrdenCompra().getId().toString());
        dto.setOrdenCompraNumero(recepcion.getOrdenCompra().getNumero());
        dto.setProveedorNombre(recepcion.getOrdenCompra().getProveedor().getNombre());
        dto.setEstado(recepcion.getEstado());
        dto.setItemsRecibidos(recepcion.getItemsRecibidos());
        dto.setItemsTotales(recepcion.getItemsTotales());
        return dto;
    }

}
