/* Program Assignment: 2A
 * Name:               Thomas LePage twl9586
 * Date:               Feb 2, 2004
 * Description:        Class which scans the file and determines LOC
 */
 
import java.io.*;

public class LineScanner
{
	protected BufferedReader bufferReader;
	private int lineCount;
	protected int numOfLines,
	              methodCount,
	              methodLines;
	protected boolean isFile;
	
	public LineScanner( File file, int correctCodeCount[] )
	{
		isFile = false;
		numOfLines = 0;
		
		try
		{
			bufferReader = new BufferedReader( new FileReader(file) );
			isFile = true;
		}
		catch( FileNotFoundException e )
		{
			isFile = false;
		}
		
		lineCount = 0;
		
		if( isFile )
		{
			numOfLines = analyzeLine( bufferReader );
			try
			{
				bufferReader = new BufferedReader( new FileReader(file) );
			}
			catch( FileNotFoundException e )
			{
				isFile = false;
				return;
			}
			analyzeMethods( bufferReader, correctCodeCount );
		}
	} // end method
	
	public int analyzeLine( BufferedReader buffer )
	{
		int rt = 0; 
		boolean isLine = false;
		String newLine = null;
		
		try
		{
			while(( newLine = buffer.readLine()) != null )
			{
				if( newLine.indexOf(";") != -1 ||
					newLine.startsWith( "public" ) ||
					newLine.startsWith( "protected") ||
					newLine.startsWith( "private" ) ||
					newLine.startsWith( "{") ||
					newLine.startsWith( "}") ||
					newLine.endsWith( "}") ||
					newLine.endsWith( "{") ||
					newLine.indexOf("public") != -1 ||
					newLine.indexOf("private") != -1 ||
					newLine.indexOf("protected") != -1 ||
					newLine.indexOf("{") != -1 ||
					newLine.indexOf("}") != -1 ||
					newLine.indexOf("} // end method") != -1 
					)
					
				{
					isLine = true;
				}
				
				if( newLine.indexOf("} // end method") == -1 )
				{
					if( newLine.indexOf( "//") != -1 ||
						newLine.indexOf( "/*") != -1 ||
						newLine.indexOf( "*/") != -1 
					   )
					{
						isLine = false;
					}
				}
				if( isLine )
				{	
					lineCount++;
					isLine = false;
				}
				
			}
		}
		catch( IOException e )
		{
			System.out.println( "IOException found in analyzeLine()." );
		}
		
		rt = lineCount;
			
		return rt;
	} // end method
	
	public boolean analyzeMethods( BufferedReader buffer, int correctMethodLines[] )
	{
		boolean rt = false,
				isMethod = false;
		String newLine = null; 
		boolean signifyMethodEnd = false;
		
		try
		{
			while(( newLine = buffer.readLine()) != null )
			{
				
				if(( newLine.indexOf( "public" ) != -1 ||
				     newLine.indexOf( "private") != -1 ||
				     newLine.indexOf( "protected") != -1) && 
				   ( newLine.indexOf( "(") != -1 ) &&
				   ( newLine.indexOf( "class" ) == -1 ) &&
				   ( newLine.indexOf( ".") == -1) )
				{
					methodCount++;
					isMethod = true;
				}
				
				if( (isMethod &&
				    ( newLine.indexOf(";") != -1 ||
					  newLine.startsWith( "public" ) ||
					  newLine.startsWith( "protected") ||
					  newLine.startsWith( "private" ) ||
					  newLine.startsWith( "{") ||
					  newLine.startsWith( "}") ||
					  newLine.endsWith( "}") ||
					  newLine.endsWith( "{") ||
					  newLine.indexOf("public") != -1 ||
					  newLine.indexOf("private") != -1 ||
					  newLine.indexOf("protected") != -1 ||
					  newLine.indexOf("{") != -1 ||
					  newLine.indexOf("}") != -1 ))) 
				{
					methodLines++;
				}
				if( isMethod && newLine.indexOf( "} // end method") != -1 )
				{
					System.out.println( "Method " + methodCount + " lines: " +
						+ methodLines );
					System.out.println( "Method " + methodCount + 
						" Line Of Code Test-> Pass: " +
						(methodLines == correctMethodLines[methodCount-1]));
					methodLines = 0;
					isMethod = false;
					signifyMethodEnd = true;
				}
			}
		}
		catch( IOException e )
		{
			System.out.println( "IOException found." );
		}
		
		return signifyMethodEnd;
	} // end method
} 
