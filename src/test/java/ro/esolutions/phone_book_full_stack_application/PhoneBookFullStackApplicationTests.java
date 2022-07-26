package ro.esolutions.phone_book_full_stack_application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import ro.esolutions.phone_book_full_stack_application.dto.UpdateContactDto;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneBookFullStackApplicationTests {

   @Autowired
   TestRestTemplate testRestTemplate;
    List<Contact> contactsList = new ArrayList<>();


    public void setContactsList(){
        contactsList.add(new Contact("ReactJs","react@gmail.com","+40750866075","Developer"));
        contactsList.add(new Contact("NodeJs","node@gmail.com","+40750866075","Sales Department"));
        contactsList.add(new Contact("Java","java@gmail.com","+40750866075","Project Manager"));
        contactsList.add(new Contact("Python","python@gmail.com","+40750866075","Architect"));
        contactsList.add(new Contact("NextJs","next@gmail.com","+40750866075","HR Manager"));
        contactsList.add(new Contact("DevOps","dev@gmail.com","+40750866075","DevOps Team"));
    }

   @Test
   public void integrationTestForCrudOperations(){
        setContactsList();
        int index = 2;


        //Add COntact
        HttpEntity<Contact> addContactEntity = new HttpEntity<Contact>(contactsList.get(index),null);
        ResponseEntity<Contact> addNewContactResponseEntity = testRestTemplate.postForEntity("/api/contacts/add",addContactEntity,Contact.class);

        Integer id = addNewContactResponseEntity.getBody().getId();

        System.out.println("Received : "+ addNewContactResponseEntity.getBody().toString());

        //get contact by id
        ResponseEntity<Contact> getContactByIdResponseEntity = testRestTemplate.getForEntity("/api/contacts/"+id,Contact.class);


        assertEquals(contactsList.get(index).getName(),getContactByIdResponseEntity.getBody().getName());
        assertEquals(contactsList.get(index).getPhoneNumber(),getContactByIdResponseEntity.getBody().getPhoneNumber());
        assertEquals(contactsList.get(index).getEmail(),getContactByIdResponseEntity.getBody().getEmail());


        // update contact

       UpdateContactDto updateContactDto = new UpdateContactDto(contactsList.get(index).getName() + " Update",contactsList.get(index).getEmail()+"Update@gmail.com",contactsList.get(index).getPhoneNumber(),contactsList.get(index).getRole());


       HttpEntity<UpdateContactDto> updateContactEntity = new HttpEntity<UpdateContactDto>(updateContactDto,null);
       testRestTemplate.put("/api/contacts/update/"+id,updateContactEntity,UpdateContactDto.class);


       ResponseEntity<Contact> getUpdatedContactById = testRestTemplate.getForEntity("/api/contacts/"+id,Contact.class);


       assertEquals(updateContactDto.getName(),getUpdatedContactById.getBody().getName());
       assertEquals(updateContactDto.getPhoneNumber(),getUpdatedContactById.getBody().getPhoneNumber());
       assertEquals(updateContactDto.getEmail(),getUpdatedContactById.getBody().getEmail());


       // delete contact
       testRestTemplate.delete("/api/contacts/delete/"+id,String.class);

       //get contact by id
       ResponseEntity<Contact> getDeleteContactById = testRestTemplate.getForEntity("/api/contacts/"+id,Contact.class);

       assertNull(getDeleteContactById.getBody());
   }
}
