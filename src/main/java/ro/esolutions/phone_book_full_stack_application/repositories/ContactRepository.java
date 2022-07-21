package ro.esolutions.phone_book_full_stack_application.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.phone_book_full_stack_application.entities.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Page<Contact> findByRoleIn(List<String> roles, Pageable pageable);
    Contact findByName(String contact);
}
