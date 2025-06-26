package com.library.library.Controller;

import com.library.library.Book.Book;
import com.library.library.Book.BookRepository;
import com.library.library.User.User;
import com.library.library.User.UserRepository;
import com.library.library.service.FavRequest;
import com.library.library.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books/favourite")
public class FavoriteController {


    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    public FavoriteController(FavoriteService favoriteService, UserRepository userRepository) {
        this.favoriteService = favoriteService;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity SetFavorite(@RequestBody FavRequest request , Principal principal) {
        String name = principal.getName();
        int  bookid = request.getBookId();
        favoriteService.addfavoriteBook(bookid, name);
        return ResponseEntity.ok("Book added to favorites");
    }

    @GetMapping
    public List GetFavorite( Principal principal) {
        String name = principal.getName();
        return  favoriteService.getFavorites(name);
    }

    @DeleteMapping("/{bookiId}")
    public ResponseEntity DeleteFavorite(@PathVariable int bookiId , Principal principal) {
        String name = principal.getName();
        favoriteService.removeFavorite(bookiId, name);
        return ResponseEntity.ok("Book deleted from favorites");
    }






}
