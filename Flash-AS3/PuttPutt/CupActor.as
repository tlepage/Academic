package  
{
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
	public class CupActor extends Actor
	{
		private static const TARGET_DIAMETER:int = 24;
		public static const TARGET_INFO:String = "Target";
		
		public function CupActor(parent:DisplayObjectContainer, location:Point) 
		{
			// Create the display
			var cupSprite:Sprite = new CupSprite();
			cupSprite.scaleX = TARGET_DIAMETER / cupSprite.width;
			cupSprite.scaleY = TARGET_DIAMETER / cupSprite.height;
			parent.addChild(cupSprite);
			
			// Create the shape definition
			var cupShapeDef:b2CircleDef = new b2CircleDef();
			cupShapeDef.radius = (TARGET_DIAMETER * 0.5) * PhysicsVals.INV_RATIO;
			cupShapeDef.density = 0.0;
			cupShapeDef.friction = 0.0;
			cupShapeDef.restitution = 0.45;
			cupShapeDef.isSensor = true;
			cupShapeDef.userData = TARGET_INFO;
			
			// Create the body definition (specify location here)
			var cupBodyDef:b2BodyDef = new b2BodyDef();
			cupBodyDef.position.Set(location.x * PhysicsVals.INV_RATIO, location.y * PhysicsVals.INV_RATIO);
			
			// makes the physics engine check for collisions more often
			cupBodyDef.isBullet = false;
			
			// Create the body
			var cupBody:b2Body = PhysicsVals.world.CreateBody(cupBodyDef);
			
			// Create the shape
			cupBody.CreateShape(cupShapeDef);
			cupBody.SetMassFromShapes();
			
			super(cupBody, cupSprite);
		}
		
		// This function returns the center point of the cup
		public function getCenter():Point
		{
			return (new Point(_body.GetWorldCenter().x, _body.GetWorldCenter().y));
		}
	}

}