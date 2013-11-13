package  
{
	import Box2D.Collision.Shapes.b2CircleDef;
	import Box2D.Common.Math.b2Vec2;
	import Box2D.Dynamics.b2Body;
	import Box2D.Dynamics.b2BodyDef;
	import com.greensock.TweenLite;
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	/**
	 * ...
	 * @author Tom LePage
	 */
	public class PegActor extends Actor
	{
		public static const NORMAL:int = 1;
		public static const GOAL:int = 2;
		
		// The diameter of all the pegs, in pixels
		private static const PEG_DIAMETER:int = 19;
		
		private var _beenHit:Boolean;
		private var _pegType:int;
		
		public function PegActor(parent:DisplayObjectContainer, location:b2Vec2, type:int) 
		{
			_beenHit = false;
			_pegType = type;
			
			// Create the display aspect of the peg
			var pegMovie:MovieClip = new PegMovie();
			pegMovie.scaleX = PEG_DIAMETER / pegMovie.width;
			pegMovie.scaleY = PEG_DIAMETER / pegMovie.height;
			parent.addChild(pegMovie);
			
			// Create shape definition
			var pegShapeDef:b2CircleDef = new b2CircleDef();
			pegShapeDef.radius = ((PEG_DIAMETER / 2 ) / PhysicsVals.RATIO);
			pegShapeDef.density = 0;
			pegShapeDef.friction = 0;
			pegShapeDef.restitution = 0.45;
			
			// Create body definition
			var pegBodyDef:b2BodyDef = new b2BodyDef();
			pegBodyDef.position.Set(location.x / PhysicsVals.RATIO, location.y / PhysicsVals.RATIO);
			
			// Create the body
			var pegBody:b2Body = PhysicsVals.world.CreateBody(pegBodyDef);
			
			// Create the shape
			pegBody.CreateShape(pegShapeDef);
			pegBody.SetMassFromShapes();
			
			// call parent constructor
			super(pegBody, pegMovie);
			
			// Set what frame the movie is going to be at.
			setMovieFrame();
		}
		
		public function hitByBall():void
		{
			if (!_beenHit)
			{
				_beenHit = true;
				setMovieFrame();
				
				dispatchEvent(new PegEvent(PegEvent.PEG_LIT_UP));
			}
		}
		
		public function fadeOut(pegNumber:int):void
		{
			TweenLite.to(_display, 0.3, { alpha:0, delay:0.08 * pegNumber, onComplete:sendFadeOutDone} );
			// if you want to call a function after Tween, use onComplete
			//TweenLite.to(_display, 0.3, { alpha:0, delay:0.08 * pegNumber, onComplete:destroy } );
		}
		
		private function sendFadeOutDone():void
		{
			dispatchEvent(new PegEvent(PegEvent.DONE_FADING_OUT));
		}
		
		public function setType(newType:int):void
		{
			_pegType = newType;
			setMovieFrame();
		}
		
		// determine what to draw based on the state/type of the peg
		private function setMovieFrame():void
		{
			if (_pegType == NORMAL)
			{
				if (_beenHit)
				{
					MovieClip(_display).gotoAndStop(2);
				}
				else
				{
					MovieClip(_display).gotoAndStop(1);
				}
			}
			else if (_pegType == GOAL)
			{
				if (_beenHit)
				{
					MovieClip(_display).gotoAndStop(4);
				}
				else
				{
					MovieClip(_display).gotoAndStop(3);
				}
			}
			else
			{
				throw (new Error("Invalid peg type used!"));
			}
		}
		
	}

}