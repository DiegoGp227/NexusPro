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
public class ProveedorDTO {

    private String id;
    private String codigo;
    private String nombre;
    private String rfc;
    private String contacto;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String estado;
    private Boolean activo;
    private String categoria;
    private BigDecimal rating;
    private Integer reviewCount;
    private Boolean verified;
    private String logo;

}
