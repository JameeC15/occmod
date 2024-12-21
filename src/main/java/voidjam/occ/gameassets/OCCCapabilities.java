package voidjam.occ.gameassets;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum OCCCapabilities implements WeaponCategory {
   YAMATO;

   final int id;

   private OCCCapabilities() {
      this.id = WeaponCategory.ENUM_MANAGER.assign(this);
   }

   public int universalOrdinal() {
      return this.id;
   }
}

