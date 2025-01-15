package voidjam.occ.client.particle.effekseer;

import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import voidjam.occ.main.OCCMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OCCEffekseerLoader {
	public static final ParticleEmitterInfo SLASHES = new ParticleEmitterInfo(new ResourceLocation(OCCMod.MODID,"sword")).scale(0.2F, 0.2F, 0.2F);
	public static final ParticleEmitterInfo JUDGEMENT_CUT = new ParticleEmitterInfo(new ResourceLocation(OCCMod.MODID,"judgement_cut")).scale(0.4F,0.4F,0.4F);
    public static final ParticleEmitterInfo JCE = new ParticleEmitterInfo(new ResourceLocation(OCCMod.MODID,"jce"));
}
