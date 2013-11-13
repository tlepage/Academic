/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class Manager
{
	protected String name, phone, login, password, address, email, hireDate;
	protected int storeNumber, level, employed;
	
	public Manager( RetrieveEmployeeID manager )
	{
		name = manager.name;
		phone = manager.phone;
		login = manager.login;
		password = manager.pWord;
		address = manager.address;
		email = manager.email;
		level = manager.level;
		storeNumber = manager.storeNumber;
		employed = manager.employed;
		hireDate = manager.hireDate;
	}
	
}
