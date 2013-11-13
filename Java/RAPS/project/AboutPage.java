/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;

public class AboutPage extends JFrame
{
	private JTextArea display;
	private Container container;
	private JPanel messagePanel;
	private JMenu fileMenu;
	private JMenuItem exitItem;
	private JMenuBar bar;
	
	public AboutPage() 
	{
		super( "About RAPS");
		
		String info = "The Retail Auto Parts System (RAPS) is a software" +
		
		JTextArea display = new JTextArea();	
		display.setText( info );
		display.setEditable( false );
		display.setWrapStyleWord( true );
		display.setLineWrap( true );
		
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout( new BorderLayout( 4, 4 ));
		messagePanel.add( new JScrollPane( display ));
		
		Container container = new Container();
		container = getContentPane();
		container.add( messagePanel, BorderLayout.CENTER );
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar( bar );
		
		bar.add( fileMenu );
		
		
		setSize( 340, 340 );
		setVisible( true );
		setResizable( false );
		validate();
	}
	
	
}