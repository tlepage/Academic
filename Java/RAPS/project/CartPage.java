/*
 * Created on Nov 9, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class CartPage extends JFrame
{
	protected JTextArea cartContents;
	protected JTextField totalDisplay;
	protected JButton okButton;
	protected CartPage thisPage;
	protected DecimalFormat twoDigits;
	
	public CartPage(String output, double total )
	{
		super( "Shopping Cart" );
		twoDigits = new DecimalFormat( "0.00" );
		thisPage = this;
		Container container = getContentPane();
		container.setLayout( new FlowLayout());
		cartContents = new JTextArea(20, 30);
		
		okButton = new JButton( "Ok" );
		cartContents.setAutoscrolls( true );
		cartContents.setLineWrap( true );
		cartContents.setWrapStyleWord( true );
		cartContents.setText("---Shopping Cart Contents---\n\n");
		totalDisplay = new JTextField(10);
		container.add( new JScrollPane( cartContents ));
		container.add( new JLabel("Total: $") );
		container.add( totalDisplay );
		container.add( okButton );
		String totalString = twoDigits.format( total );
		totalDisplay.setText( totalString );
		cartContents.append( output );
		cartContents.setEditable( false );
		totalDisplay.setEditable( false );
				
		okButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					thisPage.hide();
				}
			}
		);
		setSize( 650, 400 );
		setVisible( true );
		setResizable( false );
		show();
		
	}
}
