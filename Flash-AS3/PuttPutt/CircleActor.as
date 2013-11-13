package  
{
	import Box2D.Collision.Shapes.b2CircleDef;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.b2BodyDef;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class CircleActor extends Actor
	{
		
		public function CircleActor(parent:DisplayObjectContainer, location:Point, color:uint, radius:Number) 
		{
			// Create the display
			var circleSprite:Sprite = new Sprite();
			//circleSprite.scaleX = (radius * 2) / circleSprite.width;
			//circleSprite.scaleY = (radius * 2) / circleSprite.height;
			
			// Draw the circle
			circleSprite.graphics.beginFill(color);
			circleSprite.graphics.drawCircle(location.x, location.y, radius); 
			circleSprite.graphics.endFill();
			
			// Add to child list
			parent.addChild(circleSprite);
			
			// Create the shape definition
			var circleShapeDef:b2CircleDef = new b2CircleDef();
			circleShapeDef.radius = (radius) * PhysicsVals.INV_RATIO;
			circleShapeDef.density = 0.0;
			circleShapeDef.friction = 0.0;
			circleShapeDef.restitution = 0.45;
			circleShapeDef.isSensor = false;
			
			// Create the body definition (specify location here)
			var circleBodyDef:b2BodyDef = new b2BodyDef();
			circleBodyDef.position.Set(location.x * PhysicsVals.INV_RATIO, location.y * PhysicsVals.INV_RATIO);
			
			// makes the physics engine check for collisions more often
			circleBodyDef.isBullet = false;
			
			// Create the body
			var circleBody:b2Body = PhysicsVals.world.CreateBody(circleBodyDef);
			
			// Create the shape
			circleBody.CreateShape(circleShapeDef);
			circleBody.SetMassFromShapes();
			
			super(circleBody, circleSprite);
		}
		
	}

}