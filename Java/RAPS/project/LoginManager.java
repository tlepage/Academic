/*
 * Created on Nov 12, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;

public class LoginManager
{
	protected MainScreenEnabledCustomer customer;
	protected MainScreenEnabledEmployee employee;
	protected MainScreenEnabledTrainee trainee;
	protected MainScreenEnabledManager manager;
	protected LoginManager lManager;
	
	protected final int CUSTOMER = 0;
	protected final int MANAGER = 3, MANAGER2 = 9;
	protected final int EMPLOYEE = 2;
	protected final int TRAINEE = 1;
	protected int loadType = -1;
	protected LoginDriver driver;
	
	public LoginManager( LoginDriver login, LoginPage loginPage,
		MainScreenDefault page )
	{
		if( login.returnType == -1 )
		{
			JOptionPane.showMessageDialog( null, "Invalid Login.",
				"Error", JOptionPane.ERROR_MESSAGE );
			loadType = -1;
		}
		else
		{
			loadType = login.returnType;
			System.out.println( "Returned: " + loadType );
			driver = login;
			
			switch( loadType )
			{
				case CUSTOMER:
					RetrieveCustomerID client = 
						new RetrieveCustomerID( login.userID );
					customer = new MainScreenEnabledCustomer( client );
					loginPage.hide();
					page.hide();
						
					break;
				case TRAINEE:
					RetrieveEmployeeID trainer =
						new RetrieveEmployeeID( login.userID );
					trainee = new MainScreenEnabledTrainee( trainer );
					loginPage.hide();
					page.hide();
					
					break;
				case EMPLOYEE:
					RetrieveEmployeeID employer =
						new RetrieveEmployeeID( login.userID );
					employee = new MainScreenEnabledEmployee( employer);
					loginPage.hide();
					page.hide();
					
					break;
				case MANAGER:
				case MANAGER2:
					RetrieveEmployeeID manage =
						new RetrieveEmployeeID( login.userID );
					manager = new MainScreenEnabledManager( manage );
					loginPage.hide();
					page.hide();
					
					break;
				}
			}
		}								
}
	
	

