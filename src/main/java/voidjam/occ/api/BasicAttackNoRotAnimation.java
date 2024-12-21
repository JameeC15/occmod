package voidjam.occ.api;

import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BasicAttackNoRotAnimation extends BasicMultipleAttackAnimation {
   public BasicAttackNoRotAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
      this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
   }

   public BasicAttackNoRotAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
      this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
   }

   public BasicAttackNoRotAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
      this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, antic, contact, recovery, Float.MAX_VALUE, hand, colliderJoint, collider));
   }

   public BasicAttackNoRotAnimation(float convertTime, String path, Armature armature, boolean Coordsetter, AttackAnimation.Phase... phases) {
      super(convertTime, path, armature, phases);
   }

   public BasicAttackNoRotAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
      super(convertTime, path, armature, phases);
   }

   public boolean isBasicAttackAnimation() {
      return true;
   }

   public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
      Pose pose = this.getRawPose(time);
      this.modifyPose(this, pose, entitypatch, time, partialTicks);
      return pose;
   }
}
