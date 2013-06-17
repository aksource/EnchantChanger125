package net.minecraft.src.MultiToolHolders;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.mod_MultiToolHolders;
import net.minecraft.src.forge.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public CommonProxy(){}
	public void registerClientInformation(){}

	public void registerTileEntitySpecialRenderer(){}


	@Override
	public Object getGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == mod_MultiToolHolders.guiIdHolder3)
		{
			if(player.getCurrentEquippedItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemStack stack = player.getCurrentEquippedItem();
				return new GuiToolHolder(player.inventory, ((ItemMultiToolHolder)stack.getItem()).getData(stack, world), 3);
			}
			else
				return null;
		}
		else if(id == mod_MultiToolHolders.guiIdHolder5)
		{
			if(player.getCurrentEquippedItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemStack stack = player.getCurrentEquippedItem();
				return new GuiToolHolder(player.inventory, ((ItemMultiToolHolder)stack.getItem()).getData(stack, world), 5);
			}
			else
				return null;
		}
		else if(id == mod_MultiToolHolders.guiIdHolder9)
		{
			if(player.getCurrentEquippedItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemStack stack = player.getCurrentEquippedItem();
				return new GuiToolHolder(player.inventory, ((ItemMultiToolHolder)stack.getItem()).getData(stack, world), 9);
			}
			else
				return null;
		}
		else if(id == mod_MultiToolHolders.guiIdHolder7)
		{
			if(player.getCurrentEquippedItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemStack stack = player.getCurrentEquippedItem();
				return new GuiToolHolder(player.inventory, ((ItemMultiToolHolder)stack.getItem()).getData(stack, world), 7);
			}
			else
				return null;
		}
		else
			return null;
	}
	public World getClientWorld()
	{
		return null;
	}
}