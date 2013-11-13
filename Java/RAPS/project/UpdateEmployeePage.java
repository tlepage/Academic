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

public class UpdateEmployeePage extends JFrame
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
	Manager myManager;
	RetrieveEmployeeID empCheck;
	protected UpdateEmployeePage thisPage;
		
	public UpdateEmployeePage( String employeeLogin, Manager manager )
	{
		super( "Update Employee" );
		
		thisPage = this;
		myManager = manager;
		empCheck = new RetrieveEmployeeID( employeeLogin );
			
		if( !empCheck.flag )
		{
			return;
		}
		else
		{
			Container container = getContentPane();
			container.setLayout( new FlowLayout() );
		
			addressField = new JTextField(16);
			addressField.setText(empCheck.address);
			phoneField = new JTextField(12);
			phoneField.setText(empCheck.phone);
			accessField = new JTextField(1);
			accessField.setText(Integer.toString(empCheck.level));
			storeField = new JTextField(3);
			storeField.setText(Integer.toString(empCheck.storeNumber));
			employedField = new JTextField(1);
			employedField.setText(Integer.toString(empCheck.employed));
			loginField = new JTextField(8);
			loginField.setText(empCheck.login);
			loginField.setEditable(false);
			passwordField = new JTextField(8);
			passwordField.setText(empCheck.password);
			emailField = new JTextField( 16 );
			emailField.setText( empCheck.email );
			
			submit = new JButton("Submit");
	
			addressLabel = new JLabel( "Address" );
			phoneLabel = new JLabel( "Phone" );
			accessLabel = new JLabel( "Access (1-Trainee, 2-Full-Time Employee, 3-Manager)" );
			storeLabel = new JLabel( "Store Number" );
			employedLabel = new JLabel( "Employed (0-No, 1-Yes)" );
			loginLabel = new JLabel( "Login" );
			passwordLabel = new JLabel( "Password" );
			emailLabel = new JLabel( "Email" );
			submit.addActionListener(
		
				new ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						valid = validateEmployee();
					
						if( valid )
						{
						
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
								JOptionPane.showMessageDialog( null, "Invalid" +
									" Password Length" );
								return;
							}
							if( store != myManager.storeNumber )
							{
								valid = false;
								JOptionPane.showMessageDialog( null, "Employee is" +									" not at this store" );
								return;
							}
							else
							{ 
								new UpdateAccountEmployee( login, password, 
									address, phone, email, access, store, employed,
									thisPage );
							}
						}
						else
						{
							return;
						}
					}
				}
			);
		
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
	}
	private boolean validateEmployee()
	{
		boolean isValid = false;
		
		if( addressField.getText().equals("") ||
			phoneField.getText().equals("") ||
			accessField.getText().equals("") ||
			storeField.getText().equals("") ||
			employedField.getText().equals("") ||
			loginField.getText().equals("") ||
			passwordField.getText().equals("")||
			emailField.getText().equals("") )
			
		JOptionPane.showMessageDialog( this, "Please fill all fields" );
		
		else if( !addressField.getText().matches( 
			"\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" ))
			JOptionPane.showMessageDialog( this, "Invalid address" );
		
		else if( !phoneField.getText().matches( 
			"[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}" ))
			JOptionPane.showMessageDialog( this, "Invalid Phone Number " +
					"(Format: (000-000-0000)" );
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
			JOptionPane.showMessageDialog( this, "Invalid password " +
					"(Format: can be any 8 digits or characters" );
		else if( !emailField.getText().matches( "[A-Za-z0-9@.]*" ))
			JOptionPane.showMessageDialog( this, "Invalid Email" );
		else 
		{
			isValid = true;
		}
		
		return isValid;
	}
	
}
