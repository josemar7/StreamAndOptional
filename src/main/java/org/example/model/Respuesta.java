package org.example.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    String codigo;
    List<Poliza> polizas;

}
