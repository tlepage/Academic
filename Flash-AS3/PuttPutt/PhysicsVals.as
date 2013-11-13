package  
{
	import Box2D.Dynamics.b2World;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PhysicsVals
	{
		// pixels per meter
		public static const RATIO:Number = 40;  
		
		// inverse of the ratio - not as many divides
		public static const INV_RATIO:Number = 1 / RATIO;
		
		// Frames per second (this needs to match what the .fla file has)
		public static const FPS:Number = 30;
		
		// This is a minute adjustment to the velocity
		public static const VELOCITY_ADJ:Number = 0.15;
		
		// This is a radians to degress conversion
		public static const RAD_TO_DEG:Number = 180 / Math.PI;
		
		// This is a degress to radians conversion
		public static const DEG_TO_RAD:Number = Math.PI / 180;
		
		// This is a minimum velocity threshold in meters per second
		public static const MIN_VEL:Number = 0.3;
		
		// This is an offset to aid in the ball falling in the target
		public static const FALL_OFFSET:Number = 0.2;
		
		private static var _world:b2World;
		
		public function PhysicsVals() 
		{
			
		}
		
		static public function get world():b2World 
		{ 
			return _world; 
		}
		
		static public function set world(value:b2World):void 
		{
			_world = value;
		}
		
	}

}