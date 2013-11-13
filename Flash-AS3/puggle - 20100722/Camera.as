package  
{
	import com.greensock.TweenLite;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Camera extends Sprite
	{
		private const ZOOM_IN_AMT:Number = 3.3;
		
		public function Camera() 
		{
			
		}
		
		public function zoomTo(whatPoint:Point):void
		{
			//this.scaleX = ZOOM_IN_AMT;
			//this.scaleY = ZOOM_IN_AMT;
			
			//this.x = (this.stage.stageWidth / 2) - (whatPoint.x * ZOOM_IN_AMT);
			//this.y = (this.stage.stageHeight / 2) - (whatPoint.y * ZOOM_IN_AMT);
			
			var targetX:int = (this.stage.stageWidth / 2) - (whatPoint.x * ZOOM_IN_AMT);
			var targetY:int = (this.stage.stageHeight / 2) - (whatPoint.y * ZOOM_IN_AMT);
			
			TweenLite.to(this, 1.0, { x:targetX, y:targetY, scaleX:ZOOM_IN_AMT, scaleY:ZOOM_IN_AMT } );
			
		}
		
		public function zoomOut():void
		{
			TweenLite.to(this, 1.0, { x:0, y:0, scaleX:1.0, scaleY:1.0 } );
			
			//this.scaleX = 1.0;
			//this.scaleY = 1.0;
			//this.x = 0;
			//this.y = 0;
			
		}
	}

}