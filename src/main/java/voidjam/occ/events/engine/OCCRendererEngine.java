package voidjam.occ.events.engine;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voidjam.occ.world.items.OCCItems;
import voidjam.occ.client.renderer.patched.item.YamatoRenderer;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@EventBusSubscriber(
   modid = "occ",
   bus = Bus.MOD
)
public class OCCRendererEngine {
   public OCCRendererEngine() {
   }

   @SubscribeEvent
   public static void registerRenderer(PatchedRenderersEvent.Add event) {
      event.addItemRenderer((Item)OCCItems.YAMATO.get(), new YamatoRenderer());
   }
}
