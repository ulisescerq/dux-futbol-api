# Fútbol API - Prueba Técnica Dux Software

API RESTful para gestionar equipos de fútbol con **seguridad JWT**, **documentación swagger** y **base de datos H2**.


## Endpoints Disponibles

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/auth/login` | Inicia sesión y obtiene un token JWT |
| `GET`  | `/equipos` | Lista todos los equipos |
| `GET`  | `/equipos/{id}` | Busca un equipo por ID |
| `GET`  | `/equipos/buscar?nombre={nombre}` | Busca por nombre (contiene) |
| `POST` | `/equipos` | Crea un nuevo equipo |
| `PUT`  | `/equipos/{id}` | Actualiza un equipo |
| `DELETE` | `/equipos/{id}` | Elimina un equipo |

> **Todos los endpoints (excepto `/auth/login`) requieren token JWT**


## Documentación Swagger

**Accede a la documentación completa y prueba los endpoints en tiempo real:**

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


## Cómo Ejecutar el Proyecto

# 1. Clonar el repositorio
git clone https://github.com/ulisescerq/dux-futbol-api.git
cd futbol-api

# 2. Compilar y ejecutar
./mvnw spring-boot:run
