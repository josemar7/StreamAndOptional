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
public class Movie {
    private int id;
    private String title;
    private int year;
    private String imdb;
    private List<Genre> genres;
    private List<Director> directors;

    {
        genres = new ArrayList<>();
        directors = new ArrayList<>();
    }

    public Movie(int id, String title, int year, String imdb) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.imdb = imdb;
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", year=" + year + "]";
    }
}