package ro.esolutions.phone_book_full_stack_application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.esolutions.phone_book_full_stack_application.dto.AppUserDto;
import ro.esolutions.phone_book_full_stack_application.entities.AppUser;
import ro.esolutions.phone_book_full_stack_application.repositories.AppUserRepository;

import java.util.Collections;

@Service
@Slf4j
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUserDto addUser(AppUserDto appUserLoginRequestDto) {

        if(!isEmailExists(appUserLoginRequestDto.getEmail())){

            AppUser user = new AppUser();

            BeanUtils.copyProperties(appUserLoginRequestDto,user);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            AppUserDto appUserDto = new AppUserDto();

            BeanUtils.copyProperties(appUserRepository.save(user),appUserDto);

            return appUserDto;
        }

        return null;
    }

    private boolean isEmailExists(String email) {
        return appUserRepository.findAppUserByEmail(email) != null;
    }


    public AppUser getUserByEmail(String email){
        return appUserRepository.findAppUserByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser verifyUserCredentials = appUserRepository.findAppUserByEmail(username);

        if(verifyUserCredentials == null){
            throw new UsernameNotFoundException("User with this "+username+" email not found !!!");
        }

        return User.withUsername(verifyUserCredentials.getEmail())
                .password(verifyUserCredentials.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

}
