package org.flypass.financiera.Controller;

import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.TipoTransaccion;
import org.flypass.financiera.Service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacciones")
@Slf4j
public class TransaccionController {

    private final TransaccionService transaccionService;

    @Autowired
    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> realizarTransferencia(
            @RequestParam String numeroCuentaOrigen,
            @RequestParam String numeroCuentaDestino,
            @RequestParam BigDecimal monto) {

        log.debug("Petición para realizar transferencia desde {} hacia {} con monto {}",
                numeroCuentaOrigen, numeroCuentaDestino, monto);

        transaccionService.realizarTransaccion(numeroCuentaOrigen, numeroCuentaDestino, monto, TipoTransaccion.TRANSFERENCIA);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/retiro")
    public ResponseEntity<Void> realizarRetiro(
            @RequestParam String numeroCuentaOrigen,
            @RequestParam BigDecimal monto) {

        log.debug("Petición para realizar retiro en la cuenta {} con monto {}", numeroCuentaOrigen, monto);

        transaccionService.realizarTransaccion(numeroCuentaOrigen, null, monto, TipoTransaccion.RETIRO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/consignacion")
    public ResponseEntity<Void> realizarConsignacion(
            @RequestParam String numeroCuentaOrigen,
            @RequestParam BigDecimal monto) {

        log.debug("Petición para realizar consignación en la cuenta {} con monto {}", numeroCuentaOrigen, monto);

        transaccionService.realizarTransaccion(numeroCuentaOrigen, null, monto, TipoTransaccion.CONSIGNACION);
        return ResponseEntity.ok().build();
    }
}
