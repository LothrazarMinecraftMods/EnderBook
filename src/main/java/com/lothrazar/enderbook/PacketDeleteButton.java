package com.lothrazar.enderbook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.SoundCategory;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDeleteButton  implements IMessage, IMessageHandler<PacketDeleteButton, IMessage>
{
	public int slot;
	public PacketDeleteButton(){}
	public PacketDeleteButton(int s)
	{
		slot = s;
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
	public IMessage onMessage(PacketDeleteButton message, MessageContext ctx)
	{
		EntityPlayer player = ((NetHandlerPlayServer)ctx.netHandler).playerEntity;

		ItemEnderBook.deleteWaypoint(player.getHeldItem(player.getActiveHand()), message.slot);

		//http://minecraft.gamepedia.com/Sounds.json
		UtilSound.playSound(player.getEntityWorld(), player.getPosition(), SoundEvents.item_chorus_fruit_teleport, SoundCategory.PLAYERS);
		//player.playSound("mob.endermen.portal", 1, 1);
		
		return null;
	}
}
