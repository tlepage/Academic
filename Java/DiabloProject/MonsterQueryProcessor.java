//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// MonsterQueryProcessor.java
//-----------------------------------------------------------------------------
/*-----------------------------------------------------------------------------

Class:      MonsterQueryProcessor
Purpose:    To handle the object storage and retrieval out of the database for 
			all monster queries that are generated
Extends:    N/A
Implements: DiabloDefines
Parameters: String representing the query to be processed
			String representing the type of monster to be queried

-----------------------------------------------------------------------------*/
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class MonsterQueryProcessor implements DiabloDefines
{
	protected Connection con;
	protected Statement stmt1, 
					    stmt2, 
					    stmt3;
	protected Vector monsterVector;
	protected MonsterEntity tempEntity;
	protected String monsterType;
		
//-----------------------------------------------------------------------------
// Function: monsterQueryProcessor()
// Purpose:  class constructor
// Input:    String representing the query
//           String representing the monster type
// Output:   N/A
//-----------------------------------------------------------------------------	
	public MonsterQueryProcessor( String query, String type )
	{
		// create vector to hold monster objects
		monsterVector = new Vector();
		
		// set the monster type
		monsterType = type;
		
		// execute the query
		executeSearch( query, type );
	}
//-----------------------------------------------------------------------------
// Function: executeSearch()
// Purpose:  to execute the queries
// Input:    String representing the query
//           String representing the monster type
// Output:   N/A
//-----------------------------------------------------------------------------	
	public void executeSearch( String query, String type )
	{
		// load database drivers and establish connection
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
		try
		{
			// execute query
			ResultSet rs = stmt1.executeQuery( query );
			
			// if result set returned something
			if( !rs.wasNull() )
			{
				// if monster type is "SuperUnique"
				if( type == "SuperUnique" )
				{
					// while there are more results
					while( rs.next() )
					{
						// create temp entity
						tempEntity = new MonsterEntity( type );
						tempEntity.level = rs.getInt( "Level" );
						tempEntity.experience = rs.getInt( "Experience" );
						tempEntity.defense = rs.getInt( "Defense" );
						tempEntity.percentToBlock = rs.getInt( "PercentToBlock" );
						tempEntity.damageResist = rs.getInt( "DamageResist" );
						tempEntity.magicResist = rs.getInt( "MagicResist" );
						tempEntity.coldResist = rs.getInt ( "ColdResist" );
						tempEntity.fireResist = rs.getInt( "FireResist" );
						tempEntity.lightningResist = rs.getInt ( "LightningResist" );
						tempEntity.poisonResist = rs.getInt( "PoisonResist" );
						tempEntity.drainEffectiveness = rs.getInt( "DrainEffectiveness" );
						tempEntity.chillEffectiveness = rs.getInt( "ChillEffectiveness" );
						tempEntity.name = rs.getString( "Name" );
						tempEntity.difficultyLevel = rs.getString( "DifficultyLevel" );
						tempEntity.monsterClass = rs.getString( "MonsterClass" );
						tempEntity.monsterType = rs.getString( "MonsterType" );
						tempEntity.HP = rs.getString( "HP" );
						tempEntity.meleeAttack1 = rs.getString( "MeleeAttack" );
						tempEntity.meleeAttack2 = rs.getString( "MeleeAttack2" );
						tempEntity.attackRating1 = rs.getString( "AttackRating1" );
						tempEntity.attackRating2 = rs.getString( "AttackRating2" );
						tempEntity.immunities = rs.getString( "Immunities" );
						tempEntity.imageLocation = rs.getString( "Image" );
						tempEntity.monsterID = rs.getString( "MonsterID" );	
						tempEntity.abilities = rs.getString( "Abilities" );
						tempEntity.location = rs.getString( "Location" );
						tempEntity.hits = rs.getInt( "Hits" );
						tempEntity.total = rs.getInt( "Total" );
						tempEntity.userRating = rs.getDouble( "UserRating" );
						monsterVector.add( tempEntity );		
					}
				}
				
				// if type is "Unique"
				else if( type == "Unique" )
				{
					// while there are more results
					while( rs.next() )
					{
						// create temporary entity
						tempEntity = new MonsterEntity( type );
						tempEntity.monsterID = rs.getString( "MonsterID" );
						tempEntity.name = rs.getString( "Name" );
						tempEntity.location = rs.getString( "Location" );
						tempEntity.abilities = rs.getString( "Abilities" );
						tempEntity.monsterClass = rs.getString ( "MonsterClass" );
						tempEntity.monsterType = rs.getString( "MonsterType" );
						tempEntity.imageLocation = rs.getString( "Image" );
						tempEntity.hits = rs.getInt( "Hits" );
						tempEntity.total = rs.getInt( "Total" );
						tempEntity.userRating = rs.getDouble( "UserRating" );
						monsterVector.add( tempEntity );
						
					}
				}
			}
		}
		catch( SQLException e )
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, 
				"Database produced no result on this search." );
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
	}

//-----------------------------------------------------------------------------
// Function: getEntity()
// Purpose:  to return the temporary entity created by this class
// Input:    an integer vector index
// Output:   a MonsterEntity object
//-----------------------------------------------------------------------------
	public MonsterEntity getEntity( int index )
	{
		MonsterEntity tempEntity;
		
		// get the object out of the vector
		tempEntity = ( MonsterEntity )monsterVector.get(index);
		
		return tempEntity;
	}

//-----------------------------------------------------------------------------
// Function: getMonsterInformation()
// Purpose:  to return the String containing the entity information
// Input:    an integer vector index
// Output:   a String representing the entity information
//-----------------------------------------------------------------------------	
	public String getMonsterInformation( int index )
	{
		return getEntity(index).monsterInformation() + "\n";
	}

//-----------------------------------------------------------------------------
// Function: getVectorSize()
// Purpose:  to return the vector size 
// Input:    N/A
// Output:   an integer representing the vector size
//-----------------------------------------------------------------------------	
	public int getVectorSize()
	{
		return monsterVector.size();
	}

//-----------------------------------------------------------------------------
// Function: getEntityName()
// Purpose:  to return the name of the entity
// Input:    an integer vector index
// Output:   a String representing the name of the entity
//-----------------------------------------------------------------------------	
	public String getEntityName( int index )
	{
		return getEntity(index).name;
	}

//-----------------------------------------------------------------------------
// Function: getEntityDifficultyLevel()
// Purpose:  to return the difficulty level of the entity
// Input:    an integer vector index
// Output:   a String representing the difficulty level of the entity
//-----------------------------------------------------------------------------	
	public String getEntityDifficultyLevel( int index )
	{
		return getEntity(index).difficultyLevel;
	}

//-----------------------------------------------------------------------------
// Function: getEntityImage()
// Purpose:  to return the file name of the entity image
// Input:    an integer vector index
// Output:   a String representing the file name of the entity
//-----------------------------------------------------------------------------
	public String getEntityImage( int index )
	{
		return getEntity(index).imageLocation;
	}
}
