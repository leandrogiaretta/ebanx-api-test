package com.ebanx.api.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.api.test.model.Account;
import com.ebanx.api.test.model.AccountEvent;
import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping
	public ResponseEntity handler(@RequestBody AccountEvent accountEvent) {
				
		if ("deposit".equals(accountEvent.getType())) {
			String id = accountEvent.getDestination();
			int amount = Integer.valueOf(accountEvent.getAmount());
			Account account = accountRepository.findById(id);
			if (account == null) {
				account = new Account();
				account.setId(id);
				account.setBalance(amount);

			} else {
				int newBalance = account.getBalance() + amount;
				account.setBalance(newBalance);
			}
			accountRepository.save(account);
			Map<String,Account> response = new HashMap<String, Account>();
			response.put("destination", account);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
			
		}
		
		if ("withdraw".equals(accountEvent.getType())) {
			String id = accountEvent.getOrigin();
			int amount = Integer.valueOf(accountEvent.getAmount());
			Account account = accountRepository.findById(id);
			if (account == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");

			} else {
				if (account.getBalance() > amount ) {
					int newBalance= account.getBalance() - amount;
					account.setBalance(newBalance);
				}
			}
			accountRepository.save(account);
			Map<String,Account> response = new HashMap<String, Account>();
			response.put("origin", account);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}
		
		if ("transfer".equals(accountEvent.getType())) {
			String idOrigin = accountEvent.getOrigin();
			String idDestination = accountEvent.getDestination();
			int amount = Integer.valueOf(accountEvent.getAmount());
			Account origin = accountRepository.findById(idOrigin);
			Account destination = accountRepository.findById(idDestination);
			if (origin == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");

			} else {
				if (origin.getBalance() >= amount ) {
					int newBalance= origin.getBalance() - amount; 
					origin.setBalance(newBalance);
					if (destination == null) {
						destination = new Account();
						destination.setId(idDestination);
						destination.setBalance(amount);

					} else {
						newBalance = destination.getBalance() + amount;
						destination.setBalance(newBalance);
					}
					accountRepository.save(origin);
					accountRepository.save(destination);
					
				}
			}
			Map<String,Account>response = new HashMap<String,Account>();
			response.put("origin",origin);
			response.put("destination", destination);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Method Not Allowed");

	}

}
