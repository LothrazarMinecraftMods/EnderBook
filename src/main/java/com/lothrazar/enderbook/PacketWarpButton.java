package com.lothrazar.enderbook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

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
		
		
		int cost = (int)ModEnderBook.config.expCostPerTeleport;
		
		
		if(cost != 0 && UtilExperience.getExpTotal(player) < cost )
		{
			//TODO: send chat message to playyer/ send sound effect, etc
			System.out.println("not enough, you have only " +UtilExperience.getExpTotal(player)+ " need "+cost);
		}
		else
		{
			ItemEnderBook.teleport(player, message.slot);
			
			//then drain
			UtilExperience.drainExp(player, cost);
		}
		//http://minecraft.gamepedia.com/Sounds.json
		player.playSound("mob.endermen.portal", 1, 1);
		
		return null;
	}
}
