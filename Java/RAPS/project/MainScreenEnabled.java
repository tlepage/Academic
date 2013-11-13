/*
 * Created on Nov 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MainScreenEnabled extends JFrame implements ActionListener
{
	
	protected ImageIcon systemLogo;
		protected Container mainPane;
	
		protected JPanel 	buttonPanel;
						
		protected JTextField userField;
		protected JPasswordField passwordField;
	
		protected JButton 	searchButton, 
							loginButton,
							browseButton;
						
	
		protected JMenu 	fileMenu, loginMenu, aboutMenu;
		protected JMenuItem	aboutItem, exitItem, newUserItem, loginItem;
		protected JMenuBar 	bar;
	
		protected AboutPage aboutPage;
		
		
		public MainScreenEnabled()
		{
			super("Welcome to RAPS");
		
			// main pane
			mainPane = getContentPane();
			mainPane.setBackground( new Color(0, 0, 0) );
			mainPane.setLayout( new BorderLayout() );
		
			// logo
			systemLogo = new ImageIcon("logo.jpg");
		
			// menu
			JMenu fileMenu = new JMenu( "File" );
			fileMenu.setMnemonic( 'F' );
			JMenu loginMenu = new JMenu( "Login" );
			loginMenu.setMnemonic( 'L' );
			JMenu aboutMenu = new JMenu( "About" );
			aboutMenu.setMnemonic( 'A' );
		
			JMenuItem aboutItem = new JMenuItem( "About..." );
			aboutItem.setMnemonic( 'a' );
			aboutMenu.add( aboutItem );
		
			JMenuItem exitItem = new JMenuItem( "Exit" );
			exitItem.setMnemonic( 'x' );
			fileMenu.add( exitItem );
		
			JMenuItem newUserItem = new JMenuItem( "New User" );
			newUserItem.setMnemonic( 'n' );
			loginMenu.add( newUserItem );
		
			JMenuItem loginItem = new JMenuItem( "Login" );
			loginItem.setMnemonic( 'l' );
			loginMenu.add( loginItem ); 
		
			JMenuBar bar = new JMenuBar();
			setJMenuBar( bar );
			bar.add( fileMenu );
			bar.add( loginMenu );
			bar.add( aboutMenu );
		
			// panels
			buttonPanel = new JPanel();
			buttonPanel.setLayout( new GridLayout( 1, 3 ));
		
			// buttons
			searchButton = new JButton("Search For Parts");
			searchButton.addActionListener( this );
			browseButton = new JButton("Browse");
			browseButton.addActionListener( this );
		
			// add components
			buttonPanel.add( searchButton );
			buttonPanel.add( browseButton );
		
			mainPane.add( buttonPanel, BorderLayout.SOUTH );
	
			// Event Handling
			exitItem.addActionListener( 
			
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						System.exit(0);
					}
				}
			);
		
			aboutItem.addActionListener(
			
				new ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						AboutPage aboutPage = new AboutPage();
					}
				}
			);
			
			
			mainPane.validate();
		
			setSize(400, 400 );
			setResizable(false);
			show();
		}
	
		public void paint( Graphics g )
		{
			super.paint(g);
			systemLogo.paintIcon( mainPane, g, 0, 100 );
		}	
	
		public void actionPerformed( ActionEvent e )
		{
		
		}

}
