/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class RetrieveEmployeeID
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String pWord = "";
	
	protected String name, login, password, phone, address, email, hireDate;
	protected int storeNumber, level, employed;
	boolean flag = true;
	
	public RetrieveEmployeeID( String ID )
	{
		String retrieveEmployee = "SELECT * FROM GENERAL_EMPLOYEE WHERE login" +			" LIKE '" + ID + "'";
		String retrieveStatus = "SELECT * FROM EMPLOYEE WHERE login LIKE '" +
			ID + "'";
			
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
			con = DriverManager.getConnection( url, userName, pWord );
			stmt = con.createStatement();
			stmt2 = con.createStatement();
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
			ResultSet result = stmt.executeQuery( retrieveEmployee );
			
			if( result.next() )
			{
				name = result.getString( "name" );
				phone = result.getString( "phone");
				login = result.getString( "login" );
				password = result.getString( "password" );
				level = result.getInt( "access" );
				address = result.getString( "address" );
				email = result.getString( "email" );
				employed = result.getInt( "employed" );
				storeNumber = result.getInt( "store" );
				hireDate = result.getString( "hiredate" );
			
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Invalid Employee Login" );
				flag = false;
				return;
			}
			while( result.next() )
			{
				
			}
			
		}
		catch (Exception e) 
		{ 
				
			e.printStackTrace(); 
		}
		
		finally
		{
			try 
			{ 
				stmt.close(); 
				stmt2.close();
			}
			catch( Exception e ) {}

			try 
			{ 
				con.close(); 
			}
			catch( Exception e ) {}
		}
	}
}	

