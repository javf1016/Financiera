package org.flypass.financiera.Controller;

import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.Producto;
import org.flypass.financiera.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        log.debug("Petición para crear producto: {}", producto);
        Producto nuevoProducto = productoService.crearProducto(producto);
        log.info("Producto creado con éxito: {}", nuevoProducto);
        return ResponseEntity.status(201).body(nuevoProducto);
    }

    @PutMapping("/{numeroCuenta}/cancelar")
    public ResponseEntity<Void> cancelarProducto(@PathVariable String numeroCuenta) {
        log.debug("Petición para cancelar producto con número de cuenta: {}", numeroCuenta);
        productoService.cancelarCuenta(numeroCuenta);
        log.info("Producto con número de cuenta {} cancelado", numeroCuenta);
        return ResponseEntity.noContent().build();
    }
}
