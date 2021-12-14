package com.ebanx.api.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.api.test.model.Account;
import com.ebanx.api.test.model.AccountEvent;
import com.ebanx.api.test.model.Response;
import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping
	public ResponseEntity<Response> handler(@RequestBody AccountEvent accountEvent) {
		Response response = new Response();
		
		if ("deposit".equals(accountEvent.getType())) {
			int id = Integer.valueOf(accountEvent.getDestination());
			double amount = Double.valueOf(accountEvent.getAmount());
			Account account = accountRepository.findById(id);
			if (account == null) {
				account = new Account();
				account.setAccount_id(id);
				account.setBalance(amount);

			} else {
				double newBalance = account.getBalance() + amount;
				account.setBalance(newBalance);
			}
			accountRepository.save(account);
			response.setHttpStatus(HttpStatus.CREATED.value());
			response.setData(account);
			
		}
		
		if ("withdraw".equals(accountEvent.getType())) {
			int id = Integer.valueOf(accountEvent.getOrigin());
			double amount = Double.valueOf(accountEvent.getAmount());
			Account account = accountRepository.findById(id);
			if (account == null) {
				response.setHttpStatus(HttpStatus.NOT_FOUND.value());
				response.setData(0);
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);

			} else {
				if (account.getBalance() > amount ) {
					double newBalance= account.getBalance() - amount; 
					account.setBalance(newBalance);
				}
			}
			accountRepository.save(account);
			response.setHttpStatus(HttpStatus.CREATED.value());
			response.setData(account);
			
		}
		
		if ("transfer".equals(accountEvent.getType())) {
			int idOrigin = Integer.valueOf(accountEvent.getOrigin());
			int idDestination = Integer.valueOf(accountEvent.getDestination());
			double amount = Double.valueOf(accountEvent.getAmount());
			Account origin = accountRepository.findById(idOrigin);
			Account destination = accountRepository.findById(idDestination);
			if (origin == null) {
				response.setHttpStatus(HttpStatus.NOT_FOUND.value());
				response.setData(0);
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);

			} else {
				if (origin.getBalance() >= amount ) {
					double newBalance= origin.getBalance() - amount; 
					origin.setBalance(newBalance);
					if (destination == null) {
						destination = new Account();
						destination.setAccount_id(idDestination);
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
			
			response.setHttpStatus(HttpStatus.CREATED.value());
			response.setData(list);
			
		}
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);

	}

}
