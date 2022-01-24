package com.erwan.human.dao;

import com.erwan.human.domaine.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findOneByUsername(String username);
}
