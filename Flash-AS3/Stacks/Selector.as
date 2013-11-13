package  
{
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Selector extends MovieClip
	{
		private var _parent:DisplayObjectContainer;
		private var _currentCharacter:Sprite;
		private var _characterType:int;
		
		private var _characters:Array;
		private var _size:int;
		private var _pos:Point;
		private var _count:int;
		
		private var _gameOver:Boolean;
		
		public function Selector(parent:DisplayObjectContainer, size:int, pos:Point, characters:Array) 
		{
			_parent = parent;
			_pos = pos;
			_size = size;
			_characters = characters;
		
			this.x = _pos.x;
			this.y = _pos.y;
			
			_count = _characters.length;
			
			var nextIndex:int = 0;
			var goodIndex:Boolean = false;
			
			_gameOver = false;
			
			do
			{
				nextIndex = _count * Math.random();
				goodIndex = (_characters[nextIndex] != 0);
			}
			while (!goodIndex);

			findCharacter(nextIndex);
		}
		
		public function findCharacter(type:int):void
		{
			switch(type)
			{
				case Vals.DRACULA:
					_currentCharacter = new Dracula();
					break;
				case Vals.BLACKCAT:
					_currentCharacter = new BlackCat();
					break;
				case Vals.DEVIL:
					_currentCharacter = new ElDiablo();
					break;
				case Vals.FRANKIEBABY:
					_currentCharacter = new FrankieBaby();
					break;
				case Vals.GHOST:
					_currentCharacter = new Ghost();
					break;
				case Vals.JACK2:
					_currentCharacter = new Jack2();
					break;
				case Vals.JACK3:
					_currentCharacter = new Jack3();
					break;
				case Vals.JACK4:
					_currentCharacter = new Jack4();
					break;
				case Vals.CLOWN:
					_currentCharacter = new Clown();
					break;
				case Vals.JACKOLANTERN:
					_currentCharacter = new JackOLantern();
					break;
				case Vals.MUMMY:
					_currentCharacter = new Mummy();
					break;
				//case Vals.PUNKIN:
				//	if (_bodyType == Vals.BOX)
				//	{
				//		_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:0.0, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Punkin} );
				//		_parent.addChild(_body.userData);
				//	}
				//	break;
				case Vals.SCARECROW:
					_currentCharacter = new ScaryCrow();
					break;
				case Vals.WITCH:
					_currentCharacter = new Witch();
					break;
				case Vals.ZOMBIE:
					_currentCharacter = new Zombie();
					break;
				case Vals.SKULL:
					_currentCharacter = new Skull();
					break;
				//case Vals.ZOMBIEBLACK:
				//	if (_bodyType == Vals.BOX)
				//	{
				//		_body = Vals.world.addBox( { x:Vals.PHYS(_pos.x), y:Vals.PHYS(_pos.y), width:Vals.PHYS(_dims.x), height:Vals.PHYS(_dims.y), restitution:0.0, friction:Vals.CHARACTER_FRICTION, density:(_isSensor ? 0 : Vals.CHARACTER_DENSITY), isSensor:_isSensor, skin:Zombie} );
				//		_parent.addChild(_body.userData);
				//	}
			}
			
			_characterType = type;
			
			_currentCharacter.x = _pos.x;
			_currentCharacter.y = _pos.y;
			
			//_currentCharacter.scaleX = 0.9;
			//_currentCharacter.scaleY = 0.9;
			
			_parent.addChild(_currentCharacter);
		}
		
		public function loadNextCharacter(curCount:int):void
		{
			var nonZeroCheck:Boolean = false;
			
			for (var i:int = 0; i < _count; i++)
			{
				if (int(_characters[i]) != 0) 
				{
					nonZeroCheck = true;
					break;
				}
			}
			
			if (nonZeroCheck)
			{
				var lastIndex:int = _characterType;
				var nextIndex:int = 0;
				var goodIndex:Boolean = false;
				
				// This will need to check what characters are left
				do
				{
					nextIndex = _count * Math.random();
					goodIndex = (_characters[nextIndex] != 0);
					
					// If indices match, we want to make sure that we don't get the same
					// character selected twice in a row unless it is the only character left on
					// the screen
					if (nextIndex == lastIndex && goodIndex)
					{
						if (_characters[nextIndex] <= Vals.CHARACTER_PER_STACK_MAX && curCount <= Vals.CHARACTER_PER_STACK_MAX)
						{
							goodIndex = true;
						}
						else
						{
							goodIndex = false;
						}
					}
				}
				while (!goodIndex);
				
				//_currentCharacter = null;
				_parent.removeChild(_currentCharacter);
				_currentCharacter = null;
				
				findCharacter(nextIndex);
			}
			else
			{	
				_gameOver = true;
			}
		}
		
		public function removeOneCharacter(type:int):void
		{
			_characters[type] -= 1;
		}
		
		public function get currentCharacter():Sprite { return _currentCharacter; }
		
		public function get characters():Array { return _characters; }
		
		public function get characterType():int { return _characterType; }
		
		public function get gameOver():Boolean { return _gameOver; }
		
	}

}