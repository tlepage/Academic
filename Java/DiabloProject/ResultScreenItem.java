//-----------------------------------------------------------------------------
//Thomas LePage
//CSE 3320
//Database Project
//Spring 2004
//ResultScreenItem.java
//-----------------------------------------------------------------------------
/*-----------------------------------------------------------------------------

Class:      ResultScreenItem
Purpose:    To display all results which the database returns
Extends:    JFrame
Implements: DiabloDefines
Parameters: Vector containing all item objects

-----------------------------------------------------------------------------*/
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class ResultScreenItem extends JFrame implements DiabloDefines
{
	protected JButton rateButton;
	protected ItemQueryProcessor tempItem;
	
//-----------------------------------------------------------------------------
// Function:
// Purpose:
// Input:
// Output:
//	-----------------------------------------------------------------------------
	public ResultScreenItem( Vector itemObj )
	{
		super( "Item Search Results" );
		
		// setup tabbed pane
		JTabbedPane test = new JTabbedPane( JTabbedPane.TOP,
			JTabbedPane.SCROLL_TAB_LAYOUT );
		
		// create JPanel
		JPanel panel = new JPanel();
		
		for( int t = 0; t < itemObj.size(); t++ )
		{
			// get temporary item from vector
			tempItem = (ItemQueryProcessor)itemObj.get(t);
			
			for( int i = 0; i < tempItem.getVectorSize(); i++ )
			{	
				// set up individual panel
				panel = new JPanel();
				
				// set up rating button
				rateButton = new JButton( "Rate This Item" );
				
				// create rating button action listener
				rateButton.addActionListener(
				
					new ActionListener()
					{
						public void actionPerformed( ActionEvent e )
						{
							ItemRate rate = new ItemRate( tempItem.tempEntity );
						}
					}
				);
				
				// create image location string
				String location = ITEM_IMAGE_LOCATION + tempItem.getEntityImage(i);
				System.out.println( location );
				
				// load image onto label
				JLabel imageLabel = new JLabel( new ImageIcon( location ));
				
				// set background color
				panel.setBackground( BLACK );
				
				// add label to panel
				panel.add( imageLabel );
				
				// add textarea to panel
				panel.add( new TextArea( tempItem.getItemInformation(i) ));
				
				// add button to panel
				panel.add( rateButton );
				
				// add tab to tabbedPane
				if( tempItem.isArmor(i) )
				{	
					// if the item is armor
					test.addTab( tempItem.getEntityName(i) + "-" +
						tempItem.getEntityArmorType(i) , panel );
				}
				else
				{
					// if the item is a weapon
					test.addTab( tempItem.getEntityName(i) + "-" +
						tempItem.getEntityWeaponType(i) , panel );
				}
			}
		}
		
		// add tabbed pane to container
		getContentPane().add( test );

		// set size and make visible
		setSize( 460, 420 );
		setVisible( true );

		show();
	}
}
