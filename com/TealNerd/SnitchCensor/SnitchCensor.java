package com.TealNerd.SnitchCensor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod(modid="SnitchCensor", name="Snitch Coordinate Censor", version="1.2.0")

public class SnitchCensor {

	Minecraft mc = Minecraft.getMinecraft();
	static boolean isEnabled = false;
	static boolean custom = true;
	static boolean bounty = false;
	public static KeyBinding toggle;
	public static KeyBinding customkey;
	private String dir = mc.mcDataDir + "/mods/RadarBro/";
	private File enemies = new File(dir, "EnemyList.txt");



    // The instance of your mod that Forge uses.
    @Instance(value = "SnitchCensor")
    public static SnitchCensor instance;
    
    @SidedProxy(clientSide="com.TealNerd.SnitchCensor.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)  {
    	SnitchCensor.instance = this;
    	MinecraftForge.EVENT_BUS.register(new ChatFilter());
    	FMLCommonHandler.instance().bus().register(new KeyInputHandler());
    	
    	if(!enemies.exists()){
    		new File(dir).mkdirs();
    		try {
				enemies.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
            proxy.registerListeners();
            toggle = new KeyBinding("Toggle Censor", Keyboard.KEY_T, "Snitch Censor");
            ClientRegistry.registerKeyBinding(toggle);
            customkey = new KeyBinding("Toggle Enemy Highlighting", Keyboard.KEY_L, "Snitch Censor");
            ClientRegistry.registerKeyBinding(customkey);
    }
    
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
}
