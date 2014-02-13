/*
 * Created on Dec 23, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package basics;

/**
 * @author ZyloWolf
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestBasics
{
	public static void main( String args[] )
	{
		Board board = new Board( 8, 8 );
		Player player = new Player( "Tom" );
		board.printBoardValues();
		
		player.incrementLosses();
		player.incrementWins();
	}
}
