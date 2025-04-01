package voidjam.occ.gameassets;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voidjam.occ.skills.OCCSkillCategories;
import voidjam.occ.skills.identity.DevilTrigger;
//import voidjam.occ.skills.identity.DevilTrigger;
//import voidjam.occ.skills.weaponpassive.YamatoPassive;
import voidjam.occ.skills.weaponinnate.DarkSlayer;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.skill.Skill.ActivateType;
import yesman.epicfight.skill.passive.PassiveSkill;

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
    SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("occ");
    WeaponInnateSkill judgementCut = (WeaponInnateSkill)modRegistry.build("judgement_cut", DarkSlayer::new, WeaponInnateSkill.createWeaponInnateBuilder());
   JUDGEMENT_CUT = judgementCut;

   PassiveSkill devilTrigger = modRegistry.build("devil_trigger", DevilTrigger::new, DevilTrigger.createPassiveBuilder().setCategory(OCCSkillCategories.DEVIL_TRIGGER).setActivateType(ActivateType.DURATION_INFINITE));
    DEVIL_TRIGGER = devilTrigger;
   }
}
