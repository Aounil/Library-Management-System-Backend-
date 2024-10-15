package com.library.library.Controller;


import com.library.library.Book.book;
import com.library.library.Book.bookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@PreAuthorize("hasAnyAuthority('USER')")
@RestController
@RequestMapping("/user/books")
public class userController {


    private final com.library.library.Book.bookRepository bookRepository;

    public userController(bookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam String book_title) {
        book book =  bookRepository.findByTitle(book_title);
        return "you'r now reading the book " + book.getTitle() +" by "+ book.getAuthor() +" enjoy";
    }





}
