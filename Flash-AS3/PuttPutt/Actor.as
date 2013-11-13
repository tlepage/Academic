package  
{
	import Box2D.Collision.b2Bound;
	import Box2D.Dynamics.b2Body;
	import flash.display.DisplayObject;
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Actor extends EventDispatcher
	{
		protected var _body:b2Body;
		protected var _display:DisplayObject;
		
		public function Actor(myBody:b2Body, myDisplay:DisplayObject) 
		{
			_body = myBody;
			_body.SetUserData(this);
			_display = myDisplay;
			
			updateDisplay();
		}
		
		public function updateNow():void
		{
			if (!_body.IsStatic())
			{
				updateDisplay();
			}
			childSpecificUpdating();
		}
		
		protected function childSpecificUpdating():void
		{
			// This function does nothing at the parent level
			// In the child, perform some game logic
		}
		
		public function destroy():void
		{
			// Remove the actor from the world
			cleanUpBeforeRemoving();
			
			// Remove event listeners, misc. cleanup
			// Remove the display Sprite from the display
			_display.parent.removeChild(_display);
			
			// Destroy the body
			PhysicsVals.world.DestroyBody(_body);
		}
		
		public function getSpriteLoc():Point
		{
			return new Point(_display.x , _display.y);
		}
		
		private function cleanUpBeforeRemoving():void
		{
			// This function does nothing
			// Expect to be called by children
		}
		
		private function updateDisplay():void
		{
			_display.x = _body.GetPosition().x * PhysicsVals.RATIO;
			_display.y = _body.GetPosition().y * PhysicsVals.RATIO;
			_display.rotation = _body.GetAngle() * 180 / Math.PI;
		}
		
		public function get display():DisplayObject { return _display; }
		
	}

}