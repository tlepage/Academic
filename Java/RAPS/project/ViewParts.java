/*
 * Created on Nov 26, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewParts extends JFrame
{
	protected JButton okButton;
	protected ViewParts thisPage;
	
	public ViewParts( int storeNumber )
	{
		super( "Inventory-Store " + storeNumber );
	
		thisPage = this;
		Connection con = null;
		Statement stmt = null;
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";	
		
		String inventoryString = null;
		JTextArea area = new JTextArea( 20, 50 );
		area.setEditable(false);
		JScrollPane scrollPane = new JScrollPane( area, 
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		area.setText("------INVENTORY------\n\n" );
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		okButton = new JButton( "Ok" );
		
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
			String getInventory = "SELECT * FROM INVENTORY WHERE store LIKE '" +
				storeNumber + "'";
			ResultSet resultSet = stmt.executeQuery( getInventory );
			
			if( resultSet.next() )
			{
				inventoryString = "Part Number: " + resultSet.getInt("pnumber") +
					"\tQuantity: " + resultSet.getInt("quantity") + "\n";
			}
			while( resultSet.next() )
			{
				inventoryString += "Part Number: " + resultSet.getInt("pnumber") +
					"\tQuantity: " + resultSet.getInt("quantity") + "\n";
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
		area.append( inventoryString );
		
		container.add( scrollPane );
		container.add( okButton );
		
		okButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					thisPage.hide();
				}
			}
		);
		
		setSize( 700, 400 );
		setVisible( true );
		show();
		
	}
}
