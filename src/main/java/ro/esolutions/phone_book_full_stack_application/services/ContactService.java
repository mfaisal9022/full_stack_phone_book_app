package ro.esolutions.phone_book_full_stack_application.services;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.phone_book_full_stack_application.dto.AddressDto;
import ro.esolutions.phone_book_full_stack_application.dto.ContactDto;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.ContactRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.ContactAddressResponseDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.ContactResponseDto;
import ro.esolutions.phone_book_full_stack_application.entities.Address;
import ro.esolutions.phone_book_full_stack_application.entities.AppUser;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;
import ro.esolutions.phone_book_full_stack_application.repositories.AddressRepository;
import ro.esolutions.phone_book_full_stack_application.repositories.ContactRepository;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AppUserService appUserService;

    public ContactResponseDto saveContact(ContactRequestDto contactRequestDto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser appUser = appUserService.getUserByEmail(((UserDetails) principal).getUsername());

        Contact newContact = new Contact();

        BeanUtils.copyProperties(contactRequestDto,newContact);

        newContact.setAppUser(appUser);

        Contact savedContact = contactRepository.save(newContact);


        ContactResponseDto responseDto = new ContactResponseDto();

        BeanUtils.copyProperties(savedContact,responseDto);

        return responseDto;
    }

    public List<ContactResponseDto> saveContacts(List<ContactRequestDto> contactRequestDtoList) {

        List<ContactResponseDto> responseList = new ArrayList<>();

        for (ContactRequestDto dto : contactRequestDtoList) {

            responseList.add(saveContact(dto));

        }
        return responseList;
    }

    public List<ContactAddressResponseDto> getAllContacts() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUser appUser = appUserService.getUserByEmail(((UserDetails) principal).getUsername());

        List<ContactAddressResponseDto> dtos = new ArrayList<>();

        List<Contact> contacts = contactRepository.findAllByAppUserId(appUser.getId());

        contacts.forEach(contact -> {

            ContactAddressResponseDto dto = new ContactAddressResponseDto(new ContactDto(),new ArrayList<>());

            BeanUtils.copyProperties(contact, dto.getContact());

            List<Address> addressList = addressRepository.findByContactId(contact.getId());

            List<AddressDto> addressDtoList = new ArrayList<>();

            addressList.forEach(address -> {

                AddressDto addressDto = new AddressDto();

                BeanUtils.copyProperties(address,addressDto);

                addressDtoList.add(addressDto);

            });

            dto.setAddresses(addressDtoList);

            dtos.add(dto);
        });

        return dtos;
    }

    public ContactAddressResponseDto getContactById(int id) {

        Contact getContact = contactRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Contact with "+id+" not found !!!"));

        ContactAddressResponseDto contactResponseDto = new ContactAddressResponseDto(new ContactDto(),new ArrayList<>());

        BeanUtils.copyProperties(getContact,contactResponseDto.getContact());

        List<Address> getContactAddressList = addressRepository.findByContactId(id);

        List<AddressDto> addressDtoList = new ArrayList<>();

        getContactAddressList.forEach(address -> {

            AddressDto addressDto = new AddressDto();

            BeanUtils.copyProperties(address,addressDto);

            addressDtoList.add(addressDto);

        });

        contactResponseDto.setAddresses(addressDtoList);

        return contactResponseDto;
    }


    public String deleteContact(int id) {

        contactRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Contact with "+id+" not found !!!"));


        addressRepository.deleteByContactId(id);
        contactRepository.deleteById(id);
        return "Contact Removed having Id : " + id + " - ";
    }

    public Page<Contact> findAllContactsByPageNumber(int pageNumber,int pageSize){
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);

        return contactRepository.findAll(pageable);
    }

    public Page<Contact> filterByRoles(List<String> roles,int pageNumber,int PageSize){
        ;
        Pageable pageable = PageRequest.of(pageNumber-1,PageSize);

        return contactRepository.findByRoleIn(roles,pageable);
    }

    public ContactResponseDto updateContact(ContactRequestDto contact) {

        Contact existingContact = contactRepository.findById(contact.getId()).orElseThrow(() -> new OpenApiResourceNotFoundException("Contact with "+contact.getId()+" not found !!!"));

        BeanUtils.copyProperties(contact,existingContact);

        ContactResponseDto updateContactDto = new ContactResponseDto();

        BeanUtils.copyProperties(contactRepository.save(existingContact),updateContactDto);

        return updateContactDto;
    }


    public AddressDto createNewAddressForaContact(int id, AddressDto addressDto){
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Contact with "+id+" not found !!!"));

        Address address = new Address();

        BeanUtils.copyProperties(addressDto,address);

        address.setContact(contact);

        addressRepository.save(address);

        return addressDto;
    }

    public List<AddressDto> getAllAddressListFromaContact(int id){
        contactRepository.findById(id).orElseThrow(()-> new OpenApiResourceNotFoundException("Contact with "+id+" not found !!!"));

        List<AddressDto> addressDtoList = new ArrayList<>();

        List<Address> addressList = addressRepository.findByContactId(id);

        addressList.forEach(address -> {

            AddressDto addressDto = new AddressDto();

            BeanUtils.copyProperties(address,addressDto);

            addressDtoList.add(addressDto);
        });

        return addressDtoList;
    }

}
