/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class Employee
{
	protected String name, phone, login, password, address, email, hireDate;
	protected int storeNumber, level, employed;
	
	public Employee( RetrieveEmployeeID employee )
	{
		name = employee.name;
		phone = employee.phone;
		login = employee.login;
		password = employee.pWord;
		email = employee.email;
		address = employee.address;
		level = employee.level;
		employed = employee.employed;
		hireDate = employee.hireDate;
		storeNumber = employee.storeNumber;
		
	}
}