package com.revature.bankapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.bankapp.model.Transaction;
import com.revature.bankapp.model.UserDetails;

public interface ITransactionDAO {

	void insertHistory(long actNo, String part, int credit, int debit, long currentBal) throws SQLException;

	List<Transaction> miniStatement(UserDetails details) throws SQLException;

}