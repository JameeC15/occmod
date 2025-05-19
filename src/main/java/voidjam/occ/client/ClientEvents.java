package voidjam.occ.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import voidjam.occ.client.input.OCCKeyMappings;
import voidjam.occ.main.OCCMod;
import voidjam.occ.skills.OCCSkillslots;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = OCCMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(OCCKeyMappings.DEVIL_TRIGGER);
        }
    }

    @Mod.EventBusSubscriber(modid = OCCMod.MODID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            Player player = Minecraft.getInstance().player;
            LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
            ControllEngine engine = ClientEngine.getInstance().controllEngine;

            if (playerPatch != null && playerPatch.isLogicalClient()) {
                while (keyPressed(OCCKeyMappings.DEVIL_TRIGGER, true)) {
                    if (playerPatch.getSkill(OCCSkillslots.DEVIL_TRIGGER).getSkill() != null) {
                        SkillContainer skill = playerPatch.getSkill(OCCSkillslots.DEVIL_TRIGGER);
                        if (skill.sendExecuteRequest(playerPatch, engine).isExecutable()) {
                            engine.lockHotkeys();
                        }
                    }
                }
            }
        }

        private static boolean keyPressed(KeyMapping key, boolean eventCheck) {
            boolean consumes = key.consumeClick();

            if (consumes && eventCheck) {
                int mouseButton = InputConstants.Type.MOUSE == key.getKey().getType() ? key.getKey().getValue() : -1;
                InputEvent.InteractionKeyMappingTriggered inputEvent = ForgeHooksClient.onClickInput(mouseButton, key, InteractionHand.MAIN_HAND);

                if (inputEvent.isCanceled()) {
                    return false;
                }
            }

            return consumes;
        }
    }
}