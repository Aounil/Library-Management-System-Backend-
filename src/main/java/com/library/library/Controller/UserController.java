package com.library.library.Controller;


import com.library.library.Book.Book;
import com.library.library.Book.BookRepository;
import com.library.library.User.User;
import com.library.library.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books")
public class UserController {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

//    test
    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(name = "name") String bookTitle) {
        Book book = bookRepository.findByTitle(bookTitle);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book with title \"" + bookTitle + "\" was not found.");
        }

        return ResponseEntity.ok(" You're now reading the book \"" + book.getTitle() +
                "\" by " + book.getAuthor() + ". Enjoy!");
    }



    @GetMapping("/all")
    public List<Book> GetBooks() {
        return bookRepository.findAll();
    }

}
