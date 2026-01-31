package com.erp.compras.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "recepciones")
public class Recepcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de recepción es obligatorio")
    @Size(max = 20, message = "El número no puede superar los 20 caracteres")
    @Column(unique = true)
    private String numero;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenCompra ordenCompra;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20)
    private String estado; // pendiente, parcial, completa

    @NotNull
    @PositiveOrZero
    private Integer itemsRecibidos = 0;

    @NotNull
    @PositiveOrZero
    private Integer itemsTotales = 0;

}
