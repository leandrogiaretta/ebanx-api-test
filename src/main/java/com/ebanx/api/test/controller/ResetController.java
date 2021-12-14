package com.ebanx.api.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebanx.api.test.model.Response;
import com.ebanx.api.test.repository.AccountRepository;

@RestController
@RequestMapping(value = "/reset")
public class ResetController {

	@Autowired
	AccountRepository accountRepository;

	@GetMapping
	public ResponseEntity<Response> reset() {
		
		accountRepository.deleteAll();
		Response response = new Response();
		response.setHttpStatus(HttpStatus.OK.value());
		response.setData("OK");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
