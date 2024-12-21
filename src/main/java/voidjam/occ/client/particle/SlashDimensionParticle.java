package voidjam.occ.client.particle;

import java.util.Random;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.HitParticle;

@OnlyIn(Dist.CLIENT)
public class SlashDimensionParticle extends HitParticle {
	public SlashDimensionParticle(ClientLevel world, double x, double y, double z, SpriteSet animatedSprite) {
		super(world, x, y, z, animatedSprite);
	    this.rCol = 1.0F;
	    this.gCol = 1.0F;
	    this.bCol = 1.0F;
	    this.quadSize = 3F;
		this.lifetime = 20;
		this.setSpriteFromAge(animatedSprite);
		
		Random rand = new Random();
		float angle = (float)Math.toRadians(45.0F + ((rand.nextFloat() - 0.5F) * 90F) + (rand.nextBoolean() ? 0f : 180f));
		this.oRoll = angle;
		this.roll = angle;
	}
	
	@Override
	public void tick() {
	      if (this.age++ >= this.lifetime) {
	         this.remove();
	      } else {
	         this.setSpriteFromAge(this.animatedSprite);
	      }
	   }
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
	         this.spriteSet = spriteSet;
	    }
	    
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SlashDimensionParticle particle = new SlashDimensionParticle(worldIn, x, y, z, spriteSet);
			return particle;
		}
	}
}