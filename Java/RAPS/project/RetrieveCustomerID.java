/*
 * Created on Nov 12, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;

public class RetrieveCustomerID
{
	Connection con = null;
	Statement stmt = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String pWord = "";
	
	protected String name, phone, login, password, address, email, fax,
		CCtype, CCnumber;
	protected int status;
	
	public RetrieveCustomerID( String ID )
	{
		String retrieveEmployee = "SELECT * FROM CUSTOMER WHERE login" +
			" LIKE '" + ID + "'";
		
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
					status = result.getInt( "status" );
					phone = result.getString( "phone");
					login = result.getString( "login" );
					password = result.getString( "password" );
					
					address = result.getString( "address" );
					email = result.getString( "email" );
					fax = result.getString( "fax" );
					CCtype = result.getString( "CCtype" );
					CCnumber = result.getString( "CCnumber" );
				
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
