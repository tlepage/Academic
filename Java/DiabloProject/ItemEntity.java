//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// ItemEntity.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      ItemEntity
Purpose:    To store and provide all functionality for item objects
Extends:    N/A
Implements: DiabloDefines
Parameters: String representing the type of item 

-----------------------------------------------------------------------------*/
import java.text.DecimalFormat;

public class ItemEntity
{
	protected String name,
					 uniqueLevel,
					 weaponType,
					 armorType,
					 specificType,
					 properties[],
					 requiredStrength,
 					 requiredLevel,
 					 requiredDexterity,
					 throwDamage,
					 maxStack,
					 durability,
					 numBoxes,
					 kickDamage,
					 chanceToBlock,
					 smiteDamage,
					 category,
					 imageLocation,
					 itemID;
	
	protected double rating,
					 oneHandDamage,
					 twoHandDamage;
					 
	protected int hits, 
				  total,
				  defense,
				  baseDefense;
	
	protected DecimalFormat twoDigits;

//-----------------------------------------------------------------------------
// Function: ItemEntity()
// Purpose:  class constructor
// Input:    String representing item type
// Output:   N/A
//-----------------------------------------------------------------------------
	public ItemEntity( String type )
	{
		twoDigits = new DecimalFormat( "0.00" );
		category = type;
		properties = new String[9];
		
		for( int i = 0; i < 9; i++ )
		{
			properties[i] = null;
		}
	}

//-----------------------------------------------------------------------------
// Function: itemInformation()
// Purpose:  to create a String which contains all item entity information
// Input:    N/A
// Output:   a String representing item information
//-----------------------------------------------------------------------------
	public String itemInformation()
	{
		String infoString = null;
		
		// if item type is "Armor"
		if( category == "Armor" )
		{
			// if specific type is Boot
			if( specificType == "Boot" )
			{
				infoString = "Name: " + name + 
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + armorType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nDurability: " + durability +
					"\nBase Defense: " + baseDefense + "\nDefense: " +
					defense + "\nAssassin Kick Damage: " + kickDamage +
					"\nRating: " + twoDigits.format(rating) + "\nHits: " + hits + 
					"\nProperties:\n\n"; 
					
					for( int i = 0; i < 9; i++ )
					{
						if( properties[i] != null )
						{
							infoString += properties[i] + "\n";
						}	
					}
					 
			}
			
			// if specific type is Belt
			else if( category == "Belt" )
			{
				infoString = "Name: " + name + 
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + armorType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nDurability: " + durability +
					"\nBase Defense: " + baseDefense + "\nDefense: " +
					defense + "\nNumber of Boxes: " + numBoxes +
					"\nRating: " + twoDigits.format(rating) + "\nHits: " + hits +
					"\nProperties:\n\n"; 
	
					for( int i = 0; i < 9; i++ )
					{
						if( properties[i] != null )
						{
							infoString += properties[i] + "\n";
						}	
					}
			}
			
			// if specific type is Shield
			else if( category == "Shield" )
			{
				infoString = "Name: " + name +  
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + armorType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nDurability: " + durability +
					"\nBase Defense: " + baseDefense + "\nDefense: " +
					defense + "\nChance to Block: " + chanceToBlock +
					"\nPaladin Smite Damage: " + smiteDamage +
					"\nRating: " + twoDigits.format(rating) + "\nHits: " + hits +
					"\nProperties:\n\n"; 

					for( int i = 0; i < 9; i++ )
					{
						if( properties[i] != null )
						{
							infoString += properties[i] + "\n";
						}	
					}
			}
			
			// if type is other than above
			else
			{
				infoString = "Name: " + name + 
					"\nUnique Level: " + uniqueLevel +	
					"\nClass: " + armorType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nDurability: " + durability +
					"\nBase Defense: " + baseDefense + "\nDefense: " +
					defense + "\nRating: " + twoDigits.format(rating) + "\nHits: " + hits +
					"\nProperties:\n\n"; 

					for( int i = 0; i < 9; i++ )
					{
						if( properties[i] != null )
						{
							infoString += properties[i] + "\n";
						}	
					}
			}
		}
		
		// if item is a weapon
		else if( category == "Weapon" )
		{
			
			// if specific type is a javelin
			if( specificType == "Javelin" )
			{
				infoString = "Name: " + name +  
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + weaponType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nRequired Dexterity: " + requiredDexterity +
					"\nOne Hand Damage: " + oneHandDamage + 
					"\nThrow Damage: " + throwDamage +
					"\nMax Stack: " + maxStack + "\nRating: " + 
					twoDigits.format(rating) +
					"\nHits: " + hits + "\nProperties: \n\n";
					
				for( int i = 0; i < 9; i++ )
				{
					if( properties[i] != null )
					{
						infoString += properties[i] + "\n";
					}	
				}
			}
			
			// if specific type is a throwing weapon
			else if( specificType == "ThrowingWeapon" )
			{
				infoString = "Name: " + name + 
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + weaponType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nRequired Dexterity: " + requiredDexterity +
					"\nOne Hand Damage: " + oneHandDamage + 
					"\nThrow Damage: " + throwDamage +
					"\nMax Stack: " + maxStack + "\nRating: " + 
					twoDigits.format(rating) + 
					"\nHits: " + hits + "\nProperties: \n\n";
	
				for( int i = 0; i < 9; i++ )
				{
					if( properties[i] != null )
					{
						infoString += properties[i] + "\n";
					}	
				}
			}
			
			// if type is other than above
			else
			{
				infoString = "Name: " + name +  
					"\nUnique Level: " + uniqueLevel +
					"\nClass: " + weaponType +
					"\nType: " + specificType + 
					"\nRequired Strength: " + requiredStrength + 
					"\nRequired Level: " + requiredLevel +
					"\nRequired Dexterity: " + requiredDexterity +
					"\nOne Hand Damage: " + oneHandDamage + 
					"\nTwo Hand Damage: " + twoHandDamage +
					"\nRating: " + twoDigits.format(rating) + "\nHits: " + hits +
					"\nProperties: \n\n";
	
				for( int i = 0; i < 9; i++ )
				{
					if( properties[i] != null )
					{
						infoString += properties[i] + "\n";
					}	
				}
			}
		}
		
		// return information string
		return infoString;
	}
}
