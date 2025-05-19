package voidjam.occ.world.capabilities.item;

import voidjam.occ.gameassets.OCCAnimations;
import voidjam.occ.gameassets.OCCColliders;
import voidjam.occ.gameassets.OCCSkills;
import voidjam.occ.main.OCCMod;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;

@EventBusSubscriber(
   modid = "occ",
   bus = Bus.MOD
)
public class OCCWeaponCapabilitiesPresets {
    public static final Function<Item, CapabilityItem.Builder> DARK_SLAYER = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability
		.builder()
		.category(OCCWeaponCategories.YAMATO)
		.styleProvider((entitypatch) -> Styles.SHEATH )
		 .collider(OCCColliders.YAMATO)
		 .swingSound(EpicFightSounds.WHOOSH_BIG.get())
		 .canBePlacedOffhand(true)
		 .hitSound(EpicFightSounds.BLUNT_HIT.get())
		 .hitParticle((HitParticleType)EpicFightParticles.HIT_BLADE.get())
		 .newStyleCombo(Styles.SHEATH, new StaticAnimation[]{
			OCCAnimations.YAMATO_AUTO1, OCCAnimations.YAMATO_AUTO2, OCCAnimations.YAMATO_AUTO3, OCCAnimations.YAMATO_AUTO4,
			OCCAnimations.UPPER_SLASH, OCCAnimations.YAMATO_AERIAL_CLEAVE
		}).innateSkill(Styles.SHEATH, (itemstack) -> {
			return OCCSkills.JUDGEMENT_CUT;
		 })
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.BLOCK, OCCAnimations.YAMATO_GUARD);
		 return builder;
	  };

	public static final Function<Item, CapabilityItem.Builder> REBELLION = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability
				.builder()
				.category(OCCWeaponCategories.YAMATO)
				.styleProvider((entitypatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.LONGSWORD)
				.swingSound(EpicFightSounds.WHOOSH.get())
				.canBePlacedOffhand(true)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.hitParticle((HitParticleType)EpicFightParticles.HIT_BLADE.get())
				.newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{
						Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3,
						Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH
				}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
					return EpicFightSkills.LIECHTENAUER;
				})
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, OCCAnimations.REBELLION_IDLE);
		return builder;
	};

       public OCCWeaponCapabilitiesPresets() {
       }
    
       @SubscribeEvent
       public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(OCCMod.MODID,"yamato_ds"), DARK_SLAYER);
		   event.getTypeEntry().put(new ResourceLocation(OCCMod.MODID,"rebellion"), REBELLION);
     }
}
