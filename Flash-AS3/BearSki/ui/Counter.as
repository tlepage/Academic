package  
{
	import flash.display.MovieClip;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Counter extends MovieClip
	{
		protected var _currentValue:Number;
		
		public function Counter() 
		{
			reset();
		}
		
		public function addToValue(amountToAdd:Number):void
		{
			_currentValue += amountToAdd;
			updateDisplay();
		}
		
		public function reset():void
		{
			_currentValue = 0;
			updateDisplay();
		}
		
		public function updateDisplay():void
		{
		
		}
		
		public function get currentValue():Number { return _currentValue; }
		
	}

}