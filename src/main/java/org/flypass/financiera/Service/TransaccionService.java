package org.flypass.financiera.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.Producto;
import org.flypass.financiera.Entity.TipoTransaccion;
import org.flypass.financiera.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class TransaccionService {

    private final ProductoRepository productoRepository;

    @Autowired
    public TransaccionService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public void realizarTransaccion(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal monto, TipoTransaccion tipo) {
        log.debug("Realizando transacción tipo {} con monto {} desde la cuenta {} hacia la cuenta {}",
                tipo, monto, numeroCuentaOrigen, numeroCuentaDestino);

        Producto cuentaOrigen = productoRepository.findByNumeroCuenta(numeroCuentaOrigen)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta origen no encontrada"));
        if (tipo == TipoTransaccion.CONSIGNACION) {
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().add(monto));

            log.info("Consignacion realizada con éxito de la cuenta {} a la cuenta {} con monto {}",
                    numeroCuentaOrigen, numeroCuentaDestino, monto);
        }else{
            if (tipo == TipoTransaccion.RETIRO && cuentaOrigen.getSaldo().compareTo(monto) < 0) {
                log.error("Saldo insuficiente en la cuenta origen {} para realizar el retiro de {}", numeroCuentaOrigen, monto);
                throw new RuntimeException("Saldo insuficiente.");
            }

            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(monto));

            if (tipo == TipoTransaccion.TRANSFERENCIA) {
                Producto cuentaDestino = productoRepository.findByNumeroCuenta(numeroCuentaDestino)
                        .orElseThrow(() -> new EntityNotFoundException("Cuenta destino no encontrada"));

                cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(monto));
                productoRepository.save(cuentaDestino);

                log.info("Transferencia realizada con éxito de la cuenta {} a la cuenta {} con monto {}",
                        numeroCuentaOrigen, numeroCuentaDestino, monto);
            }
            }

        productoRepository.save(cuentaOrigen);
        log.info("Transacción completada en la cuenta origen {} con nuevo saldo {}", numeroCuentaOrigen, cuentaOrigen.getSaldo());
    }
}
