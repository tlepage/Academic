/*
 * Created on Oct 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
import java.sql.*;
import javax.swing.table.*;
import javax.swing.*;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
// Must add one to column and rowl due to ResultSet rows and columns
// starting from one and JTable rows and columns starting from zero
public class ResultSetTableModel extends AbstractTableModel
{
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	
	// database connection status flag
	private boolean connectedToDatabase = false;
	
	// initialize the resultSet and obtain its metadata object;
	// determine number of rows
	public ResultSetTableModel( String driver, String url,
		String query ) throws SQLException, ClassNotFoundException
	{
		// load database driver class
		Class.forName( driver );
		
		// connect to database
		connection = DriverManager.getConnection( url );
		
		// create Statement to query database
		statement = connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY );
		
		// update database connection status	
		connectedToDatabase = true;
		
		// set query and execute it
		setQuery( query );
	}
	
	// get Class that represents the column type
	public Class getColumnClass( int column ) throws IllegalStateException
	{
		// ensure that database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to Database" );
			
		// determine Java class of column
		try
		{
			String className = metaData.getColumnClassName( column + 1 );
			
			// return Class object that represents className
			return Class.forName( className );
		}
		
		// catch SQLExceptions and ClassNotFound Exceptions
		catch( Exception exception )
		{
			exception.printStackTrace();
		}	
		
		// if problems occur, assume type Object
		return Object.class;
	}
	
	// get number of columns in ResultSet
	public int getColumnCount() throws IllegalStateException
	{
		// ensure that database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to Database" );
		
		// determine number of columns	
		try
		{
			return metaData.getColumnCount();
		}
		
		// catch SQLExceptions and print error message
		catch( SQLException sqlException )
		{
			sqlException.printStackTrace();
		}
		
		// if problems occur, return 0 for number of columns
		return 0;
	}
	
	// get name of a particular column in ResultSet
	public String getColumnName( int column ) throws IllegalStateException
	{
		// ensure that database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to Database" );
		
		// determine column name
		try
		{
			return metaData.getColumnName( column + 1 );
		}
		
		// catch SQLExceptions and print error message
		catch( SQLException sqlException )
		{
			sqlException.printStackTrace();
		}
		
		// if problems, return empty string for column name
		return "";
	}
	
	// return number of rows in ResultSet
	public int getRowCount() throws IllegalStateException
	{
		// ensure database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to Database" );
		
		return numberOfRows;
	}
	
	// obtain value in particular row and column
	public Object getValueAt( int row, int column ) 
		throws IllegalStateException
	{
		// ensure database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to Database" );
		
		// obtain a value at specified ResultSet row and column	
		try
		{
			resultSet.absolute( row + 1 );
			
			return resultSet.getObject( column + 1 );
		}
		
		// catch SQLExceptions and print error message
		catch( SQLException sqlException )
		{
			sqlException.printStackTrace();
		}
		
		// if problems, return empty string object
		return "";
	}
	
	// set new database query string
	public void setQuery( String query )
		throws SQLException, IllegalStateException
	{
		// ensure that database is connected
		if( !connectedToDatabase )
			throw new IllegalStateException( "Not Connected to Database" );
		
		// specify database connection is available
		resultSet = statement.executeQuery( query );
		
		// obtain metadata for ResultSet
		metaData = resultSet.getMetaData();
	
		// determine number of rows in ResultSet
		resultSet.last();					// move to last row
		numberOfRows = resultSet.getRow();	// get row number
		
		if( resultSet.last() == false )
			JOptionPane.showMessageDialog( null, "Invalid Search" );
		
		// notify JTable that model has changed
		fireTableStructureChanged();
	
	}
	
	// close Statement and Connection
	public void disconnectFromDatabase()
	{
		// try to close Statement and Connection
		try
		{
			statement.close();
			connection.close();
		}
		
		// catch SQLExceptions and print error message
		catch( SQLException sqlException )
		{
			sqlException.printStackTrace();
		}
		
		// update database connection status flag
		finally
		{
			connectedToDatabase = false;
		}
	}
}
