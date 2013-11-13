package  
{
	import Box2D.Collision.b2Bound;
	import Box2D.Collision.b2Collision;
	import Box2D.Collision.Shapes.b2CircleDef;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.b2BodyDef;
	import com.greensock.TweenLite;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class BallActor extends Actor
	{
		public static const BALL_DIAMETER:int = 12;
		public static const BALL_DATA:String = "Ball";
		
		private var _isDragging:Boolean = false;
		private var _isMoving:Boolean = false;
		private var _onRamp:Boolean = false;
		
		private var _xVel:Number;
		private var _yVel:Number;
		private var _power:Number;
		
		private var _direction:Number;
		private var _target:Point;
		
		private var _dragStartPoint:Point;
		private var _dragEndPoint:Point;
		private var _dragSet:Boolean;
		
		public function BallActor(parent:DisplayObjectContainer, location:Point, initVel:Point) 
		{
			// Create the display
			var ballSprite:Sprite = new BallSprite();
			ballSprite.scaleX = BALL_DIAMETER / ballSprite.width;
			ballSprite.scaleY = BALL_DIAMETER / ballSprite.height;
			parent.addChild(ballSprite);
			
			// Create the shape definition
			var ballShapeDef:b2CircleDef = new b2CircleDef();
			ballShapeDef.radius = (BALL_DIAMETER / 2) / PhysicsVals.RATIO;
			ballShapeDef.density = 1.15;
			ballShapeDef.friction = 0.1;
			ballShapeDef.restitution = 0.85;
			
			// Create the body definition (specify location here)
			var ballBodyDef:b2BodyDef = new b2BodyDef();
			ballBodyDef.position.Set(location.x / PhysicsVals.RATIO, location.y / PhysicsVals.RATIO);
			
			// makes the physics engine check for collisions more often
			ballBodyDef.isBullet = false;
			
			// Create the body
			var ballBody:b2Body = PhysicsVals.world.CreateBody(ballBodyDef);
			
			// Create the shape
			ballBody.CreateShape(ballShapeDef);
			ballBody.SetMassFromShapes();
			
			// Set the velocity to match our parameter
			//var velocityVector:b2Vec2 = new b2Vec2(initVel.x / PhysicsVals.RATIO, initVel.y / PhysicsVals.RATIO);
			//ballBody.SetLinearVelocity(velocityVector);
			
			// Initialize some parameters
			_xVel = 0;
			_yVel = 0;
			_power = 1.0;
			_direction = 0;
			_target = null;
			_dragSet = false;
			
			_onRamp = false;
			
			super(ballBody, ballSprite);
		}
		
		// This function applies friction to the moving ball
		public function applyFriction(newtons:Number):void
		{
			if (Number(Math.abs(_body.GetLinearVelocity().x)) > 0.00 && Number(Math.abs(_body.GetLinearVelocity().y)) > 0.00)
			{
				var xFriction:Number = Number(_body.GetLinearVelocity().x) * newtons;
				var yFriction:Number = Number(_body.GetLinearVelocity().y) * newtons;
				
				var frictionForce:b2Vec2 = new b2Vec2(-xFriction, -yFriction);
				var center:b2Vec2 = _body.GetWorldCenter();
			
				//trace("Applying " + Number(xFriction) + " newtons of friction in the x.");
				_body.ApplyForce(frictionForce, center);
			}
		}

		// This function stops the ball from moving
		public function stop():void
		{
			_body.SetLinearVelocity(new b2Vec2(0, 0));
			_body.SetAngularVelocity(0);
			_isMoving = false;
			
			_xVel = 0;
			_yVel = 0;
			_direction = 0;
		}
		
		// This function fires off an event to indicate that the ball has landed in the target
		public function ballInCup():void
		{
			dispatchEvent(new BallEvent(BallEvent.BALL_IN_CUP));
		}
		
		public function hitBonusTarget():void
		{
			dispatchEvent(new BallEvent(BallEvent.BALL_HIT_BONUS));
		}
	
		// This function indicates that the velocity and aim are being calculated
		public function startDrag(mouseX:Number, mouseY:Number):void
		{
			// Indicate that the dragging has started
			_isDragging = true;
			_dragSet = false;
			_dragStartPoint = new Point(mouseX * PhysicsVals.INV_RATIO, mouseY * PhysicsVals.INV_RATIO);
		}
		
		// This function indicates that the dragging has stopped and sets the velocity and direction
		public function stopDrag(mouseX:Number, mouseY:Number):void
		{
			_dragEndPoint = new Point(mouseX * PhysicsVals.INV_RATIO, mouseY * PhysicsVals.INV_RATIO);
			
			// Indicate that the dragging has stopped
			_isDragging = false;
			
			// Get length of drag
			_xVel = Math.abs(_dragEndPoint.x - _dragStartPoint.x);
			_yVel = Math.abs(_dragEndPoint.y - _dragStartPoint.y);
			
			_dragSet = true;
			// Scale down the velocity to meters per pixel
			_xVel *= PhysicsVals.INV_RATIO;
			_yVel *= PhysicsVals.INV_RATIO;
			
			// scale down speed
			//_xVel *= PhysicsVals.VELOCITY_ADJ;
			//_yVel *= PhysicsVals.VELOCITY_ADJ;
			
			// Set the direction
			//_direction = Math.atan2(_yVel, _xVel);
			
			// Modification to handle directions
			/*
			if (_xVel < 0) 
			{
				_xVel *= -1;
			}
			
			if (_yVel < 0)
			{
				_yVel *= -1;
			}*/
			
			// Apply the impulse to the ball to set it in motion
			_body.ApplyImpulse(new b2Vec2(_xVel * Math.cos(_direction), _yVel * Math.sin(_direction)), _body.GetWorldCenter());
			
			// Indicate that the ball is now moving
			_isMoving = true;
	
			//trace("XVel: " + _xVel + ", YVel: " + _yVel);
		}
		
		public function setTarget(target:Point):void
		{
			_target = target;
			_target.x *= PhysicsVals.INV_RATIO;
			_target.y *= PhysicsVals.INV_RATIO;
			
			// reassign direction
			_direction = Math.atan2(_target.y - _body.GetWorldCenter().y, _target.x - _body.GetWorldCenter().x);
			//_body.ApplyImpulse(new b2Vec2(2 * Math.cos(_direction), 2 * Math.sin(_direction)), _body.GetWorldCenter());
			
			trace(_direction);
		}
		
		public function hitBall():void
		{
			if (_target != null)
			{
				_body.ApplyImpulse(new b2Vec2(_power * Math.cos(_direction), _power * Math.sin(_direction)), _body.GetWorldCenter());
				_isMoving = true;
			}
			
			_power = 1.0;
		}
		
		// Applies a minute impulse to ball to simulate going over a ramp
		public function rollOverRamp(force:b2Vec2):void
		{
			// Apply the modified ramp force
			_body.ApplyImpulse(force, _body.GetWorldCenter());
			
			// Set the onRamp flag
			_onRamp = true;
		}
		
		override protected function childSpecificUpdating():void 
		{
			if (_display.y > _display.stage.stageHeight)
			{
				dispatchEvent(new BallEvent(BallEvent.BALL_OFF_SCREEN));
			}
			
			// Update the velocity fields based on where the mouse is
			// TODO:  May not need to do this
			if (_isDragging)
			{
				//_xVel = (_display.x * PhysicsVals.INV_RATIO) - _display.mouseX;
				//_yVel = (_display.y * PhysicsVals.INV_RATIO) - _display.mouseY;
			 
				//_xVel = _display.x - _display.mouseX;
				//_yVel = _display.y - _display.mouseY;
			
				//_direction = Math.atan2(_yVel, _xVel);
				
				trace("x: " + _xVel + ", y: " + _yVel);
			}
			
			// If the ball's velocity has dropped below the velocity threshold
			if (Math.abs(_body.GetLinearVelocity().x) < PhysicsVals.MIN_VEL && Math.abs(_body.GetLinearVelocity().y) < PhysicsVals.MIN_VEL && !_onRamp)
			{
				// Fire an event to indicate that the ball has stopped
				dispatchEvent(new BallEvent(BallEvent.BALL_STOPPED));
			}
			
			super.childSpecificUpdating();
		}

		// Returns the display position of the ball
		public function getDisplayPosition():Point
		{
			return (new Point(_display.x, _display.y));
		}
		
		public function get xVel():Number 
		{
			return _xVel; 
		}
		
		public function get yVel():Number 
		{ 
			return _yVel; 
		}
		
		public function get direction():Number 
		{ 
			return _direction; 
		}
		
		public function get isDragging():Boolean 
		{ 
			return _isDragging; 
		}
		
		public function get isMoving():Boolean 
		{ 
			return _isMoving; 
		}
		
		public function get target():Point { return _target; }
		
		public function set target(value:Point):void 
		{
			_target = value;
		}
		
		public function get dragSet():Boolean { return _dragSet; }
		
		public function get power():Number 
		{ 
			return _power; 
		}
		
		public function set power(value:Number):void 
		{
			_power = value * PhysicsVals.INV_RATIO;
		}
		
		public function get onRamp():Boolean { return _onRamp; }
		
		public function set onRamp(value:Boolean):void 
		{
			_onRamp = value;
		}
		
	}

}