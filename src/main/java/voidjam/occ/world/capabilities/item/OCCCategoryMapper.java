package voidjam.occ.world.capabilities.item;

import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OCCCategoryMapper {
    private static final Map<OCCWeaponCategories, WeaponCategory> categoryMap = new HashMap<>();

    static {
        categoryMap.put(OCCWeaponCategories.YAMATO, CapabilityItem.WeaponCategories.UCHIGATANA);
    }

    public static CapabilityItem.Builder apply(Item item, OCCWeaponCategories category) {
        WeaponCategory mappedCategory = categoryMap.getOrDefault(category, category);

        try {
            Method applyMethod = mappedCategory.getClass().getMethod("apply", Item.class);
            return (CapabilityItem.Builder) applyMethod.invoke(mappedCategory, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
