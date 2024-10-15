package com.library.library.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface bookRepository extends JpaRepository<book,Integer> {


    public book findByTitle(String title);

}
