package com.erwan.human.services;

import com.erwan.human.config.JwtTokenProvider;
import com.erwan.human.dao.UserRepository;
import com.erwan.human.domaine.AppUser;
import com.erwan.human.domaine.AppUserRole;
import com.erwan.human.domaine.authentification.AuthResponse;
import com.erwan.human.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostConstruct
    private void init(){
        if(!userRepository.existsByUsername("palpatine")){
            var palpatine = new AppUser();
            palpatine.setUsername("palpatine");
            palpatine.setEmail("iamthesenate@senate.com");
            palpatine.setPassword("palpatine");
            palpatine.setAppUserRoles(List.of(AppUserRole.ROLE_EMPEROR));
            saveUser(palpatine);
        }
        if(!userRepository.existsByUsername("kamino")){
            var kaminoian = new AppUser();
            kaminoian.setUsername("kaminoian");
            kaminoian.setEmail("wearethecloners@tipoca.com");
            kaminoian.setPassword("kamino");
            kaminoian.setAppUserRoles(List.of(AppUserRole.ROLE_KAMINOAIN));
            saveUser(kaminoian);
        }
    }

    private void saveUser(AppUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public AuthResponse signin(String username, String password) {
        try {
            var userStored = userRepository.findByUsername(username);

            if(!Objects.isNull(userStored)){
                var storedPassword = userStored.getPassword();
                var isPasswordOk = BCrypt.checkpw(password, storedPassword);

                if(isPasswordOk){
                    var token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
                    return new AuthResponse(username, token);
                } else {
                    throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } else {
                throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
