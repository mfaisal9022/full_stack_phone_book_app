package ro.esolutions.phone_book_full_stack_application.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.phone_book_full_stack_application.dto.AddContactDto;
import ro.esolutions.phone_book_full_stack_application.dto.UpdateContactDto;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;
import ro.esolutions.phone_book_full_stack_application.services.ContactService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/contacts")
@Slf4j
public class RestApiContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/all/page/{pageNumber}/{pageSize}")
    public Page<Contact> getAllContacts(@PathVariable int pageNumber, @PathVariable int pageSize) {

        return contactService.findAllContactsByPageNumber(pageNumber,pageSize);
    }

    @PostMapping(value = "/filterBy/Roles/{pageNumber}/{pageSize}")
    public Page<Contact> filterByRoles(@RequestBody List<String> roles, @PathVariable int pageNumber, @PathVariable int pageSize){

        log.info("ROles are : "+ roles.toString());
        log.info("Page Number : "+ pageNumber);
        log.info("Page Size : "+ pageSize);

        return contactService.filterByRoles(roles,pageNumber,pageSize);
    }

    @GetMapping(value = "/{contactId}")
    public Contact getContactById(@PathVariable int contactId) {
        log.info("Received a Get contact by id request with the contact id : "+ contactId);
        return contactService.getContactById(contactId);
    }

    @PostMapping(value = "/add")
    public AddContactDto addContact(@RequestBody Contact contact) {
        log.info("Receive a Post Request with the following JSON Data : "+ contact.toString());
        return contactService.saveContact(contact);
    }

    @PostMapping(value = "/addAllContacts")
    public List<Contact> addContact(@RequestBody List<Contact> contact) {
        log.info("Receive a Post Request with the following JSON Data : "+ contact.toString());
        return contactService.saveContacts(contact);
    }

    @PutMapping(value = {"/update"})
    public UpdateContactDto updateContact(@RequestBody Contact contact) {
        log.info("Receive a PUt/Update Request with the following JSON Data : "+ contact.toString());
        return contactService.updateContact(contact);
    }

    @DeleteMapping(value = {"/delete/{contactId}"})
    public String deleteContactById(@PathVariable int contactId) {
        log.info("Receive a Delete Request with the following JSON Data : "+ contactId);
        return contactService.deleteContact(contactId);
    }
}
