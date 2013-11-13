/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.util.Date;

public class AddEmployeeDriver implements Dates
{
	public AddEmployeeDriver(String login, String pWord, String name, 
		String address, String phone, String email, int access, int store, int employed,
		HireEmployeePage hirePage )
	{
		Connection con = null;
		Statement stmt = null, stmt2 = null, stmt3 = null;
				
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";	
		
		Date currentDate = new Date();
		String dateString = currentDate.toString();
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
			String checkID = "SELECT login FROM GENERAL_EMPLOYEE WHERE login " +
				"LIKE '" + login + "'";
				
			ResultSet rs = stmt.executeQuery( checkID );
			
			if( rs.next() )
			{
				JOptionPane.showMessageDialog( null, "Invalid User ID.  Please choose" +
					" another." );
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
			String newEmployee = "INSERT INTO GENERAL_EMPLOYEE ( login, password, " +				"name, address, phone, access, store, employed, hiredate ) VALUES ( '" +
				login + "', '" + pWord + "', '" + name + "', '" + address + 
				"', '" + phone + "', '" + access + "', '" + store + "', '" + 
				employed + "', '" + dateString + "' )";
			
			System.out.println( newEmployee );
			stmt2.executeQuery( newEmployee );
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String updateEmail = "UPDATE GENERAL_EMPLOYEE SET email = '" +
				email + "' WHERE login LIKE '" + login + "'";
			System.out.println( updateEmail );
			
			stmt3.executeQuery( updateEmail );
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
		hirePage.hide();
	}

}
