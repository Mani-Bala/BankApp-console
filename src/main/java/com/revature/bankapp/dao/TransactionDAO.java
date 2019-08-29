package com.revature.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.bankapp.model.Transaction;
import com.revature.bankapp.model.UserDetails;
import com.revature.bankapp.util.ConnectionUtil;

public class TransactionDAO implements ITransactionDAO {
	
	public List<Transaction> miniStatement(UserDetails details) throws SQLException {

		Connection con = ConnectionUtil.getConnection();
		String sql = "select * from Transactions where Account_id = ? order by Transaction_Date desc limit 5";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, details.getActNo());
		ResultSet rs = pst.executeQuery();
		List<Transaction> list = new ArrayList<Transaction>();
		while (rs.next()) {
			Transaction translist = toRow1(rs);
			list.add(translist);
		}
		
		return list;
	}
	
	private Transaction toRow1(ResultSet rs) throws SQLException {

		Timestamp datetime = rs.getTimestamp("Transaction_Date");
		Integer actno = rs.getInt("Account_id");
		String part = rs.getString("Particulars");
		int credit = rs.getInt("Credit");
		Integer debit = rs.getInt("Debit");
		long balance = rs.getLong("Balance");
		
		Transaction translist = new Transaction();
		translist.setDateTime(datetime.toLocalDateTime());
		translist.setActNo(actno);
		translist.setParticulars(part);
		translist.setCredit(credit);
		translist.setDebit(debit);
		translist.setBalance(balance);
	
		return translist;
	}
	
	public void insertHistory(long actNo, String part, int credit, int debit, long currentBal) throws SQLException {

		Connection con = ConnectionUtil.getConnection();
		
		try {
			String sql = "insert into Transactions(Account_id, Particulars, Credit, Debit, Balance) values ( ?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, actNo);
			pst.setNString(2, part);
			pst.setLong(3, credit);
			pst.setInt(4, debit);
			pst.setLong(5, currentBal);
			
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
