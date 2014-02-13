/*
 * Created on Dec 18, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package basics;

import javax.swing.*;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Board implements Defines
{
	// might add stylized properties for the boards
	private int xDim, yDim;
	private BoardSquare squares[][];
	public String boardString;
	public Board( int x, int y )
	{
		xDim = x;
		yDim = y;
		
		squares = new BoardSquare[xDim][yDim];
		
		for( int i = 0; i < xDim; i++ )
		{
			for( int j = 0; j < yDim; j++ )
			{
				squares[i][j] = new BoardSquare(9);	
			}
		}
		
		System.out.println( "Created a board object of size " +
			xDim + "x" + yDim + ".\n" );
	}
	// create print function
	public void printBoardValues()
	{
		boardString = "";
		for( int s = xDim - 1; s > -1; s-- )
		{
			for( int t = 0; t < yDim; t++ )
			{
				boardString += squares[s][t].getValue();
				boardString += " ";
				System.out.print( squares[s][t].getValue() );
				System.out.print( " " );
				if( t == (yDim - 1))
				{
					boardString += "\n";
					System.out.print( "\n" );
				}
			}
		}
		boardString += "\n";
		System.out.println();
	}
	
	public void setBoardValue( int x, int y, int value )
	{
		squares[x][y].setValue( value );
	}
	
	public int getBoardValue( int x, int y )
	{
		return squares[x][y].getValue();
	}
	
	public void setSurroundings()
	{
		BoardSquare nullSquare = new BoardSquare(-1);
		
		for( int i = 0; i < xDim; i++ )
		{
			for( int j = 0; j < yDim; j++ )
			{
				// set north neighbor, if possible
				if((i - 1) < 0 ) squares[i][j].setSurroundings( NORTH, nullSquare );
				else squares[i][j].setSurroundings( NORTH, squares[i-1][j].getSquare() );
				
				// set northeast neighbor, if possible
				if(((i - 1) < 0 ) || ((j + 1) > 7 )) squares[i][j].setSurroundings( NORTHEAST, nullSquare );
				else squares[i][j].setSurroundings( NORTHEAST, squares[i-1][j+1].getSquare() );
				
				// set east neighbor, if possible
				if((j + 1) > 7 ) squares[i][j].setSurroundings( EAST, nullSquare );
				else squares[i][j].setSurroundings( EAST, squares[i][j+1].getSquare() );
				
				// set southeast neighbor, if possible
				if(((i + 1) > 7 ) || ((j + 1) > 7 )) squares[i][j].setSurroundings( SOUTHEAST, nullSquare );
				else squares[i][j].setSurroundings( SOUTHEAST, squares[i+1][j+1].getSquare() );
				
				// set south neighbor, if possible
				if((i + 1) > 7 ) squares[i][j].setSurroundings( SOUTH, nullSquare );
				else squares[i][j].setSurroundings( SOUTH, squares[i+1][j].getSquare() );
				
				// set southwest neighbor, if possible
				if(((i + 1) > 7) || ((j - 1) < 0 )) squares[i][j].setSurroundings( SOUTHWEST, nullSquare );
				else squares[i][j].setSurroundings( SOUTHWEST, squares[i+1][j-1].getSquare() );
				
				// set west neighbor, if possible
				if((j - 1) < 0 ) squares[i][j].setSurroundings( WEST, nullSquare );
				else squares[i][j].setSurroundings( WEST, squares[i][j-1].getSquare() );
				
				// set northwest neighbor, if possible
				if(((i - 1) < 0) || ((j - 1) < 0 )) squares[i][j].setSurroundings( NORTHWEST, nullSquare );
				else squares[i][j].setSurroundings( NORTHWEST, squares[i-1][j-1].getSquare() );
			}
		}
		
		System.out.println( "Created surroundings." );
	}
	public void printSquareSurroundings()
	{
		for( int i = 0; i < xDim; i++ )
		{
			for( int j = 0; j < yDim; j++ )
			{
				squares[i][j].printSurroundings();
			}
		}
	}
	
	public BoardSquare getSquare( int i, int j )
	{
		return squares[i][j].getSquare();
	}
	
	public static void main( String args[] )
	{
		Board b = new Board( 8, 8 );
		//b.printBoardValues();
		System.exit( 0 );
	}
}
