package ro.esolutions.phone_book_full_stack_application.dto.requestDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUserLoginRequestDto {
    private String email;
    private String password;
}
