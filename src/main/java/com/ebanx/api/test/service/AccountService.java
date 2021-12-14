package com.ebanx.api.test.service;

import com.ebanx.api.test.model.Account;

public interface AccountService {
	
	public void deposit (Account account,double amount);
	
	public void withdraw (Account account,double amount);
		
	public void transfer (Account origin,Account destination, double amount);

}
