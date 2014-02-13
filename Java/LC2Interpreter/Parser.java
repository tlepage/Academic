/* Name:    Thomas LePage twl9586
 * Class:   Parser.java
 * Purpose: String parser utilities
 */
 
public class Parser
{
	private int code;
	private String instrString;
	private Converter converter;

/* Function: Parser()
 * Purpose:  to initialize the attributes of a Parser object
 * Input:    nothing
 * Output:   nothing
 */
	public Parser()
	{
		instrString = null;
		code = 0;
		converter = new Converter();
	}

/* Function: parseString()
 * Purpose:  to create a code representing each function and subfunction
 * Input:    the current instruction string
 * Output:   an integer representing the function code
 */
	public int parseString( String currentInstr )
	{
		instrString = currentInstr;
		String functionString = instrString.substring( 0, 4 );
		char dest = converter.toHexDigit( functionString );
		
		switch( dest )
		{
			case '0': // BR
				code = 4;
				break;
				
			case '1': // ADD
				if( instrString.charAt(10) == '0' )
					code = 0;
				else
					code = 1;
				break;
			
			case '2': // LD
				code = 7;
				break;
			
			case '3': // ST
				code = 13;
				break;
			
			case '4': // JSR
				code = 5;
				break;
			
			case '5': // AND 
				if( instrString.charAt(10) == '0' )
					code = 2;
				else
					code = 3;
				break;
				
			case '6': // LDR
				code = 9;
				break;
				
			case '7': // STR
				code = 15;
				break;
			
			case '9': // NOT
				code = 11;
				break;
				
			case 'a': // LDI
				code = 8;
				break;
			
			case 'b': // STI
				code = 14;
				break;
			
			case 'c': // JSSR
				code = 6;
				break;
			
			case 'd': // RET
				code = 12;
				break;
				
			case 'e': // LEA
				code = 10;
				break;
			
			case 'f': // TRAP
				code = 16;
				break;
		}
		
		return code;
	}
}
