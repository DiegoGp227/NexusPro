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
public class ProductoDTO {

    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String unidad;
    private BigDecimal precioUnitario;
    private Integer stock;
    private Integer stockMinimo;
    private String proveedorId;

}
