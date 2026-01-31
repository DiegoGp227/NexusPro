package com.erp.compras.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionDTO {

    private String id;
    private String numero;
    private String fecha;
    private String ordenCompraId;
    private String ordenCompraNumero;
    private String proveedorNombre;
    private String estado;
    private Integer itemsRecibidos;
    private Integer itemsTotales;

}
