package com.library.library.auth;

import com.library.library.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistreRequest {

    private String name;
    private String email;
    private String password;
    private Role role;

}
