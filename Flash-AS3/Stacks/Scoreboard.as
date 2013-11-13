package  
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.text.Font;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.TextFormatAlign;
	import flash.utils.Timer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Scoreboard extends MovieClip
	{
		private var _timer:Timer;
		private var _timerDisplay:TextField;
		private var _score:int;
		private var _scoreDisplay:TextField;
		private var _start:int;
		private var _parent:DisplayObjectContainer;
		private var _pos:Point;
		private var _timeLeft:int;
		
		private var _gameOver:Boolean;
		private var _chillerFont:Font;
		
		private var _textColor:uint;
		private var _modifier:Number;
		
		public function Scoreboard(parent:DisplayObjectContainer, width:int, height:int, pos:Point, start:int) 
		{
			_pos = pos;
			_parent = parent;
			_start = start;
			_score = 0;
			
			_gameOver = false;
			
			_textColor = 0xffffff;
			
			_modifier = 2;
			
			this.width = width;
			this.height = height;
			
			initScoreboard();
		}
		
		private function initScoreboard():void
		{
			this.x = _pos.x;
			this.y = _pos.y;
			
			_chillerFont = new ChillerFont();
			
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 48;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = _chillerFont.fontName;
			
			_timer = new Timer(1000);
			_timer.addEventListener(TimerEvent.TIMER, onTimerTick);
			_timerDisplay = new TextField();
			_timerDisplay.mouseEnabled = false;
			_timerDisplay.defaultTextFormat = textFormat;
			_timerDisplay.x = this.x - 30;
			_timerDisplay.y = this.y + 20;
			_timerDisplay.textColor = 0xff0002;
			_timerDisplay.text = String(_start); // this needs to be modified to handle minutes/seconds
			
			_parent.addChild(_timerDisplay);
			
			_scoreDisplay = new TextField();
			_scoreDisplay.mouseEnabled = false;
			_scoreDisplay.defaultTextFormat = textFormat;
			_scoreDisplay.width = 250;
			_scoreDisplay.textColor = _textColor;
			_scoreDisplay.x = this.x - 300;
			_scoreDisplay.y = this.y + 20;
			_scoreDisplay.text = "Score: " + _score;
			_parent.addChild(_scoreDisplay);
			
			
			_timer.start();
		}
		
		private function onTimerTick(e:TimerEvent):void 
		{
			_timeLeft = _start - _timer.currentCount;

			if (_timeLeft == 0)
			{
				_timer.removeEventListener(TimerEvent.TIMER, onTimerTick);
				_gameOver = true;
			}
			_timerDisplay.text = String(_timeLeft);
		}
		
		public function updateScore(direction:int):void
		{
			if (_timeLeft >= _start * 0.5)
			{
				_modifier = 1.75
				
				if (_start == 60)
				{
					_modifier = 2.5;
				}
			}
			else if (_timeLeft <= _start * 0.5)
			{
				_modifier = 1.5;
				
				if (_start == 60)
				{
					_modifier = 1.75;
				}
			}
			
			if (direction > 0)
				_score += (17 * _modifier * _timeLeft);
			else
				_score -= _start + (17 * _modifier * _timeLeft);
				
			_scoreDisplay.text = "Score: " + _score;
		}
		
		public function hold():void
		{
			_timer.stop();
		}
		
		public function go():void
		{
			_timer.start();
		}
		
		public function stopTimer():void
		{
			_timer.stop();
			_timer.removeEventListener(TimerEvent.TIMER, onTimerTick);
			
			_timerDisplay.text = String(_timeLeft);
		}
		
		public function get timer():Timer { return _timer; }
		
		public function set timer(value:Timer):void 
		{
			_timer = value;
		}
		
		public function get score():int { return _score; }
		
		public function set score(value:int):void 
		{
			_score = value;
		}
		
		public function get scoreDisplay():TextField { return _scoreDisplay; }
		
		public function set scoreDisplay(value:TextField):void 
		{
			_scoreDisplay = value;
		}
		
		public function get gameOver():Boolean { return _gameOver; }
		
		public function get timeLeft():int { return _timeLeft; }
		
		public function get textColor():uint { return _textColor; }
		
		public function set textColor(value:uint):void 
		{
			_textColor = value;
		}
		
		public function get modifier():Number { return _modifier; }
		
		public function set modifier(value:Number):void 
		{
			_modifier = value;
		}
		
		public function get timerDisplay():TextField { return _timerDisplay; }
		
		
	}

}