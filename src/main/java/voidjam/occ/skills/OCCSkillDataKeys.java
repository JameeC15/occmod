package voidjam.occ.skills;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import voidjam.occ.skills.identity.DevilTrigger;
import yesman.epicfight.skill.SkillDataKey;

public class OCCSkillDataKeys {
   public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), "occ");
   public static final RegistryObject<SkillDataKey<Integer>> DT_STACKS;
   public static final RegistryObject<SkillDataKey<Boolean>> ACTIVE;
   public OCCSkillDataKeys() {
   }

   static {
      ACTIVE = DATA_KEYS.register("active", () -> {
         return SkillDataKey.createBooleanKey(false, false, new Class[]{DevilTrigger.class});
      });
      DT_STACKS = DATA_KEYS.register("dt_stacks", () -> {
         return SkillDataKey.createIntKey(0, false, new Class[]{DevilTrigger.class});
      });
   }
}
