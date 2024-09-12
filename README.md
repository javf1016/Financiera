# FlyPass

## Base de datos

* Postgresql

## Colección de Postman

La colección de Postman para el proyecto se encuentra en el siguiente enlace:

[FlyPass.postman_collection.json](https://github.com/javf1016/Images/tree/main/FlyPass)

## API
### Endpoint 1: Crear un cliente
#### URL: POST /clientes
#### Body (JSON):
{
    "tipoIdentificacion": "CC",
    "numeroIdentificacion": "1112223334",
    "nombres": "Luis",
    "apellidos": "Martinez",
    "email": "luis.martinez@email.com",
    "fechaNacimiento": "1988-06-21"
}

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/CrearCliente.PNG?raw=true)

### Endpoint 2: Modificar un cliente
#### URL: PUT /clientes/{id}
#### Ejemplo: PUT /clientes/1
#### Body (JSON):
{
    "tipoIdentificacion": "CC",
    "numeroIdentificacion": "1234567890",
    "nombres": "Juan Manuel",
    "apellidos": "Perez",
    "email": "juan.perez@email.com",
    "fechaNacimiento": "1990-01-01"
}

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/ModificarCliente.PNG?raw=true)

### Endpoint 3: Eliminar un cliente
#### URL: DELETE /clientes/{id}
#### Ejemplo: DELETE /clientes/3
#### Descripción: Elimina un cliente que no tiene productos asociados.

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/EliminarCliente.PNG?raw=true)

### Endpoint 4: Crear un producto (cuenta)
#### URL: POST /productos
#### Body (JSON):
{
    "tipoCuenta": "CORRIENTE",
    "saldo": 1000.00,
    "exentaGMF": true,
    "cliente": {
        "id": 1
    }
}

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/CrearProducto.PNG?raw=true)

### Endpoint 5: Cancelar un producto (cuenta)
#### URL: PUT /productos/{numeroCuenta}/cancelar
#### Ejemplo: PUT /productos/5300000001/cancelar
#### Descripción: Cancela la cuenta de ahorro con el número de cuenta 5300000001 si el saldo es 0.

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/CancelarCuenta.PNG?raw=true)

### Endpoint 6: Realizar una transferencia
#### URL: POST /transacciones/transferencia
#### Body (query parameters):
##### numeroCuentaOrigen: 5300000001
##### numeroCuentaDestino: 3300000001
##### monto: 500.00

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/Transferencia.PNG?raw=true)

### Endpoint 7: Realizar un retiro
#### URL: POST /transacciones/retiro
#### Body (query parameters):
##### numeroCuentaOrigen: 5300000001
##### monto: 300.00

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/Retiro.PNG?raw=true)

### Endpoint 8: Realizar una consignación
#### URL: POST /transacciones/consignacion
#### Body (query parameters):
##### numeroCuentaOrigen: 5300000001
##### monto: 500.00

##### Test

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/Consigancion.PNG?raw=true)

## Test
### ClienteServiceTest

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/ClienteTest.PNG?raw=true)

### ProductoServiceTest

![Exitoso](https://github.com/javf1016/Images/blob/main/FlyPass/ProductoTest.PNG?raw=true)

