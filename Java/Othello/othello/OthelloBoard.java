/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package othello;

import java.util.*;
import basics.*;

/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OthelloBoard implements Defines
{
	private Board othelloBoard;
	private int xDim, yDim;
	
	public OthelloBoard( int x, int y )
	{
		othelloBoard = new Board( x, y );
		xDim = x;
		yDim = y;
	}
	
	public void showBoard()
	{
		othelloBoard.printBoardValues();
	}
	
	public void updateSurroundings()
	{
		othelloBoard.setSurroundings();
	//	othelloBoard.printSquareSurroundings();
	}
	public void setValue( int x, int y, int value )
	{
		othelloBoard.setBoardValue( x, y, value );
	} 
	
	public int getXDim()
	{
		return xDim;
	}
	
	public int getYDim()
	{
		return yDim;
	}
	
	public int getValue( int x, int y )
	{
		return othelloBoard.getBoardValue( x, y );
	}
	
	public void updateBoard( Vector updates, int playerValue )
	{
		int length = updates.size();
		int position, x, y;
		boolean valid = false;
		
		
			position = convertUpdate( updates );
			x = position / 10;
			y = (position - ( x * 10 ));
			
			valid = validateMove( x, y );
			
			if( !valid )
			{
				System.out.println( "Invalid move.");
				return;
			}
			else
			{
				System.out.println( "X is " + x + " in updateBoard" );
				System.out.println( "Y is " + y + " in updateBoard\n" );
			
				setValue( x, y, playerValue );
			}
		
	}
	
	public int convertUpdate( Vector updates )
	{
		int position;
		Object convObj = new Object();
		
		Integer convInt = (Integer)updates.get(0);
		int x = convInt.intValue() / 10;
		int y = (convInt.intValue() - (10 * x));
		 
		position = ( x * 10 ) + y;
		
		return position;
	}
	
	public BoardSquare getBoardSquare( int i, int j )
	{
		return othelloBoard.getSquare( i, j );
	}
	
	public void initBoard()
	{
		othelloBoard.setBoardValue( 3, 3, 0 );
		othelloBoard.setBoardValue( 4, 4, 0 );
		othelloBoard.setBoardValue( 3, 4, 1 );
		othelloBoard.setBoardValue( 4, 3, 1 );
		
		othelloBoard.setSurroundings();
	//	othelloBoard.printSquareSurroundings();
	}
	
	public boolean validateMove( int x, int y )
	{
		BoardSquare testSquare = othelloBoard.getSquare( x, y );
		boolean valid = true;
		
		if( testSquare.getValue() != VALID )
		{
			valid = false;
		}
		
		return valid;
	}
	
	public String getBoardString()
	{
		return othelloBoard.boardString;
	}
}
