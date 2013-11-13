package  
{
	import flash.display.MovieClip;
	import flash.filters.*;
	
	public class Ball extends MovieClip 
	{
		// Constants:
		private var DEFAULT_RADIUS:int = 10;
		private var DEFAULT_SPEED:Number = 10;
		
		// Public Properties:
		
		// Private Properties:
		private var _dropShadow:DropShadowFilter;
		private var _radius:int;
		private var _vx:Number;
		private var _vy:Number;
		
		// UI Elements:
		
		
		// Initialization:
		public function Ball(color:Number) 
		{
			_radius = DEFAULT_RADIUS;
			configUI(color);
		}

		// Public Methods:
		public function setPos(xPos:int, yPos:int):void
		{
			this.x = xPos;
			this.y = yPos;
			_vx = 0;
			_vy = 0;
		}
		
		public function getXPos():Number
		{
			return this.x;
		}
		
		public function getYPos():Number
		{
			return this.y;
		}
		
		public function set currentRadius(radius:int):void
		{
			_radius = radius;
		}
		
		public function update():void
		{
			this.x += _vx;
			this.y += _vy;
		}
		
		public function setVx(vx:Number):void
		{
			_vx = vx;
		}
		
		public function setVy(vy:Number):void
		{
			_vy = vy;
		}
		
		public function getLeftBound():Number
		{
			return this.x - (_radius * 0.5);
		}
		
		public function getRightBound():Number
		{
			return this.x + (_radius * 0.5);
		}
		
		public function getTopBound():Number
		{
			return this.y - (_radius * 0.5);
		}
		
		public function getBottomBound():Number
		{
			return this.y + (_radius * 0.5);
		}
		
		public function collideX():void
		{
			_vx *= -1;
		}
		
		public function collideY():void
		{
			_vy *= -1;
		}
		
		public function hitStart():void
		{
			var ran:Number = Math.random();
			var mult:int;
			
			if (ran > 0.5)
			{
				mult = 1;
			}
			else
			{
				mult = -1;
			}
			
			_vx = mult * DEFAULT_SPEED;
			_vy = mult * DEFAULT_SPEED;
		}
		
		// Protected Methods:
		protected function configUI(color:Number):void 
		{
			//this.graphics.lineStyle(0, 0x0000ff);
			this.graphics.beginFill(color);
			this.graphics.drawCircle(this.x, this.y, _radius);
			this.graphics.endFill();
		 	/*
			_dropShadow = new DropShadowFilter();
			_dropShadow.distance = 5;
			_dropShadow.color = 0x000000;
			_dropShadow.blurX = 2;
			_dropShadow.blurY = 2;
			_dropShadow.quality = 3;
			this.filters = [_dropShadow];*/
		}
		
		// Private Methods:
		
	}
}