package com.erwan.human.services;

import com.erwan.human.dao.AccountRepository;
import com.erwan.human.domaine.Account;
import com.erwan.human.domaine.authentification.AuthRequest;
import org.bouncycastle.its.asn1.HashAlgorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {

    private final AccountRepository accountRepository;

    public AuthentificationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account authenticate(AuthRequest request){
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + request.getUsername() + " not found."));

        var passwordToCheck = request.getPassword();
        var passwordStored = account.getPassword();
        var isPasswordValide = BCrypt.checkpw(passwordToCheck, passwordStored);

        if (isPasswordValide){
            return account;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
