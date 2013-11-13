/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Date;

public class CheckoutFinalize implements Dates
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmt4 = null;
	Statement stmt5 = null;
	Statement stmt6 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String password = "";
	
	String pName = null , pBrand = null, pDesc = null, pNumber = null, 
		pType = null;
	
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	
	double pCost = 0.0;
	int pQuantity = 0;
	boolean flag = true;
	RemovePartRequestEmployeeSKU request;
	String realQuantity = null;
	String requestQuantity = null;
	int totalSold = 0;
	double getPrevSoldPrice = 0;
	int getPrevSoldAmt = 0;
	double getCurrentBalance = 0;
	double purchasePrice = 0;
	protected Date currentDate;
	protected long dateLong;
	protected String dateString;
	
	public CheckoutFinalize( ItemList newList, String finalPrice,
		String myID, int store, int ifCash, int ifCheck, String CCtype, 
		String CCnumber )
	{
		double transNumber = 10000 + (int)(Math.random() * 12200 );
		int transaction = (int)transNumber;
		ItemList itemList = newList;
		
		currentDate = new Date();
		dateLong = currentDate.getTime();
		dateString = currentDate.toString();
		
		purchasePrice = Double.parseDouble( finalPrice );
		try
		{
			Class.forName( driver );
		}
		catch( Exception e )
		{
			flag = false;
			System.err.println(
				"Failed to load current driver.");
			return;
		}
		
		try
		{
			con = DriverManager.getConnection( url, userName, password );
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();
			stmt4 = con.createStatement();
			stmt5 = con.createStatement();
			stmt6 = con.createStatement();
		}
		catch( Exception e )
		{
			flag = false;
			System.err.println( "problems connecting to " + url + ":" );
			System.err.println( e.getMessage() );

			if( con != null)
			{
				try { con.close(); }
				catch( Exception e2 ) {}
			}

			return;
		}
		try
		{
			for( int i = 0; i < itemList.currentAmount; i++ )
			{
				String testQuantity = Integer.toString( itemList.purchaseQuantity[i]);
				RetrievePartRequestEmployeeSKU requestTest = new 
					RetrievePartRequestEmployeeSKU( itemList.parts[i].pNumber, store );
				
				if( requestTest.pQuantity < itemList.purchaseQuantity[i] )
				{
					JOptionPane.showMessageDialog( null, "Invalid quantity on " +						"item " + i );
					return;
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			stmt3.close();
								
			stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			ResultSet updatePart = stmt3.executeQuery(
				"SELECT * FROM EMPLOYEE_TRANSACTION");
			updatePart.moveToInsertRow();
			updatePart.updateString("login", myID);
			updatePart.updateInt("transaction", transaction);
			updatePart.updateDouble("amount", purchasePrice);
			updatePart.updateInt("cash", ifCash);
			updatePart.updateInt("check", ifCheck);
			updatePart.updateString("CCtype", CCtype);
			updatePart.updateString("CCnumber", CCnumber);
			updatePart.updateString("date", dateString);
			updatePart.insertRow();
			
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			for( int i = 0; i < itemList.setAmount; i++ )
			{
				
				if( itemList.purchaseQuantity[i] == -1 )
				{
					System.out.println("Skip negative quantity");
				}
				else
				{
					String quantity = Integer.toString( itemList.purchaseQuantity[i]);
					request = new RemovePartRequestEmployeeSKU( itemList.parts[i].pNumber, 
						quantity, store );
					
					String addToEmpPart = "INSERT INTO EMPLOYEE_PART ( " +
						"transaction, part, quantity ) VALUES ( '" +
						transaction + "', '" + itemList.parts[i].pNumber +
						"', '" + itemList.purchaseQuantity[i] + "' )";
					totalSold += itemList.purchaseQuantity[i];
					itemList.purchaseQuantity[i] = -1;
					System.out.println( addToEmpPart );	
				
					try
					{
						stmt.executeQuery( addToEmpPart );
					}
					catch( Exception e)
					{
						e.printStackTrace();	
					}
				}
			}
			for( int i = 0; i < itemList.setAmount; i++ )
			{
				itemList.purchaseQuantity[i] = -1;
				itemList.currentAmount--;
			}
			itemList.currentAmount = 0;
			itemList.setAmount = 0;
			itemList.isEmpty = true;
			
		}
		catch (Exception e) 
		{ 
				
			e.printStackTrace(); 
		}
		
		try
		{
			String getBalance = "SELECT balance FROM STORE WHERE snumber LIKE '"
				+ store + "'";
				
			ResultSet result2 = stmt5.executeQuery( getBalance );
			
			if( result2.next())
			{
				getCurrentBalance = result2.getInt( "balance" );
				System.out.println( getCurrentBalance );
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			getCurrentBalance += purchasePrice;
			System.out.println( twoDigits.format(getCurrentBalance) + " " + store + " " + transaction );
			String setBalance = "UPDATE STORE SET balance = " + twoDigits.format(getCurrentBalance) +
				" WHERE snumber LIKE " + store;
			stmt6.executeQuery( setBalance );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{ 
				stmt.close(); 
				stmt1.close();
				stmt2.close();
				stmt3.close();
				stmt4.close();
				stmt5.close();
				stmt6.close();
			}
			catch( Exception e ) {}

			try 
			{ 
				con.close(); 
			}
			catch( Exception e ) {}
		}
		JOptionPane.showMessageDialog( null, "Transaction Number: " + transaction +
			", Total Tendered: $" + purchasePrice + " successful.");
	}
}	

