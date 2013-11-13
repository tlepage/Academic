/*
 * Created on Nov 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class DeliveryOptions
{
	protected String deliveryOption, address, company;
	protected double dCost;
	
	public DeliveryOptions( String deliveryType, String deliveryAddress, 
		String deliveryCompany, double cost )
	{
		deliveryOption = deliveryType;
		address = deliveryAddress;
		company = deliveryCompany;
		dCost = cost;
	}
}
