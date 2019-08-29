package com.revature.bankapp;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.revature.bankapp.dao.ITransactionDAO;
import com.revature.bankapp.dao.IUserDAO;
import com.revature.bankapp.dao.TransactionDAO;
import com.revature.bankapp.dao.UserDAO;
import com.revature.bankapp.model.Transaction;
import com.revature.bankapp.model.UserDetails;

public class LoginAc {

	static Scanner sc =new Scanner(System.in);
	
	public static void login(int actno, int pin) throws Exception {
		
		UserDetails details = new UserDetails();
		
		IUserDAO userdao=new UserDAO();
		details=userdao.findByActNo(actno,pin);
		try {
			
			long ActNo=details.getActNo();
			int PinNo=details.getPinNo();
			
			if(actno==ActNo && pin==PinNo) {
				
				System.out.println("Log-in Succesfully. ");
				System.out.println("\nYour NAME: "+details.getUserName());
				System.out.println("Your Account Number: "+details.getActNo());
				System.out.println("Mobile Number:  "+details.getMobNo());
				
			}
			
		} catch (RuntimeException e) {
			throw new RuntimeException("Invalid Account number and Password. Enter valid one.");
		}	
		features(details);
		}
	
	
	public static void features(UserDetails details) throws Exception {
		System.out.println("\n=================================================================");
		System.out.println("1. Balance Enquiry | 2. Withdrawal     | 3. Deposit         | ");
		System.out.println("\n4. Fund Transfer   | 5. Mini Statement | 6. Active/Deactive | ");
		System.out.println("\n7. Logout          |");
		System.out.println("=================================================================");
		System.out.println("\nEnter Your Choice :");
		int val=sc.nextInt();
		IUserDAO userdao=new UserDAO();
		long balance=userdao.balanceEnq(details.getActNo());
		
		if(val>0 && val<=7) {
			switch (val) {
			case 1:
				IUserDAO userdao3=new UserDAO();
				long bal=userdao3.balanceEnq(details.getActNo());
				System.out.println("\nYour account balance is: " + bal);
				features(details );
				break;
			case 2:
				if(details.getActive()==1)
					withdrawal(balance,details);
				else
					System.out.println("\nThis Account is Inactive. You can't use the features, \nPlease check your status in your BANK.");
				
				features(details);
				break;
			case 3:
				if(details.getActive()==1)
					deposit(balance, details);
				else
					System.out.println("\nThis Account is Inactive. You can't use the features, \nPlease check your status in your BANK.");
				
				features(details);
				break;
			case 4:
				if(details.getActive()==1) {
					
					System.out.println("\nEnter To-Act No :");
					int toActno = sc.nextInt();
					System.out.println("Enter Amount :");
					int amt = sc.nextInt();
					
					if(toActno>10000000) {
						if(amt<balance) {
							IUserDAO userdao1=new UserDAO();
							userdao1.fundTransfer(toActno, amt, balance, details);
						}else
							System.out.println("\n*Insufficient Balance.. Please enter the correct amt ,\nWhich doesn't exceed your current Balance*");
					}else
						System.out.println("\n*Please enter the valid Account number*");
				}
				else
					System.out.println("\nThis Account is Inactive. You can't use the features, \nPlease check your status in your BANK.");
				
				features(details);
				break;
			case 5:
				
				ITransactionDAO transdao=new TransactionDAO();
				List<Transaction> list = transdao.miniStatement(details);
				for (Transaction translist : list) {
					System.out.println(translist);
				}
				features(details);
				break;
			case 6:
				System.out.println("\nMake your account Active(1)/Deactive(0); Change your status :");
				int status=sc.nextInt();
				IUserDAO userdao2=new UserDAO();
				userdao2.activeStatus(details.getActNo(), status);
				features(details);
			case 7:
				BankOps.welcomePage();
			}
		}else {
			System.out.println("Please enter valid choise between 1 and 7..");
			features(details);
			
		}
	}
	
	
	private static void deposit(long balance, UserDetails details) throws RuntimeException {
		System.out.println("Enter Your Amount: ");
		int amt=sc.nextInt();
		long currentBal=balance+amt;
		
		String part="UPI trainsaction";
		int debit=0;
		try {
			IUserDAO userdao1=new UserDAO();
			userdao1.updateBalance(currentBal, details);
			
			ITransactionDAO transdao1=new TransactionDAO();
			transdao1.insertHistory(details.getActNo(),part ,amt, debit, currentBal );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static void withdrawal(long balance, UserDetails details) throws RuntimeException {
		System.out.println("Enter Your Amount: ");
		int amt=sc.nextInt();
		long currentBal=balance-amt;
		
		String part="ATM CASH";
		int credit=0;
		if(details.getBalance()>amt) {
		try {
			IUserDAO userdao2=new UserDAO();
			userdao2.updateBalance(currentBal, details);
			
			ITransactionDAO transdao=new TransactionDAO();
			transdao.insertHistory(details.getActNo(),part ,credit, amt, currentBal );
		} catch (SQLException e) {
			e.printStackTrace();
		}}else {
			System.out.println("InSufficiant Balance..");
		}
	}

}
