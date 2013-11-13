/*
 * Created on Oct 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class BrowseTable extends JFrame
{
	static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String DATABASE_URL = "jdbc:odbc:final";
	
	private ResultSetTableModel tableModel;
	private JTextArea queryArea;
	
	public BrowseTable( final String query, String title )
	{
		super( title );
		
		try
		{
				
			tableModel = new ResultSetTableModel( JDBC_DRIVER, DATABASE_URL,
				query );
			JTable resultTable = new JTable( tableModel );
			Container c = getContentPane();
			c.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			
			setSize( 500, 250 );
			 setVisible( true );
			
		 }
		
		 // catch ClassNotFoundException thrown by
		 // ResultSetTableModel if database driver not found
		 catch( ClassNotFoundException classNotFound )
		 {
			 JOptionPane.showMessageDialog( null, 
				 "MS Access driver not found", "Driver Not Found",
				 JOptionPane.ERROR_MESSAGE );
				
			 System.exit( 1 );
		 }
		
		 // catch SQLException thrown by ResultSetTableModel
		 // if problems occure while setting up database
		 // connection and querying database
		 catch( SQLException sqlException )
		 {
			 JOptionPane.showMessageDialog( null, sqlException.getMessage(),
				 "Database Error", JOptionPane.ERROR_MESSAGE );
				
			 // ensure database connection is closed
			 tableModel.disconnectFromDatabase();
			
			 System.exit( 1 );
		 }
		
		 // dispose of window when user quits application
		 setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
	 }
		
}
	

