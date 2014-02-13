//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// MonsterEntity.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      MonsterEntity
Purpose:    To store and provide all functionality for monster entities
Extends:    N/A
Implements: N/A
Parameters: String representing the type class of the monster

-----------------------------------------------------------------------------*/
import java.text.DecimalFormat;

public class MonsterEntity
{
	protected int level, 
				  experience,
				  defense,
			      percentToBlock,
			      damageResist,
			      magicResist,
			      coldResist,
			      fireResist,
			      lightningResist,
			      poisonResist,
			      drainEffectiveness,
			      chillEffectiveness,
			      hits,
			      total; 
	protected String name,
					 difficultyLevel,
					 monsterClass,
					 monsterType,
					 HP,
					 meleeAttack1,
					 meleeAttack2,
					 attackRating1,
					 attackRating2,
					 immunities,
					 location,
					 abilities,
					 imageLocation,
					 monsterID,
					 type;
	
	protected double userRating;
	
	protected DecimalFormat twoDigits;

//-----------------------------------------------------------------------------
// Function: MonsterEntity()
// Purpose:  class constructor
// Input:    String representing the type class of monster
// Output:   N/A
//-----------------------------------------------------------------------------
	public MonsterEntity( String typeClass )
	{
		twoDigits = new DecimalFormat( "0.00" );
		type = typeClass;	
	}

//-----------------------------------------------------------------------------
// Function: monsterInformation()
// Purpose:  to create a String which contains all monster entity information
// Input:    N/A
// Output:   String representing the monster entity information
//-----------------------------------------------------------------------------			 
	public String monsterInformation()
	{
		String infoString = null;
		
		// if monster type is "SuperUnique"
		if( type == "SuperUnique" )
		{
			infoString = "Name: " + name + "\nMonster Class: " +
				monsterClass + "\nMonster Type: " + monsterType +
				"\nLocation: " + location +
				"\nLevel: " + level + "\nExperience: " + experience + 
				"\nHP: " + HP + "\nDefense: " + defense + 
				"\nPercent To Block : " + percentToBlock + "\nMelee Attack: " +
				meleeAttack1 + "\nAttack Rating: " + attackRating1 +
				"\nMelee Attack 2: " + meleeAttack2 + "\nAttack Rating 2:" +
				"\nDamage Resist: " + damageResist + "\nMagic Resist: " + 
				magicResist + "\nCold Resist: " + coldResist + 
				"\nFire Resist: " + fireResist + "\nLightning Resist: " + 
				lightningResist + "\nPoison Resist: " + poisonResist + 
				"\nDrain Effectiveness: " + drainEffectiveness + 
				"\nImmunities: " + immunities + "\nChill Effectiveness: " +
				chillEffectiveness + "\nAbilities: " + abilities + 
				"\nUser Rating: " + twoDigits.format(userRating) + "\nHits: " + hits;
		}
		// if monster type is "Unique"
		else if( type == "Unique" )
		{
			infoString = "Name: " + name + "\nMonster Class: " + monsterClass +
				"\nMonster Type: " + monsterType + "\nLocation: " + location +
				"\nAbilities: " + abilities + "\nUser Rating: " + 
				twoDigits.format(userRating) +
				"\nHits: " + hits;
		}
		
		// return the string
		return infoString;
	}				 
					 			 
}
