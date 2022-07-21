package ro.esolutions.phone_book_full_stack_application.dto;

import lombok.Data;

@Data
public class UpdateContactDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
