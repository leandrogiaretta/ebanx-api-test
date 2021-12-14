package com.ebanx.api.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebanx.api.test.model.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
	
	Account findById(String id);

}
