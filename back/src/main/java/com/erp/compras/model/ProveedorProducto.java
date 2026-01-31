package com.erp.compras.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name = "proveedor_producto")
public class ProveedorProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El precio no puede estar vacío")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    @NotNull(message = "El stock no puede estar vacío")
    @PositiveOrZero(message = "El stock debe ser positivo o cero")
    private Integer stock;

    @PositiveOrZero(message = "Los días de entrega mínimo deben ser positivos o cero")
    private Integer diasEntregaMin;

    @PositiveOrZero(message = "Los días de entrega máximo deben ser positivos o cero")
    private Integer diasEntregaMax;

    @Size(max = 100)
    private String ubicacion;

    @Size(max = 10)
    private String sourceType; // "local" o "web"

    @Size(max = 10)
    private String currency; // "USD", "MXN", "COP"

    @PositiveOrZero
    private Integer minQuantity;

    @PositiveOrZero
    private BigDecimal shippingCost;

    @PositiveOrZero
    private BigDecimal freeShippingMinimum;

    @Size(max = 500)
    private String paymentTerms; // Comma-separated: "Crédito 30 días,Transferencia,Tarjeta"

    @Size(max = 500)
    private String conditions; // Comma-separated conditions

    @Size(max = 20)
    private String highlight; // "price", "rating", "delivery"

    private Boolean verified = false;

    @Size(max = 100)
    private String supplierCity;

    private BigDecimal supplierRating;

    private Integer reviewCount;

    @Size(max = 50)
    private String unit; // "pieza", "caja", "resma", etc.

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

}
