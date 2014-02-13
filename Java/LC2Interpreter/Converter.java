/* Name:    Thomas LePage twl9586
 * Class:   Converter.java
 * Purpose: Unit conversion utilities
 */
 
public class Converter
{
	private String instrString;
	private int decimalValue;
	
/* Function: Converter()
 * Purpose:  to initialize the attributes of the Converter object
 * Input:    nothing
 * Output:   nothing
 */
	public Converter()
	{
		instrString = null;
		decimalValue = 0;
	}
	
/* Function: binToDec()
 * Purpose:  to convert a binary string to a decimal number
 * Input:    a String representing a 16-bit binary number
 * Output:   an integer representing the decimal equivalent of the string
*/
	public int binToDec( String currentString )
	{
		int result = 0, binCount = 0;
		
		instrString = currentString;
		 
		for( int t = 15; t > -1; t-- )
		{
			if( instrString.charAt(t) == '1' )
			{
				result += ( Math.pow( 2, binCount ) );
			}
			
			binCount++;
		}
		return result;
	}

/* Function: decToBin()
 * Purpose:  to convert from an integer to a binary string
 * Input:    an integer representing the decimal value
 * Output:   a string representing the binary code
 */	
	public String decToBin( int value )
	{
		String result = null;
		
		result = Integer.toBinaryString( value );
	
		return result;
	}

/* Function: decToHex()
 * Purpose:  to convert an integer to a hex string
 * Input:    an integer representing a decimal value
 * Output:   a string representing the hex value
 */
	public String decToHex( int value )
	{
		String result = null;
		
		result = "0x" + Integer.toHexString( value );
		
		return result;
	}

/* Function: toHexDigit()
 * Purpose:  converts a 4-digit binary string to a hex digit
 * Input:    a 4 digit binary string
 * Output:   a character representing the hex digit
 */
	public char toHexDigit( String bin )
	{
		char result = '0';
		String tempResult = null;
		int tempInt = 0, binCount = 0;
		
		// check for proper length
		if( bin.length() > 4 )
		{
			System.out.println( "Converter: Requires 4 binary digits." );
		}
		else
		{
			for( int t = 3; t > -1; t-- )
			{
				if( bin.charAt(t) == '1' )
				{
					tempInt += ( Math.pow( 2, binCount ));
				}
				
				binCount++;
			}
			
			tempResult = Integer.toHexString( tempInt );
			result = tempResult.charAt(0);
		}
		
		return result;
	}
}
