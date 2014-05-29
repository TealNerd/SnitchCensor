package com.TealNerd.SnitchCensor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(SnitchCensor.toggle.isPressed()){
        	if(!SnitchCensor.isEnabled){
        	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "SnitchCensor Enabled"));
        	SnitchCensor.isEnabled = true;
        	}else if(SnitchCensor.isEnabled){
        	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "SnitchCensor Disabled"));
        	SnitchCensor.isEnabled = false;
        	}
        }
       
        else if(SnitchCensor.highlightkey.isPressed()){
                	if(!SnitchCensor.custom){
                	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Highlighting Enabled"));
                	SnitchCensor.custom = true;
                	}else if(SnitchCensor.custom){
                	mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Highlighting Disabled"));
                	SnitchCensor.custom = false;
                	}
        }
        
}
    }

