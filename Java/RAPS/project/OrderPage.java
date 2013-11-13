/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderPage extends JFrame
{
	private JButton submitButton;
	private JTextField SKU, partName;
	private JCheckBox part, number;
	private JMenu fileMenu;
	private JMenuItem exitItem;
	private OrderTest order;
	protected int storeNumber;
	protected JLabel labelSKU;
	protected JLabel labelQuantity;
	protected String currentID;
	protected JCheckBox customerCheck, FEDEX, UPS, NORMAL, EXPRESS;
	String custID;
	protected DeliveryOptions d;
	protected JCheckBox deliverySet;
	int isCustomer = 0;
	String dCompany, dType;
	double dCost;
	protected OrderPage thisPage;
	
	public OrderPage( int store, String ID )
	{
		super( "Order Part" );
		thisPage = this;
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		currentID = ID;
		storeNumber = store;
		final JTextField SKU = new JTextField(10);
		final JTextField quantityField = new JTextField(5);
		final JButton submitButton = new JButton("Submit");
		labelSKU = new JLabel( "SKU" );
		deliverySet = new JCheckBox( "Set Delivery Options" );
		FEDEX = new JCheckBox("FEDEX");
		UPS = new JCheckBox( "UPS" );
		NORMAL = new JCheckBox( "NORMAL" );
		EXPRESS = new JCheckBox( "EXPRESS" );
		customerCheck = new JCheckBox( "Customer Order" );
		
		customerCheck.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( customerCheck.isSelected() )
					{
						isCustomer = 1;
						try
						{
							custID = 
								JOptionPane.showInputDialog( "Please enter customer ID ");
						}
						catch( Exception ex )
						{
							JOptionPane.showMessageDialog( null, "Invalid Entry " );
							return;
						}
					}
					else
					{
						isCustomer = 0;
						custID = null;
					}
				}
			}
		);
		FEDEX.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( UPS.isEnabled() )
					{
						UPS.setEnabled(false);
						UPS.setSelected(false);
					}
					else
					{
						UPS.setEnabled(true);
					}
				}
			}
		);
		UPS.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( FEDEX.isEnabled() )
					{
						FEDEX.setEnabled( false );
						FEDEX.setSelected(false);
					}
					else
					{
						FEDEX.setEnabled( true );
					}
				}
			}
		);
		NORMAL.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( EXPRESS.isEnabled() )
					{
						EXPRESS.setEnabled( false );
						
					}
					else
					{
						EXPRESS.setEnabled( true );
					}
				}
			}
		);
		EXPRESS.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( NORMAL.isEnabled() )
					{
						NORMAL.setEnabled( false );
						
					}
					else
					{
						NORMAL.setEnabled( true );
					}
				}
			}
		);
		deliverySet.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if(( FEDEX.isSelected() || UPS.isSelected()) && 
						( NORMAL.isSelected() || EXPRESS.isSelected()) )
					{
						if( FEDEX.isSelected() )
							dCompany = "FEDEX";
						else if( UPS.isSelected() )
							dCompany = "UPS";
							
						if( NORMAL.isSelected() )
						{
							dCost = 7.5;
							dType = "NORMAL";
						}
						else if( EXPRESS.isSelected() )
						{
							dCost = 10.0;
							dType = "EXPRESS";
						}
						
						if( !NORMAL.isSelected() && !EXPRESS.isSelected() )
						{
							deliverySet.setSelected( false );
						}
						else if( !FEDEX.isSelected() && !UPS.isSelected() )
						{
							deliverySet.setSelected( false );
						}
						else
						{
							d = new DeliveryOptions( dType, Integer.toString(storeNumber),
								dCompany, dCost );
						}
					}
					else
					{
						JOptionPane.showMessageDialog( null, "Please select " +							"delivery type and company" );
						deliverySet.setSelected( false );
						return;
					}
				}
			}
		);
		submitButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e)
				{
					String request = null;
					if( !SKU.getText().matches("\\d{6}")) 
					{
						JOptionPane.showMessageDialog( null, "Invalid SKU." );
						return;
					}
					if( !deliverySet.isSelected())
					{
						JOptionPane.showMessageDialog( null, "Must select" +							" delivery button." );
						return;
					}
					else if( deliverySet.isSelected() )
					{
						if( !((FEDEX.isSelected() || UPS.isSelected()) &&
							(NORMAL.isSelected() || EXPRESS.isSelected())))
						{
							JOptionPane.showMessageDialog( null, 
								"Must select delivery options." );
							return;
						}
					}
					
					request = SKU.getText();
						
					if( isCustomer == 1 )
						JOptionPane.showMessageDialog( null,
							custID );
					else
						custID = null;
							
						
					order = new OrderTest( request, storeNumber, currentID, custID,
						d, thisPage );
					
				}
				
			}
		);
		
		container.add( labelSKU );
		container.add( SKU );
		container.add( FEDEX );
		container.add( UPS );
		container.add( NORMAL );
		container.add( EXPRESS );
		container.add( deliverySet );
		
		container.add( customerCheck );
		container.add( submitButton );
		
		
		setSize( 500, 500 );
		setResizable( true );  
		setVisible( true );
		
	}
	
	
}
