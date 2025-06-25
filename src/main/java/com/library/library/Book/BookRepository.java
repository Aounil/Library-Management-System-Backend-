package com.library.library.Book;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book,Integer> {


    public Book findByTitle(String title);

}
