/*
 * Created on Nov 14, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class RetrievePartRequestEmployeeSKU
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String password = "";
	
	String pNumber = null, vNumber = null, pName = null, pCond = null, pDesc = null;
	
	double pCost = 0.0;
	int store = 0, pQuantity = 0, pCategory;
	int i = 0;
	public RetrievePartRequestEmployeeSKU( String sku, int storeNumber )
	{
		System.out.println( "Store number passed in from RPRESKU: " + storeNumber );
		String requestQuery = "SELECT pnumber, vnumber, category, name, cost, " +			"condition, description FROM PART WHERE pnumber LIKE '" + sku + "'";
		String requestQuantity = "SELECT quantity, store FROM INVENTORY WHERE pnumber " +
			"LIKE '" + sku + "' AND store LIKE '" + storeNumber + "'";
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
				pNumber = result.getString( "pnumber" );
				pName = result.getString( "name" );
				vNumber = result.getString( "vnumber" );
				pCategory = result.getInt( "category" );
				pCost = result.getDouble( "cost" );
				pCond = result.getString( "condition" );
				pDesc = result.getString( "description" );
				
				JOptionPane.showMessageDialog( null, "\nPart Number: " + pNumber +
					"\nPart Name: " + pName + "\nVehicle Number: " + vNumber +
					"\nCategory: " + pCategory + "\nCost: $" + pCost +
					"\nCondition: " + pCond + "\nDescription: " + pDesc );
			}
			while( result.next() )
			{
				//shouldn't be more than one due to unique SKU
			}
			if( result2.next() )
			{
				pQuantity = result2.getInt( "quantity" );
				store = result2.getInt( "store" );
				JOptionPane.showMessageDialog( null, "\nQuantity: " + 
					pQuantity + "\nStore: " + store );
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