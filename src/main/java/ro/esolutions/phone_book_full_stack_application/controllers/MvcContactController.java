package ro.esolutions.phone_book_full_stack_application.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.phone_book_full_stack_application.dto.AddressDto;
import ro.esolutions.phone_book_full_stack_application.dto.AppUserDto;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.AddressRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.ContactRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.AddressResponseDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.ContactAddressResponseDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.ContactResponseDto;
import ro.esolutions.phone_book_full_stack_application.entities.Address;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;
import ro.esolutions.phone_book_full_stack_application.services.AddressService;
import ro.esolutions.phone_book_full_stack_application.services.AppUserService;
import ro.esolutions.phone_book_full_stack_application.services.ContactService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/mvc")
public class MvcContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressService addressService;


    @Autowired
    private AppUserService appUserService;

    @Value("${msg.title}")
    private String title;

    @GetMapping(value = "/login")
    public String index(Model model) {
        model.addAttribute("title",title);
        return "index";
    }


    @PostMapping("/signup")
    public String signUp(Model model, AppUserDto appUserDto){

        model.addAttribute("userDetails",appUserService.addUser(appUserDto));
        return "signup";
    }
    @GetMapping("/signup")
    public String signUp(Model model){

        return "signup";
    }

    @GetMapping(value = "/contacts")
    public String getUsers(Model model) {
        List<ContactAddressResponseDto> allContacts = contactService.getAllContacts();

        model.addAttribute("contacts",allContacts.stream().map(ContactAddressResponseDto::getContact).toList());

        return "contact-list";
    }

    @GetMapping(value = "/contacts/{contactId}")
    public String getContactById(Model model, @PathVariable int contactId) {
        ContactAddressResponseDto contact = null;
        try{
            contact = contactService.getContactById(contactId);

            if(contact == null){
                model.addAttribute("errorMessage","Contact with this "+contactId + " not found");
            }

            model.addAttribute("allowDelete", false);

        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }

        assert contact != null;
        model.addAttribute("contact",contact.getContact());

        model.addAttribute("addressList",contact.getAddresses());
        return "contact";
    }

    @GetMapping(value = {"/contacts/add"})
    public String showAddContact(Model model) {

        Contact contact = new Contact();

        model.addAttribute("add", true);

        model.addAttribute("contact", contact);

        return "edit-contact";
    }

    @PostMapping(value = "/contacts/add")
    public String addContact(Model model, ContactRequestDto contactRequestDto) {
        try {

            ContactResponseDto newContact = contactService.saveContact(contactRequestDto);

            return "redirect:/mvc/contacts/" + newContact.getId();

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "edit-contact";
        }
    }

    @PostMapping(value = {"/contacts/{contactId}/update"})
    public String updateContact(Model model, @PathVariable int contactId,ContactRequestDto contactRequestDto) {

        try {
            contactRequestDto.setId(contactId);

            contactService.updateContact(contactRequestDto);

            return "redirect:/mvc/contacts/" + contactRequestDto.getId();

        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);

            return "edit-contact";
        }
    }

    @GetMapping(value = {"/contacts/{contactId}/update"})
    public String showEditContact(Model model, @PathVariable int contactId) {
        ContactAddressResponseDto contact = null;
        try {
            contact = contactService.getContactById(contactId);

        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);

        assert contact != null;
        model.addAttribute("contact", contact.getContact());

        model.addAttribute("addressList",contact.getAddresses());

        return "edit-contact";
    }



    @GetMapping(value = {"/contacts/{contactId}/delete"})
    public String showDeleteUser(Model model, @PathVariable int contactId) {

        ContactAddressResponseDto contact = null;

        try {
            contact = contactService.getContactById(contactId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);

        assert contact != null;
        model.addAttribute("contact", contact.getContact());
        model.addAttribute("addressList",contact.getAddresses());

        return "contact";
    }

    @PostMapping(value = {"/contacts/{contactId}/delete"})
    public String deleteContactById(Model model, @PathVariable int contactId) {
        try {

            contactService.deleteContact(contactId);

            return "redirect:/mvc/contacts";

        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        }
    }



    @GetMapping(value = {"/contacts/addAddress"})
    public String showAddAddress(Model model) {

        Address address = new Address();

        model.addAttribute("addAddress", true);

        model.addAttribute("address", address);

        List<ContactAddressResponseDto> allContacts = contactService.getAllContacts();

        model.addAttribute("allContacts",allContacts.stream().map(ContactAddressResponseDto::getContact).toList());

        return "address";
    }

    @PostMapping(value = {"/contacts/{id}/addAddress"})
    public String showAddAddress(Model model,@PathVariable int id,AddressDto addressDto) {

        Address address = new Address();

        model.addAttribute("addAddress", true);

        model.addAttribute("address", contactService.createNewAddressForaContact(id,addressDto));

        return  "redirect:/mvc/contacts/"+id;
    }
    @GetMapping(value = "/contacts/{id}/addresses")
    public String getAllAddressesOfaContact(Model model,@PathVariable int id){

        List<AddressDto> addressDtoList = contactService.getAllAddressListFromaContact(id);

        model.addAttribute("addressList",addressDtoList);

        return "contact";
    }

    @PostMapping(value = {"/contacts/{addressId}/updateAddress"})
    public String updateAddress(Model model, @PathVariable int addressId, AddressRequestDto addressRequestDto) {

        try {

            addressService.updateAddressById(addressId,addressRequestDto);

            return "redirect:/mvc/contacts";

        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("addAddress",false);

            return "edit-contact";
        }
    }

    @GetMapping(value = {"/contacts/{addressId}/updateAddress"})
    public String updateAddress(Model model, @PathVariable int addressId) {
        AddressResponseDto address = null;
        try {
            address = addressService.getAddressById(addressId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }

        model.addAttribute("addAddress",false);

        assert address != null;
        model.addAttribute("address", address.getAddress());

        return "address";
    }


    @GetMapping(value = {"/contacts/{addressId}/deleteAddress"})
    public String deleteAddress(Model model, @PathVariable int addressId) {
        try {

            addressService.deleteAddressById(addressId);

            return "redirect:/mvc/contacts";

        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("addAddress",false);

            return "edit-contact";
        }
    }
}
