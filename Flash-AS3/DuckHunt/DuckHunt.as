package  
{
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.ui.Keyboard;
	import flash.ui.Mouse;
	import flash.utils.Timer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class DuckHunt extends MovieClip
	{
		private var _menuScreen:MovieClip;
		private var _background:MovieClip;
		private var _camera:Camera;
		private var _crossHair:Sprite;
		private var _gameScreen:MovieClip;
		private var _trees:MovieClip;
	
		private var _timer:Timer;
		private var _ducks:Array;
		
		public function DuckHunt() 
		{
			_camera = new Camera();
			addChild(_camera);
			
			_menuScreen = new MenuScreen();
			_menuScreen.x = 400;
			_menuScreen.y = 300;
			
			_menuScreen.startButton.addEventListener(MouseEvent.CLICK, onClick);
			
			_camera.addChild(_menuScreen);
			
			_ducks = [];
			
			
		}
		
		private function onTick(e:TimerEvent):void 
		{
			var direction:Boolean =  (Math.random() - 0.5 > 0) ? true : false;
			
			var startX:Number;
			var startY:Number;
			var ySpeed:Number;
			var xSpeed:Number;
			var scale:Number;
			
			if (direction) 
			{
				startX = -50;
				//startY = 500 + Math.random() * 100 - 50;
				startY = 200;
				ySpeed = -5;
				
				xSpeed = 10;
			}
			else 
			{
				startX = 900;
				startY = startY = 500 + Math.random() * 100 - 50;
				
				ySpeed = -5;
				
				xSpeed = -10;
			}

			scale = 0.3;
			
			var duck:GameDuck = new GameDuck(_camera, new Point(startX, startY), direction, xSpeed, ySpeed, scale); 
				  
			_ducks.push(duck);
		}
		
		private function onClick(e:MouseEvent):void 
		{
			_menuScreen.startButton.removeEventListener(MouseEvent.CLICK, onClick);
			_camera.removeChild(_menuScreen);
			_menuScreen = null;
			
			stage.addEventListener(Event.ENTER_FRAME, onUpdate);
			stage.addEventListener(MouseEvent.CLICK, onFire);
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onZoomIn);
			stage.addEventListener(KeyboardEvent.KEY_UP, onZoomOut);
			
			
			_background = new Background();
			_background.x = 400;
			_background.y = 300;
			
			_camera.addChildAt(_background, 0);
			
			_trees = new Trees();
			_trees.x = 400;
			_trees.y = 600;
			
			_camera.addChildAt(_trees, 1);
			
			_crossHair = new CrossHair();
			_camera.addChildAt(_crossHair, 2);
			
			Mouse.hide();
			
			_timer = new Timer(2000);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			_timer.start();
		}
		
		private function onZoomOut(e:KeyboardEvent):void 
		{
			if (e.keyCode == Keyboard.SPACE)
			{
				_camera.zoomOut(new Point(stage.stageHeight * 0.5, stage.stageWidth * 0.5));
			}
		}
		
		private function onZoomIn(e:KeyboardEvent):void 
		{
			if (e.keyCode == Keyboard.SPACE)
			{
				_camera.zoomTo(new Point(stage.stageHeight * 0.5, stage.stageWidth * 0.5));
			}
		}
		
		private function onFire(e:MouseEvent):void 
		{
			for each (var duck:GameDuck in _ducks)
			{
				if (_crossHair.hitTestObject(duck.display) && !duck.hit)
				{
					duck.hit = true;
				}
			}
		}
		
		private function onUpdate(e:Event):void 
		{
			updateCrossHair();
			
			if (_ducks && _ducks.length != 0)
			{
				for each (var duck:GameDuck in _ducks)
				{
					if (!duck.dead)
					{
						duck.update();
						//trace("Updating duck");
					}
				}
			}
			/*
			for (var i:int = _ducks.length; i > -1; --i)
			{
				if ((_ducks[i] as GameDuck).dead)
				{
					_ducks.splice(1);
				}
			}*/
		}
		
		private function updateCrossHair():void
		{
			_crossHair.x = mouseX;
			_crossHair.y = mouseY;
		}
		
	}

}