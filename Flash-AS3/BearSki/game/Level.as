package game 
{
	import flash.display.DisplayObjectContainer;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Level
	{
		private var _parent:DisplayObjectContainer;
		private var _name:String;
		private var _length:uint;
		private var _objects:Vector.<Vector.<CollidableObject>>;
		private var _difficulty:String;
		private var _isSlalom:Boolean;
		private var _numBatches:uint;
		private var _currentBatch:uint;
		private var _outOfBatches:Boolean;
		
		public function Level(parent:DisplayObjectContainer, name:String, length:uint, difficulty:String, isSlalom:Boolean, numBatches:uint) 
		{
			_parent = parent;
			_name = name;
			_length = length;
			_difficulty = difficulty;
			_isSlalom = isSlalom;
			_numBatches = numBatches;
			_currentBatch = 0;
			
			_outOfBatches = false;
			
			var batch1:Vector.<CollidableObject> = new Vector.<CollidableObject>();
			batch1.push(new CollidableObject(_parent, 3, new Point(200, 1850)), new CollidableObject(_parent, 6, new Point(400, 2050)),
			            new CollidableObject(_parent, 6, new Point(350, 2350)), new CollidableObject(_parent, Defines.FLAG_RED, new Point(700, 3550)),
						new CollidableObject(_parent, 6, new Point(350, 3350)), new CollidableObject(_parent, 6, new Point(700, 4050)),
						new CollidableObject(_parent, 7, new Point(350, 4350)), new CollidableObject(_parent, 9, new Point(700, 5050)),
						new CollidableObject(_parent, 6, new Point(350, 5350)), new CollidableObject(_parent, 6, new Point(700, 6050)),
						new CollidableObject(_parent, 6, new Point(350, 6350)), new CollidableObject(_parent, 6, new Point(700, 7050)),
						new CollidableObject(_parent, 0, new Point(350, 7350)), new CollidableObject(_parent, 3, new Point(700, 8050)),
						new CollidableObject(_parent, 0, new Point(350, 8350)), new CollidableObject(_parent, 0, new Point(700, 9050)),
						new CollidableObject(_parent, 0, new Point(350, 9350)), new CollidableObject(_parent, 0, new Point(700, 10050)),
						new CollidableObject(_parent, 3, new Point(350, 10350)), new CollidableObject(_parent, 0, new Point(700, 11050)));
			
			var batch2:Vector.<CollidableObject> = new Vector.<CollidableObject>();
			batch2.push(new CollidableObject(_parent, 0, new Point(200, 11850)), new CollidableObject(_parent, 0, new Point(400, 12050)),
			            new CollidableObject(_parent, 0, new Point(350, 12350)), new CollidableObject(_parent, 0, new Point(700, 13550)),
						new CollidableObject(_parent, 0, new Point(350, 13350)), new CollidableObject(_parent, 0, new Point(700, 14050)),
						new CollidableObject(_parent, 0, new Point(350, 14350)), new CollidableObject(_parent, 0, new Point(700, 15050)),
						new CollidableObject(_parent, 0, new Point(350, 15350)), new CollidableObject(_parent, 0, new Point(700, 16050)),
						new CollidableObject(_parent, 0, new Point(350, 16350)), new CollidableObject(_parent, 0, new Point(700, 17050)),
						new CollidableObject(_parent, 0, new Point(350, 17350)), new CollidableObject(_parent, 0, new Point(700, 18050)),
						new CollidableObject(_parent, 0, new Point(350, 18350)), new CollidableObject(_parent, 0, new Point(700, 19050)),
						new CollidableObject(_parent, 0, new Point(350, 19350)), new CollidableObject(_parent, 0, new Point(700, 20050)),
						new CollidableObject(_parent, 0, new Point(350, 21350)), new CollidableObject(_parent, 0, new Point(700, 21050)));
			
			_objects = new Vector.<Vector.<CollidableObject>>();
			_objects.push(batch1, batch2);
		}
		
		
		public function loadBatch():Vector.<CollidableObject>
		{
			var current:uint = _currentBatch;
			_currentBatch++;
			
			//trace("Batched: " + current);
			
			if (_currentBatch == _numBatches)
			{
				_outOfBatches = true;
			}
			
			return (_objects[current] as Vector.<CollidableObject>);
		}
		
		
		
		
		public function get objects():Array { return _objects; }
		
		public function set objects(value:Array):void 
		{
			_objects = value;
		}
		
		public function get name():String { return _name; }
		
		public function set name(value:String):void 
		{
			_name = value;
		}
		
		public function get length():uint { return _length; }
		
		public function set length(value:uint):void 
		{
			_length = value;
		}
		
		public function get difficulty():String { return _difficulty; }
		
		public function set difficulty(value:String):void 
		{
			_difficulty = value;
		}
		
		public function get isSlalom():Boolean { return _isSlalom; }
		
		public function set isSlalom(value:Boolean):void 
		{
			_isSlalom = value;
		}
		
		public function get outOfBatches():Boolean { return _outOfBatches; }
		
	}

}