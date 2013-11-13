package particles
{
	import flash.display.DisplayObjectContainer;
	import flash.display.MovieClip;
	import flash.geom.ColorTransform;
	
	public class ParticleSystem extends MovieClip
	{
		
		// Constants:
		// Public Properties:
		// Private Properties:
		private var particleArray:Array;
		private var particleMaxSpeed:Number = 2.5;
		private var particleFadeSpeed:Number = 0.025;
		private var particleTotal:Number = 1;
		private var particleRange:Number = 100;
		private var particleCurrentAmount:Number = 0;
		private var _parent:DisplayObjectContainer;
		
		// Initialization:
		public function ParticleSystem(parent:DisplayObjectContainer) 
		{ 
			particleArray = [];
			_parent = parent;
		}
	
		// Public Methods:
		
		public function createParticle(targetX:Number, targetY:Number):void
		{
			//run for loop based on particleTotal
			for (var i:Number = 0; i < particleTotal; i++)
			{
				var particle:Particle = new Particle();
				//var oldColor:ColorTransform = particle.transform.colorTransform;
				//var newColor:ColorTransform = 
					//new ColorTransform(oldColor.redMultiplier + Math.random(), 0, 0, 0, 0, 0);
				//particle.transform.colorTransform = newColor;
				
				//set position & rotation, alpha
				particle.x = targetX
				particle.y = targetY
				particle.rotation = Math.random() * 360;
				particle.alpha = Math.random() * .5 + .5;
				//set particle boundry
				particle.boundLeft = targetX - particleRange;
				particle.boundTop = targetY - particleRange;
				particle.boundRight = targetX + particleRange;
				particle.boundBottom = targetY + particleRange;
				//set speed/direction of fragment
				particle.speedX = Math.random() * particleMaxSpeed - Math.random() * particleMaxSpeed;
				particle.speedY = Math.random() * particleMaxSpeed + Math.random() * particleMaxSpeed;
				particle.speedX *= particleMaxSpeed
				particle.speedY *= -particleMaxSpeed
				//set fade out speed
				particle.fadeSpeed = Math.random() * particleFadeSpeed;
				//just a visual particle counter
				particleCurrentAmount++;
				// add to array
				particleArray.push(particle);
				// add to display list
				_parent.addChildAt(particle, 0);
			}
		}

		public function updateParticle():void
		{
			for (var i = 0; i < particleArray.length; i++)
			{
				var tempParticle:Particle = particleArray[i];
				//update alpha, x, y
				tempParticle.alpha -= tempParticle.fadeSpeed;
				tempParticle.x += tempParticle.speedX;
				tempParticle.y += tempParticle.speedY;
				// if fragment is invisible remove it
				if (tempParticle.alpha <= 0)
				{
					destroyParticle(tempParticle);
				}
				// if fragment is out of bounds, increase fade out speed
				else if (tempParticle.x < tempParticle.boundLeft ||
						tempParticle.x > tempParticle.boundRight || 
						tempParticle.y < tempParticle.boundTop ||
						tempParticle.y > tempParticle.boundBottom)
				{
					tempParticle.fadeSpeed += .05;
				}
			}
		}
		
		private function destroyParticle(particle:Particle):void
		{
			for (var i = 0; i < particleArray.length; i++)
			{
				var tempParticle:Particle = particleArray[i];
				if (tempParticle == particle)
				{
					particleCurrentAmount--;
					particleArray.splice(i,1);
					_parent.removeChild(tempParticle);
				}
			}
		}

		// Protected Methods:
	}
	
}