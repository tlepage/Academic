/* Program Assignment: 5A
 * Name:               Thomas LePage twl9586
 * Date:               Feb 27, 2004; updated Mar 3, 2004
 * Description:        Assignment 5 Test class for integrateNormal() and
 * 					   integrateTDistribution()	
 */
import java.util.LinkedList;
import java.text.*;

public class Test5A
{
	private double xLow, xHigh, E, oldResult, result;
	private int N, n;
	protected LinkedList termList, fXList;
	private DecimalFormat fourDigits;
	
	public Test5A()
	{
		xLow = 0.0;
		xHigh = 0.0;
		E = 0.001;
		oldResult = 0.0;
		result = 0.0;
		N = 0;
		n = 0;
		termList = new LinkedList();
		fXList = new LinkedList();
		fourDigits = new DecimalFormat("0.0000");
	}
	
	public void integrateNormal( int numSegments, double xHi, double xLo )
	{
		N = numSegments;
		xLow = xLo;
		xHigh = xHi; // xHi is Q for test9a
		
		oldResult = result;
		
		double eX2 = 0.0,
			   fXi = 0.0,
			   xi = 0.0,
			   term = 0.0,
			   sum = 0.0;
			   
		double W = ( xHigh - xLow ) / N;
		
		for( int t = 0; t < N+1; t++ )
		{
			xi = xLow + (t * W );
			eX2 = ( Math.pow(Math.E, (-(xi * xi) / 2 )));
			fXi = 0.39894 * eX2;
			
			if( t == 0 || t == N )
			{
				term = fXi * (W / 3);
			}
			else if( t % 2 != 0 )
			{
				term = 4 * fXi * (W / 3);
			}
			else 
				term = 2 * fXi * (W / 3);
			
			Double temp = new Double( term );
			termList.add( t, temp );
			sum += term;
		}
	
		result = 0.5 - sum;
		
		// test if result is within error range, E
		/*if( !(Math.abs( result - oldResult ) <= E ))
		{	
			integrateNormal( N * 2, xHigh, xLow);
		}*/
	}
	
	public double integrateChiSquaredDistribution( double xHi, int numSegments )
	{
		N = numSegments;
		xLow = 0;
		xHigh = xHi; // xHi is Q for test9a
		
		final double CONS = 0.003799;
		
		double eX2 = 0.0,
			   fXi = 0.0,
			   xi = 0.0,
			   term = 0.0,
			   sum = 0.0,
			   n = 9;
	   
		double W = ( xHigh - xLow ) / N;

		for( int t = 0; t < N+1; t++ )
		{
			xi = xLow + (t * W );
			double xiN = Math.pow( xi, (n/2) - 1 );
			eX2 = Math.pow( Math.E, (-xi / 2 ));
			fXi = CONS * xiN * eX2;
			
			if( t == 0 || t == N )
			{
				term = fXi * (W / 3);
			}
			else if( t % 2 != 0 )
			{
				term = 4 * fXi * (W / 3);
			}
			else 
				term = 2 * fXi * (W / 3);
	
			sum += term;
		}
		
		return sum;
	}
	
	public double integrateTDistribution( double xHi, int N, int n )
	{
		double result = 0.0, temp = 0.0, fXi, sum = 0.0;
		final double gammaConstant = .388033,
					 newGamma = 1;
		Double tempDouble;
		double W = xHi / N;
		
		for( int i = 0; i < N+1; i++ )
		{
			tempDouble = new Double( i * W );
			termList.add(i, tempDouble );
		}
		
		for( int i = 0; i < N+1; i++ )
		{
			fXi = Math.pow( 1 + ( (Math.pow(getTermListIndex(i), 2)) / n), 
				-(n+1)/2);
		
			if( i == 0 || i == N )
			{
				temp = fXi * (W / 3);
			}
			else if( i % 2 != 0 )
			{
				temp = 4 * fXi * (W / 3);
			}
			else 
				temp = 2 * fXi * (W / 3); 
			
			temp *= newGamma;
				
			tempDouble = new Double(temp);
			fXList.add( i, tempDouble );
			
		}
		
		for( int i = 0; i < N+1; i++ )
		{
			tempDouble = (Double)fXList.get(i);
			sum += tempDouble.doubleValue();
		}
		
		result = sum + 0.5;
		return result;
	}

	public String getResult()
	{
		return fourDigits.format(result);
	}
	
	public boolean withinEpsilon( double expectedResult )
	{
		return ( Math.abs(result - expectedResult ) <= E );
	}
	
	public double getTermListIndex( int index)
	{
		Double tempDouble = (Double)termList.get(index);
		
		return tempDouble.doubleValue();
	}
	
	public static void main( String args[] )
	{
		Test5A app = new Test5A();
		System.out.println(app.integrateChiSquaredDistribution( 34.4, 50 ));
		/*
		int N = 20;
		double	xLo = 0,
			    xHigh = 0;
		double expectedValues[] = new double[5];
		expectedValues[0] = .9938;  // test x =  2.5
		expectedValues[1] = .5793;  // test x =  0.2
		expectedValues[2] = .1357;  // test x = -1.1
		expectedValues[3] = 0.0000; // test x = -4.0
		expectedValues[4] = 1.0000; // test x =  4.0
		
		System.out.println( "Thomas LePage\nCSE 4316\nAssignment 5\n" +
			"Problem 5A\n");
		
		/**
		 * Test5A: double E = 0.001 is the amount of error that I am willing
		 * to tolerate when comparing double numbers when comparing doubles. 
		 */
		
		/** Tests of Test5A.integrateNormal() */

		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies integrateNormal correctly 
		 * 		evaluates the test value of x = 2.5
		 * Test Description: The result is stored in Test5A.result.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: expectedValues[0]
		 * Expected Result: integrateNormal([]) == 0.9938
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateNormal([])? true" represents a passed test.
		 */
		/*xHigh = -2.5;
		
		app.integrateNormal(N, xHigh, xLo );
		System.out.println( "Testing integrateNormal(x <- 2.5) = 0.9938" +			"\nPass integrateNormal(x = 2.5)? " + 
			app.withinEpsilon(expectedValues[0]));
		
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies integrateNormal correctly 
		 * 		evaluates the test value of x = 0.2
		 * Test Description: The result is stored in Test5A.result.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: expectedValues[1]
		 * Expected Result: integrateNormal([]) == 0.5739
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateNormal([])? true" represents a passed test.
		 */
		/*xHigh = -0.2;
		
		app.integrateNormal(N, xHigh, xLo );
		System.out.println( "\nTesting integrateNormal(x <- 0.2) = 0.5793" +
			"\nPass integrateNormal(x = 0.2)? " + 
			app.withinEpsilon(expectedValues[1]));
		
		/** 
		 * Test Number: 2
		 * Test Objective: This test verifies integrateNormal correctly 
		 * 		evaluates the test value of x = -1.1
		 * Test Description: The result is stored in Test5A.result.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: expectedValues[2]
		 * Expected Result: integrateNormal([]) == 0.1357
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateNormal([])? true" represents a passed test.
		 */
		/*xHigh = 1.1;
		
		app.integrateNormal(N, xHigh, xLo );
		System.out.println( "\nTesting integrateNormal(x <- -1.1) = 0.1357" +
			"\nPass integrateNormal(x = -1.1)? " + 
			app.withinEpsilon(expectedValues[2]));
		
		/** 
		 * Test Number: 3
		 * Test Objective: This test verifies integrateNormal correctly 
		 * 		evaluates the test value of x = -4.0
		 * Test Description: The result is stored in Test5A.result.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: expectedValues[3]
		 * Expected Result: integrateNormal([]) == 0.0000
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateNormal([])? true" represents a passed test.
		 */
		/*xHigh = 4.0;
		
		app.integrateNormal(N, xHigh, xLo );
		System.out.println( "\nTesting integrateNormal(x <- -4.0) = 0.0000" +
			"\nPass integrateNormal(x = -4.0)? " + 
			app.withinEpsilon(expectedValues[3]));
		
		/** 
		 * Test Number: 4
		 * Test Objective: This test verifies integrateNormal correctly 
		 * 		evaluates the test value of x = 4.0
		 * Test Description: The result is stored in Test5A.result.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: expectedValues[4]
		 * Expected Result: integrateNormal([]) == 1.0000
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateNormal([])? true" represents a passed test.
		 */
		/*xHigh = -4.0;
		
		app.integrateNormal(N, xHigh, xLo );
		System.out.println( "\nTesting integrateNormal(x <- 4.0) = 1.0000" +
			"\nPass integrateNormal(x = 4.0)? " + 
			app.withinEpsilon(expectedValues[4]));
		*/
		System.exit(0);
	}
}
