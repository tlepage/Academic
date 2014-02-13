/* Program Assignment: 1A
 * Name:               Thomas LePage twl9586
 * Date:               Jan 26, 2004
 * Description:        Assignment 1 Test class for List.java
 */
 
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Test1A
{
	static final double EPSILON = .01;
	protected LinkedList testNull, testZero, testOne, testThree,
		objectLoc, changedLoc, devHours;
	protected List listNull, listZero, listOne, listThree, listObjectLoc,
		listChangedLoc, listDevHours;
	
	public Test1A()
	{
		System.out.println( "Thomas LePage\nCSE 4316\nAssignment 1\n" +			"Problem 1A\n");
		testNull = null;
		
		testZero = new LinkedList();
		testZero.add(new Double(0.0));
		
		testOne = new LinkedList();
		testOne.add(new Double(1.0));
		
		testThree = new LinkedList();
		testThree.add(new Double(1.0));
		testThree.add(new Double(2.0));
		testThree.add(new Double(3.0));
		
		objectLoc = new LinkedList();
		objectLoc.add(new Double(160));
		objectLoc.add(new Double(591));
		objectLoc.add(new Double(114));
		objectLoc.add(new Double(229));
		objectLoc.add(new Double(230));
		objectLoc.add(new Double(270));
		objectLoc.add(new Double(128));
		objectLoc.add(new Double(1657));
		objectLoc.add(new Double(624));
		objectLoc.add(new Double(1503));
		
		changedLoc = new LinkedList();
		changedLoc.add( new Double(186));
		changedLoc.add( new Double(699));
		changedLoc.add( new Double(132));
		changedLoc.add( new Double(272));
		changedLoc.add( new Double(291));
		changedLoc.add( new Double(331));
		changedLoc.add( new Double(199));
		changedLoc.add( new Double(1890));
		changedLoc.add( new Double(788));
		changedLoc.add( new Double(1601));
		
		devHours = new LinkedList();
		devHours.add( new Double(15.0));
		devHours.add( new Double(69.9));
		devHours.add( new Double(6.5));
		devHours.add( new Double(22.4));
		devHours.add( new Double(28.4));
		devHours.add( new Double(65.9));
		devHours.add( new Double(19.4));
		devHours.add( new Double(198.7));
		devHours.add( new Double(38.8));
		devHours.add( new Double(138.2));
		
		listNull = new List( testNull );
		listZero = new List( testZero );
		listOne = new List( testOne );
		listThree = new List( testThree );
		listObjectLoc = new List( objectLoc );
		listChangedLoc = new List( changedLoc );
		listDevHours = new List( devHours );
		
	} // end method
	
	public boolean withinEpsilon( double stdDev, double stdDevExp, double eps )
	{
		boolean pass = false;
		
		if( Math.abs( stdDev - stdDevExp ) < eps )
		{
			pass = true;
		}
		
		return pass;
	} // end method
	
	public static void main( String args[] )
	{
		Test1A app = new Test1A();
		
		DecimalFormat twoDigits = new DecimalFormat( "0.00" );
		
		double avgNull, stdNull, avgZero, stdZero, avgOne, stdOne,
			avgThree, stdThree, avgOLoc, stdOLoc, avgOChange, stdOChange,
			avgHDev, stdHDev;
		
		avgNull = 0.0;
		stdNull = 0.0;
		avgZero = 0.0;
		stdZero = 0.0;
		avgOne = 1.0;
		stdOne = 0.0;
		avgThree = 2.0;
		stdThree = 1.0;
		avgOLoc = 550.6;
		avgOChange = 638.9;
		avgHDev = 60.32;
		stdOLoc = 572.03;
		stdOChange = 625.63;
		stdHDev = 62.26;
		
		System.out.println( "Test for null list:\nAverage: " + 
			twoDigits.format(app.listNull.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listNull.stdDeviation) 
			+ "\nExpected Average: " + avgNull 
			+ "\nExpected Standard Deviation: " + stdNull +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listNull.stdDeviation, 
				stdNull, EPSILON ) );
		
		System.out.println( "\nTest for zero element list:\nAverage: " + 
			twoDigits.format(app.listZero.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listZero.stdDeviation) 
			+ "\nExpected Average: " + avgZero 
			+ "\nExpected Standard Deviation: " + stdZero +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listZero.stdDeviation, 
				stdZero, EPSILON ) );
		
		System.out.println( "\nTest for a one element list:\nAverage: " + 
			twoDigits.format(app.listOne.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listOne.stdDeviation) 
			+ "\nExpected Average: " + avgOne 
			+ "\nExpected Standard Deviation: " + stdOne +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listOne.stdDeviation, 
				stdOne, EPSILON ) );
			
		System.out.println( "\nTest for a three element list:\nAverage: " + 
			twoDigits.format(app.listThree.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listThree.stdDeviation) 
			+ "\nExpected Average: " + avgThree 
			+ "\nExpected Standard Deviation: " + stdThree +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listNull.stdDeviation, 
				stdNull, EPSILON ) );
				
		System.out.println( "\nTest for ObjectLOC(Test 1):\nAverage: " + 
			twoDigits.format(app.listObjectLoc.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listObjectLoc.stdDeviation) 
			+ "\nExpected Average: " + avgOLoc 
			+ "\nExpected Standard Deviation: " + stdOLoc +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listObjectLoc.stdDeviation, 
				stdOLoc, EPSILON ) );
				
		System.out.println( "\nTest for New and Changed Object(Test 2)" +			":\nAverage: " + 
			twoDigits.format(app.listChangedLoc.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listChangedLoc.stdDeviation) 
			+ "\nExpected Average: " + avgOChange 
			+ "\nExpected Standard Deviation: " + stdOChange +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listChangedLoc.stdDeviation, 
				stdOChange, EPSILON ) );
		
		System.out.println( "\nTest for Development Hours(Test 3):\nAverage: " + 
			twoDigits.format(app.listDevHours.average) 
			+ "\nStandard Deviation: " 
			+ twoDigits.format(app.listDevHours.stdDeviation) 
			+ "\nExpected Average: " + avgHDev 
			+ "\nExpected Standard Deviation: " + stdHDev +
			"\nPass( with a .01 precision ): " 
			+ app.withinEpsilon( app.listDevHours.stdDeviation, 
				stdHDev, EPSILON ) );
				
		System.exit(0);
	} // end method
}
