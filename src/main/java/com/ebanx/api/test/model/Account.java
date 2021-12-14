package com.ebanx.api.test.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
	
	@Id
	private Integer account_id;
	
	private double balance;
	
	
	public Integer getAccount_id() {
		return account_id;
	}
	
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(account_id, other.account_id);
	}

	
	

}
