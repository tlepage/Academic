package  
{
	import flash.events.Event;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class LoadCompleteEvent extends Event 
	{
		public static const LOAD_COMPLETE:String = "LoadComplete";
		
		public function LoadCompleteEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 
		{ 
			super(type, bubbles, cancelable);
			
		} 
		
		public override function clone():Event 
		{ 
			return new LoadCompleteEvent(type, bubbles, cancelable);
		} 
		
		public override function toString():String 
		{ 
			return formatToString("LoadCompleteEvent", "type", "bubbles", "cancelable", "eventPhase"); 
		}
		
	}
	
}