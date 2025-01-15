package voidjam.occ.skills;

import yesman.epicfight.skill.SkillCategory;

public enum OCCSkillCategories implements SkillCategory {
	DEVIL_TRIGGER(true, true, true);
	
	boolean shouldSave;
	boolean shouldSyncronize;
	boolean modifiable;
	int id;
	
	OCCSkillCategories(boolean shouldSave, boolean shouldSyncronizedAllPlayers, boolean modifiable) {
		this.shouldSave = shouldSave;
		this.shouldSyncronize = shouldSyncronizedAllPlayers;
		this.modifiable = modifiable;
		this.id = SkillCategory.ENUM_MANAGER.assign(this);
	}
	
	public boolean shouldSave() {
		return this.shouldSave;
	}
	
	public boolean shouldSynchronize() {
		return this.shouldSyncronize;
	}
	
	public boolean learnable() {
		return this.modifiable;
	}

	@Override
	public int universalOrdinal() {
		return this.id;
	}
}