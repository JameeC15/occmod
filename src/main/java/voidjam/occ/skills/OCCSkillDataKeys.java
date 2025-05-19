package voidjam.occ.skills;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import voidjam.occ.skills.deviltrigger.DevilTrigger;
import voidjam.occ.skills.weaponinnate.DarkSlayer;
import yesman.epicfight.skill.SkillDataKey;

public class OCCSkillDataKeys {
   public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), "occ");
   public static final RegistryObject<SkillDataKey<Integer>> COOLDOWN;
   public static final RegistryObject<SkillDataKey<Integer>> DT_STACKS;
   public static final RegistryObject<SkillDataKey<Integer>> COMBO;
   //public static final RegistryObject<SkillDataKey<Integer>> STACKS;
   //public static final RegistryObject<SkillDataKey<Integer>> CHARGE;
   //public static final RegistryObject<SkillDataKey<Integer>> TIMER;
   public static final RegistryObject<SkillDataKey<Integer>> ATTACKS;
   public static final RegistryObject<SkillDataKey<Boolean>> ACTIVE;
   public OCCSkillDataKeys() {
   }

   static {
      ACTIVE = DATA_KEYS.register("active", () -> {
         return SkillDataKey.createBooleanKey(false, false, new Class[]{DarkSlayer.class, DevilTrigger.class});
      });
      ATTACKS = DATA_KEYS.register("attacks", () -> {
         return SkillDataKey.createIntKey(0, false, new Class[]{DarkSlayer.class});
      });
      COOLDOWN = DATA_KEYS.register("cooldown", () -> {
         return SkillDataKey.createIntKey(0, false, new Class[]{DarkSlayer.class});
      });
      DT_STACKS = DATA_KEYS.register("dt_stacks", () -> {
         return SkillDataKey.createIntKey(0, false, new Class[]{DevilTrigger.class});
      });
      //CHARGE = DATA_KEYS.register("charge", () -> {
      //   return SkillDataKey.createIntKey(0, false, new Class[]{DevilTrigger.class});
      //});
      //TIMER = DATA_KEYS.register("timer", () -> {
      //   return SkillDataKey.createIntKey(0, false, new Class[]{DevilTrigger.class});
      //});
      COMBO = DATA_KEYS.register("combo", () -> {
        return SkillDataKey.createIntKey(0, false, new Class[]{DarkSlayer.class});
     });
     	//STACKS = DATA_KEYS.register("stacks", () -> SkillDataKey.createIntKey(0, false, DevilTrigger.class));
   }
}
