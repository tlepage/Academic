//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// SearchScreenMonster.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      SearchScreenMonster
Purpose:    To search for and generate queries for all monster search criteria
Extends:    JFrame
Implements: DiabloDefines
Parameters: N/A

-----------------------------------------------------------------------------*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class SearchScreenMonster extends JFrame implements DiabloDefines
{
	protected JComboBox uniqueSearchChoicesBox,
						superSearchChoicesBox;

	protected JTextField searchField;

	protected JRadioButton uniqueButton,
						   superButton;

	protected JButton submitButton;

	protected JLabel searchLabel;
	
	protected String uniqueChoices[],
					 superChoices[],
					 monsterType;

	protected Vector monsters;

	protected boolean flag;

//-----------------------------------------------------------------------------
// Function: SearchScreenMonster()
// Purpose:  Constructor for class
// Input:    N/A
// Output:   N/A
//-----------------------------------------------------------------------------
	public SearchScreenMonster()
	{
		super( "Monster Search" );
		
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		// 0: Name
		// 1: Abilities 
		// 2: Location
		String uniqueChoices[] = { "Name", "Abilities", "Location" };
		
		// 0: Name
		// 1: Abilities
		// 2: Level
		// 3: Experience
		// 4: HP
		// 5: Difficulty
		String superChoices[] = { "Name", "Abilities", "Level", "Experience",
								  "HP", "Difficulty" };
								  
		// set up flag
		flag = true;
		
		// create search boxes
		uniqueSearchChoicesBox = new JComboBox( uniqueChoices );
		superSearchChoicesBox = new JComboBox( superChoices );

		// create search field
		searchField = new JTextField( 20 );				   
		searchField.setText( "" );
		
		// create search label
		searchLabel = new JLabel( "Search By: " );
		
		// create search buttons
		uniqueButton = new JRadioButton( "Unique" );
		superButton = new JRadioButton( "Super Unique" );

		// create submit button
		submitButton = new JButton( "Submit Request" );
		
		// setup listener for unique button
		uniqueButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( uniqueButton.isSelected() )
					{
						superButton.setEnabled( false );
						superButton.setSelected( false );
						superSearchChoicesBox.disable();
					}
					else
					{
						superButton.setEnabled( true );
						superSearchChoicesBox.enable();
						superSearchChoicesBox.setSelectedIndex(0);
					}
				}
			}
		);
		
		// setup listener for super unique button
		superButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( superButton.isSelected() )
					{
						uniqueButton.setEnabled( false );
						uniqueButton.setSelected( false );
						uniqueSearchChoicesBox.disable();
					}
					else
					{
						uniqueButton.setEnabled( true );
						uniqueSearchChoicesBox.enable();
						uniqueSearchChoicesBox.setSelectedIndex(0);
					}
				}
			}
		);
		
		// setup listener for submit button
		submitButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					String currentText = searchField.getText();
				
					// check for empty search field and no buttons selected
					if( (uniqueButton.isSelected() || superButton.isSelected())
						&& currentText.matches( "" ) )
					{
						JOptionPane.showMessageDialog( null,
							"Please enter search criteria" );
						flag = false;
						return;
	
					}
					if( !uniqueButton.isSelected() && 
						!superButton.isSelected() )
					{
						JOptionPane.showMessageDialog( null,
							"Please select a search category" );
						flag = false;
						return;	
					}
					
					// call the ResultScreenMonster class for uniques
					if( uniqueButton.isSelected() )
					{
						monsterType = "Unique";
						// start here
						monsters = generateQueries( monsterType, currentText );
						
						if( !monsters.isEmpty() )
						{
							ResultScreenMonster monsterResult = 
								new ResultScreenMonster(monsters);
						}
					}
					
					// call the ResultScreenMonster class for super uniques
					else if( superButton.isSelected() )
					{
						monsterType = "SuperUnique";
						monsters = generateQueries( monsterType, currentText );
						
						if( !monsters.isEmpty() )
						{
							ResultScreenMonster monsterResult =
								new ResultScreenMonster(monsters);
						}
					}
				}
			}
		);
		
		// add objects to container
		container.add( searchField );
		container.add( searchLabel );
		container.add( uniqueButton );
		container.add( superButton );
		container.add( uniqueSearchChoicesBox );
		container.add( superSearchChoicesBox );
		container.add( submitButton );
		
		// make visible
		setVisible( true );
		setSize( 300, 200 );
	}

//-----------------------------------------------------------------------------
// Function: generateQueries()
// Purpose:  to generate the queries based on search criteria and user entry
// Input:    String monsterType for type of monster( unique, superunique )
//           String currentText for user entry
// Output:   Vector containing MonsterQueryProcessor objects
//-----------------------------------------------------------------------------
	public Vector generateQueries( String monsterType, String currentText )
	{
		MonsterQueryProcessor monsters;
		Vector monsterVector = new Vector();
		
		// if type is "Unique"
		if( monsterType == "Unique" )
		{
			// if doing a name query
			if( uniqueSearchChoicesBox.getSelectedIndex() == U_NAME )
			{
				// create query object
				monsters = 
					new MonsterQueryProcessor( U_MONSTER_NAME + currentText +
						CONCLUDE_LIKE_QUERY, "Unique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Unique Monster.");
				}
				else
					monsterVector.add( monsters );
					
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for an ability query
			else if( uniqueSearchChoicesBox.getSelectedIndex() == U_ABILITIES )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( U_MONSTER_ABILITIES + currentText +
						CONCLUDE_LIKE_QUERY, "Unique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for a location query
			else if( uniqueSearchChoicesBox.getSelectedIndex() == U_LOCATION )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( U_MONSTER_LOCATION + currentText +
						CONCLUDE_LIKE_QUERY, "Unique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
	
		}
		
		// if type is "SuperUnique"
		else if( monsterType == "SuperUnique" )
		{
			// for name queries
			if( superSearchChoicesBox.getSelectedIndex() == S_NAME )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( S_MONSTER_NAME + currentText +
						CONCLUDE_LIKE_QUERY, "SuperUnique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Super Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for ability query
			else if( superSearchChoicesBox.getSelectedIndex() == S_ABILITIES )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( S_MONSTER_ABILITIES + currentText +
						CONCLUDE_LIKE_QUERY, "SuperUnique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector			
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Super Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for hit points query
			else if( superSearchChoicesBox.getSelectedIndex() == S_HP )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( S_MONSTER_HP + currentText, 
						"SuperUnique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Super Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for required level query
			else if( superSearchChoicesBox.getSelectedIndex() == S_LEVEL )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( S_MONSTER_LEVEL + currentText, "SuperUnique" );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Unique Monster.");
				}
				else
					monsterVector.add( monsters );
	
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for experience query
			else if( superSearchChoicesBox.getSelectedIndex() == S_EXPERIENCE )
			{
				// create query object
				monsters =
					new MonsterQueryProcessor( S_MONSTER_EXPERIENCE + currentText, "SuperUnique" );
				if( monsters.tempEntity == null )
				{
					System.out.println( "No results in Unique Monster.");
				}
				else
					monsterVector.add( monsters );
				
				// if no results, report it to system screen for debugging,
				// otherwise, store the object in the vector
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
			
			// for difficulty level query
			else if( superSearchChoicesBox.getSelectedIndex() == S_DIFFICULTY )
			{
				// change text to uppercase
				currentText = currentText.toUpperCase();
				
				// check for normal difficulty
				if( currentText.matches( "[A-Za-z]*NORMAL[A-Za-z]*" ))
				{
					// create query object and store it
					monsters =
						new MonsterQueryProcessor( S_MONSTER_DIFF_NORMAL, 
						"SuperUnique" );
					monsterVector.add( monsters );
				}
				
				// check for nightmare difficulty
				else if( currentText.matches( "[A-Za-z]*NIGHTMARE[A-Za-z]*" ))
				{
					// create query object and store it
					monsters =
						new MonsterQueryProcessor( S_MONSTER_DIFF_NIGHT, 
						"SuperUnique" );
					monsterVector.add( monsters );
				}
				
				// check for hell difficulty
				else if( currentText.matches( "[A-Za-z]*HELL[A-Za-z]*" ))
				{
					// create query object and store it
					monsters =
						new MonsterQueryProcessor( S_MONSTER_DIFF_HELL, 
						"SuperUnique" );
					monsterVector.add( monsters );
				}
				
				// Display this to user if no results
				if( monsterVector.isEmpty() )
				{
					JOptionPane.showMessageDialog( null, 
						"Search produced no results." );
				}
			}
		}
		
		// return the vector
		return monsterVector;
	}
}
