package voidjam.occ.client.screens;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class DeathReplaceOverlay {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void eventHandler(ScreenEvent.Render.Post event) {
		if (event.getScreen() instanceof DeathScreen) {
			int w = event.getScreen().width;
			int h = event.getScreen().height;
			if (true) {
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_look_at_this_fuckin_idiot"), w / 2 + -65, 22, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_dead"), w / 2 + -86, h / 2 + -42, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_unmotivated"), w / 2 + 15, h / 2 + -70, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_lmfao_deadweight"), w / 2 + 50, h / 2 + 62, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_bro_dead_tt"), w / 2 + -109, h / 2 + 67, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_erm_wat_the_sigma"), w / 2 + 82, h / 2 + -13, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_skill_issue"), w / 2 + -161, h / 2 + -80, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_git_gud"), w / 2 + 61, h / 2 + 82, -1, false);
				event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable("gui.occ.death_replace.label_ggez"), w / 2 + -134, h / 2 + 45, -1, false);
			}
		}
	}
}
