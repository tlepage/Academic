/*
 * Created on Nov 19, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

public class SearchDriver
{
	final int PART = 0;
	final int VEHICLE_MODEL = 1;
	final int VEHICLE_MAKE = 2;
	final int VEHICLE_YEAR = 3;
	final int VEHICLE_ENGINE = 4;
	
	public SearchDriver( String searchString, int choice )
	{
		String partName = "SELECT * FROM " +			"PART WHERE name LIKE '%" + searchString + "%'";
		String vehicleMake = "SELECT * FROM VEHICLE " +			"WHERE make LIKE '%" + searchString + "%'";
		String vehicleYear = "SELECT * FROM VEHICLE " +
			"WHERE year LIKE '%" + searchString + "%'";
		String vehicleModel = "SELECT * FROM VEHICLE " +
			"WHERE model LIKE '%" + searchString + "%'";
		String vehicleEngine = "SELECT * FROM VEHICLE " +
			"WHERE engine LIKE '%" + searchString + "%'"; 
	
		if( choice == PART )
		{
			new SearchResults( partName );
		}
		else if( choice == VEHICLE_MODEL )
		{
			new SearchResults( vehicleModel );
		}
		else if( choice == VEHICLE_MAKE )
		{
			new SearchResults( vehicleMake );
		}
		else if( choice == VEHICLE_YEAR )
		{
			new SearchResults( vehicleYear );
		}
		else if( choice == VEHICLE_ENGINE )
		{
			new SearchResults( vehicleEngine );
		}
			
	}
		
}
