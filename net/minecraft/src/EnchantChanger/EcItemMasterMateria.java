package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_EnchantChanger;

public class EcItemMasterMateria extends Item
{
	public static final String[] MasterMateriaNames = new String[] {"Ultimatum","Protection","Water","Attack","Digging","Bow","Addition"};
	public static final String[] MasterMateriaJPNames = new String[] {"究極","防御","水","攻撃","採掘","弓", "追加"};
	public static int MasterMateriaNum = MasterMateriaNames.length;
	public EcItemMasterMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 1;
		setMaxDamage(0);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
	}
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