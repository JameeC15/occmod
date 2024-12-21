package voidjam.occ.world.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import voidjam.occ.main.OCCMod;

public class OCCItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OCCMod.MODID);
	
	public static final RegistryObject<Item> YAMATO = ITEMS.register("ds_yamato", () -> new YamatoItem(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> REBELLION = ITEMS.register("rebellion", () -> new RebellionItem(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> YAMATO_SHEATH = ITEMS.register("yamato_sheath", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> YAMATO_BLADE = ITEMS.register("yamato_blade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
}