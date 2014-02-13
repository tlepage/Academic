/* Program Assignment: 4A
 * Name:               Thomas LePage twl9586
 * Date:               Jan 26, 2004
 * Description:        Assignment 4 Test class for List.java
 */
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Test4A
{
	private DecimalFormat twoDigits,
						  fourDigits;
	private LinkedList testOneX, 
					   testOneY, 
					   testTwoX,
					   testTwoY,
					   testThreeX,
					   testThreeY;
	private List testOneList,
	             testTwoList,
	             testThreeList;
	             
	private static final double EPSILON = .01;
	
	public Test4A()
	{
		System.out.println( "Thomas LePage\nCSE 4316\nAssignment 4\n" +
			"Problem 4A\n");
			
		twoDigits = new DecimalFormat( "0.00" );
		fourDigits = new DecimalFormat( "0.0000" );
		
		testOneX = new LinkedList();
		testOneX.add( new Double(130) );
		testOneX.add( new Double(650) );
		testOneX.add( new Double(99) );
		testOneX.add( new Double(150));
		testOneX.add( new Double(128));
		testOneX.add( new Double(302));
		testOneX.add( new Double(95));
		testOneX.add( new Double(945));
		testOneX.add( new Double(368));
		testOneX.add( new Double(961));
		
		testOneY = new LinkedList();
		testOneY.add( new Double(186));
		testOneY.add( new Double(699));
		testOneY.add( new Double(132));
		testOneY.add( new Double(272));
		testOneY.add( new Double(291));
		testOneY.add( new Double(331));
		testOneY.add( new Double(199));
		testOneY.add( new Double(1890));
		testOneY.add( new Double(788));
		testOneY.add( new Double(1601));
		
		testTwoX = new LinkedList();
		testTwoX.add( new Double(163));
		testTwoX.add( new Double(765));
		testTwoX.add( new Double(141));
		testTwoX.add( new Double(166));
		testTwoX.add( new Double(137));
		testTwoX.add( new Double(355));
		testTwoX.add( new Double(136));
		testTwoX.add( new Double(1206));
		testTwoX.add( new Double(433));
		testTwoX.add( new Double(1130));
		
		testTwoY = new LinkedList();
		testTwoY.add( new Double(186));
		testTwoY.add( new Double(699));
		testTwoY.add( new Double(132));
		testTwoY.add( new Double(272));
		testTwoY.add( new Double(291));
		testTwoY.add( new Double(331));
		testTwoY.add( new Double(199));
		testTwoY.add( new Double(1890));
		testTwoY.add( new Double(788));
		testTwoY.add( new Double(1601));
		
		testThreeX = new LinkedList();
		testThreeX.add( new Double(10));
		testThreeX.add( new Double(80));
		testThreeX.add( new Double(50));
		testThreeX.add( new Double(50));
		testThreeX.add( new Double(50));
		testThreeX.add( new Double(70));
		testThreeX.add( new Double(100));
		testThreeX.add( new Double(100));
		testThreeX.add( new Double(250));
		
		testThreeY = new LinkedList();
		testThreeY.add( new Double(0));
		testThreeY.add( new Double(94));
		testThreeY.add( new Double(37));
		testThreeY.add( new Double(31));
		testThreeY.add( new Double(43));
		testThreeY.add( new Double(69));
		testThreeY.add( new Double(111));
		testThreeY.add( new Double(122));
		testThreeY.add( new Double(295));
		
		testOneList = new List( testOneX );
		testTwoList = new List( testTwoX );
		testThreeList = new List( testThreeX );
	}
	
	public boolean withinEpsilon( double beta, double betaExp, double eps )
	{
		boolean pass = false;
		
		if( Math.abs( beta - betaExp ) < eps )
		{
			
			pass = true;
		}
	
		return pass;
	} // end method
	
	public static void main( String args[] )
	{
		Test4A app = new Test4A();
		
		double beta0T1, beta1T1, beta0T2, beta1T2, beta0T3, beta1T3;
		
		beta0T1 = -22.5500;
		beta1T1 = 1.7279;
		beta0T2 = -23.9200;
		beta1T2 = 1.4310;
		beta0T3 = -17.95;
		beta1T3 = 1.3203;
		
		double test1B1 = app.testOneList.calcBetaOne( app.testOneY );
		double test1B0 = app.testOneList.calcBetaZero();
		double test2B1 = app.testTwoList.calcBetaOne( app.testTwoY );
		double test2B0 = app.testTwoList.calcBetaZero();
		double test3B1 = app.testThreeList.calcBetaOne( app.testThreeY );
		double test3B0 = app.testThreeList.calcBetaZero();
		
		System.out.println( "Test 1:\n\nEstimated beta0: " + beta0T1 +
			"\nEstimated beta1: " + beta1T1 + "\nActual beta1: " + 
			app.fourDigits.format(test1B1) + "\nActual beta0: " + 
			app.twoDigits.format(test1B0) + "\nPass(beta0): " + 
			app.withinEpsilon(beta0T1, test1B0, EPSILON )
			+ "\nPass(beta1): " + 
			app.withinEpsilon(beta1T1, test1B1, EPSILON ) +
			"\nExpected Object LOC: 382.8" + "\nExpected N&C LOC: " +
			app.fourDigits.format((beta0T1 + ( beta1T1 * 382.8))));
			
		System.out.println( "\nTest 2:\n\nEstimated beta0: " + beta0T2 +
			"\nEstimated beta1: " + beta1T2 + "\nActual beta1: " + 
			app.fourDigits.format(test2B1) + "\nActual beta0: " + 
			app.twoDigits.format(test2B0) + "\nPass(beta0): " + 
			app.withinEpsilon(beta0T2, test2B0, EPSILON )
			+ "\nPass(beta1): " + 
			app.withinEpsilon(beta1T2, test2B1, EPSILON ) +
			"\nExpected Object LOC: 382.8" + "\nExpected N&C LOC: " +
			app.fourDigits.format((beta0T2 + ( beta1T2 * 382.8 ))));
		
		System.out.println( "\nTest 3:\n\nEstimated beta0: " + beta0T3 +
			"\nEstimated beta1: " + beta1T3 + "\nActual beta1: " + 
			app.fourDigits.format(test3B1) + "\nActual beta0: " + 
			app.twoDigits.format(test3B0) + "\nPass(beta0): " + 
			app.withinEpsilon(beta0T3, test3B0, EPSILON )
			+ "\nPass(beta1): " + 
			app.withinEpsilon(beta1T3, test3B1, EPSILON ) +
			"\nExpected Object LOC: 50" + "\nExpected N&C LOC: " +
			app.fourDigits.format((beta0T3 + ( beta1T3 * 50 ))));
	}// end method
}
