package game
{
	import com.greensock.TweenLite;
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.utils.Timer;
	import particles.ParticleSystem;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Player
	{
		private var _parent:DisplayObjectContainer;
		
		private var _bear:MovieClip;
		private var _sled:MovieClip;
		private var _speed:uint;
		private var _xVel:Number;
		
		// Crash variables
		private var _speedBeforeCrashing:Number;
		private var _bearFlyingVel:Point;
		private var _sledFlyingVel:Point;
		private var _bearFlyingRot:Number;
		private var _sledFlyingRot:Number;
		private var _flightTimer:Timer;
		private var _goingUp:Boolean;
		private var _collidingObject:DisplayObject;
		private var _previousIndex:uint;
		
		private var _currentAction:uint;
		
		private var _particleSystem:ParticleSystem;
		
		private var _onGround:Boolean;
		private var _rotating:Boolean;
		
		private var _name:String;
		private var _kName:String;
		
		private var _pos:Point;
		private var _distance:uint;
		
		private var _humanControlled:Boolean;
		
		public function Player(parent:DisplayObjectContainer, bear:MovieClip, sled:MovieClip, name:String, kName:String, humanControlled:Boolean) 
		{
			_parent = parent;
			_bear = bear;
			_sled = sled;
			_name = name;
			_kName = kName;
			_humanControlled = humanControlled;
			
			_currentAction = Defines.STRAIGHTEN_OUT;
			
			_bear.gotoAndStop(2);
			_sled.gotoAndStop(1);
			
			_pos = new Point(0, 0);
			_xVel = 0.0;

			_speed = Defines.STANDARD_SPEED;
			_onGround = true;
			
			_parent.addChildAt(sled, 0);
			_parent.addChildAt(bear, 1);
			
			_particleSystem = new ParticleSystem(_parent);
		}
		
		// Takes in an action and makes the bear react
		public function move(action:uint):void
		{
			_currentAction = action;
			
			switch(action)
			{
				case Defines.MOVE_LEFT: 
				{
					if (_onGround)
					{
						_bear.gotoAndStop(5);
						_sled.gotoAndStop(4);
						_speed = Defines.TURN_SPEED_STD;
					}
					_xVel = -Defines.SIDE_SPEED;
					
					//_onGround = true;
					break;
				}
				case Defines.MOVE_RIGHT: 
				{
					if (_onGround)
					{
						_bear.gotoAndStop(3);
						_sled.gotoAndStop(2);
						//_speed = Defines.TURN_SPEED_STD;
					}
					
					_xVel = Defines.SIDE_SPEED;
					
					//_onGround = true;
					break;
				}
				case Defines.STRAIGHTEN_OUT:
				{
					_bear.gotoAndStop(2);
					_sled.gotoAndStop(1);
					
					_onGround = true;
					_bear.rotation = 0;
					_sled.rotation = 0;
					
					_speed = Defines.STANDARD_SPEED;
					break;
				}
				case Defines.MOVE_HARD_LEFT: 
				{
					if (_onGround)
					{
						_bear.gotoAndStop(6);
						_sled.gotoAndStop(5);
					
						_xVel = -Defines.SHARP_SIDE_SPEED;
						//_speed = Defines.HARD_TURN_SPEED_STD;
					}
					//_onGround = true;
					break;
				}
				case Defines.MOVE_HARD_RIGHT:
				{
					if (_onGround)
					{
						_bear.gotoAndStop(4);
						_sled.gotoAndStop(3);
					
						_xVel = Defines.SHARP_SIDE_SPEED;
						//_speed = Defines.HARD_TURN_SPEED_STD;
					}
					//_onGround = true;
					break;
				}
				case Defines.JUMP:
				{
					_onGround = false;
					
					_bear.gotoAndStop(7);
					_sled.gotoAndStop(1);
					
					_parent.setChildIndex(_bear, _parent.numChildren - 1);
					_parent.setChildIndex(_sled, _parent.getChildIndex(_bear) - 1);
					break;
				}
				case Defines.SPEED_UP:
				{
					if (_onGround)
					{
						_bear.gotoAndStop(8);
						_sled.gotoAndStop(1);
					
						_speed = Defines.FAST_SPEED;
					}
					//_onGround = true;
					break;
				}
				case Defines.SLOW_DOWN: 
				{
					_speed = Defines.SLOW_SPEED
					_bear.gotoAndStop(9);
					break;
				}
				case Defines.SPIN: 
				{
					spin();
					break;
				}
				case Defines.STOPPED:
				{
					_bear.gotoAndStop(2);
					_sled.gotoAndStop(1);
					
					_speed = 0;
					_onGround = true;
					break;
				}
				case Defines.CRASHED:
				{
					_speedBeforeCrashing = _speed;
					_speed = 0;
						
					_parent.setChildIndex(_bear, _parent.numChildren - 1);
					
					// change animation of bear
					_bear.gotoAndStop(9);
					
					// launch the bear and sled in different directions
					_currentAction = Defines.FLYING;
					launchBearAndSled();
						
					// may zoom camera here
					
					
					break;
				}
				case Defines.OVER:
				{
					_speed = 0;
					_bear.gotoAndStop(7);
					_sled.gotoAndStop(1);
					break;
				}
			}
		}
		
		private function spin():void
		{
			// hold down the spacebar to keep spinning
			if (!_onGround)
			{
				_bear.rotation += 15;
				_sled.rotation += 15;
			}
		}
		
		private function launchBearAndSled():void
		{
			_bearFlyingVel = new Point(-_xVel * (_speedBeforeCrashing * 0.25) * Math.random(), _speedBeforeCrashing * 0.5);
			_sledFlyingVel = new Point(_xVel * (_speedBeforeCrashing * 0.35) * Math.random(), _speedBeforeCrashing * 0.5);
			
			_bearFlyingRot = (Math.random() * 25);
			_sledFlyingRot = (Math.random() * 25);
			
			_xVel = 0;
		
			_flightTimer = new Timer(1000, 1);
			_flightTimer.addEventListener(TimerEvent.TIMER_COMPLETE, onFinishedFlying);
			_flightTimer.start();
			
			_goingUp = true;
		}
		
		private function onFinishedFlying(e:TimerEvent):void 
		{
			_flightTimer.removeEventListener(TimerEvent.TIMER_COMPLETE, onFinishedFlying);
			
			_bearFlyingVel = new Point(0, 0);
			_bearFlyingRot = 0;
			_sledFlyingVel = new Point(0, 0);
			_sledFlyingRot = 0;
			
			crashReset();
			
			_currentAction = Defines.STOPPED;
			
			move(Defines.STANDARD_SPEED);
		}
		
		// Makes the bear jump
		public function jump(height:Number, duration:Number):void
		{
			TweenLite.to(_bear, duration, { scaleX:height, scaleY:height, onComplete: bearFall } );
			TweenLite.to(_sled, duration, { scaleX:height, scaleY:height, onComplete: sledFall } );
		}
		
		private function sledFall():void
		{
			TweenLite.to(_sled, 0.25, { scaleX:1.0, scaleY:1.0, onComplete: sledGrounded } );
		}
		
		private function sledGrounded():void
		{
			_onGround = true;
		}
		
		private function bearFall():void
		{
			TweenLite.to(_bear, 0.5, { scaleX:1.0, scaleY:1.0, onComplete: bearGrounded } );
		}
		
		private function bearGrounded():void
		{
			_parent.setChildIndex(_sled, 0);
			_parent.setChildIndex(_bear, _parent.getChildIndex(_sled) + 1);
			_previousIndex = 0;
		}
		
		// Update the character
		public function update():void
		{
			if (_currentAction == Defines.STRAIGHTEN_OUT || _currentAction == Defines.SPEED_UP || _currentAction == Defines.SLOW_DOWN)
			{
				if (_xVel != 0)
				{
					if (_xVel > 0)
					{
						_xVel -= 0.25;
					}
					else
					{
						_xVel += 0.25;
					}
				}
			}
			
			if (_currentAction == Defines.STOPPED)
			{
				_xVel = 0;
				_speed = 0;
			}
			
			
			if (_currentAction != Defines.JUMP && _onGround && _currentAction != Defines.STOPPED && _currentAction != Defines.CRASHED &&
			    _currentAction != Defines.FLYING)
			{
				_particleSystem.createParticle(_pos.x, _pos.y - 45);
			}

			if (_currentAction == Defines.JUMP)
			{
				var index:uint = _parent.getChildIndex(_bear);
				if (index != 0)
				{
					_previousIndex = index;
					_parent.setChildIndex(_bear, _parent.numChildren - 1);
					_parent.setChildIndex(_sled, _parent.numChildren - 2);
					_onGround = false;
				}
				
			}
			
			if (_currentAction == Defines.FLYING)
			{
				var bearIndex:uint = _parent.getChildIndex(_bear);
				_previousIndex = bearIndex;
				
				if (bearIndex != 0)
				{
					_parent.setChildIndex(_bear, _parent.numChildren - 1);
				}
				
				_bear.x += _bearFlyingVel.x;
				_bear.y += _bearFlyingVel.y;
				_sled.x += _sledFlyingVel.x;
				_sled.y += _sledFlyingVel.y;
					
				_bear.rotation += _bearFlyingRot;
				_sled.rotation += _sledFlyingRot;
				
				if (_bear.scaleX <= 2.0 && _bear.scaleX >= 1.0 && _goingUp)
				{
					_bear.scaleX += 0.05;
					_sled.scaleX += 0.05;
					_bear.scaleY += 0.05;
					_sled.scaleY += 0.05;
				}
				else
				{
					_goingUp = false;
					
					_bear.scaleX -= 0.05;
					_sled.scaleX -= 0.05;
					_bear.scaleY -= 0.05;
					_sled.scaleY -= 0.05;
				}
			}
			else
			{
				_pos.x += _xVel;

				_bear.x += _xVel;
				_sled.x += _xVel;
			}
			
			_particleSystem.updateParticle();
		}
		
		public function crashReset():void
		{
			// this start point will be different for a CPU controlled character
			
			findSpotToRestart();
			
			_bear.x = _pos.x;
			_bear.y = _pos.y;
			_sled.x = _pos.x;
			_sled.y = _pos.y;
			
			_bear.gotoAndStop(2);
			_sled.gotoAndStop(1);
			_bear.rotation = 0;
			_sled.rotation = 0;
			_bear.scaleX = 1;
			_sled.scaleX = 1;
			_bear.scaleY = 1;
			_sled.scaleY = 1;
			
			_parent.setChildIndex(_sled, 0);
			_parent.setChildIndex(_bear, _parent.getChildIndex(_sled) + 1);
			_collidingObject = null;
			_previousIndex = 0;
		}
		
		private function findSpotToRestart():void
		{
			var goodSpot:Boolean = false;
			
			while (!goodSpot)
			{
				if (_pos.x < (_sled.width * 0.5))
				{
					_pos = new Point(200, Defines.RESTART_PLAYER_Y);
					goodSpot = true;
				}
				else if (_pos.x > 800 - (_sled.width * 0.5))
				{
					_pos = new Point(600, Defines.RESTART_PLAYER_Y);
					goodSpot = true;
				}
				else
				{
					_pos = new Point (_collidingObject.x + (_collidingObject.width * 0.5) + 50, Defines.RESTART_PLAYER_Y);
					
					if (_pos.x > 0 || _pos.x < 800)
					{
						goodSpot = true;
					}
					
				}
			}
		}
		
		// GETTERS AND SETTERS /////////////////////////////////////////////////////////
				
		public function get parent():DisplayObjectContainer { return _parent; }
		
		public function get bear():MovieClip { return _bear; }
		
		public function get sled():MovieClip { return _sled; }
		
		public function get name():String { return _name; }
		
		public function get kName():String { return _kName; }
		
		public function get pos():Point { return _pos; }
		
		public function set pos(value:Point):void 
		{
			_pos = value;
			_bear.x = _pos.x;
			_bear.y = _pos.y;
			
			_sled.x = _pos.x;
			_sled.y = _pos.y;
		}
		
		public function get currentAction():uint { return _currentAction; }
		
		public function get onGround():Boolean { return _onGround; }
		
		public function get speed():uint { return _speed; }
		
		public function set speed(value:uint):void 
		{
			_speed = value;
		}
		
		public function get collidingObject():DisplayObject { return _collidingObject; }
		
		public function set collidingObject(value:DisplayObject):void 
		{
			_collidingObject = value;
		}

		
	}

}