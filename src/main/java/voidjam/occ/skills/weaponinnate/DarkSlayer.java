package voidjam.occ.skills.weaponinnate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.netty.buffer.Unpooled;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import voidjam.occ.gameassets.OCCAnimations;
import voidjam.occ.skills.OCCSkillDataKeys;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.StaticAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkSlayer extends WeaponInnateSkill {
   private static final UUID EVENT_UUID = UUID.fromString("63c38d4f-cc97-4339-bedf-d9bba36ba29f");

   private final StaticAnimationProvider[] setanimations = new StaticAnimationProvider[9];
   private final Map<StaticAnimation, AttackAnimation> comboAnimation = Maps.newHashMap();

   public DarkSlayer(WeaponInnateSkill.Builder builder) {
      super(builder);
      this.setanimations[0] = () -> {
         return OCCAnimations.JUDGEMENT_CUT;
      };
      this.setanimations[1] = () -> {
         return OCCAnimations.JUDGEMENT_CUT;
      };
      this.setanimations[2] = () -> {
         return OCCAnimations.YAMATO_POWER_DASH;
      };
      this.setanimations[3] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_A;
      };
      this.setanimations[4] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_EXTEND_TARGETED;
      };
      this.setanimations[5] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_TARGETED;
      };
      this.setanimations[6] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_EXTEND;
      };
      this.setanimations[7] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_EXTEND_TARGETED;
      };
      this.setanimations[8] = () -> {
         return OCCAnimations.JUDGEMENT_CUT_END;
      };
   }

   public void onInitiate(SkillContainer container) {
		super.onInitiate(container);
	}

	public void onRemoved(SkillContainer container) {
	}

   @OnlyIn(Dist.CLIENT)
   public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine) {
      Input input = ((LocalPlayer)executer.getOriginal()).input;
      float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity)executer.getOriginal()), 0.0F, 1.0F);
      input.tick(false, pulse);

      int forward = input.up ? 1 : 0;
      int backward = input.down ? -1 : 0;

      FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
      buf.writeInt(forward);
      buf.writeInt(backward);
      return buf;
   }

   @OnlyIn(Dist.CLIENT)
   public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
      int forward = args.readInt();
      int backward = args.readInt();

      int vertic = forward + backward;

      int setanimation;

      if (vertic == 0) {
         setanimation = 0;
      } else {
         setanimation = vertic >= 0 ? 2 : 1;
      }

      CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
      packet.getBuffer().writeInt(setanimation);
      return packet;
   }

   public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
      ServerPlayer player = (ServerPlayer)executer.getOriginal();
      DynamicAnimation animation = executer.getAnimator().getPlayerFor(null).getAnimation();
		
      int i = args.readInt();
      
      if (!player.onGround() && !player.isInWater() && (player.level().isEmptyBlock(player.blockPosition().below()) || player.yo - (double)player.blockPosition().getY() > 0.2)) {
         if (i == 0 || i == 2) {
            executer.playAnimationSynchronized(this.setanimations[3].get(), 0.0F);
         } else {
            executer.playAnimationSynchronized(this.setanimations[4].get(), 0.0F);
         }
        
      } else if (player.onGround() || player.isInWater()) {
         if (this.comboAnimation.containsKey(animation)) {
            executer.playAnimationSynchronized(this.comboAnimation.get(animation), 0.0F);
            super.executeOnServer(executer, args);
         } else {
            if (i ==0) {
               if (executer.getTarget() != null && executer.getTarget().isAlive()) {
                  executer.playAnimationSynchronized(this.setanimations[5].get(), 0.0F);
               } else {
                  executer.playAnimationSynchronized(this.setanimations[i].get(), 0.0F);
               }
            } else {
               executer.playAnimationSynchronized(this.setanimations[i].get(), 0.0F);
            }
           
         }
      }
      super.executeOnServer(executer, args);
    }

	public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
		List<Component> list = Lists.newArrayList();
		String traslatableText = this.getTranslationKey();
		list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
		list.add(Component.translatable(traslatableText + ".tooltip").withStyle(ChatFormatting.DARK_GRAY));
		this.generateTooltipforPhase(list, itemStack, cap, playerCap, (Map)this.properties.get(0), "Slashes:");
		return list;
	}

   public boolean isExecutableState(PlayerPatch<?> executer) {
      executer.updateEntityState();
      EntityState playerState = executer.getEntityState();
      return !((Player)executer.getOriginal()).isFallFlying() && executer.currentLivingMotion != LivingMotions.FALL && playerState.canUseSkill() && executer.getEntityState().canBasicAttack();
   }

   public boolean resourcePredicate(PlayerPatch<?> playerpatch) {
      return true;
   }

	public WeaponInnateSkill registerPropertiesToAnimation() {
		this.comboAnimation.clear();
		this.comboAnimation.put(OCCAnimations.YAMATO_AUTO1, (AttackAnimation)OCCAnimations.YAMATO_P1);
		this.comboAnimation.put(OCCAnimations.YAMATO_AUTO2, (AttackAnimation)OCCAnimations.YAMATO_P2);
		this.comboAnimation.put(OCCAnimations.YAMATO_AUTO3, (AttackAnimation)OCCAnimations.YAMATO_P3);
        this.comboAnimation.put(OCCAnimations.JUDGEMENT_CUT, (AttackAnimation)OCCAnimations.JUDGEMENT_CUT_EXTEND);
        //this.comboAnimation.put(OCCAnimations.JUDGEMENT_CUT_TARGETED, (AttackAnimation)OCCAnimations.JUDGEMENT_CUT_EXTEND_TARGETED);
		
		this.comboAnimation.values().forEach((animation) -> {
			animation.phases[0].addProperties(((Map)this.properties.get(0)).entrySet());
		});
		
		return this;
	}

   public void updateContainer(SkillContainer container) {
      super.updateContainer(container);
      container.setResource(10.0F);
   }
}