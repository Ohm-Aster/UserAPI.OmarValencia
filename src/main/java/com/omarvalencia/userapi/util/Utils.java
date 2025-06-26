/*
  @author OHM-ASTER
 */
package com.omarvalencia.userapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    // Encripta una contraseña en SHA-1
    public static String sha1(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Password no puede ser nulo o vacio");
        }
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Esto no debería pasar con "SHA-1"
            throw new RuntimeException("SHA-1 no soportado en esta máquina", e);
        }
    }

    // Regresa la hora actual en UK con formato pedido
    public static String currentUkTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/London"))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}

