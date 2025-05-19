package voidjam.occ.skills.deviltrigger;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.UUID;

import net.minecraft.network.FriendlyByteBuf;
import org.joml.Math;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import voidjam.occ.gameassets.OCCAnimations;
import voidjam.occ.skills.OCCSkillDataKeys;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class DevilTrigger extends PassiveSkill {
   private static final UUID EVENT_UUID = UUID.fromString("79190c82-eff0-4232-bb21-24205d416637");
   
   public DevilTrigger(Skill.Builder<? extends Skill> builder) {
      super(builder.setActivateType(ActivateType.TOGGLE));
   }
   
   @Override
   public void onInitiate(SkillContainer container) {
      container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
        if ((container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) + (int)Math.round(event.getAttackDamage() * 1.25)) <= 400) {
            container.getDataManager().setDataSync(OCCSkillDataKeys.DT_STACKS.get(), (Integer)container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) + (int)Math.round(event.getAttackDamage() * 1.25), (ServerPlayer)container.getExecuter().getOriginal());
        } else {
            container.getDataManager().setDataSync(OCCSkillDataKeys.DT_STACKS.get(), 400, (ServerPlayer)container.getExecuter().getOriginal());
        }
        
      });
   }

   @Override
   public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
      if (!executer.getSkill(this).getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get()) && (Integer)executer.getSkill(this).getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) > 0 && executer.getSkill(this).getExecuter().getStamina() == executer.getSkill(this).getExecuter().getMaxStamina()) {
         executer.getSkill(this).getExecuter().setStamina(executer.getSkill(this).getExecuter().getStamina() / 2.0F);
         executer.getSkill(this).getDataManager().setDataSync(OCCSkillDataKeys.ACTIVE.get(), true, (ServerPlayer)executer.getSkill(this).getExecuter().getOriginal());
         executer.playAnimationSynchronized(OCCAnimations.DEVIL_TRIGGER, 0.0F);
         executer.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 1, false, false));


      } else if (executer.getSkill(this).getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get())) {
         executer.getSkill(this).getDataManager().setDataSync(OCCSkillDataKeys.ACTIVE.get(), false, (ServerPlayer)executer.getSkill(this).getExecuter().getOriginal());
      }
   }

   @Override
   public void onRemoved(SkillContainer container) {
      container.getExecuter().getEventListener().removeListener(EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID);
      container.getExecuter().getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
   }

   @Override
   @OnlyIn(Dist.CLIENT)
   public boolean shouldDraw(SkillContainer container) {
      return true;
   }

   @Override
   @OnlyIn(Dist.CLIENT)
   public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, (float)gui.getSlidingProgression(), 0.0F);
      guiGraphics.blit(this.getSkillTexture(), (int)x, (int)y, 24, 24, 0.0F, 0.0F, 1, 1, 1, 1);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      guiGraphics.drawString(gui.font, String.valueOf((Integer)container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get())), x + 7.0F, y + 13.0F, 16777215, true);
      poseStack.popPose();
   }

   @Override
   public void updateContainer(SkillContainer container) {
      if (!container.getExecuter().isLogicalClient() && (Integer)container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) > 0 && container.getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get())) {
         Vec3 startColor = new Vec3(0.329f, 0.000f, 0.659f);
         Vec3 endColor = new Vec3(0.000f, 0.196f, 0.651f);
         float scale = 1.175f;
         Entity entity = container.getExecuter().getOriginal();

         DustColorTransitionOptions options = new DustColorTransitionOptions(
            startColor.toVector3f(), endColor.toVector3f(), scale
         );
         ((ServerLevel)entity.level()).sendParticles(options, entity.xo, entity.yo + 1.0, entity.zo, 5, 0.3, 0.45, 0.3, 0.25);
         container.getDataManager().setDataSync(OCCSkillDataKeys.DT_STACKS.get(), (Integer)container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) - 1, (ServerPlayer)entity);
         ((ServerPlayer)((ServerPlayerPatch)container.getExecuter()).getOriginal()).addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 3, 1, false, false));
         ((ServerPlayer)((ServerPlayerPatch)container.getExecuter()).getOriginal()).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 1, false, false));
         ((ServerPlayer)((ServerPlayerPatch)container.getExecuter()).getOriginal()).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1, 1, false, false));
      
      } else if (!container.getExecuter().isLogicalClient() && container.getDataManager().getDataValue(OCCSkillDataKeys.DT_STACKS.get()) == 0 && container.getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get())) {
        container.getDataManager().setDataSync(OCCSkillDataKeys.ACTIVE.get(), false, (ServerPlayer)container.getExecuter().getOriginal());
      }

   }
}
