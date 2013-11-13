/*
 * Created on Nov 30, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.util.Date;

public class ViewDailyBalance
{
	protected double balance;
	protected Date date;
	public ViewDailyBalance( int storeNumber )
	{
		date = new Date();
		
		Connection con = null;
		Statement stmt = null;
		
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
			String getBalance = "SELECT balance FROM STORE WHERE snumber LIKE '"
				+ storeNumber + "'";
			ResultSet rs = stmt.executeQuery( getBalance );
			
			if( rs.next() )
			{
				balance = rs.getDouble( "balance" );
			}
			
			JOptionPane.showMessageDialog( null, "Balance as of " + 
				date.toString() + ": $" + balance );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "Invalid store number." );
			return;
		}
	}
}
