package org.example;

public class Book {

    private String name;
    private String[] words;

    public Book(String name, String[] words) {
        this.name = name;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public static final Book[] SOMEBOOKS = {
            new Book("Book1", new String[]{"w1", "w2"}),
            new Book("Book2", new String[]{"w3", "w4"}),
            new Book("Book3", new String[]{"w2", "w8"}),
            new Book("Book4", new String[]{"w3", "w2"}),
            new Book("Book5", new String[]{"w3", "w4"})
    };
}
