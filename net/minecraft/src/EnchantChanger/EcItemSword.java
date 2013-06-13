package net.minecraft.src.EnchantChanger;

import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
public class EcItemSword extends ItemSword
{
    public EcItemSword(int par1 , EnumToolMaterial toolMaterial)
    {
        super(par1, toolMaterial);
        this.setTextureFile(mod_EnchantChanger.EcSprites);
    }
    public static void doMagic(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(EnchantmentHelper.getEnchantmentLevel(mod_EnchantChanger.EnchantmentMeteoId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Meteo(par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(mod_EnchantChanger.EndhantmentHolyId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Holy(par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(mod_EnchantChanger.EnchantmentTelepoId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Teleport(par1ItemStack, par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(mod_EnchantChanger.EnchantmentThunderId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Thunder(par2World, par3EntityPlayer);
    	}
    }

//    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//    {
//        return false;
//    }
    public static boolean hasFloat(ItemStack itemstack)
    {
    	if(EnchantmentHelper.getEnchantmentLevel(mod_EnchantChanger.EnchantmentFloatId, itemstack) > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}