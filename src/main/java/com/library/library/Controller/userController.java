package com.library.library.Controller;


import com.library.library.Book.book;
import com.library.library.Book.bookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/books")
public class userController {



    private final com.library.library.Book.bookRepository bookRepository;

    @Autowired
    public userController(bookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam String book_title) {
        book book =  bookRepository.findByTitle(book_title);
        return "you'r now reading the book " + book.getTitle() +" by "+ book.getAuthor() +" enjoy";
    }



    @GetMapping("/all")
    public List<book> GetBooks() {
        return bookRepository.findAll();
    }





}
