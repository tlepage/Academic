/*
 * Created on Nov 13, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class PaymentOptions
{
	protected String paymentMethod, CType, CNumber;
	protected int cash;
	
	public PaymentOptions( String paymentType, String CCType, String CCNumber )
	{
		paymentMethod = paymentType;
		CType = CCType;
		CNumber = CCNumber;
		
		if( CCType == null )
			cash = 1;
		else 
			cash = 0;
		
		System.out.println( paymentMethod + CType + CNumber );
	}
}
