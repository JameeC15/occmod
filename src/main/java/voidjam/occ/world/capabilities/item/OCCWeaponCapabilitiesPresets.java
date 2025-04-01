package voidjam.occ.world.capabilities.item;

import voidjam.occ.gameassets.OCCAnimations;
import voidjam.occ.gameassets.OCCSkills;
import voidjam.occ.main.OCCMod;
import voidjam.occ.skills.OCCSkillDataKeys;

import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
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
		.styleProvider((entitypatch) ->
		 	{
				return ((PlayerPatch<?>)entitypatch)
				.getSkill(OCCSkills.DEVIL_TRIGGER) != null && 
				((PlayerPatch<?>)entitypatch)
				.getSkill(OCCSkills.DEVIL_TRIGGER)
				.getSkill().getRegistryName()
				.getPath() == "devil_trigger" &&
				((PlayerPatch<?>)entitypatch)
				.getSkill(OCCSkills.DEVIL_TRIGGER).getDataManager().getDataValue(OCCSkillDataKeys.ACTIVE.get()) ? Styles.TWO_HAND : Styles.SHEATH;
			}
		 )
		 .collider(ColliderPreset.LONGSWORD)
		 .swingSound(EpicFightSounds.WHOOSH_BIG.get())
		 .canBePlacedOffhand(true)
		 .hitSound(EpicFightSounds.BLUNT_HIT.get())
		 .hitParticle((HitParticleType)EpicFightParticles.HIT_BLADE.get())
		 .newStyleCombo(Styles.SHEATH, new StaticAnimation[]{
			OCCAnimations.YAMATO_AUTO1, OCCAnimations.YAMATO_AUTO2,
			OCCAnimations.NO, OCCAnimations.NO
		}).innateSkill(Styles.SHEATH, (itemstack) -> {
			return OCCSkills.JUDGEMENT_CUT;
		 })
		 .newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{
			OCCAnimations.NO,
			OCCAnimations.NO, OCCAnimations.NO
		}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
			return OCCSkills.JUDGEMENT_CUT;
		 })
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.BLOCK_SHIELD, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.CLIMB, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.CREATIVE_FLY, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.CREATIVE_IDLE, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.DEATH, OCCAnimations.NO)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.EAT, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.FLY, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.INACTION, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.LANDING_RECOVERY, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.MOUNT, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.RELOAD, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.SLEEP, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.SIT, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, OCCAnimations.YAMATO_WALK)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, OCCAnimations.YAMATO_RUN)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.JUMP, OCCAnimations.YAMATO_JUMP)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, OCCAnimations.YAMATO_WALK)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.FLOAT, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.FALL, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, OCCAnimations.YAMATO_IDLE)
		 .livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, OCCAnimations.YAMATO_WALK)
		 
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK_SHIELD, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CLIMB, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.DEATH, OCCAnimations.NO)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.EAT, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLY, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.INACTION, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.LANDING_RECOVERY, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.MOUNT, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RELOAD, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SLEEP, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SIT, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, OCCAnimations.YAMATO_DT_WALK)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, OCCAnimations.YAMATO_DT_RUN)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, OCCAnimations.YAMATO_DT_JUMP)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, OCCAnimations.YAMATO_DT_WALK)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, OCCAnimations.YAMATO_DT_IDLE)
		 .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, OCCAnimations.YAMATO_DT_WALK);
		 return builder;
		};

       public OCCWeaponCapabilitiesPresets() {
       }
    
       @SubscribeEvent
       public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(OCCMod.MODID,"yamato_ds"), DARK_SLAYER);
     }
}
