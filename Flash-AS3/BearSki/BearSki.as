package  
{
	import flash.display.LoaderInfo;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.errors.IllegalOperationError;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.ProgressEvent;
	import flash.events.TimerEvent;
	import flash.geom.Point;
	import flash.ui.Keyboard;
	import flash.utils.Timer;
	import game.Camera;
	import game.CollidableObject;
	import game.Defines;
	import game.KeyHandler;
	import game.Level;
	import game.Player;
	import game.PlayerStatistics;
	import ui.Scoreboard;
	
	// Kongregate API import
	import com.kongregate.as3.client.KongregateAPI;
	import com.kongregate.as3.client.events.KongregateEvent;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class BearSki extends MovieClip
	{
		private var _loadingScreen:MovieClip;
		private var _userName:String;
		private var _userId:Number;
		private var _authToken:String;
		
		private var _keyHandler:KeyHandler;
		private var _camera:Camera;
		private var _player:Player;
		
		private var _useKongregate:Boolean = true;
		
		// These are level specific stuffs
		// As objects are added, once off-screen, reuse the sprites so that we don't destroy objects all the time
		//private var _objects:Vector.<CollidableObject>;
		private var _objects:Array;
		private var _flags:Array;
		
		private var _timer:Timer;
		private var _crashTimer:Timer;
		
		private var _distanceTraveled:uint;
		private var _crashCount:uint;
		private var _slalomInterval:uint;
		private var _slalomPoints:uint;
		private var _slalomsTotal:uint;
		private var _distBetweenCheckpoints:int;
		
		private var _lastFlag:uint;
		private var _blueFlagsActive:Boolean;
		private var _redFlagsActive:Boolean;
		
		private var _scoreBoard:Scoreboard;
		private var _playerStatistics:PlayerStatistics;
		private var _endGame:Boolean;
		private var _totalTime:uint;
		
		//private var _currentLevel:Level;
		//private var _endTrack:Boolean;
		
		// LEVEL SELECTOR - camera will zoom out, you will sled over to the area you want to go down
		// have levels like green difficulty, black, blue
		// Make slide animations (45, 60 degrees)  DONE
		// Make scream face   DONE
		// Make arms out animation  DONE
		
		// Make edge graphics so that the character could fall off of the edge (sides)
		// Make Loading screen
		// Make Character Select screen
		// Make Menu
		
		public function BearSki() 
		{
			_loadingScreen = new LoadingScreen();
			_loadingScreen.x = 400;
			_loadingScreen.y = 400;
			_loadingScreen.percentDisplay.text = "0";
			
			addChild(_loadingScreen);
			
			gotoAndStop(1);
			loaderInfo.addEventListener(Event.COMPLETE, onLoadComplete);
			loaderInfo.addEventListener(ProgressEvent.PROGRESS, onProgressMade);
		}
		
		private function onProgressMade(e:ProgressEvent):void 
		{
			_loadingScreen.percentDisplay.text = Math.floor( 100 * loaderInfo.bytesLoaded / loaderInfo.bytesTotal );
		}
		
		private function onLoadComplete(e:Event):void 
		{
			removeChild(_loadingScreen);
			gotoAndStop(3);
			
			if (_useKongregate)
			{
				try 
				{
					var kongregate:KongregateAPI = new KongregateAPI();
					kongregate.addEventListener(KongregateEvent.COMPLETE, onKongregateConnected);
					this.addChild (kongregate);
				}
				catch ( error:IllegalOperationError )
				{
					trace( error );
					kongregate = KongregateAPI.getInstance();
				}
	  
				trace( kongregate ); // [object KongregateAPI]
			}
			else
			{
				onConnect();
			}
		}
		
		// This function is for the non-Kongregate version of the game
		private function onConnect():void
		{
			// Drive all below via menu options
			loadGame();
		}
		
		private function onKongregateConnected(e:KongregateEvent):void 
		{
			var kongregate:KongregateAPI = KongregateAPI.getInstance();
			var name:String = kongregate.user.getName();
			
			if (name != "Guest")
			{
				_userName = name;
			}
			else
			{
				_userName = "Guest";
			}
			
			// Load Menu Here
			
			// Select game mode
			/*var menu:MovieClip = new MenuScreen();
			menu.x = 400;
			menu.y = 400;
			addChild(menu);
			*/
			// Drive all below via menu options
			loadGame();  // this will include game mode that is passed in to determine what to do
		}

		private function loadGame():void 
		{
			// if load game
			// loadGame();
			// else
			_playerStatistics = new PlayerStatistics();
			
			// Initialize Key Handler
			_keyHandler = new KeyHandler();
			
			// Initialize the camera
			_camera = new Camera();
			addChild(_camera);
			
			// Add score board
			_scoreBoard = new Scoreboard(this, _userName);
			
			var bear:MovieClip = new BearBlack();
			var sled:MovieClip = new BasicSled();

			//_currentLevel = new Level(_camera, "Easy", 4000, Defines.GREEN_FLAG, false, 2);
			_objects = [];//_currentLevel.loadBatch().concat();
			
			_timer = new Timer(200, 0);
			_timer.addEventListener(TimerEvent.TIMER, onTimer);
			_timer.start();
			
			// this needs to be evaluated based on saved games as well
			_distBetweenCheckpoints = _scoreBoard.checkpointCounter * Defines.BASE_DISTANCE * 0.1;
			
			if (_distBetweenCheckpoints == 0) _distBetweenCheckpoints = Defines.BASE_DISTANCE;
			
			_scoreBoard.updateDistanceUntilCheckpoints(_distBetweenCheckpoints);
			
			trace(_distBetweenCheckpoints);
			
			_distanceTraveled = 0;
			_crashCount = _playerStatistics.crashes;
			_slalomInterval = 0;
			_slalomPoints = 0;
			_slalomsTotal = 0;
			_totalTime = 0;
			
			// flag-related variables
			_blueFlagsActive = false;
			_redFlagsActive = false;
			_lastFlag = 1;
			
			loadFlags();
			//_endTrack = false;
			
			var pos:Point = new Point(this.stage.stageWidth * 0.5, Defines.RESTART_PLAYER_Y);
			
			_player = new Player(_camera, bear, sled, "Bub", _userName, true);
			_player.pos = pos;
			
			_camera.zoomOut(new Point(bear.x, bear.y));
			
			_endGame = false;
			
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
			stage.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
			
			stage.addEventListener(Event.ENTER_FRAME, updateGame);
		}
		
		private function loadFlags():void
		{
			_flags = new Array(new CollidableObject(_camera, Defines.FLAG_BLUE, new Point(0, Defines.RESTART_Y)),
							   new CollidableObject(_camera, Defines.SLALOM_CROSS, new Point(0, Defines.RESTART_Y)),
							   new CollidableObject(_camera, Defines.FLAG_BLUE_R, new Point(0, Defines.RESTART_Y)),
							   new CollidableObject(_camera, Defines.FLAG_RED, new Point(0, Defines.RESTART_Y)),
					           new CollidableObject(_camera, Defines.SLALOM_CROSS, new Point(0, Defines.RESTART_Y)),
					           new CollidableObject(_camera, Defines.FLAG_RED_R, new Point(0, Defines.RESTART_Y)));
							   
			_objects.push(_flags[0]);
			_objects.push(_flags[1]);
			_objects.push(_flags[2]);
			_objects.push(_flags[3]);
			_objects.push(_flags[4]);
			_objects.push(_flags[5]);
		}
		
		private function updateGame(e:Event):void 
		{
			if (_player.currentAction != Defines.STOPPED && _player.currentAction != Defines.CRASHED && 
			    _player.currentAction != Defines.FLYING)
			{
				if (_keyHandler.isKeyPressed(KeyHandler.LEFT) && !_keyHandler.isKeyPressed(KeyHandler.SPACE))
				{
					_player.move(Defines.MOVE_LEFT);
				}
				else if (_keyHandler.isKeyPressed(KeyHandler.LEFT) && _keyHandler.isKeyPressed(KeyHandler.SPACE))
				{
					_player.move(Defines.MOVE_HARD_LEFT);
					
				}
				else if (_keyHandler.isKeyPressed(KeyHandler.RIGHT) && !_keyHandler.isKeyPressed(KeyHandler.SPACE))
				{
					_player.move(Defines.MOVE_RIGHT);
				}
				else if (_keyHandler.isKeyPressed(KeyHandler.RIGHT) && _keyHandler.isKeyPressed(KeyHandler.SPACE))
				{
					_player.move(Defines.MOVE_HARD_RIGHT);
				}
				else if (_keyHandler.isKeyPressed(KeyHandler.DOWN) && !_keyHandler.isKeyPressed(KeyHandler.LEFT) && !_keyHandler.isKeyPressed(KeyHandler.RIGHT))
				{
					_player.move(Defines.SPEED_UP);
				}
				else if (_keyHandler.isKeyPressed(KeyHandler.SPACE) && !_player.onGround)
				{
					_player.move(Defines.SPIN);
					_scoreBoard.updateStyle(4);
				}
				else 
				{
					if (_player.onGround)
					{
						if ((_player.bear.rotation >= 91 && _player.bear.rotation <= 180) || 
						    (_player.bear.rotation <= -91 && _player.bear.rotation >= -180))
						{
							_player.move(Defines.CRASHED);
							_scoreBoard.updateCrashes(1);
						}
						else
						{
							_player.move(Defines.STRAIGHTEN_OUT);
						}
					}
				}
		
				// check for collisions
				if (_player.onGround)
				{
					for each (var obj:CollidableObject in _objects)
					{		
						if (obj.display.hitTestObject(_player.sled))
						{
							if ((_player.sled.y >= obj.pos.y - obj.yBound && _player.sled.y <= obj.pos.y + obj.yBound) && 
							    (_player.sled.x >= obj.pos.x - obj.xBound && _player.sled.x <= obj.pos.x + obj.xBound))
							{
								// determine the type of object hit
								if (obj.collisionType == Defines.TREE_TYPE || obj.collisionType == Defines.ROCK_TYPE)
								{
									_player.move(Defines.CRASHED);
									_player.collidingObject = obj.display;
									_scoreBoard.updateCrashes(1);
									obj.collided = true;
								}
								else if (obj.collisionType == Defines.RAMP_TYPE)
								{
									_player.move(Defines.JUMP);
									_player.jump(obj.rampHeight, obj.steepness);
									_player.collidingObject = obj.display;
									obj.collided = true;
								}
								else if (obj.collisionType == Defines.FLAG_TYPE)
								{
									// maybe player should slow down a little?
									_player.move(Defines.SLOW_DOWN);
									_player.collidingObject = obj.display;
									obj.collided = true;
								}
								else if (obj.collisionType == Defines.SLALOM_TYPE)
								{
									//trace("Hit slalom cross", obj.collided);
									if (!obj.collided)
									{
										updateSlalomCheckPoints();
										obj.collided = true;
									}
								}
							}
							
							// do other game related things here depending upon the game type
						}
					}
				}
			}
			
			// call character update
			_player.update();
			
			// update game objects
			// this is distance travelled in feet
			_distanceTraveled = (_player.speed * 0.1);
			scrollLevel();
			
			_scoreBoard.updateDistance(_distanceTraveled);
			//trace("distance: " + _scoreBoard.distanceUntilCheckpoint);
			
			if (_scoreBoard.distanceUntilCheckpoint <= 1)
			{
				// check for win/loss conditions
				if (_scoreBoard.funPoints <= 50)
				{
					trace ("Did not qualify for next check point!");
					_endGame = true;
					
				}
				else
				{
					updateCheckpoints();
				}
				//trace("Hit checkpoint");
				
				_playerStatistics.updateStats(_scoreBoard.stylePoints, _scoreBoard.checkpointCounter, 
				                              _scoreBoard.flagsMade, _scoreBoard.flagsMissed, _scoreBoard.funPoints,
											  _scoreBoard.distance, _totalTime, _scoreBoard.crashCounter);
			}
			
			
			if (_endGame)
			{
				stage.removeEventListener(Event.ENTER_FRAME, updateGame);
				
				// load game over screen
			}
			// update fun points based on style
			
			
			
		}
		
		private function updateCheckpoints():void
		{
			_scoreBoard.updateCheckpointCounter();
			_scoreBoard.resetUponCheckpoint();
			
			_distanceTraveled = 0;
			
			//trace("Checkpoint counter: " + _scoreBoard.checkpointCounter);
			_distBetweenCheckpoints += _scoreBoard.checkpointCounter * Defines.BASE_DISTANCE * 0.1;
			_scoreBoard.updateDistanceUntilCheckpoints(_distBetweenCheckpoints);
			
			//trace("New distance between checkpoints: " + _distBetweenCheckpoints)
		}
		
		private function updateSlalomCheckPoints():void
		{
			_slalomPoints++;
			_scoreBoard.updateFlagsMade();
			//trace("You've hit " + _slalomPoints + " out of " + _slalomsTotal + "!" );
		}
		
		// These should be per level, defined elsewhere
		private function scrollLevel():void
		{
			var speed = _player.speed;
			
			if (speed != 0)
			{
				// call object update
				for each (var obj:CollidableObject in _objects)
				{
					if (obj.collisionType != Defines.FLAG_TYPE && obj.collisionType != Defines.SLALOM_TYPE)
					{
						obj.update(speed);
							
						if (obj.pos.y < Defines.OBJ_HORIZON)
						{
							obj.offScreen = true;
							//obj.pastPlayer = true;
						}
					}
				}
				
				if (_blueFlagsActive)
				{
					CollidableObject(_flags[0]).update(speed);
					CollidableObject(_flags[1]).update(speed);
					CollidableObject(_flags[2]).update(speed);
				}
				else if (_redFlagsActive)
				{
					CollidableObject(_flags[3]).update(speed);
					CollidableObject(_flags[4]).update(speed);
					CollidableObject(_flags[5]).update(speed);
				}
			}
			
			
		}
		
		private function addObject():void
		{
			// 1. check event-based things (slalom)
			var slalomEvent:Boolean = false;
			_slalomInterval += 200;
			
			_totalTime += 200;
			
			if (_slalomInterval == Defines.SLALOM_INTERVAL)
			{
				_slalomInterval = 0;
				slalomEvent = true;
			}
			
			// 2. check difficulty level
			// could use a crash counter that would end the game
			var difficultyMultiplier:Number = 0.5;
			
			// 3. run random check to see if we will even add anything on this tick
			var addObject:Boolean = false;
			addObject = (Math.random() * difficultyMultiplier > 0.4 ? addObject = true : addObject = false);

			// third, determine if it is going to be a single item or a batch of items
			
			if (addObject)
			{
				// determine if batch of items is to be added
				//var objCount:uint = Math.floor(Math.random() * difficultyMultiplier + (2 * Math.random()));
				var objCount:uint = 1;
				
				for (var i:int = 0; i < objCount; i++)
				{
					// select random type from lists of objects
					var rand:uint = Math.floor(Math.random() * Defines.ALL_COUNT);
					var displayType:uint = Defines.ALL_OBJECTS[rand];

					var foundExistingObject:Boolean = false;
					
					// see if there is an offscreen collider object that is already of that type
					for each (var colObj:CollidableObject in _objects)
					{
						// if there is, get that object, set the offscreen flag to false, replace at bottom of screen
						if (colObj.offScreen && colObj.displayType == displayType)
						{
							foundExistingObject = true;
							colObj.reset(Defines.RESTART_Y);
							break;
						}
					}

					if (!foundExistingObject)
					{
						// if not, create a new collider object, place at bottom of screen
						var obj:CollidableObject = 
						   new CollidableObject(_camera, displayType, new Point(Math.random() * Defines.WIDTH, Defines.RESTART_Y));
						_objects.push(obj);
						_camera.addChild(obj.display);

					}
				}
			}
			
			if (slalomEvent)
			{
				_slalomsTotal++;
				_scoreBoard.updateFlagsMissed();
				
				if (_lastFlag == 1)
				{
					_blueFlagsActive = true;
					_redFlagsActive = false;
					CollidableObject(_flags[0]).flagReset(Defines.RESTART_Y, -1);
					CollidableObject(_flags[1]).flagReset(Defines.RESTART_Y, CollidableObject(_flags[0]).pos.x);
					CollidableObject(_flags[2]).flagReset(Defines.RESTART_Y, CollidableObject(_flags[1]).pos.x);
					_lastFlag = 0;
				}
				else
				{
					_redFlagsActive = true;
					_blueFlagsActive = false;
					CollidableObject(_flags[3]).flagReset(Defines.RESTART_Y, -1);
					CollidableObject(_flags[4]).flagReset(Defines.RESTART_Y, CollidableObject(_flags[3]).pos.x);
					CollidableObject(_flags[5]).flagReset(Defines.RESTART_Y, CollidableObject(_flags[4]).pos.x);
					_lastFlag = 1;
				}
			}
		}
		
		private function onTimer(e:TimerEvent):void 
		{
			// Get player's current speed
			var speed = _player.speed;
			
			if (speed != 0)
			{
				addObject();

			}
		}
	
		// Event Handler for key up events
		private function onKeyUp(e:KeyboardEvent):void 
		{
			if (e.keyCode == Keyboard.LEFT)
			{
				_keyHandler.removeKey(KeyHandler.LEFT);
			}
			else if (e.keyCode == Keyboard.RIGHT)
			{
				_keyHandler.removeKey(KeyHandler.RIGHT);
			}
			else if (e.keyCode == Keyboard.DOWN)
			{
				_keyHandler.removeKey(KeyHandler.DOWN);
			}
			else if (e.keyCode == Keyboard.UP)
			{
				_keyHandler.removeKey(KeyHandler.UP);
			}
			else if (e.keyCode == Keyboard.SPACE)
			{
				_keyHandler.removeKey(KeyHandler.SPACE);
			}
		}
		
		// Event Handler for key down events
		private function onKeyDown(e:KeyboardEvent):void 
		{
			if (e.keyCode == Keyboard.LEFT)
			{
				_keyHandler.addKey(KeyHandler.LEFT);
			}
			else if (e.keyCode == Keyboard.RIGHT)
			{
				_keyHandler.addKey(KeyHandler.RIGHT);
			}
			else if (e.keyCode == Keyboard.DOWN)
			{
				_keyHandler.addKey(KeyHandler.DOWN);
			}
			else if (e.keyCode == Keyboard.UP)
			{
				_keyHandler.addKey(KeyHandler.UP);
			}
			else if (e.keyCode == Keyboard.SPACE)
			{
				_keyHandler.addKey(KeyHandler.SPACE);
			}
			
		}
		
		
		
	}

}