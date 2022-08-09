package ro.esolutions.phone_book_full_stack_application.dto.responseDto;

import lombok.*;
import ro.esolutions.phone_book_full_stack_application.dto.AddressDto;
import ro.esolutions.phone_book_full_stack_application.dto.ContactDto;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContactAddressResponseDto {
    private ContactDto contact;
    private List<AddressDto> addresses;
}
