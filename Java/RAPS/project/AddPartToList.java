/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.text.DecimalFormat;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AddPartToList 
{
	private String sku = null, qString = null;
	private RetrievePartRequestEmployeeSKU request;
	private String output;
	private int quantity = 0;
	private boolean flag = true;
	
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	
	public AddPartToList( ItemList itemList, int storeNumber )
	{
		try
		{
			sku = JOptionPane.showInputDialog( "Please enter the SKU: " );
		}
		catch( Exception e )
		{
			JOptionPane.showMessageDialog( null, "Invalid Entry" );
			return;
		}
		request = new RetrievePartRequestEmployeeSKU( sku, storeNumber );
			
		if( request.pName == null )
		{
			JOptionPane.showMessageDialog( null, "Invalid SKU", 
				"Error", JOptionPane.ERROR_MESSAGE );
		}
		else	
		{		
			output = "\nPart Number: " + request.pNumber +
				"\nPart Name: " + request.pName + "\nVehicle Number: " + request.vNumber +
				"\nCategory: " + request.pCategory + "\nCost: $" + twoDigits.format(request.pCost) +
				"\nCondition: " + request.pCond + "\nDescription: " + request.pDesc;
			
			do
			{	
				if( request.pQuantity == 0 )
				{
					JOptionPane.showMessageDialog( null, "No parts left in" +
						" inventory.", "Out of Part", 
						JOptionPane.INFORMATION_MESSAGE );
					flag = false;
					break;
				}
				else
				{
					String qString = null;
					try
					{
						qString = JOptionPane.showInputDialog( "Please enter quantity" );
						
					}
					catch( Exception e )
					{
						flag = false;
						quantity = 0;
						JOptionPane.showMessageDialog( null, "Invalid Quantity" );
						return;
					}
					quantity = Integer.parseInt( qString );

					if( quantity > request.pQuantity || quantity <= 0 )
					{
						JOptionPane.showMessageDialog( null, "Please enter a quantity" +
							" between 1 and " + request.pQuantity + ":", 
							"Error", JOptionPane.ERROR_MESSAGE );
						flag = false;
					}
					else
						flag = true;
				}
				
			}while( quantity > request.pQuantity || quantity == 0 );
		
			if( flag == true )	
			{	
				itemList.addPart( request, quantity );
				itemList.printContents();
			}
		}
	}
}
