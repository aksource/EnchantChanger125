package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.ITextureProvider;

public class EcItemMasterMateria extends Item implements ITextureProvider
{
	public static final String[] MasterMateriaNames = new String[] {"Ultimatum","Protection","Water","Attack","Digging","Bow","Addition"};
	public static final String[] MasterMateriaJPNames = new String[] {"ãÜã…","ñhå‰","êÖ","çUåÇ","çÃå@","ã|", "í«â¡"};
	public static int MasterMateriaNum = MasterMateriaNames.length;
	public EcItemMasterMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 1;
		setMaxDamage(0);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
	}
//	public String getTextureFile()
//	{
//		return mod_EnchantChanger.EcSprites;
//	}
	public String getItemNameIS(ItemStack par1ItemStack)
	{
		String BaseName = this.getItemName();
		int itemDmg = par1ItemStack.getItemDamage();
		return "ItemMasterMateria." + itemDmg;
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		for(int i = 0;i<this.MasterMateriaNum;i++)
		{
			itemList.add(new ItemStack(this,1,i));
		}
	}
}