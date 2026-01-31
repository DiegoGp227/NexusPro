package com.erp.compras.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erp.compras.dto.OrdenCompraDTO;
import com.erp.compras.model.OrdenCompra;
import com.erp.compras.service.IOrdenCompraService;

@RestController
@RequestMapping("/api/purchase-orders")
public class OrdenCompraController {

    @Autowired
    private IOrdenCompraService serv;

    @GetMapping
    public ResponseEntity<List<OrdenCompraDTO>> getAll() {
        List<OrdenCompra> lista = serv.getAll();
        List<OrdenCompraDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return serv.findById(id)
                .map(orden -> ResponseEntity.ok(toDTO(orden)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrdenCompraDTO>> search(@RequestParam String query) {
        List<OrdenCompra> lista = serv.search(query);
        List<OrdenCompraDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<OrdenCompraDTO>> getByEstado(@PathVariable String estado) {
        List<OrdenCompra> lista = serv.findByEstado(estado);
        List<OrdenCompraDTO> dtos = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrdenCompra ordenCompra) {
        try {
            OrdenCompra saved = serv.save(ordenCompra);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        return serv.findById(id)
                .map(existing -> {
                    ordenCompra.setId(id);
                    OrdenCompra updated = serv.save(ordenCompra);
                    return ResponseEntity.ok(toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return serv.findById(id)
                .map(existing -> {
                    serv.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private OrdenCompraDTO toDTO(OrdenCompra orden) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setId(orden.getId().toString());
        dto.setNumero(orden.getNumero());
        dto.setFecha(orden.getFecha().toString());
        dto.setProveedorId(orden.getProveedor().getId().toString());
        dto.setProveedorNombre(orden.getProveedor().getNombre());
        dto.setEstado(orden.getEstado());
        dto.setSubtotal(orden.getSubtotal());
        dto.setIva(orden.getIva());
        dto.setTotal(orden.getTotal());
        dto.setItems(orden.getItems() != null ? orden.getItems().size() : 0);
        return dto;
    }

}
