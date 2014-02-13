/*
 * Created on Dec 21, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package basics;

import java.util.*;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BoardSquare
{
	//graphics options for length, width, later
	private int value;
	private int counts[];
	private Vector surroundings;
	private Object convObj, dirObj;
	private BoardSquare convSquare;
	
	public BoardSquare( int startValue )
	{
		value = startValue;
		surroundings = new Vector(8);
		counts = new int[8];
		
		for( int i = 0; i < 8; i++ )
		{
			counts[i] = 0;
		}
	}
	
	public void setValue( int newValue )
	{
		value = newValue;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public BoardSquare getSquare()
	{
		return this;
	}
	
	public Vector getSurroundings()
	{
		return surroundings;
	}
	
	public int getSurroundingValue( int direction )
	{
		convSquare = (BoardSquare)surroundings.get(direction);
		// get value from surroundings object first!!!!
		int value = convSquare.getValue();
		
		return value;
	}
	
	public void setSurroundingValue( int direction, int value )
	{
		// HERE!!!!
		convSquare = (BoardSquare)surroundings.get(direction);
		convSquare.setValue( value );
		surroundings.set(direction, convSquare );
	}
	
	public void setSurroundings( int index, BoardSquare neighbor )
	{
		surroundings.add( index, neighbor );
	}
	
	public BoardSquare getSurroundingSquare( int direction )
	{
		return (BoardSquare)surroundings.get(direction);
	}
	
	public int getCount( int direction )
	{
		return counts[direction];
	}
	
	public void setCount( int direction, int value )
	{
		counts[direction] = value;
	}
	
	public void setCountSub( int direction )
	{
		if( counts[direction] == 0 )
			counts[direction] = 0;
		else 
			counts[direction]--;
	}
	
	public void resetCount( int direction )
	{
		counts[direction] = 0;
	}
	public void printSurroundings()
	{
		System.out.println( "New board square: " );
		
		for( int i = 0; i < 8; i++ )
		{
			convObj = surroundings.get(i);
			convSquare = (BoardSquare)convObj;
			
			switch( i )
			{
				case 0: System.out.print( "NORTH     " );
					break;
				case 1: System.out.print( "NORTHEAST " );
					break;
				case 2: System.out.print( "EAST      " );
					break;
				case 3: System.out.print( "SOUTHEAST " );
					break;
				case 4: System.out.print( "SOUTH     " );
					break;
				case 5: System.out.print( "SOUTHWEST " );
					break;
				case 6: System.out.print( "WEST      " );
					break;
				case 7: System.out.print( "NORTHWEST " );
					break;
			}
			
			if( convSquare.getValue() == -1 )
				System.out.print( "null\n" );
			else
				System.out.print( convSquare.getValue() + "\n" );
		}
	}
}
