package voidjam.occ.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voidjam.occ.main.OCCMod;
import yesman.epicfight.particle.HitParticleType;

public class OCCParticles {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OCCMod.MODID);

	public static final RegistryObject<HitParticleType> SLASH_DIMENSION = PARTICLES.register("slash_dimension", () -> new HitParticleType(true));
	public static final RegistryObject<HitParticleType> SLASH_DIMENSION_FOG = PARTICLES.register("slash_dimension_fog", () -> new HitParticleType(true));
	public static final RegistryObject<HitParticleType> JUDGEMENT_CUT = PARTICLES.register("judgement_cut", () -> new HitParticleType(true));
	public static final RegistryObject<HitParticleType> GIANT_SLASH = PARTICLES.register("giant_slash", () -> new HitParticleType(true));
	public static final RegistryObject<HitParticleType> SWORD_BARRAGE = PARTICLES.register("slash_barrage", () -> new HitParticleType(true));
	
}