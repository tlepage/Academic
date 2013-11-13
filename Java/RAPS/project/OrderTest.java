/*
 * Created on Nov 8, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Date;

public class OrderTest extends JFrame implements Dates
{
	int curStore, quantity, request, thisStoreQuantity;
	double getBalance, newBalance;
	protected RetrievePartRequestSKU checkSKU;
	public OrderTest( String SKU, int store, String ID, String custID,
		DeliveryOptions deliveryOptions, OrderPage page )
	{
		super( "Displaying Query Results" );
		
		checkSKU = new RetrievePartRequestSKU( SKU );
		
		if( checkSKU.pNumber == null )
		{
			JOptionPane.showMessageDialog( null, "No matching SKU.");
			return;
		}
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		String requestQuantity = null, removeQuantity;
		Connection connection = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		Statement stmt5 = null;
		Statement stmt6 = null;
		Statement stmt7 = null;
		Statement stmt8 = null;
		
		double orderNumber = 10000 + (int)(Math.random() * 12200 );
		int number = (int)orderNumber;
		
		DecimalFormat twoDigits = new DecimalFormat( "0.00" );
		Date currentDate = new Date();
		Date deliveryDate;
		long currentLong = currentDate.getTime();
		String dateString = currentDate.toString();
		String deliveryString = null;
		
		if( deliveryOptions.deliveryOption == "EXPRESS" )
		{
			deliveryDate = new Date( currentLong + DAY );
			deliveryString = deliveryDate.toString();
		}
		else if( deliveryOptions.deliveryOption == "NORMAL" )
		{
			deliveryDate = new Date( currentLong + FIVEDAYS );
			deliveryString = deliveryDate.toString();
		}
		
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
			connection = DriverManager.getConnection( url, userName, password );
			stmt = connection.createStatement();
			stmt1 = connection.createStatement();
			stmt2 = connection.createStatement();
			stmt3 = connection.createStatement();
			stmt4 = connection.createStatement();
			stmt5 = connection.createStatement();
			stmt6 = connection.createStatement();
			stmt7 = connection.createStatement();
			stmt8 = connection.createStatement();
		}
		catch( Exception e )
		{
			System.err.println( "problems connecting to " + url + ":" );
			System.err.println( e.getMessage() );

			if( connection != null)
			{
				try { connection.close(); }
				catch( Exception e2 ) {}
			}
			return;
		}
		if( custID != null )
		{
			try
			{
				String checkValidID = "SELECT login FROM CUSTOMER WHERE login " +					"LIKE '" + custID + "'";
					
				ResultSet rs = stmt1.executeQuery( checkValidID );
				
				if( rs.next() )
				{
					JOptionPane.showMessageDialog( null, "Valid ID" );
				}
				else
				{
					JOptionPane.showMessageDialog( null, "Invalid ID" );
					return;
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
		try
		{
			String getOtherStore = "SELECT pnumber, store, quantity FROM INVENTORY " +				"WHERE pnumber LIKE '" + SKU + "' AND quantity > 10 AND store <> " +
				store;
			ResultSet result = stmt.executeQuery( getOtherStore );
			if( result.next() )
			{
				curStore = result.getInt( "store" );
				quantity = result.getInt( "quantity" );
				
				JOptionPane.showMessageDialog( null, "Got part " + 
					result.getString( "pnumber" ) + " from store #" + 
					curStore + " with quantity " + quantity );
			}
			else
			{
				JOptionPane.showMessageDialog( null, 
					"No other stores have enough or any of this part." );
				return;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			requestQuantity = JOptionPane.showInputDialog( "Please enter" +				" a valid order quantity (Store must maintain atleast 10 of their" +				" quantity." );
			request = Integer.parseInt( requestQuantity );	
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog( null, "Order Cancelled." );
			return;
			
		}	
		
			
		if( request >= (quantity - 10) || request <= 0 )
		{
			JOptionPane.showMessageDialog( null, "Invalid Quantity." );
			return;
		}
		try
		{
			removeQuantity = "UPDATE INVENTORY SET quantity = " +
				(quantity - request) + " WHERE pnumber LIKE '" + SKU + "' AND " +				"store LIKE '" + curStore + "'";
				
			stmt2.executeQuery( removeQuantity );
		}
		catch( Exception e )
		{
			e.printStackTrace();	
		}
		
		try
		{
			String getQuantity = "SELECT quantity FROM INVENTORY WHERE pnumber LIKE '" 
			+ SKU + "' AND store LIKE '" + store + "'";
			
			ResultSet result2 = stmt3.executeQuery( getQuantity );
			
			 if( result2.next() )
			 {
			 	thisStoreQuantity = result2.getInt( "quantity" );
			 }
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			thisStoreQuantity += request;
			String setQuantity = "UPDATE INVENTORY SET quantity = " + thisStoreQuantity +
				 " WHERE pnumber LIKE '" + SKU + "' AND store LIKE '" + store + "'";
			
			stmt4.executeQuery( setQuantity ); 
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{	
			if( custID == null )custID = "";
			String setEmpOrder = "INSERT INTO EMPLOYEE_ORDER ( login, Onumber, " +				" clogin, amount, Dtype, company, Dcost, purchasedate, deliverydate ) VALUES ( '" + 
				ID + "', '" + number + "', '" + custID + "', '" + twoDigits.format(deliveryOptions.dCost) +
			 	"', '" + deliveryOptions.deliveryOption + "', '" + 
			 	deliveryOptions.company + "', '" + deliveryOptions.dCost + 
				"', '" + dateString + "', '" + deliveryString + "' )";
			System.out.println( setEmpOrder );
			stmt5.executeQuery( setEmpOrder );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		} 
		try
		{
			String setEmpOrderPart = "INSERT INTO EMPLOYEE_ORDER_PART ( " +				"Onumber, store, part, quantity ) VALUES ( '" + number + "', '" + 
				store + "', '" + SKU + "', '" + request + "' )";
			
			stmt6.executeQuery( setEmpOrderPart );				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getBalancePrevious = "SELECT balance FROM STORE WHERE snumber " +				"LIKE '" + store + "'";
				
			ResultSet rSet = stmt7.executeQuery( getBalancePrevious );	
			
			if( rSet.next() )
			{
				getBalance = rSet.getDouble( "balance" );
				newBalance = getBalance - deliveryOptions.dCost; 
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String setBalance = "UPDATE STORE SET balance = " + twoDigits.format(newBalance) +
				" WHERE snumber LIKE '" + store + "'";
				
			page.hide();
			stmt8.executeQuery( setBalance );
			
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
				stmt5.close();
				stmt6.close();
				stmt7.close();
				stmt8.close();
			}
			catch( Exception e ) 
			{
			}

			try 
			{ 
				connection.close(); 
			}
			catch( Exception e ) 
			{
			}
		}
		
		JOptionPane.showMessageDialog( null, "Order Number: " + number + " was " +
			"successful." );
	}
	
}

