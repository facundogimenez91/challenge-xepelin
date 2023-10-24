# Challenge para Xepelin

## Stack utilizado:

- Java 17
- Spring Boot Webflux
- Netty Server
- JUnit y Mockito
- Postgres

## Consideraciones generales:

- Se utilizo programacion reactiva para optimizar los recursos disponibles
- Se guardan las operaciones de creacion de cuenta, deposito y retiro como eventos para posterior audiotoria sin afectar la respuesta del request
- Se tuvo en cuenta la atomizidad de las transacciones para garantizar la integridad de los balances
- La arquitectura es compatible para ser desplegada de forma distribuida.
- Los endpoints son thread-safe
- Se agrego una alerta cuando un deposito supera los 10.000
- La base de datos no persiste al volver a iniciar el container, de querer hacerlo montar el volumen correspondiente agregando a docker-compose.yml:
``` 
volumes:./postgres/data:/var/lib/postgresql/data
```

### Collection:

Se incluye una collection de Postman + Enviroment de ejemplo con todos los endpoints (root folder postman)

### DB Postgres:

Se incluye script para inicializar la DB de Postgres (root folder), el mismo se corre al iniciar el contenedor db

## Como probar el proyecto:

En el root del proyecto ejecutar
```
docker-compose build app
docker-compose up app
```