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

public class MainScreenEnabledTrainee extends JFrame implements ActionListener
{
	protected ImageIcon systemLogo;
		
	protected Container mainPane;
	
	protected JPanel 	buttonPanel;
						
	protected JButton 	searchButton, 
						loginButton,
						browseButton;
						
	
	protected JMenu 	fileMenu, accountMenu, aboutMenu, reportMenu,
						mockCheckoutMenu;
		
	protected JMenuItem	aboutItem, exitItem, logoffItem, 
						reportPartItem, reportTraineeItem, checkoutItem; 
							
	protected JMenuBar 	bar;
	
	protected AboutPage aboutPage;
	protected BrowsePage browsePage;
	protected Trainee myTrainee;
	protected MainScreenEnabledTrainee thisPage;
	protected ItemList itemList;
	public MainScreenEnabledTrainee( RetrieveEmployeeID trainee )
	{
		super("Welcome to RAPS-Trainee Enabled");
		
		thisPage = this;
		itemList = new ItemList();
		myTrainee = new Trainee( trainee );
		JOptionPane.showMessageDialog( null, "Welcome to RAPS Store Mode, " +
			trainee.name );
		// main pane
		mainPane = getContentPane();
		mainPane.setBackground( new Color(0, 0, 0) );
		mainPane.setLayout( new BorderLayout() );
		
		// logo
		systemLogo = new ImageIcon("logo.jpg");
		
		// menu
		JMenu fileMenu = new JMenu( "File" );
		fileMenu.setMnemonic( 'F' );
			
		JMenu aboutMenu = new JMenu( "About" );
		aboutMenu.setMnemonic( 'B' );
			
		JMenu accountMenu = new JMenu( "Account" );
		accountMenu.setMnemonic( 'A' );
		
		JMenu mockCheckoutMenu = new JMenu( "Practice Checkout" );
			
		JMenu reportMenu = new JMenu( "Reports" );
		reportMenu.setMnemonic( 'P' );
		
		// menu items
		JMenuItem aboutItem = new JMenuItem( "About..." );
		aboutItem.setMnemonic( 'a' );
		aboutMenu.add( aboutItem );
		
		JMenuItem logoffItem = new JMenuItem( "Logoff" );
		logoffItem.setMnemonic( 'l' );
		fileMenu.add( logoffItem );
			
		JMenuItem exitItem = new JMenuItem( "Exit" );
		exitItem.setMnemonic( 'x' );
		fileMenu.add( exitItem );
			
			
		JMenuItem reportPartItem = new JMenuItem( "Item Report" );
		reportPartItem.setMnemonic( 'p' );
		reportMenu.add( reportPartItem );
		
		JMenuItem checkoutItem = new JMenuItem( "Mock Checkout" );
		mockCheckoutMenu.add( checkoutItem );	
		
		JMenuItem viewListItem = new JMenuItem( "View List" );
		mockCheckoutMenu.add( viewListItem );
	
		JMenuItem removeListItem = new JMenuItem( "Remove Part From List" );
		mockCheckoutMenu.add( removeListItem );
		
		JMenuItem addListItem = new JMenuItem( "Add Part To List" );
		mockCheckoutMenu.add( addListItem );
		
		JMenuItem viewInventoryItem = new JMenuItem( "View Inventory" );
		mockCheckoutMenu.add( viewInventoryItem );
		// menu bar
		JMenuBar bar = new JMenuBar();
		setJMenuBar( bar );
		bar.add( fileMenu );
		bar.add( reportMenu );
		bar.add( mockCheckoutMenu );
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
					dispose();
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
		reportPartItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					new ItemReport();
				}
			}
		);
		viewListItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					itemList.printContents();
				}
			}
		);
		removeListItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					String qString = null;
					try
					{
						qString = JOptionPane.showInputDialog( 
							"Enter list ID: " );
					}
					catch( Exception e )
					{
						JOptionPane.showMessageDialog( null, "Invalid Request" );
						return;
					}	
					int partNumber = Integer.parseInt( qString );
					itemList.removePart( partNumber );
				}
			}
		);
		
		checkoutItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					if( itemList.isEmpty )
					{
						JOptionPane.showMessageDialog( null, "The list is " +
							"empty.", "List Empty", JOptionPane.ERROR_MESSAGE );
					}
					else
					{
						new ChooseStorePaymentTrainee( itemList, Double.toString(itemList.total),
							myTrainee.login, myTrainee.storeNumber );
					}
				}
			}
		);
		addListItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					new AddPartToList( itemList, myTrainee.storeNumber );
				}
			}
		);
		logoffItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					thisPage.hide();
					new MainScreenDefault();
				}
			}
		);
		viewInventoryItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					new ViewParts( myTrainee.storeNumber );
				}
			}
		);
		mainPane.validate();
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
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
	
	
}
