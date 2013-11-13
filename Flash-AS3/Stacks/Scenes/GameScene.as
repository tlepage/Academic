package Scenes 
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class GameScene
	{
		protected var _name:String;
		protected var _rows:int;
		protected var _columns:int;
		protected var _theme:int;
		protected var _groundColor:uint;
		protected var _timeLimit:int;
		protected var _hasTimer:Boolean;
		protected var _groundDensity:Number;
		
		protected var _parent:DisplayObjectContainer;
		
		public function GameScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean = false) 
		{
			_parent = parent;
			_name = name;
			_theme = theme;
			_rows = rows;
			_columns = columns;
			_timeLimit = timeLimit;
			_groundColor = groundColor;
			
			_groundDensity = 0;
			
			_hasTimer = hasTimer;
		}
		
		public function init():void
		{
			// override in child
		}
		
		public function loadScene():void
		{
			// override in child
		}
		
		public function loadPhysicalObjects():void
		{
			// override in child
		}
		
		public function updateGraphicalObjects():void
		{
			// override in child
		}
		
		public function stopEvents():void
		{
			// override in child
		}
		
		public function clearScene():void
		{
			// override in child
		}
		
		public function get name():String { return _name; }
		
		public function get rows():int { return _rows; }
		
		public function get columns():int { return _columns; }
		
		public function get theme():int { return _theme; }
		
		public function get groundColor():uint { return _groundColor; }
		
		public function get timeLimit():int { return _timeLimit; }
		
		public function get hasTimer():Boolean { return _hasTimer; }
		
		public function get groundDensity():Number { return _groundDensity; }
	}

}