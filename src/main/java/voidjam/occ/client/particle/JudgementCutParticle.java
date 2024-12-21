package voidjam.occ.client.particle;

import java.util.Random;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.main.EpicFightMod;


@OnlyIn(Dist.CLIENT)
public class JudgementCutParticle extends HitParticle {
	public JudgementCutParticle(ClientLevel world, double x, double y, double z, SpriteSet animatedSprite) {
		super(world, x, y + 0.35, z, animatedSprite);
	    this.rCol = 1.0F;
	    this.gCol = 1.0F;
	    this.bCol = 1.0F;
	    this.quadSize = 2.2F;
		this.lifetime = 4;
		this.setSpriteFromAge(animatedSprite);
		this.alpha = 2;
		
		float angle = 0.0f;
		this.oRoll = angle;
		this.roll = angle;
	}
	
	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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
			JudgementCutParticle particle = new JudgementCutParticle(worldIn, x, y + 0.35, z, spriteSet);
			return particle;
		}
	}
}