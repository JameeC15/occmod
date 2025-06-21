package voidjam.occ.world.capabilities.item;

import net.minecraft.world.item.Item;

import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public enum OCCWeaponCategories implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
    YAMATO;

    final int id;

    OCCWeaponCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
    @Override
    public CapabilityItem.Builder apply(Item item) {
        return OCCCategoryMapper.apply(item, this);
    }
}

