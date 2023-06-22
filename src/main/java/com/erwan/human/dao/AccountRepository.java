package com.erwan.human.dao;

import com.erwan.human.domaine.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findOneByUsername(String username);

    Optional<Account> findByUsername(String username);
}
