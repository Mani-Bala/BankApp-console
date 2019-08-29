package com.revature.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.revature.bankapp.model.UserDetails;
import com.revature.bankapp.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	public void fundTransfer(int toActno, int amt, long balance, UserDetails details) throws Exception {

		Connection con = ConnectionUtil.getConnection();

		String sql = "select Name, ActNo, MobNo, AcType, PinNo, Balance, Active from bankdb where ActNo = ? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, toActno);
		ResultSet rs = pst.executeQuery();
		UserDetails toUser = null;

		if (rs.next()) {
			toUser = toRow(rs);
		}

		if (toUser.getActive() == 1) {
			long userBal = toUser.getBalance();
			long userCurBal = amt + userBal;

			String part = "Fund recieved from " + details.getActNo();
			int debit = 0;
			try {
				updateBalance(userCurBal, toUser);

				ITransactionDAO transdao = new TransactionDAO();
				transdao.insertHistory(toUser.getActNo(), part, amt, debit, userCurBal);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("This account is Inactive.. You Cannot able to Transfer. ");
		}

		long currentBal = balance - amt;
		String part = "Fund transfer to " + toUser.getActNo();
		int credit = 0;

		try {
			updateBalance(currentBal, details);

			ITransactionDAO transdao = new TransactionDAO();
			transdao.insertHistory(details.getActNo(), part, credit, amt, currentBal);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public long balanceEnq(long acno) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "select Balance from bankdb where ActNo = ? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, acno);
		ResultSet rs = pst.executeQuery();
		long bal = 0l;
		if (rs.next()) {
			bal = rs.getInt("Balance");
		}
		return bal;
	}

	public void activeStatus(long actno, int status) throws SQLException {

		Connection con = ConnectionUtil.getConnection();

		String sql = "update bankdb set Active = ? where ActNo = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, status);
		pst.setLong(2, actno);

		int rows = pst.executeUpdate();
		System.out.println("No of rows updated:" + rows);

		if (status == 0)
			System.out.println("\nNow your account(" + actno + ") is Deactivated.");
		else
			System.out.println("\nNow your account(" + actno + ") is Activated.");

	}

	public UserDetails findByActNo(int actno, int pin) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "select Name, ActNo, MobNo, AcType, PinNo, Balance, Active from bankdb where ActNo = ? and PinNo = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, actno);
		pst.setInt(2, pin);
		ResultSet rs = pst.executeQuery();
		UserDetails details = null;

		if (rs.next()) {
			details = toRow(rs);
		}
		return details;
	}

	private UserDetails toRow(ResultSet rs) throws SQLException {

		String name = rs.getString("Name");
		Integer actno = rs.getInt("ActNo");
		Long mobno = rs.getLong("MobNo");
		String actype = rs.getString("AcType");
		Integer pinno = rs.getInt("PinNo");
		Integer balance = rs.getInt("Balance");
		Integer active = rs.getInt("Active");
		UserDetails user = new UserDetails();
		user.setUserName(name);
		user.setActNo(actno);
		user.setMobNo(mobno);
		user.setAcType(actype);
		user.setPinNo(pinno);
		user.setBalance(balance);
		user.setActive(active);
		return user;
	}

	public void updateBalance(long currentBal, UserDetails info) throws RuntimeException, SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "update bankdb set Balance = ? where ActNo = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setLong(1, currentBal);
		pst.setLong(2, info.getActNo());

		int rows = pst.executeUpdate();
		System.out.println("No of rows updated:" + rows);
	}

}
