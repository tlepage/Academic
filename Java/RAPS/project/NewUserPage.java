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

public class NewUserPage extends JFrame
{

	private JTextField TFlogin, TFfname, TFlname, TFaddress,
		TFcity, TFstate, TFzip, TFphone, TFfax, TFemail;
	private JPasswordField TFpassword;
	private String Slogin, Spassword, Sfname, Slname, Saddress, Sphone, Semail,
		Sfax, SCCnumber;
	private int Izip;
	private boolean bsubmit, noExceptions = true;
	
	protected NewUserPage thisPage;
	public NewUserPage()
	{
		super("New User Entry");
		
		thisPage = this;
		String choices[] = { "Visa", "MasterCard", "American Express", "Discover" };
		JLabel Lfname = new JLabel("First Name");
		JLabel Llname = new JLabel("Last Name");
		JLabel Laddress = new JLabel("Address");
		
		JLabel Lfax = new JLabel("Fax");
		JLabel Lphone = new JLabel("Phone");
		JLabel Lemail = new JLabel("Email");
		JLabel Llogin = new JLabel("Login ID");
		JLabel Lpassword = new JLabel("Password");
		final JComboBox CCtype = new JComboBox(choices);
		JLabel CClabel = new JLabel("Credit Card Number: " );
		final JTextField CCfield = new JTextField( 16 );
		
		Sfax = "";
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
				if(TFfname.getText().equals("") || TFlname.getText().equals("")
				|| TFaddress.getText().equals("") || CCfield.getText().equals("")
				|| TFphone.getText().equals("") || TFlogin.getText().equals("")
				|| TFpassword.getText().equals("") || TFemail.getText().equals(""))
				{
					noExceptions = false;
					throw new Exception();
				}

				Ierror = 1;
				if(!TFfname.getText().matches("[a-zA-Z][a-zA-Z]*"))
				{
					noExceptions = false;
					throw new Exception();
				}
				Sfname = TFfname.getText();

				Ierror = 2;
				if(!TFlname.getText().matches("[a-zA-Z][a-zA-Z]*"))
				{
					noExceptions = false;
					throw new Exception();
				}
				Slname = TFlname.getText();

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

				Ierror = 9;
				if(!TFlogin.getText().matches("([a-zA-Z]*[0-9]*)*"))
				{
					noExceptions = false;
					throw new Exception();
				}
				Slogin = TFlogin.getText();

				Ierror = 10;
				if(!TFpassword.getText().matches("([a-zA-Z]*[0-9]*)*"))
				{
					noExceptions = false;
					throw new Exception();
				}

				Spassword = TFpassword.getText();

				String name = Slname + ", " + Sfname;
					if( Sfax.equals(null) || Sfax.equals(""))
					{
						Sfax = "None";
					}
					if( Slogin.length() < 5 || Slogin.length() > 8 )
					{
						JOptionPane.showMessageDialog( null, "Login must be" +							" between 5 and 8 characters or digits" );
						noExceptions = true;
						return;
					}
					if( noExceptions )
					{
						new AddCustomerDriver( name, Saddress, Sphone, Sfax,  
							Slogin, Spassword, Semail, SCCnumber, CCtype.getSelectedIndex(),
							thisPage );
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
							TFfname.setText("");
							noExceptions = true;
							break;
						case 2:
							JOptionPane.showMessageDialog(null,
					"Last Name must entered","ERROR", JOptionPane.ERROR_MESSAGE);
							TFlname.setText("");
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
							TFstate.setText("");
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
							TFemail.setText("");
							noExceptions = true;
							break;

						case 9:
							JOptionPane.showMessageDialog(null,
					"Enter a valid login (eg. ats5433)","ERROR", JOptionPane.ERROR_MESSAGE);
							TFlogin.setText("");
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
				TFfname.setText("");
				TFlname.setText("");
				TFaddress.setText("");
				
				CCfield.setText("");
				TFfax.setText("");
				TFphone.setText("");
				TFemail.setText("");
				TFlogin.setText("");
				TFpassword.setText("");
				JOptionPane.showMessageDialog(null,
									"New User Cancelled","MESSAGE", JOptionPane.INFORMATION_MESSAGE);

				thisPage.hide();
			}
		});

		TFfname = new JTextField(15);
		TFlname = new JTextField(15);
		TFaddress = new JTextField(15);
		
		TFfax = new JTextField(12);
		TFphone = new JTextField(12);
		TFemail = new  JTextField(10);
		TFlogin = new JTextField(8);
		TFpassword = new JPasswordField(8);

		JPanel fname = new JPanel();
		JPanel lname = new JPanel();
		JPanel address = new JPanel();
	
		JPanel fax = new JPanel();
		JPanel phone = new JPanel();
		JPanel email = new JPanel();
		JPanel login = new JPanel();
		JPanel password = new JPanel();
		JPanel cc = new JPanel();
		
		fname.add(Lfname);
		fname.add(TFfname);
		lname.add(Llname);
		lname.add(TFlname);
		address.add(Laddress);
		address.add(TFaddress);
		cc.add( CCtype );
		cc.add( CClabel );
		cc.add( CCfield);
		fax.add( Lfax );
		fax.add( TFfax );
		phone.add(Lphone);
		phone.add(TFphone);
		email.add(Lemail);
		email.add(TFemail);
		login.add(Llogin);
		login.add(TFlogin);
		password.add(Lpassword);
		password.add(TFpassword);

		JPanel submit = new JPanel();
		submit.add(Bsubmit);
		JPanel cancel = new JPanel();
		cancel.add(Bcancel);

		Container container = getContentPane();
		container.setLayout( new GridLayout(7, 2));

		container.add(fname);
		container.add(lname);
		container.add(address);
		container.add(fax);
		container.add(phone);
		container.add(email);
		container.add(login);
		container.add(password);
		container.add(cc);
		container.add(submit);
		container.add(cancel);


		setSize(500, 600);
		setVisible(true);
		
	}	
	
}
