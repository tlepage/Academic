/*
 * Created on Oct 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class DisplayQueryResults extends JFrame implements DiabloDefines
{
	private ResultSetTableModel tableModel;
	private JTextArea queryArea;
	static final String DEFAULT_QUERY = "SELECT * FROM ARMOR";
	// create ResultSetTableModel and GUI
	public DisplayQueryResults()
	{
		super( "Displaying Query Results" );
		
		// create ResultSetTableModel and display database table
		try
		{
			// create TableModel for results of query SELECT * FROM authors	
			tableModel = new ResultSetTableModel( DRIVER, URL,
				DEFAULT_QUERY );
			
			// set up JTextArea in which user types queries
			queryArea = new JTextArea( DEFAULT_QUERY, 3, 100 );
			queryArea.setWrapStyleWord( true );
			queryArea.setLineWrap( true );
			
			
			JScrollPane scrollPane = new JScrollPane( queryArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
			
			// set up JButton for submitting queries	
			JButton submitButton = new JButton( "Submit Query" );
			
			// create Box to manage placement of queryArea and 
			// submitButton in GUI
			Box box = Box.createHorizontalBox();
			box.add( scrollPane );
			box.add( submitButton );
			
			// create JTable delegate for tableModel
			JTable resultTable = new JTable( tableModel );
			
			// place GUI components on content pane
			Container c = getContentPane();
			c.add( box, BorderLayout.NORTH );
			c.add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			
			// create event listener for submitButton
			submitButton.addActionListener(
			
				new ActionListener()
				{
					// pass query to table model
					public void actionPerformed( ActionEvent event)
					{
						// perform a new query
						try
						{
							tableModel.setQuery( queryArea.getText() );
						}
						
						// catch SQLExceptions when performing a new query
						catch( SQLException sqlException )
						{
							JOptionPane.showMessageDialog( null,
								sqlException.getMessage(), "Database Error",
								JOptionPane.ERROR_MESSAGE );
							// try to recover from invalid user query
							// by executing default query
							try
							{
								tableModel.setQuery( DEFAULT_QUERY );
								queryArea.setText( queryArea.getText() );
							}
							
							// catch SQLException when performing default query
							catch( SQLException sqlException2 )
							{
								JOptionPane.showMessageDialog( null,
								  sqlException2.getMessage(), "Database Error",
								  JOptionPane.ERROR_MESSAGE );
								
								// ensure database connection is closed
							 	tableModel.disconnectFromDatabase();
							 	
							 	System.exit( 1 ); 	// terminate application
							}		
						}
					}
				}
			);
			
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
	
	public static void main( String args[] )
	{
		DisplayQueryResults app = new DisplayQueryResults();
	}
}
