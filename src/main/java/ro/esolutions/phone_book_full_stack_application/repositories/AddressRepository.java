package ro.esolutions.phone_book_full_stack_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.phone_book_full_stack_application.entities.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    List<Address> findByContactId(Integer id);
    @Transactional
    void deleteByContactId(Integer id);

}
