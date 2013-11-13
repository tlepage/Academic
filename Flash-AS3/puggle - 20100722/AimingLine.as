package  
{
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class AimingLine extends Sprite
	{
		private var _gravityInMeters:Number;
		
		public function AimingLine(gravityInMeters:Number) 
		{
			_gravityInMeters = gravityInMeters;
		}
		
		public function showLine(startPoint:Point, direction:Point, velocityInPixels:Number):void
		{
			// This is going to be the vector that our ball will be traveling in, in pixels
			var velocityVector:Point = direction.clone();
			
			// Our velocity is the correct length, in pixels per second
			velocityVector.normalize(velocityInPixels);
			
			var gravityInPixels = _gravityInMeters * PhysicsVals.RATIO;
			
			var stepPoint:Point = startPoint.clone();
			
			this.graphics.clear();
			this.graphics.lineStyle(12, 0x00FF00, 0.4);
			this.graphics.moveTo(stepPoint.x, stepPoint.y);
			
			// The steps per second that we are going to draw
			// Increase the accuracy by increasing the granularity (calculation hit)
			var granularity:int = 20;
			
			for (var i:int = 0; i < granularity; i++)
			{
				// gravity in pixels per second, divided by granularity (for one second)
				velocityVector.y += gravityInPixels / granularity;
				stepPoint.x += velocityVector.x / granularity;
				stepPoint.y += velocityVector.y / granularity;
				
				this.graphics.lineTo(stepPoint.x, stepPoint.y);
			}
		}
		
		public function hide()
		{
			this.graphics.clear();
		}
		
	}

}