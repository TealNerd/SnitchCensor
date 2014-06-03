package com.TealNerd.SnitchCensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private Pattern user = Pattern.compile("([a-zA-Z0-9_]+?) entered snitch at");
    public String username;
    protected boolean isEnemy = false;
    public Matcher snitchMatcher;
    public Matcher usernameMatcher;
    private String modDir = mc.mcDataDir + "/mods/SnitchCensor/";
    public File bounties = new File(modDir, "perps.txt");
    private Pattern name = Pattern.compile("\\\"name\\\":\\\"([A-Za-z0-9_]+?)\\\"");
    boolean hasBounty = false;

    {
 
    }
      
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) throws IOException{
    	msg = e.message.getUnformattedText();
    	Matcher snitchMatcher = snitch.matcher(msg);
    	Matcher usernameMatcher = user.matcher(msg);
    	
    	if(SnitchCensor.custom){
    		if(snitchMatcher.find()){
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
        	
        	String contents;   		
    		Scanner in = new Scanner(bounties);
    		contents = readFile(bounties);
    		Matcher nameMatcher = name.matcher(contents);
    		while(nameMatcher.find())
    		{	
    		        if(nameMatcher.group(1).equals(username))
    		        {
    		                hasBounty = true;
    		                break;
    		        }
    		        else
    		        {
    		                hasBounty = false;
    		        }
    		}
    		in.close();
        	
    		if(isEnemy){
    			if(SnitchCensor.isEnabled){
    				e.setCanceled(true);
    				finalstring = snitchMatcher.replaceAll("[**** ** ****]");
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + finalstring));
    			}else{
    				e.setCanceled(true);
    				finalstring = msg;
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + finalstring));
    			}
    		}else if(hasBounty){
    			if(SnitchCensor.isEnabled){
    				e.setCanceled(true);
    				finalstring = snitchMatcher.replaceAll("[**** ** ****]");
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + finalstring));
    			}else{
    				e.setCanceled(true);
    				finalstring = msg;
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + finalstring));
    			}
    		}else{
    			if(SnitchCensor.isEnabled){
    				e.setCanceled(true);
    				finalstring = snitchMatcher.replaceAll("[**** ** ****]");
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + finalstring));
    			}else{
    				e.setCanceled(true);
    				finalstring = msg;
    				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + finalstring));
    			}
    		}
        	
    		}
    	}else if(SnitchCensor.isEnabled){
    		e.setCanceled(true);
    		finalstring = snitchMatcher.replaceAll("[**** ** ****]");
			mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + finalstring));
    	}else{
    		e.setCanceled(true);
			finalstring = msg;
			mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + finalstring));
    	}
        }
    
    	
    	
         

    
    String readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
     
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    }
