/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package othello;
import basics.*;

/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OthelloRules implements Defines
{
	public OthelloRules()
	{
		System.out.println("Rules created.");
	}
	
	public boolean checkPass( OthelloBoard board, int curColor )
	{
		boolean isValidLeft = false;
		
		for( int i = 0; i < board.getXDim(); i++ )
		{
			for( int j = 0; j < board.getYDim(); j++ )
			{
				if( board.getValue( i, j ) == VALID )
				{
					isValidLeft = true;
				}
			}
		}
		
		if( isValidLeft )System.out.println( "Pass." );
		
		return isValidLeft;
	}
	
	public boolean checkWin( OthelloBoard board, int curColor )
	{
		boolean isWinCondition = true;
		
		for( int i = 0; i < board.getXDim(); i++ )
		{
			for( int j = 0; j < board.getYDim(); j++ )
			{
				if( board.getValue( i, j ) == EMPTY )
				{
					isWinCondition = false;
				}
			}
		}
		
		if( isWinCondition )System.out.println( "Winner");
		
		return isWinCondition;
	}
}
