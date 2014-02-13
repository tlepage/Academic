/* Name:    Thomas LePage twl9586
 * Class:   Registers.java
 * Purpose: Register storage and retrieval utilities
 */
 
public class Registers
{
	private int registers[];
	private Converter converter;
	private int N, Z, P;
	
/* Function: Registers()
 * Purpose:  to initialize the attributes of a Register object
 * Input:    nothing
 * Output:   nothing
 */
	public Registers()
	{
		converter = new Converter();
		
		registers = new int[8];
		
		N = 0;
		Z = 0;
		P = 0;
		
		for( int t = 0; t < 8; t++ )
		{
			registers[t] = 0;
		}
	}

/* Function: clearRegisters()
 * Purpose:  to clear the registers
 * Input:    nothing
 * Output:   nothing
 */
	public void clearRegisters()
	{
		for( int t = 0; t < 8; t++ )
		{
			registers[t] = 0;
		}
	}

/* Function: overBounds()
 * Purpose:  to check to see if an addition created a result that was larger
 * 	         than 65535, and if so, roll it over to 0 and above
 * Input:    the integer value
 * Output:   the rolled over value, if necessary
 */
	public int overBounds( int value )
	{
		if( value > 65535 )
		{
			value = 0 + (value - 65535);
		}
		return value;
	}
	
/* Function: getRegValue()
 * Purpose:  to return the contents of a register
 * Input:    an integer representing the register index
 * Output:   the integer contents of a register
 */
	public int getRegValue( int index )
	{
		int result = 0;
		try
		{
			result = registers[index];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Register: Out Of Bounds Exception thrown." );
		}
		return result;
	}
	
/* Function: setRegValue()
 * Purpose:  to set the contents of a register
 * Input:    two integers: a register index and the value to be stored
 * Output:   nothing
 */
	public void setRegValue( int index, int value )
	{
		try
		{
			String indexString = converter.decToBin(value);
			
			if( value > 65535 )
			{ 
				int difference = indexString.length() - 16;
				indexString = 
					indexString.substring(difference, indexString.length());
				
				value = converter.binToDec(indexString);
			}
			registers[index] = value;
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Register: Out Of Bounds Exception thrown." );
		}
	}

/* Function: getRegValue()
 * Purpose:  to get the contents of a register
 * Input:    a String representing the index
 * Output:   an integer representing the register contents
 */
	public int getRegValue( String index )
	{
		int result = 0;
		try
		{
			result = registers[convertRegisterCode(index)];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Register: Out Of Bounds Exception thrown." );
		}
		return result;
	}
	
/* Function: setRegValue()
 * Purpose:  to set the contents of a register
 * Input:    two strings: a register index and the value to be stored
 * Output:   nothing
 */
	public void setRegValue( String index, String value )
	{
		int decValue = converter.binToDec( value );
		
		try
		{
			if( index.length() > 16 )
			{
				int difference = index.length() - 16;
				index = index.substring(difference, index.length());
			}	
			registers[convertRegisterCode(index)] = decValue;
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			System.out.println( "Register: Out Of Bounds Exception thrown." );
		}
	}
	
/* Function: convertRegisterCode()
 * Purpose:  to convert to decimal the register number
 * Input:    a 3 bit string representing the register number
 * Output:   the decimal register value
 */
	public int convertRegisterCode( String regCode )
	{
		int result = 0;
		
		if( regCode.equals( "000" )) result = 0;
		else if( regCode.equals( "001" )) result = 1;
		else if( regCode.equals( "010" )) result = 2;
		else if( regCode.equals( "011" )) result = 3;
		else if( regCode.equals( "100" )) result = 4;
		else if( regCode.equals( "101" )) result = 5;
		else if( regCode.equals( "110" )) result = 6;
		else if( regCode.equals( "111" )) result = 7;
		
		return result;
	}

/* Function: setConditionCodes()
 * Purpose:  sets the condition codes based on an integer value
 * Input:    an integer value
 * Output:   nothing
 */
	public void setConditionCodes( int lastResult )
	{
		N = 0;
		Z = 0;
		P = 0;
		
		if( lastResult > 0 )  P = 1;
		else if( lastResult == 0 ) Z = 1;
		else if( lastResult < 0 ) N = 1;
		/*else if( lastResult >= 0 ) 
		{
			Z = 1;
			P = 1;
		}
		else if( lastResult < 0 ) N = 1;
		else if( lastResult != 0 )
		{
			N = 1;
			P = 1;
		}
		else if( lastResult <= 0 )
		{
			N = 1;
			Z = 1;
		}
		else if( lastResult < 0 || lastResult == 0 || lastResult > 0 )
		{
			N = 1;
			Z = 1;
			P = 1;
		}*/
	}
	
/* Function: printConditionCodes()
 * Purpose:  to print out the current condition codes
 * Input:    nothing
 * Output:   nothing
 */
	public void printConditionCodes()
	{
		System.out.print( "Condition Code: " + N + " " + Z + " " +  P + "\n" );
	}

/* Function: getN()
 * Purpose:  to return the N code
 * Input:    nothing
 * Output:   the integer N value
 */
	public int getN()
	{
		return N;
	}

/* Function: getZ()
 * Purpose:  to return the Z code
 * Input:    nothing
 * Output:   the integer Z value
 */
	public int getZ()
	{
		return Z;
	}

/* Function: getP()
 * Purpose:  to return the P code
 * Input:    nothing
 * Output:   the integer P value
 */
	public int getP()
	{
		return P;
	}

/* Function: printRegisterContents()
 * Purpose:  to print out the register contents
 * Input:    nothing
 * Output:   nothing
 */
	public void printRegisterContents()
	{
		System.out.println( "\nRegister Contents\n" );
		for( int i = 0; i < 8; i++ )
		{
			System.out.println( "R" + i + ": " + registers[i] + "; " +
				converter.decToBin( registers[i] ));
		}
	}
}
