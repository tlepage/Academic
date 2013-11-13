package game
{
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class KeyHandler
	{
		public static const UP:uint = 0;
		public static const DOWN:uint = 1;
		public static const LEFT:uint = 2;
		public static const RIGHT:uint = 3;
		public static const SPACE:uint = 4;
		
		private static const KEY_COUNT:uint = 5;
		
		private var keys:Array;
		
		public function KeyHandler() 
		{
			// initialize the keys
			keys = [KEY_COUNT];
			
			keys[UP] = false;
			keys[DOWN] = false;
			keys[LEFT] = false;
			keys[RIGHT] = false;
			keys[SPACE] = false;
		}
		
		// Add a key press
		public function addKey(key:uint):void
		{
			if (keys[key] == null) return;
			
			keys[key] = true;
		}
		
		// Remove a key press
		public function removeKey(key:uint):void
		{
			if (!keys[key]) return;
			
			keys[key] = false;
		}
		
		// Find particular key
		public function isKeyPressed(key:uint):Boolean
		{
			if (keys[key] == null)
			{
				return false;
			}
			else 
			{
				return keys[key];
			}
			
		}
		
	}

}