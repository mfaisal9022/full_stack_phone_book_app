package ro.esolutions.phone_book_full_stack_application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import ro.esolutions.phone_book_full_stack_application.dto.requestDto.ContactRequestDto;
import ro.esolutions.phone_book_full_stack_application.dto.responseDto.ContactResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneBookFullStackApplicationTests {

   /*@Autowired
   TestRestTemplate testRestTemplate;
    List<ContactRequestDto> contactsList = new ArrayList<>();


    public void setContactsList(){

        contactsList.add(new ContactRequestDto(1,"NodeJs","node@gmail.com","0750866075"));
    }*/

   @Test
   public void integrationTestForCrudOperations(){
        /*setContactsList();
        int index = 0;


        //Add COntact
        HttpEntity<ContactRequestDto> addContactEntity = new HttpEntity<ContactRequestDto>(contactsList.get(index),null);

        ResponseEntity<ContactResponseDto> addNewContactResponseEntity = testRestTemplate.postForEntity("/api/contacts/add",addContactEntity,ContactResponseDto.class);

       System.out.println(addNewContactResponseEntity.getBody().getName());
        int id = Objects.requireNonNull(addNewContactResponseEntity.getBody()).getId();

        //get contact by id
        ResponseEntity<ContactResponseDto> getContactByIdResponseEntity = testRestTemplate.getForEntity("/api/contacts/"+id,ContactResponseDto.class);


        assertEquals(contactsList.get(index).getName(), Objects.requireNonNull(getContactByIdResponseEntity.getBody()).getName());
        assertEquals(contactsList.get(index).getPhoneNumber(),getContactByIdResponseEntity.getBody().getPhoneNumber());
        assertEquals(contactsList.get(index).getEmail(),getContactByIdResponseEntity.getBody().getEmail());


        // update contact
       ContactRequestDto updateContactRequestDto = new ContactRequestDto(contactsList.get(index).getId(),contactsList.get(index).getName() + " Update","Update@gmail.com","+4"+contactsList.get(index).getPhoneNumber());

       HttpEntity<ContactRequestDto> updateContactEntity = new HttpEntity<ContactRequestDto>(updateContactRequestDto,null);

       testRestTemplate.put("/api/contacts/update/"+id,updateContactEntity,ContactRequestDto.class);


       ResponseEntity<ContactResponseDto> getUpdatedContactById = testRestTemplate.getForEntity("/api/contacts/"+id,ContactResponseDto.class);




       assertEquals(updateContactRequestDto.getName(), Objects.requireNonNull(getUpdatedContactById.getBody()).getName());
       assertEquals(updateContactRequestDto.getPhoneNumber(),getUpdatedContactById.getBody().getPhoneNumber());
       assertEquals(updateContactRequestDto.getEmail(),getUpdatedContactById.getBody().getEmail());


       // delete contact
       testRestTemplate.delete("/api/contacts/delete/"+id,String.class);

       //get contact by id
       ResponseEntity<ContactResponseDto> getDeleteContactById = testRestTemplate.getForEntity("/api/contacts/"+id,ContactResponseDto.class);

       assertNull(getDeleteContactById.getBody());*/
   }
}
