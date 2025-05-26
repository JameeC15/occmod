package voidjam.occ.gameassets;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import voidjam.occ.api.YamatoAttackAnimation;
import voidjam.occ.client.particle.effekseer.OCCEffekseerLoader;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;

public class RebellionAnimations {
   //REBELLION
   public static StaticAnimation REBELLION_IDLE;

   public static StaticAnimation REBELLION_AUTO1;
   public static StaticAnimation REBELLION_AUTO2;
   public static StaticAnimation REBELLION_AUTO3;

   public RebellionAnimations() {
   }

   public static void registerAnimations(AnimationRegistryEvent event) {
      event.getRegistryMap().put("occ", RebellionAnimations::build);
   }
   private static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      REBELLION_IDLE = new StaticAnimation(true, "biped/living/rebellion/rebellion_idle", biped);
      REBELLION_AUTO1 = (new YamatoAttackAnimation(0.15F, 0.0F, 1.2F, 0.37F, 0.42F, 0.3F, 0.65F, 0.0F, 0.0F, "biped/combat/rebellion/rebellion_auto1", biped,
              new AttackAnimation.Phase(0.0F, 0.0F, 0.125F, 0.3F, 0.37F, 0.42F, InteractionHand.MAIN_HAND, biped.toolR, ColliderPreset.LONGSWORD)))
              .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
              .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
              .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
              .addState(EntityState.MOVEMENT_LOCKED, true);

      REBELLION_AUTO2 = (new YamatoAttackAnimation(0.15F, 0.0F, 1.2F, 0.42F, 0.47F, 0.4F, 0.65F, 0.0F, 0.0F, "biped/combat/rebellion/rebellion_auto2", biped,
              new AttackAnimation.Phase(0.0F, 0.0F, 0.2F, 0.32F, 0.38F, 0.42F, InteractionHand.MAIN_HAND, biped.toolR, ColliderPreset.LONGSWORD)))
              .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
              .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
              .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
              .addState(EntityState.MOVEMENT_LOCKED, true);

      REBELLION_AUTO3 = (new YamatoAttackAnimation(0.15F, 0.0F, 1.5F, Float.MAX_VALUE, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, "biped/combat/rebellion/rebellion_auto3", biped,
              new AttackAnimation.Phase(0.0F, 0.0F, 0.3F, 0.42F, Float.MAX_VALUE, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, ColliderPreset.LONGSWORD)))
              .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
              .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
              .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
              .addState(EntityState.MOVEMENT_LOCKED, true);
   }


   public static class ReuseableEvents {
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANT_ONE = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.0F;
   }

}
