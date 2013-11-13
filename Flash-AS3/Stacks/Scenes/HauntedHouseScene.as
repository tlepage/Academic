package Scenes 
{
	import Box2D.Common.Math.b2Vec2;
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.TimerEvent;
	import flash.media.Sound;
	import flash.media.SoundMixer;
	import flash.utils.Timer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class HauntedHouseScene extends GameScene
	{
		private var _pedestal:QuickObject;
		private var _timer:Timer;
		private var _count:int;
		
		private var _man:MovieClip;
		private var _windows:MovieClip;
		
		private var _blood:Sprite;
		private var _moveBlood:Boolean;
		private var _movePedestal:Boolean;
		
		private var _ground:QuickObject;
		private var _scoreboard:Scoreboard;
		
		private var _screamSound:Sound;
		private var _bubblesSound:Sound;
		
		private var _background:MovieClip;
		
		public function HauntedHouseScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean) 
		{
			super(parent, name, theme, rows, columns, timeLimit, groundColor, hasTimer);
		}
		
		override public function init():void
		{	
			_count = 0;
			
			_timer = new Timer(1000, 0);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			
			_timer.start();
			
			_ground = Stacks(_parent).ground;
			_scoreboard = Stacks(_parent).scoreboard;
							
			addBlood();
			//addWalls();
			
			_screamSound = new SoundScream();
			_bubblesSound = new SoundBubbles();
		}
		
		
		private function onTick(e:TimerEvent):void 
		{
			_count += 1;
		
			if (_count >= 20 && _count <= 22)
			{
				_scoreboard.hold();
				
				var currentCharacters:Array = Stacks(_parent).characters;
				
				if (_count == 20)
				{
					_windows.gotoAndStop(2);
					_man.visible = true;

					for each (var charToFade:Character in currentCharacters)
					{
						if (charToFade)
							Sprite(charToFade.body.body.GetUserData()).visible = false;
					}
					
					_screamSound.play();
				}
				
				if (_count == 22)
				{
					_windows.gotoAndStop(1);
					_man.visible = false;
					
					for each (var char:Character in currentCharacters)
					{
						if (char)
						{
							Sprite(char.body.body.GetUserData()).visible = true;
						}
					}
				}
				
			}
			else if (_count == 23)
			{
				_moveBlood = true;
				_scoreboard.go();
				
				_bubblesSound.play(10000);
			}
			else if (_count == 29)
			{
				_movePedestal = true;
			}
		}
		
		private function addWalls():void
		{
			var leftWall = Vals.world.addBox
			(
				{
					x	: Vals.PHYS( -100),
					y   : Vals.PHYS( -1500),
					width : Vals.PHYS(200),
					height: Vals.PHYS(700),
					density:100000,
					fixedRotation:true
				}
			);
			
			var rightWall = Vals.world.addBox
			(
				{
					x	: Vals.PHYS( 700),
					y   : Vals.PHYS( -1500),
					width : Vals.PHYS(200),
					height: Vals.PHYS(700),
					density:100000,
					fixedRotation:true
				}
			);
		}
		
		private function addBlood():void
		{
			_blood = new Blood();
			_blood.x = 300;
			_blood.y = 985;
			
			_blood.cacheAsBitmap = true;
			
			_parent.addChild(_blood);
		}
		
		override public function loadScene():void
		{
			var _background = new BackgroundHauntedHouse();
			
			_man = _background.manWithKnife;
			_man.visible = false;
			
			_windows = _background.houseWindows;
			
			_background.x = 300;
			_background.y = 300;
			_parent.addChild(_background);
			
			_windows.gotoAndStop(1);
		}

		override public function loadPhysicalObjects():void
		{
			// Loads a basic pedestal into the level
			loadPedestal();
		}
		
		override public function stopEvents():void
		{
			_timer.stop();
			_timer.removeEventListener(TimerEvent.TIMER, onTick);
			
			_moveBlood = false;
			
			SoundMixer.stopAll();
		}
		
		override public function clearScene():void
		{
			_pedestal.destroy();
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
				  density: 1,
				  fillColor: 0x000000,
				  fixedRotation: true
				  // Need skin for pedestal
               }
            );
		}
		
		override public function updateGraphicalObjects():void
		{
			if (_moveBlood)
			{
				_blood.y -= 0.30;
				
			}
			
			if (_movePedestal)
			{
				_pedestal.body.SetLinearVelocity(new b2Vec2(0, -0.6));
			}
		}
		
		public function get windows():MovieClip { return _windows; }
		
		public function get blood():Sprite { return _blood; }
	}

}