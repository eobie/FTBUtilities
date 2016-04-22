package ftb.utils.handlers;

import ftb.lib.FTBLib;
import ftb.lib.api.*;
import ftb.utils.world.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

import java.util.ArrayList;

public class FTBUChatEventHandler
{
	private static final String[] LINK_PREFIXES = {"http://", "https://"};
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChatEvent(net.minecraftforge.event.ServerChatEvent e)
	{
		String[] msg = FTBLib.removeFormatting(e.getMessage()).split(" "); // https://github.com/LatvianModder
		
		ArrayList<String> links = new ArrayList<>();
		
		for(String s : msg)
		{
			int index = getFirstLinkIndex(s);
			if(index != -1) links.add(s.substring(index).trim());
		}
		
		if(!links.isEmpty())
		{
			final ITextComponent line = new TextComponentString("");
			boolean oneLink = links.size() == 1;
			
			for(int i = 0; i < links.size(); i++)
			{
				String link = links.get(i);
				ITextComponent c = new TextComponentString(oneLink ? "[ Link ]" : ("[ Link #" + (i + 1) + " ]"));
				c.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
				c.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
				line.appendSibling(c);
				if(!oneLink) line.appendSibling(new TextComponentString(" "));
			}
			
			line.getChatStyle().setColor(TextFormatting.GOLD);
			
			FTBLib.addCallback(new ServerTickCallback()
			{
				@Override
				public void onCallback()
				{
					for(ForgePlayer p : ForgeWorldMP.inst.getOnlinePlayers())
					{
						if(FTBUPlayerDataMP.get(p.toPlayerMP()).getFlag(FTBUPlayerData.CHAT_LINKS))
						{
							p.getPlayer().addChatMessage(line);
						}
					}
				}
			});
		}
	}
	
	private static int getFirstLinkIndex(String s)
	{
		for(String s1 : LINK_PREFIXES)
		{
			int idx = s.indexOf(s1);
			if(idx != -1) return idx;
		}
		
		return -1;
	}
}