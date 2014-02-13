/*
 * Created on Dec 30, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package othello;

import ai.*;
import basics.*;
/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OthelloBoardAnalyzer implements Defines
{
	private OthelloBoard currentBoard;
	private int OPP, SAME;
	private int xDim, yDim;
	
	public OthelloBoardAnalyzer( OthelloBoard board, int curColor,
								 int x, int y )
	{
		setBoard( board );
		xDim = x;
		yDim = y;
		setCurrentColor( curColor );
		//BoardAnalyzer analyzer = new BoardAnalyzer();
	}
	
	public void setBoard( OthelloBoard board )
	{
		currentBoard = board;
	}
	
	public void setCurrentColor( int curColor )
	{
		if( curColor == WHITE )
		{
			OPP = BLACK;
			SAME = WHITE;
		}
		else
		{
			OPP = WHITE;
			SAME = BLACK;
		}
	}
	
	public void setValidMoves()
	{
		BoardSquare currentSquare, markedSquare, tempSquare, moveSquare;
		int n, ne, e, se, s, sw, w, nw, c;
		
		for( int i = 0; i < xDim; i++ )
		{
			for( int j = 0; j < yDim; j++ )
			{	
				//currentBoard.updateSurroundings();
				currentSquare = currentBoard.getBoardSquare( i, j );
				n = currentSquare.getSurroundingValue( NORTH );
				s = currentSquare.getSurroundingValue( SOUTH );
				e = currentSquare.getSurroundingValue( EAST );
				w = currentSquare.getSurroundingValue( WEST );
				ne = currentSquare.getSurroundingValue( NORTHEAST );
				se = currentSquare.getSurroundingValue( SOUTHEAST );
				sw = currentSquare.getSurroundingValue( SOUTHWEST );
				nw = currentSquare.getSurroundingValue( NORTHWEST );
				c = currentSquare.getValue();
			
				if( c == OPP )
				{
					System.out.println( "Player: " + SAME );
					System.out.println( "Current: " + c );
					System.out.println( "North: " + n );
					System.out.println( "South: " + s );
					System.out.println( "East: " + e );
					System.out.println( "West: " + w );
					
					/////////////////////////////////////////
					// check N and S
					if( (n == SAME ) && (s == EMPTY))
					{
						System.out.println( "At " + i + ", " + j + 
							" changing south.");
						currentSquare.setSurroundingValue( SOUTH, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(SOUTH));
					}
					else if( (s == SAME ) && (n == EMPTY))
					{
						System.out.println( "At " + i + ", " + j +
							" changing north." );
						currentSquare.setSurroundingValue( NORTH, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(NORTH));
					}
					else if( (n == OPP) && (s == SAME) )
					{
						// keep current square in temp
						tempSquare = currentSquare;
						
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTH);
						
						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving south.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTH);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (n == OPP) && (s == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;
	
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTH);
	
						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(SOUTH).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving north.");
								moveSquare = moveSquare.getSurroundingSquare(NORTH);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (s == OPP) && (n == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
						
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTH);
						
						// keep going north 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving north.");
								moveSquare = moveSquare.getSurroundingSquare(NORTH);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (s == OPP) && (n == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTH);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(NORTH).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving south.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTH);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					//////////////////////////////////////////////
					// check E and W
					if( (e == SAME ) && (w == EMPTY))
					{
						System.out.println( "At " + i + ", " + j + 
							" changing west.");
						currentSquare.setSurroundingValue( WEST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(SOUTH));
					}
					else if( (w == SAME ) && (e == EMPTY))
					{
						System.out.println( "At " + i + ", " + j +
							" changing east." );
						currentSquare.setSurroundingValue( EAST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(NORTH));
					}
					else if( (e == OPP) && (w == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
						
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(EAST);
						
						// keep going east 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving east.");
								moveSquare = moveSquare.getSurroundingSquare(EAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (e == OPP) && (w == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(EAST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(WEST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving east.");
								moveSquare = moveSquare.getSurroundingSquare(EAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (w == OPP) && (e == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
						
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(WEST);
						
						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving west.");
								moveSquare = moveSquare.getSurroundingSquare(WEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (w == OPP) && (e == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(WEST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(EAST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving west.");
								moveSquare = moveSquare.getSurroundingSquare(WEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}	
				
					////////////////////////////////////////
					// check NE and SW
					if( (ne == SAME ) && (sw == EMPTY))
					{
						System.out.println( "At " + i + ", " + j + 
							" changing sw.");
						currentSquare.setSurroundingValue( SOUTHWEST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(SOUTHWEST));
					}
					else if( (sw == SAME ) && (ne == EMPTY))
					{
						System.out.println( "At " + i + ", " + j +
							" changing ne." );
						currentSquare.setSurroundingValue( NORTHEAST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(NORTHEAST));
					}
					else if( (ne == OPP) && (sw == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
							
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTHEAST);
							
						// keep going northeast 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving northeast.");
								moveSquare = moveSquare.getSurroundingSquare(NORTHEAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (ne == OPP) && (sw == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTHEAST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(SOUTHWEST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving northeast.");
								moveSquare = moveSquare.getSurroundingSquare(NORTHEAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (sw == OPP) && (ne == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
							
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTHWEST);
							
						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving southwest.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTHWEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}	
					else if( (sw == OPP) && (ne == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTHWEST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(NORTHEAST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving southwest.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTHWEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					//////////////////////////////////////////
					// check NW and SE
					if( (se == SAME ) && (nw == EMPTY))
					{
						System.out.println( "At " + i + ", " + j + 
							" changing nw.");
						currentSquare.setSurroundingValue( NORTHWEST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(NORTHWEST));
					}
					else if( (nw == SAME ) && (se == EMPTY))
					{
						System.out.println( "At " + i + ", " + j +
							" changing se." );
						currentSquare.setSurroundingValue( SOUTHEAST, VALID );
						System.out.println( "New value: " + currentSquare.getSurroundingValue(SOUTHEAST));
					}
					else if( (se == OPP) && (nw == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
								
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTHEAST);
								
						// keep going east 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving southeast.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTHEAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (se == OPP) && (nw == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(SOUTHEAST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(NORTHWEST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving southeast.");
								moveSquare = moveSquare.getSurroundingSquare(SOUTHEAST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (nw == OPP) && (se == SAME))
					{
						// keep current square in temp
						tempSquare = currentSquare;
			
						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTHWEST);
			
						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving northwest.");
								moveSquare = moveSquare.getSurroundingSquare(NORTHWEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Marking.");
								moveSquare.setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}
					else if( (nw == OPP) && (se == EMPTY ))
					{
						// keep current square in temp
						tempSquare = currentSquare;

						// make moving square
						moveSquare = currentSquare.getSurroundingSquare(NORTHWEST);

						// keep going south 
						while( moveSquare.getValue() != INVALID ) 
						{
							if( moveSquare.getValue() == SAME )
							{
								System.out.println( "Same square found. Marking.");
								currentSquare.getSurroundingSquare(SOUTHEAST).setValue(VALID);
								break;
							}
							else if( moveSquare.getValue() == OPP )
							{
								System.out.println( "Opposing square found. Moving northwest.");
								moveSquare = moveSquare.getSurroundingSquare(NORTHWEST);
							}
							else if( moveSquare.getValue() == EMPTY )
							{
								System.out.println( "Empty square found. Stopping.");
								break;
							}
							else if( moveSquare.getValue() == VALID )
							{
								System.out.println( "Valid square found. Breaking." );
								break;
							}
						}
					}	
				}
			}
		}
			
	}
	
	public void clearValidMoves()
	{
		for( int i = 0; i < xDim; i++ )
		{
			for( int j = 0; j < yDim; j++ )
			{
				if( currentBoard.getBoardSquare(i, j).getValue() == VALID )
				{
					currentBoard.getBoardSquare( i, j).setValue( EMPTY );
				}
			}
		}
	}
	// !!! TEST THIS MORE !!!!
	public void createPaths( BoardSquare selectedSquare, int curColor,
							 int position )
	{
		int x = position / 10;
		int y = position - ( x * 10 );
		int currentCount = 0;
		BoardSquare tempSquare;
		
		selectedSquare.setValue( curColor );
		
		for( int i = 0; i < 8; i++ )
		{
			if( selectedSquare.getSurroundingValue(i) == OPP )
			{
				tempSquare = selectedSquare;
				for( int t = 0; t < 8; t++ )
				{
					while( tempSquare.getSurroundingValue(i) == OPP )
					{
						//tempSquare.setCountAdd(i);
						currentCount++;
						//System.out.println( "Count for " + i + " is :" +
						//	selectedSquare.getCount(i));
						//System.out.println( "Value for surrounding square" +						//	" is: " + tempSquare.getSurroundingValue(i));
						
						tempSquare = tempSquare.getSurroundingSquare(i);
						if( tempSquare.getSurroundingValue(i) == VALID 
							|| tempSquare.getSurroundingValue(i) == EMPTY
							|| tempSquare.getSurroundingValue(i) == INVALID )
						{
							System.out.println( "Found non-opp square." );
							//selectedSquare.resetCount(i);
							currentCount = 0;
							System.out.println( "Count after resetting: " +
								//tempSquare.getCount(i));
								currentCount );
							break;
						}
						
					}
					
					selectedSquare.setCount( i, currentCount );
					System.out.println( "Count for " + i + " is " + currentCount );
					
				
				}
				//tempSquare.setValue( curColor );
				
				// !!!Paths must end in same color in order to be valid
				// keep going until find same color, empty, invalid, 
				// or valid square if empty or valid, don't mark, clear 
				// counter else go through same direction and mark number of
				// squares that counter reflects
				currentCount = 0;	
			}
			
		}
		System.out.println( "Count for 0 is " + selectedSquare.getCount(0));
		for( int i = 0; i < 8; i++ )
		{
			System.out.println( "Creating paths, count for " + i + 
				" is " + selectedSquare.getCount(i));
			if( selectedSquare.getCount(i) != 0 )
			{
				selectedSquare.setValue( curColor );
				tempSquare = selectedSquare.getSurroundingSquare(i);
				System.out.println( "Count: " + selectedSquare.getCount(i));
				while( selectedSquare.getCount(i) > 0 )
				{
					tempSquare.setValue( curColor );
					selectedSquare.setCountSub( i );
					System.out.println( "Setting count to " + 
						tempSquare.getCount(i) );
					
					if( tempSquare.getSurroundingValue(i) == OPP )
					{
						System.out.println( "Moving to next opposing square");
						tempSquare = tempSquare.getSurroundingSquare(i);
					}
					else break;
				}
				
			}
		}
	}
	
	public OthelloBoard returnBoard()
	{
		return currentBoard;
	}
}
