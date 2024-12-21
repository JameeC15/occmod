package voidjam.occ.client.input;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import voidjam.occ.main.OCCMod;
import yesman.epicfight.client.input.CombatKeyMapping;
import yesman.epicfight.main.EpicFightMod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class OCCKeyMappings {
	public static final KeyMapping DEVIL_TRIGGER = new CombatKeyMapping("key." + EpicFightMod.MODID + ".devil_trigger", InputConstants.Type.KEYSYM, InputConstants.KEY_G, "key." + EpicFightMod.MODID + ".combat");
	
	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(DEVIL_TRIGGER);
	}
}