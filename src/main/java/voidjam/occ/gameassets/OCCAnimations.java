package voidjam.occ.gameassets;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class OCCAnimations {
   public static StaticAnimation YAMATO_IDLE;
   public static StaticAnimation YAMATO_WALK;
   public static StaticAnimation YAMATO_RUN;
   public static StaticAnimation YAMATO_JUMP;

   public static StaticAnimation YAMATO_DT_IDLE;
   public static StaticAnimation YAMATO_DT_WALK;
   public static StaticAnimation YAMATO_DT_RUN;
   public static StaticAnimation YAMATO_DT_JUMP;

   public static StaticAnimation YAMATO_GUARD;
   public static StaticAnimation YAMATO_GUARD_HIT;

   public static StaticAnimation YAMATO_AUTO1;
   public static StaticAnimation YAMATO_AUTO2;
   
   public static StaticAnimation NO;


   public static StaticAnimation DEVIL_TRIGGER;

   public OCCAnimations() {
   }
   public static void registerAnimations(AnimationRegistryEvent event) {
      event.getRegistryMap().put("occ", OCCAnimations::build);
   }
   private static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      DEVIL_TRIGGER = new ActionAnimation(0F, "biped/skill/devil_trigger", biped);
      
      YAMATO_DT_IDLE = new StaticAnimation(true, "biped/living/yamato/yamato_dt_idle", biped);
      YAMATO_DT_WALK = new MovementAnimation(true, "biped/living/yamato/yamato_dt_walk", biped);
      YAMATO_DT_RUN = new MovementAnimation(true, "biped/living/yamato/yamato_dt_run", biped);
      YAMATO_DT_JUMP = new StaticAnimation(0F, false, "biped/living/yamato/yamato_dt_jump", biped);

      YAMATO_IDLE = new StaticAnimation(true, "biped/living/yamato/yamato_idle", biped);
      YAMATO_WALK = new MovementAnimation(true, "biped/living/yamato/yamato_walk", biped);
      YAMATO_RUN = new MovementAnimation(true, "biped/living/yamato/yamato_run", biped);
      YAMATO_JUMP = new StaticAnimation(0F, false, "biped/living/yamato/yamato_jump", biped);
      
      
      YAMATO_AUTO1 = new AttackAnimation(0F, "biped/combat/yamato/yamato_auto1", biped,
      new AttackAnimation.Phase[]{
         (new AttackAnimation.Phase(0.0F, 0.0F, 0.15F, 0.3F, 0.3F, InteractionHand.MAIN_HAND, biped.toolL, null)), 
         (new AttackAnimation.Phase(0.3F, 0.1F, 0.3F, 0.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null))
      })
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         AnimationEvent.TimeStampedEvent.create(0.0F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      
      
      YAMATO_AUTO2 = new AttackAnimation(0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, (Collider)null, biped.toolR, "biped/combat/yamato/yamato_auto2", biped)
      .addProperty(ActionAnimationProperty.IS_DEATH_ANIMATION, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         AnimationEvent.TimeStampedEvent.create(0.0F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(0.3F, ReuseableEvents.YAMATO_OUT, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(1.7F, (entitypath, self, params) -> {
            entitypath.getOriginal().kill();
         }, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(1.6F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      
      NO = new AttackAnimation(0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, (Collider)null, biped.toolR, "biped/skill/yamato/yamato_final", biped)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE);
   }

   public static class ReuseableEvents {
      private static final AnimationEvent.AnimationEventConsumer YAMATO_OUT = (entitypatch, self, params) -> {
         ((LivingEntity)entitypatch.getOriginal()).getMainHandItem().getOrCreateTag().putInt("CustomModelData", 2);
      };
      private static final AnimationEvent.AnimationEventConsumer YAMATO_IN = (entitypatch, self, params) -> {
         ((LivingEntity)entitypatch.getOriginal()).getMainHandItem().getOrCreateTag().remove("CustomModelData");
         };
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANT_ONE = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.0F;
   }
}
