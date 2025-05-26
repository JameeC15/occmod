package voidjam.occ.gameassets;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import voidjam.occ.api.BasicAttackNoRotAnimation;
import voidjam.occ.api.BasicMultipleAttackAnimation;
import voidjam.occ.api.SpecialAttackAnimation;
import voidjam.occ.api.YamatoAttackAnimation;
import voidjam.occ.client.particle.effekseer.OCCEffekseerLoader;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
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

import java.util.List;
import java.util.Set;
//import voidjam.occ.skills.weaponpassive.YamatoPassive;

public class MiscAnimations {
   public static StaticAnimation DEVIL_TRIGGER;

   public MiscAnimations() {
   }
   public static void registerAnimations(AnimationRegistryEvent event) {
      event.getRegistryMap().put("occ", MiscAnimations::build);
   }
   private static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      DEVIL_TRIGGER = new ActionAnimation(0.15F, "biped/skill/devil_trigger", biped)
            .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
            ;
   }
   public static class ReuseableEvents {

   }
}
