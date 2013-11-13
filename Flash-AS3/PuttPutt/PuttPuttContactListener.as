package  
{
	import Box2D.Collision.b2ContactPoint;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2ContactListener;
	import flash.geom.Point;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PuttPuttContactListener extends b2ContactListener
	{
		
		public function PuttPuttContactListener() 
		{
			
		}
		
		/*
		override public function Add(point:b2ContactPoint):void
		{
			trace("Added contact");
			super.Add(point);

		}*/
		
		// This function overrides the Persist constact listener in the physics world
		override public function Persist(point:b2ContactPoint):void
		{
			var ballCenter:b2Vec2;
			var cupCenter:b2Vec2;
			var distance:Number;
			
			// Check to see if the two shapes that are in contact are the cup and the ball
			if (point.shape1.GetBody().GetUserData() is CupActor &&
				point.shape2.GetBody().GetUserData() is BallActor)
			{
				// Check to see if at least half the ball is over the hole
				ballCenter = point.shape2.GetBody().GetWorldCenter();
				cupCenter = point.shape1.GetBody().GetWorldCenter();
				
				// Calculate the distance between the center of the ball and the side of the cup
				distance = new Point(cupCenter.x - ballCenter.x, cupCenter.y - ballCenter.y).length;
				
				// Check to see if the distance is acceptable for the ball to fall in the cup
				// This allows for part of the ball to roll over the side of the cup without it falling in
				if (Math.abs(distance) <= (BallActor.BALL_DIAMETER * PhysicsVals.INV_RATIO + PhysicsVals.FALL_OFFSET) / 2)
				{
					BallActor(point.shape2.GetBody().GetUserData()).ballInCup();
				}
			}
			else if (point.shape2.GetBody().GetUserData() is CupActor &&
				     point.shape1.GetBody().GetUserData() is BallActor)
			{
				// Check to see if at least half the ball is over the hole
				ballCenter = point.shape1.GetBody().GetWorldCenter();
				cupCenter = point.shape2.GetBody().GetWorldCenter();
				
				// Calculate the distance between the center of the ball and the side of the cup
				distance = new Point(cupCenter.x - ballCenter.x, cupCenter.y - ballCenter.y).length;
				
				// Check to see if the distance is acceptable for the ball to fall in the cup
				// This allows for part of the ball to roll over the side of the cup without it falling in
				if (Math.abs(distance) <= (BallActor.BALL_DIAMETER * PhysicsVals.INV_RATIO + PhysicsVals.FALL_OFFSET) / 2)
				{
					BallActor(point.shape1.GetBody().GetUserData()).ballInCup();
				}
			}
			
			// Check to see if ball and ramp are persistently colliding
			else if (point.shape1.GetBody().GetUserData() is RampActor &&
				point.shape2.GetBody().GetUserData() is BallActor)
			{
				// Affect the ball's roll by applying a minute impulse to it
				BallActor(point.shape2.GetBody().GetUserData()).rollOverRamp(RampActor(point.shape1.GetBody().GetUserData()).force);
				trace("Applying ramp force");
			}
			else if (point.shape2.GetBody().GetUserData() is RampActor &&
				     point.shape1.GetBody().GetUserData() is BallActor)
			{
				BallActor(point.shape1.GetBody().GetUserData()).rollOverRamp(RampActor(point.shape2.GetBody().GetUserData()).force);
				trace("Applying ramp force");
			}
			
			super.Persist(point);
		}
		
		override public function Remove(point:b2ContactPoint):void
		{
			// Check to see if ball and ramp are persistently colliding
			if (point.shape1.GetBody().GetUserData() is RampActor &&
				point.shape2.GetBody().GetUserData() is BallActor)
			{
				// Affect the ball's roll by applying a minute impulse to it
				BallActor(point.shape2.GetBody().GetUserData()).onRamp = false;
				trace("Stopping ramp force");
			}
			else if (point.shape2.GetBody().GetUserData() is RampActor &&
				     point.shape1.GetBody().GetUserData() is BallActor)
			{
				BallActor(point.shape1.GetBody().GetUserData()).onRamp = false;
				trace("Stopping ramp force");
			}
		}
		
	}

}