package org.example.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    Integer id;
    String nombre;
    Integer unidades;
}
