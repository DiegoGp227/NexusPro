package com.erp.compras.model;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    @Column(unique = true)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 20, message = "El RFC no puede superar los 20 caracteres")
    private String rfc;

    @Size(max = 100, message = "El contacto no puede superar los 100 caracteres")
    private String contacto;

    @Email
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;

    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String direccion;

    @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres")
    private String ciudad;

    @Size(max = 100, message = "El estado no puede superar los 100 caracteres")
    private String estado;

    @Column(nullable = false)
    private Boolean activo = true;

    @Size(max = 50, message = "La categoría no puede superar los 50 caracteres")
    private String categoria;

    private BigDecimal rating;

    private Integer reviewCount;

    private Boolean verified = false;

    @Size(max = 500)
    private String logo;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    private List<ProveedorProducto> productos;

}
