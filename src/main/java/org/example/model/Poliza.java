package org.example.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poliza {

    Integer id;
    String nombre;
    List<Producto> productos;

}
