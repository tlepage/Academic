/*
 * Created on Nov 12, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LoginPage extends JFrame
{
	private JTextField TFlogin;
	private JPasswordField TFpassword;
	protected String Slogin, Spassword;
	private String types[] = { "Customer", "Trainee", "Employee", "Manager"};
	private JComboBox CBtypes;
	protected int Itypes;
	private boolean bsubmit;
	protected int loginType = -1;
	protected LoginDriver driver;
	protected boolean noExceptions;
	protected MainScreenDefault startPage;
	protected LoginPage loginPage;
	
	public LoginPage( MainScreenDefault page )
	{
		super("Login Page");
		
		loginPage = this;
		startPage = page;
		JLabel Llogin = new JLabel("Login ID");
		JLabel Lpassword = new JLabel("Password");

		JButton Bsubmit = new JButton("Submit");
		JButton Bcancel = new JButton("Cancel");
	
		noExceptions = true;
		Bsubmit.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					int Ierror = 0;
					String Serror = "";
					bsubmit = true;

					try
					{

						Ierror = 0;
						if(TFlogin.getText().equals("") || TFpassword.getText().equals(""))
						{
							noExceptions = false;
							throw new Exception();
						}

						Ierror = 1;
						if(!TFlogin.getText().matches("([a-zA-Z]*[0-9]*)*"))
						{
							noExceptions = false;
							throw new Exception();
						}
					
						Slogin = TFlogin.getText();

						Ierror = 2;
						if(!TFpassword.getText().matches("([a-zA-Z]*[0-9]*)*"))
						{
							noExceptions = false;
							throw new Exception();
						}
						Spassword = TFpassword.getText();
						Itypes = CBtypes.getSelectedIndex();
						
						if( noExceptions )
						{
							driver = new LoginDriver( Slogin, Spassword, Itypes,
								loginPage, startPage );
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
								break;
							case 1:
								JOptionPane.showMessageDialog(null,
									"Enter a valid login (eg. ats5433)","ERROR", JOptionPane.ERROR_MESSAGE);
								TFlogin.setText("");
								break;
							case 2:
								JOptionPane.showMessageDialog(null,
									"Enter a valid password (eg. ll23hn12)","ERROR", JOptionPane.ERROR_MESSAGE);
								TFpassword.setText("");
								break;
						}
					}
					
					
				}
			}
		);
		Bcancel.addActionListener( 
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent event )
				{
					bsubmit = false;
					JOptionPane.showMessageDialog(null,
						"Login Canceled","MESSAGE", JOptionPane.INFORMATION_MESSAGE);
					TFlogin.setText("");
					TFpassword.setText("");
				}
			}
		);
		TFlogin = new JTextField(8);
		TFpassword = new JPasswordField(8);

		JPanel login = new JPanel();
		JPanel password = new JPanel();
		CBtypes = new JComboBox(types);
		CBtypes.setMaximumRowCount(2);

		login.add(Llogin);
		login.add(TFlogin);
		password.add(Lpassword);
		password.add(TFpassword);

		JPanel submit = new JPanel();
		submit.add(Bsubmit);
		JPanel cancel = new JPanel();
		cancel.add(Bcancel);

		Container container = getContentPane();
		container.setLayout( new FlowLayout());

		container.add(login);
		container.add(password);
		container.add(CBtypes);
		container.add(submit);
		container.add(cancel);

		setSize(400, 100);
		setVisible(true);
		
	}

}