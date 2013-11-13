package Scenes 
{
	import Box2D.Common.Math.b2Vec2;
	import com.actionsnippet.qbox.QuickObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.TimerEvent;
	import flash.media.Sound;
	import flash.utils.Timer;
	import flash.media.SoundMixer;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class FallTimeScene extends GameScene
	{
		private var _pedestal:QuickObject;
		private var _timer:Timer;
		private var _count:int;
		
		private var _acorns:Array;
		private var _leaves:Array;
		private var _deadLeaves:Array;
		private var _leafTypes:Array;
		
		private var _wind:Sound;
		private var _birds:Sound;
		private var _background:MovieClip;
		
		public function FallTimeScene(parent:DisplayObjectContainer, name:String, theme:int, rows:int, columns:int, timeLimit:int, groundColor:uint, hasTimer:Boolean) 
		{
			super(parent, name, theme, rows, columns, timeLimit, groundColor, hasTimer);
			
		}
		
		override public function init():void
		{
			_count = 0;
			_acorns = [];
			_leaves = [];
			_deadLeaves = [];
			_leafTypes = [new Leaf1(), new Leaf2(), new Leaf3(), new Leaf4()];
			
			_timer = new Timer(1000, 0);
			_timer.addEventListener(TimerEvent.TIMER, onTick);
			
			_wind = new SoundWind();
			_birds = new SoundBirds();
			
			_timer.start();
			
			_wind.play(0, 1);
			_birds.play(0, 2);
		}
		
		private function onTick(e:TimerEvent):void 
		{
			_count += 1;
			
			if (_count == 10)
			{
				// launch an acorn every two seconds
				launchAcorn();
				
				// reset the count
				_count = 0;
				
				// clean out dead leaves
				for (var i:int = _deadLeaves.length - 1; i >= 0; i--)
				{
					//_parent.removeChild(Sprite(_deadLeaves[i]));
					_deadLeaves[i] = null;
				}
				
				_deadLeaves = [];
			}
			
			if (_count % 5 == 0)
			{
				launchLeaf();
			}
		}
		
		override public function loadScene():void
		{
			_background = new BackgroundFall();
			_background.x = 300;
			_background.y = 300;
			
			_background.cacheAsBitmap = true;
			
			_parent.addChild(_background);
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

			removeAcorns();
			
			SoundMixer.stopAll();
		}
		
		override public function clearScene():void
		{
			_pedestal.destroy();
			
			for each (var q:QuickObject in _acorns)
			{
				if (q)
					q.destroy();
			}
			
			_acorns = [];
			_leaves = [];
			
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
				  fillColor: 0x004500
				  // Need skin for pedestal
               }
            );
		}
		
		private function launchAcorn():void
		{
			var x:Number = Vals.PHYS(200 + Math.random() * 200);
			var y:Number = Vals.PHYS( -1);
			var randVelX:Number = -3 + (Math.random() * 6);
			var randVelY:Number = Vals.PHYS(Math.random() * 150);
			
			var acorn:QuickObject = Vals.world.addBox
			(
				{
					x	: x,
					y	: y,
					width : Vals.PHYS(25),
					height : Vals.PHYS(25),
					restitution : 0.3,
					friction: 0.3,
					density: 0.009,
					skin: Acorn,
					scaleSkin:false
				}
				
				
			);
			
			_parent.addChild(acorn.userData);
			
			acorn.body.SetLinearVelocity(new b2Vec2(randVelX, randVelY));
			acorn.body.SetAngularVelocity(randVelX);
			
			_acorns.push(acorn);
		}
		
		private function launchLeaf():void
		{
			// create leaf
			var index:int = Math.random() * _leafTypes.length - 1;
			
			// add it to leaves array
			var currentLeaf:Sprite = _leafTypes[index];
			currentLeaf.x = Math.random() * 600;
			currentLeaf.y = -5;
			
			_leaves.push(currentLeaf);
			_parent.addChild(currentLeaf);
		}
		
		override public function updateGraphicalObjects():void
		{			
			// for every leaf in the leaves array
			for (var i:int = _leaves.length - 1; i >= 0; i--)
			{
				// update the position
				var leaf:MovieClip = MovieClip(_leaves[i]);
				
				// check if off screen; if so, remove from array
				if (leaf.y < 650)
				{
					leaf.x += Math.random() * 3;
					leaf.y += 3;
				
					// update the rotation
					leaf.rotationX += Math.random() * 1;
					leaf.rotationY += Math.random() * 1;
					leaf.rotationZ += Math.random() * 1;
				}
				else
				{
					_deadLeaves.push(leaf);
				}
			}
			
			
		}
		
		private function removeAcorns():void
		{
			for (var i:int = _acorns.length - 1; i >= 0; i--)
			{
				//_parent.stage.removeChild(MovieClip(QuickObject(_acorns[i]).body.GetUserData()));
				_acorns.splice(i, 1);
			}
		}
	}

}