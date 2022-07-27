package ro.esolutions.phone_book_full_stack_application.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.phone_book_full_stack_application.dto.AddContactDto;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;
import ro.esolutions.phone_book_full_stack_application.services.ContactService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/mvc")
public class MvcContactController {

    @Autowired
    private ContactService contactService;

    @Value("${msg.title}")
    private String title;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title",title);

        return "index";
    }

    @GetMapping(value = "/contacts")
    public String getUsers(Model model) {
        List<Contact> allContacts = contactService.getContacts();

        model.addAttribute("contacts",allContacts);

        return "contact-list";
    }

    @GetMapping(value = "/contacts/{contactId}")
    public String getContactById(Model model, @PathVariable int contactId) {
        Contact contact = null;
        try{
            contact = contactService.getContactById(contactId);

            if(contact == null){
                model.addAttribute("errorMessage","Contact with this "+contactId + " not found");
            }
            model.addAttribute("allowDelete", false);

        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }
        model.addAttribute("contact",contact);
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
    public String addContact(Model model, @ModelAttribute("contact") Contact contact) {
        try {
            AddContactDto newContact = contactService.saveContact(contact);
            return "redirect:/mvc/contacts/" + newContact.getId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", true);
            return "edit-contact";
        }
    }

    @GetMapping(value = {"/contacts/{contactId}/update"})
    public String showEditContact(Model model, @PathVariable int contactId) {
        Contact contact = null;
        try {
            contact = contactService.getContactById(contactId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("contact", contact);
        return "edit-contact";
    }

    @PostMapping(value = {"/contacts/{contactId}/update"})
    public String updateContact(Model model, @PathVariable int contactId, @ModelAttribute("contact") Contact contact) {
        System.out.println(contactId + " - "+ contact.toString());
        try {
            contact.setId(contactId);
            contactService.updateContact(contact);
            return "redirect:/mvc/contacts/" + contact.getId();
        } catch (Exception ex) {

            String errorMessage = ex.getMessage();

            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);

            return "edit-contact";
        }
    }

    @GetMapping(value = {"/contacts/{contactId}/delete"})
    public String showDeleteUser(Model model, @PathVariable int contactId) {

        Contact contact = null;
        try {
            contact = contactService.getContactById(contactId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("contact", contact);

        return "contact";
    }

    @PostMapping(value = {"/contacts/{contactId}/delete"})
    public String deleteContactById(
            Model model, @PathVariable int contactId) {
        try {
            contactService.deleteContact(contactId);
            return "redirect:/mvc/contacts";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        }
    }
}
