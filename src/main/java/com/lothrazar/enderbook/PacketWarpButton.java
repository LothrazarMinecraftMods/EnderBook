package com.lothrazar.enderbook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketWarpButton  implements IMessage, IMessageHandler<PacketWarpButton, IMessage>
{
	@Override
	public void fromBytes(ByteBuf buf) 
	{

		
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{

		
	}
	
	@Override
	public IMessage onMessage(PacketWarpButton message, MessageContext ctx)
	{
		//since we are on the server right now:
		EntityPlayer player = ((NetHandlerPlayServer)ctx.netHandler).playerEntity;
		//otherwise, on the client we would use  Minecraft.getMinecraft().thePlayer;
		

		//TODO: send data inside packet  and use  message.x
		
		
		
		
		
		//do player warp and stuff. just a test:
		player.setPositionAndUpdate(0, 99, 0);
		
		
		return null;
	}
}
