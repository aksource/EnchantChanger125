package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.IItemRenderer;
public class EcItemSword extends ItemSword implements IItemRenderer
{
	private static final EcModelUltimateWeapon UModel = new EcModelUltimateWeapon();
	private static final EcModelCloudSwordCore2 CCModel = new EcModelCloudSwordCore2();
	private static final EcModelCloudSword2 CModel = new EcModelCloudSword2();
	private static final EcModelSephirothSword SModel = new EcModelSephirothSword();
	private static final EcModelZackSword ZModel = new EcModelZackSword();
	private boolean toggle = false;
	public EcItemSword(int par1 , EnumToolMaterial toolMaterial)
	{
		super(par1, toolMaterial);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
		this.setMaxDamage(-1);
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par3Entity instanceof EntityPlayer && par5)
		{
			this.toggle = mod_EnchantChanger.MagicKeyDown && !toggle;
			if(toggle)
			{
				doMagic(par1ItemStack, par2World, (EntityPlayer) par3Entity);
				mod_EnchantChanger.MagicKeyDown = toggle = !toggle;
			}
		}
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1));
	}
	public Item setNoRepair()
	{
		canRepair = false;
		return this;
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
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() instanceof EcItemZackSword)
			this.ZModel.renderItem(item, (EntityLiving) data[1]);
		else if(item.getItem() instanceof EcItemCloudSword)
			this.CModel.renderItem(item, (EntityLiving) data[1]);
		else if(item.getItem() instanceof EcItemCloudSwordCore)
			this.CCModel.renderItem(item, (EntityLiving) data[1], ((EcItemCloudSwordCore)item.getItem()).ActiveMode);
		else if(item.getItem() instanceof EcItemSephirothSword || item.getItem() instanceof EcItemSephirothSwordImit)
			this.SModel.renderItem(item, (EntityLiving) data[1]);
		else if(item.getItem() instanceof EcItemUltimateWeapon)
			this.UModel.renderItem(item, (EntityLiving) data[1]);
	}
}