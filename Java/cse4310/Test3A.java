/* Program Assignment: 3A
 * Name:               Thomas LePage twl9586
 * Date:               Feb 10, 2004
 * Description:        Test driver for Problem 3A -- tests method count
 *                     and LOC
 */

import java.io.*;

public class Test3A
{
	public static void main( String args[] )
	{
		File files[] = new File[10];
		int lines[] = new int[10];
		int methods[] = new int[10];
		int test1ALines[] = new int[10];
		int test2ALines[] = new int[10];
		int test3ALines[] = new int[10];
		int testListLines[] = new int[10];
		int testLineScannerLines[] = new int[10];
		int testNothingLines[] = new int[10];
		int testEmptyLines[] = new int[10];

		// add new files if you wish to test
		files[0] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test1A.java");
		files[1] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test2A.java");
		files[2] = new File( "C:/eclipse/eclipse/workspace/cse4310/StandardDeviation.java");
		files[3] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test5A.java");
		files[4] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test8A.java" );
		
		// non-existing file
		files[5] = new File( "C:/eclipse/eclipse/workspace/cse4310/Test9A.java" );

		// empty file
		files[6] = new File( "C:/eclipse/eclipse/workspace/cse4310/empty.txt" );

		// create expected test results
		lines[0] = 99;
		lines[1] = 34;
		lines[2] = 55;
		lines[3] = 120; 
		lines[4] = 71;
		lines[5] = 0;
		lines[6] = 0;
		
		methods[0] = 3;
		methods[1] = 1;
		methods[2] = 3;
		methods[3] = 3;
		methods[4] = 1;
		methods[5] = 0;
		methods[6] = 0;
		
		test1ALines[0] = 53;
		test1ALines[1] = 8;
		test1ALines[2] = 28;
		
		test2ALines[0] = 30;
		
		test3ALines[0] = 67;
		
		testListLines[0] = 6;
		testListLines[1] = 18;
		testListLines[2] = 25;
		
		testLineScannerLines[0] = 24;
		testLineScannerLines[1] = 21;
		testLineScannerLines[2] = 31;
		
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 3" +
			"\nProblem 3A" );
		
		// create LineScanner objects
		
		System.out.println( "\nTest for Test1A.java:\n");
		
		LineScanner try1A = new LineScanner( files[0], test1ALines );
		
		System.out.println( "Total Methods: " + try1A.methodCount 
			+ "\nPass(correct method count): " + (try1A.methodCount == methods[0])
			+ "\nTotal program LOC: " + try1A.numOfLines 
			+ "\nPass(correct total LOC): " + (try1A.numOfLines == lines[0] ) 
			+ "\nPass(is File): " + try1A.isFile ); 
		
		System.out.println( "\nTest for Test2A.java:\n");
		
		LineScanner try2A = new LineScanner( files[1], test2ALines );

		System.out.println( "Total Methods: " + try2A.methodCount 
			+ "\nPass(correct method count): " + (try2A.methodCount == methods[1])
			+ "\nTotal program LOC: " + try2A.numOfLines 
			+ "\nPass(correct total LOC): " + (try2A.numOfLines == lines[1] ) 
			+ "\nPass(is File): " + try2A.isFile );
			
		System.out.println( "\nTest for Test3A.java:\n");
		
		LineScanner try3A = new LineScanner( files[4], test3ALines );

		System.out.println( "Total Methods: " + try3A.methodCount 
			+ "\nPass(correct method count): " + (try3A.methodCount == methods[4])
			+ "\nTotal program LOC: " + try3A.numOfLines 
			+ "\nPass(correct total LOC): " + (try3A.numOfLines == lines[4] ) 
			+ "\nPass(is File): " + try3A.isFile );
			
		System.out.println( "\nTest for List.java:\n");
		
		LineScanner tryList = new LineScanner( files[2], testListLines );

		System.out.println( "Total Methods: " + tryList.methodCount 
			+ "\nPass(correct method count): " + (tryList.methodCount == methods[2])
			+ "\nTotal program LOC: " + tryList.numOfLines 
			+ "\nPass(correct total LOC): " + (tryList.numOfLines == lines[2] ) 
			+ "\nPass(is File): " + tryList.isFile );
			
		System.out.println( "\nTest for LineScanner.java:\n");
		
		LineScanner tryLineScanner = new LineScanner( files[3], testLineScannerLines );

		System.out.println( "Total Methods: " + tryLineScanner.methodCount 
			+ "\nPass(correct method count): " + (tryLineScanner.methodCount == methods[3])
			+ "\nTotal program LOC: " + tryLineScanner.numOfLines 
			+ "\nPass(correct total LOC): " + (tryLineScanner.numOfLines == lines[3] ) 
			+ "\nPass(is File): " + tryLineScanner.isFile );
			
		System.out.println( "\nTest for nothing.txt:\n");
		
		LineScanner tryNothing = new LineScanner( files[5], testNothingLines );

		System.out.println( "Total Methods: " + tryNothing.methodCount 
			+ "\nPass(correct method count): " + (tryNothing.methodCount == methods[5])
			+ "\nTotal program LOC: " + tryNothing.numOfLines 
			+ "\nPass(correct total LOC): " + (tryNothing.numOfLines == lines[5] ) 
			+ "\nPass(is File): " + tryNothing.isFile );
			
		System.out.println( "\nTest for empty.txt:\n");
		
		LineScanner tryEmpty = new LineScanner( files[6], testEmptyLines );

		System.out.println( "Total Methods: " + tryEmpty.methodCount 
			+ "\nPass(correct method count): " + (tryEmpty.methodCount == methods[6])
			+ "\nTotal program LOC: " + tryEmpty.numOfLines 
			+ "\nPass(correct total LOC): " + (tryEmpty.numOfLines == lines[6] ) 
			+ "\nPass(is File): " + tryEmpty.isFile );
		} // end method
}
