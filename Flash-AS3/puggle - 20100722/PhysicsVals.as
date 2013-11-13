package  
{
	import Box2D.Dynamics.b2World;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PhysicsVals
	{
		public static const RATIO:Number = 45;  // pixels per meter
		public static const FPS:Number = 60;
		
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