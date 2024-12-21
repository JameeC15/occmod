package voidjam.occ.gameassets;

import java.util.Set;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voidjam.occ.main.OCCMod;
//import voidjam.occ.skills.identity.DevilTrigger;
//import voidjam.occ.skills.weaponpassive.YamatoPassive;
import voidjam.occ.skills.weaponinnate.DarkSlayer;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.skill.Skill.ActivateType;
import yesman.epicfight.skill.identity.RevelationSkill;

@EventBusSubscriber(
   modid = "occ",
   bus = Bus.MOD
)
public class OCCSkills {
   //public static Skill YAMATO_PASSIVE;
   public static Skill JUDGEMENT_CUT;
   public static Skill DEVIL_TRIGGER;

   public OCCSkills() {
   }
   @SubscribeEvent
   public static void buildSkillEvent(SkillBuildEvent build) {
    //YAMATO_PASSIVE = onBuild.build("occ", "yamato_passive");
    SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("occ");
    WeaponInnateSkill judgementCut = (WeaponInnateSkill)modRegistry.build("judgement_cut", DarkSlayer::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.DURATION_INFINITE));
    judgementCut.newProperty()
					.addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
					.addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(20))
					.addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
					.addProperty(AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
					.addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
					.addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT);
   JUDGEMENT_CUT = judgementCut;
    
    //DEVIL_TRIGGER = modRegistry.build("devil_trigger", DevilTrigger::new, DevilTrigger.createDevilTriggerSkillBuilder());
   }
}
