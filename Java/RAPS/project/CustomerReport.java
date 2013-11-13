/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CustomerReport extends JFrame
{
	public CustomerReport( Customer myCustomer )
	{
		super( "Customer Report" );
		Connection con = null;
		Statement stmt = null, stmt2 = null;
				
		String transaction = null;
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		JTextArea area = new JTextArea( 20, 50 );
		area.setEditable( false );
		area.setText("------CUSTOMER REPORT------\n\n");
		
		String customerInfo = "\nName: " + myCustomer.name + "\nLogin: " +
			myCustomer.login + "\nAddress: " + myCustomer.address + "\nPhone: " +
			myCustomer.phone + "\nFax: " + myCustomer.fax + "\nCCtype: " +
			myCustomer.CCtype + "\nCCnumber: " + myCustomer.CCnumber;
		
		JScrollPane scrollPane = new JScrollPane( area, 
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
			
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		area.append( "\n\n------CUSTOMER INfORMATION------\n" );
		area.append( customerInfo );
		
		DecimalFormat twoDigits = new DecimalFormat( "0.00" );
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
			String getTransactionHistory = "SELECT * FROM CUSTOMER_DELIVERY WHERE "
				+ "login LIKE '" + myCustomer.login + "'";
			System.out.println( getTransactionHistory );
			ResultSet rs = stmt.executeQuery( getTransactionHistory );
			
			if( rs.next() )
			{
				transaction = "\nOrder Number: " + rs.getInt( "Onumber" ) +
					"\nPurchase Date: " + rs.getString( "Purchasedate" ) +
					"\nDelivery Date: " + rs.getString( "Deliverydate" ) +
					"\nAmount: $" + twoDigits.format(rs.getDouble( "amount")) + 
					"\nCash: " + rs.getInt( "cash" ) +
					"\nCredit Card Type: " + rs.getString( "CCtype" ) +
					"\nCredit Card Number: " + rs.getString( "CCnumber" ) +
					"\nDelivery Type: " + rs.getString( "Dtype") +
					"\nDelivery Company: " + rs.getString( "company" ) +
					"\nDelivery Cost: $" + rs.getDouble( "Dcost" ) + "\n";
			}
			while( rs.next() )
			{
				transaction += "\nOrder Number: " + rs.getInt( "Onumber" ) +
					"\nPurchase Date: " + rs.getString( "Purchasedate" ) +
					"\nDelivery Date: " + rs.getString( "Deliverydate" ) +
					"\nAmount: $" + twoDigits.format(rs.getDouble( "amount")) + 
					"\nCash: " + rs.getInt( "cash" ) +
					"\nCredit Card Type: " + rs.getString( "CCtype" ) +
					"\nCredit Card Number: " + rs.getString( "CCnumber" ) +
					"\nDelivery Type: " + rs.getString( "Dtype") +
					"\nDelivery Company: " + rs.getString( "company" ) +
					"\nDelivery Cost: $" + rs.getDouble( "Dcost" ) +  "\n";
			}
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
		
		area.append( "\n\n------TRANSACTION HISTORY------\n\n" );
		area.append( transaction );
		
		container.add( scrollPane );
		
		setSize( 700, 400 );
		setVisible( true );
	
	}
}
