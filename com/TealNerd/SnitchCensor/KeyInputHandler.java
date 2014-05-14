package com.TealNerd.SnitchCensor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(SnitchCensor.toggle.isPressed() && !SnitchCensor.isEnabled){
        	mc.thePlayer.addChatMessage(new ChatComponentText("SnitchCensor Enabled"));
        	SnitchCensor.isEnabled = true;
        	}
        else if(SnitchCensor.toggle.isPressed() && SnitchCensor.isEnabled){
        	mc.thePlayer.addChatMessage(new ChatComponentText("SnitchCensor Disabled"));
        	SnitchCensor.isEnabled = false;
        	}
    }

}
