package com.erp.compras.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de orden es obligatorio")
    @Size(max = 20, message = "El número no puede superar los 20 caracteres")
    @Column(unique = true)
    private String numero;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estado; // borrador, pendiente, aprobada, recibida, cancelada

    @NotNull
    private BigDecimal subtotal = BigDecimal.ZERO;

    @NotNull
    private BigDecimal iva = BigDecimal.ZERO;

    @NotNull
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenCompraItem> items;

}
