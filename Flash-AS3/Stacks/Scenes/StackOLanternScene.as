package Scenes 
{
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.media.Sound;
	import flash.media.SoundMixer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class StackOLanternScene extends GameScene
	{
		private var _pedestal:QuickObject;
		private var _background:MovieClip;
		
		private var _music:Sound;
		
		public function StackOLanternScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean) 
		{
			super(parent, name, theme, rows, columns, timeLimit, groundColor, hasTimer);
		}
		
		override public function init():void
		{
			// do nothing here
			
		}
		
		override public function loadScene():void
		{
			_background = new BackgroundStackOLantern();
			_background.x = 300;
			_background.y = 300;
			
			_background.cacheAsBitmap = true;
			
			_music = new SoundPumpkin();
			_music.play(0, 2);
			
			_parent.addChild(_background);
		}

		override public function loadPhysicalObjects():void
		{
			// Loads a basic pedestal into the level
			loadPedestal();
		
		}
		
		override public function stopEvents():void
		{
			// do nothing here
			SoundMixer.stopAll();
		}
		
		override public function updateGraphicalObjects():void
		{
			// do nothing
		}
		
		override public function clearScene():void
		{
			_pedestal.destroy();
			_parent.removeChild(_background);
		}
		
		private function loadPedestal():void
		{
			_pedestal = Vals.world.addBox   
            (      
               {
                  x      : Vals.PHYS(this._parent.stage.stageWidth * 0.5),
                  y      : Vals.PHYS(568), 
                  width  : Vals.PHYS(370), 
                  height : Vals.PHYS(35),
				  density: 0,
				  fillColor: 0x030000
				  // Need skin for pedestal
               }
            );
		}
	}

}