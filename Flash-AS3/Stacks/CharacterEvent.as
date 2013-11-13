package  
{
	import flash.events.Event;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class CharacterEvent extends Event 
	{
		public static const KILL_CHARACTER:String = "KillCharacter";
		
		public function CharacterEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 
		{ 
			super(type, bubbles, cancelable);
			
		} 
		
		public override function clone():Event 
		{ 
			return new CharacterEvent(type, bubbles, cancelable);
		} 
		
		public override function toString():String 
		{ 
			return formatToString("CharacterEvent", "type", "bubbles", "cancelable", "eventPhase"); 
		}
		
	}
	
}