/* Program Assignment: 10A
 * Name:               Thomas LePage twl9586
 * Date:               April 12, 2004
 * Description:        Class containing Gauss Methods 
 */
public class GaussMethod
{
	protected double betaValues[];
	
	public GaussMethod( double a[], double b[], double c[], double d[] )
	{
		double e[] = new double[4], 
			  f[] = new double[4], 
			  g[] = new double[4], 
			  h[] = new double[3], 
			  i[] = new double[3], 
			  j[] = new double[2];
		
		betaValues = new double[4];
		
		for( int t = 0; t < 4; t++ )
		{
			betaValues[t] = 0;
		}
		
		e[0] = b[1] - ( (a[1] * b[0]) / a[0] ); //e1
		e[1] = b[2] - ( (a[2] * b[0]) / a[0] ); //e2
		e[2] = b[3] - ( (a[3] * b[0]) / a[0] ); //e3
		e[3] = b[4] - ( (a[4] * b[0]) / a[0] ); //e4
		
		f[0] = c[1] - ( (a[1] * c[0]) / a[0] );
		f[1] = c[2] - ( (a[2] * c[0]) / a[0] );
		f[2] = c[3] - ( (a[3] * c[0]) / a[0] );
		f[3] = c[4] - ( (a[4] * c[0]) / a[0] );
		
		g[0] = d[1] - ( (a[1] * d[0]) / a[0] );
		g[1] = d[2] - ( (a[2] * d[0]) / a[0] );
		g[2] = d[3] - ( (a[3] * d[0]) / a[0] );
		g[3] = d[4] - ( (a[4] * d[0]) / a[0] );
		
		h[0] = f[1] - ( (e[1] * f[0]) / e[0] ); //h2
		h[1] = f[2] - ( (e[2] * f[0]) / e[0] ); //h3
		h[2] = f[3] - ( (e[3] * f[0]) / e[0] ); //h4
		
		i[0] = g[1] - ( (e[1] * g[0]) / e[0] ); //i2
		i[1] = g[2] - ( (e[2] * g[0]) / e[0] ); //i3
		i[2] = g[3] - ( (e[3] * g[0]) / e[0] ); //i4
		
		j[0] = i[1] - ( (h[1] * i[0]) / h[0] ); //j3
		j[1] = i[2] - ( (h[2] * i[0]) / h[0] ); //j4
		
		betaValues[3] = ( j[1] / j[0] );
		betaValues[2] = ( (h[2] - (h[1] * betaValues[3])) / h[0] );
		betaValues[1] = ( (e[3] - (e[1] * betaValues[2]) - 
								  (e[2] * betaValues[3])) / e[0] );
		betaValues[0] = ( (a[4] - (a[3] * betaValues[3]) -
								  (a[2] * betaValues[2]) -
								  (a[1] * betaValues[1])) / a[0] );						  
	}
	
	public double[] getBetaValues()
	{
		return betaValues;
	}
	
	public void printBetaValues()
	{
		for( int i = 0; i < 4; i++ )
		{
			System.out.println( "B" + i + ": " + betaValues[i] );
		}
	}
}
