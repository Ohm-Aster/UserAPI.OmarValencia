# User API 

Este proyecto es una API REST creada con **Spring Boot**. Permite realizar operaciones básicas (CRUD) sobre una lista simulada de usuarios y sus direcciones. La aplicación fue desarrollada en Java 17 y está desplegada en [Render](https://userapi-omarvalencia.onrender.com/swagger-ui/index.html).

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.3.x
- Spring Web
- Lombok
- JUnit 5 (Pruebas unitarias)
- Swagger UI (Documentación interactiva)
- Maven

---

## Funcionalidades principales

La API permite:

✔️ Obtener la lista de usuarios (opcionalmente ordenados)  
✔️ Consultar las direcciones de un usuario por ID  
✔️ Crear nuevos usuarios  
✔️ Actualizar parcialmente un usuario (`PATCH`)  
✔️ Actualizar una dirección específica (`PUT`)  
✔️ Eliminar usuarios por ID

Los datos son simulados en memoria, no se usa base de datos.

---

## Endpoints disponibles

| Método | Endpoint                         | Descripción                                 |
|--------|----------------------------------|---------------------------------------------|
| GET    | `/users`                         | Lista todos los usuarios                    |
| GET    | `/users?sortedBy=name`           | Lista usuarios ordenados (por `id`, `email`, `name`, `created_at`) |
| GET    | `/users/{id}/addresses`          | Direcciones de un usuario específico        |
| POST   | `/users`                         | Crear un nuevo usuario                      |
| PATCH  | `/users/{id}`                    | Actualizar campos parciales del usuario     |
| PUT    | `/users/{id}/addresses/{id}`     | Editar una dirección del usuario            |
| DELETE | `/users/{id}`                    | Eliminar usuario                            |

---

## Formato esperado (JSON)

### Crear un usuario:

`json
{
  "email": "nuevo@correo.com",
  "name": "NombreNuevo",
  "password": "clave123"
}`

La contraseña se encripta automáticamente usando SHA-1.

Pruebas unitarias

Incluye pruebas con MockMvc para validar que:
- Se puedan consultar usuarios
- Se puedan consultar direcciones
- Se cree un usuario correctamente
- Se actualicen campos específicos
- Se borren usuarios
- Maneje casos de usuarios inexistentes

Swagger UI

Para ver la documentación interactiva:

Abre en el navegador:
http://localhost:8080/swagger-ui.html

Producción en Render

La API está desplegada en Render:

https://userapi-omarvalencia.onrender.com/swagger-ui/index.html

Puedes probar todos los endpoints directamente desde la interfaz Swagger en línea.
