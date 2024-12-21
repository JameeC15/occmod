package voidjam.occ.world.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import voidjam.occ.main.OCCMod;

public class OCCTabs {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OCCMod.MODID);

	public static final RegistryObject<CreativeModeTab> ITEMS = TABS.register("items",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.occ.items"))
					.icon(() -> new ItemStack(OCCItems.YAMATO.get()))
					.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
					.displayItems((params, output) -> {
						OCCItems.ITEMS.getEntries().forEach(it -> {
							// FIXME: bad implement, maybe based protocol better yet.
							// ignore UCHIGATANA_SHEATH
							if (it == OCCItems.YAMATO_SHEATH) {
								return;
							}
							if (it == OCCItems.YAMATO_BLADE) {
								return;
							}
							output.accept(it.get());
						});
					})
					.build());
}