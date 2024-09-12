package org.flypass.financiera;

import org.flypass.financiera.Entity.Cliente;
import org.flypass.financiera.Repository.ClienteRepository;
import org.flypass.financiera.Service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testCrearCliente_MenorDeEdad() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now().minusYears(17));

        Assertions.assertThrows(RuntimeException.class, () -> clienteService.crearCliente(cliente));
    }

    @Test
    public void testCrearCliente_Exito() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now().minusYears(20));
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.crearCliente(cliente);
        assertNotNull(resultado);
        Mockito.verify(clienteRepository).save(cliente);
    }
}
