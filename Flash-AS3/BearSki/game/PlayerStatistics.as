package game 
{
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PlayerStatistics
	{
		private var _style:uint;
		private var _lastCheckPoint:uint;
		private var _flagsMade:uint;
		private var _flagsMissed:uint;
		private var _funPoints:uint;
		private var _totalDistance:uint;
		private var _timeLasted:uint;
		private var _crashes:uint;
		
		public function PlayerStatistics() 
		{
			_style = 0;
			_lastCheckPoint = 0;
			_flagsMade = 0;
			_flagsMissed = 0;
			_funPoints = 0;
			_totalDistance = 0;
			_timeLasted = 0;
			_crashes = 0;
		}
		
		public function updateStats(style:uint, lastCheckPoint:uint, flagsMade:uint, flagsMissed:uint, funPoints:uint,
		                            totalDistance:uint, timeLasted:uint, crashes:uint)
		{
			_style = style;
			_lastCheckPoint = lastCheckPoint;
			_flagsMade = flagsMade;
			_flagsMissed = flagsMissed;
			_funPoints = funPoints;
			_totalDistance = totalDistance;
			_timeLasted = timeLasted;
			_crashes = crashes;
		}
		
		public function get style():uint { return _style; }
		
		public function get lastCheckPoint():uint { return _lastCheckPoint; }
		
		public function get flagsMade():uint { return _flagsMade; }
		
		public function get flagsMissed():uint { return _flagsMissed; }
		
		public function get funPoints():uint { return _funPoints; }
		
		public function get totalDistance():uint { return _totalDistance; }
		
		public function get timeLasted():uint { return _timeLasted; }
		
		public function get crashes():uint { return _crashes; }
		
	}

}