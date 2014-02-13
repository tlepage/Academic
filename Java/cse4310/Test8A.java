/* Program Assignment: 8A
 * Name:               Thomas LePage twl9586
 * Date:               March 29, 2004
 * Description:        Assignment 8 Test class 
 */
import java.util.*;
import java.text.*;

public class Test8A
{
	protected LinkedList sortList;
	protected DecimalFormat fourDigits, twoDigits;
	
	public Test8A()
	{
		sortList = new LinkedList();
		fourDigits = new DecimalFormat( "0.0000" );
		twoDigits = new DecimalFormat( "0.00" );
	}
	
	// updated
	public void loadList( double x[], // Object per LOC Method
						  double y[], // Normal form 
						  double z[] )// fractional part
	{
		Vector currentVector;
		Object xObj = new Object(), 
			   yObj = new Object(),
			   zObj = new Object();
		
		for( int i = 0; i < x.length; i++ )
		{
			currentVector = new Vector();
			
			xObj = new Double( x[i] );
			yObj = new Double( y[i] );
			zObj = new Double( z[i] );
			
			currentVector.add( 0, xObj );
			currentVector.add( 1, yObj );
			currentVector.add( 2, zObj );
			
			sortList.add( i, currentVector );
		}
	}
	
	public void clearList()
	{
		sortList.clear();
	}
	
	public double getVectorXValue( int index )
	{
		Vector temp = (Vector)sortList.get(index);
		Double xObj = (Double)temp.get(0);
			   
		double x = xObj.doubleValue();
		return x;
	}
	
	public double getVectorYValue( int index )
	{
		Vector temp = (Vector)sortList.get(index);
		Double yObj = (Double)temp.get(1);
		   
		double y = yObj.doubleValue();
		return y;
	}
	
	// new
	public double getVectorZValue( int index )
	{
		Vector temp = (Vector)sortList.get(index);
		Double zObj = (Double)temp.get(1);
   
		double z = zObj.doubleValue();
		return z;
	}
	
	// updated
	public void printList()
	{
		/*System.out.println( "Item\tActual New And\t\tDevelopment\nList" +			"\tChanged LOC\t\t\tHours" );
		for( int i = 0; i < sortList.size(); i++ )
		{
			System.out.println( i + "\t\t" + getVectorXValue(i)
				+ "\t\t\t\t" + getVectorYValue(i));
		}
		*/
		
		System.out.println( "Item\tCumul. Frac\t\tObj. LOC\tNormal Form" );
		for( int i = 0; i < sortList.size(); i++ )
		{
			System.out.println( i + "\t\t" + fourDigits.format(getVectorZValue(i)) + "\t\t\t" +
				twoDigits.format(getVectorXValue(i)) + "\t\t" + fourDigits.format(getVectorYValue(i)));
		}
		
		System.out.println();
	}
	
	public void sortByX()
	{
		Collections.sort(sortList, new CompareX() );
	}
	
	// use only this function
	public void sortByY()
	{
		Collections.sort(sortList, new CompareY() );
	}
	
	public boolean compareLists( LinkedList exp, LinkedList act )
	{
		boolean rt = true;
		
		for( int i = 0; i < exp.size(); i++ )
		{
			Vector temp1 = (Vector)exp.get(i);
			Vector temp2 = (Vector)act.get(i);
			
			Double xObj1 = (Double)temp1.get(0);
			Double xObj2 = (Double)temp2.get(0);
			
			Double yObj1 = (Double)temp1.get(1);
			Double yObj2 = (Double)temp2.get(1);
			
			//System.out.println( "t1x: " + xObj1.doubleValue() +
			//" t2x: " + xObj2.doubleValue() + "\nt1y: " + yObj1.doubleValue() +
			//" t2y: " + yObj2.doubleValue() );
			if( (xObj1.doubleValue() != xObj2.doubleValue()) &&
				(yObj1.doubleValue() != yObj2.doubleValue()))
				rt = false;
		}
		
		return rt;
	}
	
	public static void main( String args[] )
	{
		Test8A app = new Test8A();
		Test8A appTester = new Test8A();
		
		double x[] = { 26, 39, 26, 53, 17, 26, 22, 53, 31, 18, 25, 30, 42, 45, 
				       40, 195, 114, 87, 87, 25, 53, 47, 58, 49, 88, 82, 46, 55, 
				       32, 18, 89, 18, 36, 161, 112, 89, 85, 82, 82, 46, 184, 
				       145, 96, 123, 211, 405, 58, 264, 58, 305 }, 
			  // load only this list
			   y[] = { 8.67, 7.8, 8.67, 7.57, 5.67, 8.67, 7.33, 8.83, 10.33, 6, 
			   		   8.33, 7.5, 14, 9, 10, 39, 38, 21.75, 29, 8.33, 13.25, 
			   		   11.75, 5.8, 7, 14.67, 16.4, 5.75, 13.75, 10.67, 6, 29.67, 
			   		   6, 12, 40.25, 37.33, 22.25, 28.33, 20.5, 16.4, 7.67, 
			   		   36.8, 24.17, 19.2, 41, 30.14, 50.63, 14.5, 52.8, 19.33, 
			   		   25.42 };
		double xF[] = { 17, 18, 18, 18, 22, 25, 25, 26, 26, 26, 30, 31, 32, 36, 
					    39, 40, 42, 45, 46, 46, 47, 49, 53, 53, 53, 55, 58, 58, 
					    58, 82, 82, 82, 85, 87, 87, 88, 89, 89, 96, 112, 114, 
					    123, 145, 161, 184, 195, 211, 264, 305, 405 },
			   yS[] = { 5.67, 5.75, 5.8, 6, 6, 6, 7, 7.33, 7.5, 7.57, 7.67, 7.8, 
			   		    8.33, 8.33, 8.67, 8.67, 8.67, 8.83, 9, 10, 10.33, 10.67, 
			   		    11.75, 12, 13.25, 13.75, 14, 14.5, 14.67, 16.4, 16.4, 
			   		    19.2, 19.33, 20.5, 21.75, 22.25, 24.17, 25.42, 28.33, 
			   		    29, 29.67, 30.14, 36.8, 37.33, 38, 39, 40.25, 41, 50.63, 
			   		    52.8 };
		
		System.out.println( "Thomas LePage\nCSE 4310\nAssignment 8\nProblem 8A\n");
		
		/** Tests of Test8A */
		
		/** These first two tests verify that the program actually sorts the 
		 *  lists correctly, first by first attribute and then by second 
		 *  attribute from the Humphrey Textbook.  The next two tests do the
		 *  same for my test data.
		 */

		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies that the program correctly sorts
		 * 		the list by first attribute.
		 * Test Description: The result is stored in Test8A.sortList.  
		 * 		This, along with a pre-generated list of the correct sorting,
		 *      will be used to test correctness in compareLists().  
		 * Test Conditions: testing sorted lists ( app.sortList and 
		 * 		appTester.sortList
		 * Expected Result: app.compareLists([])? = true
		 * Actual Results: See the Console Output window: 
		 * 		"List (by first element) sorted correctly? true" represents a passed test.
		 */
		//app.loadList( x, y );
		//appTester.loadList( xF, yS );
		System.out.println( "--------------------------------------------------\n" +			"Humphrey Textbook Data\n" );
		System.out.println( "Unsorted List\n" );
		app.printList();
		app.sortByX();
		System.out.println( "Sorted List by First Element" );
		app.printList();
		app.sortByY();
		System.out.println( "List (by first element) sorted correctly? " + app.compareLists( appTester.sortList, 
			app.sortList ));
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies that the program correctly sorts
		 * 		the list by second attribute.
		 * Test Description: The result is stored in Test8A.sortList.  
		 * 		This, along with a pre-generated list of the correct sorting,
		 *      will be used to test correctness in compareLists().  
		 * Test Conditions: testing sorted lists ( app.sortList and 
		 * 		appTester.sortList
		 * Expected Result: app.compareLists([])? = true
		 * Actual Results: See the Console Output window: 
		 * 		"List (by second element) sorted correctly? true" represents a passed test.
		 */
		System.out.println( "\nSorted List by Second Element" );
		app.printList();
		System.out.println( "List (by second element) sorted correctly? " + app.compareLists( appTester.sortList, 
			app.sortList ));
		
		double x2[] = { 23, 6.54, 12.56, 987.3, 987.4, 912.3, 22.0, 
			9.52, 67.8, 44.1 };
		double y2[] = { 44, 902, 12.0, 3.547, 1289.2, 32.1, 912.3, 
			43.2, 65.7, 203.5 };
		
		double x2f[] = { 6.54, 9.52, 12.56, 22.0, 23.0, 44.1, 67.8, 912.3, 987.3, 987.4 },
		       y2s[] = { 902.0, 43.2, 12.0, 912.3, 44.0, 203.5, 65.7, 32.1, 3.547, 1289.2 },
		       x2s[] = { 987.3, 12.56, 912.3, 9.52, 23.0, 67.8, 44.1, 6.54, 22.0, 987.4 },
		       y2f[] = { 3.547, 12.0, 32.1, 43.2, 44.0, 65.7, 203.5, 902.0, 912.3, 1289.2 };
		System.out.println();
		app.clearList();
		appTester.clearList();
		
		/** 
		 * Test Number: 2
		 * Test Objective: This test verifies that the program correctly sorts
		 * 		the list by first attribute.
		 * Test Description: The result is stored in Test8A.sortList.  
		 * 		This, along with a pre-generated list of the correct sorting,
		 *      will be used to test correctness in compareLists().  
		 * Test Conditions: testing sorted lists ( app.sortList and 
		 * 		appTester.sortList
		 * Expected Result: app.compareLists([])? = true
		 * Actual Results: See the Console Output window: 
		 * 		"List (by first element) sorted correctly? true" represents a passed test.
		 */
		//app.loadList( x2, y2 );
		//appTester.loadList(x2f, y2s);
		System.out.println( "-----------------------------------------------------\n" +			"My Test Data\n" );
		System.out.println( "Unsorted List\n" );
		app.printList();
		app.sortByX();
		System.out.println( "Sorted List by First Element" );
		app.printList();
		System.out.println( "List (by first element) sorted correctly? " + app.compareLists( appTester.sortList, 
			app.sortList ));
		
		/** 
		 * Test Number: 3
		 * Test Objective: This test verifies that the program correctly sorts
		 * 		the list by second attribute.
		 * Test Description: The result is stored in Test8A.sortList.  
		 * 		This, along with a pre-generated list of the correct sorting,
		 *      will be used to test correctness in compareLists().  
		 * Test Conditions: testing sorted lists ( app.sortList and 
		 * 		appTester.sortList
		 * Expected Result: app.compareLists([])? = true
		 * Actual Results: See the Console Output window: 
		 * 		"List (by second element) sorted correctly? true" represents a passed test.
		 */
		app.sortByY();
		System.out.println( "\nSorted List by Second Element" );
		app.printList();
		appTester.clearList();
		//appTester.loadList( x2s, y2f );
		System.out.println( "List (by second element) sorted correctly? " + app.compareLists( appTester.sortList, 
			app.sortList ));	           
	}
	
	private class CompareX implements Comparator
	{
		protected int xCompare;
		protected Double x1, x2;
		
		public int compare( Object object1, Object object2 )
		{
			Vector tempV1 = (Vector)object1;
			Vector tempV2 = (Vector)object2;
			
			x1 = (Double)tempV1.get(0);
			x2 = (Double)tempV2.get(0);
			
			xCompare = new Double( x1.doubleValue() ).compareTo( 
				new Double( x2.doubleValue() ) );
			
			return xCompare;
		}
	}
	
	private class CompareY implements Comparator
	{
		protected int yCompare;
		protected Double y1, y2;
	
		public int compare( Object object1, Object object2 )
		{
			Vector tempV1 = (Vector)object1;
			Vector tempV2 = (Vector)object2;
		
			y1 = (Double)tempV1.get(1);
			y2 = (Double)tempV2.get(1);
		
			yCompare = new Double( y1.doubleValue() ).compareTo( 
				new Double( y2.doubleValue() ) );
		
			return yCompare;
		}
	}
}

