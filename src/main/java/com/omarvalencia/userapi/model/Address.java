/*
  @author OHM-ASTER
 */
package com.omarvalencia.userapi.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private int id;
    private String name;
    private String street;
    private String countryCode;
}
