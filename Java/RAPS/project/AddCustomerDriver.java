/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class AddCustomerDriver
{
	final int VISA = 0;
	final int MASTERCARD = 1;
	final int AMERICAN_EXPRESS = 2;
	final int DISCOVER = 3;
	protected String cType = null;
	protected int status = 1;
	
	public AddCustomerDriver( String name, String address, String phone, 
		String fax, String login, String pWord, String email, String CCnumber, 
		int CCtype, NewUserPage page )
	{
		Connection con = null;
		Statement stmt = null;
		Statement stmt2 = null, stmt3 = null;
	
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		if( CCtype == VISA )cType = "VISA";
		else if( CCtype == MASTERCARD ) cType = "MASTERCARD";
		else if( CCtype == AMERICAN_EXPRESS )cType = "AMERICAN EXPRESS";
		else if( CCtype == DISCOVER )cType = "DISCOVER";
		
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
			String checkID = "SELECT login FROM CUSTOMER WHERE login " +				"LIKE '" + login + "'";
				
			ResultSet rs = stmt.executeQuery( checkID );
			
			if( rs.next() )
			{
				JOptionPane.showMessageDialog( null, "Invalid User ID.  Please choose" +					" another." );
				return;
			}
			else
			{
				System.out.println( "VALIDATED" );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			
			String makeNewUser = "INSERT INTO CUSTOMER ( login, password, " +				"name, address, phone, fax, CCtype, CCnumber, status ) VALUES ( '" +				login + "', '" + pWord + "', '" + name + "', '" + address +
				"', '" + phone + "', '" + fax + "', '" +
				cType + "', '" + CCnumber + "', '" + status + "' )";
			
			System.out.println( makeNewUser );	
			System.out.println( fax );
			
			stmt2.executeQuery( makeNewUser );
			
		}
		
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String addEmail = "UPDATE CUSTOMER SET email = '" + 
				email + "' WHERE login LIKE '" + login + "'";
			System.out.println( addEmail );
			
			stmt3.executeQuery( addEmail );
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
		JOptionPane.showMessageDialog( null, "Thank You." );
		page.hide();
	}
	
}
