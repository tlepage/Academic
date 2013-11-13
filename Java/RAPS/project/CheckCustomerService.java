/*
 * Created on Nov 24, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckCustomerService extends JFrame
{

	static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String DB_URL = "jdbc:odbc:final";

	private Connection connection;
	private Statement statement;

	private JTextArea display;
	private JButton Bquery, Bcancel;
	private JTextField TFlogin;
	private String Slogin, Smessage;

	public CheckCustomerService()
	{
		super("Check Customer Service Requests");

		JLabel Llogin = new JLabel("Enter Customer Login");
		TFlogin = new JTextField(8);
		Bquery = new JButton("GO");
		Bcancel = new JButton("CANCEL");


		Bquery.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{
				int Ierror = 0;
				try
				{
					String temp = "nothing";
					Slogin = "";
					display.setText("");
					Slogin = TFlogin.getText();
					Smessage = "";
					Class.forName(JDBC_DRIVER);
					connection = DriverManager.getConnection(DB_URL);
					statement = connection.createStatement();
					ResultSet resultSet;
					boolean Exist = false;

					if(TFlogin.getText().matches(""))
					{
						resultSet = statement.executeQuery("SELECT login, message FROM CUSTOMER_SERVICE");
						while(resultSet.next())
						{
							Slogin = resultSet.getString("login");
							Smessage = resultSet.getString("message");
							display.append(Slogin + "\n" + Smessage.toString() +
							"\n\n--------------------------------" +
							"--------\n\n");
						}

					}
					else
					{
						resultSet = statement.executeQuery("SELECT login, message FROM CUSTOMER_SERVICE");
						while(resultSet.next())
						{
							if(resultSet.getString("login").equals(Slogin))
							{
								Smessage = resultSet.getString("message");
								Exist = true;
								display.setText(Slogin + "\n" + Smessage  +
								"\n\n--------------------------------" +
								"--------\n\n");
							}
						}
						if(!Exist)
						{
							throw new Exception();
						}

					}

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
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(null,
						"Login does not exist","ERROR", JOptionPane.ERROR_MESSAGE);
					TFlogin.setText("");
				}
			}
		});
		
		Bcancel.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{
				display.setText("");
			}
		});


		display = new JTextArea(30, 50);
		display.setEditable(false);
		JScrollPane displayScrollPane = new JScrollPane(display);

		JPanel login = new JPanel();
		login.add(Llogin);
		login.add(TFlogin);

		JPanel submit = new JPanel();
		submit.add(Bquery);

		JPanel cancel = new JPanel();
		submit.add(Bcancel);

		Container container = getContentPane();
		container.setLayout( new FlowLayout());

		container.add(login);
		container.add(submit);
		container.add(cancel);
		container.add(displayScrollPane);

		setSize(1000, 800);
		setVisible(true);
		
	}

}

