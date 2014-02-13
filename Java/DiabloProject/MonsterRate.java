//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// MonsterRate.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      MonsterRate
Purpose:    To provide the functionality associated with rating a monster
Extends:    N/A
Implements: DiabloDefines
Parameters: String representing the type class of the monster

-----------------------------------------------------------------------------*/
import java.sql.*;
import javax.swing.*;

public class MonsterRate implements DiabloDefines
{
	private Connection con;
	private Statement stmt1, 
					  stmt2,
					  stmt3;
	protected double currentRating,
					 updatedRating,
					 hits;
	protected int newRating, 
	    		  currentTotal, 
	    		  newTotal;
	protected String userRating,
					 monsterID;

//-----------------------------------------------------------------------------
// Function: MonsterRate()
// Purpose:  class constructor
// Input:    MonsterEntity object
// Output:   N/A
//-----------------------------------------------------------------------------			  
	public MonsterRate( MonsterEntity currentMonster )
	{
		// get necessary rating information
		currentRating = currentMonster.userRating;
		currentTotal = currentMonster.total;
		hits = currentMonster.hits + 1;
		monsterID = currentMonster.monsterID;
		
		System.out.println( "From rate: " + monsterID );
		// get rating from the user
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
		
		// call updateRating()
		updateRating();
	}

//-----------------------------------------------------------------------------
// Function: updateRating()
// Purpose:  to access the database and change the current rating of a monster
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
	
		// Query to update monster rating
		String updateUserRating = "UPDATE MONSTER SET UserRating = " +
			updatedRating + " WHERE MonsterID = '" + monsterID + "'";
		
		// Query to update monster hits
		String updateHits = "UPDATE MONSTER SET Hits = " + hits + 
			" WHERE MonsterID = '" + monsterID + "'";
		
		// Query to update monster total
		String updateTotal = "UPDATE MONSTER SET Total = " + newTotal +
			" WHERE MonsterID = '" + monsterID + "'";
		
		// Execute query to update user rating
		try
		{
			stmt1.executeQuery( updateUserRating );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	
		// Execute query to update hits
		try
		{
			stmt2.executeQuery( updateHits );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		// Execute query to update total
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
