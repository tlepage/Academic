/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class UpdateAccountCustomer
{
	final int VISA = 0;
	final int MASTERCARD = 1;
	final int AMERICAN_EXPRESS = 2;
	final int DISCOVER = 3;
	protected String cType = null;
	
	public UpdateAccountCustomer( String CCnumber, String pWord, 
		String address, String phone, String fax, int CCtype, 
		int status, String email, Customer myCustomer, UpdateCustomerPage page,
		MainScreenEnabledCustomer enabledPage )
	{
		if( CCtype == VISA )cType = "VISA";
		else if( CCtype == MASTERCARD ) cType = "MASTERCARD";
		else if( CCtype == AMERICAN_EXPRESS )cType = "AMERICAN EXPRESS";
		else if( CCtype == DISCOVER )cType = "DISCOVER";
		
		Connection con = null;
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null,
			stmt5 = null, stmt6 = null, stmt7 = null, stmt8 = null;
		
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
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
			stmt5 = con.createStatement();
			stmt6 = con.createStatement();
			stmt7 = con.createStatement();
			stmt8 = con.createStatement();
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
			String updateCustomerPassword = "UPDATE CUSTOMER SET password = '" 
				+ pWord + "' WHERE login LIKE '" + myCustomer.login + "'";
			System.out.println( updateCustomerPassword );
			stmt.executeQuery( updateCustomerPassword );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateCustomerAddress = "UPDATE CUSTOMER SET address = '"
				+ address + "' WHERE login LIKE '" + myCustomer.login + "'";
			stmt2.executeQuery( updateCustomerAddress );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateCustomerPhone = "UPDATE CUSTOMER SET phone = '"
				+ phone + "' WHERE login LIKE '" + myCustomer.login + "'";
				
			stmt3.executeQuery( updateCustomerPhone );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		} 
		try
		{
			String updateCustomerFax = "UPDATE CUSTOMER SET fax = '"
			 	+ fax + "' WHERE login LIKE '" + myCustomer.login + "'";
			 stmt4.executeQuery( updateCustomerFax );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateCustomerCC = "UPDATE CUSTOMER SET CCnumber = '"
				+ CCnumber + "' WHERE login LIKE '" + myCustomer.login + "'";
			stmt5.executeQuery( updateCustomerCC );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateCustomerCCType = "UPDATE CUSTOMER SET CCtype = '"
				+ cType + "' WHERE login LIKE '" + myCustomer.login + "'";
			stmt6.executeQuery( updateCustomerCCType );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateCustomerStatus = "UPDATE CUSTOMER SET status = '"
				+ status + "' WHERE login LIKE '" + myCustomer.login + "'";
			
			stmt7.executeQuery( updateCustomerStatus );
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateEmail = "UPDATE CUSTOMER SET email = '" +
				email + "' WHERE login LIKE '" + myCustomer.login + "'";
			
			
			stmt8.executeQuery( updateEmail );
			
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
				con.close(); 
			}
			catch( Exception e ) 
			{
			}
		}
		page.hide();
		enabledPage.hide();
		new MainScreenDefault();
		JOptionPane.showMessageDialog( null, "RAPS must log-off to update changes." +
			"\nThank you." );		
	}
}