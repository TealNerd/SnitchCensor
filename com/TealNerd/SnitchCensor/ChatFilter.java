package com.TealNerd.SnitchCensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChatFilter {
    
    Minecraft mc = Minecraft.getMinecraft();
 
    public String msg;
    public String finalstring;
    private Pattern snitch = Pattern.compile("\\[[-0-9]* [0-9]* [-0-9]*\\]");
    private Pattern user = Pattern.compile("([a-zA-Z0-9]+?) entered snitch at");
    public String username;
    protected boolean isEnemy = false;

    {
 
    }
      
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) throws FileNotFoundException{
    	
    	if(SnitchCensor.custom){
    		
    		msg = e.message.getUnformattedText();
    		Matcher snitchMatcher = snitch.matcher(msg);
        	Matcher usernameMatcher = user.matcher(msg);
        	if(usernameMatcher.find()){
        		username = usernameMatcher.group(1);
        	}
        	String dir = mc.mcDataDir + "/mods/RadarBro/";
        	File enemies = new File(dir, "EnemyList.txt");
        	Scanner input = new Scanner(enemies);
        	
        	while(input.hasNext()){
        		String nextLine = input.nextLine();
        		if(nextLine.equals(username)){
        			isEnemy = true;
        		}else{
        			isEnemy = false;
        		}
        	}
        	
        	input.close();
        	
        	if(snitchMatcher.find() && isEnemy){
        	
        		if(SnitchCensor.isEnabled){
        			e.setCanceled(true);
        			finalstring = snitchMatcher.replaceAll("[**** ** ****]");
        			mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + finalstring));
        		}else{
        		e.setCanceled(true);
        		mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + msg));}
        	}else return;
        
        }
    	
    	
    	
    	else if(SnitchCensor.isEnabled){
	 		msg = e.message.getUnformattedText();
	 		Matcher snitchMatcher = snitch.matcher(msg);
            if (snitchMatcher.find()){
            e.setCanceled(true);
            finalstring = snitchMatcher.replaceAll("[**** ** ****]");
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + finalstring));
            }
            if(e.message.getFormattedText().contains("[Realistic Biomes]")){
            	e.setCanceled(true);
            }
    }
    	
    	
         
         }
    }
         

            
	
    
