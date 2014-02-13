/* Program Assignment: 6A
 * Name:               Thomas LePage twl9586
 * Date:               Mar 3, 2004
 * Description:        Assignment 6 Test class 
 */
import java.util.LinkedList;

public class Test6A
{
	protected double variance,
	                 stdDev,
	                 tDist,
	                 range,
	                 LPI,
	                 UPI,
	                 xAvg,
	                 xHi;
	
	protected LinkedList xList,
						 yList;
	
	protected Test5A integrator;
	
	                 
	public Test6A()
	{
		integrator = new Test5A();
		xList = new LinkedList();
		yList = new LinkedList();
	}
	
	public double getXValue( int index )
	{
		double result;
		String temp = xList.get(index).toString();
		
		return Double.parseDouble(temp);
	}
	
	public double getYValue( int index )
	{
		double result;
		String temp = yList.get(index).toString();
		
		return Double.parseDouble(temp);
	}
	
	public double calcVariance(int n, double betaZero, double betaOne )
	{
		double result = 0.0,
			   tempSum = 0.0;
		
		for( int i = 0; i < yList.size(); i++ )
		{
			tempSum += Math.pow(
				getYValue(i) - betaZero - (betaOne * getXValue(i)), 2);
			//System.out.println( i + ": " + 
			//	Math.pow(getYValue(i) - betaZero - (betaOne * getXValue(i)), 2));
		}
		//System.out.println( tempSum );
		result = (tempSum / (n - 2));
		variance = result;
		
		return result; 
	}
	
	public double calcStdDev()
	{
		stdDev = Math.sqrt(variance);
		
		return stdDev;
	}
	
	public double calcRange(double xHi, int n, double fraction, double xK)
	{
		double sum = 0.0;
		
		for( int i = 0; i < xList.size(); i++ )
		{
			xAvg += getXValue(i);
		}
		
		xAvg /= n;
		//System.out.println( "Xavg:" + xAvg );
		for( int i = 0; i < xList.size(); i++ )
		{
			sum += Math.pow( (getXValue(i) - xAvg), 2 );
		}
		
		double temp = Math.pow(( xK - xAvg), 2);
		double subTemp = 1 + fraction + ( temp / sum );
		
		
		range = xHi * stdDev * Math.sqrt(subTemp);
			
		return range;
	}
	
	public double calcTDist( double xHi, int N, int n )
	{
		double result = 0.0;
		
		// call 5A function
		result = integrator.integrateTDistribution(xHi, N, n);
		tDist = result;
		return result;
	}
	
	public double calcLPI( double yK )
	{
		LPI = yK - range;
		return ( yK - range );
	}
	
	public double calcUPI( double yK )
	{
		UPI = yK + range;
		return( yK + range );
	}
	
	public void loadLists( double arrayXList[], double arrayYList[] )
	{
		Double tempDouble;
		
		for( int i = 0; i < arrayXList.length; i++ )
		{
			tempDouble = new Double( arrayXList[i] );
			xList.add(i, tempDouble );
		}
		
		for( int i = 0; i < arrayYList.length; i++ )
		{
			tempDouble = new Double( arrayYList[i] );
			yList.add( i, tempDouble );
		}
	}
	
	public void clearLists()
	{
		xList.clear();
		yList.clear();
	}
	public boolean withinEpsilon( double exp, double act )
	{
		final double E = 0.1;
		boolean result = false;
		
		if( Math.abs( exp - act ) <= E )
		{
			result = true;
		}
		else result = false;
		
		return result;
	}
	
	public static void main( String args[] )
	{
		Test6A app = new Test6A();
		
		double xElementsT1[] = { 130, 650, 99, 150, 128, 302, 95, 945, 368,
			961 };
		double yElementsT1[] = { 186, 699, 132, 272, 291, 331, 199, 1890, 788,
			1601 };
		
		double xElementsT2[] = { 10, 80, 50, 50, 20 };
		double yElementsT2[] = { 0, 94, 37, 31, 165 };
		
		double expectedResults[] = new double[20];
		
		app.loadLists( xElementsT1, yElementsT1 );
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 6\nProblem 6A\n");
		
		/**
		 * Test6A: double E = 0.1 is the amount of error that I am willing
		 * to tolerate when comparing double numbers. 
		 */

		/** Tests of Test5A.integrateTDistribution() */

		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies integrateTDistribution correctly 
		 * 		evaluates the test value of x = 1.108
		 * Test Description: The result is stored in Test6A.tDist.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of .85
		 * Expected Result: integrateTDist([]) == 0.85
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateTDist([])? true" represents a passed test.
		 */
		System.out.println( "integrateTDist(1.1) = " + 
			app.calcTDist(1.1, 20, 9));
		System.out.println( "Pass integrateTDist(1.1) = .85?" +
			app.withinEpsilon(.85, app.tDist ));
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies integrateTDistribution correctly 
		 * 		evaluates the test value of x = 1.86
		 * Test Description: The result is stored in Test6A.tDist.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of .95
		 * Expected Result: integrateTDist([]) == 0.95
		 * Actual Results: See the Console Output window: 
		 * 		"Pass integrateTDist([])? true" represents a passed test.
		 */
		System.out.println( "integrateTDist(1.86) = " + 
			app.calcTDist(1.86, 40, 9));
		System.out.println( "Pass integrateTDist(1.1) = .95?" +
			app.withinEpsilon(.95, app.tDist ));
			
		/** Tests of Test6A.calcRange(), calcUPI(), and calcLPI() */
		
		/** 
		 * Test Number: 2
		 * Test Objective: This test verifies that the range function returns
		 * 		the same value as in the book when x is 1.1.
		 * Test Description: The result is stored in Test6A.range.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: test values of ( Test6A.range, Test6A.LPI, Test6A.UPI )
		 * Expected Result: calcRange([]) == 229.9715
		 * 					calcUPI([]) == 874.401
		 * 					calcLPI([]) == 414.4579
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcRange([])? true" represents a passed test.
		 * 		"Pass calcUPI([])? true" represents a passed test.
		 * 		"Pass calcLPI([])? true" represents a passed test.
		 */	
		app.calcVariance(10, -22.55, 1.7279);
		app.calcStdDev();
		System.out.println( "\ncalcRange(70%) = " + 
			app.calcRange(1.108, 10, .1, 386 ));
		System.out.println( "Pass calcRange([])? " + 
			app.withinEpsilon( 229.9715, app.range));
		System.out.println( "calcUPI(70%) = " + app.calcUPI(644.429));
		System.out.println( "Pass calcUPI(70%)? " + 
			app.withinEpsilon(874.401, app.UPI ));
		System.out.println( "calcLPI(70%) = " + app.calcLPI(644.429));
		System.out.println( "Pass calcLPI(70%)? " + 
			app.withinEpsilon(414.4579, app.LPI ));
		
		/** 
		 * Test Number: 3
		 * Test Objective: This test verifies that the range function returns
		 * 		the same value as in the book when x is 1.86.
		 * Test Description: The result is stored in Test6A.range.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: test values of ( Test6A.range, Test6A.UPI, Test6A.LPI )
		 * Expected Result: calcRange([]) == 386
		 * 					calcUPI([]) == 1030
		 * 					calcLPI([]) == 258
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcRange([])? true" represents a passed test.
		 * 		"Pass calcUPI([])? true" represents a passed test.
		 * 		"Pass calcLPI([])? true" represents a passed test.
		 */	
		System.out.println( "\ncalcRange(90%) = " + 
			app.calcRange(1.86, 10, .1, 386 ));
		System.out.println( "Pass calcRange(90%)? " + 
			app.withinEpsilon( 386.2, app.range));
		System.out.println( "calcUPI(90%) = " + app.calcUPI(644.429));
		System.out.println( "Pass calcUPI(90%)? " + 
			app.withinEpsilon(1030.6, app.UPI ));
		System.out.println( "calcLPI(90%) = " + app.calcLPI(644.429));
		System.out.println( "Pass calcLPI(90%)? " + 
			app.withinEpsilon(258.1, app.LPI ));
		
		app.clearLists();
		app.loadLists( xElementsT2, yElementsT2 );
		
		/** 
		 * Test Number: 4
		 * Test Objective: This test verifies that the range function returns
		 * 		the same value as I calculated where x = 1.1.
		 * Test Description: The result is stored in Test6A.range.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: test values of ( Test6A.range, Test6A.UPI, Test6A.LPI )
		 * Expected Result: calcRange([]) == 120.23
		 * 					calcUPI([]) == 168.3
		 * 					calcLPI([]) == -72.17
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcRange([])? true" represents a passed test.
		 * 		"Pass calcUPI([])? true" represents a passed test.
		 * 		"Pass calcLPI([])? true" represents a passed test.
		 */	
		app.calcVariance(5, -21.52, 1.3024);
		app.calcStdDev();
		System.out.println("\nTests for my data\nBeta Zero: -21.52\nBeta One: 1.3024" +			"\nEstimated Object LOC: 50");
			
		System.out.println( "\ncalcRange(70%) = " + 
			app.calcRange(1.108, 5, .2, 50 ));
		System.out.println( "Pass calcRange(70%)? " + 
			app.withinEpsilon( 120.23, app.range));
		System.out.println( "calcUPI(70%) = " + app.calcUPI(48.065));
		System.out.println( "Pass calcUPI(70%)? " + 
			app.withinEpsilon(168.3, app.UPI ));
		System.out.println( "calcLPI(70%) = " + app.calcLPI(48.065));
		System.out.println( "Pass calcLPI(70%)? " + 
			app.withinEpsilon(-72.17, app.LPI ));
		
		/** 
		 * Test Number: 5
		 * Test Objective: This test verifies that the range function returns
		 * 		the same value as I calculated where x = 1.86.
		 * Test Description: The result is stored in Test6A.range.  This will
		 * 		be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: test values of ( Test6A.range, Test6A.UPI, Test6A.LPI )
		 * Expected Result: calcRange([]) == 193.96
		 * 					calcUPI([]) == 242.03
		 * 					calcLPI([]) == -145.90
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcRange([])? true" represents a passed test.
		 * 		"Pass calcUPI([])? true" represents a passed test.
		 * 		"Pass calcLPI([])? true" represents a passed test.
		 */	
		
		System.out.println( "\ncalcRange(90%) = " + 
			app.calcRange(1.86, 5, .2, 50 ));
		System.out.println( "Pass calcRange(90%)? " + 
			app.withinEpsilon( 193.96, app.range));
		System.out.println( "calcUPI(90%) = " + app.calcUPI(48.065));
		System.out.println( "Pass calcUPI(90%)? " + 
			app.withinEpsilon(242.03, app.UPI ));
		System.out.println( "calcLPI(90%) = " + app.calcLPI(48.065));
		System.out.println( "Pass calcLPI(90%)? " + 
			app.withinEpsilon(-145.90, app.LPI ));
		
		System.out.println( "\nActual Object LOC: " + 48.065 );
		System.exit(0);
	}
}
