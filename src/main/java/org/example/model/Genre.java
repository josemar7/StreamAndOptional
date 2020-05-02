package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Genre [id=" + id + ", name=" + name + "]";
    }

}
