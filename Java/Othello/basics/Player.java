/*
 * Created on Dec 18, 2003
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
public class Player
{
	private String playerID;
	private int winCount, lossCount;
	
	public Player( String name )
	{
		winCount = 0;
		lossCount = 0;
		playerID = name;
		
		System.out.println( "New Player is: " + playerID +
			"\nStatistics:\n\nWins: " + winCount + "\nLosses: " +
			lossCount + "\n" );
	}
	
	public void incrementWins()
	{
		winCount++;
		System.out.println("\nStatistics:\n\nWins: " + winCount + "\nLosses: " +
			lossCount + "\n" );
	}
	
	public int getWins()
	{
		return winCount;
	}
	
	public void incrementLosses()
	{
		lossCount++;
		System.out.println("\nStatistics:\n\nWins: " + winCount + "\nLosses: " +
			lossCount + "\n" );
	}
	
	public int getLosses()
	{
		return lossCount;
	}
	
	public String getID()
	{
		return playerID;
	}
}
