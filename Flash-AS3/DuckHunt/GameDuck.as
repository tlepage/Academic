package  
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.utils.Timer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class GameDuck
	{
		private var _parent:DisplayObjectContainer;
		
		private var _display:MovieClip;
		private var _direction:Boolean;
		private var _xSpeed:int;
		private var _ySpeed:int;
		private var _pos:Point;
		
		private var _scale:Number;
		
		private var _hit:Boolean;
		private var _timer:Timer;
		
		private var _dead:Boolean;
		
		public function GameDuck(parent:DisplayObjectContainer, pos:Point, direction:Boolean, xSpeed:Number, ySpeed:Number, scale:Number) 
		{
			_parent = parent;
			_display = new Duck();
			_pos = pos;
			_scale = scale;
			_display.x = _pos.x;
			_display.y = _pos.y;
			_display.scaleX = _scale;
			_display.scaleY = _scale;
			
			_parent.addChildAt(_display, 1);
			_direction = direction;
			
			if (_direction)
			{
				_display.gotoAndStop(1)
			}
			else
			{
				_display.gotoAndStop(4);
			}
			
			_xSpeed = xSpeed;
			_ySpeed = ySpeed;
			
			_hit = false;
			
			_timer = new Timer(250);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			
			_timer.start();
			_dead = false;
			
		}
		
		private function onTick(e:TimerEvent):void 
		{
			if (_direction)
			{
				if (_display.currentFrame == 1)
				{
					_display.gotoAndStop(2);
				}
				else if (_display.currentFrame == 2)
				{
					_display.gotoAndStop(1);
				}
			}
			else
			{
				if (_display.currentFrame == 4)
				{
					_display.gotoAndStop(5);
				}
				else if (_display.currentFrame == 5)
				{
					_display.gotoAndStop(4);
				}
			}
		}
		
		public function update()
		{
			if (_pos.x > 1300 || _pos.x < -400 || 
			    _pos.y > 1000 || _pos.y < -200)
			{
				_dead = true;
			}
			else
			{
				if (_hit == true)
				{
					if (_direction)
						_display.gotoAndStop(3);
					else
						_display.gotoAndStop(6);
					
					_xSpeed *= 0.5;
					_ySpeed = 13;
					
					//_dead = true;
					
					_timer.stop();
				}
				
				_pos.x += _xSpeed;
				_pos.y += _ySpeed;
				
				_display.x = _pos.x;
				_display.y = _pos.y;
			}
		}
		
		public function set hit(value:Boolean):void 
		{
			_hit = value;
		}
		
		public function get hit():Boolean { return _hit; }
		
		public function get dead():Boolean { return _dead; }
		
		public function set dead(value:Boolean):void 
		{
			_dead = value;
		}
		
		public function get display():MovieClip { return _display; }
		
	}

}