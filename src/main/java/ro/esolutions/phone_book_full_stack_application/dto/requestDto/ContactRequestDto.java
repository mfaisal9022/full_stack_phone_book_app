package ro.esolutions.phone_book_full_stack_application.dto.requestDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactRequestDto {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
}
