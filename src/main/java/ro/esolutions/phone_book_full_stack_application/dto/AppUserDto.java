package ro.esolutions.phone_book_full_stack_application.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AppUserDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

}
