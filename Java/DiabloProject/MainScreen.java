//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// MainScreen.java
//-----------------------------------------------------------------------------

/*-----------------------------------------------------------------------------

Class:      MainScreen
Purpose:    To handle the operation of the program
Extends:    JFrame
Implements: DiabloDefines
Parameters: N/A

-----------------------------------------------------------------------------*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainScreen extends JFrame implements DiabloDefines
{
	protected JButton itemSearchButton,
				      monsterSearchButton;
	protected Container container;
	protected JLabel logoLabel;
	protected Icon logoIcon;

//-----------------------------------------------------------------------------
// Function: MainScreen()
// Purpose:  class constructor; acts as a delegate for what actions take place
// Input:    N/A
// Output:   N/A
//	-----------------------------------------------------------------------------
	public MainScreen()
	{
		super( "Diablo II: Lord Of Destruction Database" );
		
		// setup container
		container = getContentPane();
		container.setLayout( new FlowLayout() );
		container.setBackground( BLACK );
		
		// create logo
		logoIcon = new ImageIcon( IMAGE_LOCATION + "wall20.jpg" );
		
		// create and add logo label
		logoLabel = new JLabel( logoIcon );
		container.add( logoLabel );
		
		// create and add item search button
		itemSearchButton = new JButton( "Item Search" );
		container.add( itemSearchButton );
		
		// create and add monster search button
		monsterSearchButton = new JButton( "Monster Search" );
		container.add( monsterSearchButton );
		
		// create listener for item search button
		itemSearchButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					// call search screen for item
					new SearchScreenItem();
				}
			}
		);
		
		// create listener for monster search button
		monsterSearchButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					// call search screen for monster
					new SearchScreenMonster();
				}
			}
		);
		
		// make visible and display
		setSize( 700, 550 );
		setVisible( true );
		
		show();
	}

//-----------------------------------------------------------------------------
// Function: main()
// Purpose:  to run the application
// Input:    command line strings
// Output:   N/A
//-----------------------------------------------------------------------------
	public static void main( String args[] )
	{
		MainScreen app = new MainScreen();
		app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
