/* Program Assignment: 2A
 * Name:               Thomas LePage twl9586
 * Date:               Feb 2, 2004
 * Description:        Assignment 2 Tests LOC counter, LineScanner.java
 */

import java.io.*;

public class Test2A
{
	public static void main( String args[] )
	{
		File files[] = new File[10];
		int lines[] = new int[10];
		
		// add new files if you wish to test
		files[0] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test1A.java");
		files[1] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test2A.java");
		files[2] = new File( "C:/eclipse/eclipse/workspace/cse4310/List.java");
		files[3] = new File( "C:/eclipse/eclipse/workspace/cse4310/LineScanner.java");
		
		// non-existing file
		files[4] = new File( "C:/eclipse/eclipse/workspace/cse4310/nothing.txt" );
		
		// empty file
		files[5] = new File( "C:/eclipse/eclipse/workspace/cse4310/empty.txt" );
		
		// create expected test results
		lines[0] = 99;
		lines[1] = 34;
		lines[2] = 55;
		lines[3] = 60; 
		lines[4] = 0;
		lines[5] = 0;
		
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 2" +			"\nProblem 2A" );
		// create LineScanner objects
		/*
		LineScanner try1A = new LineScanner( files[0] );
		System.out.println( "\nTest for Test1A.java:\nNumber of Lines: " 
			+ try1A.numOfLines + "\nPass(is File): " + try1A.isFile 
			+ "\nPass(correct line count): " + (try1A.numOfLines == lines[0]));
		LineScanner try2A = new LineScanner( files[1] );
		System.out.println( "\nTest for Test2A.java:\nNumber of Lines: "
			+ try2A.numOfLines + "\nPass(is File): " + try2A.isFile
			+ "\nPass(correct line count): " + (try2A.numOfLines == lines[1]));
		LineScanner tryList = new LineScanner( files[2] );
		System.out.println( "\nTest for List.java:\nNumber of Lines: "
			+ tryList.numOfLines + "\nPass(is File): " + tryList.isFile 
			+ "\nPass(correct line count): " + (tryList.numOfLines == lines[2]));
		LineScanner tryLine = new LineScanner( files[3] );
		System.out.println( "\nTest for LineScanner.java:\nNumber of Lines: "
			+ tryLine.numOfLines + "\nPass(is File): " + tryLine.isFile 
			+ "\nPass(correct line count): " + (tryLine.numOfLines == lines[3]));
		LineScanner tryNonExist = new LineScanner( files[4] );
		System.out.println( "\nTest for non-existing file:\nNumber of Lines: "
			+ tryNonExist.numOfLines + "\nPass(is File): " + tryNonExist.isFile
			+ "\nPass(correct line count): " + (tryNonExist.numOfLines == lines[4]));
		LineScanner tryEmpty = new LineScanner( files[5] );
		System.out.println( "\nTest for empty file:\nNumber of Lines: "
			+ tryEmpty.numOfLines + "\nPass(is File): " + tryEmpty.isFile 
			+ "\nPass(correct line count): " + (tryEmpty.numOfLines == lines[5]));	
	*/
	} // end method
}
