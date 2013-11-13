/*
 * Created on Nov 2, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainScreenDefault extends JFrame implements ActionListener
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
	protected BrowsePage browsePage;
	protected LoginPage loginPage;
	
	protected MainScreenDefault page;
	
	public MainScreenDefault()
	{
		super("Welcome to RAPS - Default");
		
		page = this;
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
		aboutMenu.setMnemonic( 'B' );
		
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
		
		loginItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
							
					new LoginPage(page);
				}
			}
		);
		newUserItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					NewUserPage newUser = new NewUserPage();
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
		if( e.getSource() == browseButton )
		{
			BrowsePage browsePage = new BrowsePage();
		}
		if( e.getSource() == searchButton )
		{
			SearchPage searchPage = new SearchPage();
		}
	}
	public void closePage()
	{
		dispose();
	}
	public static void main( String args[] )
	{
		MainScreenDefault application = new MainScreenDefault();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
