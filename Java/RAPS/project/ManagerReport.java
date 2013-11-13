/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class ManagerReport extends JFrame
{
	public ManagerReport()
	{
		super( "Manager Report" );
		Connection con = null;
		Statement stmt = null, stmt3 = null;
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";	
		
		String employeeInfo = null, employeeOrderInfo = null,
			transactionInfo = null; 
		
		JTextArea area = new JTextArea( 20, 50 );
		area.setEditable(false);
		JScrollPane scrollPane = new JScrollPane( area, 
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		area.setText("------EMPLOYEE REPORT------" );
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
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
			String getOrderInfo = "SELECT * FROM EMPLOYEE_ORDER";
			ResultSet rs = stmt.executeQuery( getOrderInfo );
			if( rs.next() )
			{
				employeeOrderInfo = "\nLogin: " + rs.getString( "login" ) + "\nOrder Number: " + rs.getInt( "Onumber" ) +
					"\nCustomer: " + rs.getString( "clogin" ) + "\nAmount: " +
					rs.getDouble( "amount" ) + "\nPurchase Date: " + 
					rs.getString( "purchasedate" ) + "\nDelivery Date: " +
					rs.getString( "deliverydate" ) + 
					 "\nDelivery Type: " + rs.getString("Dtype") 
					+ "\nDelivery Company: " + rs.getString( "company" ) + 
					"\nDelivery Cost: " + rs.getDouble( "Dcost" ) + "\n";	
					
			}
			while( rs.next() )
			{
				employeeOrderInfo +=  "\nLogin: " + rs.getString( "login" ) + "\nOrder Number: " + rs.getInt( "Onumber" ) +
					"\nCustomer: " + rs.getString( "clogin" ) + "\nAmount: " +
					rs.getDouble( "amount" ) + "\nPurchase Date: " + 
					rs.getString( "purchasedate" ) + "\nDelivery Date: " +
					rs.getString( "deliverydate" ) +  "\nDelivery Type: " + rs.getString("Dtype") 
					+ "\nDelivery Company: " + rs.getString( "company" ) + 
					"\nDelivery Cost: " + rs.getDouble( "Dcost" ) + "\n";
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getTransaction = "SELECT * FROM EMPLOYEE_TRANSACTION";
				
			ResultSet rs3 = stmt3.executeQuery( getTransaction );
			
			if( rs3.next() )
			{
				transactionInfo = "\nLogin: " + rs3.getString( "login" ) + "\nTransaction: " + rs3.getInt( "transaction")
					+ "\nAmount: " + rs3.getDouble( "amount" ) + "\nDate: " +
					rs3.getString( "date" ) + "\nCash: " + rs3.getInt( "cash" ) +
					"\nCheck: " + rs3.getInt( "check" ) + "\nCCtype: " + 
					rs3.getString( "CCtype" ) + "\nCCnumber: " + 
					rs3.getString( "CCnumber" ) + "\n";
			}
			while( rs3.next() )
			{
				transactionInfo += "\nLogin: " + rs3.getString( "login" ) + "\nTransaction: " + rs3.getInt( "transaction")
					+ "\nAmount: " + rs3.getDouble( "amount" ) + "\nDate: " +
					rs3.getString( "date" ) + "\nCash: " + rs3.getInt( "cash" ) +
					"\nCheck: " + rs3.getInt( "check" ) + "\nCCtype: " + 
					rs3.getString( "CCtype" ) + "\nCCnumber: " + 
					rs3.getString( "CCnumber" ) + "\n";	
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
		area.append( "\n\n------EMPLOYEE ORDER INFORMATION------\n" );
		area.append( employeeOrderInfo );
		area.append( "\n------EMPLOYEE TRANSACTION INFORMATION------\n" );
		area.append( transactionInfo );
		
		container.add( scrollPane );
		
		setSize( 700, 400 );
		setVisible( true );
		show();
		
	}
}
