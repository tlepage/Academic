/*
 * Created on Dec 23, 2003
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
public class OthelloPlayer
{
	private int color;
	private Player player;
	private Vector moveList;
	private Integer moveObj;
	
	public OthelloPlayer( String name, int playerColor )
	{
		player = new Player( name );
		color = playerColor;
		moveList = new Vector();
	}
	
	public Vector getMoveList()
	{
		return moveList;
	}
	
	public void addMove( int move )
	{
		moveObj = new Integer( move );
		moveList.add(moveObj);
	}
	
	public Vector selectMove( int x, int y, int value )
	{
		Vector position = new Vector(1);
		
		Integer pos = new Integer( (x * 10) + y );
		position.add( 0, pos );
		
		System.out.println( "Move: " + pos.toString() );
		return position;
	}
	
	public Vector makeMove()
	{
		String move = JOptionPane.showInputDialog( "Select move.");
		int moveInt = Integer.parseInt( move );
		int x = moveInt / 10;
		int y = moveInt - ( x * 10 );
		
		Vector position = selectMove( x, y, color );
		
		return position;
	}

	public int getColor()
	{
		return color;
	}
}
