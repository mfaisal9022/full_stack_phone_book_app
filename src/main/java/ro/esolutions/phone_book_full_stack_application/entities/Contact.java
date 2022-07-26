package ro.esolutions.phone_book_full_stack_application.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Contact {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private String role;//developer, project manager, architect, devOps, sales

    public Contact(String name, String email,String phoneNumber,String role){
        setEmail(email);
        setName(name);
        setRole(role);
        setPhoneNumber(phoneNumber);
    }
}
