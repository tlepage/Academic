package
{
	import flash.display.MovieClip;
	import flash.events.*;
	import flash.ui.Keyboard;
	import flash.text.TextField;
	import fl.data.DataProvider;
	
	public class Main extends MovieClip
	{
		private var HUMAN:int = 0;
		private var CPU:int = 1;
		private var DEMO:int = 2;
		private var LEFT_SIDE:int = 0;
		private var RIGHT_SIDE:int = 1;
		
		private var EASY:int = 2;
		private var MEDIUM:int = 1;
		private var HARD:int = 0;
		
		private var _difficulty:int;
		
		private var _menu:Menu;
		private var _startButton:StartButton;
		
		private var _player:Player;
		private var _name:String;
		
		private var _player2:Player;
		
		private var _paddle:Paddle;
		private var _paddle2:Paddle;
		
		private var _table:Table;
		private var _topBoundary:Boundary;
		private var _bottomBoundary:Boundary;
		
		private var _ball:Ball;
		
		private var _gameType:int;
		private var _particleSystem:ParticleSystem;
		
		private var _nameField:TextField;
		
		public function Main():void
		{
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDownHandler);
			stage.addEventListener(KeyboardEvent.KEY_UP, onKeyUpHandler);
			
			_menu:Menu;
			_menu = new Menu();
			addChild(_menu);
			
			var difficulties:Array = new Array(
       
				{label: "Easy", data: "diffEasy"},	
				{label: "Advanced", data: "diffAdv"},
				{label: "Impossible", data: "diffImp"}
			);
			
			_startButton = new StartButton();
			_startButton.x = 750;
			_startButton.y = 420;
			
			_startButton.addEventListener(MouseEvent.CLICK, onStartButtonClick);
			addChild(_startButton);
			
			_menu.difficultyComboBox.width = 120;
			_menu.difficultyComboBox.dataProvider = new DataProvider(difficulties);
			_menu.difficultyComboBox.selectedIndex = 0;
			_menu.difficultyComboBox.addEventListener(Event.CHANGE, onDifficultyChange);
			
			_menu.ballColor.selectedColor = 0xffffff;
			_menu.tableColor.selectedColor = 0x2b5238;

			_difficulty = EASY;
		}
		
		public function onStartButtonClick(event:MouseEvent):void
		{
			_startButton.removeEventListener(MouseEvent.CLICK, onStartButtonClick);
			removeChild(_startButton);
			removeChild(_menu);
			startGame(CPU);
		}
		
		public function onDifficultyChange(event:Event):void
		{
			switch (_menu.difficultyComboBox.selectedIndex)
			{
				case 0: _difficulty = EASY; break;
				case 1: _difficulty = MEDIUM; break;
				case 2: _difficulty = HARD; break;
			}
		}
		
		private function startGame(gameType:int):void
		{
			stage.addEventListener(Event.ENTER_FRAME, onFrameEnter);
			
			// create the table
			_table = new Table(stage.stageWidth, 500, _menu.tableColor.selectedColor);
			stage.addChild(_table);
			
			// create the boundaries for collisions
			_topBoundary = new Boundary(0, 0, stage.stageWidth, 1);
			stage.addChild(_topBoundary);
			
			_bottomBoundary = new Boundary(0, 499, stage.stageWidth, 550);
			stage.addChild(_bottomBoundary);
			
			_paddle = new Paddle();
			_paddle.setPos(20, 10);
			stage.addChild(_paddle);
			
			_paddle2 = new Paddle();
			_paddle2.setPos(870, 440);
			stage.addChild(_paddle2);
			
			_gameType = gameType;
			
			if (_gameType == CPU) // the variables will be entered on the screen later
			{
				_player:Player;
				_player = new Player("Tom", LEFT_SIDE, HUMAN, 0);
				
				_player2:Player;
				_player2 = new Player("Gus", RIGHT_SIDE, CPU, _difficulty);
			}
			
			_ball = new Ball(_menu.ballColor.selectedColor);
			_ball.setPos(_table.getLength() * 0.5, _table.getWidth() * 0.5);

			stage.addChild(_ball);
			
			_particleSystem = new ParticleSystem();
			stage.addChild(_particleSystem);
			
			_ball.hitStart();
		}
		
		public function onFrameEnter(event:Event):void
		{
			_paddle.update();
			
			if (_gameType == CPU)
			{
				cpuDecision(_player2);
				_paddle2.update();
			}
			
			_particleSystem.createParticle(_ball.getXPos(), _ball.getYPos());
			_ball.update();
			
			_particleSystem.updateParticle();
			if (_ball.hitTestObject(_paddle) || _ball.hitTestObject(_paddle2))
			{
				_ball.collideX();
				//_particleSystem.createParticle(_ball.getXPos(), _ball.getYPos());
			}
			else if (_ball.hitTestObject(_topBoundary) || _ball.hitTestObject(_bottomBoundary))
			{
				_ball.collideY();
				//_particleSystem.createParticle(_ball.getXPos(), _ball.getYPos());
			}
			else if (_ball.getRightBound() < 0)
			{
				trace("You lose a point");
				_ball.setVx(0);
				_ball.setVy(0);
				_ball.setPos(_table.getLength() * 0.5, _table.getWidth() * 0.5);
				_ball.hitStart();
			}
			else if (_ball.getLeftBound() > stage.stageWidth)
			{
				trace("CPU loses a point");
				_ball.setVx(0);
				_ball.setVy(0);
				_ball.setPos(_table.getLength() * 0.5, _table.getWidth() * 0.5);
				_ball.hitStart();
			}
		}
		
		public function cpuDecision(player:Player):void
		{
			if (player.getPlayerSide() == RIGHT_SIDE)
			{
				var difficultyMultiplier:Number = player.getPlayerDifficulty() * Math.random();
				var differentiation:Number = Math.random() - 0.5;
					
				_paddle2.setPos(_paddle2.getXPos(), _ball.getYPos() - (_paddle2.getCurrentHeight() + difficultyMultiplier));
			}
		}
		
		public function onKeyDownHandler(event:KeyboardEvent):void
		{
			if (event.keyCode == Keyboard.UP)
			{
				if (_paddle.hitTestObject(_topBoundary))
				{
					_paddle.stopMovement();
				}
				else
				{
					_paddle.moveUp();
				}
			}
			else if (event.keyCode == Keyboard.DOWN)
			{
				if (_paddle.hitTestObject(_bottomBoundary))
				{
					_paddle.stopMovement();
				}
				else
				{
					_paddle.moveDown();
				}
			}
		}
		
		public function onKeyUpHandler(event:KeyboardEvent):void
		{
			if (event.keyCode == Keyboard.UP)
			{
				_paddle.stopMovement();
			}
			else if (event.keyCode == Keyboard.DOWN)
			{
				_paddle.stopMovement();
			}
		}
	}
}	