/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UpdateCustomerPage extends JFrame
{
	private String address, fax, phone, password, CCnumber;
	private JTextField TFaddress,TFphone, TFfax, TFemail;
	private JPasswordField TFpassword;
	private String Slogin, Spassword, Sfname, Slname, Saddress, Sphone, Semail,
		Sfax, SCCnumber;
	private JComboBox statusBox;
	private int Izip;
	private boolean bsubmit;
	private Customer cust;
	protected boolean noExceptions;
	protected UpdateCustomerPage thisPage;
	protected MainScreenEnabledCustomer enabledPage;
	public UpdateCustomerPage(Customer myCustomer, MainScreenEnabledCustomer page)
	{
		super("Update Customer Information-" + myCustomer.name );
		
		enabledPage = page;
		thisPage = this;
		cust = myCustomer;
		address = myCustomer.address;
		fax = myCustomer.fax;
		phone = myCustomer.phone;
		password = myCustomer.password;
		CCnumber = myCustomer.CCnumber;
		
		noExceptions = true;
		String statusChoices[] = { "Non-Customer", "Customer" };
		String choices[] = { "Visa", "MasterCard", "American Express", "Discover" };
		
		JLabel Laddress = new JLabel("Address");
		JLabel statusLabel = new JLabel( "Status" );
		
		JLabel Lfax = new JLabel("Fax");
		JLabel Lphone = new JLabel("Phone");
		JLabel Lemail = new JLabel("Email");
		
		JLabel Lpassword = new JLabel("Password");
		final JComboBox CCtype = new JComboBox(choices);
		JLabel CClabel = new JLabel("Credit Card Number: " );
		final JTextField CCfield = new JTextField( 16 );
		CCfield.setText(CCnumber);
		
		statusBox = new JComboBox( statusChoices );
		statusBox.setSelectedIndex( myCustomer.status );
		JButton Bsubmit = new JButton("Submit");
		JButton Bcancel = new JButton ("Cancel");
		
		
		Bsubmit.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{
				int Ierror = 0;
				bsubmit = true;

				try{

				Ierror = 0;
				if( TFaddress.getText().equals("") || CCfield.getText().equals("")
					|| TFphone.getText().equals("") || TFemail.getText().equals("") ||
					 TFpassword.getText().equals(""))
				{
					noExceptions = false;
					throw new Exception();
				}

				Ierror = 3;
				if(!TFaddress.getText().matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s+[a-zA-Z]+)"))
				{
					noExceptions = false;
					throw new Exception();
				}
				Saddress = TFaddress.getText();

				Ierror = 4;
				if( !CCfield.getText().matches("\\d{16}"))
				{
					noExceptions = false;
					throw new Exception();
				}
				SCCnumber = CCfield.getText();
				
				Sfax = TFfax.getText();
				Ierror = 7;
				if(!TFphone.getText().matches("[0-9]\\d{2}-[0-9]\\d{2}-\\d{4}"))
				{
					noExceptions = false;
					throw new Exception();
				}
				Sphone = TFphone.getText();



				Ierror = 8;
				if(!TFemail.getText().matches("[a-zA-Z0-9@.]*"))
				{
					throw new Exception();
				}
				
				Semail = TFemail.getText();
				
				Ierror = 10;
				if(!TFpassword.getText().matches("([a-zA-Z]*[0-9]*)*"))
				{
					noExceptions = false;
					throw new Exception();
				}

				Spassword = TFpassword.getText();
			
				
					if( noExceptions )
					{ 
						new UpdateAccountCustomer( SCCnumber, Spassword, Saddress,
							Sphone, Sfax, CCtype.getSelectedIndex(), statusBox.getSelectedIndex(), 
							Semail, cust, thisPage, enabledPage );
				
					}
				}
				catch(Exception x)
				{
					bsubmit = false;
					switch(Ierror)
					{
						case 0:
							JOptionPane.showMessageDialog(null,
					"Please fill all fields","ERROR", JOptionPane.ERROR_MESSAGE);
							noExceptions = true;
							break;
						case 1:
							JOptionPane.showMessageDialog(null,
					"First Name must entered","ERROR", JOptionPane.ERROR_MESSAGE);
							noExceptions = true;
							break;
						case 2:
							JOptionPane.showMessageDialog(null,
					"Last Name must entered","ERROR", JOptionPane.ERROR_MESSAGE);
						noExceptions = true;
							break;

						case 3:
							JOptionPane.showMessageDialog(null,
					"Enter a valid address","ERROR", JOptionPane.ERROR_MESSAGE);
							TFaddress.setText("");
							noExceptions = true;
							break;
						case 4:
							JOptionPane.showMessageDialog(null,
					"Enter a valid Credit Card number (16 digits)","ERROR", JOptionPane.ERROR_MESSAGE);
							CCfield.setText("");
							noExceptions = true;
							break;
						case 5:
							JOptionPane.showMessageDialog(null,
					"Enter a valid 2 digit State (eg. TX)","ERROR", JOptionPane.ERROR_MESSAGE);
							noExceptions = true;
							break;
						case 6:
							JOptionPane.showMessageDialog(null,
					"Enter a valid fax number or use home number\ne.g 000-000-0000","ERROR", 
					JOptionPane.ERROR_MESSAGE);
							TFfax.setText("");
							noExceptions = true;
							break;
						case 7:
							JOptionPane.showMessageDialog(null,
					"Enter a valid Phone number (eg. 000-000-0000)","ERROR", JOptionPane.ERROR_MESSAGE);
							TFphone.setText("");
							noExceptions = true;
							break;
						case 8:
							JOptionPane.showMessageDialog(null,
								"Enter a valid email (eg. john@hotmail.com)","ERROR", JOptionPane.ERROR_MESSAGE);
						
							noExceptions = true;
							break;

						case 9:
							JOptionPane.showMessageDialog(null,
					"Enter a valid login (eg. ats5433)","ERROR", JOptionPane.ERROR_MESSAGE);
					
							noExceptions = true;
							break;
						case 10:
							JOptionPane.showMessageDialog(null,
					"Enter a valid password (eg. ll233ohn)","ERROR", JOptionPane.ERROR_MESSAGE);
							TFpassword.setText("");
							noExceptions = true;
							break;
					}

				}
			}
		});

		Bcancel.addActionListener( new ActionListener()
		{
		public void actionPerformed( ActionEvent event )
			{
				bsubmit = false;
				TFaddress.setText("");
				CCfield.setText("");
				TFfax.setText("");
				TFphone.setText("");
				TFemail.setText("");
				TFpassword.setText("");
				JOptionPane.showMessageDialog(null,
					"Update Canceled","MESSAGE", JOptionPane.INFORMATION_MESSAGE);


			}
		});

		TFaddress = new JTextField(15);
		TFaddress.setText(address);
		CCfield.setText(CCnumber);
	
		TFfax = new JTextField(12);
		TFfax.setText(fax);
		TFphone = new JTextField(12);
		TFphone.setText(phone);
		TFemail = new  JTextField(16);
		TFemail.setText( myCustomer.email );

		TFpassword = new JPasswordField(8);
		TFpassword.setText(password);
	
		JPanel addressPanel = new JPanel();
		
		JPanel fax = new JPanel();
		JPanel phone = new JPanel();
	
		JPanel password = new JPanel();
		JPanel cc = new JPanel();
		
		addressPanel.add(Laddress);
		addressPanel.add(TFaddress);
		
		cc.add( CCtype );
		cc.add( CClabel );
		cc.add( CCfield);
		fax.add( Lfax );
		fax.add( TFfax );
		phone.add(Lphone);
		phone.add(TFphone);
	
		password.add(Lpassword);
		password.add(TFpassword);

		JPanel submit = new JPanel();
		submit.add(Bsubmit);
		JPanel cancel = new JPanel();
		cancel.add(Bcancel);

		Container container = getContentPane();
		container.setLayout( new FlowLayout() );

		container.add(addressPanel);
	
		container.add(fax);
		container.add(phone);
		container.add( Lemail );
		container.add(TFemail);

		container.add(password);
		container.add(cc);
		container.add( statusLabel );
		container.add( statusBox );
		container.add(submit);
		container.add(cancel);

		setSize(500, 500);
		setVisible(true);
		
	}	
	
}
