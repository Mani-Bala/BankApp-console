package com.revature.bankapp.dao;

import java.sql.SQLException;

import com.revature.bankapp.model.UserDetails;

public interface IUserDAO {

	void fundTransfer(int toActno, int amt, long balance, UserDetails details) throws Exception;

	long balanceEnq(long acno) throws SQLException;

	void activeStatus(long actno, int status) throws SQLException;

	UserDetails findByActNo(int actno, int pin) throws SQLException;

	void updateBalance(long currentBal, UserDetails info) throws RuntimeException, SQLException;

}