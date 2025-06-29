package com.library.library.User;


import com.library.library.Book.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data   //provide getter and setter methods and equals()
@Builder//This annotation allows for the creation of objects using the builder design pattern.
//Like this
//User user = User.builder()
//        .name("John")
//        .email("john@example.com")
//        .password("securePassword")
//        .role(Role.USER)
//        .build();
@NoArgsConstructor//Purpose: Generates a no-argument constructor for the class.
//Use Case: Useful for frameworks that require a default constructor (e.g., JPA, Hibernate).
@AllArgsConstructor//Purpose: Generates a constructor that takes parameters for all fields in the class.
@Entity//Specifies that the class is a JPA entity, meaning it represents a table in the database.
@Table(name = "_user_") //creat a database table for the class
@Getter
@Setter
public class User implements UserDetails {//makes u r user a spring user

    @Id//use the id as the key id for the class
    @GeneratedValue//auto generate the int id
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated//use is when you are working with enum class
    private Role role;

//  when working wiht one to many u should creat a join table for best practise
//    @joincolumn is used when u want to refrencce a pk in a diff table to use it as fk
//    joinColumns = ...... is setting up the pk of the join table
//    inverseJoinColumns...... same but for the fk in the join table
    @OneToMany()
    @JoinTable(name = "user_favorites" ,
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "Book_id" ))
    private List<Book> favorites;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));//return which role does the user have
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
