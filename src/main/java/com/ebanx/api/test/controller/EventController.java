package com.ebanx.api.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.ebanx.api.test.model.AccountResponse;
import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping
	public ResponseEntity handler(@RequestBody AccountEvent accountEvent) {
				
		if ("deposit".equals(accountEvent.getType())) {
			int id = Integer.valueOf(accountEvent.getDestination());
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
			//response.setHttpStatus(HttpStatus.CREATED.value());
			//response.setData(account);
			Map<String,AccountResponse> response = new HashMap<String, AccountResponse>();
			AccountResponse accountResponse = new AccountResponse();
			accountResponse.setId(account.getId().toString());
			accountResponse.setBalance(String.valueOf(account.getBalance()));
			response.put("destination", accountResponse);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
			
		}
		
		if ("withdraw".equals(accountEvent.getType())) {
			int id = Integer.valueOf(accountEvent.getOrigin());
			int amount = Integer.valueOf(accountEvent.getAmount());
			Account account = accountRepository.findById(id);
			if (account == null) {
				//response.setHttpStatus(HttpStatus.NOT_FOUND.value());
				//response.setData(0);
				//return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");

			} else {
				if (account.getBalance() > amount ) {
					int newBalance= account.getBalance() - amount;
					account.setBalance(newBalance);
				}
			}
			accountRepository.save(account);
			//response.setHttpStatus(HttpStatus.CREATED.value());
			//response.setData(account);
			Map<String,Account> response = new HashMap<String, Account>();
			response.put("origin", account);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}
		
		if ("transfer".equals(accountEvent.getType())) {
			int idOrigin = Integer.valueOf(accountEvent.getOrigin());
			int idDestination = Integer.valueOf(accountEvent.getDestination());
			int amount = Integer.valueOf(accountEvent.getAmount());
			Account origin = accountRepository.findById(idOrigin);
			Account destination = accountRepository.findById(idDestination);
			if (origin == null) {
				//response.setHttpStatus(HttpStatus.NOT_FOUND.value());
				//response.setData(0);
				//return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
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
			List<Account> list = new ArrayList<Account>();
			list.add(origin);
			list.add(destination);
			
			//response.setHttpStatus(HttpStatus.CREATED.value());
			//response.setData(list);
			Map<String,List<Account>>response = new HashMap<String, List<Account>>();
			response.put("destination",list);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}
		//return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		return null;

	}

}
