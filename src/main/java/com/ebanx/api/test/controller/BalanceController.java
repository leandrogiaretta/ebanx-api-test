package com.ebanx.api.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.api.test.model.Account;
import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/balance")
public class BalanceController {

	@Autowired
	AccountRepository accountRepository;

	@GetMapping
	public ResponseEntity getBalance(@RequestParam(value = "account_id") int account_id) {

		Account account = accountRepository.findById(account_id);
		if (account == null) {
			// response.setHttpStatus(HttpStatus.NOT_FOUND.value());
			// response.setData(0);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
		} else {
			// response.setHttpStatus(HttpStatus.OK.value());
			// response.setData(account);
			return ResponseEntity.status(HttpStatus.OK).body(account.getBalance());
		}

	}

}
