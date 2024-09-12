package org.flypass.financiera;

import org.flypass.financiera.Entity.EstadoCuenta;
import org.flypass.financiera.Entity.Producto;
import org.flypass.financiera.Entity.TipoCuenta;
import org.flypass.financiera.Repository.ProductoRepository;
import org.flypass.financiera.Service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    public void testCrearProducto() {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.AHORRO);
        producto.setSaldo(BigDecimal.valueOf(1000));

        when(productoRepository.save(producto)).thenReturn(producto);
        Producto nuevoProducto = productoService.crearProducto(producto);
        assertNotNull(nuevoProducto);
        verify(productoRepository).save(producto);
    }
}
