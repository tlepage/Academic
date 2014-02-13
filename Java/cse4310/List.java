/* Program Assignment: 1A; 4A
 * Name:               Thomas LePage twl9586
 * Date:               Jan 26, 2004; Feb 18, 2004
 * Description:        Class which calculates std dev functions and holds
 *                     the linked list; also calculates linear regression
 */

import java.util.LinkedList;

public class List
{
	protected LinkedList list;
	protected double average, 
					 stdDeviation, 
					 beta0, 
					 beta1,
					 xAvg,
					 yAvg,
					 x2Avg,
					 xySum,
					 x2Sum,
					 xSum,
					 ySum;
	protected int n; 
	
	public List( LinkedList tempList )
	{
		list = tempList;
		average = calcAvg();
		stdDeviation = calcStdDeviation(average);
		beta0 = 0.0;
		beta1 = 0.0;
	} // end method
	
	public double calcAvg()
	{
		double average = 0.0, temp = 0.0;
		Double doubleObject;
		
		if( list == null )
		{
			average = 0.0;
		}
		else
		{
			for( int i = 0; i < list.size(); i++ )
			{
				doubleObject = (Double)list.get(i);
				temp = doubleObject.doubleValue();
				average += temp;
			}
		
			average /= list.size();
		}
		return average;
	} // end method
	
	public double calcStdDeviation( double average )
	{
		double numerator = 0.0, temp = 0.0, stdDev = 0.0;
		Double doubleObject;
		
		if( list == null )
		{
			stdDev = 0.0;
		}
		else
		{
			for( int i = 0; i < list.size(); i++ )
			{
				doubleObject = (Double)list.get(i);
				temp = doubleObject.doubleValue();
				numerator += ((temp - average)*(temp - average));
			}
		
			if( list.size() - 1  == 0 )
			{
				stdDev = 0;
				System.out.println("Divide by zero error found.  " +					"Setting to zero.\n");
			}
			else
			{
				numerator /= (list.size() - 1);
				stdDev = Math.sqrt( numerator );
			}
		}
			
		return stdDev;
	} // end method
	
	public double calcBetaOne( LinkedList yList )
	{
		Double doubleObjectX, doubleObjectY;
		double betaOne = 0.0;
		
		if( list == null )
		{
			beta1 = 0.0;
		}
		else
		{
			for( int t = 0; t < yList.size(); t++ )
			{
				doubleObjectX = (Double)list.get(t);
				doubleObjectY = (Double)yList.get(t);
				
				xySum += (doubleObjectX.doubleValue() * 
					doubleObjectY.doubleValue());
				
				x2Sum += (doubleObjectX.doubleValue() * 
					doubleObjectX.doubleValue());	
				
				xSum += doubleObjectX.doubleValue();
				ySum += doubleObjectY.doubleValue();
			}
			
			xAvg = xSum / list.size();
			yAvg = ySum / yList.size();
			
			x2Avg = xAvg * xAvg;
			
			betaOne = (xySum - ( list.size() * xAvg * yAvg )) /
					(x2Sum - ( list.size() * x2Avg ));
					
			beta1 = betaOne;
		}
		
		return betaOne;
	}// end method
	
	public double calcBetaZero()
	{
		double betaZero = 0.0;
		
		betaZero = yAvg - (beta1 * xAvg);
		beta0 = betaZero;
		
		return betaZero;
	}
	
}