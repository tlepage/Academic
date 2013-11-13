/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class Trainee
{
	protected String name, phone, login, password, address, email, hireDate;

	protected int storeNumber, level,employed;
	
	public Trainee( RetrieveEmployeeID trainee )
	{
		name = trainee.name;
		phone = trainee.phone;
		login = trainee.login;
		password = trainee.pWord;
		email = trainee.email;
		address = trainee.address;
		employed = trainee.employed;
		level = trainee.level;
		storeNumber = trainee.storeNumber;
		hireDate = trainee.hireDate;
	}
}
