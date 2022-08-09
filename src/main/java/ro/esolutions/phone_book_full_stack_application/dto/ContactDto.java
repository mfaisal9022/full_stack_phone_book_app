package ro.esolutions.phone_book_full_stack_application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDto {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
