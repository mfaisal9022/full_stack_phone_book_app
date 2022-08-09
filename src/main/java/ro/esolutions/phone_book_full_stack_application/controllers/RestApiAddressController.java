package ro.esolutions.phone_book_full_stack_application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.AddressRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.AddressResponseDto;
import ro.esolutions.phone_book_full_stack_application.services.AddressService;

@RestController
@RequestMapping(value = "/api")
public class RestApiAddressController {

    @Autowired
    private AddressService addressService;

    @DeleteMapping("/address/{id}/delete")
    private ResponseEntity<HttpStatus> deleteAddressById(@PathVariable int id){

        return new ResponseEntity<>(addressService.deleteAddressById(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/contacts/{id}/delete")
    private ResponseEntity<HttpStatus> deleteCommentById(@PathVariable int id){
        addressService.deleteAddressById(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("address/{id}")
    private ResponseEntity<AddressResponseDto> getAddressById(@PathVariable int id){

        return new ResponseEntity<>(addressService.getAddressById(id), HttpStatus.OK);
    }

    @PutMapping("address/{id}/update")
    private ResponseEntity<AddressResponseDto> updateAddressById(@PathVariable int id,@RequestBody AddressRequestDto address){

        return new ResponseEntity<>(addressService.updateAddressById(id,address),HttpStatus.OK);
    }

    @DeleteMapping(value = "/contacts/{id}/deleteAddresses")
    public ResponseEntity<HttpStatus> deleteAllAddressOfaContact(@PathVariable int id){

        addressService.deleteAllAddressOfaContact(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
