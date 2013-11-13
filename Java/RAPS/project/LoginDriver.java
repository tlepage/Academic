/*
 * Created on Nov 12, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class LoginDriver
{
	final int CUSTOMER = 0;
	final int MANAGER = 3;
	final int EMPLOYEE = 2;
	final int TRAINEE = 1;
	
	Connection con = null;
	Statement stmt = null, stmt2 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String password = "";
	String userID = null;
	String requestIdentity = null, requestPassword = null;
	int returnType = -1, level, isEmployed = -1, status;
	public LoginDriver( String ID, String passW, int type, LoginPage loginPage,
		MainScreenDefault page )
	{
		System.out.println( type );
		System.out.println( ID );
		System.out.println( passW );
		if( type == CUSTOMER )
		{
			System.out.println( "in customer...");
			requestIdentity = "SELECT login, status FROM CUSTOMER WHERE " +
				"login LIKE '" + ID + "'";
			System.out.println( requestIdentity );
			requestPassword = "SELECT password FROM CUSTOMER WHERE " +				"password LIKE '" + passW + "'";
			System.out.println( requestPassword );
		}else if( type == MANAGER || type == EMPLOYEE || type == TRAINEE )
		{
			System.out.println( "in employee...");
			requestIdentity = "SELECT login, access, employed FROM GENERAL_EMPLOYEE " +				"WHERE login LIKE '" + ID + "'";
			requestPassword = "SELECT password FROM GENERAL_EMPLOYEE " +				"WHERE password LIKE '" + passW + "'";
		}
		System.out.println( requestIdentity);
		System.out.println( requestPassword ); 
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
			System.out.println( requestIdentity );
			System.out.println( requestPassword );
			ResultSet result1 = stmt.executeQuery( requestIdentity );
			ResultSet result2 = stmt2.executeQuery( requestPassword );
			
			
			if( result1.next() )
			{
				if(type == CUSTOMER )
				{	userID = result1.getString( "login" );
					status = result1.getInt( "status" );
					if( status == 0 )
					{
						JOptionPane.showMessageDialog( null, "Invalid Login",
							"Error", JOptionPane.ERROR_MESSAGE );
						return;
					}
					else
					{
						returnType = 0;
					}
				}
				else 	
				{
					userID = result1.getString( "login" );
					level = result1.getInt( "access" );
					isEmployed = result1.getInt( "employed" );
					if( level != type || isEmployed == 0 )
					{
						JOptionPane.showMessageDialog( null, 
							"Invalid Login", "Error" , JOptionPane.ERROR_MESSAGE );
						return;
					}
					else
					{
						returnType = level;
					}
				}
					
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Incorrect Login.",
					"Incorrect Login", JOptionPane.ERROR_MESSAGE );
				return;
			}
			while( result1.next() )
			{
				
			}
			if( result2.next() )
			{
				String pass = result2.getString( "password" );
				System.out.println( pass + passW );
				if( !pass.equals(passW) )
				{
					JOptionPane.showMessageDialog( null, "Incorrect Login.",
						"Incorrect Login", JOptionPane.ERROR_MESSAGE );
					return;
				}
				
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Incorrect Login.",
					"Incorrect Login", JOptionPane.ERROR_MESSAGE );
				return;
			}
			while( result2.next() )
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
			}
			catch( Exception e ) {}

			try 
			{ 
				con.close(); 
			}
			catch( Exception e ) {}
	
		}
		
		LoginManager manage = new LoginManager( this, loginPage, page );
	
	}
	
}
