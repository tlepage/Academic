package  
{
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.Point;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class LevelLoader extends Sprite
	{
		private var _fileName:String;
		private var _levels:Array;
		private var _lines:Array;
		
		// Constants for file reading
		public static const NODE_LEVEL_BEGIN:String = "#LB";
		public static const NODE_LEVEL_END:String = "#LE";
		public static const NODE_NAME:String = "#NM";
		public static const NODE_START:String = "#ST";
		public static const NODE_BOUND:String = "#BO";
		public static const NODE_RAMP:String = "#RA";
		public static const NODE_BALL:String = "#BA";
		public static const NODE_BLOCK:String = "#BL";
		public static const NODE_TARGET:String = "#TA";
		public static const NODE_DECOR:String = "#DE";
		public static const NODE_SPECIAL:String = "#SP";
		public static const SEP:String = " ";
		public static const CODE_LEN:int = 3;
		public static const EOF:String = "#EF";
		
		public function LevelLoader(fileName:String) 
		{
			// 1. Check to make sure that the file name string is valid
			if (fileName != null && fileName.length > 0)
			{
				_fileName = fileName;
			}
			else
			{
				throw (new Error("Can't create level because file name is invalid!"));
			}
			
			// Initialize the levels array to blank
			_levels = [];
			
			// 2. Load the levels file to generate the levels
			loadLevels();
		}
		
		private function loadLevels():void
		{
			// 1. Load the text file
			var urlLoader:URLLoader = new URLLoader();
			
			try
			{
				urlLoader.load(new URLRequest(fileName));
			}
			catch (errObj:Error)
			{
				throw (new Error("The levels file failed to load!"));
			}
			
			// 2. Fire off event to process the XML file once the reading is complete
			urlLoader.addEventListener(Event.COMPLETE, processFile);
		}
		
		private function processFile(e:Event):void 
		{
			// 1. Get text data
			_lines = String(e.target.data).split(/\n/);
			
			// 2. Go through all the levels
			var lineCount:int = _lines.length;
			var curLevel:Level = null;
			var dataSegments:Array;
			var location:Point;
			
			for (var i:int = 0; i < lineCount; ++i)
			{
				// Reset data segments
				dataSegments = [];
				
				// Reset location
				location = null;
				
				// Get current line and code
				var curLine:String = _lines[i];
				var code:String = curLine.substr(0, CODE_LEN);
				
				// Check to make sure that there is a code
				if (code == null || code.length == 0)
				{
					throw (new Error("Invalid code detected while parsing data on line " + i + "."));
				}
				
				var data:String = curLine.substr(CODE_LEN + 1, curLine.length - 1);
				
				// Check to make sure that there is data present
				if (data == null || data.length == 0 && code != NODE_LEVEL_BEGIN && code != NODE_LEVEL_END && code != EOF)
				{
					throw (new Error("No data found while parsing line " + i + "."));
				}
				
				// If current line is EOF
				if (code == EOF)
				{
					// do nothing here, allow exit out of function
				}
				
				// If this is a level begin node
				if (code == NODE_LEVEL_BEGIN)
				{
					// Create a level object
					curLevel = new Level();
					
					trace("Creating level...");
				}
				else if (code == NODE_LEVEL_END)
				{
					// Push the current level on to the levels array
					_levels.push(curLevel);
					
					// Reset the current level to null
					curLevel = null;
					
					trace("Adding level...");
				}
				else if (code == NODE_NAME)
				{
					// Set the level's name
					if (curLevel != null)
					{
						curLevel.levelName = data;
						trace("Level name is " + data);
					}
					else
					{
						throw (new Error("Error parsing file data in NAME node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_START)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments = data.split(SEP);
						
						// Expecting two data segments for start node
						if (dataSegments.length == 2)
						{
							// Create point from data
							location = new Point(dataSegments[0], dataSegments[1]);
							
							// Set start point in the current level
							curLevel.start = location;
						}
						else
						{
							throw (new Error("Error parsing START data on line " + i + "...invalid parameter count."));
						}
					}
					else
					{
						throw (new Error("Error parsing file data in START node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_TARGET)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments = data.split(SEP);
						
						// Expecting two data segments for target node
						if (dataSegments.length == 2)
						{
							// Create point from data
							location = new Point(dataSegments[0], dataSegments[1]);
							
							// Create target object in level
							curLevel.createTarget(location);
						}
						else
						{
							throw (new Error("Error parsing TARGET data on line " + i + "...invalid parameter count."));
						}
					}
					else
					{
						throw (new Error("Error parsing file data in TARGET node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_BOUND)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments = data.split(SEP);
						
						// Expecting a number that is divisible evenly by four
						if (dataSegments.length % 4 == 0)
						{
							// Create bounds in level
							curLevel.createBounds(dataSegments);
						}
						else
						{
							throw (new Error("Error parsing BOUND data on line " + i + "...invalid parameter count."));
						}
					}
					else
					{
						throw (new Error("Error parsing file data in BOUND node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_BLOCK)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments= data.split(SEP);
						
						// First two segments are location data
						location = new Point(dataSegments[0], dataSegments[1]);
						
						// Third segment is type
						var blockType:String = dataSegments[2];
						
						// Color of object
						var blockColor:uint = dataSegments[3];
						
						var dims:Array = [];
						
						// Check type of block
						if (blockType == "REC")
						{
							if (dataSegments.length == 6)
							{
								// Expect two segments to describe dimensions of the rectangle
								dims.push(dataSegments[4]);
								dims.push(dataSegments[5]);
							}
							else
							{
								throw (new Error("Error parsing file data in BLOCK node for line " + i + ". Invalid parameter count..."));
							}
						}
						else if (blockType == "CIR")
						{
							if (dataSegments.length == 5)
							{
								// Expect one segment for radius
								dims.push(dataSegments[4]);
							}
							else
							{
								throw (new Error("Error parsing file data in BLOCK node for line " + i + ". Invalid parameter count..."));
							}
						}
						
						curLevel.createBlock(location, blockType, blockColor, dims);
					}
					else
					{
						throw (new Error("Error parsing file data in BLOCK node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_RAMP)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments= data.split(SEP);
						
						// Expect 4 parameters for ramps
						if (dataSegments.length == 4)
						{
							// First two segments are location data
							location = new Point(dataSegments[0], dataSegments[1]);
							
							// Third segment is for direction (in degrees)
							var direction:Number = dataSegments[2];
							
							// Fourth segment is for steepness factor
							var steepness:Number = dataSegments[3];
							
							curLevel.createRamp(location, direction, steepness);
						}
						else 
						{
							throw (new Error("Error parsing file data in RAMP node for line " + i + ". Invalid parameter count..."));
						}
					}
					else
					{
						throw (new Error("Error parsing file data in RAMP node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_BALL)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments = data.split(SEP);
						
						// Expect two parameters for ball
						if (dataSegments.length == 2)
						{
							// First two segments are location data
							location = new Point(dataSegments[0], dataSegments[1]);
							
							curLevel.createBall(location);
						}
						else 
						{
							throw (new Error("Error parsing file data in BALL node for line " + i + ". Invalid parameter count..."));
						}
					}
					else
					{
						throw (new Error("Error parsing file data in BALL node for line " + i + ".  Level object is null..."));
					}
				}
				else if (code == NODE_DECOR)
				{
					if (curLevel != null)
					{
						// Get data segments
						dataSegments = data.split(SEP);
						
						// Expect three parameters for decor
						if (dataSegments.length == 3)
						{
							// First two segments are location data
							location= new Point(dataSegments[0], dataSegments[1]);
							
							// Third segment is for type
							var decorType:String = dataSegments[2];
							
							curLevel.createDecor(location, decorType);
						}
					}
					
					else
					{
						throw (new Error("Error parsing file data in DECOR node for line " + i + ".  Level object is null..."));
					}
				}
			}
			
			// Dispatch an event the signals the completion of level loading
			dispatchEvent(new LoadCompleteEvent(LoadCompleteEvent.LOAD_COMPLETE));
		}
		
		public function get fileName():String 
		{ 
			return _fileName; 
		}
		
		public function set fileName(value:String):void 
		{
			_fileName = value;
		}
		
		public function get levels():Array 
		{ 
			return _levels; 
		}
		
		public function set levels(value:Array):void 
		{
			_levels = value;
		}
		
	}

}