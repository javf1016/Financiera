package org.flypass.financiera.Controller;

import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.Cliente;
import org.flypass.financiera.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        log.debug("Petición para crear cliente: {}", cliente);
        Cliente nuevoCliente = clienteService.crearCliente(cliente);
        log.info("Cliente creado: {}", nuevoCliente);
        return ResponseEntity.status(201).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        log.debug("Petición para actualizar cliente con ID: {}", id);
        Cliente clienteActualizado = clienteService.modificarCliente(id, cliente);
        log.info("Cliente actualizado con éxito: {}", clienteActualizado);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.debug("Petición para eliminar cliente con ID: {}", id);
        clienteService.eliminarCliente(id);
        log.info("Cliente con ID {} eliminado", id);
        return ResponseEntity.noContent().build();
    }
}
