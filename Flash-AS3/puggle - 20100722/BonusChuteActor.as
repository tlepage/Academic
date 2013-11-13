package  
{
	import Box2D.Collision.b2Bound;
	import Box2D.Collision.Shapes.b2PolygonDef;
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
	public class BonusChuteActor extends Actor
	{
		public static const BONUS_TARGET:String = "BonusTarget";
		
		private static const TRAVEL_SPEED:Number = 2;
		
		private var _bounds:Array;
		private var _yPos:int;
		private var _direction:int;
		
		public function BonusChuteActor(parent:DisplayObjectContainer, leftBounds:int, rightBounds:int, yPos:int) 
		{
			_bounds = [leftBounds, rightBounds];
			_yPos = yPos;
			_direction = 1;
			
			var chuteGraphic:Sprite = new BonusChuteGraphic();
			parent.addChild(chuteGraphic);
			
			// create all polygons in clockwise order
			var leftRampShapeDef:b2PolygonDef = new b2PolygonDef();
			leftRampShapeDef.vertexCount = 3;
			b2Vec2(leftRampShapeDef.vertices[0]).Set( -90/ PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			b2Vec2(leftRampShapeDef.vertices[1]).Set( -75 / PhysicsVals.RATIO, -12 / PhysicsVals.RATIO);
			b2Vec2(leftRampShapeDef.vertices[2]).Set( -75 / PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			leftRampShapeDef.friction = 0.1
			leftRampShapeDef.restitution = 0.6;
			leftRampShapeDef.density = 0;
			
			var rightRampShapeDef:b2PolygonDef = new b2PolygonDef();
			rightRampShapeDef.vertexCount = 3;
			b2Vec2(rightRampShapeDef.vertices[0]).Set( 90 / PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			b2Vec2(rightRampShapeDef.vertices[1]).Set( 75 / PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			b2Vec2(rightRampShapeDef.vertices[2]).Set( 75 / PhysicsVals.RATIO, -12 / PhysicsVals.RATIO);
			rightRampShapeDef.friction = 0.1
			rightRampShapeDef.restitution = 0.6;
			rightRampShapeDef.density = 0;
			
			var centerHoleShapeDef:b2PolygonDef = new b2PolygonDef();
			centerHoleShapeDef.vertexCount = 4;
			b2Vec2(centerHoleShapeDef.vertices[0]).Set(-75 / PhysicsVals.RATIO, -12 / PhysicsVals.RATIO);
			b2Vec2(centerHoleShapeDef.vertices[1]).Set(75 / PhysicsVals.RATIO, -12 / PhysicsVals.RATIO);
			b2Vec2(centerHoleShapeDef.vertices[2]).Set(75 / PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			b2Vec2(centerHoleShapeDef.vertices[3]).Set( -75 / PhysicsVals.RATIO, 12 / PhysicsVals.RATIO);
			centerHoleShapeDef.friction = 0.1;
			centerHoleShapeDef.restitution = 0.6;
			centerHoleShapeDef.density = 1;
			centerHoleShapeDef.isSensor = true;
			centerHoleShapeDef.userData = BonusChuteActor.BONUS_TARGET;
			
			var chuteBodyDef:b2BodyDef = new b2BodyDef();
			chuteBodyDef.position.Set(((leftBounds + rightBounds) / 2) / PhysicsVals.RATIO, yPos / PhysicsVals.RATIO);
			
			// doesn't allow rotation						
			chuteBodyDef.fixedRotation = true;	
			
			var chuteBody:b2Body = PhysicsVals.world.CreateBody(chuteBodyDef);
			chuteBody.CreateShape(leftRampShapeDef);
			chuteBody.CreateShape(rightRampShapeDef);
			chuteBody.CreateShape(centerHoleShapeDef);
			chuteBody.SetMassFromShapes();
			
			super(chuteBody, chuteGraphic);
		}
		
		override protected function childSpecificUpdating():void 
		{
			if (_display.x >= _bounds[1])
			{
				_direction = -1;
			}
			else if (_display.x <= _bounds[0])
			{
				_direction = 1;
			}
			
			var idealLocation:b2Vec2 = new b2Vec2(_display.x + (_direction * TRAVEL_SPEED), _yPos);
			var directionToTravel:b2Vec2 = new b2Vec2(idealLocation.x - _display.x, idealLocation.y - _display.y);
			//trace("I want to travel " + directionToTravel.x + ", " + directionToTravel.y);
			
			// The distance we want to travel by in one frame, in meters
			directionToTravel.Multiply( 1 / PhysicsVals.RATIO);
			
			// The distance we want to travel in one second, in meters
			directionToTravel.Multiply(PhysicsVals.FPS);  
			_body.SetLinearVelocity(directionToTravel);
			
			super.childSpecificUpdating();
		}
		
	}

}