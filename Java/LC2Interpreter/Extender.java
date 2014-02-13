/* Name:    Thomas LePage twl9586
 * Class:   Extender.java
 * Purpose: String extending utilities
 */
 
public class Extender
{

/* Function: Extender()
 * Purpose:  to initialize the attributes of an Extender object
 * Input:    nothing
 * Output:   nothing
 */
	public Extender()
	{
		// empty constructor
	}


/* Function: SEXT()
 * Purpose:  to sign extend a binary string to 16 bits
 * Input:    a binary string
 * Output:   a sign extended binary string
 */
	public String SEXT( String binString )
	{
		String result = null;
		
		if( binString.length() == 16 )
		{
			result = binString;
		}
		else
		{
			// get MSB
			char extendedBit = binString.charAt(0);
			
			StringBuffer extensionBuffer = new StringBuffer();
			
			// create a bit string of extended bit
			for( int t = 0; t < (16 - binString.length()); t++ )
			{
				extensionBuffer.append( extendedBit );
			}
			
			// concatenate to original string
			result = 
				extensionBuffer.toString().concat( binString );
		}
		
		return result;
	}

/* Function: ZEXT()
 * Purpose:  to zero extend a binary string
 * Input:    a binary string
 * Output:   a zero extended binary string
 */
	public String ZEXT( String binString )
	{
		String result = null;
		
		if( binString.length() == 16 )
		{
			result = binString;
		}
		else
		{
			char extendedBit = '0';
			
			StringBuffer extensionBuffer = new StringBuffer();
			
			// create a zero extended string
			for( int t = 0; t < (16 - binString.length()); t++ )
			{
				extensionBuffer.append( extendedBit );
			}
			
			// concatenate with original string
			result = 
				extensionBuffer.toString().concat( binString );
		}
		
		return result;
	}

/* Function: createOffset()
 * Purpose:  to create a page offset string
 * Input:    the current instruction string and the page offset string
 * Output:   a new string containing the top 7 bits of the instruction
 *           string and the 9 bits of the page offset
 */
	public String createOffset( String currInstr, String pgOffset )
	{
		String result = null;
		
		// get top 7 bits
		String curr7Bits = currInstr.substring(0, 7);
		
		// concatenate with page offset
		result = curr7Bits.concat( pgOffset );
		
		return result;
	}
}
