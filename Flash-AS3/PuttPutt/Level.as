package  
{
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Level extends Sprite
	{
		private var _levelName:String;
		private var _start:Point;
		private var _bounds:Array;
		private var _blocks:Array;
		private var _ramps:Array;
		private var _decor:Array;
		
		private var _target:Actor;
		private var _ball:Actor;
		private var _putter:Actor;
		
		private var _allActors:Array;
		
		public static const TYPE_REC:String = "REC";
		public static const TYPE_CIR:String = "CIR";
		
		public function Level() 
		{
			_allActors = [];
			_bounds = [];
			_blocks = [];
			_ramps = [];
			_decor = [];
		}

		// Takes in bounds of level and generates the ArbitraryStaticActors for them
		public function createBounds(bounds:Array):void
		{
			var numBounds:int = bounds.length;
			
			// 1. Create the ASAs
			for (var i:int = 0; i < numBounds; i += 4 )
			{
				// 0: x loc
				var x:Number = bounds[i];
				
				// 1: y loc
				var y:Number = bounds[i + 1];
				
				// 2: x length
				var xLength:Number = bounds[i + 2];
				
				// 3: y length
				var yLength:Number = bounds[i + 3];
				
				var location:Point = new Point(x, y);
				var points:Array = [[new Point(0, 0), new Point(xLength, 0), new Point(xLength, yLength), new Point(0, yLength)]];
				
				var asa:ArbitraryStaticActor = new ArbitraryStaticActor(this, location, points);
				
				// 2. Store the ASAs in the bounds array
				_bounds.push(asa);
				
				// 3. Store the ASAs in the all actors array
				_allActors.push(asa);
			}
			
			trace("Created bounds...");
		}
		
		// Takes in a location and generates the ASA
		public function createTarget(location:Point):void
		{
			// 1. Create TargetActor at location
			_target = new CupActor(this, location);
			
			// 2. Store the TargetActor in the allActors array
			_allActors.push(_target);
			
			trace("Creating target...");
		}
		
		// Takes in a location, type, color, and dimensions to create an ASA
		public function createBlock(location:Point, type:String, color:uint, dims:Array):void
		{
			// Check the type of the block
			if (type == TYPE_REC)
			{
				var xLength:Number = dims[0];
				var yLength:Number = dims[1];
				
				var points:Array = [[new Point(0, 0), new Point(xLength, 0), new Point(xLength, yLength), new Point(0, yLength)]];
				var asa:ArbitraryStaticActor = new ArbitraryStaticActor(this, location, points, null, true);
				
				// add to blocks array
				_blocks.push(asa);
				
				// add to all actors array
				_allActors.push(asa);
			}
			else if (type == TYPE_CIR)
			{
				var radius:Number = dims[0];
				var ca:CircleActor = new CircleActor(this, location, color, radius);
				
				// add to blocks array
				_blocks.push(ca);
				
				// add to all actors array
				_allActors.push(ca);
			}
			
			// Add to allActors array!!!
			trace("Creating block...");
		}
		
		// Takes in a location, direction, and steepness factor
		public function createRamp(location:Point, direction:Number, steepness:Number):void
		{
			
			// Add to allActors array!!!
			trace("Creating ramp...");
		}
		
		// Takes in a location, radius, and color
		public function createBall(location:Point):void
		{
			trace("Creating ball...");
			_ball = new BallActor(this, location, new Point(-100, 50)); 
			
			// Add to allActors array!!!
			_allActors.push(_ball);	
			//addChild(ballActor);
		}
		
		// Takes in a location and a type
		public function createDecor(location:Point, type:String):void
		{
			
			// Add to allActors array!!!
			trace("Creating decor...");
			
		}
		
		// Getters and Setters
		public function get bounds():Array { return _bounds; }
		
		public function set bounds(value:Array):void 
		{
			_bounds = value;
		}
		
		public function get blocks():Array { return _blocks; }
		
		public function set blocks(value:Array):void 
		{
			_blocks = value;
		}
		
		public function get ramps():Array { return _ramps; }
		
		public function set ramps(value:Array):void 
		{
			_ramps = value;
		}
		
		public function get decor():Array { return _decor; }
		
		public function set decor(value:Array):void 
		{
			_decor = value;
		}
		
		public function get target():Actor { return _target; }
		
		public function set target(value:Actor):void 
		{
			_target = value;
		}
		
		public function get ball():Actor { return _ball; }
		
		public function set ball(value:Actor):void 
		{
			_ball = value;
		}
		
		public function get start():Point { return _start; }
		
		public function set start(value:Point):void 
		{
			_start = value;
		}
		
		public function get allActors():Array { return _allActors; }
		
		public function set allActors(value:Array):void 
		{
			_allActors = value;
		}
		
		public function get levelName():String { return _levelName; }
		
		public function set levelName(value:String):void 
		{
			_levelName = value;
		}
		
	}

}