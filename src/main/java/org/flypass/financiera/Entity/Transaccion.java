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
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_origen_id")
    private Producto cuentaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_destino_id")
    private Producto cuentaDestino;

    private BigDecimal monto;
    private LocalDateTime fechaTransaccion;
}
