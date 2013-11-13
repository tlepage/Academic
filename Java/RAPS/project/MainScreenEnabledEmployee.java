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

public class MainScreenEnabledEmployee extends JFrame implements ActionListener
{
	protected ImageIcon systemLogo;
		
	protected Container mainPane;
	
	protected JPanel 	buttonPanel;
						
	protected JButton 	searchButton, 
						loginButton,
						browseButton;
						
	
	protected JMenu 	fileMenu, accountMenu, aboutMenu, checkoutMenu, 
						returnMenu, reportMenu, orderMenu;
		
	protected JMenuItem	aboutItem, exitItem, logoffItem,
						checkoutItem, returnItem, 
						reportPartItem, reportEmployeeItem, 
						requestItem, orderItem, updateAccountItem,
						customerAccountItem, viewInventoryItem;
		
	protected JMenuBar 	bar;
	
	protected AboutPage aboutPage;
	protected BrowsePage browsePage;
	protected Employee myEmployee;
	protected ItemList itemList;
	protected MainScreenEnabledEmployee thisPage;
	public MainScreenEnabledEmployee( RetrieveEmployeeID employee )
	{
		super("Welcome to RAPS-Employee Enabled");
		
		thisPage = this;
		myEmployee = new Employee( employee );
		itemList = new ItemList();
		JOptionPane.showMessageDialog( null, "Welcome to RAPS Store Mode, " +
			employee.name );
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
			
		JMenu checkoutMenu = new JMenu( "Checkout" );
		checkoutMenu.setMnemonic( 'C' );
		
		JMenu returnMenu = new JMenu( "Return" );
		returnMenu.setMnemonic( 'R' );
			
		JMenu accountMenu = new JMenu( "Accounts" );
		accountMenu.setMnemonic( 'A' );
			
		JMenu reportMenu = new JMenu( "Reports" );
		reportMenu.setMnemonic( 'P' );
			
		JMenu orderMenu = new JMenu( "Order" );
		orderMenu.setMnemonic( 'O' );
		
			
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
			
		JMenuItem checkoutItem = new JMenuItem( "Checkout" );
		checkoutItem.setMnemonic( 'c' );
		checkoutMenu.add( checkoutItem );
			
		JMenuItem returnItem = new JMenuItem( "Return Part" );
		returnItem.setMnemonic( 'r' );
		returnMenu.add( returnItem );
			
		JMenuItem customerAccountItem = 
			new JMenuItem( "Create Customer Account" );
		customerAccountItem.setMnemonic( 'c' );
		accountMenu.add( customerAccountItem );
			
		JMenuItem orderItem = new JMenuItem( "Order Part" );
		orderItem.setMnemonic( 'o' );
		orderMenu.add( orderItem );
			
		JMenuItem requestItem = 
			new JMenuItem( "Employee Request Form" );
		requestItem.setMnemonic( 'f' );
		orderMenu.add( requestItem );
			
		JMenuItem reportPartItem = new JMenuItem( "Item Report" );
		reportPartItem.setMnemonic( 'p' );
		reportMenu.add( reportPartItem );
			
		JMenuItem reportEmployeeItem = new JMenuItem( "Employee Report" );
		reportEmployeeItem.setMnemonic( 'e' );
		reportMenu.add( reportEmployeeItem );
		
		JMenuItem viewListItem = new JMenuItem( "View List" );
		checkoutMenu.add( viewListItem );
		
		JMenuItem removeListItem = new JMenuItem( "Remove Part From List" );
		checkoutMenu.add( removeListItem );
		
		JMenuItem addListItem = new JMenuItem( "Add Part To List" );
		checkoutMenu.add( addListItem );
		
		JMenuItem viewInventoryItem = new JMenuItem( "View Inventory" );
		checkoutMenu.add( viewInventoryItem );
		// menu bar
		JMenuBar bar = new JMenuBar();
		setJMenuBar( bar );
		bar.add( fileMenu );
		bar.add( accountMenu );
		bar.add( checkoutMenu );
		bar.add( orderMenu );
		bar.add( reportMenu );
		bar.add( returnMenu );
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
		
		orderItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					OrderPage orderPage = new OrderPage(myEmployee.storeNumber,
						myEmployee.login );
				}
			}
		);
		
		requestItem.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					RequestForm request = new RequestForm();
				}
			}
		);
		
		customerAccountItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					NewUserPage newUser = new NewUserPage();
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
		reportEmployeeItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					new EmployeeReport( myEmployee );
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
		addListItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					new AddPartToList( itemList, myEmployee.storeNumber );
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
						new ChooseStorePayment( itemList, Double.toString(itemList.total),
							myEmployee.login, myEmployee.storeNumber );
					}
				}
			}
		);
		returnItem.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					String order = null, q = null;
					try
					{
						order = JOptionPane.showInputDialog( "Please enter " +
							"transaction number" );
						q = JOptionPane.showInputDialog( "Please enter " +
							"quantity" );
					}
					catch( Exception ex )
					{
						JOptionPane.showMessageDialog( null, "Invalid transaction" +
							" request" );
						return;
					}
								
					int orderNumber = 0;
					int quantity = 0;
								
					try
					{
						orderNumber = Integer.parseInt( order );
						quantity = Integer.parseInt( q );
						if( quantity < 0 ) quantity *= -1;
						System.out.println( "Quantity on return: " + quantity );
					}
					catch( Exception ex )
					{
						JOptionPane.showMessageDialog( null, "Invalid transaction " +
							"request" );
						return;
					}
					new ReturnPart( orderNumber, myEmployee.storeNumber, quantity );
					
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
					new ViewParts( myEmployee.storeNumber );
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
