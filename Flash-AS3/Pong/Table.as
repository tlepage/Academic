package  
{
	import flash.display.MovieClip;
	
	public class Table extends MovieClip 
	{
		//                       LENGTH                      //
		//---------------------------------------------------// 
		//-------------------------|-------------------------// 
		//-------------------------|-------------------------// W
		//-------------------------|-------------------------// I
		//-------------------------|-------------------------// D
		//-------------------------|-------------------------// T
		//-------------------------|-------------------------// H
		//-------------------------|-------------------------//	
		//---------------------------------------------------//
		//                                                   //
		
		// Constants:
		// Public Properties:
		
		// Private Properties:
		private var _tableLength:int; // the X component on the screen
		private var _tableWidth:int;  // the Y component on the screen
		
		// UI Elements:
		

		
		// Initialization:
		public function Table(tableLength:int, tableWidth:int, color:Number):void
		{
			_tableLength = tableLength;
			_tableWidth = tableWidth;
			
			configUI(color);
		}

		// Public Methods:
		// Getter for table length
		public function getLength():int
		{
			return _tableLength;
		}
		
		// Getter for table width
		public function getWidth():int
		{
			return _tableWidth;
		}
		
		// Setter for table length
		public function set setLength(tableLength:int):void
		{
			if (tableLength > 0)
			{
				_tableLength = tableLength;
			}
		}
		
		// Setter for table width
		public function set setWidth(tableWidth:int):void
		{
			if (tableWidth > 0)
			{
				_tableWidth = tableWidth;
			}
		}
		
		// Protected Methods:
		protected function configUI(color:Number):void 
		{ 
			this.graphics.lineStyle(5, 0xffffff);
			this.graphics.beginFill(color);
			this.graphics.drawRect(0, 0, _tableLength, _tableWidth);
			this.graphics.endFill();
			this.graphics.beginFill(0xffffff);
			this.graphics.moveTo(_tableLength * 0.5, 0);
			this.graphics.lineTo(_tableLength * 0.5, _tableWidth);
			this.graphics.endFill();
		}
		
		// Private Methods:
		
	}
	
}