package voidjam.occ.events.engine;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import voidjam.occ.main.OCCMod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid=OCCMod.MODID, value=Dist.CLIENT, bus=EventBusSubscriber.Bus.MOD)
public class OCCClientModBusEvent {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onParticleRegistry(final RegisterParticleProvidersEvent event) {
		Minecraft mc = Minecraft.getInstance();
		
    }
}