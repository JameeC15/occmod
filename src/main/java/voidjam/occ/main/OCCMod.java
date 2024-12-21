package voidjam.occ.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import voidjam.occ.world.items.OCCItems;
import voidjam.occ.world.items.OCCTabs;
import voidjam.occ.gameassets.OCCAnimations;
import voidjam.occ.gameassets.OCCSkills;
import voidjam.occ.gameassets.OCCSounds;
import voidjam.occ.particle.OCCParticles;
// The value here should match an entry in the META-INF/mods.toml file
@Mod("occ")
public class OCCMod
{
	public static final String MODID = "occ";
	public static final String CONFIG_FILE_PATH = OCCMod.MODID + ".toml";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static OCCMod instance;
	
	public static OCCMod getInstance() {
		return instance;
	}
	
    public OCCMod() {
    	instance = this;
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(OCCAnimations::registerAnimations);
    	
    	OCCItems.ITEMS.register(bus);
    	OCCParticles.PARTICLES.register(bus);
    	OCCSounds.SOUNDS.register(bus);
    	OCCTabs.TABS.register(bus);
    	//WOMSkillDataKeys.DATA_KEYS.register(bus);
    	
    	MinecraftForge.EVENT_BUS.register(this);
        //ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(IngameConfigurationScreen::new));
    }
}