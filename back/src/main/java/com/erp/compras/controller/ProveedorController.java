package com.erp.compras.controller;

import java.util.List;
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
import com.erp.compras.model.Proveedor;
import com.erp.compras.service.IProveedorService;

@RestController
@RequestMapping("/api/suppliers")
public class ProveedorController {

    @Autowired
    private IProveedorService serv;

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAll() {
        List<Proveedor> lista = serv.getProveedores();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return serv.findProveedor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Proveedor>> search(@RequestParam String query) {
        List<Proveedor> lista = serv.search(query);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Proveedor proveedor) {
        try {
            Proveedor saved = serv.saveProveedor(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return serv.findProveedor(id)
                .map(existing -> {
                    proveedor.setId(id);
                    Proveedor updated = serv.saveProveedor(proveedor);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return serv.findProveedor(id)
                .map(existing -> {
                    serv.deleteProveedor(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
