package com.revature.bankapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.bankapp.controller.BankOps;
import com.revature.bankapp.util.ConnectionUtil;

public class Register {

	static Scanner sc =new Scanner(System.in);
	public static void register(String name, long mobno, long actno, String actype, int pin) throws Exception {
		
		Connection con = ConnectionUtil.getConnection();
		
		try {
			String sql = "insert into bankdb(Name, ActNo, MobNo, AcType, PinNo) values ( ?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setLong(2, actno);
			pst.setLong(3, mobno);
			pst.setString(4, actype);
			pst.setInt(5, pin);
			
			pst.executeUpdate();
			System.out.println("\nRegistered Successfully. ");
			BankOps.welcomePage();
		} catch (SQLException e) {
			throw new RuntimeException("This account is already existing. Please give valid new data's." );
		}
		
	}
}
