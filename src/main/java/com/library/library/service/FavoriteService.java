package com.library.library.service;

import com.library.library.Book.Book;
import com.library.library.Book.BookRepository;
import com.library.library.User.User;
import com.library.library.User.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class FavoriteService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public FavoriteService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<String> addfavoriteBook(int bookId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        if (!user.getFavorites().contains(book)) {
            user.getFavorites().add(book);
            userRepository.save(user);
            return ResponseEntity.ok("Book with title \"" + book.getTitle() + "\" was favorited.");
        } else {
            return ResponseEntity.ok("Book with title \"" + book.getTitle() + "\" is already in favorites.");
        }
    }

    public List<Book> getFavorites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));
        return user.getFavorites();
    }

    public ResponseEntity<String> removeFavorite(int bookId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        if (user.getFavorites().contains(book)) {
            user.getFavorites().remove(book);
            userRepository.save(user);
            return ResponseEntity.ok("Book with title \"" + book.getTitle() + "\" was removed.");
        } else {
            return ResponseEntity.ok("Book with title \"" + book.getTitle() + "\" was not in favorites.");
        }
    }

}