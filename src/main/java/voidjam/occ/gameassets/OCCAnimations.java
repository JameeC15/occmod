package voidjam.occ.gameassets;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.jfr.stats.TimeStamped;

import java.util.function.Consumer;

import com.ibm.icu.impl.UResource.Value;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
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
import yesman.epicfight.api.animation.types.AirSlashAnimation;
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
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.EpicFightDamageTypes;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent.Causal;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent.Damage;
import voidjam.occ.api.BasicAttackNoRotAnimation;
import voidjam.occ.api.BasicMultipleAttackAnimation;
import voidjam.occ.api.SpecialAttackAnimation;
import voidjam.occ.particle.OCCParticles;
//import voidjam.occ.skills.weaponpassive.YamatoPassive;
import voidjam.occ.world.items.YamatoItem;

public class OCCAnimations {
   public static StaticAnimation YAMATO_IDLE;
   public static StaticAnimation YAMATO_WALK;
   public static StaticAnimation YAMATO_RUN;
   public static StaticAnimation YAMATO_GUARD;
   public static StaticAnimation YAMATO_GUARD_HIT;
   public static StaticAnimation YAMATO_AUTO1;
   public static StaticAnimation YAMATO_AUTO2;
   public static StaticAnimation YAMATO_AUTO3;
   public static StaticAnimation YAMATO_AUTO4;
   public static StaticAnimation YAMATO_STRIKE1;
   public static StaticAnimation YAMATO_STRIKE2;
   public static StaticAnimation YAMATO_CUT;
   public static StaticAnimation YAMATO_P0;
   public static StaticAnimation YAMATO_P0_1;
   public static StaticAnimation YAMATO_P1;
   public static StaticAnimation YAMATO_P2;
   public static StaticAnimation YAMATO_P3;
   public static StaticAnimation YAMATO_P3_REPEAT;
   public static StaticAnimation YAMATO_P3_REPEAT_2;
   public static StaticAnimation YAMATO_P3_REPEAT_3;
   public static StaticAnimation YAMATO_P3_REPEAT_4;
   public static StaticAnimation YAMATO_P3_FINISH;
   public static StaticAnimation UPPER_SLASH;
   public static StaticAnimation YAMATO_DASH;
   public static StaticAnimation YAMATO_POWER_DASH;
   public static StaticAnimation YAMATO_AIR_SLASH;
   public static StaticAnimation YAMATO_AERIAL_CLEAVE;
   public static StaticAnimation YAMATO_COUNTER_1;
   public static StaticAnimation YAMATO_COUNTER_2;
   public static StaticAnimation JUDGEMENT_CUT;
   public static StaticAnimation JUDGEMENT_CUT_TARGETED;
   public static StaticAnimation JUDGEMENT_CUT_EXTEND;
   public static StaticAnimation JUDGEMENT_CUT_EXTEND_TARGETED;
   public static StaticAnimation JUDGEMENT_CUT_A;
   public static StaticAnimation JUDGEMENT_CUT_A_TARGETED;
   public static StaticAnimation JUDGEMENT_CUT_A_EXTEND;
   public static StaticAnimation JUDGEMENT_CUT_A_EXTEND_TARGETED;
   public static StaticAnimation JUDGEMENT_CUT_END;
   public static StaticAnimation DEVIL_TRIGGER;

   
   public OCCAnimations() {
   }
   public static void registerAnimations(AnimationRegistryEvent event) {
      event.getRegistryMap().put("occ", OCCAnimations::build);
   }
   private static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      DEVIL_TRIGGER = new ActionAnimation(0.15F, "biped/skill/devil_trigger", biped)
            .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
            ;
      UPPER_SLASH = new BasicMultipleAttackAnimation(0.05F, 0.6F, 1.63F, 1.75F, OCCColiders.RISING_STAR, biped.rootJoint, "biped/combat/yamato_rising_star", biped)
				.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
				.addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
				.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.3F, 1.9F))
                .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_FAST)
				//.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
            .addEvents(
            TimeStampedEvent.create(0.6F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
            TimeStampedEvent.create(0.6F, ReuseableEvents.KATANA_OUT, Side.SERVER),
            TimeStampedEvent.create(1.93F, ReuseableEvents.KATANA_IN, Side.SERVER),
            TimeStampedEvent.create(1.93F, ReuseableEvents.YAMATO_IN, Side.SERVER)
            );;
      YAMATO_IDLE = new StaticAnimation(true, "biped/living/yamato_idle", biped);
      YAMATO_WALK = new MovementAnimation(true, "biped/living/yamato_walk", biped);
      YAMATO_RUN = new MovementAnimation(true, "biped/living/yamato_run", biped);
      //YAMATO_GUARD = new StaticAnimation(true, "biped/yamato/yamato_guard", biped);
      //YAMATO_GUARD_HIT = new GuardAnimation(0.05F, "biped/yamato/yamato_guard_hit", biped);
      //YAMATO_ACTIVE_GUARD_HIT = (new BasicMultipleAttackAnimation(0.02F, 0.2F, 0.19F, "biped/yamato/yamato_guard_parry", biped)).addProperty(StaticAnimationProperty.PLAY_SPEED, 1.2F);
      //YAMATO_ACTIVE_GUARD_HIT2 = (new BasicMultipleAttackAnimation(0.02F, 0.2F, 0.19F, "biped/yamato/yamato_guard_parry2", biped)).addProperty(StaticAnimationProperty.PLAY_SPEED, 1.2F);
      YAMATO_AUTO1 = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_auto1", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.27F, 0.271F, 0.33F, 0.56F, 0.56F, InteractionHand.MAIN_HAND, biped.toolL, OCCColiders.YAMATO_SHEATH))
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
         .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
         .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT), 
         (new AttackAnimation.Phase(0.56F, 0.6F, 0.651F, 0.681F, 0.931F, InteractionHand.MAIN_HAND, biped.toolL, OCCColiders.YAMATO_SHEATH))
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
         .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
         .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)}))
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
         .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
         .addEvents(
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(1.31F, ReuseableEvents.COMBO_BREAK, Side.SERVER)
            );
      YAMATO_AUTO2 = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_auto2", biped, 
      new AttackAnimation.Phase[]{
         new AttackAnimation.Phase(0.0F, 0.3F, 0.37F, 0.53F, 0.53F, biped.toolR, (Collider)null), 
         (new AttackAnimation.Phase(0.53F, 0.6F, 0.67F, 0.7F, Float.MAX_VALUE, biped.toolR, (Collider)null))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)}))
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
         .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
         .addEvents(
            TimeStampedEvent.create(Float.MAX_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER),
            TimeStampedEvent.create(0.25F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
            TimeStampedEvent.create(0.3F, ReuseableEvents.KATANA_OUT, Side.SERVER), 
            TimeStampedEvent.create(1.75F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(1.8F, ReuseableEvents.KATANA_IN, Side.SERVER), 
            TimeStampedEvent.create(1.21F, ReuseableEvents.COMBO_BREAK, Side.SERVER)
            );
      YAMATO_AUTO3 = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_auto3", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.7F, 0.78F, 0.88F, 0.88F, biped.toolR, (Collider)null))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD), 
         (new AttackAnimation.Phase(0.88F, 1.12F, 1.23F, 1.24F, Float.MAX_VALUE, biped.toolR, (Collider)null))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(1.5F))}))
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
         .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
         .addEvents(
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
            TimeStampedEvent.create(2.11F, ReuseableEvents.KATANA_IN, Side.SERVER),  
            TimeStampedEvent.create(1.76F, ReuseableEvents.COMBO_BREAK, Side.SERVER)
            );
      YAMATO_AUTO4 = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_auto4", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.81F, 0.9F, 2.87F, 2.87F, biped.toolR, OCCColiders.YAMATO_P))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
         .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(15.0F))}))
         .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
         .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
         .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
         .addEvents(
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
            TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.COMBO_BREAK, Side.SERVER), 
            TimeStampedEvent.create(2.5F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
            TimeStampedEvent.create(2.55F, ReuseableEvents.STAMINA_RECOVERY_15, Side.SERVER), 
            TimeStampedEvent.create(2.55F, ReuseableEvents.KATANA_IN, Side.SERVER)
            );
      JUDGEMENT_CUT_A = new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.08F, 1.14F, OCCColiders.YAMATO_DASH, biped.rootJoint, "biped/skill/yamato_jc_air", biped)
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		.addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.14F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(1.0F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
         TimeStampedEvent.create(1.08F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(1.14F, ReuseableEvents.YAMATO_IN, Side.SERVER));
      JUDGEMENT_CUT_A_TARGETED = new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.08F, 1.14F, OCCColiders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato_jc_air_targeted", biped)
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
		.addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.8F))
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANT_ONE)
      .addEvents(
         TimeStampedEvent.create(1F, ReuseableEvents.YAMATO_OUT, Side.SERVER),
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(1.08F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(1.14F, ReuseableEvents.YAMATO_IN, Side.SERVER));
      JUDGEMENT_CUT = new BasicMultipleAttackAnimation(0.05F, 0.86F, 0.96F, 0.97F, OCCColiders.YAMATO_DASH, biped.rootJoint, "biped/skill/yamato_jc_ground", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_SLOW)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.85F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.86F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(0.96F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(0.96F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER));
      JUDGEMENT_CUT_TARGETED = new BasicMultipleAttackAnimation(0.05F, 0.86F, 0.96F, 0.97F, OCCColiders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato_jc_ground_targeted", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_SLOW)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.85F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.86F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(0.96F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(0.96F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER),
         TimeStampedEvent.create(0.86F, (entitypatch, animation, params) -> {
            if (entitypatch.getTarget() != null) {
               double x = entitypatch.getTarget().getX();
               double y = entitypatch.getTarget().getY();
               double z = entitypatch.getTarget().getZ();
               entitypatch.getOriginal().level().addAlwaysVisibleParticle(OCCParticles.JUDGEMENT_CUT.get(), x, y, z, 0, 0, 0);
            
            }
         }, Side.CLIENT),
         TimeStampedEvent.create(0.86F, (entitypatch, animation, params) -> {
            if (entitypatch.getTarget() != null) {
               AABB box = AABB.ofSize(((LivingEntity)entitypatch.getTarget()).getPosition(1.0F), 3.0, 3.0, 3.0);
               List<Entity> list = ((LivingEntity)entitypatch.getOriginal()).level().getEntities(entitypatch.getOriginal(), box);
               for (Entity entity : list) {
                  if (entity instanceof LivingEntity) {
                     LivingEntity livingEntity = (LivingEntity) entity;
                     EpicFightDamageSource epicFightDamageSource = entitypatch.getDamageSource(OCCAnimations.JUDGEMENT_CUT, InteractionHand.MAIN_HAND);
                     epicFightDamageSource.setImpact(2.0F);
                     epicFightDamageSource.setArmorNegation(0.3F);
                     epicFightDamageSource.setStunType(StunType.LONG);

                     epicFightDamageSource.addRuntimeTag(EpicFightDamageType.WEAPON_INNATE);
                     livingEntity.hurt(epicFightDamageSource, 12F);
                  }
               }
            }
         }, Side.SERVER));
      JUDGEMENT_CUT_EXTEND = new BasicMultipleAttackAnimation(0.05F, 0.13F, 0.3F, 0.3F, OCCColiders.NO_HITBOX, biped.rootJoint, "biped/skill/yamato_jc_ground_2", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION_SLOW)
      .addEvents(
         TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
         TimeStampedEvent.create(0.13F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.3F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(0.3F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(0.3F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER));
      JUDGEMENT_CUT_END = new AttackAnimation(0.05F, 0.0F, 0.6F, 2.9F, 5.4F, OCCColiders.NO_HITBOX, biped.rootJoint, "biped/skill/judgement_cut_end", biped)
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
      .addProperty(AttackPhaseProperty.SWING_SOUND, OCCSounds.S_DIM_START.get())
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
      .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_LOC_TARGET)
      .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
      .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
      //.addProperty(ActionAnimationProperty.MOVE_TIME, TimePairList.create(0.0F, 5.5F))
      //.addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReuseableEvents.CONSTANTATION)
      .addEvents(
         StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((entitypatch, animation, params) -> {
            AAALevel.addParticle(entitypatch.getOriginal().level(), true, YamatoItem.JCE.clone());
            System.out.println("Particle");
         }, Side.CLIENT))
      .addEvents(
      TimeStampedEvent.create(0F, ReuseableEvents.YAMATO_IN, Side.SERVER),
      TimeStampedEvent.create(2.4F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
      TimeStampedEvent.create(5.4F, ReuseableEvents.YAMATO_IN, Side.SERVER),
      TimeStampedEvent.create(5.4F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
      TimeStampedEvent.create(5.4F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER))
      .addState(EntityState.MOVEMENT_LOCKED, true);;
      YAMATO_P1 = (new SpecialAttackAnimation(0.05F, 0.42F, 0.43F, 0.53F, 3.83F, OCCColiders.YAMATO_P, biped.toolR, "biped/combat/yamato_power1", biped))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
      .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
      .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1.0F))
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addEvents(
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.COMBO_BREAK, Side.SERVER), 
         TimeStampedEvent.create(0.53F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.43F, ReuseableEvents.KATANA_OUT, Side.SERVER), 
         TimeStampedEvent.create(2.89F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(2.93F, ReuseableEvents.STAMINA_RECOVERY_30, Side.SERVER), 
         TimeStampedEvent.create(2.93F, ReuseableEvents.KATANA_IN, Side.SERVER));
      YAMATO_P2 = (new SpecialAttackAnimation(0.05F, "biped/combat/yamato_power2", biped, 
      new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.62F, 0.68F, 1.05F, 1.05F, biped.toolR, OCCColiders.YAMATO_P))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.5F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)), 
      (new AttackAnimation.Phase(1.05F, 1.28F, 1.55F, 4.91F, 4.91F, biped.toolR, OCCColiders.YAMATO_P))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
      .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
      .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))}))
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addEvents(
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.COMBO_BREAK, Side.SERVER), 
         TimeStampedEvent.create(4.4F, ReuseableEvents.YAMATO_IN, Side.SERVER), 
         TimeStampedEvent.create(4.5F, ReuseableEvents.STAMINA_RECOVERY_15, Side.SERVER), 
         TimeStampedEvent.create(4.45F, ReuseableEvents.KATANA_IN, Side.SERVER));
      YAMATO_P3 = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_power3", biped, 
      new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.67F, 0.73F, 0.88F, 0.88F, biped.rootJoint, OCCColiders.YAMATO_DASH))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F))
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(5.0F)), 
      (new AttackAnimation.Phase(0.88F, 0.94F, 1.0F, 1.28F, 1.3F, biped.rootJoint, OCCColiders.YAMATO_DASH))
      .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
      .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
      .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F))
      .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(5.0F))}))
      
      .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
      .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.35F)
      .addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
      .addEvents(
         TimeStampedEvent.create(1.25F, (entitypatch, self, params) -> {
            entitypatch.playAnimationSynchronized(YAMATO_P3_FINISH.get(), 0.05F);
            System.out.println("Slashing");
         }, AnimationEvent.Side.SERVER),

         TimeStampedEvent.create(0.65F, (entitypatch, self, params) -> {
            AAALevel.addParticle(entitypatch.getOriginal().level(), true ,YamatoItem.SLASHES.clone().bindOnEntity(entitypatch.getOriginal()));
            System.out.println("Particle");
         }, Side.CLIENT),
         TimeStampedEvent.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(0.46F, ReuseableEvents.KATANA_IN_WEAK, Side.SERVER), 
         TimeStampedEvent.create(0.65F, ReuseableEvents.YAMATO_OUT, Side.SERVER), 
         TimeStampedEvent.create(1.0F, ReuseableEvents.YAMATO_IN, Side.SERVER));
      YAMATO_P3_FINISH = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_power3_finish", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.05F, 0.13F, 0.43F, 0.43F, biped.rootJoint, OCCColiders.YAMATO_DASH))
         .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
         .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
         , 
         (new AttackAnimation.Phase(0.43F, 0.82F, 0.9F, 1.35F, 1.35F, biped.rootJoint, OCCColiders.YAMATO_DASH_FINISH))
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
      YAMATO_POWER_DASH = (new SpecialAttackAnimation(0.05F, "biped/skill/yamato_rapid_slash", biped, 
      new AttackAnimation.Phase[]{(
         new AttackAnimation.Phase(0.0F, 0.38F, 0.9F, 1.17F, 1.17F, biped.rootJoint, OCCColiders.YAMATO_DASH))
         .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
         .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
         .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
         .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F)),
         //.addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG), 
         (new AttackAnimation.Phase(1.17F, 1.6F, 1.67F, 2.26F, 2.26F, biped.rootJoint, OCCColiders.YAMATO_DASH_FINISH))
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
      /*YAMATO_DASH = (new BasicAttackWinAnimation(0.01F, 0.0F, 1.41F, 0.75F, 1.18F, 0.27F, 1.41F, 0.0F, 0.0F, "biped/yamato/yamato_auto1", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.27F, 0.33F, 0.56F, 0.56F, "Tool_L", YamatoColliders.YAMATO_SHEATH)).addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5F)).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT), (new AttackAnimation.Phase(0.56F, 0.65F, 0.68F, 1.3F, Float.MAX_VALUE, "Tool_L", YamatoColliders.YAMATO_SHEATH)).addProperty(AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5F)).addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)})).addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F).addProperty(AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F).addProperty(StaticAnimationProperty.EVENTS, new StaticAnimation.Event[]{Event.create(Float.MIN_VALUE, ReuseableEvents.YAMATO_IN, Side.SERVER), Event.create(Float.MIN_VALUE, (entitypatch) -> {
         if (entitypatch instanceof PlayerPatch) {
            ((PlayerPatch)entitypatch).getSkill(SkillCategories.BASIC_ATTACK).getDataManager().setData(BasicWinAttack.COMBO_COUNTER, 1);
         }

      }, Side.SERVER), Event.create(1.45F, ReuseableEvents.COMBO_BREAK, Side.SERVER)});
      */YAMATO_AIR_SLASH = (new BasicMultipleAttackAnimation(0.05F, "biped/combat/yamato_aerial_a", biped,
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

      YAMATO_AERIAL_CLEAVE = new BasicAttackNoRotAnimation(0.05F, 0.6F, 1.2F, 1.2F, OCCColiders.AERIAL_CLEAVE, biped.rootJoint, "biped/skill/yamato_aerial_cleave", biped)
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

               Vec3f direction = new Vec3f(0.0F, -16F, 0.0F);
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
      private static final AnimationEvent.AnimationEventConsumer CLASHES = (entitypatch, self, params) -> {
         entitypatch.playSound(OCCSounds.S_DIM.get(), 0, 0);
      };
      private static final AnimationEvent.AnimationEventConsumer BLAST = (entitypatch, self, params) -> {
         entitypatch.playSound(EpicFightSounds.LASER_BLAST.get(), 0, 0);
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
         if (entitypatch instanceof PlayerPatch) {
            //((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setData(YamatoPassive.COMBO, 0);
         }

      };
      private static final AnimationEvent.AnimationEventConsumer SLASHES_G = (entitypatch, self, params) -> {
         Entity entity = entitypatch.getOriginal();
         RandomSource random = entitypatch.getOriginal().getRandom();
            double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 5.0D;
            double y = entity.getY();
            double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 5.0D;
            entity.playSound(EpicFightSounds.CLASH.get(), 0.0F, 0.0F);
      ((LivingEntity)entitypatch.getOriginal()).level().addAlwaysVisibleParticle((ParticleOptions)OCCParticles.SLASH_DIMENSION.get(), x, y + 1, z, 0, 0.0, 0.0);
      };
      private static final AnimationEvent.AnimationEventConsumer SLASHES_A = (entitypatch, self, params) -> {
         Entity entity = entitypatch.getOriginal();
         RandomSource random = entitypatch.getOriginal().getRandom();
            double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 5.0D;
            double y = entity.getY();
            double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 5.0D;
            entity.playSound(EpicFightSounds.CLASH.get(), 0.0F, 0.0F);
      ((LivingEntity)entitypatch.getOriginal()).level().addAlwaysVisibleParticle((ParticleOptions)OCCParticles.SLASH_DIMENSION.get(), x, y - 0.5, z, 0, 0.0, 0.0);
      };
      private static final AnimationEvent.AnimationEventConsumer STAMINA_RECOVERY_15 = (entitypatch, self, params) -> {
         if (entitypatch instanceof PlayerPatch) {
            float stamina = ((PlayerPatch)entitypatch).getStamina();
            float maxstamina = ((PlayerPatch)entitypatch).getMaxStamina();
            if (stamina < maxstamina) {
               ((PlayerPatch)entitypatch).setStamina(stamina + 0.15F * maxstamina);
            }
         }

      };
      private static final AnimationEvent.AnimationEventConsumer STAMINA_RECOVERY_30 = (entitypatch, self, params) -> {
         if (entitypatch instanceof PlayerPatch) {
            float stamina = ((PlayerPatch)entitypatch).getStamina();
            float maxstamina = ((PlayerPatch)entitypatch).getMaxStamina();
            if (stamina < maxstamina) {
               ((PlayerPatch)entitypatch).setStamina(stamina + 0.3F * maxstamina);
            }
         }

      };
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANTATION = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.35F;
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANTATION_FAST = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.75F;
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANTATION_SLOW = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 0.85F;
      public static final AnimationProperty.PlaybackSpeedModifier CONSTANT_ONE = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.0F;
   }

}
