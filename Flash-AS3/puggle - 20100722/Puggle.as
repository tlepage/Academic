package  
{
	import Box2D.Collision.b2AABB;
	import Box2D.Collision.Shapes.b2CircleDef;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.b2BodyDef;
	import Box2D.Dynamics.b2World;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Puggle extends Sprite
	{		
		var _allActors:Array;
		var _actorsToRemove:Array;
		var _pegsLitUp:Array;
		var _camera:Camera;
		var _currentBall:BallActor;
		var _goalPegs:Array;
		var _timeMaster:TimeMaster;
		var _director:Director;
		var _aimingLine:AimingLine;
		var _shooter:Shooter;
		
		private const SHOOTER_POINT:Point = new Point(323, 10);
		private const LAUNCH_VELOCITY:Number = 470.0;
		private const GOAL_PEG_NUM:int = 22;
		private const GRAVITY:Number = 7.8;
		
		public function Puggle() 
		{
			_camera = new Camera();
			addChild(_camera);
			
			_timeMaster = new TimeMaster();
			
			_director = new Director(_camera, _timeMaster);
			
			_shooter = new Shooter();
			_camera.addChild(_shooter);
			
			_shooter.x = SHOOTER_POINT.x;
			_shooter.y = SHOOTER_POINT.y;
			
			_aimingLine = new AimingLine(GRAVITY);
			
			_camera.addChild(_aimingLine);
			//_camera.zoomTo(new Point(400, 200));
			
			_allActors = [];
			_actorsToRemove = [];
			_pegsLitUp = [];
			_goalPegs = [];
			setupPhysicsWorld();
			_currentBall = null;
			//makeBall();
			createLevel();
			
			addEventListener(Event.ENTER_FRAME, newFrameListener);
			stage.addEventListener(MouseEvent.MOUSE_MOVE, showAimLine);
			stage.addEventListener(MouseEvent.CLICK, launchBall);
		}
		
		
		
		private function createLevel():void
		{
			var horizSpacing:int = 36;
			var vertSpacing:int = 36;
			var pegBounds:Rectangle = new Rectangle(114, 226, 418, 255);
			var flipRow:Boolean = false;
			var allPegs:Array = [];
			
			// Create all of our pegs
			for (var pegY:int = pegBounds.top; pegY < pegBounds.bottom; pegY += vertSpacing)
			{
				var startX:int = pegBounds.left + ((flipRow) ? 0 : (horizSpacing / 2));
				flipRow = !flipRow;
				
				for (var pegX:int = startX; pegX < pegBounds.right; pegX += horizSpacing)
				{
					var newPeg:PegActor = new PegActor(_camera, new b2Vec2(pegX, pegY), PegActor.NORMAL);
					newPeg.addEventListener(PegEvent.PEG_LIT_UP, handlePegLitUp);
					newPeg.addEventListener(PegEvent.DONE_FADING_OUT, destroyPegNow);
					_allActors.push(newPeg);
					allPegs.push(newPeg);
				}
			}
			
			// TODO:  Get this working for > 1 goal peg
			if (allPegs.length < GOAL_PEG_NUM)
			{
				throw (new Error("Not enough pegs to set up a level"));
			}
			else
			{
				for (var i:int = 0; i < GOAL_PEG_NUM; i++)
				{
					// Turn some of the pegs into Goal pegs
					var randomPegNum = Math.floor(Math.random() * allPegs.length);
					PegActor(allPegs[randomPegNum]).setType(PegActor.GOAL);
					
					// Keep track of which pegs these are
					_goalPegs.push(allPegs[randomPegNum]);
					allPegs.splice(randomPegNum, 1);
				}
			}
			
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 603), new Point(0, 603)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(-9, 0), wallShapes);
			_allActors.push(leftWall);
			
			// Add the ramps
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(645, 0), wallShapes);
			_allActors.push(rightWall);
			
			var leftRampCoordinates:Array = [[new Point(0, 0), new Point(79, 27), new Point(79, 30), new Point(0, 3)]];
			var leftRamp1:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 265), leftRampCoordinates);
			_allActors.push(leftRamp1);
			
			var leftRamp2:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 336), leftRampCoordinates);
			_allActors.push(leftRamp2);
			
			var leftRamp3:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 415), leftRampCoordinates);
			_allActors.push(leftRamp3);
			
			var rightRampCoordinates:Array = [[new Point(0, 0), new Point(0, 3), new Point(-85, 32), new Point(-85, 29)]];
			var rightRamp1:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(646, 232), rightRampCoordinates);
			_allActors.push(rightRamp1);
			
			var rightRamp2:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(646, 308), rightRampCoordinates);
			_allActors.push(rightRamp2);
			
			var rightRamp3:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(646, 388), rightRampCoordinates);
			_allActors.push(rightRamp3);
			
			
			// create the bonus chute
			var bonusChute:BonusChuteActor = new BonusChuteActor(_camera, 100, 550, 580);
			_allActors.push(bonusChute);
		}
		
		private function destroyPegNow(e:PegEvent):void 
		{
			safeRemoveActor(PegActor(e.currentTarget));
			PegActor(e.currentTarget).removeEventListener(PegEvent.DONE_FADING_OUT, destroyPegNow);
		}
		
		
		
		private function newFrameListener(e:Event):void 
		{
			PhysicsVals.world.Step(_timeMaster.getTimeStep(), 10);
			
			for each (var actor:Actor in _allActors)
			{
				actor.updateNow();
			}
			
			checkForZooming();
			
			reallyRemoveActors();
		}
		
		private function checkForZooming():void
		{
			if (_goalPegs.length == 1 && _currentBall != null)
			{
				var finalPeg:PegActor = _goalPegs[0];
				var p1:Point = finalPeg.getSpriteLoc();
				var p2:Point = finalPeg.getSpriteLoc();
						
				if (getDistSquared(p1, p2) < 50 * 50)
				{
					_director.zoomIn(p1);
				}
				else
				{
					trace("going back to normal");
					
					_director.backToNormal();
				}
			}
			else if (_goalPegs.length == 0)
			{
				_director.backToNormal();
			}
			
		}
		
		private function getDistSquared(p1:Point, p2:Point):int		 
		{
			return ((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y));
		}
		
		
		private function showAimLine(e:MouseEvent):void 
		{
			if (_currentBall == null)
			{
				var launchPoint:Point = _shooter.getLaunchPosition();
				var direction:Point = new Point(mouseX, mouseY).subtract(launchPoint);
				
				_aimingLine.showLine(launchPoint, direction, LAUNCH_VELOCITY);
			}
		}
		
		
		/*
		private function makeBall():void
		{
			var ballActor = new BallActor(_camera, new b2Vec2(200, 10), new b2Vec2(30, -3));
			ballActor.addEventListener(BallEvent.BALL_OFF_SCREEN, handleBallOffScreen);
			_allActors.push(ballActor);
		}*/
		
		private function launchBall(e:MouseEvent):void
		{
			if (_currentBall == null)
			{
				var launchPoint:Point = _shooter.getLaunchPosition();
				// gives vector of direction to launch the ball
				var direction:Point = new Point(mouseX, mouseY).subtract(launchPoint);
				
				// gives vector (always of length LAUNCH_VELOCITY), in the direction we want
				direction.normalize(LAUNCH_VELOCITY);
				
				var newBall:BallActor = new BallActor(_camera, launchPoint, direction);
				newBall.addEventListener(BallEvent.BALL_OFF_SCREEN, handleBallOffScreen);
				newBall.addEventListener(BallEvent.BALL_HIT_BONUS, handleBallInBonusChute);
				
				_allActors.push(newBall);
				_currentBall = newBall;
				_aimingLine.hide();
			}
		}
		
		
		private function handleBallInBonusChute(e:BallEvent):void 
		{
			//trace("Ball hit target!");
			
			// remove the ball
			handleBallOffScreen(e);
		}
		
		private function handleBallOffScreen(e:Event):void 
		{
			//trace("Ball is off screen");
			var ballToRemove:BallActor = BallActor(e.currentTarget);
			ballToRemove.removeEventListener(BallEvent.BALL_OFF_SCREEN, handleBallOffScreen);
			ballToRemove.removeEventListener(BallEvent.BALL_HIT_BONUS, handleBallInBonusChute);
			
			_currentBall = null;
			
			safeRemoveActor(ballToRemove);
			
			// Remove the pegs that have been lit up at _camera point
			//for each (var pegToRemove:PegActor in _pegsLitUp)
			//{
			//	pegToRemove.fadeOut();
				//safeRemoveActor(pegToRemove);
			//}
			
			for (var i:int = 0; i < _pegsLitUp.length; i++)
			{
				var pegToRemove:PegActor = PegActor(_pegsLitUp[i]);
				pegToRemove.fadeOut(i);
			}
			_pegsLitUp = [];
			
			showAimLine(null);
		}
		
		private function handlePegLitUp(e:PegEvent):void 
		{
			// Record the fact that the peg has been lit
			var pegActor:PegActor = PegActor(e.currentTarget);
			pegActor.removeEventListener(PegEvent.PEG_LIT_UP, handlePegLitUp);
			
			if (_pegsLitUp.indexOf(pegActor) < 0)
			{
				_pegsLitUp.push(pegActor);
			}
			
			var goalPegIndex:int = _goalPegs.indexOf(pegActor);
			if (goalPegIndex > -1)
			{
				_goalPegs.splice(goalPegIndex, 1);
			}
		}
		
		private function setupPhysicsWorld():void
		{
			var worldBounds:b2AABB = new b2AABB();
			worldBounds.lowerBound.Set(-5000 / PhysicsVals.RATIO, -5000 / PhysicsVals.RATIO);
			worldBounds.upperBound.Set(5000 / PhysicsVals.RATIO, 5000 / PhysicsVals.RATIO);
			
			var gravity:b2Vec2 = new b2Vec2(0, GRAVITY);
			var allowSleep:Boolean = true;
			
			PhysicsVals.world = new b2World(worldBounds, gravity, allowSleep);
			
			// tell the world to use the custom contact listener
			PhysicsVals.world.SetContactListener(new PuggleContactListener());
		}
		
		// Actually remove the actors that have been marked for deletion
		// in my removeActor function.
		private function reallyRemoveActors():void
		{
			for each (var removeMe:Actor in _actorsToRemove)
			{
				removeMe.destroy();
				
				// Remove from main list of all actors
				var actorIndex:int = _allActors.indexOf(removeMe);
				if (actorIndex > -1)
				{
					_allActors.splice(actorIndex, 1);
				}
			}
			
			_actorsToRemove = [];
		}
		
		// Mark an actor to be removed later at a safe time
		// _camera won't actually remove the actor
		public function safeRemoveActor(actorToRemove:Actor):void
		{
			if (_actorsToRemove.indexOf(actorToRemove) < 0)
			{
				_actorsToRemove.push(actorToRemove);
			}
			
		}
	}

}