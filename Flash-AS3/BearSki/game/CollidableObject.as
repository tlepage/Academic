package game
{
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.events.EventDispatcher;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class CollidableObject
	{
		private var _displayType:uint;
		private var _collisionType:uint;
		
		private var _height:Number;
		private var _width:Number;
		private var _xBound:uint;
		private var _yBound:uint;
		
		private var _pos:Point;
		private var _location:Point;
		private var _isMoving:Boolean;
		private var _xSpeed:uint;
		private var _ySpeed:uint;
		
		private var _display:DisplayObject;
		private var _offScreen:Boolean;
		private var _pastPlayer:Boolean;
		
		private var _collided:Boolean;
		private var _parent:DisplayObjectContainer;
		
		// if object is a ramp type
		private var _rampHeight:Number;
		private var _steepness:Number;
		
		public function CollidableObject(parent:DisplayObjectContainer, displayType:uint, pos:Point, 
		                                 isMoving:Boolean = false, xSpeed:uint = 0, ySpeed:uint = 0 ) 
		{
			_parent = parent;
			_displayType = displayType;
			_pos = pos;
			_location = _pos;
			
			_pastPlayer = false;
			selectDisplay();
			
			_isMoving = isMoving;
			_xSpeed = xSpeed;
			_ySpeed = ySpeed;
			
			_collided = false;
			
			show();
		}
		
		private function show():void
		{
			_parent.addChild(_display);
		}
		
		private function selectDisplay():void
		{
			switch (_displayType)
			{
				case Defines.TREE1:
				{
					_display = new Tree1();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					_xBound = 100;
					_yBound = 100;
					_collisionType = Defines.TREE_TYPE;
					break;
				}
				case Defines.RAMP1:
				{
					_display = new Ramp1();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_rampHeight = 1.5;
					_steepness = 0.5;
					
					_yBound = 100;
					_xBound = 150;
					_collisionType = Defines.RAMP_TYPE;
					break;
				}
				case Defines.FLAG_BLUE:
				{
					_display = new BlueFlag();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_yBound = 75;
					_xBound = 40;
					_collisionType = Defines.FLAG_TYPE;
					break;
				}
				case Defines.FLAG_BLUE_R:
				{
					_display = new BlueFlagR();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_yBound = 75;
					_xBound = 40;
					_collisionType = Defines.FLAG_TYPE;
					break;
				}
				case Defines.FLAG_RED:
				{
					_display = new RedFlag();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_yBound = 75;
					_xBound = 40;
					_collisionType = Defines.FLAG_TYPE;
					break;
				}
				case Defines.FLAG_RED_R:
				{
					_display = new RedFlagR();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_yBound = 75;
					_xBound = 40;
					_collisionType = Defines.FLAG_TYPE;
					break;
				}
				case Defines.SLALOM_CROSS:
				{
					_display = new SlalomCross();
					_display.x = _pos.x;
					_display.y = _pos.y;
					_height = _display.height;
					_width = _display.width;
					
					_yBound = 100;
					_xBound = 200;
					_collisionType = Defines.SLALOM_TYPE;
					break;
				}
			}
		}
		
		public function reset(y:uint):void
		{
			_offScreen = false;
			_pos = new Point(Math.random() * 800, y);
			_display.x = _pos.x;
			_display.y = _pos.y;
			_collided = false;
		}
		
		public function flagReset(y:uint, prevX:int):void
		{
			_offScreen = false;
			
			if (prevX == -1)
			{
				_pos = new Point(Math.random() * 600 + 100, y);
			}
			else
			{
				_pos = new Point(prevX + 100, y);
			}
			
			_display.x = _pos.x;
			_display.y = _pos.y;
			_collided = false;
		}
		
		public function update(speed:uint):void
		{
			if (isMoving)
			{
				speed += _ySpeed;
				_pos.x += _xSpeed;
			}
			
			_pos.y -= speed;
			_display.y = _pos.y;
		}
		
		public function get pos():Point { return _pos; }
		
		public function set pos(value:Point):void 
		{
			_pos = value;
		}
		
		public function get display():Sprite { return _display; }
		
		public function set display(value:Sprite):void 
		{
			_display = value;
		}

		public function get parent():DisplayObjectContainer { return _parent; }
		
		public function set parent(value:DisplayObjectContainer):void 
		{
			_parent = value;
		}
		
		public function get offScreen():Boolean { return _offScreen; }
		
		public function set offScreen(value:Boolean):void 
		{
			_offScreen = value;
		}
		
		public function get displayType():uint { return _displayType; }
		
		public function set displayType(value:uint):void 
		{
			_displayType = value;
		}
		
		public function get collisionType():uint { return _collisionType; }
		
		public function set collisionType(value:uint):void 
		{
			_collisionType = value;
		}
		
		public function get rampHeight():Number { return _rampHeight; }
		
		public function set rampHeight(value:Number):void 
		{
			_rampHeight = value;
		}
		
		public function get steepness():Number { return _steepness; }
		
		public function set steepness(value:Number):void 
		{
			_steepness = value;
		}
		
		public function get xBound():uint { return _xBound; }
		
		public function get yBound():uint { return _yBound; }
		
		public function get pastPlayer():Boolean { return _pastPlayer; }
		
		public function set pastPlayer(value:Boolean):void 
		{
			_pastPlayer = value;
		}
		
		public function get location():Point { return _location; }
		
		public function set location(value:Point):void 
		{
			_location = value;
		}
		
		public function get isMoving():Boolean { return _isMoving; }
		
		public function set isMoving(value:Boolean):void 
		{
			_isMoving = value;
		}
		
		public function get xSpeed():uint { return _xSpeed; }
		
		public function set xSpeed(value:uint):void 
		{
			_xSpeed = value;
		}
		
		public function get ySpeed():uint { return _ySpeed; }
		
		public function set ySpeed(value:uint):void 
		{
			_ySpeed = value;
		}
		
		public function get collided():Boolean { return _collided; }
		
		public function set collided(value:Boolean):void 
		{
			_collided = value;
		}
		
	}

}