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


    @PostMapping("/set/favorites")
    public ResponseEntity<String> addFavorite(@RequestParam(name = "title") String title ,@RequestParam(name = "user_id") int user_id) {
        Book book = bookRepository.findByTitle(title);

        if (book == null) {
            return new ResponseEntity<>("Book with title \"" + title + "\" was not found.", HttpStatus.NOT_FOUND);
        }

        Optional<User> userOptional = userRepository.findById(user_id);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User with id " + user_id + " was not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get(); //to get the user
        user.getFavorites().add(book);
        userRepository.save(user);

        return ResponseEntity.ok("Book with title \"" + title + "\" was favorited.");
    }

    @GetMapping("/favorites")
    public List<Book> getFavorites(@RequestParam(name = "user_id") int user_id) {
        User user = userRepository.findById(user_id).get();
        List<Book> favorites = user.getFavorites();
        return user.getFavorites();
    }


    @GetMapping("/all")
    public List<Book> GetBooks() {
        return bookRepository.findAll();
    }

}
