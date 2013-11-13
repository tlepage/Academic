package  
{
	public class Player 
	{
		// Constants:
		private var DEFAULT_NAME:String = "Anonymous";

		// Public Properties:
		
		// Private Properties:
		private var _name:String;
		private var _side:int;
		private var _type:int;
		private var _difficulty:int;
		
		// Initialization:
		public function Player(name:String, side:int, type:int, difficulty:int) 
		{
			if(name != null && name.length > 0)
			{
				_name = name;
			}
			else
			{
				_name = DEFAULT_NAME + side;
			}
			
			_side = side;
			_type = type;
			_difficulty = difficulty;
		}
	
		// Public Methods:
		public function getPlayerName():String
		{
			return _name;
		}
		
		public function getPlayerSide():int
		{
			return _side;
		}
		
		public function getPlayerType():int
		{
			return _type;
		}
		
		public function getPlayerDifficulty():int
		{
			return _difficulty;
		}
		
		// Protected Methods:
	}
}