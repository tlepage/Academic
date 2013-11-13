/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchPage extends JFrame
{
	protected SearchPage thisPage;
	
	public SearchPage()
	{
		super( "Search RAPS");
		
		thisPage = this;
		String choices[] = { "Part Name", "Vehicle Model", "Vehicle Make",
			"Vehicle Year", "Vehicle Engine" };
		final JComboBox choiceBox = new JComboBox(choices);
		final JTextField searchField = new JTextField( 10 );
		JButton submit = new JButton("Submit");
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		submit.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( searchField.getText() == null )
					{
						JOptionPane.showMessageDialog( null, 
							"Must enter a search request" );
						return;
					}
					else
					{
						String searchRequest = searchField.getText();
						new SearchDriver( searchRequest, choiceBox.getSelectedIndex() );
						thisPage.hide();
					}
				}
			}
		);
		container.add( choiceBox );
		container.add( searchField );
		container.add( submit );
		
		setSize( 300, 100 );
		setVisible( true );
		
	}
}
