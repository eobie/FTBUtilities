package com.feed_the_beast.ftbu.net;

import com.feed_the_beast.ftbl.lib.net.MessageToServer;
import com.feed_the_beast.ftbl.lib.net.NetworkWrapper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageRequestWarpList extends MessageToServer<MessageRequestWarpList>
{
    public MessageRequestWarpList()
    {
    }

    @Override
    public NetworkWrapper getWrapper()
    {
        return FTBUNetHandler.NET;
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
    }

    @Override
    public void toBytes(ByteBuf io)
    {
    }

    @Override
    public void onMessage(MessageRequestWarpList m, EntityPlayer player)
    {
        new MessageSendWarpList((EntityPlayerMP) player).sendTo(player);
    }
}