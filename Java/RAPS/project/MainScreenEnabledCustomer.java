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

public class MainScreenEnabledCustomer extends JFrame implements ActionListener
{
	protected ImageIcon systemLogo;
		
	protected Container mainPane;
	
	protected JPanel 	buttonPanel;
						
	protected JButton 	searchButton, 
						loginButton,
						browseButton;
						
	
	protected JMenu 	fileMenu, accountMenu, aboutMenu, cartMenu,
						returnMenu, reportMenu;
		
	protected JMenuItem	aboutItem, exitItem, logoffItem,
						cartItem,  
						customerServiceItem,
						reportItem, updateAccountItem;
		
	protected JMenuBar 	bar;
	
	protected AboutPage aboutPage;
	protected BrowsePage browsePage;
	protected Cart myCart;
	protected Customer myCustomer;
	protected MainScreenEnabledCustomer thisPage;
	
	public MainScreenEnabledCustomer( RetrieveCustomerID customer )
	{
		super("Welcome to RAPS-Customer Enabled");
		
		thisPage = this;
		myCustomer = new Customer( customer );
		System.out.println( "First init: " + myCustomer.login );
		myCart = new Cart();
		JOptionPane.showMessageDialog( null, "Welcome to RAPS Customer Mode, " +
			customer.name );

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
			
		JMenu cartMenu = new JMenu( "Cart" );
		cartMenu.setMnemonic( 'C' );
			
		JMenu accountMenu = new JMenu( "Account" );
		accountMenu.setMnemonic( 'A' );
			
		JMenu reportMenu = new JMenu( "Report" );
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
			
		final JMenuItem cartItem = new JMenuItem( "View Cart" );
		cartItem.setMnemonic( 'c' );
		cartMenu.add( cartItem );
			
		JMenuItem addPartItem = new JMenuItem( "Add Part" );
		cartItem.setMnemonic( 'a' );
		cartMenu.add( addPartItem );
		
		JMenuItem removePartItem = new JMenuItem( "Remove Part" );
		removePartItem.setMnemonic( 'r' );
		cartMenu.add( removePartItem );
		
		JMenuItem checkoutItem = new JMenuItem( "Checkout" );
		checkoutItem.setMnemonic( 'h' );
		cartMenu.add( checkoutItem );
			
		JMenuItem updateAccountItem = new JMenuItem( "Update Account" );
		updateAccountItem.setMnemonic( 'u' );
		accountMenu.add( updateAccountItem );
			
		JMenuItem customerServiceItem = 
			new JMenuItem( "Customer Request Form" );
		customerServiceItem.setMnemonic( 's' );
		accountMenu.add( customerServiceItem );
			
		JMenuItem reportItem = new JMenuItem( "Report" );
		reportItem.setMnemonic( 'r' );
		reportMenu.add( reportItem );
			
		// menu bar
		JMenuBar bar = new JMenuBar();
		setJMenuBar( bar );
		bar.add( fileMenu );
		bar.add( accountMenu );
		bar.add( cartMenu );
		bar.add( reportMenu );
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
		
		cartItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					myCart.printContents();
				}
			}
		);
		
		addPartItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					new AddPartToCart( myCart );
				}
			}
		);
		
		removePartItem.addActionListener(
		
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
					myCart.removePart( partNumber );
				}
			}
		);
		
		checkoutItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					if( myCart.isEmpty )
					{
						JOptionPane.showMessageDialog( null, "The cart is " +							"empty.", "Cart Empty", JOptionPane.ERROR_MESSAGE );
					}
					else
					{
						ChooseOptions options = new ChooseOptions( myCart, myCustomer);
						cartItem.disable();
					}
					
				}
			}
		);
		updateAccountItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					new UpdateCustomerPage( myCustomer, thisPage );
				}
			}
		);
		reportItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					new CustomerReport( myCustomer );
				}
			}
		);
		customerServiceItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					new CustomerService( myCustomer.login );
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
