package com.ebanx.api.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/reset")
public class ResetController {

	@Autowired
	AccountRepository accountRepository;

	@PostMapping
	public String reset() {
		
		accountRepository.deleteAll();
		return HttpStatus.OK.name();
	}

}
