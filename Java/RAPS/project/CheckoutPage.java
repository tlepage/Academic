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
import java.text.DecimalFormat;

public class CheckoutPage extends JFrame 
{
	private JTextArea purchaseItems;
	private JTextField totalDisplay;
	private JButton purchaseButton;
	private Cart cart;
	private Customer myCustomer;
	private boolean flag = false;
	private String paymentMethod, CCType, CCNumber, optionsString;
	private PaymentOptions payment;
	private DeliveryOptions delivery;
	private CheckoutPage checkPage;
	int number;
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	public CheckoutPage( Cart myCart, Customer me, PaymentOptions p, DeliveryOptions d )
	{
		super( "Checkout Confirmation" );
		
		double orderNumber = 10000 + (int)(Math.random() * 12200 );
		number = (int)orderNumber;
			
		checkPage = this;
		cart = myCart;
		myCustomer = me;
		payment = p;
		delivery = d;
		
		double extra = 0.0;
		
		if( delivery.deliveryOption == "EXPRESS" )
		{
			cart.total += 10.00;
			extra = 10.00;
		}
		else
		{
			cart.total += 7.50;
			extra = 7.50;
		}
		if( payment.CType == null )
		{
			optionsString = "\n\nTransaction Number: " + number +
				"\nPayment Method: COD" + 
				"\nDelivery Method: " + delivery.deliveryOption +
				"\nShipping Cost: $" + extra + "\nDelivery Location: " + 
				delivery.address;
		}
		else
		{
			optionsString = "\n\nTransaction Number: " + number +
				"\nPayment Method: " + payment.CType +
				"\nDelivery Method: " + delivery.deliveryOption +
				"\nShipping Cost: $" + extra + "\nDelivery Location: " +
				delivery.address; 
		}
		Container container = getContentPane();
		container.setLayout( new FlowLayout());
		purchaseItems = new JTextArea(20, 30);
		
		purchaseButton = new JButton("Purchase");
		purchaseItems.setAutoscrolls( true );
		purchaseItems.setLineWrap( true );
		purchaseItems.setWrapStyleWord( true );
		purchaseItems.setText("---Item Purchase---\n\n");
		totalDisplay = new JTextField(10);
		container.add( new JScrollPane( purchaseItems ));
		container.add( new JLabel("Total: $") );
		container.add( totalDisplay );
		container.add( purchaseButton );
		String totalString = Double.toString( myCart.total );
		totalDisplay.setText( totalString );
		purchaseItems.append( myCart.currentOrder );
		purchaseItems.append( optionsString );
		purchaseItems.setEditable( false );
		totalDisplay.setEditable( false );
		
		purchaseButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					new CheckoutManager( cart, myCustomer, payment, delivery, number );
					checkPage.hide();
					purchaseItems.setText( "" );
				}
			}
		);
		setSize( 650, 400 );
		setVisible( true );
		setResizable( false );
		show();
		
	}
}
