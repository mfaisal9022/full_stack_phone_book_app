package ro.esolutions.phone_book_full_stack_application.dto.requestDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressRequestDto {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
}
