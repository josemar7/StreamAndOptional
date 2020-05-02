package org.example;

import org.example.model.Director;
import org.example.model.Genre;

import java.util.Map;

class DirectorGenre implements Map.Entry<Director, Genre> {
    private Director director;
    private Genre genre;

    public DirectorGenre(Director director, Genre genre) {
        this.director = director;
        this.genre = genre;
    }

    @Override
    public Director getKey() {
        return director;
    }

    @Override
    public Genre getValue() {
        return genre;
    }

    @Override
    public Genre setValue(Genre genre) {
        this.genre = genre;
        return genre;
    }
}
