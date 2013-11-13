/*
 * Created on Nov 15, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChooseStorePayment extends JFrame
{
	private final int VISA = 0;
		private final int MASTERCARD = 1;
		private final int AMERICANEXPRESS = 2;
		private JButton submit;
		protected JComboBox CCTypes;
		protected String types[] = { "Visa", "MasterCard", "American Express", "Discover" };
		protected JTextField checkNumber;
		protected JTextField cardNumber;
		protected JLabel checkLabel, cardLabel;
		protected JCheckBox check, card, cash;
		protected boolean flag = true;
		protected RetrievePartRequestEmployeeSKU items[];
		protected int purchaseQuantity[];
		protected int currentAmount;
		protected String finalPrice;
		protected String myID, CCtype, CCnumber;
		protected int store, cashPayment = 0, checkPayment = 0;
		protected ChooseStorePayment choosePage;
		protected ItemList newItems;
		public ChooseStorePayment( ItemList itemList, String finalP,
			String ID, int s )
		{
			super( "Choose Method of Payment");
		
			newItems = itemList;
			choosePage = this;
		
			finalPrice = finalP;
			myID = ID;
			store = s;
		
			Container container = getContentPane();
			container.setLayout( new FlowLayout() );
		
			CCTypes = new JComboBox( types );
			checkNumber = new JTextField( 5 );
			cardNumber = new JTextField( 10 );
			checkLabel = new JLabel( "Check Number: " );
			cardLabel = new JLabel( "Card Number: " );
			check = new JCheckBox( "Check" );
			card = new JCheckBox( "Card" );
			cash = new JCheckBox( "Cash" );
			submit = new JButton( "Submit Payment" );
		
			check.addActionListener(
		
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						if( card.isEnabled() && cash.isEnabled() 
							&& cardNumber.isEnabled() )
						{
							card.setEnabled(false);
							cash.setEnabled(false);
							cardNumber.setEnabled(false);
							cardNumber.setText("");
						}
						else
						{
							card.setEnabled(true);
							cash.setEnabled(true);
							cardNumber.setEnabled(true);
							checkNumber.setText("");
						}
					}
				}
			);
			cash.addActionListener(
		
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						if( card.isEnabled() && check.isEnabled() && 
							checkNumber.isEnabled() && cardNumber.isEnabled() )
						{
							card.setEnabled(false);
							check.setEnabled(false);
							cardNumber.setEnabled(false);
							cardNumber.setText("");
							checkNumber.setEnabled(false);
							checkNumber.setText("");
						}
						else
						{
							card.setEnabled(true);
							check.setEnabled(true);
							checkNumber.setEnabled(true);
							cardNumber.setEnabled(true);
							
						}
					}
				}
			);
			card.addActionListener(
		
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						if( cash.isEnabled() && checkNumber.isEnabled() && 
						check.isEnabled() )
						{
							cash.setEnabled(false);
							check.setEnabled(false);
							checkNumber.setText("");
							checkNumber.setEnabled(false);
						}
						else
						{
							cash.setEnabled(true);
							check.setEnabled(true);
							checkNumber.setEnabled(true);
							cardNumber.setText("");
						}
					}
				}
			);
			submit.addActionListener(
		
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						if( !check.isSelected() && !card.isSelected() && 
							!cash.isSelected() )
						{
							JOptionPane.showMessageDialog( null, 
								"Please select a payment method.");
							flag = false;
							return;
						}
						if( check.isSelected() )
						{
							if( !checkNumber.getText().matches( "\\d{4}" ))
							{
								JOptionPane.showMessageDialog( null, 
									"Invalid Check Number.");
								flag = false;
								return;
							}
							else
							{
								checkPayment = 1;
								flag = true;
							}
						}
						else if( card.isSelected() )
						{
							if( !cardNumber.getText().matches( "\\d{16}" ))
							{
								JOptionPane.showMessageDialog( null, 
									"Invalid Card Number." );
								flag = false;
								return;
							}
							else
							{
								int type;
								if( cardNumber.getText() == "" )
								{
									JOptionPane.showMessageDialog( null, 
										"Please fill in credit card number" );
									flag = false;
									return;
								}
								else
								{
									CCnumber = cardNumber.getText();
									type = CCTypes.getSelectedIndex();
									if( type == 0 )
										CCtype = "VISA";
									else if( type == 1 )
										CCtype = "MASTERCARD";
									else if( type == 2 )
										CCtype = "AMERICAN EXPRESS";
									else if( type == 3 )
										CCtype = "DISCOVER";
									flag = true;
								}
							}
						}
						else if( cash.isSelected() )
						{
							cashPayment = 1;
							flag = true;
						}
					
						if( flag )
						{
							new CheckoutFinalize( newItems, Double.toString(newItems.total),
								myID, store, cashPayment, checkPayment, CCtype, CCnumber );	
						
							choosePage.hide();
						}
					}
				}
			);
		
			container.add(check);
			container.add(checkLabel);
			container.add(checkNumber);
			container.add(card);
			container.add(cardLabel);
			container.add(cardNumber);
			container.add(cash);
			container.add(submit);
		
			setVisible(true);
			setResizable(false);
			setSize( 500, 150 );
			show();
		
		}
}
