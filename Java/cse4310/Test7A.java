/* Program Assignment: 7A
 * Name:               Thomas LePage twl9586
 * Date:               March 25, 2004
 * Description:        Assignment 7 Test class 
 */
import java.util.LinkedList;

public class Test7A
{
	protected LinkedList xList, 
						 yList;
	protected Test6A integrator;
	protected double r, r2, t, p, significance;
	protected int n;
	protected final double E = 0.01;
	
	public Test7A()
	{
		xList = new LinkedList();
		yList = new LinkedList();
		integrator = new Test6A();
		r = 0.0; 
		r2 = 0.0;
		t = 0.0;
		p = 0.0;
		significance = 0.0;
	}
	
	public void loadLists( double x[], double y[] )
	{
		Double tempDouble;

		for( int i = 0; i < x.length; i++ )
		{
			tempDouble = new Double( x[i] );
			xList.add(i, tempDouble );
		}

		for( int i = 0; i < y.length; i++ )
		{
			tempDouble = new Double( y[i] );
			yList.add( i, tempDouble );
		}
		
		n = x.length;
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
	
	public void clearLists()
	{
		xList.clear();
		yList.clear();
	}
	
	public double rCorrelation()
	{
		double result = 0.0, 
			   sumXY = 0.0, 
			   sumX = 0.0, 
			   sumY = 0.0, 
			   sumXS = 0.0, 
			   sumYS = 0.0,
			   sumSX = 0.0, 
			   sumSY = 0.0;
			   
		for( int i = 0; i < xList.size(); i++ )
		{
			sumXY += (getXValue(i) * getYValue(i));
			sumX += getXValue(i);
			sumY += getYValue(i);
			sumXS += Math.pow( getXValue(i), 2);
			sumYS += Math.pow( getYValue(i), 2); 
		}
		
		sumSX = Math.pow( sumX, 2 );
		sumSY = Math.pow( sumY, 2 );
		
		double tempSqr = Math.sqrt((( n * sumXS) - sumSX ) * ( (n * sumYS) - sumSY ));
		double tempNum = (( n * sumXY) - ( sumX * sumY ));
		result = tempNum / tempSqr ;	
		
		r = result;
		r2 = r * r;
		
		return result;
	}
	
	public double calcT()
	{
		double result = 0.0;
		
		result = ( (Math.abs(r) * Math.sqrt( (n - 2) )) /
			Math.sqrt( 1 - ( r * r )));
		
		t = result;
		
		return result;
	}
	
	public double calcP()
	{
		double result = 0.0;
		
		p = integrator.calcTDist( t, 40, n );
		
		if( p > .99 ) p = .99;
		significance = 2 * ( 1 - p );
		
		result = significance;
		
		return result;
	}
	
	public boolean withinEpsilon( double exp, double act )
	{
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
		Test7A app = new Test7A();
		double x[] = {186, 699, 132, 272, 291, 331, 199, 1890, 788, 1601};
		double y[] = {15, 69.9, 6.5, 22.4, 28.4, 65.9, 19.4, 198.7, 38.8, 138.2};
		
		app.loadLists( x, y );
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 7\nProblem 7A\n");
		
		/**
		 * Test7A: double E = 0.01 is the amount of error that I am willing
		 * to tolerate when comparing double numbers. 
		 */

		/** Tests of Test7A */
		
		/** These first three tests verify that the values my program returns
		 *  are identical to the book.  The second set of three tests verifies
		 *  that my program produces values that I have calculated by hand based
		 *  on my programs( 1A through 7A )
		 */
		
		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies that the r value is similar to 
		 * 		the one found in the Humphrey text book
		 * Test Description: The result is stored in Test7A.r.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of 0.95
		 * Expected Result: app.rCorrelation([]) ~= 0.95.
		 * Actual Results: See the Console Output window: 
		 * 		"Pass rCorrelation([])? true" represents a passed test.
		 */
		
		app.rCorrelation();
		System.out.println( "Table D13 Results for Humphrey Data." );
		System.out.println( "\nr(x, y) =  " + app.r + 
			"\nPass rCorrelation()? " + app.withinEpsilon( .95, app.r ));
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies that the t value is similar to 
		 * 		the one found in the Humphrey text book
		 * Test Description: The result is stored in Test7A.t.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of 9.0335
		 * Expected Result: app.calcT([]) ~= 9.0335.
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcT([])? true" represents a passed test.
		 */
		
		app.calcT();
		
		System.out.println( "\nt = " + app.t + 
			"\nPass calcT([])? " + app.withinEpsilon( 9.0335, app.t ));
		/** 
		 * Test Number: 2
		 * Test Objective: This test verifies that the significance value is 
		 * 		similar to the one found in the Humphrey text book
		 * Test Description: The result is stored in Test7A.significance.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of .02
		 * Expected Result: app.calcT([]) ~= .02.
		 * Assumptions: I assumed that since the book doesn't show p values of
		 * 		over .99, that I would limit my tests to those values for sake
		 * 		of answer checking
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcP([])? true" represents a passed test.
		 */
		app.calcP();
		
		System.out.println( "\nSignificance: " + app.significance +
			"\nPass calcP([])? " + app.withinEpsilon( .02, app.significance ));
		
		app.clearLists();
		
		double x2[] = {99,154,132,133,78,165,108};
		double y2[] = {145,424,144,163,154,214,124};
		
		app.loadLists(x2, y2);
		
		/** 
		 * Test Number: 3
		 * Test Objective: This test verifies that the r value is similar to 
		 * 		my hand calculated value of 0.57909
		 * Test Description: The result is stored in Test7A.r.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of 0.57909
		 * Expected Result: app.rCorrelation([]) ~= 0.57909.
		 * Actual Results: See the Console Output window: 
		 * 		"Pass rCorrelation([])? true" represents a passed test.
		 */

		app.rCorrelation();
		System.out.println( "\nTable D13 results from Program 1A through 7A." );
		System.out.println( "\nExpected: r(x, y) = .95\nActual: r(x, y) =  " + 
			app.r + "\nPass rCorrelation()?  " + 
			app.withinEpsilon( .57909, app.r ));
		/** 
		 * Test Number: 4
		 * Test Objective: This test verifies that the t value is similar to 
		 * 		my hand calculated value of 1.588
		 * Test Description: The result is stored in Test7A.t.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of 1.588
		 * Expected Result: app.calcT([]) ~= 1.588.
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcT([])? true" represents a passed test.
		 */

		app.calcT();

		System.out.println( "\nExpected: t = 1.588\nActual: t = " + app.t + 
			"\nPass calcT([])? " + app.withinEpsilon( 1.588, app.t ));
		/** 
		 * Test Number: 5
		 * Test Objective: This test verifies that the significance value is 
		 * 		similar to my hand calculated value of .1495
		 * Test Description: The result is stored in Test7A.significance.  
		 * 		This will be the variable which withinEpsilon() tests. Use 
		 * 		app.withinEpsilon() to determine if values agree within E. 
		 * Test Conditions: testing value of .1495
		 * Expected Result: app.calcT([]) ~= .1495.
		 * Actual Results: See the Console Output window: 
		 * 		"Pass calcP([])? true" represents a passed test.
		 */
		app.calcP();

		System.out.println( "\nExpected: Significance = .1495\nActual: " +			"Significance = " + app.significance +
			"\nPass calcP([])? " + app.withinEpsilon( .1495, app.significance ));
	}
}
