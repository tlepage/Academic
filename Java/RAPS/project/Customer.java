/*
 * Created on Nov 12, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class Customer
{
	protected String login, name, password, phone, email, address, 
		fax, CCtype, CCnumber;
	protected int status;
		
	public Customer( RetrieveCustomerID customer )
	{
		name = customer.name;
		phone = customer.phone;
		email = customer.email;
		address = customer.address;
		password = customer.password;
		login = customer.login;
		fax = customer.fax;
		CCtype = customer.CCtype;
		CCnumber = customer.CCnumber;
		status = customer.status;
	}
	public void update( Customer updatedCustomer )
	{
		name = updatedCustomer.name;
		phone = updatedCustomer.phone;
		email = updatedCustomer.email;
		address = updatedCustomer.address;
		password = updatedCustomer.password;
		fax = updatedCustomer.fax;
		CCtype = updatedCustomer.CCtype;
		CCnumber = updatedCustomer.CCnumber;
		status = updatedCustomer.status;
	}
	
	public String printCustomer()
	{
		String myInfo = "\nName: " + this.name + " " 
			+ "\nPhone Number: " + this.phone + "\nFax: " + this.fax +
			"\nLocation: " + this.address + "\nEmail: " + this.email;
			
		return myInfo;
	}
}
