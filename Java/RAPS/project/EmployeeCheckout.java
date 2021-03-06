/*
 * Created on Nov 15, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;


public class EmployeeCheckout extends JFrame 
{	
	protected final int QUANTITY_MAX = 50;
	protected JTextArea purchaseWindow;
	protected JTextField amount;
	protected JTextField partNumber;
	protected JLabel amountLabel;
	protected JLabel partLabel;
	protected JButton add, cancel;
	protected JTextField total;
	protected JButton purchase;
	protected RetrievePartRequestEmployeeSKU items[];
	protected int purchaseQuantity[];
	protected Connection connection;
	protected Statement stmt;
	protected int currentAmount = 0, quantity, store;
	protected String sku;
	protected RetrievePartRequestEmployeeSKU request;
	protected boolean flag = false;
	protected double totalPrice = 0.0;
	protected String finalPrice = null, currentItem = null;
	protected String myID = null;
	protected EmployeeCheckout thisPage;
	protected DecimalFormat twoDigits;
	boolean isEmpty = true, isSame = false;
	public EmployeeCheckout( int storeNumber, String ID )
	{
		super( "Checkout Customer" );
		
		twoDigits = new DecimalFormat( "0.00" );
		thisPage = this;
		items = new RetrievePartRequestEmployeeSKU[QUANTITY_MAX];
		purchaseQuantity = new int[QUANTITY_MAX];
		
		myID = ID;
		store = storeNumber;
		
		for( int i = 0; i < QUANTITY_MAX; i++ )
		{
			purchaseQuantity[i] = 0;
		}
		
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		purchaseWindow = new JTextArea( 20, 20 );
		purchaseWindow.setEditable( false );
		purchaseWindow.setLineWrap( true );
		purchaseWindow.setWrapStyleWord( true );
		purchaseWindow.setAutoscrolls( true );
		purchaseWindow.setText( "---Items---\n\n");
		
		amountLabel = new JLabel("Amount: $");
		partLabel = new JLabel("SKU: ");
		
		amount = new JTextField( 5 );
		partNumber = new JTextField( 5 );
		
		total = new JTextField( 10 );
		total.setEditable( false );
		
		add = new JButton("Add Part");
		purchase = new JButton("Purchase");
		cancel = new JButton( "Cancel" );
		
		add.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					try
					{
						sku = JOptionPane.showInputDialog( "Please enter the SKU: " );
					}
					catch( Exception ex)
					{
						sku = "0";
					}	
			
					request = new RetrievePartRequestEmployeeSKU( sku, store );
			
					if( request.pName == null )
					{
						JOptionPane.showMessageDialog( null, "Invalid SKU", 
							"Error", JOptionPane.ERROR_MESSAGE );
					}
					else	
					{		
						String output = "\nPart Number: " + request.pNumber +
							"\nPart Name: " + request.pName + "\nVehicle Number: " + 
							request.vNumber + "\nCategory: " + request.pCategory + 
							"\nCost: $" + request.pCost + "\nCondition: " + request.pCond + 
							"\nDescription: " + request.pDesc;
					
						do
						{	
							if( request.pQuantity == 0 )
							{
								JOptionPane.showMessageDialog( null, "No parts left in" +
									" inventory.", "Out of Part", 
									JOptionPane.INFORMATION_MESSAGE );
								flag = false;
								break;
							}
							else
							{
								String qString = "0";
								try
								{
									qString = JOptionPane.showInputDialog( "Please enter quantity" );	
								}
								catch( Exception ex )
								{
									qString = "0";
									JOptionPane.showMessageDialog( null, "Invalid Quantity" );
									return;
								}
								try
								{
									quantity = Integer.parseInt( qString );
								}
								catch(Exception ex )
								{
									JOptionPane.showMessageDialog( null, "Invalid Quantity" );
									return;
								}
								if( quantity > request.pQuantity || quantity <= 0 )
								{
									JOptionPane.showMessageDialog( null, "Please enter a quantity" +
										" between 1 and " + request.pQuantity + ":", 
										"Error", JOptionPane.ERROR_MESSAGE );
									flag = false;
								}
								else
									flag = true;
						
							}
						}while( quantity > request.pQuantity || quantity == 0 );
				
						if( flag == true)
						{
							System.out.println( "isSame = " + isSame );
							if( currentAmount != 0 )
							{ 
								System.out.println( "Current Amount greater than 0");
								for( int i = currentAmount - 1; i > -1; i-- )
								{
									System.out.println( "Current Amount: " + currentAmount );
									System.out.println( "PartNumber for parts[i]: " + items[i].pNumber );
									System.out.println( "PartNumber for request: " + request.pNumber );
									if( items[i].pNumber.equals(request.pNumber) )
									{
										purchaseQuantity[i] += quantity;
										System.out.println( "Same part found. Adding to previous." );
										totalPrice += items[i].pCost * purchaseQuantity[i];
										purchaseWindow.setText("");
										currentItem += "\nPart " + currentAmount + ": " + items[i].pNumber
											+ " " + items[i].pName + " " + items[i].vNumber + " $" +
											items[i].pCost + " " + purchaseQuantity[i] + 
											"  Total: $" + 
											twoDigits.format(items[i].pCost * purchaseQuantity[i]);
										purchaseWindow.append( currentItem );
										finalPrice = twoDigits.format( totalPrice );
										total.setText( finalPrice );
										isSame = true;
									}
								}
							}
							if( !isSame )
							{
								System.out.println( "Not same part." );
								
								items[currentAmount] = request;
								purchaseQuantity[currentAmount] = quantity;
								totalPrice += items[currentAmount].pCost * purchaseQuantity[currentAmount];
					
								currentItem += "\nPart " + currentAmount + ": " + items[currentAmount].pNumber
									+ " " + items[currentAmount].pName + " " + items[currentAmount].vNumber + " $" +
									items[currentAmount].pCost + " " + purchaseQuantity[currentAmount] + 
									"  Total: $" + 
									twoDigits.format(items[currentAmount].pCost * purchaseQuantity[currentAmount]);
					
								purchaseWindow.append( currentItem );
							
								finalPrice = twoDigits.format( totalPrice );
								total.setText( finalPrice );
								currentAmount++;
					
							}
							
						}
					}
				}
			}
		);
		purchase.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( currentAmount != 0)
					{
						purchaseWindow.setText("");
						thisPage.hide();
					}
					else
					{
						JOptionPane.showMessageDialog( null, "No parts" );
					}
				}
			}
		);
		cancel.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					currentAmount = 0;
					purchaseWindow.setText("");
					thisPage.hide();
				}
			}
		);
		container.add( purchaseWindow );
		container.add( add );
		container.add( purchase );
		
		container.add( amountLabel );
		container.add( total );
		container.add( cancel );
		setSize( 350, 450 );
		setResizable( false );
		setVisible( true );
		show();
		
	}
	
}
