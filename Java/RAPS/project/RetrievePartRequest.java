/*
 * Created on Nov 9, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;

public class RetrievePartRequest 
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:setest";
	String userName = "";
	String password = "";
	
	String pName = null , pBrand = null, pDesc = null, pNumber = null;
	
	String requestQuery = "SELECT pnumber, name, type, brand, description," +		" cost FROM PART WHERE pnumber LIKE '1234'";
	String requestQuantity = "SELECT quanity FROM INVENTORY WHERE pnumber " +		"LIKE '1234'";
	
	double pCost = 0.0;
	int pQuantity = 0;
	
	public RetrievePartRequest()
	{
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
			ResultSet result = stmt.executeQuery( requestQuery );
			ResultSet result2 = stmt2.executeQuery( requestQuantity );
			if( result.next() )
			{
				pName = result.getString("name");
				pBrand = result.getString("brand");
				pDesc = result.getString("description");
				pNumber = result.getString("pnumber");
				pCost = result.getDouble("cost");
				
				System.out.print( "Part is: " + pName + "\nBrand: " + pBrand
					+ "\nDescription: " + pDesc + "\nNumber: " + 
					pNumber + "\nCost: " + pCost );
			}
			while( result.next() )
			{
				//shouldn't be more than one due to unique SKU
			}
			if( result2.next() )
			{
				pQuantity = result2.getInt( "quanity" );
				System.out.println( "\nQuantity: " + pQuantity );
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
