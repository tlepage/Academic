/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;

public class RemovePartRequestEmployeeSKU
{
	Connection con = null;
	Statement stmt = null;
	Statement stmt2 = null;
	
	String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	String url = "jdbc:odbc:final";
	String userName = "";
	String password = "";
	
	String pName = null , pBrand = null, pDesc = null, pNumber = null, 
		pType = null;
	
	double pCost = 0.0;
	int pQuantity = 0;
	boolean flag = true;
	private RetrievePartRequestEmployeeSKU retrieve;
	String realQuantity = null;
	String requestQuantity = null;
	
	public RemovePartRequestEmployeeSKU( String sku, String qString, int store  )
	{
		retrieve = new RetrievePartRequestEmployeeSKU( sku, store );
		
		System.out.println( retrieve.pNumber );
		System.out.println( retrieve.pQuantity );
		
		int quantity = Integer.parseInt( qString );
		
		if( quantity <= 0 || quantity > retrieve.pQuantity )
		{
			flag = false;	
			return;
		}
		else
		{
			
			String getAmount = 
				"SELECT quantity FROM INVENTORY WHERE pnumber LIKE '" + sku + 
					"' AND store LIKE '" + store + "'";
			System.out.println( "Info: " + getAmount );
					
			try
			{
				Class.forName( driver );
			}
			catch( Exception e )
			{
				flag = false;
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
				flag = false;
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
				ResultSet result = stmt2.executeQuery( getAmount );
			
			
				if( result.next() )
				{
					pQuantity = result.getInt( "quantity" );
					System.out.println( "\nQuantity: " + pQuantity );
					
					int q = Integer.parseInt( qString );
					int newQuantity = pQuantity - q;
					realQuantity = Integer.toString( newQuantity );
					
					requestQuantity = "UPDATE INVENTORY SET quantity = '" + 
						realQuantity + "' WHERE pnumber LIKE '" + retrieve.pNumber + 
						"' AND store LIKE '" + store + "'";
				}
				while( result.next() )
				{
				
				}
			}
			catch (Exception e) 
			{ 
				
				e.printStackTrace(); 
			}
			
			try
			{
				stmt.executeQuery( requestQuantity );
				JOptionPane.showMessageDialog( null, "Quantity changed to " +
					realQuantity + " successfully.", "Success", 
					JOptionPane.INFORMATION_MESSAGE );
			}
			catch( Exception e)
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
}
