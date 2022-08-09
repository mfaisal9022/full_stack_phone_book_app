package ro.esolutions.phone_book_full_stack_application.services;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.esolutions.phone_book_full_stack_application.dto.AddressDto;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.AddressRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.AddressResponseDto;
import ro.esolutions.phone_book_full_stack_application.entities.Address;
import ro.esolutions.phone_book_full_stack_application.repositories.AddressRepository;
import ro.esolutions.phone_book_full_stack_application.repositories.ContactRepository;

@Service
@Slf4j
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    public AddressResponseDto getAddressById(int id){

        Address address = addressRepository.findById(id).orElseThrow(()-> new OpenApiResourceNotFoundException("Address with "+id+" not found !!!"));

        AddressResponseDto addressDto = new AddressResponseDto(new AddressDto());

        BeanUtils.copyProperties(address,addressDto.getAddress());

        return addressDto;
    }

    public boolean deleteAddressById(int id){

        if(addressRepository.existsById(id)){

            addressRepository.deleteById(id);

            return true;
        }
        return false;
    }

    public AddressResponseDto updateAddressById(int id, AddressRequestDto addressToUpdate){

        Address currentAddress = addressRepository.findById(id).orElseThrow(()-> new OpenApiResourceNotFoundException("Address Id "+id+" not found "));

        BeanUtils.copyProperties(addressToUpdate,currentAddress);

        addressRepository.save(currentAddress);

        AddressResponseDto addressResponseDto = new AddressResponseDto(new AddressDto());

        BeanUtils.copyProperties(currentAddress,addressResponseDto.getAddress());

        return addressResponseDto;
    }

    public void deleteAllAddressOfaContact(int id){
        contactRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Contact with "+ id+" not found !!!"));

        addressRepository.deleteByContactId(id);
    }
}
