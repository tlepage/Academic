//-----------------------------------------------------------------------------
// Thomas LePage
// CSE 3320
// Database Project
// Spring 2004
// ResultScreenMonster.java
//-----------------------------------------------------------------------------
/*-----------------------------------------------------------------------------

Class:      ResultScreenMonster
Purpose:    To display all monster results from the database
Extends:    JFrame
Implements: DiabloDefines
Parameters: Vector containing all monster results

-----------------------------------------------------------------------------*/
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class ResultScreenMonster extends JFrame implements DiabloDefines
{
	protected JButton rateButton;
	protected MonsterQueryProcessor tempMonster;
	protected MonsterEntity temp;

//-----------------------------------------------------------------------------
// Function: ResultScreenMonster()
// Purpose:  class constructor
// Input:    a Vector containing MonsterEntity objects
// Output:   N/A
//-----------------------------------------------------------------------------	
	public ResultScreenMonster( Vector monsterObj )
	{
		super( "Monster Search Results" );
 		
 		// setup tabbed pane
		JTabbedPane test = new JTabbedPane( JTabbedPane.TOP,
			JTabbedPane.SCROLL_TAB_LAYOUT );
		
		// create the JPanel
		JPanel panel = new JPanel();
			
		// for each object in the vector, do the following
		for( int t = 0; t < monsterObj.size(); t++ )
		{	
			// get a MonsterQueryProcessor object from the Vector
			tempMonster = (MonsterQueryProcessor)monsterObj.get(t);
	
			// for each MonsterEntity object in the sub-Vector
			for( int i = 0; i < tempMonster.getVectorSize(); i++ )
			{
				temp = (MonsterEntity)tempMonster.getEntity(i);
				// set up individual panel
				panel = new JPanel();
				// set up rating button
				rateButton = new JButton( "Rate This Monster" );
			
				// create image location string
				String location = IMAGE_LOCATION + tempMonster.getEntityImage(i);
				
				// load image onto label
				JLabel imageLabel = new JLabel( new ImageIcon( location ));
				
				// set background color
				panel.setBackground( BLACK );
				
				// add label to panel
				panel.add( imageLabel );
				
				// add textarea to panel
				panel.add( new TextArea( tempMonster.getMonsterInformation(i) ));
				
				// add the listener for the rating button
				 rateButton.addActionListener(

					 new ActionListener()
					 {
						 public void actionPerformed( ActionEvent e )
						 {
							 // call the MonsterRate object constructor
							 MonsterRate rate = new MonsterRate( temp );
							 System.out.println(temp.monsterID);
						 }
					 }
				 );
				
				// add rating button
				panel.add( rateButton );
				
				// add tab to tabbedPane
				
				// if monster type is "SuperUnique"
				if( tempMonster.monsterType == "SuperUnique" )
				{
					test.addTab( tempMonster.getEntityName(i) + "-" +
						tempMonster.getEntityDifficultyLevel(i), panel );
				}
				// if monster type is "Unique"
				else
				{
					test.addTab( tempMonster.getEntityName(i), panel );
				}
			}
		}
		

		// add tabbed pane to container
		getContentPane().add( test );
		
		// set size and make visible
		setSize( 1024, 768 );
		setVisible( true );
		
		show();
	}
}
