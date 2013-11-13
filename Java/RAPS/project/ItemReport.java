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

public class ItemReport extends JFrame
{
	public ItemReport()
	{
		super( "Item Report" );
		
		Connection con = null;
		Statement stmt = null, stmt2 = null;
		
		String partInfo = null, transactionInfo = null;		
		
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		JTextArea area = new JTextArea( 20, 50 );
		area.setText("------ITEM REPORT------\n\n");
		
		JScrollPane scrollPane = new JScrollPane( area, 
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
			
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
			String getAllParts = "SELECT part, quantity, transaction FROM EMPLOYEE_PART";
				
			ResultSet rs = stmt.executeQuery( getAllParts );
				
			if( rs.next() )
			{
				partInfo = "Part: " + rs.getInt( "part" ) +
					"\nTransaction: " + rs.getInt( "transaction" ) +
					"\nQuantity: " + rs.getInt( "quantity" ) + "\n";
			}
			while( rs.next() )
			{
				partInfo += "\nPart: " + rs.getInt( "part") + 
					"\nTransaction: " + rs.getInt( "transaction" ) + 
					"\nQuantity: " + rs.getInt( "quantity" ) + "\n";
			} 
			area.append( partInfo );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		try
		{
			String getTransactions = "SELECT transaction, amount, cash, check," +				" CCtype, CCnumber, date FROM EMPLOYEE_TRANSACTION";
			ResultSet rs2 = stmt2.executeQuery( getTransactions );
				
			
			if( rs2.next() )
			{
				transactionInfo = "\n\nTransaction: " + rs2.getInt("transaction") 
					+ "\nAmount: $" + rs2.getDouble("amount")  
					+ "\nCash: " + rs2.getInt( "cash" ) + "\nCheck: " + rs2.getInt("check") 
					+ "\nCCtype: " + rs2.getString("CCtype") + "\nCCnumber: " + rs2.getString( "CCnumber") 
					+ "\nPurchase Date: " + rs2.getString( "date" ) + "\n";
			}
			while ( rs2.next() )
			{
				transactionInfo += "\n\nTransaction: " + rs2.getInt("transaction") 
					+ "\nAmount: $" + rs2.getDouble("amount") 
					+ "\nCash: " + rs2.getInt( "cash" ) + "\nCheck: " + rs2.getInt("check") 
					+ "\nCCtype: " + rs2.getString("CCtype") + "\nCCnumber: " + 
					rs2.getString( "CCnumber") + "\nPurchase Date: " + rs2.getString( "date" )
					+ "\n";
			}
			area.append( transactionInfo );
			
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
		area.setEditable( false );
		area.setWrapStyleWord( true );
		area.setLineWrap( true );
		container.add( scrollPane );
			
		setSize(700, 400 );
		setVisible(true);
		show();
		
	}		
}

