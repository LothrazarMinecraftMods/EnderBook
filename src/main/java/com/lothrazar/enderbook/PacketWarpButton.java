package com.lothrazar.enderbook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
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
		this.slot = buf.readInt();//ByteBufUtils.readVarInt(buf,5);//FALSE DO NOT DO THISbuf.readInt(); //??
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		//ByteBufUtils.writeVarInt(buf, slot, 5);
		buf.writeInt(slot);
	}
	
	@Override
	public IMessage onMessage(PacketWarpButton message, MessageContext ctx)
	{
		//since we are
		//System.out.println("onMessage  with "+ message.slot );  
		EntityPlayer player = ((NetHandlerPlayServer)ctx.netHandler).playerEntity;
		//otherwise, on the client we would use  Minecraft.getMinecraft().thePlayer;
		

		//TODO: send data inside packet  and use  message.x
		
		
		
		ItemEnderBook.teleport(player, message.slot);
		
		//do player warp and stuff. just a test:
		//player.setPositionAndUpdate(0, 99, 0);
		 
		//http://minecraft.gamepedia.com/Sounds.json
		player.playSound("mob.endermen.portal", 1, 1);
		
		return null;
	}
}
