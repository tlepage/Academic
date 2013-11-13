/*
 * Created on Nov 11, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;

public class RemovePartPage
{
	public RemovePartPage( String sku, String qString, int store )
	{
		int quantity = 0;
		try
		{
			quantity = Integer.parseInt( qString );
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog( null, "Invalid Quantity" );
			return;
		}
	
		System.out.println( "Store Number: " + store );
		if( quantity > 0 )
		{	
			RemovePartRequestEmployeeSKU removal = 
				new RemovePartRequestEmployeeSKU( sku, qString, store );
			
			if( removal.flag == false )
			{
				JOptionPane.showMessageDialog( null, "Invalid Request",
					"Error", JOptionPane.ERROR_MESSAGE );
				return;
			}
			else
			{
				JOptionPane.showMessageDialog( null, "Successful Update",
					"Success", JOptionPane.ERROR_MESSAGE );
			}
		}	
		else
		{
			JOptionPane.showMessageDialog( null, "Invalid Quantity.",
				"Error", JOptionPane.ERROR_MESSAGE );
			return;
		}
	}
	
}
