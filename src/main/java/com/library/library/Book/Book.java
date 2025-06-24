package com.library.library.Book;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique=true)
    private String title;

    private String author;

    // We use a Set instead of a List for categories to prevent duplicate entries.
    // A Set ensures that each category is unique, which matches the typical use case for tags or categories.
    // The @ElementCollection annotation tells JPA to persist this simple value collection in a separate table.
    @ElementCollection
    private Set<String> categories = new HashSet<>();

    public Book() {
    }

    public Book(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

}
