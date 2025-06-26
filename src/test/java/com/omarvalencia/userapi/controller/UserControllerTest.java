/*
  @author OHM-ASTER
 */
package com.omarvalencia.userapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omarvalencia.userapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // --------------------------
    // --- Tests tipo GET ---
    // --------------------------

    // Verifica que /users responde con 200 y un JSON válido
    @Test
    void getUsers_deberiaResponderOkYJson() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        System.out.println("Test getUsers_deberiaResponderOkYJson passed");
    }

    // Verifica que /users/123/addresses retorna direcciones para un usuario válido
    @Test
    void getAddresses_deUsuario123_deberiaResponderOkYJson() throws Exception {
        mockMvc.perform(get("/users/123/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
        System.out.println("Test getAddresses_deUsuario123_deberiaResponderOkYJson passed");
    }

    // Verifica que un ID inexistente no lanza error, solo retorna lista vacía
    @Test
    void getAddresses_usuarioInexistente_deberiaRetornarListaVacia() throws Exception {
        mockMvc.perform(get("/users/999/addresses"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        System.out.println("Test getAddresses_usuarioInexistente_deberiaRetornarListaVacia passed");
    }

    // --------------------------
    // --- Tests tipo POST ---
    // --------------------------

    // Verifica que se puede crear un nuevo usuario y que regresa los datos esperados
    @Test
    void createUser_deberiaCrearUsuarioCorrectamente() throws Exception {
        String json = """
        {
          "email": "nuevo@ejemplo.com",
          "name": "NuevoUsuario",
          "password": "clave123"
        }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("nuevo@ejemplo.com"))
                .andExpect(jsonPath("$.name").value("NuevoUsuario"))
                .andExpect(jsonPath("$.password").doesNotExist());
        System.out.println("Test createUser_deberiaCrearUsuarioCorrectamente passed");
    }

    // --------------------------
    // --- Tests tipo PATCH ---
    // --------------------------

    // Verifica que se puede actualizar parcialmente un usuario (nombre solamente)
    @Test
    void patchUser_deberiaActualizarSoloNombre() throws Exception {
        String patchJson = "{\"name\": \"NombreEditado\"}";

        mockMvc.perform(patch("/users/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NombreEditado"));
        System.out.println("Test patchUser_deberiaActualizarSoloNombre passed");
    }

    // --------------------------
    // --- Tests tipo DELETE ---
    // --------------------------

    // Verifica que un usuario pueda ser eliminado correctamente
    @Test
    void deleteUser_deberiaEliminarUsuarioExistente() throws Exception {
        mockMvc.perform(delete("/users/124"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado: 124"));
        System.out.println("Test deleteUser_deberiaEliminarUsuarioExistente passed");
    }

    // Se intenta eliminar un usuario que no existe
    @Test
    void deleteUser_usuarioInexistente_deberiaResponderMensajeAdecuado() throws Exception {
        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isOk())
                .andExpect(content().string("No se encontró el usuario con ID: 999"));
        System.out.println("Test deleteUser_usuarioInexistente_deberiaResponderMensajeAdecuado passed");
    }
}
