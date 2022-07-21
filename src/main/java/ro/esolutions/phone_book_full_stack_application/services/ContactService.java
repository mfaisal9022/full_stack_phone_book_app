package ro.esolutions.phone_book_full_stack_application.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.esolutions.phone_book_full_stack_application.dto.AddContactDto;
import ro.esolutions.phone_book_full_stack_application.dto.UpdateContactDto;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;
import ro.esolutions.phone_book_full_stack_application.repositories.ContactRepository;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;


    public AddContactDto saveContact(Contact contact) {
        AddContactDto addContactDto = new AddContactDto();
        BeanUtils.copyProperties(repository.save(contact),addContactDto);
        return addContactDto;
    }

    public List<Contact> saveContacts(List<Contact> contact) {
        return repository.saveAll(contact);
    }

    public List<Contact> getContacts() {
        return repository.findAll();
    }

    public Contact getContactById(int id) {
        return repository.findById(id).orElse(null);
    }


    public String deleteContact(int id) {
        repository.deleteById(id);
        return "Contact Removed having Id : " + id;
    }

    public Page<Contact> findAllContactsByPageNumber(int pageNumber,int pageSize){
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);

        return repository.findAll(pageable);
    }

    public Page<Contact> filterByRoles(List<String> roles,int pageNumber,int PageSize){
        System.out.println("Received Collection is : "+ roles);
        Pageable pageable = PageRequest.of(pageNumber-1,PageSize);

        return repository.findByRoleIn(roles,pageable);
    }

    public UpdateContactDto updateContact(Contact contact) {

        Contact existingContact = repository.findById(contact.getId()).orElse(null);
        if(existingContact != null){
            existingContact.setName(contact.getName());
            existingContact.setEmail(contact.getEmail());
            existingContact.setPhoneNumber(contact.getPhoneNumber());
            existingContact.setRole(contact.getRole());
            UpdateContactDto updateContactDto = new UpdateContactDto();

            BeanUtils.copyProperties(repository.save(existingContact),updateContactDto);

            return updateContactDto;
        }
        return null;
    }

}
