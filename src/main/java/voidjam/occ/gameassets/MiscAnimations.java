package voidjam.occ.gameassets;

import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class MiscAnimations {
   public static StaticAnimation DEVIL_TRIGGER;

   public MiscAnimations() {
   }
   public static void registerAnimations(AnimationRegistryEvent event) {
      event.getRegistryMap().put("occ", MiscAnimations::build);
   }
   private static void build() {
      HumanoidArmature biped = Armatures.BIPED;
      YamatoAnimations.build();
      RebellionAnimations.build();
      DEVIL_TRIGGER = new ActionAnimation(0.15F, "biped/skill/devil_trigger", biped)
            .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
            ;
   }
   public static class ReuseableEvents {

   }
}
