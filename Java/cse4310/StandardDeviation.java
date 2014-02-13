/*
 * Created on Jan 27, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
// Thomas LePage
// January 27, 2004
// 1A
import java.util.LinkedList;
import java.text.DecimalFormat;

public class StandardDeviation
{
	private LinkedList listObject, listNewChanged, listDevelopment;
	
	private double objLoc[] = { 160, 591, 114, 229, 230, 270, 128,
								1657, 624, 1503 };
	private double changedLoc[] = { 186, 699, 132, 272, 291, 331, 
									199, 1890, 788, 1601 };
	private double devHours[] = { 15.0, 69.9, 6.5, 22.4, 28.4, 65.9,
								  19.4, 198.7, 38.8, 138.2 };
	private double averages[];
	private double stdDeviations[];
	
	private DecimalFormat twoDigits;
								  
	public StandardDeviation()
	{
		/*listObject = new LinkedList();
		listNewChanged = new LinkedList();
		listDevelopment = new LinkedList();
		
		averages = new double[3];
		stdDeviations = new double[3];
		
		twoDigits = new DecimalFormat( "0.00" );
		
		initialize( listObject, objLoc );
		initialize( listNewChanged, changedLoc );
		initialize( listDevelopment, devHours );
		
		averages[0] = calcAvg( listObject );
		averages[1] = calcAvg( listNewChanged );
		averages[2] = calcAvg( listDevelopment );
		
		stdDeviations[0] = calcStdDeviation( listObject, averages[0] );
		stdDeviations[1] = calcStdDeviation( listNewChanged, averages[1] );
		stdDeviations[2] = calcStdDeviation( listDevelopment, averages[2] );
		
		printForProblem( averages, stdDeviations );*/
	}
	
	public void initialize( LinkedList list, double array[] )
	{
		Double doubleObj;
		
		for( int i = 0; i < array.length; i++ )
		{
			doubleObj = new Double(array[i]);
			list.add(i, doubleObj);
		}
	}
	
	public double calcAvg( LinkedList list )
	{
		double average = 0.0, temp = 0.0;
		Double doubleObject;
		
		for( int i = 0; i < list.size(); i++ )
		{
			doubleObject = (Double)list.get(i);
			temp = doubleObject.doubleValue();
			average += temp;
		}
		
		average /= list.size();
		
		return average;
	}
	
	public double calcStdDeviation( LinkedList list, double average )
	{
		double numerator = 0.0, temp = 0.0, stdDev = 0.0;
		Double doubleObject;
		
		for( int i = 0; i < list.size(); i++ )
		{
			doubleObject = (Double)list.get(i);
			temp = doubleObject.doubleValue();
			numerator += ((temp - average)*(temp - average));
		}
		
		numerator /= (list.size() - 1);
		stdDev = Math.sqrt( numerator );
		
		return stdDev;
	}
	
	public void printForProblem( double averages[], double stdDevs[] )
	{
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 1\n" +			"Problem 1A\nOutput:\n\nTest 1: Object LOC\nTest 2: " +			"New and Changed LOC\nTest 3: Development Hours\n" );
		for( int i = 0; i < 3; i++ )
		{
			System.out.println( "Mean for test " + (i + 1) + ": " +
				twoDigits.format(averages[i]) );
			System.out.println( "Standard deviation for test " + (i + 1) +
				": " + twoDigits.format(stdDevs[i]) + "\n" );
		}
	}
	
	/*public static void main( String args[] )
	{
		StandardDeviation app = new StandardDeviation();
	}*/
}