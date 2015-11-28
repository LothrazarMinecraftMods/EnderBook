package com.lothrazar.enderbook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWarpButton  implements IMessage, IMessageHandler<PacketWarpButton, IMessage>
{
	public int slot;
	public PacketWarpButton(){}
	public PacketWarpButton(int s)
	{
		slot=s;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.slot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(slot);
	}
	
	@Override
	public IMessage onMessage(PacketWarpButton message, MessageContext ctx)
	{
		EntityPlayer player = ((NetHandlerPlayServer)ctx.netHandler).playerEntity;
		
		
		int cost = (int)ConfigSettings.expCostPerTeleport;
		
		
		if(cost != 0 && UtilExperience.getExpTotal(player) < cost )
		{
			player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("gui.chatexp")));
		}
		else
		{ 
			ItemEnderBook.teleport(player, message.slot);
		}
		
		return null;
	}
}
