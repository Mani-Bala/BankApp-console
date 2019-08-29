package com.revature.bankapp.model;

import java.time.LocalDateTime;

public class Transaction {
	
	private LocalDateTime dateTime;
	private int actNo;
	private String particulars;
	private int credit;
	private int debit;
	private long balance;
	
	
	@Override
	public String toString() {
		return "TransactionList [dateTime=" + dateTime + ", actNo=" + actNo + ", particulars=" + particulars
				+ ", credit=" + credit + ", debit=" + debit + ", balance=" + balance + "]";
	}
	
	public int getActNo() {
		return actNo;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setActNo(int actNo) {
		this.actNo = actNo;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getDebit() {
		return debit;
	}
	public void setDebit(int debit) {
		this.debit = debit;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

}
