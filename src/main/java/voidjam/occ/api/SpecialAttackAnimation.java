package voidjam.occ.api;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;

public class SpecialAttackAnimation extends BasicAttackNoAntiStunAnimation {
	public SpecialAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
	}

	public SpecialAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
	}

	public SpecialAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
		this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, antic, contact, recovery, Float.MAX_VALUE, hand, colliderJoint, collider));
	}

	public SpecialAttackAnimation(float convertTime, String path, Armature armature, boolean Coordsetter, AttackAnimation.Phase... phases) {
		super(convertTime, path, armature, phases);
	}

	public SpecialAttackAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
		super(convertTime, path, armature, phases);
	}

	public boolean isBasicAttackAnimation() {
		return false;
	}
}
