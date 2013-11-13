package  
{
	import com.actionsnippet.qbox.QuickBox2D;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Vals
	{
		// pixels per meter
		public static const RATIO:Number = 30;  
		
		// iterations
		public static const ITERATIONS:int = 12;
		
		// gravity
		public static const GRAVITY:Number = 9.8;
		
		// inverse of the ratio - not as many divides
		public static const INV_RATIO:Number = 1 / RATIO;
		
		// Frames per second (this needs to match what the .fla file has)
		public static const FPS:Number = 30;
		
		// This is a radians to degress conversion
		public static const RAD_TO_DEG:Number = 180 / Math.PI;
		//public static const RAD_TO_DEG:Number = 180 * 0.31830988618379067153776752674503;
		
		// This is a degress to radians conversion
		public static const DEG_TO_RAD:Number = Math.PI / 180;
		//public static const DEG_TO_RAD:Number = Math.PI * 0.0055555555555555555556;
		
		public static const STACKOLANTERN_SCENE:int = 0;
		public static const CREEPY_TREE_SCENE:int = 1;
		public static const FALL_TIME_SCENE:int = 2;
		public static const HAUNTED_HOUSE_SCENE:int = 3;
		public static const CEMETARY_SCENE:int = 4;
		
		// Theme types
		public static const HALLOWEEN_THEME:int = 0;
		public static const BEAR_THEME:int = 1;
		
		public static const CHARACTER_FRICTION:Number = 0.6;
		public static const CHARACTER_DENSITY:Number = 0.3;//0.002;
		public static const CHARACTER_RESTITUTION:Number = 0;
		
		public static const CHARACTER_PER_STACK_MAX:int = 4;
		
		public static const GROUND_X:int = 10;
		public static const GROUND_Y:int = 20;
		public static const GROUND_WIDTH:int = 300;
		public static const GROUND_HEIGHT:int = 1;
		public static const GROUND_DENSITY:int = 0;
		public static const GROUND_RESTITUTION:int = 0;
		
		public static const BOX:int = 0;
		public static const CIRCLE:int = 1;
		public static const POLY:int = 2;
		
		private static var _world:QuickBox2D;
		
		// Characters
		public static const DRACULA:int = 0;
		public static const FRANKIEBABY:int = 1;
		//public static const PUNKIN:int = 2;
		public static const JACKOLANTERN:int = 2;
		public static const MUMMY:int = 3;
		public static const GHOST:int = 4;
		//public static const ZOMBIEBLACK:int = 6;
		public static const BLACKCAT:int = 5;
		public static const WITCH:int = 6;
		public static const SCARECROW:int = 7;
		public static const DEVIL:int = 8;
		public static const ZOMBIE:int = 9;
		public static const JACK2:int = 10;
		public static const JACK3:int = 11;
		public static const JACK4:int = 12;
		public static const CLOWN:int = 13;
		public static const SKULL:int = 14;
		
		public static const HALLOWEEN_CHARACTERS:Array = [	DRACULA, 
															FRANKIEBABY, 
															//PUNKIN, 
															JACKOLANTERN, 
															MUMMY, 
															GHOST, 
															//ZOMBIEBLACK, 
															BLACKCAT, 
															WITCH, 
															SCARECROW, 
															DEVIL, 
															ZOMBIE, 
															JACK2, 
															JACK3, 
															JACK4,
															CLOWN,
															SKULL
														];
															
		public static const BEARBLACK:int = 20;
		public static const BEARBLUE:int = 21;
		public static const BEARBRICK:int = 22;
		public static const BEARBROWN:int = 23;
		public static const BEARBUBBLEGUM:int = 24;
		public static const BEARCYAN:int = 25;
		public static const BEARGREEN:int = 26;
		public static const BEARINDIGO:int = 27;
		public static const BEARLIME:int = 28;
		public static const BEARORANGE:int = 29;
		public static const BEARPINK:int = 30;
		public static const BEARPURPLE:int = 31;
		public static const BEARRED:int = 32;
		public static const BEARVIOLET:int = 33;
		public static const BEARWHITE:int = 34;
		public static const BEARWINTERGREEN:int = 35;
		public static const BEARYELLOW:int = 36;
		
		public static const BEAR_CHARACTERS:Array = [	BEARBLACK, 
														BEARBLUE,
														BEARBRICK,
														BEARBROWN,
														BEARBUBBLEGUM,
														BEARCYAN,
														BEARGREEN,
														BEARINDIGO,
														BEARLIME,
														BEARORANGE,
														BEARPINK,
														BEARPURPLE,
														BEARRED,
														BEARVIOLET,
														BEARWHITE,
														BEARWINTERGREEN,
														BEARYELLOW
														];
														
		
		
		public function Vals()
		{
			
		}
		
		static public function get world():QuickBox2D { return _world; }
		
		static public function set world(value:QuickBox2D):void 
		{
			_world = value;
		}
		
		//*********************************************************************
		// ********* MACROS ********
		//*********************************************************************
		static public function PHYS(val:Number):Number
		{
			return (val * Vals.INV_RATIO);
		}
	}

}