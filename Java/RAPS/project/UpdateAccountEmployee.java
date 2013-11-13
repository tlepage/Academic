/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class UpdateAccountEmployee
{
	public UpdateAccountEmployee(String login, String pWord, String address, String phone,
		String email, int access, int store, int employed, UpdateEmployeePage page )
	{
		Connection con = null;
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null,
			stmt5 = null, stmt6 = null, stmt7 = null;
		
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		String updatePassword = "UPDATE GENERAL_EMPLOYEE SET password = '" 
			+ pWord + "' WHERE login LIKE '" + login + "'";
		String updateAddress = "UPDATE GENERAL_EMPLOYEE SET address = '" 
			+ address + "' WHERE login LIKE '" + login + "'";
		String updatePhone = "UPDATE GENERAL_EMPLOYEE SET phone = '" 
			+ phone + "' WHERE login LIKE '" + login + "'";
		String updateAccess = "UPDATE GENERAL_EMPLOYEE SET access = '" 
			+ access + "' WHERE login LIKE '" + login + "'";
		String updateStore = "UPDATE GENERAL_EMPLOYEE SET store = '"
			+ store + "' WHERE login LIKE '" + login + "'";
		String updateEmployed = "UPDATE GENERAL_EMPLOYEE SET employed = '"
			+ employed + "' WHERE login LIKE '" + login + "'";
		String updateEmail = "UPDATE GENERAL_EMPLOYEE SET email = '"
			+ email + "' WHERE login LIKE '" + login + "'";

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
			System.out.println( updatePassword );
			stmt.executeQuery( updatePassword );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( updateAddress );
			stmt2.executeQuery( updateAddress );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( updatePhone );
			stmt3.executeQuery( updatePhone );	
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}	
		try
		{
			System.out.println( updateAccess );
			stmt4.executeQuery( updateAccess );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( updateStore );
			stmt5.executeQuery( updateStore );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( updateEmployed );
			page.hide();
			stmt6.executeQuery( updateEmployed );
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			System.out.println( updateEmail );
			
			stmt7.executeQuery( updateEmail );
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
		JOptionPane.showMessageDialog( null, "Thank you." );
		page.hide();		
	}
}
