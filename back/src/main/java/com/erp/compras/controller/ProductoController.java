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
import com.erp.compras.model.Producto;
import com.erp.compras.service.IProductoService;

@RestController
@RequestMapping("/api/products")
public class ProductoController {

    @Autowired
    private IProductoService serv;

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        List<Producto> lista = serv.getProductos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return serv.findProducto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Producto>> search(@RequestParam String query) {
        List<Producto> lista = serv.search(query);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/category/{categoria}")
    public ResponseEntity<List<Producto>> getByCategory(@PathVariable String categoria) {
        List<Producto> lista = serv.findByCategoria(categoria);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        try {
            Producto saved = serv.saveProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto) {
        return serv.findProducto(id)
                .map(existing -> {
                    producto.setId(id);
                    Producto updated = serv.saveProducto(producto);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return serv.findProducto(id)
                .map(existing -> {
                    serv.deleteProducto(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
