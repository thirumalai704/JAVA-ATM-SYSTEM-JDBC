package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ATM  implements IATM{
	
	public Connection createConnection()
	{
		 Connection  connection= null ;
		
		 try {
			 String url = "jdbc:postgresql://localhost:5432/atmjdbc";
			 String username = "postgres";
			 String password = "root";
			 Class.forName("org.postgresql.Driver");
			 connection = DriverManager.getConnection(url,username,password);
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		return connection ;
	}
	
	
	Scanner scanner = new Scanner(System.in);
	
	public void createAccount()
	{
		System.out.println("ENTER ACCOUNT NUMBER");
		String accountNumber = scanner.next();
		System.out.println("ENTER NAME");
		String name = scanner.next();
		System.out.println("ENTER PIN");
		int pin = scanner.nextInt();
		
		try {
			Connection connection = createConnection();
			String query = "INSERT INTO atm VALUES (?,?,?,?)";
			PreparedStatement  preparedStatement = connection.prepareStatement(query);
			
			//set value to delimeter
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, pin);
			preparedStatement.setDouble(4, 0.0);
			
			int row = preparedStatement.executeUpdate(); // return int value --give number of rows affected
			if(row != 0)
			{
				System.out.println("ACCOUNT CREATION WAS SUCCESS..\nYOUR ACCOUNT NUMBER IS :"+ accountNumber);
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void depositAmount() {
		System.out.println("ENTER AMOUNT TO BE DEPOSITED");
		double amount  = scanner.nextDouble();
		System.out.println("ENTER ACCOUNT NUMBER");
		String accountNumber = scanner.next();
		System.out.println("ENTER PIN");
		int pinNumber = scanner.nextInt();
		try {
			Connection connection = createConnection();
	
			String query ="UPDATE atm SET balance = balance + ? WHERE account_number = ? AND pin = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1,amount );
			preparedStatement.setString(2,accountNumber);
			preparedStatement.setInt(3, pinNumber);
			int row = preparedStatement.executeUpdate();
			if(row != 0) {
				System.out.println("DEPOSITED UPDATED");
			}
			else {
				System.out.println("INVALID ACCOUNT NUMBER OR PIN");
			}
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void withdrawAmount() {
		System.out.println("PLEASE ENTER WITHDRAW AMOUNT");
		double amount = scanner.nextDouble();
		System.out.println("ENTER ACCOUNT NUMBER");
		String accountNumber = scanner.next();
		System.out.println("ENTER PIN NUMBER");
		int pinNumber = scanner.nextInt();
		
		try {
			Connection connection = createConnection();
			//fetch balance
			String query ="SELECT balance FROM atm  WHERE account_number = ? AND pin = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);	
			preparedStatement.setString(1,accountNumber);
			preparedStatement.setInt(2, pinNumber);
			ResultSet  resultset = preparedStatement.executeQuery();
			
			//verify balance is present or not
			if(resultset.next()) {
				double currentBalance = resultset.getDouble("balance");
				
				if(currentBalance > amount)
				{
					//do operation
					
					String withdrawQuery = "UPDATE atm SET balance = balance -? WHERE account_number = ? AND pin = ? ";
					PreparedStatement withdrawPreparedStatement = connection.prepareStatement(withdrawQuery);
					withdrawPreparedStatement.setDouble(1, amount);
					withdrawPreparedStatement.setString(2,accountNumber);
					withdrawPreparedStatement.setInt(3, pinNumber);
					withdrawPreparedStatement.executeUpdate();
					System.out.println("WITHDRAW COMPLETED REMAINING BALANCE IS " +(currentBalance - amount) );
					
					
				}
				else {
					System.out.println("INSUFFICIENT BALANCE");
				}
			}
			else {
				System.out.println("INVALID ACCOUNT NUMBER OR PIN");
			}
			connection.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void fetchBalance() {
		
		try {
		Connection connection = createConnection();
		System.out.println("ENTER ACCOUNT NUMBER");
		String accountNumber = scanner.next();
		System.out.println("ENTER PIN");
		int pinNumber = scanner.nextInt();
		String queryBalance = "SELECT balance  from atm WHERE account_number = ? AND pin = ?";
		PreparedStatement statement = connection.prepareStatement(queryBalance);
		statement.setString(1,accountNumber);
		statement.setInt(2,pinNumber);
		ResultSet resultset = statement.executeQuery();
		if(resultset.next())
		{
			System.out.println(resultset.getDouble("balance"));
			
		}
		else {
			System.out.println("ENTER CORRECT PIN OR ACCOUNT_NUMBER");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

	public void update()
	{
		System.out.println("1.NAME\n2.PIN");
		System.out.println("ENTER CHOICE");
		int choice = scanner.nextInt();
		System.out.println("ENTER ACCOUNT_NUMBER ");
		String accountNumber = scanner.next();
		System.out.println("ENTER PIN");
		int pin = scanner.nextInt();
		switch(choice)
		{
		case 1 :
			try {
				Connection connection = createConnection();
				String 	updateQuery = "SELECT * FROM atm WHERE account_number = ? AND pin = ? ";
				PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
				preparedStatement.setString(1,accountNumber);
				preparedStatement.setInt(2,pin);
				ResultSet resultset = preparedStatement.executeQuery();
				if(resultset.next())
				{
					    String newName = scanner.next();
						String updatename = "UPDATE atm SET name = ? WHERE account_number = ?  ";
						PreparedStatement updateNamePrepared = connection.prepareStatement(updatename);
						updateNamePrepared.setString(1,newName);
						updateNamePrepared.setString(2,accountNumber);
						updateNamePrepared.executeUpdate();
						System.out.println("UPDATE COMPLETED");
				}
				else {
					System.out.println("YOU ENTERED WRONG ACCOUNT NUMBER OR PIN");	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			      
			break ;
		case 2 :
				// UPDATE PIN
			try {
				Connection connection = createConnection();
				String 	updateQuery = "SELECT * FROM atm WHERE account_number = ? AND pin = ? ";
				PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
				preparedStatement.setString(1,accountNumber);
				preparedStatement.setInt(2,pin);
				ResultSet resultset = preparedStatement.executeQuery();
				if(resultset.next())
				{       System.out.println("ENTER NEW PIN");
					    int newPin = scanner.nextInt();
						String updatePin = "UPDATE atm SET pin = ? WHERE account_number = ?  ";
						PreparedStatement updatePinPrepared = connection.prepareStatement(updatePin);
						updatePinPrepared.setInt(1,newPin);
						updatePinPrepared.setString(2,accountNumber);
						updatePinPrepared.executeUpdate();
						System.out.println("UPDATE COMPLETED");
				}
				else {
					System.out.println("YOU ENTERED WRONG ACCOUNT NUMBER OR PIN");	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			      
			break ;
		}
	}

	@Override
	public void displayAll() {
		try {
			Connection connection = createConnection();
			Statement statement = connection.createStatement();
			String fetchAll = "SELECT * FROM atm";
			 ResultSet resultset = statement.executeQuery(fetchAll);
			while(resultset.next())
			{
				System.out.println("Account Number is " + resultset.getString("account_number") + " name : " + resultset.getString("name")
				+ " Balance : " + resultset.getDouble("balance"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteUser() {
	
		try
		{
			System.out.println("ENTER ACCOUNT NUMBER");
			String accountNumber = scanner.next();
			System.out.println("ENTER PIN NUMBER");
			int pin = scanner.nextInt();
			Connection connection = createConnection();
			String fetch = "SELECT * FROM atm WHERE account_number = ? AND pin = ?";;
			PreparedStatement preparedStatementFetch = connection.prepareStatement(fetch);
			preparedStatementFetch.setString(1, accountNumber);
			preparedStatementFetch.setInt(2, pin);
			ResultSet resultset = preparedStatementFetch.executeQuery();
			if(resultset.next())
			{
				String deleteQuery = "DELETE FROM atm WHERE account_number = ? AND pin = ?";;
				PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
				preparedStatement.setString(1, accountNumber);
				preparedStatement.setInt(2, pin);
				preparedStatement.executeQuery();
				System.out.println("ACCCOUNT DELETED succesfully");
			}
			else {
				System.out.println("YOU ENTERED WRONG ACCOUNT NUMBER OR PIN");	
			}
			
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
}
