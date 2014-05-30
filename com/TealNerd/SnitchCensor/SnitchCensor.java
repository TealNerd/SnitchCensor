package com.TealNerd.SnitchCensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

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

@Mod(modid="SnitchCensor", name="Snitch Coordinate Censor", version="1.2.0")

public class SnitchCensor {

	Minecraft mc = Minecraft.getMinecraft();
	static boolean isEnabled = true;
	static boolean custom = true;
	public static KeyBinding toggle;
	public static KeyBinding highlightkey;
	private String dir = mc.mcDataDir + "/mods/RadarBro/";
	private String modDir = mc.mcDataDir + "/mods/SnitchCensor/";
	private File enemies = new File(dir, "EnemyList.txt");
	public File bounties = new File(modDir, "perps.txt");
    private Pattern snitch = Pattern.compile("\\[[-0-9]* [0-9]* [-0-9]*\\]");
    private Pattern user = Pattern.compile("([a-zA-Z0-9]+?) entered snitch at");
    String perps;
    static boolean hasBounty;
    private Pattern name = Pattern.compile("\"name\":\"([A-Za-z0-9]+?)\"");
    String username;



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
    	
    	if(!bounties.exists()){
    		new File(modDir).mkdirs();
    		try {
				bounties.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
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
            toggle = new KeyBinding("Toggle Censor", Keyboard.KEY_K, "Snitch Censor");
            ClientRegistry.registerKeyBinding(toggle);
            highlightkey = new KeyBinding("Toggle Highlighting", Keyboard.KEY_L, "Snitch Censor");
            ClientRegistry.registerKeyBinding(highlightkey);
    }
    
    @SubscribeEvent

    public void onChat(ClientChatReceivedEvent e) throws IOException, FileNotFoundException{
    	
    	String msg = e.message.getUnformattedText();
    	Matcher snitchMatcher = snitch.matcher(msg);
    	Matcher usernameMatcher = user.matcher(msg);

    		if(snitchMatcher.find()){
    			
    			//Get perps in file
    			URL url;
    			InputStream is = null;
    			BufferedReader br;
    			String line;    			

    			try{

    				url = new URL("http://www.civbounty.com/api/perpetrators/active");

    				is = url.openStream();

    				br = new BufferedReader(new InputStreamReader(is));

    				

    				while((line = br.readLine()) != null){

    					try{

    						FileWriter fw = new FileWriter(bounties,false);

    						fw.write(line);

    						fw.close();

    					} catch(IOException ioe){

    						System.err.println("IOException: " + ioe.getMessage());

    					}

    				}

    			} catch (MalformedURLException mue) {

    		         mue.printStackTrace();

    		    } catch (IOException ioe) {

    		         ioe.printStackTrace();

    		    } finally {

    		        try {

    		            if (is != null) is.close();

    		        } catch (IOException ioe) {

    		            // nothing to see here

    		        }

    		}
    		
    		
    		if(usernameMatcher.find()){
    			username = usernameMatcher.group(1);
    		}
    		
    		
    	
    		//Check if user is in file
    		 
    		File perplist = new File(mc.mcDataDir + "/mods/SnitchCensor/perps.txt");
        	Scanner in = new Scanner(perplist);
        	while(in.hasNext()){
        		String nameString = in.next(name);
        		Matcher nameMatcher = name.matcher(nameString);
        		String finalname = nameMatcher.group(1);
        		
        		if(finalname.equals(username)){
        			hasBounty = true;
        		}else{hasBounty = false;}
        	}
        	in.close();
        	
        	
    		}
    		}
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
}
