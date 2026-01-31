package com.erp.compras.service;

import java.util.List;
import com.erp.compras.model.Pedido;

public interface IPedidoService {

    void crearPedido(Long proveedorProductoId, Integer cantidad);

    List<Pedido> getPedidos();

}
