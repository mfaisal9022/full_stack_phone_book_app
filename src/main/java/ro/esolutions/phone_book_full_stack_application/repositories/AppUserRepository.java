package ro.esolutions.phone_book_full_stack_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.phone_book_full_stack_application.entities.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findAppUserByEmail(String email);
}
