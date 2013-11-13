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


/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SearchResults extends JFrame
{
	// JDBC Driver and database URL
	static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String DATABASE_URL = "jdbc:odbc:final";
	
	// default query selects all rows from authors table
	//static final String DEFAULT_QUERY = "SELECT * FROM authors";
	
	private ResultSetTableModel tableModel;
	
	// create ResultSetTableModel and GUI
	public SearchResults( final String string )
	{
		super( "Displaying Search Results" );
		
		// create ResultSetTableModel and display database table
		try
		{
			// create TableModel for results of query SELECT * FROM authors	
			tableModel = new ResultSetTableModel( JDBC_DRIVER, DATABASE_URL,
				string );	
			
			// create JTable delegate for tableModel
			JTable resultTable = new JTable( tableModel );
			
			// place GUI components on content pane
			Container c = getContentPane();
			
			c.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			
			// set window size and display window		
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
