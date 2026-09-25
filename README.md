# Inditex Price Service Technical Test

Este repositorio contiene la implementación del ejercicio técnico para consultar la tarifa de precios aplicable a un producto y cadena en una fecha determinada.

## Arquitectura

El servicio está implementado utilizando **Arquitectura Hexagonal (Ports and Adapters)**, lo que permite separar claramente:
- **Capa de Dominio:** Contiene el modelo `Price`, los puertos de entrada y salida, y la lógica de negocio core (independiente de cualquier framework, incluyendo Spring).
- **Capa de Aplicación:** Implementa los casos de uso coordinando el dominio y los puertos.
- **Capa de Infraestructura:** Contiene los adaptadores, como el controlador REST y la implementación del repositorio JPA conectado a H2.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.3.4**
- **Base de datos H2 (In-memory)**
- **MapStruct** para el mapeo de objetos entre capas.
- **Lombok** para reducir el código boilerplate.
- **Karate** para pruebas funcionales (E2E).
- **OpenAPI / Swagger** para documentación (API First enfocado a los interfaces).

## Endpoints

### Consultar Tarifa Aplicable
**URL:** `/prices`
**Método:** `GET`
**Parámetros:**
- `applicationDate`: Fecha y hora de aplicación (formato ISO 8601, ej. `2020-06-14T10:00:00`).
- `productId`: ID del producto (ej. `35455`).
- `brandId`: ID de la marca/cadena (ej. `1` para ZARA).

**Ejemplo de Petición:**
```bash
curl -X GET "http://localhost:8080/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

## Pruebas

El servicio incluye pruebas unitarias y pruebas E2E configuradas con Karate que cubren las 5 casuísticas solicitadas, además de escenarios extra de errores HTTP 400 y 404.

Para ejecutar todas las pruebas:
```bash
mvn clean test
```

## Ejecución Segura con Mise

Para aislar la resolución de dependencias y garantizar que se utiliza un entorno consistente y reproducible (conectando siempre directamente a Maven Central y descartando configuraciones locales del sistema), el proyecto está configurado para utilizar `mise`.

### 1. Instalar herramientas
```bash
mise install
```

### 2. Ejecutar Tests y Compilar
```bash
mise exec -- mvn clean verify -s maven-central-settings.xml
```

### 3. Iniciar la aplicación
```bash
mise exec -- mvn spring-boot:run -s maven-central-settings.xml
```
Una vez iniciada, puedes acceder a:
- **Swagger UI (API Docs):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Consola H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Métricas Prometheus:** [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

## Observabilidad y Métricas

La aplicación expone las siguientes métricas funcionales y técnicas en formato Prometheus:
- `pricing_searches_success_total`: Contador de peticiones que han devuelto una tarifa válida satisfactoriamente.
- `pricing_searches_not_found_total`: Contador de peticiones que no han encontrado tarifa para los parámetros dados (equivalentes a errores funcionales 404).
- `http_server_requests_seconds_*`: Métricas nativas de Spring Boot que contabilizan las peticiones HTTP agrupadas por `status` code (200, 400, 404, 500) y por `uri`.

## Docker

Para construir y ejecutar la aplicación mediante Docker:

```bash
docker build -t price-service .
docker run -p 8080:8080 price-service
```
