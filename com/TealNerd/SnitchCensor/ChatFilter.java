package com.TealNerd.SnitchCensor;

import java.util.ConcurrentModificationException;

import com.TealNerd.SnitchCensor.*;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatFilter {

	Minecraft mc = Minecraft.getMinecraft();

	public String msg;
	public int x = 0;

	public static void init()
	{

	}
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e){
		if(e.message!=null&&e.message.getFormattedText().contains("entered snitch at")){
			msg = e.message.getFormattedText();
			int l = msg.length();
			e.setCanceled(true);
			for(int i=0; i<l; i++){
				if(msg.substring(i, i+1).equals("[")){
					x = i;
					break;
				}
			}
			mc.thePlayer.addChatMessage(new ChatComponentText(msg.substring(0, x) + "[**** ** ****]"));
			
		}

		}
}
