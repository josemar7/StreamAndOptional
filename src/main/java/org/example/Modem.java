package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Modem {
    private Double price;

    public Modem(Double price) {
        this.price = price;
    }

}