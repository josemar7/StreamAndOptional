package org.example.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    Integer id;
    String nombre;
    List<Servicio> servicios;

}
