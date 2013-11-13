/*
 * Created on Nov 9, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.text.DecimalFormat;

public class Cart extends JFrame
{
	protected final int QUANTITY_MAX = 50;
	protected RetrievePartRequestSKU parts[];
	
	protected int currentAmount = 0, setAmount = 0;
	protected double total = 0.0;
	protected int purchaseQuantity[];
	protected String currentOrder;
	
	protected boolean isEmpty = true;
	
	DecimalFormat twoDigits = new DecimalFormat( "0.00" );
	public Cart()
	{
		super( "Shopping Cart");
		
		
		parts = new RetrievePartRequestSKU[QUANTITY_MAX];
		purchaseQuantity = new int[QUANTITY_MAX];
		
		for( int i = 0; i < QUANTITY_MAX; i++ )
		{
			purchaseQuantity[i] = 0;
		}
	}
	
	public void addPart( RetrievePartRequestSKU request, int quantity )
	{
		boolean isSame = false;
		
		if( currentAmount >= QUANTITY_MAX )
		{
			JOptionPane.showMessageDialog( null, "Cart is full.",
				"Cart Full", JOptionPane.ERROR_MESSAGE );
		}
		else
		{
			System.out.println( "isSame = " + isSame );
			if( currentAmount != 0 )
			{ 
				System.out.println( "Current Amount greater than 0");
				for( int i = currentAmount - 1; i > -1; i-- )
				{
					System.out.println( "Current Amount: " + currentAmount );
					System.out.println( "PartNumber for parts[i]: " + parts[i].pNumber );
					System.out.println( "PartNumber for request: " + request.pNumber );
					if( parts[i].pNumber.equals(request.pNumber) )
					{
						purchaseQuantity[i] += quantity;
						System.out.println( "Same part found. Adding to previous." );
						isSame = true;
					}
				}
			}
			if( !isSame )
			{
				System.out.println( "Not same part." );
				parts[currentAmount] = request;
				purchaseQuantity[currentAmount] = quantity;
				System.out.println("Added part to cart");
				System.out.println("Part: " + request.pName );
				currentAmount++;
				setAmount++;
				isEmpty = false;
			}
		}
	}
	
	public void removePart( int partNumber )
	{
		boolean flag = false;
		
		if( purchaseQuantity[partNumber] == -1 || purchaseQuantity[partNumber] == 0 )
		{
			JOptionPane.showMessageDialog( null, "No such part.", "Error",
				JOptionPane.ERROR_MESSAGE );
		}
		else
		{
			do
			{
				String quantityRemove = null;
				try
				{
					quantityRemove = JOptionPane.showInputDialog( 
						"What quantity do you want removed(-1 for all):" );	
				}
				catch( Exception e )
				{
					quantityRemove = "0";
				} 
				int quantity = Integer.parseInt( quantityRemove );
			
				if( quantity > purchaseQuantity[partNumber] || 
					quantity < -1 )
				{
					JOptionPane.showMessageDialog( null, "Not a valid quantity",
						"Error", JOptionPane.ERROR_MESSAGE );
					flag = false;	
				}
				else
				{
					if( quantity == -1 )
					{
						purchaseQuantity[partNumber] = -1;
						currentAmount--;
						if( currentAmount <= 0 )
						{
							isEmpty = true;
							setAmount = 0;
						}
					}
					else
					{
						purchaseQuantity[partNumber] -= quantity;
						if( purchaseQuantity[partNumber] == 0 )
						{
							purchaseQuantity[partNumber] = -1;
							currentAmount--;
							if( currentAmount <= 0 )
							{
								isEmpty = true;
								setAmount = 0;
							}
						}
						
					}
					flag = true;
					
					JOptionPane.showMessageDialog( null, "Cart successfully" +						" updated.", "Cart Updated", 
						JOptionPane.INFORMATION_MESSAGE);
				}
				
			}while( flag == false );
		}
	}
	
	public void printContents()
	{
		String totalString, output = "";
		total = 0;
		
		
		if( currentAmount == 0 ) 
		
		{
			output = "The cart is empty.\n";
			isEmpty = true;
			
			JOptionPane.showMessageDialog( null, output, "Cart Empty",
				JOptionPane.INFORMATION_MESSAGE);
			System.out.println(output);
		
		}
		else
		{
			System.out.println( "Current Amount: " + currentAmount );
			for( int i = 0; i < setAmount; i++ )
			{
				System.out.println( "Purchase Quantity: " + purchaseQuantity[i] );
				if( parts[i].pName != null || purchaseQuantity[i] != 0 )
				{	
					if( purchaseQuantity[i] == -1 )
						output = "";
					else
					{	
						output += "\n\nCart ID: " + i + "\nPart Number: " + parts[i].pNumber +
						"\nPart Name: " + parts[i].pName + "\nVehicle Number: " + 
						parts[i].vNumber + "\nCategory: " + parts[i].pCategory + 
						"\nCost: $" + twoDigits.format(parts[i].pCost) + "\nCondition: " + 
						parts[i].pCond + "\nDescription: " + parts[i].pDesc + 
						"\nQuantity: " + purchaseQuantity[i];
					
						total += (parts[i].pCost * purchaseQuantity[i]);
						System.out.println( output );
						System.out.println( "Total is: " + twoDigits.format(total) + "\n" );
						output += ("\nTotal: $" + twoDigits.format(total) );
					}
				}
				else
				{
					output = "";
					break;
				}
			}
		
			currentOrder = output;
		
		}
		if( isEmpty )
			output = "";
		if( !isEmpty )
			new CartPage( output, total );
		
	}
}
