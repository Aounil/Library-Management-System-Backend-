package com.library.library.Book;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class book {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique=true)
    private String title;

    private String author;

    public book() {
    }

    public book(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

}
