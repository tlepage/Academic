package  
{
	import Box2D.Collision.b2AABB;
	import Box2D.Collision.Shapes.b2Shape;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.Joints.b2MouseJoint;
	import Box2D.Dynamics.Joints.b2MouseJointDef;
	import com.actionsnippet.qbox.QuickBox2D;
	import com.actionsnippet.qbox.QuickContacts;
	import com.actionsnippet.qbox.QuickObject;
	import fl.controls.ProgressBar;
	import fl.controls.ProgressBarDirection;
	import fl.controls.ProgressBarMode;
	import fl.controls.TextArea;
	import flash.display.DisplayObject;
	import flash.display.MovieClip;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.ProgressEvent;
	import flash.filters.BlurFilter;
	import flash.geom.Point;
	import flash.media.Sound;
	import flash.system.System;
	import flash.text.Font;
	import flash.text.TextField;
	import flash.ui.Keyboard;
	import flash.ui.Mouse;
	import flash.utils.Dictionary;
	import Scenes.CemetaryScene;
	import Scenes.CreepyTreeScene;
	import Scenes.FallTimeScene;
	import Scenes.GameScene;
	import Scenes.HauntedHouseScene;
	import Scenes.StackOLanternScene;
	
	// Kongregate API import
	import com.kongregate.as3.client.KongregateAPI;
	import com.kongregate.as3.client.events.KongregateEvent;
	import flash.errors.IllegalOperationError;
	
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class Stacks extends MovieClip
	{
		private var _mainMenu:MovieClip;
		
		private var _world:QuickBox2D;
		private var _camera:Camera;
		private var _director:Director;
		private var _timeMaster:TimeMaster;
		private var _scoreboard:Scoreboard;
		
		// Container for all characters on the screen
		private var _characterDictionary:Dictionary;
		private var _characters:Array;
		
		// Mouse drag related variables
		private var _isDragging:Boolean;
		private var _dragObject:QuickObject;
		private var _dragCharacter:Character;
		
		private var _mouseJoint:b2MouseJoint;
		
		private var _contacts:QuickContacts;
		
		private var _themes:Array;
		private var _scenes:Array;
		private var _ground:QuickObject;
		private var _teeterTotter:QuickObject;
		
		private var _selector:Selector;
		private var _charTypes:Array;
		private var _charTotal:int;
		
		private var _sceneIndex:int;
		private var _currentScene:GameScene;
		private var _currentSceneOver:Boolean;

		private var _progressBar:ProgressBar;
		private var _scorecard:MovieClip;
		
		// Statistics tracking for Kongregate
		private var _useKongregate:Boolean = true;			// USE THIS TO TURN KONGREGATE CALLS ON OR OFF
		private var _perfectRound:Boolean;
		
		// Sounds
		private var _menuSelectSound:Sound;
		private var _doomSound:Sound;
		private var _selectorSound:Sound;
		
		private var _mouseCursor:MovieClip;
		
		private var _achievements:String;
		
		public function Stacks() 
		{
			_progressBar = new ProgressBar();
			_progressBar.x = stage.stageWidth * 0.5;
			_progressBar.y = stage.stageHeight * 0.5;
			_progressBar.mode = ProgressBarMode.POLLED;
			_progressBar.direction = ProgressBarDirection.RIGHT;
			_progressBar.source = root.loaderInfo;
			
			addChild(_progressBar);
			
			_progressBar.addEventListener(ProgressEvent.PROGRESS, loadHandler);
			_progressBar.addEventListener(Event.COMPLETE, onLoadComplete);
		
			// TODO:
	// DONE	// 1.  Finalize scenes
	// DONE		//     1.a blood (make blood bubbles look better), ghost in cemetary level
	// DONE		//     1.b Sound for selector
	// DONE		// OPTIMIZE - look at removing all movie clips that may be running
	// DONE		//          - clear event listeners
	// DONE		//          - figure out issue with teeter totter (falls at beginning, can be pulled down)
	// DONE		// 2.  Add sounds
	// DONE		//     2.a.  GHOSTS ON CEMETARY - ADD GHOST SOUND
	// IP		// Adjust scoring model
	// DONE		// 3.  Upload to Kongregate
	// DONE		//     3.a.  Create badges on website
	// IP		//	   3.b.  Add badge achievement code
	// DONE		// 4.  Change mouse cursor
	// IP		// 6.  Test
			// How to Page
	// MAKE A TESTER THAT CREATES SCORES FOR EACH LEVEL TO SEE WHAT IS POSSIBLE SCORE-WISE
			// 7.  Look into color differences on monitors
			// 8.  Submit game
		}
		
		//*********************************************************************
		// ********* LOAD FUNCTIONS **********
		//*********************************************************************
		private function loadHandler(e:ProgressEvent):void 
		{
			var loaded:Number = stage.loaderInfo.bytesLoaded;
			var total:Number = stage.loaderInfo.bytesTotal;
			var percent:Number = (loaded / total) * 100;
			
			_progressBar.value = percent;
		}
		
		
		private function onLoadComplete(e:Event):void 
		{
			_progressBar.removeEventListener(ProgressEvent.PROGRESS, loadHandler);
			_progressBar.removeEventListener(Event.COMPLETE, onLoadComplete);
			removeChild(_progressBar);
			
			if (_useKongregate)
			{
				// Load Kongregate API
				var kongregate:KongregateAPI;
	  
				try 
				{
					kongregate = new KongregateAPI();
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
			// Load sounds
			_menuSelectSound = new SoundMenuSelect();
			_doomSound = new SoundDoom();
			_selectorSound = new SoundDing();
			
			// Use a movie clip for the new mouse cursor
			_mouseCursor = new Hand();
			_mouseCursor.gotoAndStop(1);
			
			// Drive all below via menu options
			loadMenu();
		}
		
		private function onKongregateConnected(e:KongregateEvent):void 
		{
			// Load sounds
			_menuSelectSound = new SoundMenuSelect();
			_doomSound = new SoundDoom();
			_selectorSound = new SoundDing();
			
			// Use a movie clip for the new mouse cursor
			_mouseCursor = new Hand();
			_mouseCursor.gotoAndStop(1);
			
			// Drive all below via menu options
			loadMenu();
		}
		
		//*********************************************************************
		// ********* MENU FUNCTIONS **********
		//*********************************************************************
		private function loadMenu():void
		{
			_mainMenu = new MainMenu();
			_mainMenu.x = stage.stageWidth * 0.5;
			_mainMenu.y = stage.stageHeight * 0.5;
			
			if (!stage.getChildByName(_mainMenu.name))
			{
				stage.addChild(_mainMenu);
			}
			
			_mainMenu.gotoAndStop(1);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
		}
		
		private function onAchievementsButtonClick(e:MouseEvent):void 
		{
			_mainMenu.playButton.removeEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.removeEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.removeEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.removeEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(5);
			
			_mainMenu.backFromAchButton.addEventListener(MouseEvent.CLICK, onBackFromAchButtonClick);
		}
		
		private function onBackFromAchButtonClick(e:MouseEvent):void 
		{
			_mainMenu.backFromAchButton.removeEventListener(MouseEvent.CLICK, onBackFromAchButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(1);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
		}
		
		private function onCreditsButtonClick(e:MouseEvent):void 
		{
			_mainMenu.playButton.removeEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.removeEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.removeEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.removeEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(3);
			
			_mainMenu.backFromCreditsButton.addEventListener(MouseEvent.CLICK, onBackFromCreditsButtonClick);
		}
		
		private function onBackFromCreditsButtonClick(e:MouseEvent):void 
		{
			_mainMenu.backFromCreditsButton.removeEventListener(MouseEvent.CLICK, onBackFromCreditsButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(1);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
		}
		
		private function onPlayButtonClick(e:MouseEvent):void 
		{
			_mainMenu.playButton.removeEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.removeEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.removeEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.removeEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(2);
			
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.CLICK, onCreepyTreeButtonClick);
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.MOUSE_OVER, onCreepyTreeButtonMouseOver);
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.MOUSE_OUT, onCreepyTreeButtonMouseOut);
			
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.CLICK, onStackOLanternButtonClick);
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.MOUSE_OVER, onStackOLanternButtonMouseOver);
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.MOUSE_OUT, onStackOLanternButtonMouseOut);
			
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.CLICK, onFallTimeButtonClick);
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.MOUSE_OVER, onFallTimeButtonMouseOver);
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.MOUSE_OUT, onFallTimeButtonMouseOut);
			
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.CLICK, onHauntedHouseButtonClick);
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.MOUSE_OVER, onHauntedHouseButtonMouseOver);
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.MOUSE_OUT, onHauntedHouseButtonMouseOut);
			
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.CLICK, onCemetaryButtonClick);
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.MOUSE_OVER, onCemetaryButtonMouseOver);
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.MOUSE_OUT, onCemetaryButtonMouseOut);
			
			_mainMenu.backToMenuButton.addEventListener(MouseEvent.CLICK, onBackToMenuButtonClick);
		}
		
		
		private function onBackToMenuButtonClick(e:MouseEvent):void 
		{
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.CLICK, onCreepyTreeButtonClick);
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.MOUSE_OVER, onCreepyTreeButtonMouseOver);
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.MOUSE_OUT, onCreepyTreeButtonMouseOut);
			
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.CLICK, onStackOLanternButtonClick);
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.MOUSE_OVER, onStackOLanternButtonMouseOver);
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.MOUSE_OUT, onStackOLanternButtonMouseOut);
			
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.CLICK, onFallTimeButtonClick);
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.MOUSE_OVER, onFallTimeButtonMouseOver);
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.MOUSE_OUT, onFallTimeButtonMouseOut);
			
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.CLICK, onHauntedHouseButtonClick);
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.MOUSE_OVER, onHauntedHouseButtonMouseOver);
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.MOUSE_OUT, onHauntedHouseButtonMouseOut);
			
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.CLICK, onCemetaryButtonClick);
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.MOUSE_OVER, onCemetaryButtonMouseOver);
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.MOUSE_OUT, onCemetaryButtonMouseOut);
			
			_mainMenu.backToMenuButton.removeEventListener(MouseEvent.CLICK, onBackToMenuButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(1);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
		}

		private function onFallTimeButtonMouseOut(e:MouseEvent):void 
		{
			_mainMenu.removeChildAt(1);
		}
		
		private function onFallTimeButtonMouseOver(e:MouseEvent):void 
		{
			var background:MovieClip = new BackgroundFall();
			background.x = 0;
			background.y = 0;
			
			var blurFilter:BlurFilter = new BlurFilter();
			blurFilter.blurX = 10;
			blurFilter.blurY = 10;
			blurFilter.quality = 25;
			
			background.filters = [blurFilter];
			_mainMenu.addChildAt(background, 1);
		}
		
		private function onFallTimeButtonClick(e:MouseEvent):void 
		{
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.CLICK, onFallTimeButtonClick);
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.MOUSE_OVER, onFallTimeButtonMouseOver);
			_mainMenu.fallTimeButton.removeEventListener(MouseEvent.MOUSE_OUT, onFallTimeButtonMouseOut);
			
			_mainMenu.fallTimeButton.selected = false;

			stage.removeChild(_mainMenu);
			
			this.startGame(Vals.FALL_TIME_SCENE);
		}
		
		private function onStackOLanternButtonClick(e:MouseEvent):void 
		{
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.CLICK, onStackOLanternButtonClick);
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.MOUSE_OVER, onStackOLanternButtonMouseOver);
			_mainMenu.stackOLanternButton.removeEventListener(MouseEvent.MOUSE_OUT, onStackOLanternButtonMouseOut);
			
			_mainMenu.stackOLanternButton.selected = false;

			stage.removeChild(_mainMenu);
			
			this.startGame(Vals.STACKOLANTERN_SCENE);
		}
		
		private function onStackOLanternButtonMouseOver(e:MouseEvent):void 
		{
			var background:MovieClip = new BackgroundStackOLantern();
			background.x = 0;
			background.y = 0;
			
			var blurFilter:BlurFilter = new BlurFilter();
			blurFilter.blurX = 10;
			blurFilter.blurY = 10;
			blurFilter.quality = 25;
			
			background.filters = [blurFilter];
			_mainMenu.addChildAt(background, 1);
		}
		
		private function onStackOLanternButtonMouseOut(e:MouseEvent):void 
		{
			_mainMenu.removeChildAt(1);
		}
		
		private function onHowToPlayButtonClick(e:MouseEvent):void 
		{
			_mainMenu.playButton.removeEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.removeEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.removeEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.removeEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(4);
			
			_mainMenu.backFromHowToButton.addEventListener(MouseEvent.CLICK, onBackFromHowToButtonClick);
		}
		
		private function onBackFromHowToButtonClick(e:MouseEvent):void 
		{
			_mainMenu.backFromHowToButton.removeEventListener(MouseEvent.CLICK, onBackFromHowToButtonClick);
			
			_menuSelectSound.play();
			_mainMenu.gotoAndStop(1);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
		}
	
		private function onCreepyTreeButtonClick(e:MouseEvent):void 
		{
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.CLICK, onCreepyTreeButtonClick);
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.MOUSE_OVER, onCreepyTreeButtonMouseOver);
			_mainMenu.creepyTreeButton.removeEventListener(MouseEvent.MOUSE_OUT, onCreepyTreeButtonMouseOut);
			
			_mainMenu.creepyTreeButton.selected = false;

			stage.removeChild(_mainMenu);
			
			this.startGame(Vals.CREEPY_TREE_SCENE);
		}
	
		private function onCreepyTreeButtonMouseOver(e:MouseEvent):void 
		{
			var background:MovieClip = new BackgroundCreepyTree();
			background.x = 0;
			background.y = 0;
			
			var blurFilter:BlurFilter = new BlurFilter();
			blurFilter.blurX = 10;
			blurFilter.blurY = 10;
			blurFilter.quality = 25;
			
			background.filters = [blurFilter];
			_mainMenu.addChildAt(background, 1);
		}
		
		private function onCreepyTreeButtonMouseOut(e:MouseEvent):void
		{
			_mainMenu.removeChildAt(1);
		}
		
		private function onCemetaryButtonMouseOut(e:MouseEvent):void 
		{
			_mainMenu.removeChildAt(1);
		}
		
		private function onCemetaryButtonMouseOver(e:MouseEvent):void 
		{
			var background:MovieClip = new BackgroundCemetary();
			background.x = 0;
			background.y = 0;
			
			var blurFilter:BlurFilter = new BlurFilter();
			blurFilter.blurX = 10;
			blurFilter.blurY = 10;
			blurFilter.quality = 25;
			
			background.filters = [blurFilter];
			_mainMenu.addChildAt(background, 1);
		}
		
		private function onCemetaryButtonClick(e:MouseEvent):void 
		{
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.CLICK, onCemetaryButtonClick);
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.MOUSE_OVER, onCemetaryButtonMouseOver);
			_mainMenu.cemetaryButton.removeEventListener(MouseEvent.MOUSE_OUT, onCemetaryButtonMouseOut);
			
			_mainMenu.cemetaryButton.selected = false;

			stage.removeChild(_mainMenu);
			
			this.startGame(Vals.CEMETARY_SCENE);
		}
		
		
		private function onHauntedHouseButtonMouseOut(e:MouseEvent):void 
		{
			_mainMenu.removeChildAt(1);
		}
		
		private function onHauntedHouseButtonMouseOver(e:MouseEvent):void 
		{
			var background:MovieClip = new BackgroundHauntedHouse();
			background.x = 0;
			background.y = 0;
			
			var blurFilter:BlurFilter = new BlurFilter();
			blurFilter.blurX = 10;
			blurFilter.blurY = 10;
			blurFilter.quality = 25;
			
			background.filters = [blurFilter];
			_mainMenu.addChildAt(background, 1);
		}
		
		private function onHauntedHouseButtonClick(e:MouseEvent):void 
		{
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.CLICK, onHauntedHouseButtonClick);
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.MOUSE_OVER, onHauntedHouseButtonMouseOver);
			_mainMenu.hauntedHouseButton.removeEventListener(MouseEvent.MOUSE_OUT, onHauntedHouseButtonMouseOut);
			
			_mainMenu.hauntedHouseButton.selected = false;

			_mainMenu.visible = false;
			stage.removeChild(_mainMenu);
			
			this.startGame(Vals.HAUNTED_HOUSE_SCENE);
		}
		
		//*********************************************************************
		// ********* GAME START FUNCTIONS **********
		//*********************************************************************
		private function startGame(sceneIndex:int):void
		{			
			// This all needs to be a function that has results from the menu
			// 1.  Setup the director for Camera and Time based behavior
			setupDirector();
			
			
			// 1.a.  Load Scenes into scene array
			loadScenes();
			_sceneIndex = sceneIndex;
			_currentScene = _scenes[_sceneIndex];
			
			_currentScene.loadScene();
			_currentSceneOver = false;

			_achievements = "";
		
			// 2.  Setup the Physics World values
			setupPhysicsWorld();
			
			// 3.  Initialize variables
			_isDragging = false;
			_dragObject = null;
			
			// 4.  Load themes
			loadThemes();
			
			// Keeps track of all of the bodies and Character objects in a key/value pair
			_characterDictionary = new Dictionary(true);
			_characters = [];

			///////////////////////////////////////////////////////
			// set statistical variables here
			_perfectRound = true;
			
			// we can set default properties for all QuickObjects
			//_world.setDefault({fillColor:0x00CC22, lineAlpha:0, radius:1.5});

			// polygon vertices must be defined from top to bottom and clockwise
			// additional polygons can be added in the form of arrays. Each individual
			// poly must be convex and can only have up to 8 vertices
			// there is no limit to the number of polygons you can add - and combined polygons
			// can be used to create concave shapes
			//_world.addPoly({x:15, y:8, verts:[[0,0,2,2,0,2], [0,0,0,1,-2,-0.2]], fillColor:0x0000FF});
			 
			// 8.  Load the ground object
			_ground = Vals.world.addBox( { x:Vals.GROUND_X, 
										   y:Vals.GROUND_Y, 
										   width:Vals.GROUND_WIDTH, 
										   height:Vals.GROUND_HEIGHT, 
										   density:_currentScene.groundDensity, 
										   restitution:Vals.GROUND_RESTITUTION, 
										   fillColor:_currentScene.groundColor
										   } );
			_ground.userData = "GROUND";
			
			// Need to load physical items of the scene before the stack is created
			_currentScene.loadPhysicalObjects();
			
			_charTypes = [];
			generateStackByTheme(_currentScene.rows, _currentScene.columns, _currentScene.theme);
			
			// 9.  Load Contacts
			_contacts = Vals.world.addContactListener();
			_contacts.addEventListener(QuickContacts.ADD, onContactAdd);
			//_contacts.addEventListener(QuickContacts.PERSIST, onContactResolve);
			
			// 10. Load Selector
			_selector = new Selector(this, 60, new Point(50, 50), _charTypes);
			
			/////////////////////////////////////////////////////
			Vals.world.start();
			this.mouseDrag();
			//Vals.world.mouseDrag();
			
			// may need scene-specific things in the scoreboard!
			_scoreboard = new Scoreboard(this, 600, 10, new Point(550, 10), _currentScene.timeLimit);
			
			
			// Score color adjustment for Fall Time Level
			if (_sceneIndex == Vals.FALL_TIME_SCENE)
			{
				_scoreboard.textColor = 0xdd0000;
			}
			
			addEventListener(Event.ENTER_FRAME, onEnterFrame);
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
			
			//stage.addEventListener(CharacterEvent.KILL_CHARACTER, onKillCharacter);
			
			if (_currentScene.hasTimer) _currentScene.init();
			
			// Set mouse
			this.stage.addChild(_mouseCursor);
			Mouse.hide();
		}
		
		// Generates the stack based on the properties of the current scene
		public function generateStackByTheme(rows:int, columns:int, theme:int):void
		{
			var charArray:Array = _themes[theme];
			var count:int = charArray.length;
			var validIndex:Boolean = false;
			
			_charTypes = new Array(count);
			for (var x:int = 0; x < _charTypes.length; x++)
			{
				_charTypes[x] = 0;
			}
			
			for (var i:int = 0; i < rows; i++)
			{
				for (var j:int = 0; j < columns; j++)
				{
					// 1.  Get random character from character array
					do
					{
						var charIndex:int = count * Math.random();
						
						// Ensure that we don't have more than the max allowed of one particular character
						// in the stack
						if (_charTypes[charIndex] == Vals.CHARACTER_PER_STACK_MAX)
						{
							charIndex = count * Math.random();
						}
						else
						{
							validIndex = true;
						}
					}
					while (!validIndex);
					
					// 2.  Generate character - need to do some character specific checking here for heights and widths
					var char:Character = new Character(this, charIndex, new String("char" + j + i), new Point(150 + (j * 60), 480 - (i * 70)), Vals.BOX, new Point(55, 55));
					_characterDictionary[char.body.body] = char;
					
					_charTypes[charIndex] += 1;
					_charTotal++;
					
					_characters.push(char);
					
					// reset the index checker
					validIndex = false;
				}
			}
			
		}
		
		
		//*********************************************************************
		// ********* WORLD FUNCTIONS **********
		//*********************************************************************
		public function setupPhysicsWorld():void
		{
			// Set up the simulation environment
			if (!Vals.world)
			{
				Vals.world = new QuickBox2D(this, 
											{
												 debug			:	false, 
												 frim			:	false, 
												 gravityY		:	Vals.GRAVITY, 
												 timeStep		: 	_timeMaster.getTimeStep(),
												 iterations		:	Vals.ITERATIONS,
												 bounds			:	[Vals.PHYS(-5000), Vals.PHYS(-5000), Vals.PHYS(5000), Vals.PHYS(5000)],
												 customMouse	:	false
											});
			}
		}
		
		// This function loads the camera and timeMaster into the director object
		private function setupDirector()
		{
			// Create camera and add it to the stage
			_camera = new Camera();
			//stage.addChild(_camera);
			
			_timeMaster = new TimeMaster();
			_director = new Director(_camera, _timeMaster);

		}
		
		// This function loads the scene objects into the scenes array
		private function loadScenes():void
		{
			_scenes = [];
			
			var stackOLanternScene:StackOLanternScene = new StackOLanternScene(this, "Stack-O-Lantern", Vals.HALLOWEEN_THEME, 8, 6, 90, 0x030000, true);
			_scenes.push(stackOLanternScene);
			
			var creepyTreeScene:CreepyTreeScene = new CreepyTreeScene(this, "The Creepy Tree", Vals.HALLOWEEN_THEME, 6, 6, 90, 0x646464, true);
			_scenes.push(creepyTreeScene);
			
			var fallTimeScene:FallTimeScene = new FallTimeScene(this, "Fall Time", Vals.HALLOWEEN_THEME, 7, 6, 90, 0x004500, true);
			_scenes.push(fallTimeScene);
			
			var hauntedHouseScene:HauntedHouseScene = new HauntedHouseScene(this, "Haunted House", Vals.HALLOWEEN_THEME, 6, 6, 60, 0x000d00, true);
			_scenes.push(hauntedHouseScene);
			
			var cemetaryScene:CemetaryScene = new CemetaryScene(this, "Cemetary", Vals.HALLOWEEN_THEME, 6, 6, 90, 0x004500, true);
			_scenes.push(cemetaryScene);
		}
		
		// This function loads the themes into the themes array
		private function loadThemes():void
		{
			_themes = [];
			_themes[Vals.HALLOWEEN_THEME] = Vals.HALLOWEEN_CHARACTERS;
			_themes[Vals.BEAR_THEME] = Vals.BEAR_CHARACTERS;
		}
		
		
		
		
		//*********************************************************************
		// ********* GAME FUNCTIONS ********
		//*********************************************************************
		// Removes character from the world
		private function removeCharacter(char:Character):void
		{
			_characterDictionary[char.body.body] = null;
			_characters[char] = null;
			_charTotal--;
			
			this.removeChild(char.graphic);
			char.body.destroy();
			
			char = null;
		}
		
		// Removes all of the running event listeners to stop game play
		private function removeSceneEventListeners():void
		{
			removeEventListener(Event.ENTER_FRAME, onEnterFrame);
			stage.removeEventListener(MouseEvent.MOUSE_DOWN, grab);
			stage.removeEventListener(MouseEvent.MOUSE_WHEEL, rotateBody);
			stage.removeEventListener(MouseEvent.MOUSE_UP, letGo);
		}
		
		// Clears up a scene
		private function clearScene():void
		{
			for each (var char:Character in _characterDictionary)
			{
				if (char)
				{
					removeCharacter(char);
				}
			}
			
			//_ground.destroy();
			
			
			var outOfBodies:Boolean = false;
			
			// clear out any remaining bodies
			do
			{
				var body:b2Body = Vals.world.w.GetBodyList().GetNext();
				
				if (body)
				{
					Vals.world.w.DestroyBody(body);
				}
				else
				{
					outOfBodies = true;
				}
			}
			while (!outOfBodies);
			
			Vals.world.stop();
			
			_currentScene.clearScene();
			_currentScene = null;
			
			// Remove selector's child
			this.removeChild(_selector.currentCharacter);
			_selector = null;
			
			// Clear scoreboard
			this.removeChild(_scoreboard.scoreDisplay);
			this.removeChild(_scoreboard.timerDisplay);
			
			_scoreboard = null;
			
			// Clear camera
			//this.stage.removeChild(_camera);

			for (var t:int = this.numChildren - 1; t >= 0; t--)
			{
				var child:DisplayObject = this.getChildAt(t);
				
				if (child is Acorn || child is Leaf1 || child is Leaf2 || child is Leaf3 || child is Leaf4 || child is GravePedestal || child is Slab || child is BackgroundCemetary ||
				    child is BackgroundHauntedHouse || child is Blood || child is BackgroundStackOLantern || child is BackgroundFall || child is BackgroundCreepyTree || child is CemetaryGhost)
				{
					this.removeChild(child);
				}
			}
			
			Vals.world.destroy();		
			Vals.world = null;
		}
		
		private function loadScorecard(score:int, timeLeft:int, sceneName:String, charactersLeft:int):void
		{
			stage.removeEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
			
			Mouse.show();
			
			if (this.stage.contains(_mouseCursor))
			{
				this.stage.removeChild(_mouseCursor);
			}
			
			_scorecard = new Scorecard();
			_scorecard.x = 300;
			_scorecard.y = 300;
			
			// add in score, time, and name
			_scorecard.levelText.text = sceneName;
			_scorecard.scoreText.text = String(score);
			_scorecard.timeText.text = String(timeLeft);
			_scorecard.characterText.text = String(charactersLeft);
			
			_scorecard.achievementText.text = _achievements;
			
			addChild(_scorecard);
			
			_scorecard.restartButton.addEventListener(MouseEvent.CLICK, onRestartButtonClick);
			_scorecard.selectSceneButton.addEventListener(MouseEvent.CLICK, onSceneSelectButtonClick);
			_scorecard.backButton.addEventListener(MouseEvent.CLICK, onBackButtonClick);
			
			//_scorecard.achievementText.addEventListener(MouseEvent.MOUSE_OVER, onAchievementMouseOver);
			//_scorecard.achievementText.addEventListener(MouseEvent.MOUSE_OUT, onAchievementMouseOut);
		}
		
		/*
		private function onAchievementMouseOut(e:MouseEvent):void 
		{
			_scorecard.removeChildAt(1);
		}
		
		private function onAchievementMouseOver(e:MouseEvent):void 
		{
			var mc:MovieClip = new MovieClip();
			mc.alpha = 0;
			mc.width = 300;
			mc.height = 300;
			mc.x = 100;
			mc.y = 100;
			var textArea:TextArea = new TextArea();
			textArea.appendText(_achievements);
			mc.addChild(textArea);
			
			_scorecard.addChildAt(mc, 1);
		}
		*/
		private function onRestartButtonClick(e:MouseEvent):void 
		{
			_scorecard.restartButton.removeEventListener(MouseEvent.CLICK, onRestartButtonClick);
			_scorecard.selectSceneButton.removeEventListener(MouseEvent.CLICK, onSceneSelectButtonClick);
			_scorecard.backButton.removeEventListener(MouseEvent.CLICK, onBackButtonClick);

			removeChild(_scorecard);
			startGame(_sceneIndex);
		}
		
		private function onSceneSelectButtonClick(e:MouseEvent):void 
		{
			_scorecard.restartButton.removeEventListener(MouseEvent.CLICK, onRestartButtonClick);
			_scorecard.selectSceneButton.removeEventListener(MouseEvent.CLICK, onSceneSelectButtonClick);
			_scorecard.backButton.removeEventListener(MouseEvent.CLICK, onBackButtonClick);
			
			stage.addChild(_mainMenu);
			_mainMenu.visible = true;
			
			_mainMenu.removeChildAt(1);
			_mainMenu.gotoAndStop(2);
			
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.CLICK, onCreepyTreeButtonClick);
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.MOUSE_OVER, onCreepyTreeButtonMouseOver);
			_mainMenu.creepyTreeButton.addEventListener(MouseEvent.MOUSE_OUT, onCreepyTreeButtonMouseOut);
			
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.CLICK, onStackOLanternButtonClick);
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.MOUSE_OVER, onStackOLanternButtonMouseOver);
			_mainMenu.stackOLanternButton.addEventListener(MouseEvent.MOUSE_OUT, onStackOLanternButtonMouseOut);
			
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.CLICK, onFallTimeButtonClick);
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.MOUSE_OVER, onFallTimeButtonMouseOver);
			_mainMenu.fallTimeButton.addEventListener(MouseEvent.MOUSE_OUT, onFallTimeButtonMouseOut);
			
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.CLICK, onHauntedHouseButtonClick);
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.MOUSE_OVER, onHauntedHouseButtonMouseOver);
			_mainMenu.hauntedHouseButton.addEventListener(MouseEvent.MOUSE_OUT, onHauntedHouseButtonMouseOut);
			
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.CLICK, onCemetaryButtonClick);
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.MOUSE_OVER, onCemetaryButtonMouseOver);
			_mainMenu.cemetaryButton.addEventListener(MouseEvent.MOUSE_OUT, onCemetaryButtonMouseOut);
			
			_mainMenu.backToMenuButton.addEventListener(MouseEvent.CLICK, onBackToMenuButtonClick);
			
			_scorecard.visible = false;
			removeChild(_scorecard);
		}
		
		private function onBackButtonClick(e:MouseEvent):void 
		{
			_scorecard.restartButton.removeEventListener(MouseEvent.CLICK, onRestartButtonClick);
			_scorecard.selectSceneButton.removeEventListener(MouseEvent.CLICK, onSceneSelectButtonClick);
			_scorecard.backButton.removeEventListener(MouseEvent.CLICK, onBackButtonClick);
			
			stage.addChild(_mainMenu);
			_mainMenu.visible = true;
			
			_mainMenu.removeChildAt(1);
			_mainMenu.gotoAndStop(1);
		
			_scorecard.visible = false;
			removeChild(_scorecard);
			
			_mainMenu.playButton.addEventListener(MouseEvent.CLICK, onPlayButtonClick);
			_mainMenu.howToPlayButton.addEventListener(MouseEvent.CLICK, onHowToPlayButtonClick);
			_mainMenu.creditsButton.addEventListener(MouseEvent.CLICK, onCreditsButtonClick);
			_mainMenu.achievementsButton.addEventListener(MouseEvent.CLICK, onAchievementsButtonClick);
			
		}
		
		//*********************************************************************
		// ********* MOUSE FUNCTIONS ********
		//*********************************************************************
		private function mouseDrag():void
		{
			/*for each(var obj:QuickObject in _characterDictionary) 
			{
                obj.handCursor();
            }*/

			
			
			stage.addEventListener(MouseEvent.MOUSE_DOWN, grab);
			stage.addEventListener(MouseEvent.MOUSE_WHEEL, rotateBody);
			stage.addEventListener(MouseEvent.MOUSE_UP, letGo);
			//stage.addEventListener(MouseEvent.MOUSE_LEAVE, letGo);
		}
		
		private function rotateBody(e:MouseEvent):void 
		{
			if (_isDragging)
			{
				var x:int = e.delta;
				
				// Find nearest 90 degree angle
				if (x < 0)
				{
					if (_dragCharacter.rotationIndex == 0) _dragCharacter.rotationIndex = 3;
					else _dragCharacter.rotationIndex--;
					
					_dragCharacter.updateRotation();
				}
				else if (x > 0)
				{
					if (_dragCharacter.rotationIndex == 3) _dragCharacter.rotationIndex = 0;
					else _dragCharacter.rotationIndex++;
					
					_dragCharacter.updateRotation();
				}
			}
		}
		
		private function grab(e:MouseEvent):void 
		{
			var body:b2Body = getBodyAtMouse(null);
			var mouseJointDef:b2MouseJointDef;
			
			_mouseCursor.gotoAndStop(2);
			
			if (body)
			{
				//trace(_characterDictionary[body]);
				if (_characterDictionary[body])
				{
					
					// Check if the body is draggable or not
					_dragCharacter = Character(_characterDictionary[body]);
					_dragObject = _dragCharacter.body;
					
					_isDragging = true;
					/*
					mouseJointDef = new b2MouseJointDef();
					mouseJointDef.body1 = Vals.world.w.GetGroundBody();
					mouseJointDef.body2 = body;
					mouseJointDef.target.Set(Vals.PHYS(mouseX), Vals.PHYS(mouseY));
					//mouseJointDef.target.Set(body.GetWorldCenter().x, body.GetWorldCenter().y);
					mouseJointDef.maxForce = 0;
					mouseJointDef.timeStep = _timeMaster.getTimeStep();
					mouseJointDef.frequencyHz = 0;
					mouseJointDef.dampingRatio = 1;
					
					_mouseJoint = Vals.world.w.CreateJoint(mouseJointDef) as b2MouseJoint;
					trace(_mouseJoint);*/
				}
			}
		}
		
		// If a character is being dragged, this will let it go
		private function letGo(e:MouseEvent):void 
		{
			// Check to see if a mouse joint exists
			/*
			if (_mouseJoint)
			{
				Vals.world.w.DestroyJoint(_mouseJoint);
				_mouseJoint = null;
			}*/
			_mouseCursor.gotoAndStop(1);
			
			if (_isDragging)
			{
				_isDragging = false;
				_dragObject.body.SetLinearVelocity(new b2Vec2(0, 0));
			}
		}
		
		// This function searches all shapes at the mouse point and selects a body
		private function getBodyAtMouse(objectTypes:Array):b2Body
		{
			// Get world mouse coordinates
			var worldMouseX = Vals.PHYS(mouseX);
			var worldMouseY = Vals.PHYS(mouseY);
			
			// Set this to be the mouse vector
			var mouseVector = new b2Vec2(worldMouseX, worldMouseY);
			
			// Create a bounding box around the mouse point
			var mouseBoundingBox:b2AABB = new b2AABB();
			mouseBoundingBox.lowerBound.Set(worldMouseX - 0.001, worldMouseY - 0.001);
			mouseBoundingBox.upperBound.Set(worldMouseX + 0.001, worldMouseY + 0.001);
			
			// Define shape count
			var shapeCount:int = 10;
			
			// Create an array to hold the shapes
			var shapesArray:Array = new Array();
			
			// Get the actual count of the shapes inside the mouse's bounding box
			var count:int = Vals.world.w.Query(mouseBoundingBox, shapesArray, shapeCount);
			
			if (count > 0)
			{
				// Create a body
				var body:b2Body = null;
				
				// Look through all of the shapes to see if we have a valid one under the mouse
				for (var i:int = 0; i < count; ++i) 
				{
					if (shapesArray[i].GetBody().IsStatic() == false)// && (shapesArray[i].m_body.GetUserData() is QuickObject))  // check that the shape is a shoe
					{
						var tShape:b2Shape = shapesArray[i] as b2Shape;
						var inside:Boolean = tShape.TestPoint(tShape.m_body.GetXForm(), mouseVector);
						if (inside) 
						{
							body = tShape.m_body;
							break;
						}
					}
				}
			}
			return body;
		}
		
		// This function updates the mouse every frame if a a character is
		// being dragged across the screen
		public function updateMouse():void
		{
			
			// If a mouse joint is present, update the mouse
			/*
			if (_mouseJoint) 
			{
				// Reset the mouse joint's position based on the new position
				// of the mouse pointer
				var p2:b2Vec2 = new b2Vec2(Vals.PHYS(mouseX), Vals.PHYS(mouseY));
				//var p2:b2Vec2 = _mouseJoint.GetBody2().GetWorldCenter();
				_mouseJoint.SetTarget(p2);
				trace(p2.x + ", " + p2.y);
			}*/
			
			
			if (_isDragging)
			{
				var p2:b2Vec2 = new b2Vec2(Vals.PHYS(mouseX), Vals.PHYS(mouseY));
				_dragObject.setLoc(p2.x, p2.y);
				_dragObject.body.SetLinearVelocity(new b2Vec2(0, 0));
				_dragCharacter.updateRotation();
				
				
				
				if (_dragCharacter.type == _selector.characterType)
				{
					if (_dragCharacter.graphic.hitTestObject(_selector.currentCharacter))
					{
						_isDragging = false;
						
						_selectorSound.play();
					
						// update scoreboard's score
						_scoreboard.updateScore(1);
						
						if (_charTotal != 0)
						{
							_selector.removeOneCharacter(_dragCharacter.type);
							_selector.loadNextCharacter(_charTotal);
						}
						
						removeCharacter(_dragCharacter);
						_dragCharacter = null;
					}
				}
			}
		}
		
		//*********************************************************************
		// ********* EVENT HANDLERS **********
		//*********************************************************************
		private function onEnterFrame(e:Event):void 
		{
			// Update the mouse every frame only in the case where a character is
			// being dragged
			if (_isDragging)
				updateMouse();
			
			_mouseCursor.x = mouseX;
			_mouseCursor.y = mouseY;
			
			// update any scene's graphical objects
			_currentScene.updateGraphicalObjects();
			
			// Check win/loss scenarios
			if (_scoreboard.gameOver)
			{
				_currentSceneOver = true;
				_perfectRound = false;
				
				// remove event listeners
				removeSceneEventListeners();
				_scoreboard.stopTimer();
				
				if (_currentScene.hasTimer)
				{
					_currentScene.stopEvents();
				}
			}
			else if (_selector.gameOver)
			{
				_currentSceneOver = true;
				
				// remove event listeners
				removeSceneEventListeners();
				_scoreboard.stopTimer();
				
				if (_currentScene.hasTimer)
				{
					_currentScene.stopEvents();
				}
			}
			
			if (_currentSceneOver)
			{
				Mouse.show();
				
				this.stage.removeChild(_mouseCursor);
				
				// Submit score to Kongregate
				if (_useKongregate)
				{
					var kongregate:KongregateAPI = KongregateAPI.getInstance();
				}
				
				var score:Number = _scoreboard.score;
				var level:String = _currentScene.name;
				var timeLeft:int = _scoreboard.timeLeft;
				
				if (score <= 0)
				{
					score = 0;
					if (_useKongregate) kongregate.stats.submit("devilsReject", 1);
					_achievements += "The Devil's Reject\n";
				}
				
				// Might have to make a HighScore stat
				/*
				 Name: HighScore-Normal
				 Description: HighScore-Normal
				 Max Type: Selected
				 Display in Leaderboards: Yes
				 Display Name: HighScore-Normal
				 */
				 
				if (_useKongregate) kongregate.scores.submit(score);
				
				if (_useKongregate) kongregate.stats.submit("HighScore-Normal", score);
				
				// Submit achievement for perfect round if applicable
				// Have score achievements (10000, 20000, 30000, 40000, 50000)
				// Have low score achievements (-10000)
				// Have time achievements (> 20 sec, > 30 sec, > 40 sec, > 50 sec)
				if (_perfectRound && score >= 50000 && timeLeft > 30)
				{
					if (_useKongregate) kongregate.stats.submit("pumpkinHead", 1);
					_achievements += "The Pumpkin Head\n";
				}
				else if (_perfectRound && score >= 50000)
				{
					// may do perfect round && score >= 50000 && > 10 sec left (PumpkinHead)
					if (_useKongregate) kongregate.stats.submit("hellRaiser", 1);
					_achievements += "Hellraiser\n";
				}
				else if (score >= 60000)
				{
					if (_useKongregate) kongregate.stats.submit("boogeyman", 1);
					_achievements += "Boogeyman\n";
				}
				else if (score >= 50000 && score < 60000)
				{
					if (_useKongregate) kongregate.stats.submit("exorcist", 1);
					_achievements += "The Exorcist\n";
				}
				else if (score >= 40000 && score < 50000)
				{
					if (_useKongregate) kongregate.stats.submit("freaks", 1);
					_achievements += "Freaks!\n";
				}
				else if (score >= 30000 && score < 40000)
				{
					if (_useKongregate) kongregate.stats.submit("poltergeist", 1);
					_achievements += "Poltergeist\n";
				}
				else if (score >= 20000 && score < 30000)
				{
					if (_useKongregate) kongregate.stats.submit("candyman", 1);
					_achievements += "Candyman\n";
				}
				else if (score >= 10000 && score < 20000)
				{
					if (_useKongregate) kongregate.stats.submit("voorhees", 1);
					_achievements += "Voorhees\n";
				}
				
				if (_perfectRound && timeLeft >= 20 && timeLeft < 30)
				{
					if (_useKongregate) kongregate.stats.submit("craven", 1);
					_achievements += "Craven\n";
				}
				else if (_perfectRound && timeLeft >= 30 && timeLeft < 40)
				{
					if (_useKongregate) kongregate.stats.submit("romero", 1);
					_achievements += "Romero\n";
				}
				else if (_perfectRound && timeLeft >= 40 && timeLeft < 50)
				{
					if (_useKongregate) kongregate.stats.submit("carpenter", 1);
					_achievements += "Carpenter\n";
				}
				else if (_perfectRound && timeLeft >= 50)
				{
					if (_useKongregate) kongregate.stats.submit("hitchcock", 1);
					_achievements += "Hitchcock\n";
				}
				
				// Display scorecard
				loadScorecard(_scoreboard.score, _scoreboard.timeLeft, _currentScene.name, _charTotal);
				
				_doomSound.play();
				
				
				
				// Clear out current scene
				clearScene();
				
				
				
				// Display scene selection
				// Send scores (if applicable)
				// Start new game
			}
		}
		
		
		private function onKeyDown(e:KeyboardEvent):void 
		{
			if (e.keyCode == 32)
			{	
				_currentSceneOver = true;
				_perfectRound = false;
				
				// remove event listeners
				removeSceneEventListeners();
				_scoreboard.stopTimer();
				
				if (_currentScene.hasTimer)
				{
					_currentScene.stopEvents();
				}
				
				// Display scorecard
				loadScorecard(_scoreboard.score, _scoreboard.timeLeft, _currentScene.name, _charTotal);
				
				_doomSound.play();
				
				// Clear out current scene
				clearScene();
			}
		}
		
	
		// Overrides Box2D's add contact listener
		// Checks to see if a character has hit the ground; if so, kill the character, update the selector (if necessary), modify quantities
		private function onContactAdd(e:Event):void 
		{
			if (String(_contacts.currentPoint.shape1.GetBody().GetUserData()) == "GROUND" || String(_contacts.currentPoint.shape2.GetBody().GetUserData()) == "GROUND")
			{
				if (String(_contacts.currentPoint.shape1.GetBody().GetUserData()) == "GROUND")
				{
					var char1:Character = _characterDictionary[_contacts.currentPoint.shape2.GetBody()];
					
					if (char1 != null && char1.isAlive)
					{
						// update scoreboard's score
						_scoreboard.updateScore( -1);

						char1.isAlive = false;
						_selector.removeOneCharacter(char1.type);
						
						if (_selector.characterType == char1.type && _selector.characters[char1.type] == 0)
						{
							_selector.loadNextCharacter(_charTotal);
						}
						
						// set perfect round to false here
						_perfectRound = false;
						
						// remove the current character on the selector to ensure that 
						removeCharacter(char1);
					}
				
				}
				else if (String(_contacts.currentPoint.shape2.GetBody().GetUserData()) == "GROUND")
				{
					var char2:Character = _characterDictionary[_contacts.currentPoint.shape1.GetBody()];
					
					if (char2 != null && char2.isAlive)
					{
						// update scoreboard's score
						_scoreboard.updateScore( -1);

						char2.isAlive = false;
						_selector.removeOneCharacter(char2.type);
						
						if (_selector.characterType == char2.type && _selector.characters[char2.type] == 0)
						{
							removeCharacter(char2);
							_selector.loadNextCharacter(_charTotal);
						}
						
						// set perfect round to false here
						_perfectRound = false;
						
						// remove the current character on the selector to ensure that 
						removeCharacter(char2);
					}
				}	
			}
		}
		
		public function get characters():Array { return _characters; }
		
		public function get dragCharacter():Character { return _dragCharacter; }
		
		public function get ground():QuickObject { return _ground; }
		
		public function get scoreboard():Scoreboard { return _scoreboard; }
	}

}