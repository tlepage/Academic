//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// DiabloDefines.java
//-----------------------------------------------------------------------------
/*-----------------------------------------------------------------------------

Class:      DiabloDefines
Purpose:    An interface which holds all constants and queries in the program
Extends:    N/A
Implements: N/A
Parameters: N/A

-----------------------------------------------------------------------------*/
import java.awt.*;

public interface DiabloDefines
{	
	// Microsoft Access Database drivers and user information
	static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String URL = "jdbc:odbc:DiabloProject";
	static final String USERNAME = "";
	static final String PASSWORD = "";
	
	// Image locations which database refers to
	static final String IMAGE_LOCATION = "C:/DiabloProject/Images/";
	static final String ITEM_IMAGE_LOCATION = "C:/DiabloProject/ItemImages/";
	
	// Color constants used in the program
	static final Color GOLD = new Color( 144, 136, 88 );
	static final Color BLUE = new Color( 72, 80, 184 );
	static final Color WHITE = Color.WHITE;
	static final Color BLACK = Color.BLACK;
	
	// Armor search box constants
	static final int A_ARMOR_TYPE = 0;
	static final int A_NAME = 1;
	static final int A_DEFENSE = 2;
	static final int A_LEVEL = 3;
	static final int A_STRENGTH = 4;
	static final int A_PROPERTY = 5;
	
	// Weapon search box constants
	static final int W_WEAPON_TYPE = 0;
	static final int W_NAME = 1;
	static final int W_OHDAMAGE = 2;
	static final int W_THDAMAGE = 3;
	static final int W_LEVEL = 4;
	static final int W_STRENGTH = 5;
	static final int W_DEXTERITY = 6;
	static final int W_PROPERTY = 7;
	
	// Unique monster search box constants
	static final int U_NAME = 0;
	static final int U_ABILITIES = 1;
	static final int U_LOCATION = 2;
	
	// Super unique monster search box constants
	static final int S_NAME = 0;
	static final int S_ABILITIES = 1;
	static final int S_LEVEL = 2;
	static final int S_EXPERIENCE = 3;
	static final int S_HP = 4;
	static final int S_DIFFICULTY = 5;
	
	// Query helper ender constants
	static final String CONCLUDE_LIKE_QUERY = "%'";
	static final String CONCLUDE_LIKE_QUERY_NO_AFTER = "'";
	
/*-----------------------------------------------------------------------------
  QUERIES - 313 Queries for Weapon, Armor, Unique Monster, Super Unique Monster
/*---------------------------------------------------------------------------*/
	
	// Armor Type queries (6)
	static final String A_ARMOR_TYPE_ARMOR = "SELECT * FROM ITEM AS I, " +		"ARMOR AS A, BODY_ARMOR AS B WHERE I.ItemType = 'Body Armor' AND " +		"I.ItemID = A.ItemID AND A.ArmorID = B.ArmorID";
	static final String A_ARMOR_TYPE_BELT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BELT AS B WHERE I.ItemType = 'Belt' AND " +
		"I.ItemID = A.ItemID AND A.ArmorID = B.ArmorID";
	static final String A_ARMOR_TYPE_BOOT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BOOT AS B WHERE I.ItemType = 'Boot' AND " +
		"I.ItemID = A.ItemID AND A.ArmorID = B.ArmorID";
	static final String A_ARMOR_TYPE_GLOVE = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, GLOVE AS G WHERE I.ItemType = 'Glove' AND " +
		"I.ItemID = A.ItemID AND A.ArmorID = G.ArmorID";
	static final String A_ARMOR_TYPE_HELM = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, HELM AS H WHERE I.ItemType = 'Helm' AND " +
		"I.ItemID = A.ItemID AND A.ArmorID = H.ArmorID";
	static final String A_ARMOR_TYPE_SHIELD = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, SHIELD AS S WHERE I.ItemType = 'Shield' AND " +
		"I.ItemID = A.ItemID AND A.ArmorID = S.ArmorID";
	
	// Armor Name queries (6)
	static final String A_ARMOR_NAME_ARMOR = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND B.Name LIKE '%";
	static final String A_ARMOR_NAME_BELT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BELT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND B.Name LIKE '%";
	static final String A_ARMOR_NAME_BOOT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BOOT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND B.Name LIKE '%";
	static final String A_ARMOR_NAME_GLOVE = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, GLOVE AS G WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = G.ArmorID AND G.Name LIKE '%";
	static final String A_ARMOR_NAME_HELM = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, HELM AS H WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = H.ArmorID AND H.Name LIKE '%";
	static final String A_ARMOR_NAME_SHIELD = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, SHIELD AS S WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = S.ArmorID AND S.Name LIKE '%";
	
	// Armor Defense queries (6)
	static final String A_ARMOR_DEFENSE_ARMOR = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.Defense ";
	static final String A_ARMOR_DEFENSE_BELT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BELT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.Defense ";
	static final String A_ARMOR_DEFENSE_BOOT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BOOT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.Defense ";
	static final String A_ARMOR_DEFENSE_GLOVE = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, GLOVE AS G WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = G.ArmorID AND A.Defense ";
	static final String A_ARMOR_DEFENSE_HELM = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, HELM AS H WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = H.ArmorID AND A.Defense ";
	static final String A_ARMOR_DEFENSE_SHIELD = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, SHIELD AS S WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = S.ArmorID AND A.Defense ";
		
	// Armor Required Level queries (6)
	static final String A_ARMOR_LEVEL_ARMOR = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredLevel ";
	static final String A_ARMOR_LEVEL_BELT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BELT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredLevel ";
	static final String A_ARMOR_LEVEL_BOOT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BOOT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredLevel ";
	static final String A_ARMOR_LEVEL_GLOVE = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, GLOVE AS G WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = G.ArmorID AND A.RequiredLevel ";
	static final String A_ARMOR_LEVEL_HELM = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, HELM AS H WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = H.ArmorID AND A.RequiredLevel ";
	static final String A_ARMOR_LEVEL_SHIELD = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, SHIELD AS S WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = S.ArmorID AND A.RequiredLevel ";
		
	// Armor Required Strength queries (6)
	static final String A_ARMOR_STRENGTH_ARMOR = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredStrength ";
	static final String A_ARMOR_STRENGTH_BELT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BELT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredStrength ";
	static final String A_ARMOR_STRENGTH_BOOT = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, BOOT AS B WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = B.ArmorID AND A.RequiredStrength ";
	static final String A_ARMOR_STRENGTH_GLOVE = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, GLOVE AS G WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = G.ArmorID AND A.RequiredStrength ";
	static final String A_ARMOR_STRENGTH_HELM = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, HELM AS H WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = H.ArmorID AND A.RequiredStrength ";
	static final String A_ARMOR_STRENGTH_SHIELD = "SELECT * FROM ITEM AS I, " +
		"ARMOR AS A, SHIELD AS S WHERE I.ItemID = A.ItemID AND " +
		"A.ArmorID = S.ArmorID AND A.RequiredStrength ";
		
	// Weapon Type queries (13)
	static final String W_WEAPON_TYPE_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND I.ItemType = 'Axe'";
	static final String W_WEAPON_TYPE_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND I.ItemType = 'Bow'";
	static final String W_WEAPON_TYPE_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND I.ItemType = 'Crossbow'";
	static final String W_WEAPON_TYPE_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND I.ItemType = 'Dagger'";
	static final String W_WEAPON_TYPE_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND I.ItemType = 'Javelin'";
	static final String W_WEAPON_TYPE_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND I.ItemType = 'Mace/Maul'";
	static final String W_WEAPON_TYPE_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND I.ItemType = 'Polearm'";
	static final String W_WEAPON_TYPE_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND I.ItemType = 'Scepter'";
	static final String W_WEAPON_TYPE_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND I.ItemType = 'Spear'";
	static final String W_WEAPON_TYPE_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND I.ItemType = 'Stave'";
	static final String W_WEAPON_TYPE_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND I.ItemType = 'Sword'";
	static final String W_WEAPON_TYPE_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND I.ItemType = 'Throwing Weapon'";
	static final String W_WEAPON_TYPE_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND I.ItemType = 'Wand'";
		
	// Weapon Name queries (13)
	static final String W_WEAPON_NAME_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND A.Name LIKE '%";
	static final String W_WEAPON_NAME_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND B.Name LIKE '%";
	static final String W_WEAPON_NAME_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND C.Name LIKE '%";
	static final String W_WEAPON_NAME_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND D.Name LIKE '%";
	static final String W_WEAPON_NAME_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND J.Name LIKE '%";
	static final String W_WEAPON_NAME_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND M.Name LIKE '%";
	static final String W_WEAPON_NAME_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND P.Name LIKE '%";
	static final String W_WEAPON_NAME_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND S.Name LIKE '%";
	static final String W_WEAPON_NAME_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND S.Name LIKE '%";
	static final String W_WEAPON_NAME_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND S.Name LIKE '%";
	static final String W_WEAPON_NAME_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND S.Name LIKE '%";
	static final String W_WEAPON_NAME_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND T.Name LIKE '%";
	static final String W_WEAPON_NAME_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND A.Name LIKE '%";	
		
	// Weapon One Hand Damage queries (13)
	static final String W_WEAPON_OHDAMAGE_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND W.OneHandDmg ";
	static final String W_WEAPON_OHDAMAGE_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.OneHandDmg ";	
		
	// Weapon Two Hand Damage queries (13)
	static final String W_WEAPON_THDAMAGE_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND W.TwoHandDmg ";
	static final String W_WEAPON_THDAMAGE_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.TwoHandDmg ";	
		
	// Weapon Required Level queries (13)
	static final String W_WEAPON_LEVEL_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND W." + "RequiredLevel ";
	static final String W_WEAPON_LEVEL_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_LEVEL_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredLevel ";
		
	// Weapon Required Strength queries (13)
	static final String W_WEAPON_STRENGTH_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredLevel ";
	static final String W_WEAPON_STRENGTH_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND W.RequiredStrength ";
	static final String W_WEAPON_STRENGTH_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredStrength ";	
		
	// Weapon Required Dexterity queries (13)
	static final String W_WEAPON_DEX_AXE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, AXE AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_BOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, BOW AS B WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = B.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_CROSSBOW = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, CROSSBOW AS C WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = C.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_DAGGER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, DAGGER AS D WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = D.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_JAVELIN = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, JAVELIN AS J WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = J.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_MACE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, MACE AS M WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = M.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_POLEARM = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, POLEARM AS P WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = P.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_SCEPTER = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SCEPTER AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_SPEAR = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SPEAR AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_STAVE = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, STAVE AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_SWORD = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, SWORD AS S WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = S.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_THROWING = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, THROWING_WEAPON AS T WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = T.WeaponID AND W.RequiredDexterity ";
	static final String W_WEAPON_DEX_WAND = "SELECT * FROM ITEM AS I, " +
		"WEAPON AS W, WAND AS A WHERE I.ItemID = W.ItemID AND " +
		"W.WeaponID = A.WeaponID AND W.RequiredDexterity ";	
		
	// Unique Monster queries (3) Name, Abilities, and Location
	static final String U_MONSTER_NAME = "SELECT * FROM MONSTER AS M, " +
		"UNIQUE_MONSTER AS U WHERE M.MonsterID = U.MonsterID AND " +
		"U.Name LIKE '%";
	static final String U_MONSTER_ABILITIES = "SELECT * FROM MONSTER AS M, " +
		"UNIQUE_MONSTER AS U WHERE M.MonsterID = U.MonsterID AND " +
		"U.Abilities LIKE '%";
	static final String U_MONSTER_LOCATION = "SELECT * FROM MONSTER AS M, " +
		"UNIQUE_MONSTER AS U WHERE M.MonsterID = U.MonsterID AND " +
		"U.Location LIKE '%";
		
	// Super Unique Monster queries (8) Name, Abilities, Hit Points(HP)
	// Experience, Level, Difficulty Levels (Normal, Nightmare, Hell)
	static final String S_MONSTER_NAME = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.Name LIKE '%";
	static final String S_MONSTER_ABILITIES = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.Abilities LIKE '%";
	static final String S_MONSTER_HP = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.HP ";
	static final String S_MONSTER_EXPERIENCE = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.Experience ";
	static final String S_MONSTER_LEVEL = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.Level ";
	static final String S_MONSTER_DIFF_NORMAL = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.DifficultyLevel LIKE 'Normal'";
	static final String S_MONSTER_DIFF_NIGHT = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.DifficultyLevel LIKE 'Nightmare'";
	static final String S_MONSTER_DIFF_HELL = "SELECT * FROM MONSTER AS M, " +
		"SUPER_UNIQUE_MONSTER AS S WHERE M.MonsterID = S.MonsterID AND " +
		"S.DifficultyLevel LIKE 'Hell'";
	
	// Body Armor Property queries (9)
	static final String ARMOR_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +		"B.ArmorID AND B.Property1 LIKE '%";
	static final String ARMOR_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String ARMOR_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String ARMOR_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String ARMOR_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String ARMOR_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String ARMOR_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String ARMOR_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String ARMOR_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BODY_ARMOR AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Belt Property queries (9)
	static final String BELT_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property1 LIKE '%";
	static final String BELT_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String BELT_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String BELT_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String BELT_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String BELT_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String BELT_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String BELT_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String BELT_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BELT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Boot Property queries (9)
	static final String BOOT_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property1 LIKE '%";
	static final String BOOT_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String BOOT_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String BOOT_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String BOOT_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String BOOT_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String BOOT_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String BOOT_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String BOOT_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"BOOT AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Glove Property queries (9)
	static final String GLOVE_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property1 LIKE '%";
	static final String GLOVE_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String GLOVE_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String GLOVE_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String GLOVE_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String GLOVE_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String GLOVE_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String GLOVE_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String GLOVE_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"GLOVE AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Helm Property queries (9)
	static final String HELM_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property1 LIKE '%";
	static final String HELM_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String HELM_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String HELM_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String HELM_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String HELM_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String HELM_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String HELM_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String HELM_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"HELM AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Shield Property queries (9)
	static final String SHIELD_PROP1 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property1 LIKE '%";
	static final String SHIELD_PROP2 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property2 LIKE '%";
	static final String SHIELD_PROP3 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property3 LIKE '%";
	static final String SHIELD_PROP4 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property4 LIKE '%";
	static final String SHIELD_PROP5 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property5 LIKE '%";
	static final String SHIELD_PROP6 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property6 LIKE '%";
	static final String SHIELD_PROP7 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property7 LIKE '%";
	static final String SHIELD_PROP8 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property8 LIKE '%";
	static final String SHIELD_PROP9 = "SELECT * FROM ITEM AS I, ARMOR AS A, " +
		"SHIELD AS B WHERE I.ItemID = A.ItemID AND A.ArmorID = " +
		"B.ArmorID AND B.Property9 LIKE '%";
	
	// Axe Property queries (9)
	static final String AXE_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +		"A.Property1 LIKE '%";
	static final String AXE_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String AXE_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String AXE_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String AXE_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String AXE_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String AXE_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String AXE_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String AXE_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"AXE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Bow Property queries (9)
	static final String BOW_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String BOW_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String BOW_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String BOW_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String BOW_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String BOW_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String BOW_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String BOW_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String BOW_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"BOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Crossbow Property queries (9)
	static final String CROSSBOW_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String CROSSBOW_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String CROSSBOW_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String CROSSBOW_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String CROSSBOW_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String CROSSBOW_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String CROSSBOW_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String CROSSBOW_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String CROSSBOW_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"CROSSBOW AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Dagger Property (9)
	static final String DAGGER_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String DAGGER_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String DAGGER_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String DAGGER_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String DAGGER_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String DAGGER_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String DAGGER_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String DAGGER_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String DAGGER_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"DAGGER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Javelin Property queries (9)
	static final String JAVELIN_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String JAVELIN_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String JAVELIN_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String JAVELIN_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String JAVELIN_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String JAVELIN_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String JAVELIN_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String JAVELIN_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String JAVELIN_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"JAVELIN AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Mace Property queries (9)
	static final String MACE_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String MACE_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String MACE_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String MACE_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String MACE_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String MACE_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String MACE_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String MACE_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String MACE_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"MACE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Polearm Property queries (9)
	static final String POLEARM_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String POLEARM_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String POLEARM_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String POLEARM_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String POLEARM_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String POLEARM_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String POLEARM_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String POLEARM_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String POLEARM_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"POLEARM AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Scepter Property queries (9)
	static final String SCEPTER_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String SCEPTER_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String SCEPTER_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String SCEPTER_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String SCEPTER_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String SCEPTER_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String SCEPTER_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String SCEPTER_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String SCEPTER_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SCEPTER AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Spear Property queries (9)
	static final String SPEAR_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String SPEAR_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String SPEAR_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String SPEAR_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String SPEAR_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String SPEAR_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String SPEAR_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String SPEAR_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String SPEAR_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SPEAR AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Stave Property queries (9)
	static final String STAVE_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String STAVE_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"STAVE AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	
	// Sword Property queries (9)
	static final String SWORD_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String SWORD_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String SWORD_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String SWORD_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String SWORD_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String SWORD_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String SWORD_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String SWORD_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String SWORD_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"SWORD AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Throwing Weapon Property queries (9)
	static final String THROWING_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String THROWING_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String THROWING_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String THROWING_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String THROWING_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String THROWING_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String THROWING_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String THROWING_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String THROWING_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"THROWING_WEAPON AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
	
	// Wand Property queries (9)
	static final String WAND_PROP1 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property1 LIKE '%";
	static final String WAND_PROP2 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property2 LIKE '%";
	static final String WAND_PROP3 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property3 LIKE '%";
	static final String WAND_PROP4 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property4 LIKE '%";
	static final String WAND_PROP5 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property5 LIKE '%";
	static final String WAND_PROP6 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property6 LIKE '%";
	static final String WAND_PROP7 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property7 LIKE '%";
	static final String WAND_PROP8 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property8 LIKE '%";
	static final String WAND_PROP9 = "SELECT * FROM ITEM AS I, WEAPON AS W, " +
		"WAND AS A WHERE I.ItemID = W.ItemID AND W.WeaponID = A.WeaponID AND " +
		"A.Property9 LIKE '%";
}
