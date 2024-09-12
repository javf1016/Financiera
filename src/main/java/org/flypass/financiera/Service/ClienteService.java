package org.flypass.financiera.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.flypass.financiera.Entity.Cliente;
import org.flypass.financiera.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente) {
        log.debug("Intentando crear cliente: {}", cliente);

        if (cliente.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
            log.error("El cliente no puede ser menor de edad: {}", cliente);
            throw new RuntimeException("El cliente no puede ser menor de edad.");
        }

        Cliente nuevoCliente = clienteRepository.save(cliente);
        log.info("Cliente creado con éxito: {}", nuevoCliente);

        return nuevoCliente;
    }

    public Cliente modificarCliente(Long id, Cliente clienteActualizado) {
        log.debug("Modificando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        cliente.setNombres(clienteActualizado.getNombres());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setEmail(clienteActualizado.getEmail());

        Cliente clienteModificado = clienteRepository.save(cliente);
        log.info("Cliente modificado con éxito: {}", clienteModificado);

        return clienteModificado;
    }

    public void eliminarCliente(Long id) {
        log.debug("Eliminando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        if (cliente.getProductos() != null && !cliente.getProductos().isEmpty()) {
            log.warn("El cliente con ID {} tiene productos vinculados y no puede ser eliminado", id);
            throw new RuntimeException("El cliente tiene productos vinculados y no puede ser eliminado.");
        }

        clienteRepository.delete(cliente);
        log.info("Cliente con ID {} eliminado con éxito", id);
    }
}
