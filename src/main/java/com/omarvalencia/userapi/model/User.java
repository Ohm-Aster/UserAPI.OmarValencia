/*
  @author OHM-ASTER
 */
package com.omarvalencia.userapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private String email;
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Evita mostrar la contraseña
    private String password;

    private String createdAt; // Ya formateada
    private List<Address> addresses;
}
