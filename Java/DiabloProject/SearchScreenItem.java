//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// SearchScreenItem.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      SearchScreenItem
Purpose:    To search for and generate queries for all item search criteria
Extends:    JFrame
Implements: DiabloDefines
Parameters: N/A

-----------------------------------------------------------------------------*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class SearchScreenItem extends JFrame implements DiabloDefines
{
	protected JComboBox armorSearchChoicesBox,
					    weaponSearchChoicesBox;
	
	protected JTextField searchField;
	
	protected JRadioButton weaponButton,
						   armorButton;
	
	protected JLabel searchLabel,
					 armorLabel,
					 weaponLabel;
	
	protected JButton submitButton;
	
	protected String armorChoices[],
					 weaponChoices[],
					 itemType;
	
	protected Vector items;
	
	protected boolean flag;
					 
//-----------------------------------------------------------------------------
// Function: SearchScreenItem()
// Purpose:  Constructor for class
// Input:    N/A
// Output:   N/A
//-----------------------------------------------------------------------------
	public SearchScreenItem()
	{
		super( "Item Search" );	
		
		// setup container
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		// 0: ARMOR_TYPE
		// 1: NAME
		// 2: DEFENSE
		// 3: LEVEL
		// 4: STRENGTH
		// 5: PROPERTY
		String armorChoices[] = { "Armor Type", "Name" , "Defense",
						   "Level", "Strength", "Property" };
		// 0: WEAPON_TYPE 
		// 1: NAME
		// 2: ONE HAND DAMAGE
		// 3: TWO HAND DAMAGE
		// 4: LEVEL
		// 5: STRENGTH
		// 6: DEXTERITY
		// 7: PROPERTY
		String weaponChoices[] = { "Weapon Type", "Name", "OHDamage",
						   "THDamage", "Level", "Strength", "Dexterity",
						   "Property" };
		
		// setup flag condition
		flag = true;
		
		// setup armor and weapon search boxes
		armorSearchChoicesBox = new JComboBox( armorChoices );
		weaponSearchChoicesBox = new JComboBox( weaponChoices );
		
		// setup search field
		searchField = new JTextField( 20 );				   
		searchField.setText( "" );
		
		// setup weapon and armor select buttons
		weaponButton = new JRadioButton( "Weapons" );
		armorButton = new JRadioButton( "Armor" );
		
		// setup "Search By" label
		searchLabel = new JLabel( "Search By: " );
		
		// create submit button
		submitButton = new JButton( "Submit Request" );
		
		// create button listener for armor button
		armorButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( armorButton.isSelected() )
					{
						weaponButton.setEnabled( false );
						weaponButton.setSelected( false );
						weaponSearchChoicesBox.disable();
					}
					else
					{
						weaponButton.setEnabled( true );
						weaponSearchChoicesBox.enable();
						weaponSearchChoicesBox.setSelectedIndex(0);
					}
				}
			}
		);
		
		// create button listener for weapon button
 		weaponButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( weaponButton.isSelected() )
					{
						armorButton.setEnabled( false );
						armorButton.setSelected( false );
						armorSearchChoicesBox.disable();
					}
					else
					{
						armorButton.setEnabled( true );
						armorSearchChoicesBox.enable();
						armorSearchChoicesBox.setSelectedIndex(0);
					}
				}
			}
		);
		
		// create button listener for submit button
		submitButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					// get current text from search field
					String currentText = searchField.getText();
					
					// check if no buttons are selected or empty search field
					if( (armorButton.isSelected() || weaponButton.isSelected())
						&& currentText.matches( "" ) )
					{
						JOptionPane.showMessageDialog( null,
							"Please enter search criteria" );
						flag = false;
						return;
	
					}
					
					// check if search category is selected
					if( !armorButton.isSelected() && 
						!weaponButton.isSelected() )
					{
						JOptionPane.showMessageDialog( null,
							"Please select a search category" );
						flag = false;
						return;	
					}
					
					// if armor button is selected
					if( armorButton.isSelected() )
					{
						// set type of item
						itemType = "Armor";
						
						// call generateQueries() function
						items = generateQueries( itemType, currentText );
						
						// if the query returned results, call the result screen
						if( !items.isEmpty() )
						{
							ResultScreenItem itemResult = 
								new ResultScreenItem(items);
						}
					}
					
					// if weapon button is selected
					else if( weaponButton.isSelected() )
					{
						// set type of item
						itemType = "Weapon";
						
						// call generateQueries() function
						items = generateQueries( itemType, currentText );
						
						// if the query returned results, call the result screen
						if( !items.isEmpty() )
						{
							ResultScreenItem itemResult =
								new ResultScreenItem(items);
						}
					}
				}
			}
		);
		
		// add all components to the container
		container.add( searchField );
		container.add( searchLabel );
		container.add( armorButton );
		container.add( weaponButton );
		container.add( armorSearchChoicesBox );
		container.add( weaponSearchChoicesBox );
		container.add( submitButton );
		
		// make screen visible
		setVisible( true );
		setSize( 300, 200 );
	}

//-----------------------------------------------------------------------------
// Function: generateQueries()
// Purpose:  to generate the queries based on search criteria and user entry
// Input:    String monsterType for type of item( armor, weapon )
//			 String currentText for user entry
// Output:   Vector containing ItemQueryProcessor objects
//-----------------------------------------------------------------------------
	public Vector generateQueries( String type, String currentText )
	{
		// create ItemQueryProcessor object
		ItemQueryProcessor items;
		
		// create Vector to store objects
		Vector itemVector = new Vector();
		
		// if item type is armor
		if( type == "Armor" )
		{
			// Run Armor Type queries for Body Armor, Belt, Boot, Glove,
			// Helm, and Shield tables
			if( armorSearchChoicesBox.getSelectedIndex() == A_ARMOR_TYPE )
			{
				// convert text to uppercase for easier matching
				currentText = currentText.toUpperCase();
				if( currentText.matches( "[A-Za-z]*BODY ARMOR[A-Za-z]*") ||
					currentText.matches( "[A-Za-z]*ARMOR[A-Za-z]*" ) ) 
				{
					System.out.println( "Load body armor." );
					items = 
						new ItemQueryProcessor( A_ARMOR_TYPE_ARMOR, "Armor" );
					itemVector.add( items );
				}
				else if( currentText.matches( "[A-Za-z]*BELT[A-Za-z]*" ) )
				{
					System.out.println( "Load belt." );
					items =
						new ItemQueryProcessor( A_ARMOR_TYPE_BELT, "Armor" );
					itemVector.add( items );
				}
				else if( currentText.matches( "[A-Za-z]*BOOT[A-Za-z]*" ) )
				{
					System.out.println( "Load boot." );
					items =
						new ItemQueryProcessor( A_ARMOR_TYPE_BOOT, "Armor" );
					itemVector.add( items );
				}
				else if( currentText.matches( "[A-Za-z]*GLOVE[A-Za-z]*" ) )
				{
					System.out.println( "Load glove." );
					items =
						new ItemQueryProcessor( A_ARMOR_TYPE_GLOVE, "Armor" );
					itemVector.add( items );
				}
				else if( currentText.matches( "[A-Za-z]*HELM[A-Za-z]*" ) )
				{
					System.out.println( "Load helm." );
					items =
						new ItemQueryProcessor( A_ARMOR_TYPE_HELM, "Armor" );
					itemVector.add( items );
				}
				else if( currentText.matches( "[A-Za-z]*SHIELD[A-Za-z]*" ) )
				{
					System.out.println( "Load shield." );
					items =
						new ItemQueryProcessor( A_ARMOR_TYPE_SHIELD, "Armor" );
					itemVector.add( items );
				}
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
			}
			
			// Run Armor Name queries for Body Armor, Belt, Boot, Glove,
			// Helm, and Shield tables
			else if( armorSearchChoicesBox.getSelectedIndex() == A_NAME )
			{
				System.out.println( "Searching for name in armor tables." );
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_ARMOR + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Armor.");
				}
				else
					itemVector.add( items );
			
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_BELT + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Belt.");
				}
				else
					itemVector.add( items );
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_BOOT + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Boot.");
				}
				else
					itemVector.add( items );
			
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_GLOVE + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Glove.");
				}
				else
					itemVector.add( items );
		
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_HELM + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Helm.");
				}
				else
					itemVector.add( items );
		
				items = 
					new ItemQueryProcessor( A_ARMOR_NAME_SHIELD + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield.");
				}
				else
					itemVector.add( items );
		
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
			}
			
			// Run Armor Defense queries for Body Armor, Belt, Boot, Glove,
			// Helm, and Shield tables
			else if( armorSearchChoicesBox.getSelectedIndex() == A_DEFENSE )
			{
				System.out.println( "Searching defense in armor tables." );

				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_ARMOR + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Body Armor" );
				}
				else
					itemVector.add( items );
					
				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_BELT + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Belt" );
				}
				else
					itemVector.add( items );
					
				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_BOOT + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Boot" );
				}
				else
					itemVector.add( items );
					
				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_GLOVE + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Glove" );
				}
				else
					itemVector.add( items );
					
				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_HELM + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Helm" );
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( A_ARMOR_DEFENSE_SHIELD + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// Run Armor Required Level queries for Body Armor, Belt, Boot, 
			// Glove, Helm, and Shield tables
			else if( armorSearchChoicesBox.getSelectedIndex() == A_LEVEL )
			{
				System.out.println( "Searching armor for level." );
				
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_ARMOR + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Body Armor" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_BELT + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Belt" );
				}
				else
					itemVector.add( items );
					
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_BOOT + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Boot" );
				}
				else
					itemVector.add( items );
					
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_GLOVE + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Glove" );
				}
				else
					itemVector.add( items );
					
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_HELM + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Helm" );
				}
				else
					itemVector.add( items );
					
				items = 
					new ItemQueryProcessor( A_ARMOR_LEVEL_SHIELD + 
						currentText, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
				
			}
			
			// Run Armor Required Strength queries for Body Armor, Belt, Boot, 
			// Glove, Helm, and Shield tables
			else if( armorSearchChoicesBox.getSelectedIndex() == A_STRENGTH )
			{
				System.out.println( "Searching armor for strength." );

				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_ARMOR + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Body Armor" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_BELT + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Belt" );
				}
				else
					itemVector.add( items );
	
				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_BOOT + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Boot" );
				}
				else
					itemVector.add( items );
	
				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_GLOVE + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Glove" );
				}
				else
					itemVector.add( items );
	
				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_HELM + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Helm" );
				}
				else
					itemVector.add( items );
	
				items = 
					new ItemQueryProcessor( A_ARMOR_STRENGTH_SHIELD + currentText,
						"Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
			}
			
			// Run Armor Property queries for Body Armor, Belt, Boot, Glove,
			// Helm, and Shield tables
			else if( armorSearchChoicesBox.getSelectedIndex() == A_PROPERTY )
			{
				// Body armor properties (9)
				items = 
					new ItemQueryProcessor( ARMOR_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( ARMOR_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
					
				// Belt properties (9)
				
				items = 
					new ItemQueryProcessor( BELT_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BELT_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
					
				// Boot properties (9)
				
				items = 
					new ItemQueryProcessor( BOOT_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOOT_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Glove properties (9)
				items = 
					new ItemQueryProcessor( GLOVE_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( GLOVE_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
					
				// Helm properties (9)	
				
				items = 
					new ItemQueryProcessor( HELM_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( HELM_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Shield properties (9)
				items = 
					new ItemQueryProcessor( SHIELD_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( ARMOR_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( ARMOR_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SHIELD_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Armor" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
	
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
		}
		
		// if item type is a weapon
		else if( type == "Weapon" )
		{
			// weapon type queries
			if( weaponSearchChoicesBox.getSelectedIndex() == W_WEAPON_TYPE )
			{
				currentText = currentText.toUpperCase();
				if( currentText.matches( "[A-Za-z]*AXE[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for axe." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_AXE, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "BOW[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for bow." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_BOW, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*CROSSBOW[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for crossbow." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_CROSSBOW, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*DAGGER[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for dagger." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_DAGGER, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*JAVELIN[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for javelin." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_JAVELIN, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*MACE[A-Za-z]*") ||
					currentText.matches( "[A-Za-z]*MAUL[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for mace." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_MACE, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*POLEARM[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for polearm." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_POLEARM, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*SCEPTER[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for scepter." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_SCEPTER, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*SPEAR[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for spear." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_SPEAR, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*STAVE[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for stave." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_STAVE, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*SWORD[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for sword." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_SWORD, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*THROWING WEAPON[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for throwing weapon." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_THROWING, "Weapon" );
					itemVector.add( items );
				}
				if( currentText.matches( "[A-Za-z]*WAND[A-Za-z]*") )
				{
					System.out.println( "Searching weapons for wand." );
					items = 
						new ItemQueryProcessor( W_WEAPON_TYPE_WAND, "Weapon" );
					itemVector.add( items );
				}
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
			}
			
			// weapon name queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_NAME )
			{
				System.out.println( "Searching for name in weapon tables." );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_AXE + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_BOW + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_CROSSBOW + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_DAGGER + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_JAVELIN + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_MACE + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_POLEARM + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_SCEPTER + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_SPEAR + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_STAVE + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_SWORD + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_THROWING + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapon.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_NAME_WAND + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null,
						"Search produced no results." );
				}
			}
			
			// weapon one hand damage queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_OHDAMAGE )
			{
				System.out.println( "Searching weapons for one-hand damage" );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_AXE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_BOW + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );
			
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_CROSSBOW + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );
			
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_DAGGER + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );
			
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_JAVELIN + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_MACE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_POLEARM + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_SCEPTER + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_SPEAR + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_STAVE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_SWORD + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_THROWING + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapon.");
				}
				else
					itemVector.add( items );
				
				items =
					new ItemQueryProcessor( W_WEAPON_OHDAMAGE_WAND + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
									
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// weapon two hand damage queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_THDAMAGE )
			{
				System.out.println( "Searching weapons for two-hand damage" );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_AXE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_BOW + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );

				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_CROSSBOW + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );

				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_DAGGER + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );

				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_JAVELIN + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_MACE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_POLEARM + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_SCEPTER + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_SPEAR + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_STAVE + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_SWORD + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_THROWING + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapon.");
				}
				else
					itemVector.add( items );
	
				items =
					new ItemQueryProcessor( W_WEAPON_THDAMAGE_WAND + currentText 
						, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
						
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// weapon level queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_LEVEL )
			{
				System.out.println( "Searching weapons for level." );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_AXE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_BOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_CROSSBOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_DAGGER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_JAVELIN + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_MACE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_POLEARM + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_SCEPTER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_SPEAR + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_STAVE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_SWORD + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_THROWING + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapons.");
				}
				else
					itemVector.add( items );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_LEVEL_WAND + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
				
			}
			
			// weapon strength queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_STRENGTH )
			{
				System.out.println( "Searching weapons for strength." );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_AXE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_BOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_CROSSBOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_DAGGER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_JAVELIN + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_MACE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_POLEARM + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_SCEPTER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_SPEAR + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_STAVE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_SWORD + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_THROWING + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapons.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_STRENGTH_WAND + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// weapon dexterity queries
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_DEXTERITY )
			{
				System.out.println( "Searching weapons for dexterity." );
				
				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_AXE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Axe.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_BOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Bow.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_CROSSBOW + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Crossbow.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_DAGGER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Dagger.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_JAVELIN + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Javelin.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_MACE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Mace/Maul.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_POLEARM + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Polearm.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_SCEPTER + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Scepter.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_SPEAR + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Spear.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_STAVE + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Stave.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_SWORD + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Sword.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_THROWING + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Throwing Weapons.");
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( W_WEAPON_DEX_WAND + currentText,
						"Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Wand.");
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// weapon property queries 
			else if( weaponSearchChoicesBox.getSelectedIndex() == W_PROPERTY )
			{
				// Axe properties (9)
				items = 
					new ItemQueryProcessor( AXE_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( AXE_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Bow properties (9)
				
				items = 
					new ItemQueryProcessor( BOW_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( BOW_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Crossbow properties (9)
				
				items = 
					new ItemQueryProcessor( CROSSBOW_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( CROSSBOW_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Dagger properties (9)
				
				items = 
					new ItemQueryProcessor( DAGGER_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( DAGGER_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Javelin properties (9)
				
				items = 
					new ItemQueryProcessor( JAVELIN_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( JAVELIN_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Mace properties (9)
				
				items = 
					new ItemQueryProcessor( MACE_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( MACE_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Polearm properties (9)
				
				items = 
					new ItemQueryProcessor( POLEARM_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( POLEARM_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Scepter properties (9)
				
				items = 
					new ItemQueryProcessor( SCEPTER_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SCEPTER_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Spear properties (9)
				
				items = 
					new ItemQueryProcessor( SPEAR_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SPEAR_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Stave properties (9)
				
				items = 
					new ItemQueryProcessor( STAVE_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( STAVE_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Sword properties (9)
				
				items = 
					new ItemQueryProcessor( SWORD_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( SWORD_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Throwing Weapon properties (9)
				
				items = 
					new ItemQueryProcessor( THROWING_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( THROWING_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				// Wand properties (9)
				
				items = 
					new ItemQueryProcessor( WAND_PROP1 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP2 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP3 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP4 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP5 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP6 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP7 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP8 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );

				items = 
					new ItemQueryProcessor( WAND_PROP9 + currentText +
						CONCLUDE_LIKE_QUERY, "Weapon" );
				if( items.tempEntity == null )
				{
					System.out.println( "No results in Shield" );
				}
				else
					itemVector.add( items );
				
				if( itemVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
		}
		
		// return the vector
		return itemVector;
	}
}
