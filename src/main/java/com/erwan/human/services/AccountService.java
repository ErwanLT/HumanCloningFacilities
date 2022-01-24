package com.erwan.human.services;

import com.erwan.human.dao.AccountRepository;
import com.erwan.human.domaine.Account;
import com.erwan.human.reference.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void initialize(){
        if(accountRepository.findOneByUsername("kamino") == null){
            save(new Account("kamino", "kamino", AccountRole.KAMINOAIN.name()));
        }
        if(accountRepository.findOneByUsername("palpatine") == null){
            save(new Account("palpatine", "palpatine", AccountRole.EMPEROR.name()));
        }
    }

    @Transactional
    private Account save(Account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepository.save(user);
    }
}
