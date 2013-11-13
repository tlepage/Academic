package  
{
	import flash.display.MovieClip;
	
	public class Boundary extends MovieClip 
	{
		
		// Constants:
		// Public Properties:
		// Private Properties:
		private var _xTop:int;
		private var _yTop:int;
		private var _xBound:int;
		private var _yBound:int;
		
		// UI Elements:
		

		
		// Initialization:
		public function Boundary(xTop:int, yTop:int, xBound:int, yBound:int) 
		{
			_xTop = xTop;
			_yTop = yTop;
			_xBound = xBound;
			_yBound = yBound;
			
			configUI();
		}

		// Public Methods:
		// Protected Methods:
		// Private Methods:
		protected function configUI():void 
		{ 
			this.graphics.beginFill(0xffffff, 0);
			this.graphics.drawRect(_xTop, _yTop, _xBound, _yBound);
			this.graphics.endFill();
		}
	}
	
}