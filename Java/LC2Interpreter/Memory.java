/* Name:    Thomas LePage twl9586
 * Class:   Memory.java
 * Purpose: Memory storage and retrieval utilities
 */

public class Memory
{
	private int memStack[];
	private int IP, currentIPValue, lastIP;
	
/* Function: Memory()
 * Purpose:  to initialize the attributes of a Memory object
 * Input:    nothing
 * Output:   nothing
 */
	public Memory()
	{
		memStack = new int[65536];
		
		for( int t = 0; t < 65536; t++ )
		{
			memStack[t] = 0;
		}	
		
		IP = 0;
		currentIPValue = memStack[IP];
		lastIP = IP;
	}
	
/* Function: getValueAtMemAddress()
 * Purpose:  returns the contents of memory at a certain address
 * Input:    an integer representing the index in memory
 * Output:   the integer value representing contents of memory
 */
	public int getValueAtMemAddress( int index )
	{
		int result = 0;
		
		try
		{
			result =  memStack[index];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Memory: Out of Bounds Exception thrown." );
		}
		
		return result;
	}

/* Function: setValueAtMemAddress()
 * Purpose:  set the memory contents at a certain index
 * Input:    an integer representing the index and an integer representing
 *           the value to be put into memory
 * Output:   nothing
 */
	public void setValueAtMemAddress( int index, int value )
	{
		try
		{
			memStack[index] = value;
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Memory: Out of Bounds Exception thrown." );
		}
	}

/* Function: getIPIndex()
 * Purpose:  to get the current IP
 * Input:    nothing
 * Output:   an integer representing the IP 
 */
	public int getIPIndex()
	{
		return IP;
	}

/* Function: setIPIndex()
 * Purpose:  to set the current IP
 * Input:    an integer representing the new IP
 * Output:   nothing
 */
	public void setIPIndex( int index )
	{
		try
		{
			lastIP = IP;
			IP = index;
			currentIPValue = memStack[IP];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Memory: Out of Bounds Exception thrown." );
			IP = lastIP;
			currentIPValue = memStack[lastIP];
		}
	}

/* Function: printUsedMemoryContents()
 * Purpose:  to print out the contents of all used memory addresses
 * Input:    nothing
 * Output:   nothing
 */
	public void printUsedMemContents()
	{
		System.out.println( "\nMemory Contents\n" );
		for( int t = 0; t < 65536; t++ )
		{
			if( memStack[t] != 0 )
			{
				System.out.println( "Memory[" + t + "]: " + memStack[t] );
			}
		}
	}
}
