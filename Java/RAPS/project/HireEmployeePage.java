/*
 * Created on Nov 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HireEmployeePage extends JFrame
{
	JTextField fNameField, lNameField, addressField, phoneField, 
		accessField, storeField, employedField, loginField, passwordField,
		emailField;
	JButton submit;
	JLabel fNameLabel, lNameLabel, addressLabel, phoneLabel, accessLabel,
		storeLabel, employedLabel, loginLabel, passwordLabel, emailLabel;
	String address, phone, fullName, accessString, storeString, employedString,
		login, password, email;
	int access, store, employed;
	boolean valid;
	protected HireEmployeePage thisPage;
	public HireEmployeePage( Manager myManager )
	{
		super( "Hire Employee" );
		
		thisPage = this;
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		fNameField = new JTextField(10);
		lNameField = new JTextField(10);
		addressField = new JTextField(16);
		phoneField = new JTextField(12);
		accessField = new JTextField(1);
		storeField = new JTextField(3);
		storeField.setText( Integer.toString(myManager.storeNumber) );
		storeField.setEditable( false );
		employedField = new JTextField(1);
		loginField = new JTextField(8);
		passwordField = new JTextField(8);
		emailField = new JTextField(16);
		submit = new JButton("Submit");
		
		fNameLabel = new JLabel( "First Name ");
		lNameLabel = new JLabel( "Last Name ");
		addressLabel = new JLabel( "Address" );
		phoneLabel = new JLabel( "Phone" );
		accessLabel = new JLabel( "Access (1-Trainee, 2-Full-Time Employee, 3-Manager)" );
		storeLabel = new JLabel( "Store Number" );
		employedLabel = new JLabel( "Employed(0-No, 1-Yes)" );
		loginLabel = new JLabel( "Login" );
		passwordLabel = new JLabel( "Password" );
		emailLabel = new JLabel( "Email");
		submit.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					valid = validateEmployee();
					
					if( valid )
					{
						fullName = lNameField.getText() + ", " +
						fNameField.getText();
						
						address = addressField.getText();
						phone = phoneField.getText();
						accessString = accessField.getText();
						access = Integer.parseInt( accessString );
						storeString = storeField.getText();
						store = Integer.parseInt( storeString );
						employedString = employedField.getText();
						employed = Integer.parseInt( employedString );
						password = passwordField.getText();
						login = loginField.getText();
						email = emailField.getText();
						if( password.length() > 8 )
						{
							valid = false;
							JOptionPane.showMessageDialog( null, "Invalid" +								" Password Length" );
							return;
						}
						
						else
						{ 
							new AddEmployeeDriver( login, password, fullName, 
								address, phone, email, access, store, employed, thisPage );
						}
					}
					else
					{
						return;
					}
				}
			}
		);
		
		container.add( fNameLabel );
		container.add( fNameField );
		container.add( lNameLabel );
		container.add( lNameField );
		container.add( addressLabel );
		container.add( addressField );
		container.add( phoneLabel );
		container.add( phoneField );
		container.add( accessLabel );
		container.add( accessField );
		container.add( storeLabel );
		container.add( storeField );
		container.add( employedLabel );
		container.add( employedField );
		container.add( emailLabel );
		container.add( emailField );
		container.add( loginLabel );
		container.add( loginField );
		container.add( passwordLabel );
		container.add( passwordField );
		container.add( submit );
		
		setSize(650, 150);
		setVisible(true);
		
	}
	private boolean validateEmployee()
	{
		boolean isValid = false;
		
		if( fNameField.getText().equals("") ||
			lNameField.getText().equals("") ||
			addressField.getText().equals("") ||
			phoneField.getText().equals("") ||
			accessField.getText().equals("") ||
			storeField.getText().equals("") ||
			employedField.getText().equals("") ||
			loginField.getText().equals("") ||
			passwordField.getText().equals("")||
			emailField.getText().equals(""))
			
			JOptionPane.showMessageDialog( this, "Please fill all fields" );
			
		else if( !fNameField.getText().matches( "[A-Z][a-zA-Z]*" ))
			JOptionPane.showMessageDialog( this, "Invalid first name" );
		
		else if( !lNameField.getText().matches( "[A-Z][a-zA-Z]*" ))
			JOptionPane.showMessageDialog( this, "Invalid last name" );
		
		else if( !addressField.getText().matches( 
			"\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" ))
			JOptionPane.showMessageDialog( this, "Invalid address" );
		
		else if( !phoneField.getText().matches( 
			"[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}" ))
			JOptionPane.showMessageDialog( this, "Invalid Phone Number " +				"(Format: (000-000-0000)" );
		else if( !accessField.getText().matches("[1-3]"))
			JOptionPane.showMessageDialog( this, "Invalid access code" );
		else if( !storeField.getText().matches("[1][0][0-3]"))
			JOptionPane.showMessageDialog( this, "Invalid store number" );
		else if( !employedField.getText().matches("[0-1]"))
			JOptionPane.showMessageDialog( this, "Invalid employed code" );
		else if( !loginField.getText().matches(
			"[a-zA-Z][a-zA-Z][a-zA-Z]\\d{2}"))
			JOptionPane.showMessageDialog( this, "Invalid Login (Format: ttt00" );
		else if( !passwordField.getText().matches("([a-zA-Z]*[0-9]*)*"))
			JOptionPane.showMessageDialog( this, "Invalid password " +				"(Format: can be any 8 digits or characters" );
		else if( !emailField.getText().matches( "[A-Za-z0-9@.]*"))
			JOptionPane.showMessageDialog( this, "Invalid email" );
		else 
		{
	
			isValid = true;
		}
		
		return isValid;
	} 
	
}
