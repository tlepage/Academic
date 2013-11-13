package  
{
	import Box2D.Collision.Shapes.b2PolygonDef;
	import Box2D.Collision.Shapes.b2ShapeDef;
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
	public class RampActor extends Actor
	{
		private var _force:b2Vec2;
		private var _steepness:Number;
		private var _color:uint;
		
		public function RampActor(parent:DisplayObjectContainer,
								  location:Point,
								  arrayOfCoords:Array,
								  color:uint,
								  force:b2Vec2,
								  steepness:Number,
								  spriteToUse:Class = null,
								  visibleSprite:Boolean = true
								  ) 
		{
			_color = color;
			_force = force;
			_steepness = steepness;
			
			// Modify the force to account for steepness of the ramp
			_force.Multiply(_steepness);
			
			var myBody:b2Body = createBodyFromCoordinates(arrayOfCoords, location);
			var mySprite:Sprite;
			
			if (spriteToUse != null)
			{
				mySprite = new spriteToUse();
				parent.addChild(mySprite);
			}
			else
			{
				mySprite = createSpriteFromCoordinates(arrayOfCoords, location, parent);
			}
			
			if (!visibleSprite)
			{
				mySprite.visible = false;
			}
			
			super(myBody, mySprite);
		}
		
		private function createSpriteFromCoordinates(arrayOfCoords:Array, location:Point, parent:DisplayObjectContainer):Sprite
		{
			var newSprite:Sprite = new Sprite();
			newSprite.graphics.lineStyle(2, _color); // Ctrl-K to get color
			
			for each (var listOfPoints:Array in arrayOfCoords)
			{
				var firstPoint:Point = listOfPoints[0];
				
				newSprite.graphics.moveTo(firstPoint.x, firstPoint.y);
				newSprite.graphics.beginFill(_color);
				
				for each (var newPoint:Point in listOfPoints)
				{
					newSprite.graphics.lineTo(newPoint.x, newPoint.y);
				}
				
				newSprite.graphics.lineTo(firstPoint.x, firstPoint.y);
				newSprite.graphics.endFill();
			}
			
			newSprite.x = location.x;
			newSprite.y = location.y;
			parent.addChild(newSprite);
			
			return (newSprite);
		}
		
		private function createBodyFromCoordinates(arrayOfCoords:Array, location:Point):b2Body
		{
			// Define shapes
			var allShapeDefs:Array = [];
			
			for each (var listOfPoints:Array in arrayOfCoords)
			{
				var newShapeDef:b2PolygonDef = new b2PolygonDef();
				newShapeDef.vertexCount = listOfPoints.length;
				for (var i:int = 0; i < listOfPoints.length; i++)
				{
					var nextPoint:Point = listOfPoints[i];
					b2Vec2(newShapeDef.vertices[i]).Set(nextPoint.x / PhysicsVals.RATIO, 
														nextPoint.y / PhysicsVals.RATIO);
				}
			}
			newShapeDef.density = 0;  // makes this static
			newShapeDef.friction = 0.2;
			newShapeDef.restitution = 0.8;
			
			// Set to sensor
			newShapeDef.isSensor = true;
			
			allShapeDefs.push(newShapeDef);
			
			// Define a body
			var arbitraryBodyDef:b2BodyDef = new b2BodyDef();
			arbitraryBodyDef.position.Set(location.x / PhysicsVals.RATIO, location.y / PhysicsVals.RATIO);
			
			// Create the body
			var arbitraryBody:b2Body = PhysicsVals.world.CreateBody(arbitraryBodyDef);
			
			// Create the shapes
			for each (var newShapeDefToAdd:b2ShapeDef in allShapeDefs)
			{
				arbitraryBody.CreateShape(newShapeDefToAdd); 
			}
			arbitraryBody.SetMassFromShapes();
			
			return arbitraryBody;
		}	
		
		public function get color():uint { return _color; }
		
		public function set color(value:uint):void 
		{
			_color = value;
		}
		
		public function get force():b2Vec2 { return _force; }
		
		public function set force(value:b2Vec2):void 
		{
			_force = value;
		}
		
		public function get steepness():Number { return _steepness; }
		
		public function set steepness(value:Number):void 
		{
			_steepness = value;
		}
		
	}

}