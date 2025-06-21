package voidjam.occ.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.client.input.CombatKeyMapping;
import yesman.epicfight.main.EpicFightMod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class OCCKeyMappings {
	public static final KeyMapping DEVIL_TRIGGER = new CombatKeyMapping("key.occ.devil_trigger", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key." + EpicFightMod.MODID + ".combat");
}