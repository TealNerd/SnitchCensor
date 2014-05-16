package com.TealNerd.SnitchCensor;

import java.util.ConcurrentModificationException;

import com.TealNerd.SnitchCensor.*;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatFilter {
    
    Minecraft mc = Minecraft.getMinecraft();
 
    public String msg;
    public String finalstring;
    public int x = 0;
    private Pattern snitch = Pattern.compile("\\[[-0-9]* [0-9]* [-0-9]*\\]");
   
   
    public static void init()
    {
 
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e){
            if(SnitchCensor.isEnabled == true){
                    msg = e.message.getFormattedText();
                  Matcher snitchMatcher = snitch.matcher(msg);
                    if (snitchMatcher.find()){
                    e.setCanceled(true);
                    finalstring = snitchMatcher.replaceAll("[**** ** ****]");
                    mc.thePlayer.addChatMessage(new ChatComponentText(finalstring));
                    }
            }
    }
}