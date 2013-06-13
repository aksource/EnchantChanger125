package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;

public class EcItemHugeMateria extends Item// implements ITextureProvider
{
	public EcItemHugeMateria(int par1)
	{
		super(par1);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
	}
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
	{
		switch(par7)
		{
		case 0:par5-=3;break;
		case 1:par5++;break;
		case 2:par6--;break;
		case 3:par6++;break;
		case 4:par4--;break;
		case 5:par4++;break;
		}
		Block hugemateria = mod_EnchantChanger.HugeMateria;
		if (par2EntityPlayer.canPlayerEdit(par4, par5, par6) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6) && par2EntityPlayer.canPlayerEdit(par4, par5 + 2, par6))
		{
			if (!hugemateria.canPlaceBlockAt(par3World, par4, par5, par6) || !hugemateria.canPlaceBlockAt(par3World, par4, par5 + 1, par6) || !hugemateria.canPlaceBlockAt(par3World, par4, par5 + 2, par6))
			{
				return false;
			}
			else
			{
				par3World.editingBlocks = true;
				par3World.setBlockAndMetadataWithNotify(par4, par5, par6, hugemateria.blockID, 0);
				par3World.setBlockAndMetadataWithNotify(par4, par5 + 1, par6, hugemateria.blockID, 1);
				par3World.setBlockAndMetadataWithNotify(par4, par5 + 2, par6, hugemateria.blockID, 2);
				par3World.editingBlocks = false;
				par3World.notifyBlocksOfNeighborChange(par4, par5, par6, hugemateria.blockID);
				par3World.notifyBlocksOfNeighborChange(par4, par5 + 1, par6, hugemateria.blockID);
				par3World.notifyBlocksOfNeighborChange(par4, par5 + 2, par6, hugemateria.blockID);
				--par1ItemStack.stackSize;
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this));
	}
}