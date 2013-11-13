package  
{
	import com.greensock.TweenLite;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class TimeMaster
	{
		private var _frameRate:int;
		
		public function TimeMaster() 
		{
			_frameRate = Vals.FPS;
		}
		
		public function getTimeStep():Number
		{
			return (1.0 / _frameRate);
		}
		
		public function slowDown():void
		{
			TweenLite.to(this, 0.5, { _frameRate:Vals.FPS * 5 } );
			//_frameRate = PhysicsVals.FPS * 5;
		}
		
		public function backToNormal():void
		{
			TweenLite.to(this, 0.5, { _frameRate:Vals.FPS } );
			//_frameRate = PhysicsVals.FPS;
		}
		
		public function get frameRate():int { return _frameRate; }
		
		public function set frameRate(value:int):void 
		{
			_frameRate = value;
		}
		
	}

}