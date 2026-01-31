package com.erp.compras.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erp.compras.model.ProveedorProducto;
import com.erp.compras.service.IProveedorProductoService;

@RestController
@RequestMapping("/api/catalogo")
public class ProveedorProductoController {

    @Autowired
    private IProveedorProductoService serv;

    @GetMapping("/buscar")
    public ResponseEntity<List<ProveedorProducto>> buscar(@RequestParam String nombre) {
        List<ProveedorProducto> lista = serv.buscarCatalogo(nombre);
        return ResponseEntity.ok(lista);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorProducto>> getAll() {
        List<ProveedorProducto> lista = serv.getAll();
        return ResponseEntity.ok(lista);
    }

}
