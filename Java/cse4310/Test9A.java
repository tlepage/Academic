/* Program Assignment: 9A
 * Name:               Thomas LePage twl9586
 * Date:               April 9, 2004
 * Description:        Assignment 9 Test class 
 */
import java.util.LinkedList;
import java.text.*;

public class Test9A
{
	protected double average,
					 stdDev,
					 Q,
					 p,
					 E,
					 dataArray[],
					 normArray[],
					 fracArray[];
	protected int k[];
	protected StandardDeviation stdDevObj;
	protected Test8A listObj;
	protected Test5A integrator;
	protected LinkedList dataList;
	protected DecimalFormat fourDigits;
	
	public Test9A()
	{
		average = 0.0;
		stdDev = 0.0;
		Q = 0.0;
		stdDevObj = new StandardDeviation();
		listObj = new Test8A();
		integrator = new Test5A();
		E = .01;
		dataList = new LinkedList();
		fourDigits = new DecimalFormat( "0.0000" );
	}
	
	public void calcNormValue()
	{
		normArray = new double[dataArray.length];
		fracArray = new double[dataArray.length];
		
		for( int i = 0; i < dataArray.length; i++ )
		{
			normArray[i] = (( dataArray[i] - average) / stdDev );
		}
	}
	
	public void calcAverage()
	{
		average = stdDevObj.calcAvg( dataList );
		System.out.println( "Average: " + fourDigits.format(average) );
	}
	
	public void calcStdDeviation()
	{
		stdDev = stdDevObj.calcStdDeviation( dataList, average );
		System.out.println( "Standard Deviation: " + 
			fourDigits.format(stdDev) );
	}
	
	public void generateKTable()
	{
		// acquired these values from Humphrey textbook
		int temp[] = { 0, 7, 15, 7, 2, 3, 3, 2, 3, 8 };
		k = temp;
	}
	
	public void generateFracArray()
	{
		for( int i = 0; i < dataArray.length; i++ )
		{
			fracArray[i] = 1 / (i+1);
		}
	}
	public void loadDataArray( double array[] )
	{
		dataArray = array;
	}
	
	public void calcQ()
	{
		double temp = 0.0;
		// assuming value of N = 5
		for( int i = 0; i < 10; i++ )
		{
			temp += ( Math.pow(( 5 - k[i] ), 2) / 5);
		}
		
		Q = temp;
	}
	
	public double calcChiPValue()
	{
		p = integrator.integrateChiSquaredDistribution( Q, 50 );
		return p;
	}
	
	public void printTableA22()
	{
		System.out.println( "\nTable A22\n" );
		listObj.sortByY();
		listObj.printList();
	}
	
	public void loadIntoList()
	{
		listObj.loadList( dataArray, normArray, fracArray );
	}
	
	public boolean withinEpsilon( double expectedResult, double actual )
	{
		return ( Math.abs(actual - expectedResult ) <= E );
	}
	
	public void loadDataList()
	{
		Double tempDouble;
		
		for( int i = 0; i < dataArray.length; i++ )
		{
			tempDouble = new Double( dataArray[i] );
			dataList.add( i, tempDouble );
		}
	}
	public static void main( String args[] )
	{
		Test9A app = new Test9A();
		
		double y[] = { 8.67, 7.8, 8.67, 7.57, 5.67, 8.67, 7.33, 8.83, 10.33, 6, 
			8.33, 7.5, 14, 9, 10, 39, 38, 21.75, 29, 8.33, 13.25, 
			11.75, 5.8, 7, 14.67, 16.4, 5.75, 13.75, 10.67, 6, 29.67, 
			6, 12, 40.25, 37.33, 22.25, 28.33, 20.5, 16.4, 7.67, 
			36.8, 24.17, 19.2, 41, 30.14, 50.63, 14.5, 52.8, 19.33, 
			25.42 };
		
		System.out.println( 
			"Thomas LePage\nCSE 4310\nAssignment 9\nProblem 9A\n");
		app.loadDataArray(y);
		app.loadDataList();
		app.calcAverage();
		app.calcStdDeviation();
		app.calcNormValue();
		app.loadIntoList();
		app.printTableA22();
		app.generateKTable();
		app.calcQ();
		
		/** Tests of Test9A */
		
		/** There is two tests in this test driver.  They are used to test whether
		 *  the program generates the same Q value and significance as is found
		 *  in the Humphrey textbook, Table D15.
		 */

		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies that the Q value generated by 
		 * 		Test9A is the same as that of the Humphrey textbook, Table D15.
		 * Test Description: The result is stored in Test9A.Q.  
		 * 		The result is tested using Test9A.withinEpsilon(), to check
		 * 		whether my result is within .01 of the expected result.  This
		 * 		standard is used according to the Humphrey textbook.  
		 * Test Conditions: testing that Q = 34.4 
		 * Expected Result: Pass test for significance value? = true
		 * Actual Results: See the Console Output window: 
		 * 		"Pass test for Q value? true" represents a passed test.
		 */
		// Table D15
		System.out.println( "Table D15\n" );
		System.out.println( "Test\tExp Result\t\tAct Result" );
		System.out.println( "Q\t\t34.4\t\t\t" + app.Q );
		System.out.println( "1-p\t\t0.000076\t\t" + 
			app.fourDigits.format((1 - app.calcChiPValue())));
		System.out.println( "\nPass test for Q value? " + 
			app.withinEpsilon( 34.4, app.Q ));
		
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies that the 1-p value generated by 
		 * 		Test9A is the same as that of the Humphrey textbook, Table D15.
		 * Test Description: The result is stored in Test9A.p.  
		 * 		The result is tested using Test9A.withinEpsilon(), to check
		 * 		whether my result is within .01 of the expected result.  This
		 * 		standard is used according to the Humphrey textbook.  
		 * Test Conditions: testing that 1-p = ( .000076 +/- .01 )
		 * Expected Result: Pass test for significance value? = true 
		 * Actual Results: See the Console Output window: 
		 * 		"Pass test for significance value? true" represents a passed test.
		 */
		System.out.println( "Pass test for significance value? " +
			app.withinEpsilon( .000076, ( 1 - app.p ) ));
	}
}
