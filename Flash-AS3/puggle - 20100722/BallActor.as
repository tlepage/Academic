package  
{
	import Box2D.Collision.b2Bound;
	import Box2D.Collision.b2Collision;
	import Box2D.Collision.Shapes.b2CircleDef;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.b2BodyDef;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class BallActor extends Actor
	{
		private static const BALL_DIAMETER:int = 12;
		
		public function BallActor(parent:DisplayObjectContainer, location:Point, initVel:Point) 
		{
			// Create the display
			var ballSprite:Sprite = new BallSprite();
			ballSprite.scaleX = BALL_DIAMETER / ballSprite.width;
			ballSprite.scaleY = BALL_DIAMETER / ballSprite.height;
			parent.addChild(ballSprite);
			
			// Create the shape definition
			var ballShapeDef:b2CircleDef = new b2CircleDef();
			ballShapeDef.radius = BALL_DIAMETER / 2 / PhysicsVals.RATIO;
			ballShapeDef.density = 1.5;
			ballShapeDef.friction = 0.0;
			ballShapeDef.restitution = 0.45;
			
			// Create the body definition (specify location here)
			var ballBodyDef:b2BodyDef = new b2BodyDef();
			ballBodyDef.position.Set(location.x / PhysicsVals.RATIO, location.y / PhysicsVals.RATIO);
			
			// makes the physics engine check for collisions more often
			ballBodyDef.isBullet = true;
			
			// Create the body
			var ballBody:b2Body = PhysicsVals.world.CreateBody(ballBodyDef);
			
			// Create the shape
			ballBody.CreateShape(ballShapeDef);
			ballBody.SetMassFromShapes();
			
			// Set the velocity to match our parameter
			var velocityVector:b2Vec2 = new b2Vec2(initVel.x / PhysicsVals.RATIO, initVel.y / PhysicsVals.RATIO);
			ballBody.SetLinearVelocity(velocityVector);
			
			super(ballBody, ballSprite);
		}
		
		public function hitBonusTarget():void
		{
			dispatchEvent(new BallEvent(BallEvent.BALL_HIT_BONUS));
		}
		
		
		override protected function childSpecificUpdating():void 
		{
			if (_display.y > _display.stage.stageHeight)
			{
				dispatchEvent(new BallEvent(BallEvent.BALL_OFF_SCREEN));
			}
			
			super.childSpecificUpdating();
		}
		
	}

}