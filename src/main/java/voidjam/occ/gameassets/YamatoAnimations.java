package voidjam.occ.gameassets;

import java.util.List;
import java.util.Set;

import net.minecraft.core.BlockPos;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.GuardAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import voidjam.occ.api.BasicAttackNoRotAnimation;
import voidjam.occ.api.BasicMultipleAttackAnimation;
import voidjam.occ.api.SpecialAttackAnimation;
import voidjam.occ.api.YamatoAttackAnimation;
import voidjam.occ.client.particle.effekseer.OCCEffekseerLoader;

public class YamatoAnimations {
   public static StaticAnimation YAMATO_IDLE;
   public static StaticAnimation YAMATO_WALK;
   public static StaticAnimation YAMATO_RUN;

   public static StaticAnimation YAMATO_GUARD;
   public static StaticAnimation YAMATO_GUARD_HIT;

   public static StaticAnimation YAMATO_AUTO1;
   public static StaticAnimation YAMATO_AUTO2;
   public static StaticAnimation YAMATO_AUTO3;
   public static StaticAnimation YAMATO_AUTO4;

   public static StaticAnimation YAMATO_COMBO_B;
   public static StaticAnimation YAMATO_COMBO_C;
   public static StaticAnimation YAMATO_COMBO_C_END;

   public static StaticAnimation UPPER_SLASH;
   public static StaticAnimation YAMATO_POWER_DASH;
   public static StaticAnimation YAMATO_AIR_SLASH;
   public static StaticAnimation YAMATO_AERIAL_CLEAVE;
   public static StaticAnimation JUDGEMENT_CUT;
   public static StaticAnimation JUDGEMENT_CUT_TARGETED;

   public static StaticAnimation JUDGEMENT_CUT_A;
   public static StaticAnimation JUDGEMENT_CUT_A_TARGETED;

   public static StaticAnimation JUDGEMENT_CUT_END;

   public YamatoAnimations() {
   }

   public static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      UPPER_SLASH = new BasicMultipleAttackAnimation(0.05F, 0.6F, 0.835F, 1.75F, OCCColliders.RISING_STAR, biped.rootJoint, "biped/combat/yamato/yamato_upper_slash", biped)
				.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
				.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
				.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.3F, 1.9F))
                .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_FAST)
            .addEvents(
            TimeStampedEvent.create(0.6F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
            TimeStampedEvent.create(0.6F, ReuseableEvents.KATANA_OUT, Side.SERVER),
            TimeStampedEvent.create(1.1F, ReuseableEvents.KATANA_IN, Side.SERVER),
            TimeStampedEvent.create(1.1F, ReuseableEvents.YAMATO_IN, Side.SERVER)
            );
      YAMATO_IDLE = new StaticAnimation(true, "biped/living/yamato/yamato_idle", biped);
      YAMATO_WALK = new MovementAnimation(true, "biped/living/yamato/yamato_walk", biped);
      YAMATO_RUN = new MovementAnimation(true, "biped/living/yamato/yamato_run", biped);
      YAMATO_GUARD = new StaticAnimation(0.075f, true, "biped/skill/yamato/guard_yamato", biped);
      YAMATO_GUARD_HIT = new GuardAnimation(0.15F, "biped/skill/yamato/guard_yamato_hit", biped);

      /////////////////
      //
      //
      //
      // Attacks
      //
      //
      /////////////////

      YAMATO_AUTO1 = (new YamatoAttackAnimation(0.05F, 0.0F, 1.41F, 0.75F, 1.3F, 0.27F, 1.41F, 0.0F, 0.0F, "biped/combat/yamato/yamato_auto1", biped,
      new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.25F, 0.45F, 0.45F, InteractionHand.MAIN_HAND, biped.toolL, OCCColliders.YAMATO_SHEATH)
         .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
         .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
         .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)))
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         AnimationEvent.TimeStampedEvent.create(0.95F, ReuseableEvents.COMBO_BREAK, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(0.0F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      YAMATO_AUTO2 = (new YamatoAttackAnimation(0.05F, 0.0F, 2.1F, 0.7F, 1.2F, 0.3F, 2.1F, 0.0F, 0.0F, "biped/combat/yamato/yamato_auto2", biped,
      new AttackAnimation.Phase(0.0F, 0.05F, 0.17F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, biped.toolL, OCCColliders.YAMATO_SHEATH)
         .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
         .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
         .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)))
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         AnimationEvent.TimeStampedEvent.create(0.95F, ReuseableEvents.COMBO_BREAK, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(0.0F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      YAMATO_AUTO3 = new YamatoAttackAnimation(0.05F, 0.0F, 2.65F, 1.3F, 1.75F, 0.7F, 2.65F, 0.0F, 0.0F, "biped/combat/yamato/yamato_auto3", biped,
         new AttackAnimation.Phase[]{(
            new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.35F, biped.toolR, (Collider)null)
            .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())), 
            (new AttackAnimation.Phase(0.35F, 0.35F, 0.45F, 0.65F, 0.65F, biped.toolR, (Collider)null))
            .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
            .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)})
      .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
      .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.75F)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(new AnimationEvent.TimeStampedEvent[]{
         TimeStampedEvent.create(0.25F, ReuseableEvents.YAMATO_OUT, AnimationEvent.Side.SERVER),
         TimeStampedEvent.create(2.35F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER),
         TimeStampedEvent.create(2.2F, ReuseableEvents.COMBO_BREAK, AnimationEvent.Side.SERVER),
         TimeStampedEvent.create(2.2F, ReuseableEvents.KATANA_IN, Side.SERVER)})
         .addState(EntityState.MOVEMENT_LOCKED, true);
      YAMATO_AUTO4 = new YamatoAttackAnimation(0.1F, 0.0F, 2.87F, 2.87F, 2.87F, 0.81F, 2.87F, 0.81F, 2.87F, "biped/combat/yamato/yamato_auto4", biped,
         (new AttackAnimation.Phase(0.0F, 0.45F, 0.50F, 0.60F, 2.7F, biped.toolR, OCCColliders.YAMATO_P))
      .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
      .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
      .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter( 5F))
      .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F)))
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         AnimationEvent.TimeStampedEvent.create(0.0F, ReuseableEvents.YAMATO_OUT, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(3F,ReuseableEvents.COMBO_BREAK, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(2.5F, ReuseableEvents.YAMATO_IN, AnimationEvent.Side.SERVER),
         AnimationEvent.TimeStampedEvent.create(2.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(OCCSounds.SWORD_IN.get()))
      .addState(EntityState.MOVEMENT_LOCKED, true);

      /////////////////
      //
      //
      //
      // Combo Finishers
      //
      //
      /////////////////
      YAMATO_COMBO_B = (new AttackAnimation(0.05F, 0.42F, 0.43F, 0.53F, 3.83F, OCCColliders.YAMATO_P, biped.toolR, "biped/combat/yamato/yamato_combo_b", biped))
      .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
      .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
      .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
      .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3F))
      .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2F))
      .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter( 15F))
      .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3))
      .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER))
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F).addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.COMBO_BREAK, Side.SERVER), 
         TimeStampedEvent.create(4.4F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(4.5F, ReuseableEvents.STAMINA_RECOVERY_15, Side.SERVER), 
         TimeStampedEvent.create(4.45F, ReuseableEvents.KATANA_IN, Side.SERVER));
      YAMATO_COMBO_C = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato/yamato_combo_c", biped, 
      new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.67F, 0.73F, 0.88F, 0.88F, biped.rootJoint, OCCColliders.YAMATO_DASH))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F))
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(5.0F)), 
      (new AttackAnimation.Phase(0.88F, 0.94F, 1.0F, 1.28F, 1.3F, biped.rootJoint, OCCColliders.YAMATO_DASH))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F))
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(5.0F))}))
      
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         TimeStampedEvent.create(1.25F, (entitypatch, self, params) -> {
            entitypatch.playAnimationSynchronized(YAMATO_COMBO_C_END.get(), 0.05F);
            System.out.println("Slashing");
         }, AnimationEvent.Side.SERVER),

         TimeStampedEvent.create(0.65F, (entitypatch, self, params) -> {
            AAALevel.addParticle(entitypatch.getOriginal().level(), true ,OCCEffekseerLoader.SLASHES.clone().bindOnEntity(entitypatch.getOriginal()).useEntityHeadSpace(true).position(0f,-1.7f,0f).rotation(0f,4.7f,0f));
            System.out.println("Particle");
         }, Side.CLIENT),
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.46F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER), 
         TimeStampedEvent.create(0.65F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(1.0F, ReuseableEvents.YAMATO_IN, Side.SERVER));
      YAMATO_COMBO_C_END = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato/yamato_combo_c_end", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.05F, 0.13F, 0.43F, 0.43F, biped.rootJoint, OCCColliders.YAMATO_DASH))
         .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
         .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
         , 
         (new AttackAnimation.Phase(0.43F, 0.82F, 0.9F, 1.35F, 1.35F, biped.rootJoint, OCCColliders.YAMATO_DASH_FINISH))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
         .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(35.0F))
         .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)}))
         .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
         .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
         .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
         .addEvents(
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.COMBO_BREAK, Side.SERVER), 
            TimeStampedEvent.create(0.133F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
            TimeStampedEvent.create(0.9F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(0.895F, ReuseableEvents.KATANA_IN, Side.SERVER));
      /////////////////
      //
      //
      //
      // Skills
      //
      //
      /////////////////
      JUDGEMENT_CUT_A = new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.08F, 1.14F, OCCColliders.JUDGEMENT_CUT, biped.rootJoint, "biped/skill/yamato/yamato_jc_air", biped)
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		.addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.14F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.07F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(0.069F, (entitypatch, animation, params) -> {
				Entity entity = entitypatch.getOriginal();
				entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
			}, Side.CLIENT))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      JUDGEMENT_CUT_A_TARGETED = new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.08F, 1.14F, OCCColliders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato/yamato_jc_air_targeted", biped)
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		.addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.8F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.07F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(0.15F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(0.1F, (entitypatch, animation, params) -> {
            if (entitypatch.getTarget() != null) {
               double x = entitypatch.getTarget().getX();
               double y = entitypatch.getTarget().getY() + 1;
               double z = entitypatch.getTarget().getZ();
               AAALevel.addParticle(entitypatch.getOriginal().level(),OCCEffekseerLoader.JUDGEMENT_CUT.clone().position(x,y,z));
            
            }
         }, Side.SERVER),
         TimeStampedEvent.create(0.069F, (entitypatch, animation, params) -> {
				Entity entity = entitypatch.getOriginal();
				entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
			}, Side.CLIENT),
         TimeStampedEvent.create(0.1F, ReuseableEvents.ENTITY_SCAN, Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      JUDGEMENT_CUT = new BasicMultipleAttackAnimation(0.05F, 0.13F, 0.3F, 0.3F, OCCColliders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato/yamato_jc", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_FAST)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.3F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(0.10F, (entitypatch, animation, params) -> {
				Entity entity = entitypatch.getOriginal();
				entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
			}, Side.CLIENT))
      .addState(EntityState.MOVEMENT_LOCKED, true);
      JUDGEMENT_CUT_TARGETED = new BasicMultipleAttackAnimation(0.05F, 0.13F, 0.3F, 0.3F, OCCColliders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato/yamato_jc_targeted", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_FAST)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.3F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(0.2F, (entitypatch, animation, params) -> {
            if (entitypatch.getTarget() != null) {
               double x = entitypatch.getTarget().getX();
               double y = entitypatch.getTarget().getY() + 1;
               double z = entitypatch.getTarget().getZ();
               AAALevel.addParticle(entitypatch.getOriginal().level(),OCCEffekseerLoader.JUDGEMENT_CUT.clone().position(x,y,z));
            }
         }, Side.SERVER),
         TimeStampedEvent.create(0.10F, (entitypatch, animation, params) -> {
				Entity entity = entitypatch.getOriginal();
				entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
			}, Side.CLIENT),
         TimeStampedEvent.create(0.2F, ReuseableEvents.ENTITY_SCAN, Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);

      JUDGEMENT_CUT_END = new AttackAnimation(0.05F, 2.4F, 5.4F, 5.6F, 5.9F, OCCColliders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato/judgement_cut_end", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_LOC_TARGET)
      .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
      .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((entitypatch, animation, params) -> {
            AAALevel.addParticle(entitypatch.getOriginal().level(), true, OCCEffekseerLoader.JCE.clone());
            System.out.println("Particle");
         }, Side.SERVER))
      .addEvents(
      TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
      TimeStampedEvent.create(2.4F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
      TimeStampedEvent.create(5.4F, ReuseableEvents.YAMATO_IN, Side.SERVER),
      TimeStampedEvent.create(5.4F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
      TimeStampedEvent.create(5.4F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),

      TimeStampedEvent.create(2.4F, ReuseableEvents.JCE_SCAN_INITIALIZE, Side.SERVER),
      TimeStampedEvent.create(5.4F, ReuseableEvents.JCE_SCAN_LAST, Side.SERVER),
      TimeStampedEvent.create(2F, (entitypatch, animation, params) -> {
         Entity entity = entitypatch.getOriginal();
         entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
      }, Side.CLIENT))
      .addState(EntityState.MOVEMENT_LOCKED, true);;

      YAMATO_POWER_DASH = (new SpecialAttackAnimation(0.05F, "biped/skill/yamato/yamato_rapid_slash", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.38F, 0.9F, 1.17F, 1.17F, biped.rootJoint, OCCColliders.YAMATO_DASH))
         .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F)),
         //.addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG), 
         (new AttackAnimation.Phase(1.17F, 1.6F, 1.67F, 2.26F, 2.26F, biped.rootJoint, OCCColliders.YAMATO_DASH_FINISH))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
         .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(35.0F))
         //.addProperty(AttackPhaseProperty.SWING_SOUND, YamatoSounds.SWORD_IN)
         .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)}))
         .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
         .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
         .addEvents(
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(0.7F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
            TimeStampedEvent.create(0.7F, ReuseableEvents.KATANA_OUT, Side.SERVER), 
            TimeStampedEvent.create(1.6F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(0.275F, (entitypatch, animation, params) -> {
               Entity entity = entitypatch.getOriginal();
               entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
            }, Side.CLIENT));

      YAMATO_AIR_SLASH = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato/yamato_aerial_a", biped,
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.00F, 0.4F, 0.55F, 0.6F, 0.6F, biped.toolR, null))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
         new AttackAnimation.Phase(0.6F, 0.5F, 0.65F, 0.7F, 0.7F, biped.toolR, null)
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
         new AttackAnimation.Phase(0.7F, 0.85F, 1.0F, 1.05F, 1.05F, biped.toolR, null)
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
      })
      .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
      .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
      .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.55F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         TimeStampedEvent.create(0.5F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
         TimeStampedEvent.create(0.5F, ReuseableEvents.KATANA_OUT, Side.SERVER),
         TimeStampedEvent.create(1.45F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(1.45F, ReuseableEvents.KATANA_IN, Side.SERVER)));

      YAMATO_AERIAL_CLEAVE = new BasicAttackNoRotAnimation(0.05F, 0.6F, 1.2F, 1.2F, OCCColliders.AERIAL_CLEAVE, biped.rootJoint, "biped/skill/yamato/yamato_aerial_cleave", biped)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
         .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
         .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
         .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
         .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
         .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 0.68F}))
         .newTimePair(0.0F, 0.9F)
         .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
            if (elapsedTime >= 0.68F && elapsedTime < 0.78F) {
               float dpx = (float)((LivingEntity)entitypatch.getOriginal()).getX();
               float dpy = (float)((LivingEntity)entitypatch.getOriginal()).getY();
               float dpz = (float)((LivingEntity)entitypatch.getOriginal()).getZ();
   
               for(BlockState block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos((double)dpx, (double)dpy, (double)dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = ((LivingEntity)entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos((double)dpx, (double)dpy, (double)dpz))) {
                  --dpy;
               }
               
               LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();

               Vec3f direction = new Vec3f(0.0F, -8F, 0.0F);
               OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)entitypatch.getOriginal()).yBodyRotO + 90.0F))), new Vec3f(0.0F, 1.0F, 0.0F));
               OpenMatrix4f.transform3v(rotation, direction, direction);
               float distanceToGround = (float)Math.max(Math.abs(((LivingEntity)entitypatch.getOriginal()).getY() - (double)dpy) - 1.0, 0.0);
               if (distanceToGround > 0.5F) {
                  //entitypatch.getAnimator().getPlayerFor(self).setElapsedTimeCurrent(0.76F);
                  livingentity.move(MoverType.SELF, direction.toDoubleVector());
               return 0.001F;
               } else {
                  return 1.0F * speed;
               }
            } else {
               return 1.0F * speed;
            }
      }).addEvents(new AnimationEvent.TimeStampedEvent[]{
         TimeStampedEvent.create(0.7F, (entitypatch, self, params) -> {
         ((LivingEntity)entitypatch.getOriginal()).resetFallDistance();
      }, Side.SERVER),
      TimeStampedEvent.create(0.7F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
      TimeStampedEvent.create(0.7F, ReuseableEvents.KATANA_OUT, Side.SERVER),
      TimeStampedEvent.create(1.15F, ReuseableEvents.YAMATO_IN, Side.SERVER),
      TimeStampedEvent.create(1.15F, ReuseableEvents.KATANA_IN, Side.SERVER)});
   }











   public static class ReuseableEvents {
      private static final AnimationEvent.AnimationEventConsumer KATANA_IN = (entitypatch, self, params) -> {
         entitypatch.playSound(OCCSounds.SWORD_IN.get(), 0.0F, 0.0F);
      };
      private static final AnimationEvent.AnimationEventConsumer KATANA_IN_WEAK = (entitypatch, self, params) -> {
         entitypatch.playSound(EpicFightSounds.SWORD_IN.get(), 0, 0);
      };
      private static final AnimationEvent.AnimationEventConsumer KATANA_OUT = (entitypatch, self, params) -> {
        entitypatch.playSound(EpicFightSounds.WHOOSH_SHARP.get(), 0.0F, 0.0F);
      };
      private static final AnimationEvent.AnimationEventConsumer YAMATO_OUT = (entitypatch, self, params) -> {
         ((LivingEntity)entitypatch.getOriginal()).getMainHandItem().getOrCreateTag().putInt("CustomModelData", 2);
      };
      private static final AnimationEvent.AnimationEventConsumer YAMATO_IN = (entitypatch, self, params) -> {
         ((LivingEntity)entitypatch.getOriginal()).getMainHandItem().getOrCreateTag().remove("CustomModelData");
         };
      private static final AnimationEvent.AnimationEventConsumer COMBO_BREAK = (entitypatch, self, params) -> {
         if (entitypatch instanceof ServerPlayerPatch) {
            //((PlayerPatch<?>) entitypatch).getSkill(SkillCategories.BASIC_ATTACK.universalOrdinal()).getDataManager().setData(SkillDataKeys.COMBO_COUNTER.get(), 0);
         }

      };

      private static final AnimationEvent.AnimationEventConsumer ENTITY_SCAN = (entitypatch, self, params) -> {
         if (entitypatch.getTarget() != null) {
            AABB box = AABB.ofSize(((LivingEntity)entitypatch.getTarget()).getPosition(1.0F), 3.0, 3.0, 3.0);
            List<Entity> list = ((LivingEntity)entitypatch.getOriginal()).level().getEntities(entitypatch.getOriginal(), box);
            for (Entity entity : list) {
               if (entity instanceof LivingEntity) {
                  LivingEntity livingEntity = (LivingEntity) entity;
                  EpicFightDamageSource epicFightDamageSource = entitypatch.getDamageSource(YamatoAnimations.JUDGEMENT_CUT, InteractionHand.MAIN_HAND);
                  epicFightDamageSource.setImpact(2.0F);
                  epicFightDamageSource.setArmorNegation(0.3F);
                  epicFightDamageSource.setStunType(StunType.LONG);

                  epicFightDamageSource.addRuntimeTag(EpicFightDamageType.WEAPON_INNATE);
                  livingEntity.hurt(epicFightDamageSource, 12F);
               }
            }
         }
      };

      private static final AnimationEvent.AnimationEventConsumer JCE_SCAN_INITIALIZE = (entitypatch, self, params) -> {
         AABB box = AABB.ofSize(entitypatch.getOriginal().getPosition(1.0F), 5.5, 2.0, 5.5);
         List<Entity> list = ((LivingEntity)entitypatch.getOriginal()).level().getEntities(entitypatch.getOriginal(), box);
         for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
               LivingEntity livingEntity = (LivingEntity) entity;
               livingEntity.setInvulnerable(true);
            }
         }
      };

      private static final AnimationEvent.AnimationEventConsumer JCE_SCAN_LAST = (entitypatch, self, params) -> {
         AABB box = AABB.ofSize(entitypatch.getOriginal().getPosition(1.0F), 5.5, 2.0, 5.5);
         List<Entity> list = ((LivingEntity)entitypatch.getOriginal()).level().getEntities(entitypatch.getOriginal(), box);
         for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
               LivingEntity livingEntity = (LivingEntity) entity;
               livingEntity.setInvulnerable(false);

               EpicFightDamageSource epicFightDamageSource = entitypatch.getDamageSource(YamatoAnimations.JUDGEMENT_CUT_END, InteractionHand.MAIN_HAND);
               epicFightDamageSource.setImpact(2.0F);
               epicFightDamageSource.setArmorNegation(0.3F);
               epicFightDamageSource.setStunType(StunType.NEUTRALIZE);
               epicFightDamageSource.addExtraDamage(ExtraDamageInstance.TARGET_LOST_HEALTH.create(0.25F));

               epicFightDamageSource.addRuntimeTag(EpicFightDamageType.WEAPON_INNATE);
               livingEntity.hurt(epicFightDamageSource, 12F);
            }
         }
      };

      private static final AnimationEvent.AnimationEventConsumer STAMINA_RECOVERY_15 = (entitypatch, self, params) -> {
         if (entitypatch instanceof ServerPlayerPatch) {
            float stamina = ((ServerPlayerPatch)entitypatch).getStamina();
            float maxstamina = ((ServerPlayerPatch)entitypatch).getMaxStamina();
            if (stamina < maxstamina) {
               ((ServerPlayerPatch)entitypatch).setStamina(stamina + 0.15F * maxstamina);
            }
         }

      };
      private static final AnimationEvent.AnimationEventConsumer STAMINA_RECOVERY_30 = (entitypatch, self, params) -> {
         if (entitypatch instanceof ServerPlayerPatch) {
            float stamina = ((ServerPlayerPatch)entitypatch).getStamina();
            float maxstamina = ((ServerPlayerPatch)entitypatch).getMaxStamina();
            if (stamina < maxstamina) {
               ((ServerPlayerPatch)entitypatch).setStamina(stamina + 0.3F * maxstamina);
            }
         }

      };
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANTATION = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.35F;
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANTATION_FAST = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.75F;
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANT_ONE = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.0F;
   }

}
