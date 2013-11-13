package  
{
	import flash.events.Event;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class GameEvent extends Event 
	{
		public static const LEVEL_STOP:String = "LevelStop";
		
		public function GameEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 
		{ 
			super(type, bubbles, cancelable);
			
		} 
		
		public override function clone():Event 
		{ 
			return new GameEvent(type, bubbles, cancelable);
		} 
		
		public override function toString():String 
		{ 
			return formatToString("GameEvent", "type", "bubbles", "cancelable", "eventPhase"); 
		}
		
	}
	
}