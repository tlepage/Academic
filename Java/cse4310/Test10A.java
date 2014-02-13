/* Program Assignment: 10A
 * Name:               Thomas LePage twl9586
 * Date:               April 19, 2004
 * Description:        Assignment 10 Test class 
 */
public class Test10A
{
	protected GaussMethod gauss;
	protected double a[],
				    b[],
				    c[],
				    d[],
				    initA[],
				    initB[],
				    initC[],
				    initD[];
	protected double numElements;
	protected double wk,
					xk,
					yk,
					zk,
					newK,
					reuseK,
					modifyK,
					newI,
					reuseI,
					modifyI,
					wAvg,
					xAvg,
					yAvg,
					range70,
					range90,
					E,
					LPI,
					UPI;
	protected double betaValues[];
	protected double variance,
					stdDev;
	protected Test5A integrator;
	
	public Test10A( int n, double dA[], double dB[], double dC[], double dD[] )
	{
		a = new double[5];
		b = new double[5];
		c = new double[5];
		d = new double[5];
		
		initA = dA;
		initB = dB;
		initC = dC;
		initD = dD;
		
		wk = 0;
		xk = 0;
		yk = 0;
		zk = 0;
		newK = 0;
		reuseK = 0;
		modifyK = 0;
		newI = 0;
		reuseI = 0;
		modifyI = 0;
		wAvg = 0;
		xAvg = 0;
		yAvg = 0;
		range70 = 0;
		range90 = 0;
		LPI = 0;
		UPI = 0;
		
		variance = 0;
		stdDev = 0;
		
		numElements = n;
		
		integrator = new Test5A();
		makeFormulaValues( n, dA, dB, dC, dD );
		gauss = new GaussMethod( a, b, c, d );
		betaValues = gauss.getBetaValues();
		
		E = .1;
	}
	
	public void makeFormulaValues( int n, double w[], double x[], double y[],
								   double z[] )
	{
		double sumW = 0, 
			  sumX = 0, 
			  sumY = 0, 
			  sumZ = 0,
			  sumW2 = 0,
			  sumX2 = 0,
			  sumY2 = 0,
			  sumWX = 0,
			  sumWY = 0,
			  sumWZ = 0,
			  sumXY = 0,
			  sumXZ = 0,
			  sumYZ = 0;
			  
		for( int i = 0; i < n; i++ )
		{
			sumW += w[i];
			sumX += x[i];
			sumY += y[i];
			sumZ += z[i];
			
			sumW2 += ( w[i] * w[i] );
			sumX2 += ( x[i] * x[i] );
			sumY2 += ( y[i] * y[i] );
			
			sumWX += ( w[i] * x[i] );
			sumWY += ( w[i] * y[i] );
			sumWZ += ( w[i] * z[i] );
			
			sumXY += ( x[i] * y[i] );
			sumXZ += ( x[i] * z[i] );
			
			sumYZ += ( y[i] * z[i] );
		}
		
		a[0] = n;
		a[1] = sumW;
		a[2] = sumX;
		a[3] = sumY;
		a[4] = sumZ;
		
		b[0] = sumW;
		b[1] = sumW2;
		b[2] = sumWX;
		b[3] = sumWY;
		b[4] = sumWZ;
		
		c[0] = sumX;
		c[1] = sumWX;
		c[2] = sumX2;
		c[3] = sumXY;
		c[4] = sumXZ;
		
		d[0] = sumY;
		d[1] = sumWY;
		d[2] = sumXY;
		d[3] = sumY2;
		d[4] = sumYZ;
	}
	
	public double calcZ( double wK, double xK, double yK )
	{
		wk = wK;
		xk = xK;
		yk = yK;
		
		zk = betaValues[0] + (betaValues[1] * wk) + 
				 			 (betaValues[2] * xk) +
				 			 (betaValues[3] * yk);
				 			 
		return zk;
	}
	
	public double calcMRVariance()
	{
		double sum = 0;
		
		for( int i = 0; i < numElements; i++ )
		{
			sum += Math.pow((initD[i] -  betaValues[0] 
								  - (betaValues[1] * initA[i]) 
					     		  - (betaValues[2] * initB[i]) 
					     		  - (betaValues[3] * initC[i])), 2);
		}
		
		variance = sum * (1 / (numElements - 4));
		
		return variance;
	}
	
	public double calcMRStdDev()
	{
		stdDev = Math.sqrt( variance );
		
		return stdDev;
	}
	
	public void calcAverages()
	{
		double wSum = 0, 
			   xSum = 0, 
			   ySum = 0;
			   
		for( int i = 0; i < numElements; i++ )
		{
			wSum += initA[i];
			xSum += initB[i];
			ySum += initC[i];
		}
		
		wAvg = (wSum / numElements );
		xAvg = (xSum / numElements );
		yAvg = (ySum / numElements );
	}
	
	public void calcNRMTerms()
	{
		newK = Math.pow((wk - wAvg), 2);
		reuseK = Math.pow((xk - xAvg), 2);
		modifyK = Math.pow((yk - yAvg), 2);
		
		for( int i = 0; i < numElements; i++ )
		{
			newI += Math.pow((initA[i] - wAvg), 2);
			reuseI += Math.pow((initB[i] - xAvg), 2);
			modifyI += Math.pow((initC[i] - yAvg), 2);
		}
	}
	
	public void calcRange()
	{
		double t70 = 1.386,
			   t90 = 2.920;
		range70 = t70 * stdDev * Math.sqrt( 1 + (1 / numElements) + 
			(newK / newI ) + (reuseK / reuseI) + (modifyK / modifyI));
		range90 = t90 * stdDev * Math.sqrt( 1 + (1 / numElements) + 
			(newK / newI ) + (reuseK / reuseI) + (modifyK / modifyI));
	}
	
	public double calculateLPI( double yK, int percent )
	{
		double rt = 0;
		if( percent == 70 )
			LPI = ( yK - range70 );
		else
			LPI = ( yK - range90 );
			
		rt = LPI;
		
		return rt;
	}
	
	public double calculateUPI( double yK, int percent )
	{
		double rt = 0;
		if( percent == 70 )
			UPI = ( yK + range70 );
		else
			UPI = ( yK + range90 );
		
		rt = UPI;
	
		return rt;
	}
	
	public boolean withinEpsilon( double expectedResult, double actualResult )
	{
		return ( Math.abs(actualResult - expectedResult ) <= E );
	}
	
	public static void main( String args[] )
	{
		double w[] = {1142, 863, 1065, 554, 983, 256};
		double x[] = {1060, 995, 3205, 120, 2896, 485};
		double y[] = {325, 98, 23, 0, 120, 88};
		double z[] = {201, 98, 162, 54, 138, 61};
		
		System.out.println( 
			"Thomas LePage\nCSE 4310\nAssignment 10\nProblem 10A\n");
		/** Tests of Test10A */
		
		/** There are two tests in this test driver.  They are used to test the
		 *  data that my program calculates is the same as what is found in the
		 *  Humphrey textbook in Table D17.
		 */

		/** 
		 * Test Number: 0
		 * Test Objective: This test verifies that the data produced by my 
		 * 		program is the same for the Appendix A values in Table D17 
		 * 		in the Humphrey textbook.
		 * Test Description: The results are printed on the screen in the 4th
		 * 		column of the test table, labeled "Pass".   
		 * Test Conditions: This test verifies the following conditions:
		 * 		Beta 0: 6.7013
		 * 		Beta 1: 0.0784
		 * 		Beta 2: 0.0150
		 * 		Beta 3: 0.2461
		 * 		Projected Hours: 140.9
		 * 		UPI(70%): 179.7
		 * 		LPI(70%): 102.1
		 * 		UPI(90%): 222.74
		 * 		LPI(90%): 59.60
		 * All results are expected to be within 10% of these values. 
		 * Expected Result: All expected values are listed above and in the
		 * 		table under the "Expected Value Column"
		 * Actual Results: See the Console Output window: 
		 * 		All tests should display "true" in the "Pass" 
		 * 		column of the table.
		 */
		Test10A app = new Test10A( 6, w, x, y, z );
		app.calcZ( 650, 3000, 155 );
		app.calcMRVariance();
		app.calcMRStdDev();
		app.calcAverages();
		app.calcNRMTerms();
		System.out.println( "Appendix A\n\n" );
		System.out.println( "Parameter\t\tExpected Value\t\tActual Value\tPass\n");
		System.out.println( "Beta 0\t\t\t6.7013\t\t" + app.betaValues[0] +
			"\t\t" + app.withinEpsilon( 6.7013, app.betaValues[0] ));
		System.out.println( "Beta 1\t\t\t0.0784\t\t" + app.betaValues[1] +
			"\t\t" + app.withinEpsilon( 0.0784, app.betaValues[1] ));
		System.out.println( "Beta 2\t\t\t0.0150\t\t" + app.betaValues[2] +
			"\t\t" + app.withinEpsilon( 0.0150, app.betaValues[2] ));
		System.out.println( "Beta 3\t\t\t0.2461\t\t" + app.betaValues[3] +
			"\t\t" + app.withinEpsilon( 0.2461, app.betaValues[3] ));
		System.out.println( "Projected Hours\t140.9\t\t" + app.zk +
			"\t\t" + app.withinEpsilon( 140.9, app.zk ));
	
		app.calcRange();
	
		System.out.println( "LPI(70)\t\t\t102.1\t\t" + 
			app.calculateLPI( app.zk, 70) + "\t\t" +
			app.withinEpsilon( 102.1, app.calculateLPI( app.zk, 70 ) ));
		System.out.println( "UPI(70)\t\t\t179.7\t\t" + 
			app.calculateUPI( app.zk, 70) + "\t\t" +
			app.withinEpsilon( 179.7, app.calculateUPI( app.zk, 70) ));
		System.out.println( "LPI(90)\t\t\t59.06\t\t" + 
			app.calculateLPI( app.zk, 90) + "\t\t" +
			app.withinEpsilon( 59.06, app.calculateLPI( app.zk, 90) ));
		System.out.println( "UPI(90)\t\t\t222.74\t\t" + 
			app.calculateUPI( app.zk, 90) + "\t\t" + 
			app.withinEpsilon( 222.74, app.calculateUPI( app.zk, 90) ));
		
		double w2[] = {345, 168, 94, 187, 621, 255,};
		double x2[] = {65, 18, 0, 185, 87, 0};
		double y2[] = {23, 18, 0, 98, 10, 0};
		double z2[] = {31.4, 14.6, 6.4, 28.3, 42.1, 15.3};
		
		/** 
		 * Test Number: 1
		 * Test Objective: This test verifies that the data produced by my 
		 * 		program is the same for the Table D16 values in Table D17 
		 * 		in the Humphrey textbook.
		 * Test Description: The results are printed on the screen in the 4th
		 * 		column of the test table, labeled "Pass".   
		 * Test Conditions: This test verifies the following conditions:
		 * 		Beta 0: 0.56645
		 * 		Beta 1: 0.06533
		 * 		Beta 2: 0.008719
		 * 		Beta 3: 0.15105
		 * 		Projected Hours: 20.76
		 * 		UPI(70%): 26.89
		 * 		LPI(70%): 14.63
		 * 		UPI(90%): 33.67
		 * 		LPI(90%): 7.84
		 * All results are expected to be within 10% of these values. 
		 * Expected Result: All expected values are listed above and in the
		 * 		table under the "Expected Value Column"
		 * Actual Results: See the Console Output window: 
		 * 		All tests should display "true" in the "Pass" 
		 * 		column of the table.
		 */
		
		app = new Test10A( 6, w2, x2, y2, z2 );
		app.calcZ( 185, 150, 45 );
		app.calcMRVariance();
		app.calcMRStdDev();
		app.calcAverages();
		app.calcNRMTerms();
		System.out.println( "\n\nTable D16\n\n");
		System.out.println( "Parameter\t\tExpected Value\t\tActual Value\tPass\n");
		System.out.println( "Beta 0\t\t\t0.56645\t\t" + app.betaValues[0] +
			"\t\t" + app.withinEpsilon( 0.56645, app.betaValues[0] ));
		System.out.println( "Beta 1\t\t\t0.06533\t\t" + app.betaValues[1] +
			"\t\t" + app.withinEpsilon( 0.06533, app.betaValues[1] ));
		System.out.println( "Beta 2\t\t\t0.00872\t\t" + app.betaValues[2] +
			"\t\t" + app.withinEpsilon( 0.008719, app.betaValues[2] ));
		System.out.println( "Beta 3\t\t\t0.15105\t\t" + app.betaValues[3] +
			"\t\t" + app.withinEpsilon( 0.15105, app.betaValues[3] ));
		System.out.println( "Projected Hours\t20.76\t\t" + app.zk +
			"\t\t" + app.withinEpsilon( 20.76, app.zk ));
		
		app.calcRange();

		System.out.println( "LPI(70)\t\t\t14.63\t\t" + 
			app.calculateLPI( app.zk, 70) + "\t\t" +
			app.withinEpsilon( 14.63, app.calculateLPI( app.zk, 70) ));
		System.out.println( "UPI(70)\t\t\t26.89\t\t" + 
			app.calculateUPI( app.zk, 70) + "\t\t" +
			app.withinEpsilon( 26.89, app.calculateUPI( app.zk, 70) ));
		System.out.println( "LPI(90)\t\t\t7.840\t\t" + 
			app.calculateLPI( app.zk, 90) + "\t\t" +
			app.withinEpsilon( 7.84, app.calculateLPI( app.zk, 90) ));
		System.out.println( "UPI(90)\t\t\t33.67\t\t" + 
			app.calculateUPI( app.zk, 90) + "\t\t" +
			app.withinEpsilon( 33.67, app.calculateUPI( app.zk, 90) ));
	}
}
