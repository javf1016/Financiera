package org.flypass.financiera.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.EstadoCuenta;
import org.flypass.financiera.Entity.Producto;
import org.flypass.financiera.Entity.TipoCuenta;
import org.flypass.financiera.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto) {
        log.debug("Creando producto: {}", producto);

        // Generar número de cuenta único
        String numeroCuenta = generarNumeroCuentaUnico(producto.getTipoCuenta());
        producto.setNumeroCuenta(numeroCuenta);

        Producto nuevoProducto = productoRepository.save(producto);

        log.info("Producto creado con éxito: {}", nuevoProducto);
        return nuevoProducto;
    }

    private String generarNumeroCuentaUnico(TipoCuenta tipoCuenta) {
        String prefijo;
        if (tipoCuenta == TipoCuenta.AHORRO) {
            prefijo = "53";
        } else if (tipoCuenta == TipoCuenta.CORRIENTE) {
            prefijo = "33";
        } else {
            throw new IllegalArgumentException("Tipo de cuenta desconocido");
        }

        Random random = new Random();
        String numeroCuenta;
        boolean existe;

        do {
            // Generar un número aleatorio de 8 dígitos
            int numeroAleatorio = 10000000 + random.nextInt(90000000);
            numeroCuenta = prefijo + numeroAleatorio;

            // Verificar si el número ya existe en la base de datos
            existe = productoRepository.existsByNumeroCuenta(numeroCuenta);

        } while (existe);

        return numeroCuenta;
    }

    public void cancelarCuenta(String numeroCuenta) {
        log.debug("Intentando cancelar cuenta con número: {}", numeroCuenta);

        Producto cuenta = productoRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));

        if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            log.error("La cuenta con número {} tiene saldo {} y no puede ser cancelada", numeroCuenta, cuenta.getSaldo());
            throw new RuntimeException("Solo se pueden cancelar cuentas con saldo igual a 0.");
        }

        cuenta.setEstado(EstadoCuenta.CANCELADA);
        productoRepository.save(cuenta);

        log.info("Cuenta con número {} cancelada con éxito", numeroCuenta);
    }
}
