/*
  @author OHM-ASTER
 */
package com.omarvalencia.userapi.controller;

import com.omarvalencia.userapi.model.User;
import com.omarvalencia.userapi.model.Address;
import com.omarvalencia.userapi.util.Utils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    // Lista de usuarios
    private final List<User> users = new CopyOnWriteArrayList<>();

    public UserController() {
        // Usuario 1 usando builder
        users.add(User.builder()
                .id(123)
                .email("user1@mail.com")
                .name("First User")
                .password(Utils.sha1("123456"))
                .createdAt(Utils.currentUkTime())
                .addresses(List.of(
                        new Address(1, "workaddress", "street No. 1", "UK"),
                        new Address(2, "homeaddress", "street No. 2", "AU")
                ))
                .build());

        // Usuario 2 usando setters
        User user2 = new User();
        user2.setId(124);
        user2.setEmail("user2@mail.com");
        user2.setName("Second User");
        user2.setPassword(Utils.sha1("654321"));
        user2.setCreatedAt(Utils.currentUkTime());
        user2.setAddresses(List.of(new Address(1, "main", "calle 45", "US")));
        users.add(user2);

        // Usuario 3 usando setters
        User user3 = new User();
        user3.setId(125);
        user3.setEmail("user3@mail.com");
        user3.setName("Third User");
        user3.setPassword(Utils.sha1("abcdef"));
        user3.setCreatedAt(Utils.currentUkTime());
        user3.setAddresses(List.of(new Address(1, "House", "Elm 1428", "US")));
        users.add(user3);


    }

    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String sortedBy) {
        // Ordenar usuarios si se especifica el campo, si no, regresamos como están
        Comparator<User> comparator = null;

        if (sortedBy != null) {
            switch (sortedBy) {
                case "email" -> comparator = Comparator.comparing(User::getEmail);
                case "id" -> comparator = Comparator.comparingInt(User::getId);
                case "name" -> comparator = Comparator.comparing(User::getName);
                case "created_at" -> comparator = Comparator.comparing(User::getCreatedAt);
                // si se pasa otro valor, no ordenamos
            }
        }

        if (comparator != null) {
            return users.stream().sorted(comparator).collect(Collectors.toList());
        } else {
            return new ArrayList<>(users); // sin orden
        }
    }

    // Obtener direcciones de un usuario
    @GetMapping("/{id}/addresses")
    public List<Address> getAddresses(@PathVariable int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .map(User::getAddresses)
                .orElse(Collections.emptyList());
    }

    // Actualizar una dirección del usuario
    @PutMapping("/{userId}/addresses/{addressId}")
    public Address updateAddress(@PathVariable int userId, @PathVariable int addressId, @RequestBody Address newAddress) {
        for (User user : users) {
            if (user.getId() == userId) {
                for (Address address : user.getAddresses()) {
                    if (address.getId() == addressId) {
                        address.setName(newAddress.getName());
                        address.setStreet(newAddress.getStreet());
                        address.setCountryCode(newAddress.getCountryCode());
                        return address;
                    }
                }
            }
        }
        throw new NoSuchElementException("No se encontró la dirección para ese usuario");
    }

    // Crear usuario nuevo
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(generateUserId());
        user.setPassword(Utils.sha1(user.getPassword()));
        user.setCreatedAt(Utils.currentUkTime());
        users.add(user);
        return user;
    }

    // Actualizar parcialmente un usuario
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        for (User user : users) {
            if (user.getId() == id) {
                if (updates.containsKey("email")) {
                    user.setEmail((String) updates.get("email"));
                }
                if (updates.containsKey("name")) {
                    user.setName((String) updates.get("name"));
                }
                return user;
            }
        }
        throw new NoSuchElementException("Usuario no encontrado");
    }

    // Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean removed = users.removeIf(u -> u.getId() == id);
        if (removed) {
            return "Usuario eliminado: " + id;
        } else {
            return "No se encontró el usuario con ID: " + id;
        }
    }

    // Genera el siguiente ID disponible
    private int generateUserId() {
        return users.stream().mapToInt(User::getId).max().orElse(100) + 1;
    }
}
