/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class AddExistingPart
{
	protected RetrievePartRequestEmployeeSKU retrieve;
	protected int newQuantity, currentQuantity;
	protected double balance, newBalance;
	protected DecimalFormat twoDigits;
	public AddExistingPart( String sku, int quantity, int storeNumber )
	{
		Connection con = null;
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null;
	
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		twoDigits = new DecimalFormat( "0.00" );
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
			retrieve = new RetrievePartRequestEmployeeSKU( sku, storeNumber );
			
			if( retrieve.pNumber == null )
			{
				JOptionPane.showMessageDialog( null, "Store does not contain" +					" a part with this SKU." );
				return;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}	
		try
		{
			String getQuantity = "SELECT quantity FROM INVENTORY WHERE pnumber " +				"LIKE '" + sku + "' AND store LIKE '" + storeNumber + "'";
			ResultSet rs = stmt.executeQuery( getQuantity );
			
			if( rs.next() )
			{
				currentQuantity = rs.getInt( "quantity" );
			}
			
			System.out.println( "Current Quantity: " + currentQuantity );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getBalance = "SELECT balance FROM STORE WHERE snumber LIKE '"
				+ storeNumber + "'";
			ResultSet rs2 = stmt3.executeQuery( getBalance );
			
			if( rs2.next() )
			{
				balance = rs2.getDouble( "balance" );
				newBalance = balance - ( quantity * retrieve.pCost );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			JOptionPane.showMessageDialog( null, "Cost: $" + 
				twoDigits.format((quantity * retrieve.pCost)));
			String updateBalance = "UPDATE STORE SET balance = " + newBalance +
				" WHERE snumber LIKE '" + storeNumber + "'";
			stmt4.executeQuery( updateBalance );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			newQuantity = currentQuantity + quantity;
			
			String setQuantity = "UPDATE INVENTORY SET quantity = " +
				newQuantity + " WHERE pnumber LIKE '" + sku + "'";
				
			stmt2.executeQuery( setQuantity );
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
		
		JOptionPane.showMessageDialog( null, "Part successfully updated.\n" +			"Old Balance: $" + twoDigits.format(balance) + 
			"\nNew Balance: $" + twoDigits.format(newBalance) );
	}
}
