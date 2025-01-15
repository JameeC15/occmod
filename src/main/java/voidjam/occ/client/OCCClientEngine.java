package voidjam.occ.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.events.engine.ControllEngine;

@OnlyIn(Dist.CLIENT)
public class OCCClientEngine {
	private static OCCClientEngine instance;
	
	public static OCCClientEngine getInstance() {
		return instance;
	}

	public Minecraft minecraft;
	public ControllEngine controllEngine;
	
	public OCCClientEngine() {
		instance = this;
		this.minecraft = Minecraft.getInstance();
		this.controllEngine = new ControllEngine();
	}
}