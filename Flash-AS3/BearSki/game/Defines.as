package game
{
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Defines
	{
		public static const MOVE_LEFT:uint = 0;
		public static const MOVE_HARD_LEFT:uint = 1;
		public static const MOVE_RIGHT:uint = 2;
		public static const MOVE_HARD_RIGHT:uint = 3;
		public static const SLOW_DOWN:uint = 4;
		public static const SPEED_UP:uint = 5;
		public static const JUMP:uint = 6;
		public static const SPIN:uint = 7;
		public static const STRAIGHTEN_OUT:uint = 8;
		public static const STOPPED:uint = 9;
		public static const CRASHED:uint = 10;
		public static const FLYING:uint = 11;
		public static const OVER:uint = 12;
		
		public static const BASE_DISTANCE:uint = 1000;
		
		public static const RESTART_PLAYER_Y:uint = 400;
		public static const RESTART_Y:uint = 1200;
		public static const WIDTH:uint = 800;
		public static const OBJ_HORIZON:int = -500;
		
		// GAME DEFINES
		public static const GM_FREESTYLE:uint = 0;
		public static const GM_LEVEL_SPECIFIC:uint = 1;
		
		// LEVEL DEFINES
		public static const GREEN_FLAG:String = "Green Flag";
		public static const BLUE_FLAG:String = "Blue Flag";
		public static const YELLOW_FLAG:String = "Yellow Flag";
		public static const ORANGE_FLAG:String = "Orange Flag";
		public static const RED_FLAG:String = "Red Flag";
		public static const BLACK_FLAG:String = "Black Flag";
		
		
		// TYPES
		public static const TREE_TYPE:uint = 0;
		public static const ROCK_TYPE:uint = 1;
		public static const MOUND_TYPE:uint = 2;
		public static const RAMP_TYPE:uint = 3;
		public static const FLAG_TYPE:uint = 4;
		public static const NPC_TYPE:uint = 5;
		public static const SLALOM_TYPE:uint = 6;
		
		public static const TREE1:uint = 0;
		public static const ROCK:uint = 1;
		public static const SNOW_MOUND:uint = 2;
		public static const RAMP1:uint = 3;
		public static const ANIMAL:uint = 4;
		public static const HUMAN:uint = 5;
		public static const FLAG_BLUE:uint = 6;
		public static const FLAG_BLUE_R:uint = 7;
		public static const FLAG_RED:uint = 8;
		public static const FLAG_RED_R:uint = 9;
		public static const SLALOM_CROSS:uint = 10;
		
		// This is to hold all the types of objects
		public static const ALL_OBJECTS:Array = [TREE1, RAMP1];
		public static const ALL_COUNT:uint = 2;
		
		public static const TREE_TYPES:Array = [TREE1];
		public static const TREE_COUNT:uint = 1;
		
		public static const RAMP_TYPES:Array = [RAMP1];
		public static const RAMP_COUNT:uint = 2;
		
		public static const ROCK_TYPES:Array = [ROCK];
		
		public static const SIDE_SPEED:uint = 10;
		public static const SLOW_SPEED:uint = 5;
		public static const SHARP_SIDE_SPEED:uint = 15;
		public static const STANDARD_SPEED:uint = 20;
		public static const TURN_SPEED_STD:uint = 15;
		public static const HARD_TURN_SPEED_STD:uint = 12;
		
		public static const FAST_SPEED:uint = 35; 
		public static const TURN_SPEED_FAST:uint = 33;
		public static const HARD_TURN_SPEED_FAST:uint = 30;
		
		public static const SLALOM_INTERVAL:uint = 3000;
		
		public function Defines() 
		{
			
		}
		
	}

}