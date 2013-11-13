package ui 
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Scoreboard
	{
		private var _display:MovieClip;
		private var _parent:DisplayObjectContainer;
		private var _stylePoints:uint;
		private var _distance:uint;
		private var _distanceUntilCheckpoint:int;
		private var _funPoints:uint;
		private var _name:String;
		private var _checkpointCounter:uint;
		private var _flagsMade:uint;
		private var _flagsMissed:uint;
		private var _crashCounter:uint;
		
		public function Scoreboard(parent:DisplayObjectContainer, name:String) 
		{	
			_parent = parent;
			_name = name;
			
			_display = new ScoreboardDisplay();
			_display.x = 400;
			_display.y = 40;
			
			_parent.addChild(_display);
			
			trace("Name: " + _name);
			
			_display.nameText.display.text = _name;
			
			trace(_display.nameText.display.text);
			
			resetAll();
		}
		
		public function resetAll():void
		{
			_stylePoints = 0;
			_distance = 0;
			_distanceUntilCheckpoint = 0;
			_funPoints = 100;
			_checkpointCounter = 0;
			_flagsMade = 0;
			_flagsMissed = 0;
			_crashCounter = 0;
			_display.funMeter.gotoAndStop(3);
			
			updateDisplay();
		}
		
		public function resetUponCheckpoint():void
		{
			_distanceUntilCheckpoint = 0;
			//_flagsMade = 0;
			//_flagsMissed = 0;
			updateDisplay();
		}
		
		public function updateDisplay():void
		{
			_display.checkpointText.display.text = _checkpointCounter;
			_display.distanceText.display.text = _distanceUntilCheckpoint;
			_display.crashesText.display.text = _crashCounter;
			_display.progressText.displayPrimary.text = _flagsMade;
			_display.progressText.displaySecondary.text = _flagsMissed;
			_display.scoreText.display.text = _stylePoints;
			
			if (_funPoints > 0 && _funPoints < 50)
			{
				_display.funMeter.gotoAndStop(1);
			}
			else if (_funPoints > 50 && _funPoints < 75)
			{
				_display.funMeter.gotoAndStop(2);
			}
			else if (_funPoints > 75)
			{
				_display.funMeter.gotoAndStop(3);
			}
		}
		
		public function updateStyle(points:uint)
		{
			_stylePoints += points;
			updateDisplay();
		}
		
		public function updateCheckpointCounter():void
		{
			_checkpointCounter += 1;
			updateDisplay();
		}
		
		public function updateCrashes(amount:int):void
		{
			_crashCounter += amount;
			updateFunPoints( -10);
			if (_crashCounter < 0) _crashCounter = 0;
			updateDisplay();
		}
		
		public function updateFlagsMade():void
		{
			_flagsMade += 1;
			updateFunPoints(10);
			
			updateDisplay();
		}
		
		public function updateFlagsMissed():void
		{
			_flagsMissed += 1;
			
			updateFunPoints(-5);
			
			updateDisplay();
		}
		
		public function updateDistanceUntilCheckpoints(dist:uint):void
		{
			_distanceUntilCheckpoint = dist;
			updateDisplay();
		}
		
		public function updateDistance(dist:uint)
		{
			_distance += dist;
			
			if (_distanceUntilCheckpoint >= 0) 
			{
				_distanceUntilCheckpoint -= dist;
			}
			else
			{
				_distanceUntilCheckpoint = 0;
			}
			
			updateDisplay();
		}
		
		public function updateFunPoints(amount:int):void
		{
			_funPoints += amount;
			
			if (_funPoints <= 0) _funPoints = 0;
			else if (_funPoints >= 100) _funPoints = 100;
		}
		
		public function get checkpointCounter():uint { return _checkpointCounter; }
		
		public function get distanceUntilCheckpoint():uint { return _distanceUntilCheckpoint; }
		
		public function get funPoints():uint { return _funPoints; }
		
		public function get stylePoints():uint { return _stylePoints; }
		
		public function get distance():uint { return _distance; }
		
		public function get flagsMade():uint { return _flagsMade; }
		
		public function get flagsMissed():uint { return _flagsMissed; }
		
		public function get crashCounter():uint { return _crashCounter; }
	}

}