package com.ebanx.api.test.service;

import com.ebanx.api.test.model.Account;

public class AccountServiceImpl implements AccountService {

	@Override
	public void deposit(Account account, double amount) {
		double newBalance= account.getBalance() + amount; 
		account.setBalance(newBalance);
		
	}

	@Override
	public void withdraw(Account account, double amount) {
		if (account.getBalance() > amount ) {
			double newBalance= account.getBalance() - amount; 
			account.setBalance(newBalance);
		}
	}

	@Override
	public void transfer(Account origin, Account destination, double amount) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
