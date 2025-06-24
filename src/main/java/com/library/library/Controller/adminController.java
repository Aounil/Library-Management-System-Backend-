package com.library.library.Controller;


import com.library.library.Book.Book;
import com.library.library.Book.bookRepository;
import com.library.library.User.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@PreAuthorize("hasAnyAuthority('ADMIN')")
@RestController
@RequestMapping("/admin/books")
public class adminController {


    private final bookRepository bookRepository;
    private final UserRepository userRepository;

    public adminController(com.library.library.Book.bookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/add")
    public List<Book> addBook(@RequestBody List<Book> Books) {
        return bookRepository.saveAll(Books);
    }

    @DeleteMapping("/delete/{book-id}")  //I don't know if it's better to let it like this or delete by title
    public void Delete(@PathVariable("book-id") Integer book_id) {
        bookRepository.deleteById(book_id);
    }

    @DeleteMapping("/user_delete/{user_id}")
    public String userDelete(@PathVariable("user_id") Integer user_id) {
        String msg = "the user "+ userRepository.findById(user_id).get().getName()+ " has been deleted";
        userRepository.deleteById(user_id);
        return msg;
    }

    @PostMapping("/{id}/addCategories")
    public ResponseEntity<Book> addCategory(@PathVariable("id") Integer book_id, @RequestBody Set<String> Categorys) {
        Book book = bookRepository.findById(book_id).get();
        book.setCategories(Categorys);
        return ResponseEntity.ok(bookRepository.save(book));
    }
}



