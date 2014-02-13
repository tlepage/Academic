/*
 * Created on Dec 26, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package othello;

import javax.swing.*;
import java.util.*;
import basics.*;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

// convert move list printing to A-H and 1-8
// add borders ( A B C... )
// subtract 1 from each to convert to 0-7s

// check win and pass conditions

// start AI processes
public class Test implements Defines
{
	public static void main( String args[] )
	{
		OthelloBoard board = new OthelloBoard( 8, 8 );
		board.initBoard();
		board.updateSurroundings();
		board.showBoard();
		OthelloBoardAnalyzer analyzer = new OthelloBoardAnalyzer( board, WHITE,
			8, 8 );
		
		int position;
		BoardSquare testSquare;
		
		OthelloPlayer white = new OthelloPlayer( "tom", WHITE );
		OthelloPlayer black = new OthelloPlayer( "test", BLACK );
		
		// X AND Y ARE BACKWARDS!!!!!!!!
		boolean win = false, pass = false, validChoice = false;
		int move, curColor = WHITE, x = 0, y = 0;
		String xPos, yPos;
		OthelloRules rules = new OthelloRules();
		
		// play sequence
		while( !win )
		{
			board.updateSurroundings();
			analyzer.setValidMoves();
			board.showBoard();
			
			while( validChoice == false )
			{	
				xPos = JOptionPane.showInputDialog( board.getBoardString() + "White R: " );
				yPos = JOptionPane.showInputDialog( board.getBoardString() + "White C: " );
				x = Integer.parseInt( xPos );
				y = Integer.parseInt( yPos );
				
				validChoice = board.validateMove(x, y);
			}
			
			testSquare = board.getBoardSquare( x, y );
			position = ( x * 10 ) + y;
			white.addMove(position);
			analyzer.setBoard( board );
			analyzer.createPaths( testSquare, WHITE, position );
			analyzer.clearValidMoves();
			board.showBoard();
			
			win = rules.checkWin( board, WHITE );
			
			if( win )break;
			
			validChoice = false;
			analyzer.setCurrentColor( BLACK );
			board.updateSurroundings();
			analyzer.setValidMoves();
			board.showBoard();
			while( validChoice == false )
			{	
				xPos = JOptionPane.showInputDialog( board.getBoardString() + "Black R: " );
				yPos = JOptionPane.showInputDialog( board.getBoardString() + "Black C: " );

				x = Integer.parseInt( xPos );
				y = Integer.parseInt( yPos );
				
				validChoice = board.validateMove( x, y );
			}	
			testSquare = board.getBoardSquare( x, y );
			position = ( x * 10 ) + y;
			black.addMove(position);
			analyzer.setBoard( board );
			analyzer.createPaths( testSquare, BLACK, position );
			analyzer.clearValidMoves();
			board.showBoard();
			
			win = rules.checkWin( board, BLACK );
			if( win )break;
			
			analyzer.setCurrentColor( WHITE );
			validChoice = false;
			
			for( int i = 0; i < white.getMoveList().size(); i++ )
			{
				System.out.println( white.getMoveList().get(i).toString() + ":" +
					black.getMoveList().get(i).toString() + "\n" );
			}
			
		}
	}
}