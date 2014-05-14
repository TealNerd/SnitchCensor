package com.TealNerd.SnitchCensor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid="snitchcensor", name="Snitch Coordinate Censor", version="1.0.0")

public class SnitchCensor {

	
    // The instance of your mod that Forge uses.
    @Instance(value = "SnitchCensor")
    public static SnitchCensor instance;
    
    @SidedProxy(clientSide="com.TealNerd.SnitchCensor.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	SnitchCensor.instance = this;
    	MinecraftForge.EVENT_BUS.register(new ChatParse());
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
            proxy.registerListeners();
            
           
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
}
