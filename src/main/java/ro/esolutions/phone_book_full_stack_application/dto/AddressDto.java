package ro.esolutions.phone_book_full_stack_application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {
    private int id;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
}
