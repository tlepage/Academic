package  
{
	import flash.display.MovieClip;
	
	public class Particle extends MovieClip 
	{
		
		// Constants:
		// Public Properties:
		public var boundLeft:Number;
		public var boundTop:Number;
		public var boundRight:Number;
		public var boundBottom:Number;
		public var speedX:Number;
		public var speedY:Number;
		public var fadeSpeed:Number;
		
		// Private Properties:
		// UI Elements:
		

		
		// Initialization:
		public function Particle() 
		{
			configUI();
		}

		// Public Methods:
		// Protected Methods:
		// Private Methods:
		protected function configUI():void 
		{ 
		
		}
	}
	
}