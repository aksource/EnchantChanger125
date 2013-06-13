package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.ITextureProvider;
public class EcItemEnchantmentTable extends Item implements ITextureProvider
{

	public EcItemEnchantmentTable(int par1) {
		super(par1);
		maxStackSize = 1;
		setMaxDamage(0);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
	}
//	public String getTextureFile()
//	{
//		return mod_EnchantChanger.EcSprites;
//	}
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.openGui(mod_EnchantChanger.instance, mod_EnchantChanger.PortableEnchantmentTableGuiId,par2World,0,0,0);

		return par1ItemStack;
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1));
	}
	@Override
	public Item setIconIndex(int par1)
	{
		this.iconIndex = par1;
		return this;
	}
}