package voidjam.occ.api;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import javax.annotation.Nullable;

public class BasicAttackNoAntiStunAnimation extends BasicMultipleAttackAnimation {
    public BasicAttackNoAntiStunAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
    }

    public BasicAttackNoAntiStunAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
    }

    public BasicAttackNoAntiStunAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, antic, contact, recovery, Float.MAX_VALUE, hand, colliderJoint, collider));
    }

    public BasicAttackNoAntiStunAnimation(float convertTime, String path, Armature armature, boolean Coordsetter, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    public BasicAttackNoAntiStunAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public float applyAntiStunLock(Entity hitten, float anti_stunlock, EpicFightDamageSource source, AttackAnimation.Phase phase, String tag, float poseTime) {
        if (tag == null) {
            anti_stunlock = 1.0F;
            hitten.addTag("anti_stunlock:" + anti_stunlock + ":" + hitten.tickCount + ":" + this.getId() + "-" + ((float)hitten.tickCount / 20.0F - poseTime + this.phases[this.phases.length - 1].recovery));
            return anti_stunlock;
        } else {
            hitten.removeTag(tag);
            anti_stunlock = Float.valueOf(tag.split(":")[1]);
            int maxSavedAttack = 5;
            String replaceTag = "anti_stunlock:" + anti_stunlock + ":" + hitten.tickCount + ":" + this.getId() + "-" + ((float)hitten.tickCount / 20.0F - poseTime + this.phases[this.phases.length - 1].recovery);

            for(int i = 3; i < tag.split(":").length && i < maxSavedAttack; ++i) {
                replaceTag = replaceTag.concat(":" + tag.split(":")[i]);
            }

            hitten.addTag(replaceTag);
            return anti_stunlock;
        }
    }
}
