package com.erp.compras.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCompraDTO {

    private String id;
    private String numero;
    private String fecha;
    private String proveedorId;
    private String proveedorNombre;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private Integer items;

}
