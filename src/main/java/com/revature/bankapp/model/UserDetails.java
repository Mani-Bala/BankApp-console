package com.revature.bankapp.model;

public class UserDetails {
	
	private String userName;
	private long actNo;
	private long mobNo;
	private String acType;
	private int pinNo;
	private long balance;
	private int active;

	@Override
	public String toString() {
		return "UserDetails [userName=" + userName + ", actNo=" + actNo + ", mobNo=" + mobNo + ", acType=" + acType
				+ ", pinNo=" + pinNo + ", balance=" + balance + ", active=" + active + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getActNo() {
		return actNo;
	}
	public void setActNo(long actNo) {
		this.actNo = actNo;
	}
	public long getMobNo() {
		return mobNo;
	}
	public void setMobNo(long mobNo) {
		this.mobNo = mobNo;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public int getPinNo() {
		return pinNo;
	}
	public void setPinNo(int pinNo) {
		this.pinNo = pinNo;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		if(balance<0) {
			throw new IllegalArgumentException("Invalid Balance.");
		}
		this.balance = balance;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		if(active==1||active==0) {
			this.active = active;
		}else {
			throw new IllegalArgumentException("Invalid active Status.");
		}
	}
		
}
