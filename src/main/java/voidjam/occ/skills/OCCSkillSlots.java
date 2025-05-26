package voidjam.occ.skills;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum OCCSkillSlots implements SkillSlot {
	DEVIL_TRIGGER(OCCSkillCategories.DEVIL_TRIGGER),
	;
	
	SkillCategory category;
	int id;
	
	OCCSkillSlots(SkillCategory category) {
		this.category = category;
		this.id = SkillSlot.ENUM_MANAGER.assign(this);
	}
	
	@Override
	public SkillCategory category() {
		return this.category;
	}
	
	@Override
	public int universalOrdinal() {
		return this.id;
	}
}