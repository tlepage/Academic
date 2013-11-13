package ui 
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.net.SharedObject;
	import game.PlayerStatistics;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class GameOverScreen
	{
		private var _parent:DisplayObjectContainer;
		private var _display:MovieClip;
		private var _statistics:PlayerStatistics;
		private var _sharedObject:SharedObject;
		
		public function GameOverScreen(parent:DisplayObjectContainer, stats:PlayerStatistics) 
		{
			_parent = parent;
			_statistics = stats;
			
			_sharedObject = SharedObject.getLocal( "bbSave" );

		}
		
		public function setData():void
		{
			try
			{
				if (_sharedObject != null)
				{
					_sharedObject.data.style = _statistics.style;
					_sharedObject.data.lastCheckPoint = _statistics.lastCheckPoint;
					_sharedObject.data.flagsMade = _statistics.flagsMade;
					_sharedObject.data.flagsMissed = _statistics.flagsMissed;
					_sharedObject.data.funPoints = _statistics.funPoints;
					_sharedObject.data.totalDistance = _statistics.totalDistance;
					_sharedObject.data.crashes = _statistics.crashes;

					_sharedObject.flush();
				}
				else
				{
					// display error message
				}
			}
			catch ( sharedObjectError:Error )
			{
				trace( "Caught this error:", sharedObjectError.name, sharedObjectError.message );
			}
		}
	}

}