package  
{
	import flash.display.MovieClip;
	import flash.filters.*;
	
	public class Paddle extends MovieClip 
	{
		// Constants:
		private var DEFAULT_WIDTH:int = 10;
		private var DEFAULT_HEIGHT:int = 50;
		private var DEFAULT_SPEED:Number = 15;
		
		// Public Properties:
		// Private Properties:
		private var _dropShadow:DropShadowFilter;
		private var _curWidth:int;
		private var _curHeight:int;
		
		private var _vy;
		// UI Elements:
		

		
		// Initialization:
		public function Paddle():void 
		{
			_curWidth = DEFAULT_WIDTH;
			_curHeight = DEFAULT_HEIGHT;
			
			_vy = 0;
			configUI();
		}

		// Public Methods:
		public function setPos(xPos:int, yPos:int):void
		{
			this.x = xPos;
			this.y = yPos;
		}
		
		public function getXPos():Number
		{
			return this.x;
		}
		
		public function getYPos():Number
		{
			return this.y;
		}
		
		public function getCurrentHeight():int
		{
			return _curHeight;
		}
		
		public function set currentWidth(curWidth:int)
		{
			_curWidth = curWidth;
		}
		
		public function set currentHeight(curHeight:int)
		{
			_curHeight = curHeight;
		}
		
		public function moveUp():void
		{
			_vy = -DEFAULT_SPEED;
		}
		
		public function moveDown():void
		{
			_vy = DEFAULT_SPEED;
		}
		
		public function stopMovement():void
		{
			_vy = 0;
		}
		
		public function getHalfPoint():Number
		{
			return this.y + (_curHeight * 0.5);
		}
		
		public function update():void
		{
			this.y += _vy;
		}
		
		// Protected Methods:
		// Private Methods:
		protected function configUI():void 
		{
			this.graphics.beginFill(0xffffff, 0.9);
			this.graphics.drawRect(this.x, this.y, _curWidth, _curHeight);
			this.graphics.endFill();
			
			_dropShadow = new DropShadowFilter();
			_dropShadow.distance = 5;
			_dropShadow.color = 0x000000;
			_dropShadow.blurX = 2;
			_dropShadow.blurY = 2;
			_dropShadow.quality = 3;
			this.filters = [_dropShadow];
		}
	}
}