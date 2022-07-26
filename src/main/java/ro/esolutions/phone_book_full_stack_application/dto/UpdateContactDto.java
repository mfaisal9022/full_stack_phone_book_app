package ro.esolutions.phone_book_full_stack_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
