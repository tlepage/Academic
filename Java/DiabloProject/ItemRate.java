//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// ItemRate.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      ItemRate
Purpose:    To provide the functionality associated with rating an item
Extends:    N/A
Implements: DiabloDefines
Parameters: ItemEntity object representing item to be rated

-----------------------------------------------------------------------------*/
import java.sql.*;
import javax.swing.*;

public class ItemRate implements DiabloDefines
{
	protected Connection con;
	
	protected Statement stmt1, 
					  stmt2,
					  stmt3;
	
	protected double currentRating,
					 updatedRating,
					 hits;
	
	protected int newRating, 
				  currentTotal, 
				  newTotal;
	
	protected String userRating,
					 itemID;
//-----------------------------------------------------------------------------
// Function: ItemRate()
// Purpose:  class constructor
// Input:    an ItemEntity object
// Output:   N/A
//-----------------------------------------------------------------------------				  
	public ItemRate( ItemEntity currentItem )
	{
		// get necessary rating information from object
		currentRating = currentItem.rating;
		currentTotal = currentItem.total;
		hits = currentItem.hits + 1;
		itemID = currentItem.itemID;
		
		// get rating from user
		try
		{
			userRating = JOptionPane.showInputDialog( 
				"Please enter a rating (0 to 5): " );
		}
		catch( NumberFormatException e )
		{
			JOptionPane.showMessageDialog( null, "Please try again." );
			return;
		}
		
		// convert new rating
		newRating = Integer.parseInt( userRating );
		
		// if rating range was insufficient, adjust
		if( newRating > 5 )newRating = 5;
		else if( newRating < 0 )newRating = 0;
		
		// create updated rating
		updatedRating = (currentTotal + newRating) / hits;
		
		// create updated hit total
		newTotal = currentTotal + newRating;
		
		// call updateRating
		updateRating();
	}

//-----------------------------------------------------------------------------
// Function: updateRating()
// Purpose:  to access the database and change the current rating of an item
// Input:    N/A
// Output:   N/A
//-----------------------------------------------------------------------------
	public void updateRating()
	{
		// load database drivers and create a connection
		try
		{
			Class.forName( DRIVER );
		}
		catch( Exception e )
		{
	
			System.err.println(
				"Failed to load current driver.");
			return;
		}
		try
		{
			con = DriverManager.getConnection( URL, USERNAME, PASSWORD );
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();
		}
		catch( Exception e )
		{
			System.err.println( "problems connecting to " + URL + ":" );
			System.err.println( e.getMessage() );
	
			if( con != null)
			{
				try 
				{ 
					con.close(); 
				}
				catch( Exception e2 ) 
				{
				}
			}
			return;
		}
		
		// Query to update item rating
		String updateUserRating = "UPDATE ITEM SET UserRating = " +
			updatedRating + " WHERE ItemID = '" + itemID + "'";
		
		// Query to update item hits
		String updateHits = "UPDATE ITEM SET Hits = " + hits + 
			" WHERE ItemID = '" + itemID + "'";
		
		// Query to update total 
		String updateTotal = "UPDATE ITEM SET Total = " + newTotal +
			" WHERE ItemID = '" + itemID + "'";
			
		// execute query to update user rating
		try
		{
			stmt1.executeQuery( updateUserRating );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		// execute query to update hits
		try
		{
			stmt2.executeQuery( updateHits );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		// execute query to update total
		try
		{
			stmt3.executeQuery( updateTotal );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{ 
				stmt1.close(); 
				stmt2.close();
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
		
		// if everything went OK, display this
		JOptionPane.showMessageDialog( null, "Thank you" );
	}
}
