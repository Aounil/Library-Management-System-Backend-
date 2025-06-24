package com.library.library.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// UserRepository interface that provides database operations for the 'user' entity.
// It extends JpaRepository, which gives access to various CRUD and JPA-specific methods.
//So this file is responsible for database interaction
public interface UserRepository extends JpaRepository<User, Integer> {  // 'user' is the entity, and 'Integer' is the type for the ID (primary key).

    // Custom query method to find a user by their email address (username).
    // Spring Data JPA automatically implements this method based on the method name convention.
    // It returns an Optional<user> which means the user may or may not exist in the database.
    //Optional helps with null exceptions
    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);
    //public void deleteById(Integer id);


}
