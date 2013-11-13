package  
{
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Shooter extends Sprite
	{
		private const BALL_OFFSET:Point = new Point(72, 0);  // get this from the sprite
		
		public function Shooter() 
		{
			this.addEventListener(Event.ENTER_FRAME, alignToMouse);
		}
		
		private function alignToMouse(e:Event):void 
		{
			// angle in radians to align to mouse
			var mouseAngle:Number = Math.atan2(this.stage.mouseY - this.y, 
											   this.stage.mouseX - this.x) * 180 / Math.PI;
			this.rotation = mouseAngle;
		}
	
		
		public function getLaunchPosition():Point
		{
			return (localToGlobal(BALL_OFFSET));
		}
	}

}