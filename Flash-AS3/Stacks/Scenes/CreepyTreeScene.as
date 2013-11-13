package Scenes 
{
	import Box2D.Common.Math.b2Vec2;
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.events.TimerEvent;
	import flash.media.Sound;
	import flash.media.SoundMixer;
	import flash.utils.Timer;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class CreepyTreeScene extends GameScene
	{
		private var _pedestal:QuickObject;
		private var _timer:Timer;
		private var _count:Number;
		
		private var _shakeImpulse:b2Vec2;
		private var _thunder:Sound;
		private var _rain:Sound;
		
		private var _background:MovieClip;
		private var _treeEyes:MovieClip;
		
		public function CreepyTreeScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean) 
		{
			super(parent, name, theme, rows, columns, timeLimit, groundColor, hasTimer);
		}
		
		override public function init():void
		{
			_shakeImpulse = new b2Vec2(0.001, -0.015);
			
			_thunder = new SoundThunder();
			_rain = new SoundThunder();
			
			_timer = new Timer(500, 0);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			
			_count = 0;
			
			_timer.start();
			
			_rain.play(10000, 3);
		}
		
		private function onTick(e:TimerEvent):void 
		{
			_count += 0.5;
			
			var currentCharacters:Array = Stacks(_parent).characters;
			var dragCharacter:Character = Stacks(_parent).dragCharacter;

			if (_count == 16.5)
			{
				_rain = null;
				_thunder.play(0, 5);
			}
			
			if (_count == 19 || _count == 19.5 || _count == 20 || 
			    _count == 37 || _count == 37.5 || _count == 38 ||
				_count == 55 || _count == 55.5 || _count == 56 ||
				_count == 73 || _count == 73.5 || _count == 74)
			{
				if (_count % 2 == 0)
				{
					_shakeImpulse.x *= -1;
				}
				// perform shake
				for each (var char:Character in currentCharacters)
				{
					if (char && char != dragCharacter)
					{
						char.body.body.ApplyImpulse(_shakeImpulse, char.body.body.GetWorldCenter());
					}
				}
			}
		}
		
		override public function loadScene():void
		{
			_background = new BackgroundCreepyTree();
			_background.x = 300;
			_background.y = 300;
			_parent.addChild(_background);
			
			_background.gotoAndPlay(1);
			
			_treeEyes = new TreeEyes();
			_treeEyes.x = 530;
			_treeEyes.y = 280;
			_treeEyes.scaleX = 0.15;
			_treeEyes.scaleY = 0.15;
			_parent.addChild(_treeEyes);
			
			_treeEyes.gotoAndPlay(1);
		}

		override public function loadPhysicalObjects():void
		{
			// Loads a basic pedestal into the level
			loadPedestal();
		
		}
		
		override public function updateGraphicalObjects():void
		{
			// do nothing
		}
		
		override public function stopEvents():void
		{
			_timer.stop();
			_timer.removeEventListener(TimerEvent.TIMER, onTick);
			
			SoundMixer.stopAll();	
		}
		
		override public function clearScene():void
		{
			_pedestal.destroy();
			_parent.removeChild(_background);
			_parent.removeChild(_treeEyes);
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
				  density: 0
				  // Need skin for pedestal
               }
            );
		}
	}

}