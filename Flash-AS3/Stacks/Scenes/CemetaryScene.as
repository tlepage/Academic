package Scenes 
{
	import com.actionsnippet.qbox.QuickBox2D;
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.media.Sound;
	import flash.media.SoundMixer;
	import flash.utils.Timer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class CemetaryScene extends GameScene
	{
		private var _pedestal:QuickObject;
		private var _teeterTotter:QuickObject;
		private var _idealCenter:Point;
		
		private var _background:MovieClip;
		
		private var _timer:Timer;
		private var _count:int;
		
		private var _fog:Sprite;
		
		private var _noises:Sound;
		private var _ghost:Sprite;
		private var _breath:Sound;
		
		public function CemetaryScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean) 
		{
			super(parent, name, theme, rows, columns, timeLimit, groundColor, hasTimer);
		}
		
		override public function init():void
		{
			_count = 0;
			
			_breath = new SoundBreath();
			
			_timer = new Timer(1000, 0);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			
			_timer.start();
			
			_noises = new SoundNoise();
			_noises.play(0, 2);
		}
		
		private function onTick(e:TimerEvent):void 
		{
			_count += 1;
			
			if (_count % 10 == 0)
			{				
				_ghost = new CemetaryGhost();
				
				_ghost.x = Math.random() * 600;
				_ghost.y = 700;
				
				_ghost.cacheAsBitmap = true;
				
				_parent.addChild(_ghost);
				
				// play sound
				_breath.play();
			}
		}
		
		// Loads the teeter totter into the level
		private function loadTeeterTotter():void
		{
			_pedestal = Vals.world.addBox   
            (      
               {
                  x      : Vals.PHYS(_parent.stage.stageWidth * 0.5),
                  y      : Vals.PHYS(550), 
                  width  : Vals.PHYS(60), 
                  height : Vals.PHYS(70),
				  density: 0,
				  skin:GravePedestal
				  // Need skin for pedestal
               }
            );
         
			//  Create the teeter totter
			_teeterTotter = Vals.world.addBox   
            (      
               {
                  x      : Vals.PHYS(_parent.stage.stageWidth * 0.5),
                  y      : Vals.PHYS(510), 
                  width  : Vals.PHYS(480),                      /// TODO: need Scene rows and columns
                  height : Vals.PHYS(15),
                  density: 5000,//13.5,						 // Need skin for teeter totter
				  friction: 0.175,
				  restitution: 0.01,
				  skin:Slab
               }
            );
        
			// Set ideal center for pull-down checking
			_idealCenter = new Point(Vals.PHYS(_parent.stage.stageWidth * 0.5), Vals.PHYS(510));
			
			//  create the joint to hold the teeter totter to the pedestal
			Vals.world.addJoint
            (
               {
                  type: QuickBox2D.REVOLUTE, 
                  a:_teeterTotter.body, 
                  b:_pedestal.body, 
                  collideConnected:false,
                  lowerAngle:-Math.PI / 6,
                  upperAngle:Math.PI / 6,
                  enableLimit:true,
				  density:0 				// not sure if this works
               }
            );
		}
		
		override public function loadScene():void
		{
			_background = new BackgroundCemetary();
			_background.x = 300;
			_background.y = 300;
			
			_background.cacheAsBitmap = true;
			
			_parent.addChild(_background);
			
			_fog = new Fog();
			_fog.x = -500;
			_fog.y = 300;
			
			_fog.cacheAsBitmap = true;
			
			_parent.addChild(_fog);
		}
		
		override public function clearScene():void
		{
			_pedestal.destroy();
			_teeterTotter.destroy();
			
			//Stacks(_parent).removeChild(_fog);
			//_parent.removeChild(_background);
		}
		
		override public function loadPhysicalObjects():void
		{
			// Loads a basic pedestal into the level
			loadTeeterTotter();
		}
		
		override public function stopEvents():void
		{
			_timer.stop();
			_timer.removeEventListener(TimerEvent.TIMER, onTick);
			
			_fog.visible = false;
			_fog.x = 0;
			_parent.removeChild(_fog);
			
			SoundMixer.stopAll();
		}
		
		override public function updateGraphicalObjects():void
		{
			_fog.x += 0.75;
			
			// do check here for desired center point for the teeter totter
			if (_teeterTotter.body.GetWorldCenter().y > _idealCenter.y)
			{
				_teeterTotter.y = _idealCenter.y;
			}
			
			// if any attempt is made to drop it, correct it by moving it to desired position
			// or, if the center point hits the ground, end the game
			if (_teeterTotter.y > 590) 
			{
				// need to end game here
			}
			
			if (_ghost)
			{
				if (_ghost.y < -150)
				{
					_ghost = null;
				}
				else
				{
					_ghost.y -= 5;
				}
			}
		}
	}

}