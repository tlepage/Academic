/*
 * Created on Nov 24, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CustomerService extends JFrame
{
	static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String DB_URL = "jdbc:odbc:final";

	private Connection connection;
	private Statement statement;
	private JButton submitButton;

	private JTextField idfield, namefield, commentfield;
	private String Slogin, Smessage;
	private int Ierror;
	private String myLogin;
	protected boolean noExceptions;
	protected CustomerService csPage;

  	public CustomerService( String login )
   	{
		super("CUSTOMER SERVICE");
		
		csPage = this;
		noExceptions = true;
		myLogin = login;
		Container container = getContentPane();
	  	container.setLayout(new FlowLayout());

		JLabel comment = new JLabel("COMMENTS OR QUESTIONS");
		container.add(comment);
		commentfield = new JTextField(50);
		container.add(commentfield);

	   	submitButton = new JButton("OK");
	   	submitButton.addActionListener(
				 
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					noExceptions = search( myLogin );
					
					if( noExceptions )
						csPage.hide();
				}	
			}
		);

		container.add(submitButton);


	  	setSize(300,700);
	   	setVisible(true);
		
   	}


   	public boolean search( String login )
   	{
		boolean noExceptions = true;
		
		try 
		{
			Ierror = 0;
			ResultSet resultSet, updateMessage;
			Slogin = login;                  
			Smessage = commentfield.getText();
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
			   
			if(commentfield.getText().equals(""))
			{
				noExceptions = false;
				throw new Exception();
			}

			Ierror = 1;
			resultSet = statement.executeQuery("SELECT login, message FROM CUSTOMER_SERVICE");
			
			while(resultSet.next())
			{
				if(resultSet.getString("login").equals(Slogin) && resultSet.getString("message").equals(Smessage))
				{
					noExceptions = false;
					throw new Exception();
				}
			}

			statement.close();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			updateMessage = statement.executeQuery("SELECT * FROM CUSTOMER_SERVICE");
			updateMessage.moveToInsertRow();
			updateMessage.updateString("login", Slogin);
			updateMessage.updateString("message", Smessage);
			updateMessage.insertRow();

			JOptionPane.showMessageDialog(null, "Message Sent", "Message",
			JOptionPane.INFORMATION_MESSAGE);

			statement.close();
			connection.close();
		}
		catch(ClassNotFoundException cnfe)
		{
			System.err.println("Error loading " + JDBC_DRIVER);
		}
		catch(SQLException sqle)
		{
			System.err.println(sqle);
		}
		catch (Exception e)
		{
			switch(Ierror)
			{
				case 0:
					JOptionPane.showMessageDialog(null,
						"Message field is empty","ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				case 1:
					JOptionPane.showMessageDialog(null,
						"Message already exists","ERROR", JOptionPane.ERROR_MESSAGE);
					commentfield.setText("");
					break;
			}
		}
		
		return noExceptions;
	}
}
