/*
 * Created on Nov 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseOptions extends JFrame
{
	private final int VISA = 0;
	private final int MASTERCARD = 1;
	private final int AMERICANEXPRESS = 2;
	private final int DISCOVER = 3;
 	private JButton submit;
	protected JComboBox CCTypes;
	protected String types[] = { "Visa", "MasterCard", "American Express", "Discover" };
	private JLabel creditLabel;
	private JLabel deliverySite;
	protected JCheckBox cod, cc;
	protected JCheckBox air, ground;
	protected JCheckBox UPS, FEDEX;
	protected JTextField cardNumber;
	protected JTextField location;
	protected DeliveryOptions delivery;
	protected PaymentOptions payment;
	protected Cart cart;
	protected Customer c;
	protected String card, company;
	protected double extra;
	protected ChooseOptions choosePage;
	
	public ChooseOptions( Cart myCart, Customer customer )
	{
		super( "Choose Payment and Delivery");
		choosePage = this;
		cart = myCart;
		c = customer;
		Container container = getContentPane();
		container.setLayout( new FlowLayout());
		
		CCTypes = new JComboBox( types );
		creditLabel = new JLabel( "Credit Card Number");
		cardNumber = new JTextField( 16 );
		cod = new JCheckBox("COD");
		cc = new JCheckBox("Credit Card");
		air = new JCheckBox( "Express( Next Day/ $10.00 SH)" );
		ground = new JCheckBox( "Normal( 5 Business Days/ $7.50 SH)" );
		UPS = new JCheckBox( "UPS" );
		FEDEX = new JCheckBox( "FEDEX" );
		submit = new JButton( "Purchase" );
		deliverySite = new JLabel("Address");
		location = new JTextField(20 );
		location.setText( customer.address );
		cardNumber.setText( customer.CCnumber );
		int initialType;
		if( customer.CCtype == "VISA" )initialType = 0;
		else if( customer.CCtype == "MASTERCARD" )initialType = 1;
		else if( customer.CCtype == "AMERICAN EXPRESS" )initialType = 2;
		else if( customer.CCtype == "DISCOVER" )initialType = 3;
		else initialType = 0;
		
		CCTypes.setSelectedIndex( initialType );
		cod.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e)
				{
					if( cc.isEnabled() )
					{
						cc.setEnabled( false );
						cardNumber.disable();
						cardNumber.setText( "" );
					}
					else
					{
						cc.setEnabled( true );
						cardNumber.enable();
					}
				}
			}
		);
		
		cc.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( cod.isEnabled() )
					{
						cod.setEnabled( false );
					}
					else
					{
						cod.setEnabled( true );
					}
				}
			}
		);
		
		air.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e)
				{
					if( ground.isEnabled() )
					{
						ground.setEnabled( false );
					}
					else
					{
						ground.setEnabled( true );
					}
				}
			}
		);
		
		ground.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e)
				{
					if( air.isEnabled() )
					{
						air.setEnabled( false );
					}
					else
					{
						air.setEnabled( true );
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
						FEDEX.setEnabled(false);
					}
					else
					{
						FEDEX.setEnabled(true);
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
					}
					else
					{
						UPS.setEnabled(true);
					}
				} 
			}
		);
		submit.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					if( !cod.isSelected() && !cc.isSelected() )
					{
						JOptionPane.showMessageDialog( null, "Please select a payment method.");
						return;
					}
					
					if( cc.isSelected() && (cardNumber.getText() == "" || !cardNumber.getText().matches("\\d{16}"))) 
						
					{
						JOptionPane.showMessageDialog( null, "Please fill" +							" out credit card number", "Error",
							JOptionPane.ERROR_MESSAGE );
						return;
						
					}
					else if( cc.isSelected() && ( !FEDEX.isSelected() && !UPS.isSelected()) &&
						(!ground.isSelected() && !air.isSelected())) 	
					{ 
						JOptionPane.showMessageDialog( null, "Please select a delivery company." );
						return;
					}
					else if( cc.isSelected() && ( FEDEX.isSelected() || UPS.isSelected()) &&
						(!ground.isSelected() && !air.isSelected()) )
					{
						JOptionPane.showMessageDialog( null, "Please select a delivery option.");
						return;
					}
					else if( cc.isSelected() && ( ground.isSelected() || air.isSelected()) &&
						(!FEDEX.isSelected() && !UPS.isSelected()) )
					{
						JOptionPane.showMessageDialog( null, "Please select a delivery company.");
						return;
					}
					else if( cod.isSelected() && ( !FEDEX.isSelected() && !UPS.isSelected()) &&
						(!ground.isSelected() && !air.isSelected())) 	
					{ 
						JOptionPane.showMessageDialog( null, "Please select a delivery company." );
						return;
					}
					else if( cod.isSelected() && ( FEDEX.isSelected() || UPS.isSelected()) &&
						(!ground.isSelected() && !air.isSelected()) )
					{
						JOptionPane.showMessageDialog( null, "Please select a delivery option.");
						return;
					}
					else if( cod.isSelected() && ( ground.isSelected() || air.isSelected()) &&
						(!FEDEX.isSelected() && !UPS.isSelected()) )
					{
						JOptionPane.showMessageDialog( null, "Please select a delivery company.");
						return;
					}
					else
					{
						if( cod.isSelected() )
						{
							payment = new PaymentOptions( "COD", null, null );
						}
						else if( cc.isSelected() )
						{
							int type = CCTypes.getSelectedIndex();
							
							if(type == VISA)card = "VISA";
							else if( type == MASTERCARD )card = "MASTERCARD";
							else if( type == AMERICANEXPRESS )card = "AMERICAN EXPRESS";
							else if( type == DISCOVER )card = "DISCOVERY";
							System.out.println( card );
							payment = new PaymentOptions( "CC", card, 
								cardNumber.getText());
						}
						

						if( UPS.isSelected() )
						{
							company = "UPS";
						}	
						else if( FEDEX.isSelected() )
						{
							company = "FEDEX";
						}
						
						if( air.isSelected() )
						{
							extra = 10.00;
							delivery = new DeliveryOptions( "EXPRESS", location.getText(),
								company, extra );
							System.out.println( company );
						}
						else if( ground.isSelected() )
						{	
							extra = 7.50;
							delivery = new DeliveryOptions( "NORMAL", location.getText(),
								company, extra );
							System.out.println( company );
						}
						
						new CheckoutPage( cart, c, payment, delivery );
						choosePage.hide();
					}
				}
			}
		);
		
		container.add( cod );
		container.add( cc );
		container.add( CCTypes );
		container.add( creditLabel );
		container.add( cardNumber );
		container.add( air );
		container.add( ground );
		container.add( deliverySite );
		container.add( location );
		container.add( UPS );
		container.add( FEDEX );
		container.add( submit );
		setSize( 500, 500 );
		setVisible( true );
		setResizable( true );
		show();
		
	}
	
}
