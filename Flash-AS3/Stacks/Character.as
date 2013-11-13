package  
{
	import com.actionsnippet.qbox.QuickObject;
	import com.greensock.loading.core.DisplayObjectLoader;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Character extends Sprite
	{
		private var _parent:DisplayObjectContainer;
		private var _type:int;
		private var _charName:String;
		private var _pos:Point;
		private var _body:QuickObject;
		private var _bodyType:int;
		private var _dims:Point;
		private var _sounds:Array; // maybe for future?
		private var _rotationIndex:int;
		private var _isSensor:Boolean;
		private var _graphic:Sprite;
		private var _charAlpha:Number;
		private var _isAlive:Boolean;
		
		public function Character(parent:DisplayObjectContainer, type:int, name:String, pos:Point, bodyType:int, dims:Point, isSensor:Boolean = false) 
		{
			_parent = parent;
			_type = type;
			_charName = name;
			_pos = pos;
			_bodyType = bodyType;
			_dims = dims;
			
			_rotationIndex = 0;
			
			_isSensor = isSensor;
			_isAlive = true;
			
			loadCharacter();
		}
		
		public function loadCharacter():void
		{
			switch(type)
			{
				case Vals.DRACULA:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(60), height:Vals.PHYS(60), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Dracula, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					else if (_bodyType == Vals.CIRCLE)
					{
						_body = Vals.world.addCircle( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), radius:Vals.PHYS(_dims.x), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Dracula, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.BLACKCAT:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(50), height:Vals.PHYS(45), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:BlackCat, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					else if (_bodyType == Vals.CIRCLE)
					{
						_body = Vals.world.addCircle( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), radius:Vals.PHYS(_dims.x), friction:Vals.CHARACTER_FRICTION, restitution:Vals.CHARACTER_RESTITUTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:BlackCat, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.DEVIL:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:ElDiablo, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.FRANKIEBABY:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:FrankieBaby, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.GHOST:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Ghost, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.JACK2:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Jack2, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.JACK3:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Jack3, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.JACK4:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Jack4, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.CLOWN:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Clown, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.JACKOLANTERN:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:JackOLantern, isBullet:true, scaleSkin:false } );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.MUMMY:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Mummy, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				//case Vals.PUNKIN:
				//	if (_bodyType == Vals.BOX)
				//	{
				//		_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Punkin} );
				//		_parent.addChild(_body.userData);
				//	}
				//	break;
				case Vals.SCARECROW:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:ScaryCrow, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.WITCH:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(60), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Witch, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.ZOMBIE:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Zombie, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				//case Vals.ZOMBIEBLACK:
				//	if (_bodyType == Vals.BOX)
				//	{
				//		_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Zombie} );
				//		_parent.addChild(_body.userData);
				//	}
					//break;
				case Vals.SKULL:
					if (_bodyType == Vals.BOX)
					{
						_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:Vals.CHARACTER_RESTITUTION, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Skull, isBullet:true, scaleSkin:false} );
						_parent.addChild(_body.userData);
						_graphic = _body.userData;
						_charAlpha = _graphic.alpha;
					}
					break;
				case Vals.BEARBLACK:
					break;
				case Vals.BEARBLUE:
					break;
				case Vals.BEARBRICK:
					break;
				case Vals.BEARBROWN:
					break;
				case Vals.BEARBUBBLEGUM:
					break;
				case Vals.BEARCYAN:
					break;
				case Vals.BEARGREEN:
					break;
				case Vals.BEARINDIGO:
					break;
				case Vals.BEARLIME:
					break;
				case Vals.BEARORANGE:
					break;
				case Vals.BEARPINK:
					break;
				case Vals.BEARPURPLE:
					break;
				case Vals.BEARRED:
					break;
				case Vals.BEARVIOLET:
					break;
				case Vals.BEARWHITE:
					break;
				case Vals.BEARWINTERGREEN:
					break;
				case Vals.BEARYELLOW:
					break;
			}
		}
	
		public function updateRotation():void
		{
			_body.angle = (90 * _rotationIndex) * Vals.DEG_TO_RAD;
		}
		
		
		public function get type():int { return _type; }
		
		public function set type(value:int):void 
		{
			_type = value;
		}
		
		public function get pos():Point { return _pos; }
		
		public function set pos(value:Point):void 
		{
			_pos = value;
		}
		
		public function get body():QuickObject { return _body; }
		
		public function set body(value:QuickObject):void 
		{
			_body = value;
		}
		
		public function get bodyType():int { return _bodyType; }
		
		public function set bodyType(value:int):void 
		{
			_bodyType = value;
		}
		
		public function get dims():Point { return _dims; }
		
		public function set dims(value:Point):void 
		{
			_dims = value;
		}
		
		public function get rotationIndex():int { return _rotationIndex; }
		
		public function set rotationIndex(value:int):void 
		{
			_rotationIndex = value;
		}
		
		public function get graphic():Sprite { return _graphic; }
		public function set graphic(value:Sprite):void 
		{
			_graphic = value;
		}
		
		public function get charName():String { return _charName; }
		
		public function set charName(value:String):void 
		{
			_charName = value;
		}
		
		public function get charAlpha():Number { return _charAlpha; }
		
		public function set charAlpha(value:Number):void 
		{
			_charAlpha = value;
		}
		
		public function get isAlive():Boolean { return _isAlive; }
		
		public function set isAlive(value:Boolean):void 
		{
			_isAlive = value;
		}
		
		
		
	}

}