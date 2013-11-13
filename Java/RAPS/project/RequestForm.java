/*
 * Created on Nov 7, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;

public class RequestForm extends JFrame
{
	private JTextArea input;
	private JButton submit;
	private JTextField storeNumber;
	private JTextField store;
	
	private OrderTest order;
	
	public RequestForm()
	{
		super("Item Request Form");
		
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		JLabel store = new JLabel( "Store Number ");
		JTextField storeNumber = new JTextField(10);
		JTextArea input = new JTextArea( 10, 20 );
		input.setWrapStyleWord( true );
		input.setLineWrap( true );
		JButton submit = new JButton("Submit Request");
		container.add( store );
		container.add( storeNumber );
		container.add( new JScrollPane( input ));
		container.add( submit );
	
		setSize( 300, 260 );
		setResizable( false );
		setVisible( true );
		validate();
	}
	
}
