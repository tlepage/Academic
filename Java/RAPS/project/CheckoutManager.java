/*
 * Created on Nov 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;

public class CheckoutManager implements Dates
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null, stmt3 = null, stmt4 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String password = "";
	
	RemovePartRequestSKU request;
	
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	protected Date currentDate, deliveryDate;
	protected long currentDateLong, deliveryDateLong;
	protected String currentDateString, deliveryDateString;
	
	public CheckoutManager( Cart myCart, Customer me, PaymentOptions p, 
			DeliveryOptions d, int Onumber)
	{
		double balance = 0;
		int number = Onumber;
		currentDate = new Date();
		currentDateLong = currentDate.getTime();
		currentDateString = currentDate.toString();
		
		if( d.deliveryOption == "EXPRESS" )
		{
			deliveryDate = new Date( currentDateLong + DAY );
			deliveryDateLong = currentDateLong + DAY;
			deliveryDateString = deliveryDate.toString();
		}
		else if( d.deliveryOption == "NORMAL" )
		{
			deliveryDate = new Date( currentDateLong + FIVEDAYS );
			deliveryDateLong = currentDateLong + FIVEDAYS;
			deliveryDateString = deliveryDate.toString();
		}
		
		String order = Integer.toString( number );
		System.out.println( me.login );
		String addToDelivery = "INSERT INTO CUSTOMER_DELIVERY ( login, " +			"Onumber, amount, cash, CCType, Purchasedate, DeliveryDate, " +			"CCNumber, Dtype, company, Dcost ) VALUES ( '" + me.login + "', '" + number +
			"', '" + twoDigits.format(myCart.total) + "', '" + p.cash + "', '" +
			p.CType + "', '" + currentDateString + "', '" + deliveryDateString + "', '" +
			p.CNumber + "', '" + d.deliveryOption + "', '" + 
			d.company + "', '" + twoDigits.format(d.dCost) + "' )";
		String getOnlineBalance = "SELECT balance FROM STORE WHERE snumber LIKE '999'";
		
		try
		{
			Class.forName( driver );
		}
		catch( Exception e )
		{
			System.err.println(
				"Failed to load current driver.");
			return;
		}
		try
		{
			con = DriverManager.getConnection( url, userName, password );
			stmt = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();
			stmt4 = con.createStatement();
		}
		catch( Exception e )
		{
			
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
			for( int i = 0; i < myCart.currentAmount; i++ )
			{
				String testQuantity = Integer.toString( myCart.purchaseQuantity[i] );
				RetrievePartRequestSKU requestTest = new RetrievePartRequestSKU( myCart.parts[i].pNumber );
					
				if( requestTest.pQuantity < myCart.purchaseQuantity[i] )
				{
					JOptionPane.showMessageDialog( null, "Invalid quantity on cart ID "
					 + i );
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
			stmt.executeQuery( addToDelivery );
		}
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
		}
		try
		{
			ResultSet rs1 = stmt4.executeQuery( getOnlineBalance );
			if( rs1.next() )
			{
				balance = rs1.getDouble( "balance" );
			} 
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String addToOnlineBalance = "UPDATE STORE SET balance = '" + twoDigits.format((balance + myCart.total - d.dCost)) 
				+ "' WHERE snumber LIKE '999' ";
			stmt3.executeQuery( addToOnlineBalance );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			for( int i = 0; i < myCart.setAmount; i++ )
			{
				if( myCart.purchaseQuantity[i] == -1 )
				{
					System.out.println( "Skip negative quantity" );
				}
				else
				{
					String quantity = Integer.toString( myCart.purchaseQuantity[i]);
					request = new RemovePartRequestSKU( myCart.parts[i].pNumber, 
						quantity );
				
					String updateCustomerPart = "INSERT INTO CUSTOMER_PART (" +						" Onumber, store, part, quantity ) VALUES ( '" + order +
						"', '" + myCart.parts[i].storeNumber + "', '" + 
						myCart.parts[i].pNumber + "', '" + quantity + "' )";
				
					try
					{
						stmt2.executeQuery( updateCustomerPart );
					}
					catch( Exception e)
					{
						e.printStackTrace();
					}
				}
			}	
			for( int t = 0; t < myCart.setAmount; t++ )
			{
				myCart.purchaseQuantity[t] = -1;
				myCart.currentAmount--;
			}
			myCart.currentAmount = 0;
			myCart.setAmount = 0;
			myCart.isEmpty = true;	
			
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
				stmt2.close();
				stmt3.close();
				stmt4.close();
			}
			catch( Exception e ) {}

			try 
			{ 
				con.close(); 
			}
			catch( Exception e ) {}
		}
		JOptionPane.showMessageDialog( null, "Order: " + Onumber +
		 	", Cost: $" + twoDigits.format(myCart.total) + " successful.");
	}
}	

