package com.atm;

import java.util.Scanner;

public class ATM_MAIN {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		//upcasting
		IATM atm = new ATM();
		
		while(true) {
			System.out.println("1.CREATE AN ACCOUNT\n2.DEPOSIT\n3.WITHDRAW\n4.CHECK BALANCE\n5.UPDATION\n6.DISPLAY ALL DETAILS\n7.DELETE ACCOUNT\n8.EXIST");
			System.out.println("Choose an option");	
			int option = scanner.nextInt();
			switch(option)
			{
			case 1 : // create an account
				atm.createAccount();
				break ;
			case 2 : // deposit
				atm.depositAmount();
				break ;
			case 3 :
				atm.withdrawAmount();
				break ;
			case 4: 
				atm.fetchBalance();
				break ;
			case 5 : 
				atm.update();
				break ;
			case 6 :
				atm.displayAll();
				break ;
			case 7 :
				atm.deleteUser();
				break ;
			case 8 :
				System.out.println("THANK YOU!!!");
				System.exit(0);
				break ;
			default : // CUSTOM EXCEPTION --INVALID EXCEPTION
				try {
					throw new InvalidChoiceException("SELECT CHOICE PROPERLY");
				}
				catch( InvalidChoiceException e)
				{
					System.out.println(e.getMessage());
				}				
			}
		}
	}

}
