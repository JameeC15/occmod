package voidjam.occ.gameassets;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OCCSounds {
public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "occ");
   public static final RegistryObject<SoundEvent> SWORD_IN = registerSound("sfx.sword_in");
   public static final RegistryObject<SoundEvent> JUDGEMENT_CUT = registerSound("sfx.jc");
   public static final RegistryObject<SoundEvent> S_DIM_START = registerSound("sfx.slashjc");
   public static final RegistryObject<SoundEvent> S_DIM = registerSound("sfx.slashd");

   private static RegistryObject<SoundEvent> registerSound(String name) {
      ResourceLocation res = new ResourceLocation("occ", name);
      return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
   }
}
