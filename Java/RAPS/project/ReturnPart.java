/*
 * Created on Nov 15, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class ReturnPart extends JFrame
{
	public ReturnPart( int orderNumber, int storeNumber, int quantity )
	{
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		double getBalance = 0, newBalance = 0, currentCost = 0, totalCost = 0;
		int storeQuantity = 0;
		int newQuantity = 0;
		Connection connection = null;
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null,
			stmt5 = null, stmt6 = null, stmt7 = null, stmt8 = null;
		
		int currentPart = 0, currentQty = 0;
		
		DecimalFormat twoDigits = new DecimalFormat( "0.00" );
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
		try
		{
			String getInfo = "SELECT part, quantity FROM EMPLOYEE_PART " +				"WHERE transaction LIKE '" + orderNumber + "'";
				
			ResultSet result = stmt.executeQuery( getInfo );
			
			if( result.next() )
			{
				currentPart = result.getInt( "part" );
				currentQty = result.getInt( "quantity" );
				
				JOptionPane.showMessageDialog( null, "Transaction quantity: " +
					currentQty );
				if( currentQty == -1 )
				{
					JOptionPane.showMessageDialog( null, "Invalidated Transaction.");
					return;
				}
				else if( quantity > currentQty || quantity <= 0 )
				{
					JOptionPane.showMessageDialog( null, "Invalid quantity" );
					return;
				}
				
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Invalid Request." );
				return;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getStoreNumber = "SELECT snumber, balance FROM STORE WHERE " +				"snumber LIKE '" + storeNumber + "'";
				
			ResultSet result2 = stmt2.executeQuery( getStoreNumber );
			
			if( result2.next() )
			{
				JOptionPane.showMessageDialog( null, "Valid store." );
				getBalance = result2.getDouble( "balance" );
				
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Wrong store." );
				return;
			}
			
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			String getPartInfo = "SELECT cost FROM PART WHERE pnumber LIKE '" +				currentPart + "'";
				
			ResultSet result3 = stmt3.executeQuery( getPartInfo );
			
			if( result3.next() )
			{
				currentCost = result3.getDouble( "cost" );
				totalCost = currentCost * quantity;
				newBalance = getBalance - totalCost;
				JOptionPane.showMessageDialog( null, "Amount to be returned in cash: $" +
					twoDigits.format(totalCost) );
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Invalid Request" );
				return;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getStoreQuantity = "SELECT quantity FROM INVENTORY WHERE " +				"store LIKE '" + storeNumber + "' AND pnumber LIKE '" +
				currentPart + "'";
				
			ResultSet result4 = stmt5.executeQuery( getStoreQuantity );
			
			if( result4.next() )
			{
				storeQuantity = result4.getInt( "quantity" );
				JOptionPane.showMessageDialog( null, "Store quantity: " +
					storeQuantity );
			} 
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			newQuantity = storeQuantity + quantity;
			String setQuantity = "UPDATE INVENTORY SET quantity = " +
				newQuantity + " WHERE store LIKE '" + storeNumber + "' AND " +				"pnumber LIKE '" + currentPart + "'";
			stmt4.executeQuery( setQuantity );
				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( "New Balance(before): " + newBalance );
			String updateBalance = "UPDATE STORE SET balance = " +
				twoDigits.format(newBalance) + " WHERE snumber LIKE '" + storeNumber + "'";
			System.out.println( updateBalance );
			stmt6.executeQuery( updateBalance );
			System.out.println( "new balance: " + updateBalance + " " + newBalance );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String invalidateOrder = "UPDATE EMPLOYEE_PART SET quantity = -1 "
				+ "WHERE transaction LIKE '" + orderNumber + "' AND part LIKE '"
				+ currentPart + "'";
			System.out.println( invalidateOrder );
			stmt7.executeQuery( invalidateOrder );
			
			System.out.println( "transaction invalidated: " + currentQty );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateReturnedStatus = "UPDATE EMPLOYEE_TRANSACTION SET returned = 1 " +				"WHERE transaction LIKE '" + orderNumber + "'";
			System.out.println( updateReturnedStatus );
			stmt8.executeQuery( updateReturnedStatus );
			
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
		
	}
}
