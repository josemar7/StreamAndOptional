package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    private int id;
    private String name;
    private String imdb;
    private List<Movie> movies= new ArrayList<>();

    public Director(int id, String name, String imdb) {
        this.id = id;
        this.name = name;
        this.imdb = imdb;
    }

    @Override
    public String toString() {
        return "Director [id=" + id + ", name=" + name + ", imdb=" + imdb + "]";
    }
}