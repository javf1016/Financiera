package org.flypass.financiera.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private BigDecimal saldo;

    private Boolean exentaGMF;
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (tipoCuenta == TipoCuenta.AHORRO) {
            this.estado = EstadoCuenta.ACTIVA;
        }
    }

    @PreUpdate
    private void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
