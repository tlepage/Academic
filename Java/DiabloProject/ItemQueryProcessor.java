//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// ItemQueryProcessor.java
//-----------------------------------------------------------------------------
/*-----------------------------------------------------------------------------

Class:      ItemQueryProcessor
Purpose:    To handle the object storage and retrieval out of the database for 
			all item queries that are generated
Extends:    N/A
Implements: DiabloDefines
Parameters: String representing the query to be processed
			String representing the type of item to be queried

-----------------------------------------------------------------------------*/
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class ItemQueryProcessor implements DiabloDefines
{
	private Connection con;
	private Statement stmt1, 
					  stmt2, 
					  stmt3;
	private Vector itemVector;
	protected ItemEntity tempEntity;
	protected String itemType;
	
//-----------------------------------------------------------------------------
// Function: ItemQueryProcessor()
// Purpose:  class constructor
// Input:    String representing the query
//           String representing item type
// Output:   N/A
//-----------------------------------------------------------------------------
	public ItemQueryProcessor( String query, String type )
	{
		itemVector = new Vector();
		itemType = type;
		executeSearch( query, type );
	}

//-----------------------------------------------------------------------------
// Function: executeSearch()
// Purpose:  to execute the database query
// Input:   String representing the query
//          String representing item type
// Output:  N/A
//-----------------------------------------------------------------------------
	public void executeSearch( String query, String type )
	{
		// load database drivers and connect to the database
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
		
		// execute the query
		try
		{
			
			ResultSet rs = stmt1.executeQuery( query );
			
			// if result set is not null
			if( !rs.wasNull() )
			{
				// if item type is armor
				if( type == "Armor" )
				{
					// while there are results
					while( rs.next() )
					{
						// create item object
						tempEntity = new ItemEntity( type );
						
						// get armor type
						tempEntity.armorType = rs.getString( "ArmorType" );
						
						// if type is belt
						if( tempEntity.armorType == "Belt" )
						{
							tempEntity.baseDefense = rs.getInt( "BaseDefense" );
							tempEntity.defense = rs.getInt( "Defense" );
							tempEntity.durability = rs.getString( "Durability" );
							tempEntity.name = rs.getString( "Name" );
							tempEntity.numBoxes = rs.getString( "NumBoxes" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString( "RequiredStrength" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// if type is boot
						else if( tempEntity.armorType == "Boot" )
						{
							tempEntity.baseDefense = rs.getInt( "BaseDefense" );
							tempEntity.defense = rs.getInt( "Defense" );
							tempEntity.durability = rs.getString( "Durability" );
							tempEntity.name = rs.getString( "Name" );
							tempEntity.kickDamage = rs.getString( "AssassinKickDamage" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString( "RequiredStrength" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// if type is shield
						else if( tempEntity.armorType == "Shield" )
						{
							tempEntity.baseDefense = rs.getInt( "BaseDefense" );
							tempEntity.defense = rs.getInt( "Defense" );
							tempEntity.durability = rs.getString( "Durability" );
							tempEntity.name = rs.getString( "Name" );
							tempEntity.chanceToBlock = rs.getString( "ChanceToBlock" );
							tempEntity.smiteDamage = rs.getString( "PaladinSmiteDamage" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString( "RequiredStrength" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// if type is other than listed above
						else
						{
							tempEntity.baseDefense = rs.getInt( "BaseDefense" );
							tempEntity.defense = rs.getInt( "Defense" );
							tempEntity.durability = rs.getString( "Durability" );
							tempEntity.name = rs.getString( "Name" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString( "RequiredStrength" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// add item object to vector
						itemVector.add( tempEntity );		
					}
				}
				
				// if item is a weapon
				else if( type == "Weapon" )
				{
					// while there are results left
					while( rs.next() )
					{
						// create item object
						tempEntity = new ItemEntity( type );
						
						// get weapon type
						tempEntity.weaponType = rs.getString( "WeaponType" );
						
						// if type is javelin
						if( tempEntity.weaponType == "Javelin" )
						{
							tempEntity.name = rs.getString( "Name" );
							tempEntity.requiredDexterity = rs.getString( "RequiredDexterity" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString ( "RequiredStrength" );
							tempEntity.oneHandDamage = rs.getDouble( "OneHandDmg" );
							tempEntity.twoHandDamage = rs.getDouble( "TwoHandDmg" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.throwDamage = rs.getString( "ThrowDamage" );
							tempEntity.maxStack = rs.getString( "MaxStack" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// if type is throwing weapon
						else if( tempEntity.weaponType == "ThrowingWeapon" )
						{
							tempEntity.name = rs.getString( "Name" );
							tempEntity.requiredDexterity = rs.getString( "RequiredDexterity" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString ( "RequiredStrength" );
							tempEntity.oneHandDamage = rs.getDouble( "OneHandDmg" );
							tempEntity.twoHandDamage = rs.getDouble( "TwoHandDmg" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.throwDamage = rs.getString( "ThrowDamage" );
							tempEntity.maxStack = rs.getString( "MaxStack" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// if type is other than above
						else
						{
							tempEntity.name = rs.getString( "Name" );
							tempEntity.requiredDexterity = rs.getString( "RequiredDexterity" );
							tempEntity.requiredLevel = rs.getString( "RequiredLevel" );
							tempEntity.requiredStrength = rs.getString ( "RequiredStrength" );
							tempEntity.oneHandDamage = rs.getDouble( "OneHandDmg" );
							tempEntity.twoHandDamage = rs.getDouble( "TwoHandDmg" );
							tempEntity.specificType = rs.getString( "SpecificType" );
							tempEntity.properties[0] = rs.getString( "Property1" );
							tempEntity.properties[1] = rs.getString( "Property2" );
							tempEntity.properties[2] = rs.getString( "Property3" );
							tempEntity.properties[3] = rs.getString( "Property4" );
							tempEntity.properties[4] = rs.getString( "Property5" );
							tempEntity.properties[5] = rs.getString( "Property6" );
							tempEntity.properties[6] = rs.getString( "Property7" );
							tempEntity.properties[7] = rs.getString( "Property8" );
							tempEntity.properties[8] = rs.getString( "Property9" );
							tempEntity.imageLocation = rs.getString( "Image" );
							tempEntity.rating = rs.getDouble( "UserRating" );
							tempEntity.hits = rs.getInt( "Hits" );
							tempEntity.total = rs.getInt( "Total" );
							tempEntity.uniqueLevel = rs.getString( "UniqueLevel" );
							tempEntity.itemID = rs.getString( "ItemID" );
						}
						
						// add to item vector
						itemVector.add( tempEntity );
					}
				}
			}
			else
			{
				// Display to user that there are no results
				JOptionPane.showMessageDialog( null, "No results." );
			}
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{ 
				stmt1.close(); 
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
// Purpose:  to get an item out of the item vector
// Input:    an integer vector index
// Output:   an ItemEntity object
//-----------------------------------------------------------------------------	
	public ItemEntity getEntity( int index )
	{
		ItemEntity tempEntity;
	
		tempEntity = ( ItemEntity )itemVector.get(index);
		return tempEntity;
	}

//-----------------------------------------------------------------------------
// Function: getItemInformation()
// Purpose:  to get the item information string from an ItemEntity object
// Input:    an integer vector index
// Output:   a String representing the information string
//-----------------------------------------------------------------------------
	public String getItemInformation( int index )
	{
		return getEntity(index).itemInformation() + "\n";
	}

//-----------------------------------------------------------------------------
// Function: getVectorSize()
// Purpose:  to get the size of the vector
// Input:    N/A
// Output:   N/A
//-----------------------------------------------------------------------------
	public int getVectorSize()
	{
		return itemVector.size();
	}

//-----------------------------------------------------------------------------
// Function: getEntityName()
// Purpose:  to get the name of the item in the vector
// Input:    an integer vector index
// Output:   a String representing the name of the item
//-----------------------------------------------------------------------------
	public String getEntityName( int index )
	{
		return getEntity(index).name;
	}

//-----------------------------------------------------------------------------
// Function: getEntityImage()
// Purpose:  to get the name of the image file for an item
// Input:    an integer vector index
// Output:   a String representing the name of the image file
//-----------------------------------------------------------------------------
	public String getEntityImage( int index )
	{
		return getEntity(index).imageLocation;
	}

//-----------------------------------------------------------------------------
// Function: getEntityWeaponType()
// Purpose:  to get the weapon type of an item
// Input:    an integer vector index
// Output:   a String representing the type of weapon
//-----------------------------------------------------------------------------
	public String getEntityWeaponType( int index )
	{
		return getEntity(index).weaponType;
	}

//-----------------------------------------------------------------------------
// Function: getEntityArmorType()
// Purpose:  to get the armor type of an item
// Input:    an integer vector index
// Output:   a String representing the type of armor
//-----------------------------------------------------------------------------	
	public String getEntityArmorType( int index )
	{
		return getEntity(index).armorType ;
	}

//-----------------------------------------------------------------------------
// Function: getHits()
// Purpose:  to get the number of hits an item has acquired
// Input:    an integer vector index
// Output:   an integer representing the number of hits
//-----------------------------------------------------------------------------
	public int getHits( int index )
	{
		return getEntity(index).hits;
	}

//-----------------------------------------------------------------------------
// Function: isArmor() 
// Purpose:  to get whether the item is armor or not
// Input:    an integer vector index
// Output:   a boolean representing armor status
//-----------------------------------------------------------------------------
	public boolean isArmor( int index )
	{
		boolean rt = false;
		
		if( getEntity(index).armorType == null )
			rt = false;
		else 
			rt = true;
		
		return rt;
	}
}
