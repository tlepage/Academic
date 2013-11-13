package  
{
	import Box2D.Collision.b2AABB;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2World;
	import com.greensock.TweenLite;
	import fl.motion.MatrixTransformer;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Shader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.filters.BlurFilter;
	import flash.filters.DropShadowFilter;
	import flash.filters.GradientBevelFilter;
	import flash.geom.Matrix;
	import flash.geom.Matrix3D;
	import flash.geom.Point;
	import flash.geom.Vector3D;
	import flash.text.AntiAliasType;
	import flash.text.Font;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.TextFormatAlign;
	import flash.ui.Keyboard;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PuttPutt extends Sprite
	{
		//private var _allLevels:Array;
		
		private var _camera:Camera;
		private var _director:Director;
		private var _timeMaster:TimeMaster;
		
		private var _levelLoader:LevelLoader;
		//private var _curLevel:Level;
		private var _curLevelNumber:int;
		//private var _putter:PutterActor;
		
		private var _allActors:Array;
		private var _ball:BallActor;
		
		private const GRAVITY:Number = 0.0;
		private const PULSATE_SPEED:Number = 0.03;
		
		private var _cup:CupActor;
		private var _targetSprite:Sprite;
		
		private var _arrowSprite:Sprite;
		private var _actorsToRemove:Array;
		
		private var _power:Number;
		private var _pulseDir:int;
		
		private var _strokeCounter:int;
	
		private var _resultField:TextField;
		private var _levelNameField:TextField;
		private var _levelParField:TextField;
		private var _levelStrokeField:TextField;
		private var _congratulationsField:TextField;
		private var _scores:Array;
		
		// Levels array that stores Functions
		private var _levels:Array;
		
		// A variable to keep track of where you are in the array
		private var _levelCounter:int;
		
		private var _canSwitchLevels:Boolean;
		private var _showScorecard:Boolean;
		
		private var _bevelFilter:GradientBevelFilter;
		private var _background:Sprite;
		private var _courseNumberTextField:TextField;
		private var _parTextField:TextField;
		private var _strokesTextField:TextField;
		
		public function PuttPutt() 
		{
			//this.z = 500;
			//this.rotationX = -30;
			
			// Initialize variables
			//_allLevels = [];
			_camera = new Camera();
			addChild(_camera)
			_timeMaster = new TimeMaster();
			
			_director = new Director(_camera, _timeMaster);
			_curLevelNumber = 0;
			
			_allActors = [];
			_actorsToRemove = [];
			
			// Create the physics world
			setupPhysicsWorld();
			
			// Load the levels
			// TODO:  Need to have a level change handler instead of this
			_levels = [];
			_scores = [];
			//_functionPointer = loadLevelOne;
			
			_levels.push(loadLevelOne);
			_levels.push(loadLevelTwo);
			_levels.push(loadLevelThree);
			_levels.push(loadLevelFour);
			_levels.push(loadLevelFive);
			_levels.push(loadLevelSix);
			_levels.push(loadLevelSeven);
			_levels.push(loadLevelEight);
			_levels.push(loadLevelNine);
			_levels.push(loadLevelTen);
			_levels.push(loadLevelEleven);
			_levels.push(loadLevelTwelve);
			_levels.push(loadLevelThirteen);
			_levels.push(loadLevelFourteen);
			_levels.push(loadLevelFifteen);
			_levels.push(loadLevelSixteen);
			_levels.push(loadLevelSeventeen);
			
			_levelCounter = 16;
			
			//_levelLoader = new LevelLoader("levels.lev");
			//_levelLoader.addEventListener(LoadCompleteEvent.LOAD_COMPLETE, loadCompleted);
			
			runLevel();
			
			_canSwitchLevels = false;	
			_showScorecard = false;
			
			_power = 0.0;
			_pulseDir = 1.0;
			
			_camera.z = 500;
			_camera.rotationX = -20;
			
			_strokeCounter = 0;
				
			loadEventListeners();
			loadCameraEssentials();
		}
		
		// Loads the event listeners necessary for play
		private function loadEventListeners():void
		{
			addEventListener(GameEvent.LEVEL_STOP, handleLevelStop);
			
			//stage.addEventListener(MouseEvent.MOUSE_DOWN, mouseDown);
			//stage.addEventListener(MouseEvent.MOUSE_UP, mouseReleased);
			stage.addEventListener(MouseEvent.CLICK, onMouseClick);
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
			stage.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
			
			// Add event listener to run per frame
			addEventListener(Event.ENTER_FRAME, newFrameListener);
			

		}
		
		public function loadCameraEssentials():void
		{
			_arrowSprite = new ArrowSprite();
			_arrowSprite.alpha = 1.0;

			_targetSprite = new TargetSprite();
			_targetSprite.alpha = 1.0;
			
			_arrowSprite.visible = false;
			_targetSprite.visible = false;
			
			_camera.addChild(_arrowSprite);
			_camera.addChild(_targetSprite);
		}
		
		// Runs the current level
		private function runLevel():void
		{
			//clearLevel();
			
			if (_levels.length > 0)
			{
				var currentLevel:Function = _levels[_levelCounter];
				currentLevel.call();
			}
		}
		
		// Switches to the next level
		private function switchLevel():void
		{
			// Clear level
			clearLevel();
			
			if (++_levelCounter < _levels.length)
			{
				var currentLevel:Function = _levels[_levelCounter];
				currentLevel.call();
				loadEventListeners();
				loadCameraEssentials();
			}
		}
		
		// Retry the current level
		private function retryLevel():void
		{
			// Clear the level
			clearLevel();
			
			var currentLevel:Function = _levels[_levelCounter];
			currentLevel.call();
			loadEventListeners();
			loadCameraEssentials();
		}
		
		// Clear level of all actors
		private function clearLevel():void
		{
			for each (var actor:Actor in _allActors)
			{
				actor.destroy();
			}
			
			var children:int = _camera.numChildren;
			
			for (var i:int = children - 1; i >= 0; i--)
			{
				_camera.removeChildAt(i);
			}
			
			_allActors = [];
			
			_strokeCounter = 0;
		}
		
		// This function sets up the physics world
		private function setupPhysicsWorld():void
		{
			var worldBounds:b2AABB = new b2AABB();
			worldBounds.lowerBound.Set(-5000 * PhysicsVals.INV_RATIO, -5000 * PhysicsVals.INV_RATIO);
			worldBounds.upperBound.Set(5000 * PhysicsVals.INV_RATIO, 5000 * PhysicsVals.INV_RATIO);
			
			var gravity:b2Vec2 = new b2Vec2(0.0, GRAVITY);
			var allowSleep:Boolean = true;
			
			PhysicsVals.world = new b2World(worldBounds, gravity, allowSleep);
			
			// Override the default contact listener for the physics engine
			PhysicsVals.world.SetContactListener(new PuttPuttContactListener());
		}

		private function getDistSquared(p1:Point, p2:Point):int		 
		{
			return ((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y));
		}
		
		private function checkForZooming():void
		{
			// Only zoom if the ball is moving
			if (_ball.isMoving)
			{
				var p1:Point = _ball.getSpriteLoc();
				var p2:Point = _cup.getSpriteLoc();
						
				if (getDistSquared(p1, p2) < 50 * 50)
				{
					_director.zoomIn(p2);
				}
				else
				{
					_director.backToNormal();
				}
			}
		}
		
		
		
		private function isMouseWithin(point:Point, threshold:Number):Boolean
		{
			// Check to see if mouseX and mouseY are within the supplied point and its threshold
			if ((mouseX <= point.x + threshold / 2) && (mouseX >= point.x - threshold / 2) && 
			    (mouseY <= point.y + threshold / 2) && (mouseY >= point.y - threshold / 2))
			{
				return (true);
			}
			else
			{
				return (false);
			}
		}
		
		
		private function pulsateSprite(spriteToPulsate:Sprite):void
		{
			// Modify alpha of sprite passed in to give off appearance of pulsing
			if (spriteToPulsate.alpha >= 1.0) 
			{
				_pulseDir = -1;
			}
			else if (spriteToPulsate.alpha <= 0.0)
			{
				_pulseDir = 1;
			}
			
			spriteToPulsate.alpha += PULSATE_SPEED * _pulseDir;
		}
		
		
		private function drawArrow():void
		{
			_arrowSprite.x = _ball.getDisplayPosition().x;
			_arrowSprite.y = _ball.getDisplayPosition().y;
			_arrowSprite.rotation = (_ball.direction * PhysicsVals.RAD_TO_DEG);
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
		
		// This function operates on every frame and steps the physics engine
		private function newFrameListener(e:Event):void 
		{
			PhysicsVals.world.Step(_timeMaster.getTimeStep(), 10);
			
			for each (var actor:Actor in _allActors)
			{
				actor.updateNow();
			}
			
			// Apply friction
			_ball.applyFriction(0.09);
			
			// Check to see if zooming needs to occur
			checkForZooming();
			
			// Call pulsate effect on sprites
			//pulsateSprite(_targetSprite);
			
			// Remove actors flagged for removal after the physics engine has completed the current step
			reallyRemoveActors();
		}
		
		
		private function onKeyUp(e:KeyboardEvent):void 
		{
			// Check to see if key pressed is SPACE
			if (e.keyCode == Keyboard.UP)
			{
				// set current power level in ball
				_ball.power = _power;
			}
			else if (e.keyCode == Keyboard.DOWN)
			{
				// set current power level in ball
				_ball.power = _power;			
			}
		}
		
		private function onKeyDown(e:KeyboardEvent):void 
		{
			// Check to see if key pressed is SPACE
			if (e.keyCode == Keyboard.UP && !_canSwitchLevels)
			{
				// set and scale power
				_power = (_power >= 100) ? _power = 100.0 : (_power += 30 / PhysicsVals.FPS);
				
				// modify the scale of the aiming arrow
				_arrowSprite.scaleX = _power / PhysicsVals.FPS;
			}
			else if (e.keyCode == Keyboard.DOWN && !_canSwitchLevels)
			{
				// set and scale power
				_power = (_power <= 0) ? _power = 0.0 : (_power -= 30 / PhysicsVals.FPS);
				
				// modify the scale of the aiming arrow
				_arrowSprite.scaleX = _power / PhysicsVals.FPS;
			}
			else if (e.keyCode == Keyboard.SPACE && !_canSwitchLevels && !_showScorecard && _power > 1.0)
			{
				// just in case the player is still pressing one of the power buttons....
				_ball.power = _power;
				
				// reset the power level
				_power = 1.0;
				
				// make the arrow invisible and reset the scale
				_arrowSprite.visible = false;
				_arrowSprite.scaleX = _power;
				
				// make the target invisible
				_targetSprite.visible = false;
				
				// remove the event listeners while the ball is hit
				stage.removeEventListener(MouseEvent.CLICK, onMouseClick);
				// TODO: MAKE SCORECARD AND SET THIS BACK!!!
				stage.removeEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
				stage.removeEventListener(KeyboardEvent.KEY_UP, onKeyUp);
				
				// add listener to detect when/if ball's motion has stopped
				_ball.addEventListener(BallEvent.BALL_STOPPED, handleBallStopped);
				
				// putt the ball
				_ball.hitBall();
			}
			else if (e.keyCode == Keyboard.SPACE && _canSwitchLevels && !_showScorecard)
			{
				removeEventListener(Event.ENTER_FRAME, newFrameListener);
					
				// Need to update score table
				loadScorecard();
					
				//stage.removeEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
				//switchLevel();
					
				_canSwitchLevels = false;
			}
			else if (e.keyCode == Keyboard.SPACE && !_canSwitchLevels && _showScorecard)
			{
				stage.removeChild(_background);
				stage.removeChild(_strokesTextField);
				stage.removeChild(_courseNumberTextField);
				stage.removeChild(_parTextField);
				
				_showScorecard = false;
				stage.removeEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
				
				switchLevel();
			}
			
		}
		
		private function onMouseClick(e:MouseEvent):void 
		{
			// This function will set a target point and give that to the ball object
			_ball.setTarget(new Point(mouseX, mouseY));
			
			_power = 1.0;
			
			// Show arrow if not visible
			if (!_arrowSprite.visible) _arrowSprite.visible = true;
			
			// Adjust scale of arrow to current power setting
			_arrowSprite.scaleX = 0.0;
			
			// Draw aiming target
			_targetSprite.visible = true;
			
			//_targetSprite.z = 500;
			//_targetSprite.rotationX = -30;
			
			_targetSprite.x = mouseX;
			_targetSprite.y = mouseY;

			
			// Draw the arrow
			drawArrow();
		}

		private function handleBallStopped(e:BallEvent):void 
		{
			if (_ball.isMoving)
			{
				// Stop the ball from moving
				_ball.stop();
			
				trace("ball stopped");
				
				// Increase stroke counter
				++_strokeCounter;
					
				// Readjust the time and zoom factor
				_director.backToNormal();
			
				// Update the stroke text field
				_levelStrokeField.replaceText(0, _levelStrokeField.length, "Strokes: " + _strokeCounter);
					
				// Remove the event listener
				_ball.removeEventListener(BallEvent.BALL_STOPPED, handleBallStopped);
				stage.addEventListener(MouseEvent.CLICK, onMouseClick);
				stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
				stage.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
			}
		}
		
		private function handleBallInCup(e:BallEvent):void
		{
			// Stop the ball
			_ball.stop();
			
			// Increase stroke count
			_strokeCounter += 1;
			
			// Update the stroke text field
			_levelStrokeField.replaceText(0, _levelStrokeField.length, "Strokes: " + _strokeCounter);
			
			// Safely remove the actor from the world
			safeRemoveActor(_ball);
	
			dispatchEvent(new GameEvent(GameEvent.LEVEL_STOP));
			
			stage.removeEventListener(MouseEvent.CLICK, onMouseClick);
			removeEventListener(BallEvent.BALL_STOPPED, handleBallStopped);
			removeEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
		}
		
		private function handleLevelStop(e:GameEvent):void
		{
			// Zoom out
			_director.backToNormal();
			
			// Display Congratulations
			// Load congratulations font
			var jokermanFont:Font = new JokermanFont();
			var congratsTextFormat:TextFormat = new TextFormat();
			congratsTextFormat.size = 64;
			congratsTextFormat.align = TextFormatAlign.CENTER;
			congratsTextFormat.font = jokermanFont.fontName;
			
			_congratulationsField = new TextField();
			_congratulationsField.defaultTextFormat = congratsTextFormat;
			_congratulationsField.x = 50;
			_congratulationsField.y = 650;
			_congratulationsField.width = 800;
			_congratulationsField.appendText("CONGRATULATIONS!");
			_congratulationsField.antiAliasType = AntiAliasType.ADVANCED;
			_congratulationsField.selectable = false;
			_congratulationsField.textColor = 0xffffff;
			
			_camera.addChild(_congratulationsField);

			_scores.push(_strokeCounter);
			//_strokeCounter = 0;
			
			// Switch levels
			_canSwitchLevels = true;
			
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
		}
		
		
		private function loadScorecard():void
		{
			_congratulationsField.visible = false;
			
			_showScorecard = true;
			
			// Load the scorecard
			_background = new Sprite();
			_background.graphics.beginFill(0xFFFFFF, 0.95);
			_background.graphics.drawRoundRect(0, 0, 1024, 768, 2, 2);
			_background.graphics.endFill();
			
			
			var effectArray:Array = [];
			var effect:BlurFilter = new BlurFilter(255, 255, 1);
			effectArray.push(effect);
			
			_background.filters = effectArray;
			stage.addChild(_background);
			
			// Print the scores
			
			// Add a text field to say "Scorecard"
			// Load a font
			var oratorFont:Font = new OratorFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 18;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = oratorFont.fontName;
			textFormat.color = 0x000000;
			
			// Change text format
			_courseNumberTextField = new TextField();
			_courseNumberTextField.defaultTextFormat = textFormat;
			_courseNumberTextField.x = 20;
			_courseNumberTextField.y = 250;
			_courseNumberTextField.width = 1000;
			_courseNumberTextField.appendText("Hole:   1   2   3   4   5   6   7   8   9   10   11   12   13   14   15   16   17   18");
			
			_parTextField = new TextField();
			_parTextField.defaultTextFormat = textFormat;
			_parTextField.x = 20;
			_parTextField.y = 275;
			_parTextField.width = 1000;
			_parTextField.appendText("Par:    2   2   2   3   2   2   2   2   3   2   1   2   3   2   1   1   2");
			
			_strokesTextField = new TextField();
			_strokesTextField.defaultTextFormat = textFormat;
			_strokesTextField.x = 20;
			_strokesTextField.y = 300;
			_strokesTextField.width = 1000;
			_strokesTextField.appendText("Score:  ");

			// Cycle through the levels to get hole count right
			for each (var i:int in _scores)
			{
				if (i < 10)
				{
					_strokesTextField.appendText(i + "   ");
				}
				else
				{
					_strokesTextField.appendText(i + "  ");
				}
			}
			
			// Cycle through the scores array
			stage.addChild(_courseNumberTextField);
			stage.addChild(_parTextField);
			stage.addChild(_strokesTextField);
			
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
		}
		
		
		// Level loading methods
		private function loadLevelOne():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 603), new Point(0, 603)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(-9, 0), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(645, 0), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 593), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, -5), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Add the ramps
			var rampShape:Array = [[new Point(0, 0), new Point(170, 0), new Point(170, 10), new Point(0, 10)]];
			
			var middleRamp:RampActor = new RampActor(_camera, new Point(200, 150), rampShape, 0x00fd00, new b2Vec2(0.0, 0.02), 0.3);
			_allActors.push(middleRamp);
			
			// Create the bounds of the course
			var sideBoundShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 450), new Point(0, 450)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 140), sideBoundShapes, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(380, 140), sideBoundShapes, null, true, 0x0000FF);
			_allActors.push(rightBound);
			
			var backBoundShapes:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 10), new Point(0, 10)]];
			
			var backBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 140), backBoundShapes, null, true, 0x0000FF);
			_allActors.push(backBound);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(280, 200));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(280, 500), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Easy as Pie");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		
		}
		
		private function loadLevelTwo():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 603), new Point(0, 603)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(-9, 0), wallShapes, null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(645, 0), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 593), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, -5), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the bounds of the course
			var rightSideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 450), new Point(0, 450)]];
			var leftSideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 290), new Point(0, 290)]];
			var dogLegTopShape:Array = [[new Point(0, 0), new Point(300, 0), new Point(300, 10), new Point(0, 10)]];
			var dogLegBottomShape:Array = [[new Point(0, 0), new Point(120, 0), new Point(120, 10), new Point(0, 10)]];
			var dogLegBackShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 160), new Point(0, 160)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 300), leftSideBoundShape, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(380, 140), rightSideBoundShape, null, true, 0x0000FF);
			_allActors.push(rightBound);
			

			var dogLegTopBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(60, 140), dogLegTopShape, null, true, 0x0000FF);
			_allActors.push(dogLegTopBound);
			
			var dogLegBottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(60, 300), dogLegBottomShape, null, true, 0x0000FF);
			_allActors.push(dogLegBottomBound);
			
			var dogLegBackBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(60, 140), dogLegBackShape, null, true, 0x0000FF);
			_allActors.push(dogLegBackBound);
			
			var backBoundShapes:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 10), new Point(0, 10)]];
			
			var backBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 140), backBoundShapes, null, true, 0x0000FF);
			_allActors.push(backBound);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(100, 250));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(280, 500), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Dogleg Left");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelThree():void
		{
			// Add the ramp
			var rampShape:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 20), new Point(0, 20)]];
			
			var middleRamp:RampActor = new RampActor(_camera, new Point(180, 150), rampShape, 0x005500, new b2Vec2(0.0, -0.02), 0.3);
			_allActors.push(middleRamp);
			
			// Create the bounds of the course
			var sideBoundShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 550), new Point(0, 550)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 40), sideBoundShapes, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(380, 40), sideBoundShapes, null, true, 0x0000FF);
			_allActors.push(rightBound);
			
			var backBoundShapes:Array = [[new Point(0, 0), new Point(210, 0), new Point(210, 10), new Point(0, 10)]];
			
			var topBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 40), backBoundShapes, null, true, 0x0000FF);
			_allActors.push(topBound);
			
			var backBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 590), backBoundShapes, null, true, 0x0000FF);
			_allActors.push(backBound);

			// Create a peg
			var peg:PegActor = new PegActor(_camera, new b2Vec2(210, 230), PegActor.NORMAL);
			_allActors.push(peg);
			
			var pegTwo:PegActor = new PegActor(_camera, new b2Vec2(240, 200), PegActor.NORMAL);
			_allActors.push(pegTwo);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(210, 200));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(280, 500), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Tricky");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelFour():void
		{
			// Create the bounds of the course
			var rightSideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 450), new Point(0, 450)]];
			var leftSideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 290), new Point(0, 290)]];
			var dogLegTopShape:Array = [[new Point(0, 0), new Point(400, 0), new Point(400, 10), new Point(0, 10)]];
			var dogLegBottomShape:Array = [[new Point(0, 0), new Point(120, 0), new Point(120, 10), new Point(0, 10)]];
			var dogLegBackShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 450), new Point(0, 160)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(350, 300), leftSideBoundShape, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(550, 140), rightSideBoundShape, null, true, 0x0000FF);
			_allActors.push(rightBound);
			

			var dogLegTopBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(160, 140), dogLegTopShape, null, true, 0x0000FF);
			_allActors.push(dogLegTopBound);
			
			var dogLegBottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(240, 300), dogLegBottomShape, null, true, 0x0000FF);
			_allActors.push(dogLegBottomBound);
			
			var dogLegBackBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(160, 140), rightSideBoundShape, null, true, 0x0000FF);
			_allActors.push(dogLegBackBound);
			
			var backBoundShapes:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 10), new Point(0, 10)]];
			
			var backBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 140), backBoundShapes, null, true, 0x0000FF);
			_allActors.push(backBound);
			
			var bottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(160, 580), dogLegTopShape, null, true, 0x0000FF);
		    _allActors.push(bottomBound);
			
			// Create pegs
			var peg:PegActor = new PegActor(_camera, new b2Vec2(250, 230), PegActor.NORMAL);
			_allActors.push(peg);
			
			var pegTwo:PegActor = new PegActor(_camera, new b2Vec2(300, 230), PegActor.NORMAL);
			_allActors.push(pegTwo);
			
			var pegThree:PegActor = new PegActor(_camera, new b2Vec2(350, 230), PegActor.NORMAL);
			_allActors.push(pegThree);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(200, 550));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(450, 500), new Point( 0, -0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("U-Turn");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 3");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelFive():void
		{
			var sideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 450), new Point(0, 450)]];
			var topShape:Array = [[new Point(0, 0), new Point(410, 0), new Point(410, 10), new Point(0, 10)]];
			var blockShape:Array = [[new Point(0, 0), new Point(20, 0), new Point(20, 400), new Point(0, 400)]];
			var rampShape:Array = [[new Point(0, 0), new Point(385, 0), new Point(385, 100), new Point(0, 100)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(200, 200), sideBoundShape, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(600, 200), sideBoundShape, null, true, 0x0000FF);
			_allActors.push(rightBound);
			
			var topBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(200, 200), topShape, null, true, 0x0000FF);
			_allActors.push(topBound);
			
			var bottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(200, 650), topShape, null, true, 0x0000FF);
			_allActors.push(bottomBound);
			
			var ramp:RampActor = new RampActor(_camera, new Point(212, 212), rampShape, 0x00F500, new b2Vec2(0.0, -0.1), -0.5, null, true);
			_allActors.push(ramp);
			
			var blockBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(400, 250), blockShape, null, true, 0x0000FF);
			_allActors.push(blockBound);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(550, 550));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(275, 550), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Uphill");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelSix():void
		{
			var longSideShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 400), new Point(0, 400)]];
			var shortSideShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 150), new Point(0, 150)]];
			var longTopSides:Array = [[new Point(0, 0), new Point(335, 0), new Point(335, 10), new Point(0, 10)]];
			var shortTopSides:Array = [[new Point(0, 0), new Point(85, 0), new Point(85, 10), new Point(0, 10)]];
			
			// Top of I
			var top:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 50), longTopSides);
			_allActors.push(top);
			
			// Bottom of I
			var bottom:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 750), longTopSides);
			_allActors.push(bottom);
			
			// Long left side of I
			var longLeftSide:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(225, 200), longSideShapes);
			_allActors.push(longLeftSide);
			
			// Long right side of I
			var longRightSide:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(400, 200), longSideShapes);
			_allActors.push(longRightSide);
			
			// Short sides of left
			var shortLeftSideTop:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 50), shortSideShapes);
			_allActors.push(shortLeftSideTop);
			
			var shortLeftSideBottom:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 600), shortSideShapes);
			_allActors.push(shortLeftSideBottom);
			
			// Short sides of right
			var shortRightSideTop:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(475, 50), shortSideShapes);
			_allActors.push(shortRightSideTop);
			
			var shortRightSideBottom:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(475, 600), shortSideShapes);
			_allActors.push(shortRightSideBottom);
			
			// Short caps on left
			var shortLeftCapTop:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 200), shortTopSides);
			_allActors.push(shortLeftCapTop);
			
			var shortLeftCapBottom:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(150, 600), shortTopSides);
			_allActors.push(shortLeftCapBottom);
			
			// Short caps on right
			var shortRightCapTop:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(400, 200), shortTopSides);
			_allActors.push(shortRightCapTop);
			
			var shortRightCapBottom:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(400, 600), shortTopSides);
			_allActors.push(shortRightCapBottom);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(450, 170));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(325, 700), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("I for an I");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelSeven():void
		{
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 500), new Point(0, 500)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(100, 50), wallShapes, null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(645, 50), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(555, 0), new Point(555, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(100, 550), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(100, 50), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the ramp shapes
			var rampShapes:Array = [[new Point(0, 0), new Point(530, 0), new Point(530, 100), new Point(0, 100)]];
			
			var leftRamp:RampActor = new RampActor(_camera, new Point(112, 200), rampShapes, 0x00EE00, new b2Vec2( -0.4, 0.01), 0.03);
			_allActors.push(leftRamp);
			
			var rightRamp:RampActor = new RampActor(_camera, new Point(112, 300), rampShapes, 0x005500, new b2Vec2( 0.8, 0.01), 0.03);
			_allActors.push(rightRamp);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(375, 150));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(375, 525), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
				
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Duality");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelEight():void
		{
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(160, 250), new Point(150, 250)]];
			var otherWallShapes:Array = [[new Point(150, 0), new Point(160, 0), new Point(10, 250), new Point(0, 250)]];
			
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(370, 115), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
		
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(220, 365), wallShapes, null, true, 0x000000);
			_allActors.push(leftWall);
			
			var lowerRightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(370, 365), otherWallShapes, null, true, 0x000000);
			_allActors.push(lowerRightWall);
			
			var upperLeftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(220, 115), otherWallShapes, null, true, 0x000000);
			_allActors.push(upperLeftWall);
			
			// Create the pegs
			
			// Center pegs
			//var centerPeg1:PegActor = new PegActor(_camera, new b2Vec2(375, 190), PegActor.GOAL);
			//_allActors.push(centerPeg1);
			
			var centerPeg2:PegActor = new PegActor(_camera, new b2Vec2(375, 240), PegActor.GOAL);
			_allActors.push(centerPeg2);
			
			var centerPeg3:PegActor = new PegActor(_camera, new b2Vec2(375, 290), PegActor.GOAL);
			_allActors.push(centerPeg3);
			
			var centerPeg4:PegActor = new PegActor(_camera, new b2Vec2(375, 340), PegActor.GOAL);
			_allActors.push(centerPeg4);
			
			var centerPeg5:PegActor = new PegActor(_camera, new b2Vec2(375, 390), PegActor.GOAL);
			_allActors.push(centerPeg5);
			
			var centerPeg6:PegActor = new PegActor(_camera, new b2Vec2(375, 440), PegActor.GOAL);
			_allActors.push(centerPeg6);
			
			var centerPeg7:PegActor = new PegActor(_camera, new b2Vec2(375, 490), PegActor.GOAL);
			_allActors.push(centerPeg7);
			
			//var centerPeg8:PegActor = new PegActor(_camera, new b2Vec2(375, 540), PegActor.GOAL);
			//_allActors.push(centerPeg8);
			
			// Left pegs 
			var leftPeg1:PegActor = new PegActor(_camera, new b2Vec2(320, 290), PegActor.NORMAL);
			_allActors.push(leftPeg1);
			
			var leftPeg2:PegActor = new PegActor(_camera, new b2Vec2(320, 340), PegActor.NORMAL);
			_allActors.push(leftPeg2);
			
			var leftPeg3:PegActor = new PegActor(_camera, new b2Vec2(320, 390), PegActor.NORMAL);
			_allActors.push(leftPeg3);
			
			var leftPeg4:PegActor = new PegActor(_camera, new b2Vec2(320, 440), PegActor.NORMAL);
			_allActors.push(leftPeg4);
			
			// Right pegs
			var rightPeg1:PegActor = new PegActor(_camera, new b2Vec2(430, 290), PegActor.NORMAL);
			_allActors.push(rightPeg1);
			
			var rightPeg2:PegActor = new PegActor(_camera, new b2Vec2(430, 340), PegActor.NORMAL);
			_allActors.push(rightPeg2);
			
			var rightPeg3:PegActor = new PegActor(_camera, new b2Vec2(430, 390), PegActor.NORMAL);
			_allActors.push(rightPeg3);
			
			var rightPeg4:PegActor = new PegActor(_camera, new b2Vec2(430, 440), PegActor.NORMAL);
			_allActors.push(rightPeg4);
			
			// Far left peg
			var farLeftPeg:PegActor = new PegActor(_camera, new b2Vec2(270, 365), PegActor.GOAL);
			_allActors.push(farLeftPeg);
			
			// Far right peg
			var farRightPeg:PegActor = new PegActor(_camera, new b2Vec2(480, 365), PegActor.GOAL);
			_allActors.push(farRightPeg);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(375, 150));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(375, 575), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Diamonds are Forever");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelNine():void
		{
			var sideBoundShape:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 600), new Point(0, 600)]];
			var topShape:Array = [[new Point(0, 0), new Point(610, 0), new Point(610, 10), new Point(0, 10)]];
			var sideBlockShape:Array = [[new Point(0, 0), new Point(20, 0), new Point(20, 200), new Point(0, 200)]];
			var backBlockShape:Array = [[new Point(0, 0), new Point(155, 0), new Point(155, 20), new Point(0, 20)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(50, 100), sideBoundShape, null, true, 0x0000FF);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(650, 100), sideBoundShape, null, true, 0x0000FF);
			_allActors.push(rightBound);
			
			var topBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(50, 100), topShape, null, true, 0x0000FF);
			_allActors.push(topBound);
			
			var bottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(50, 700), topShape, null, true, 0x0000FF);
			_allActors.push(bottomBound);
			
			var leftSideBlock:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(275, 300), sideBlockShape, null, true, 0x0000FF);
			_allActors.push(leftSideBlock);
			
			var rightSideBlock:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(410, 300), sideBlockShape, null, true, 0x0000FF);
			_allActors.push(rightSideBlock);
			
			var backBlock:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(275, 500), backBlockShape, null, true, 0x0000FF);
			_allActors.push(backBlock);
			
			// Create the three ramps
			var sideRampShapes:Array = [[new Point(0, 0), new Point(212, 0), new Point(212, 300), new Point(0, 300)]];
			var middleRampShape:Array = [[new Point(0, 0), new Point(300, 0), new Point(300, 200), new Point(0, 200)]];
			var centerRampShape:Array = [[new Point(0, 0), new Point(157, 0), new Point(157, 185), new Point(0, 185)]];
			var cupRampShape:Array = [[new Point(0, 0), new Point(110, 0), new Point(110, 75), new Point(0, 75)]];
			
			var leftRamp:RampActor = new RampActor(_camera, new Point(61, 112), sideRampShapes, 0x00FF00, new b2Vec2(0.0, 0.2), 0.3);
			_allActors.push(leftRamp);
			
			var rightRamp:RampActor = new RampActor(_camera, new Point(433, 112), sideRampShapes, 0x00FF00, new b2Vec2(0.0, 0.2), 0.3);
			_allActors.push(rightRamp);
			
			var centerTopRamp:RampActor = new RampActor(_camera, new Point(275, 112), centerRampShape, 0x009900, new b2Vec2(0.0, 0.1), 0.2);
			_allActors.push(centerTopRamp);
			
			var cupRamp:RampActor = new RampActor(_camera, new Point(297, 425), cupRampShape, 0x009000, new b2Vec2(0.0, -0.2), 0.2);
			_allActors.push(cupRamp);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(350, 450));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(350, 650), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
		
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("The Pit");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 3");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelTen():void
		{
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(260, 350), new Point(250, 350)]];
			var otherWallShapes:Array = [[new Point(250, 0), new Point(260, 0), new Point(10, 350), new Point(0, 350)]];
			
			// Cap shapes
			var capShapes:Array = [[new Point(0, 0), new Point(150, 0), new Point(150, 10), new Point(0, 10)]];
			
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(370, 45), otherWallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
		
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(220, 395), wallShapes, null, true, 0x000000);
			_allActors.push(leftWall);
			
			var lowerRightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(370, 395), wallShapes, null, true, 0x000000);
			_allActors.push(lowerRightWall);
			
			var upperLeftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(220, 45), otherWallShapes, null, true, 0x000000);
			_allActors.push(upperLeftWall);
			
			var topCap:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(470, 45), capShapes, null, true, 0x000000);
			_allActors.push(topCap);
			
			var bottomCap:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(470, 735), capShapes, null, true, 0x000000);
			_allActors.push(bottomCap);
			
			var centerPeg1:PegActor = new PegActor(_camera, new b2Vec2(445, 200), PegActor.GOAL);
			_allActors.push(centerPeg1);
			
			var centerPeg2:PegActor = new PegActor(_camera, new b2Vec2(260, 390), PegActor.GOAL);
			_allActors.push(centerPeg2);
			
			var centerPeg3:PegActor = new PegActor(_camera, new b2Vec2(350, 390), PegActor.GOAL);
			_allActors.push(centerPeg3);
			
			var centerPeg4:PegActor = new PegActor(_camera, new b2Vec2(445, 590), PegActor.GOAL);
			_allActors.push(centerPeg4);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(520, 100));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(520, 700), new Point( 0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("V for Victory");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelEleven():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 603), new Point(0, 603)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(-9, 0), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(645, 0), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, 593), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(0, -5), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the four ramps
			var rampShapes:Array = [[new Point(0, 0), new Point(100, 0), new Point(100, 100), new Point(0, 100)]];
			
			var topLeftRamp:RampActor = new RampActor(_camera, new Point(180, 150), rampShapes, 0x00EE00, new b2Vec2(0.1, 0.1), 0.15);
			_allActors.push(topLeftRamp);
			
			var topRightRamp:RampActor = new RampActor(_camera, new Point(280, 150), rampShapes, 0x00EE00, new b2Vec2(-0.1, 0.1), 0.2);
			_allActors.push(topRightRamp);
			
			var bottomLeftRamp:RampActor = new RampActor(_camera, new Point(180, 250), rampShapes, 0x00EE00, new b2Vec2(0.1, -0.1), 0.15);
			_allActors.push(bottomLeftRamp);
			
			var bottomRightRamp:RampActor = new RampActor(_camera, new Point(280, 250), rampShapes, 0x00EE00, new b2Vec2(-0.1, -0.1), 0.2);
			_allActors.push(bottomRightRamp);
			
			// Create the center block
			var blockShape:Array = [[new Point(0, 0), new Point(100, 0), new Point(100, 10), new Point(0, 10)]];
			
			var block:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(230, 355), blockShape, null, true, 0x0000EE);
			_allActors.push(block);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(280, 250));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(280, 500), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("The Bowl");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 1");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelTwelve():void 
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 300), new Point(0, 300)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(655, 100), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 400), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the four ramps
			var rampShapes:Array = [[new Point(0, 0), new Point(550, 0), new Point(550, 270), new Point(0, 270)]];
			
			var topLeftRamp:RampActor = new RampActor(_camera, new Point(22, 112), rampShapes, 0x00EE00, new b2Vec2(0.05, 0.1), 0.20);
			_allActors.push(topLeftRamp);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(50, 150));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(600, 250), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("The Curve");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelThirteen():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 603), new Point(0, 603)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(1, 0), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(655, 0), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 593), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, -5), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			var blockShape:Array = [[new Point(0, 0), new Point(575, 0), new Point(575, 400), new Point(0, 400)]];
			
			var block:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(45, 75), blockShape, null, true, 0x0000BB);
			_allActors.push(block);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(325, 50));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(325, 525), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Blockage");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 3");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelFourteen():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 300), new Point(0, 300)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(655, 100), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 400), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the four ramps
			var rampShapes:Array = [[new Point(0, 0), new Point(630, 0), new Point(630, 80), new Point(0, 80)]];
			
			var topRamp:RampActor = new RampActor(_camera, new Point(22, 142), rampShapes, 0x00EE00, new b2Vec2(-0.09, -0.1), 0.20);
			_allActors.push(topRamp);
			
			var bottomRamp:RampActor = new RampActor(_camera, new Point(22, 282), rampShapes, 0x00EE00, new b2Vec2(-0.09, 0.1), 0.20);
			_allActors.push(bottomRamp);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(620, 250));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(200, 390), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("The Ridge");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelFifteen():void
		{
			// Add the ramps
			var rampShape:Array = [[new Point(0, 0), new Point(190, 0), new Point(190, 590), new Point(0, 590)]];
			
			var middleRamp:RampActor = new RampActor(_camera, new Point(190, 70), rampShape, 0x00BF00, new b2Vec2(0.0, 0.3), 0.1);
			//_allActors.push(middleRamp);
			
			// Create the bounds of the course
			var sideBoundShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 700), new Point(0, 700)]];
			
			var leftBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 20), sideBoundShapes, null, true, 0x000000);
			_allActors.push(leftBound);
			
			var rightBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(380, 20), sideBoundShapes, null, true, 0x000000);
			_allActors.push(rightBound);
			
			var backBoundShapes:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 10), new Point(0, 10)]];
			
			var backBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 20), backBoundShapes, null, true, 0x000000);
			_allActors.push(backBound);
			
			var bottomBound:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(180, 700), backBoundShapes, null, true, 0x000000);
			_allActors.push(bottomBound);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(210, 50));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(290, 700), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Tower of Babel");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 1");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelSixteen():void
		{
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 300), new Point(0, 300)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(655, 100), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			
			// Create the back walls
			var backWallShapes:Array = [[new Point(0, 0), new Point(655, 0), new Point(655, 10), new Point(0, 10)]];
			
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 100), backWallShapes, null, true, 0x000000);
			_allActors.push(backWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(10, 400), backWallShapes, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the series of ramps
			var rampShapes:Array = [[new Point(0, 0), new Point(50, 0), new Point(50, 285), new Point(0, 285)]];
			
			var ramp1:RampActor = new RampActor(_camera, new Point(550, 112), rampShapes, 0x00E000, new b2Vec2(-0.02, -0.2), 0.20);
			_allActors.push(ramp1);
			
			var ramp2:RampActor = new RampActor(_camera, new Point(500, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, 0.2), 0.20);
			_allActors.push(ramp2);
			
			var ramp3:RampActor = new RampActor(_camera, new Point(450, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, -0.2), 0.20);
			_allActors.push(ramp3);
			
			var ramp4:RampActor = new RampActor(_camera, new Point(400, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, 0.2), 0.20);
			_allActors.push(ramp4);
			
			var ramp5:RampActor = new RampActor(_camera, new Point(350, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, -0.2), 0.20);
			_allActors.push(ramp5);
			
			var ramp6:RampActor = new RampActor(_camera, new Point(300, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, 0.2), 0.20);
			_allActors.push(ramp6);
			
			var ramp7:RampActor = new RampActor(_camera, new Point(250, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, -0.2), 0.20);
			_allActors.push(ramp7);
			
			var ramp8:RampActor = new RampActor(_camera, new Point(200, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, 0.2), 0.20);
			_allActors.push(ramp8);
			
			var ramp9:RampActor = new RampActor(_camera, new Point(150, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, -0.2), 0.20);
			_allActors.push(ramp9);
			
			var ramp10:RampActor = new RampActor(_camera, new Point(100, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, 0.4), 0.20);
			_allActors.push(ramp10);
			
			//var ramp11:RampActor = new RampActor(_camera, new Point(50, 112), rampShapes, 0x00D000, new b2Vec2(-0.02, -0.2), 0.20);
			//_allActors.push(ramp11);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(35, 250));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(620, 250), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("Seriously?");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 1");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
		
		private function loadLevelSeventeen():void
		{
			// straight side walls
			// Create the side walls
			// Add the side walls
			var wallShapes:Array = [[new Point(0, 0), new Point(10, 0), new Point(10, 300), new Point(0, 300)]];
			
			var leftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(250, 400), wallShapes ,null, true, 0x000000);
			_allActors.push(leftWall);
		
			var rightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(450, 400), wallShapes, null, true, 0x000000);
			_allActors.push(rightWall);
			
			var backWallShape:Array = [[new Point(0, 0), new Point(210, 0), new Point(210, 10), new Point(0, 10)]];
			 
			var backWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(250, 700), backWallShape, null, true, 0x000000);
			_allActors.push(backWall);
			
			var lowerShortWallShapes:Array = [[new Point(0, 0), new Point(200, 0), new Point(200, 10), new Point(0, 10)]];
			
			var lowerShortLeft:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(50, 400), lowerShortWallShapes, null, true, 0x000000);
			_allActors.push(lowerShortLeft);
			
			var lowerShortRight:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(450, 400), lowerShortWallShapes, null, true, 0x000000);
			_allActors.push(lowerShortRight);
			
			var leftAngleShape:Array = [[new Point(200, 0), new Point(10, 200), new Point(0, 200), new Point(190, 0)]];
			
			var leftAngle:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(50, 200), leftAngleShape, null, true, 0x000000);
			_allActors.push(leftAngle);
			
			var rightAngleShape:Array = [[ new Point(0, 0), new Point(10, 0), new Point(200, 200), new Point(190, 200)]];
			
			var rightAngle:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(450, 200), rightAngleShape, null, true, 0x000000);
			_allActors.push(rightAngle);
			
			var upperSideWalls:Array = [[ new Point(0, 0), new Point(10, 0), new Point(10, 150), new Point(0, 150) ]];
			
			var upperLeftWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(240, 50), upperSideWalls, null, true, 0x000000);
			_allActors.push(upperLeftWall);
			
			var upperRightWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(450, 50), upperSideWalls, null, true, 0x000000);
			_allActors.push(upperRightWall);
			
			var topWall:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(250, 50), backWallShape, null, true, 0x000000);
			_allActors.push(topWall);
			
			// Create the ramps
			var lowerRampShape:Array = [[ new Point(0, 0), new Point(180, 0), new Point(180, 200), new Point(0, 200) ]];
			var upperRampShape:Array = [[ new Point(0, 0), new Point(190, 0), new Point(190, 100), new Point(0, 100) ]];
			var leftRampShape:Array = [[ new Point(0, 0), new Point(100, 0), new Point(100, 150), new Point(0, 150) ]];
			//var rightRampShape:Array = [[ new Point(100, 150), new Point(100, 0), new Point(0, 0), new Point(0, 150) ]];
			
			var lowerRamp:RampActor = new RampActor(_camera, new Point(265, 410), lowerRampShape, 0x00dd00, new b2Vec2( -0.2, 0.3), 0.1);
			_allActors.push(lowerRamp);
			
			var upperRamp:RampActor = new RampActor(_camera, new Point(255, 100), upperRampShape, 0x00aa00, new b2Vec2(0.1, -0.2), 0.2);
			_allActors.push(upperRamp);
			
			var leftRamp:RampActor = new RampActor(_camera, new Point(210, 245), leftRampShape, 0x00aa00, new b2Vec2( -0.2, 0.0), 0.2);
			_allActors.push(leftRamp);
			
			var rightRamp:RampActor = new RampActor(_camera, new Point(390, 245), leftRampShape, 0x00aa00, new b2Vec2(0.3, 0.0), 0.2);
			_allActors.push(rightRamp);
			
			// create block
			var blockShape:Array = [[ new Point(0, 0), new Point(50, 0), new Point(50, 50), new Point(0, 50) ]];
			
			var block:ArbitraryStaticActor = new ArbitraryStaticActor(_camera, new Point(325, 280), blockShape, null, true, 0x0000AA);
			_allActors.push(block);
			
			// Create the hole
			_cup = new CupActor(_camera, new Point(350, 250));
			
			// Create the ball
			_ball = new BallActor(_camera, new Point(350, 650), new Point(0, 0));
			_ball.addEventListener(BallEvent.BALL_IN_CUP, handleBallInCup);
			
			_allActors.push(_ball);
			
			// Load font
			var chillerFont:Font = new ChillerFont();
			var textFormat:TextFormat = new TextFormat();
			textFormat.size = 32;
			textFormat.align = TextFormatAlign.LEFT;
			textFormat.font = chillerFont.fontName;
			
			// Show the details of the level
			_levelNameField = new TextField();
			_levelNameField.defaultTextFormat = textFormat;
			_levelNameField.x = 700;
			_levelNameField.y = 50;
			_levelNameField.width = 200;
			_levelNameField.appendText("The Pagoda");
			_levelNameField.antiAliasType = AntiAliasType.ADVANCED;
			_levelNameField.selectable = false;
			_levelNameField.textColor = 0xffffff;
			_camera.addChild(_levelNameField);
			
			_levelParField = new TextField();
			_levelParField.defaultTextFormat = textFormat;
			_levelParField.x = 700;
			_levelParField.y = 75;
			_levelParField.width = 200;
			_levelParField.appendText("Par 2");
			_levelParField.antiAliasType = AntiAliasType.ADVANCED;
			_levelParField.selectable = false;
			_levelParField.textColor = 0xffffff;
			_camera.addChild(_levelParField);
			
			_levelStrokeField = new TextField();
			_levelStrokeField.defaultTextFormat = textFormat;
			_levelStrokeField.x = 700;
			_levelStrokeField.y = 125;
			_levelStrokeField.width = 200;
			_levelStrokeField.appendText("Strokes: 0");
			_levelStrokeField.antiAliasType = AntiAliasType.ADVANCED;
			_levelStrokeField.selectable = false;
			_levelStrokeField.textColor = 0xffffff;
			_camera.addChild(_levelStrokeField);
		}
	}

}