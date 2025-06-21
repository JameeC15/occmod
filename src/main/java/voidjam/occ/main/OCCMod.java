package voidjam.occ.main;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import voidjam.occ.gameassets.MiscAnimations;
import voidjam.occ.gameassets.OCCSounds;
import voidjam.occ.skills.OCCSkillCategories;
import voidjam.occ.skills.OCCSkillDataKeys;
import voidjam.occ.skills.OCCSkillSlots;
import voidjam.occ.skills.guard.OCCGuard;
import voidjam.occ.world.items.OCCItems;
import voidjam.occ.world.items.OCCTabs;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;
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
		bus.addListener(MiscAnimations::registerAnimations);
    	
		SkillCategory.ENUM_MANAGER.registerEnumCls(OCCMod.MODID, OCCSkillCategories.class);
    	SkillSlot.ENUM_MANAGER.registerEnumCls(OCCMod.MODID, OCCSkillSlots.class);

		OCCItems.ITEMS.register(bus);
    	OCCSounds.SOUNDS.register(bus);
		bus.addListener(OCCGuard::buildSkillEvent);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(OCCGuard::regIcon));
    	OCCTabs.TABS.register(bus);
    	OCCSkillDataKeys.DATA_KEYS.register(bus);
    	
    	MinecraftForge.EVENT_BUS.register(this);

		
        //ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(IngameConfigurationScreen::new));
    }
}