package ro.esolutions.phone_book_full_stack_application.dto.responseDto;

import lombok.*;
import ro.esolutions.phone_book_full_stack_application.dto.AddressDto;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
    AddressDto address;
}
