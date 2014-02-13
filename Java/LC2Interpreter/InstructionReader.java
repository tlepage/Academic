/* Name:    Thomas LePage twl9586
 * Class:   InstructionReader.java
 * Purpose: Loads in binary codes from file
 */
import java.io.*;
import java.util.Vector;

public class InstructionReader
{
	private BufferedReader fileBuffer;
	private Vector instrStack;
	private int instrCount;
	private String nextInstruction;
	
/* Function: InstructionReader()
 * Purpose:  takes a file and extracts the binary code
 * Input:    a file containing the binary code
 * Output:   nothing
 */
	public InstructionReader( File file )
	{	
		instrCount = 0;
		instrStack = new Vector();
		
		int count = 0;
		
		try
		{
			fileBuffer = new BufferedReader( new FileReader(file) );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "Not a file." );
		}
		
		try
		{
			// stores the strings into a vector
			while( (nextInstruction = fileBuffer.readLine()) != null )
			{
				instrStack.add( count, nextInstruction );
				count++;
			}
			
			instrCount = count;
		}
		catch( IOException e )
		{
			System.out.println( "IOException in InstructionReader." );
		}
	}

/* Function: getBuffer()
 * Purpose:  returns the file buffer
 * Input:    nothing
 * Output:   a BufferedReader containing the file
 */
	public BufferedReader getBuffer()
	{
		return fileBuffer;
	}

/* Function: getInstrCount()
 * Purpose:  return the current instruction count
 * Input:    nothing
 * Output:   an integer representing the instruction count
 */
	public int getInstrCount()
	{
		return instrCount;
	}
	
/* Function: getStack()
 * Purpose:  returns the vector which contains the instructions
 * Input:    nothing
 * Output:   a Vector containing the instructions
 */
	public Vector getStack()
	{
		return instrStack;
	}
}
