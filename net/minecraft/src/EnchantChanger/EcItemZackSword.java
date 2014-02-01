package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;

public class EcItemZackSword extends EcItemSword //implements IItemRenderer
{
//	public static final EcModelZackSword2 ZModel = new EcModelZackSword2();
	private double BoxSize=3D;

	public EcItemZackSword(int par1)
	{
		super(par1, EnumToolMaterial.IRON);
		this.setMaxDamage(EnumToolMaterial.IRON.getMaxUses() * 14);
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		if(mod_EnchantChanger.LimitBreakFlag[0])
		{
			par2EntityLiving.heartsLife=0;
		}
		return true;
	}
//	@Override
//	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//		return type == ItemRenderType.EQUIPPED;
//	}
//	@Override
//	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//		return false;
//	}
//
//	@Override
//	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		ZModel.renderItem(item, (EntityLiving)data[1]);
//	}
//	public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
//	{
//		par1ItemStack.damageItem(2, par6EntityLiving);
//		return true;
//	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking() && mod_EnchantChanger.LimitBreakCoolDownCount[0] == 0&&(par3EntityPlayer.getHealth() < 3 || par3EntityPlayer.capabilities.isCreativeMode))
		{
			mod_EnchantChanger.LimitBreakFlag[0]=true;
			mod_EnchantChanger.LimitBreakCount[0]=20*15;
			mod_EnchantChanger.LimitBreakCoolDownCount[0]=20*60*3;
			par3EntityPlayer.addPotionEffect(new PotionEffect(3,mod_EnchantChanger.LimitBreakCount[0],3));
			par3EntityPlayer.addChatMessage("LIMIT BREAK!!");
			return par1ItemStack;
		}
//		else
//		{
//			super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
//		}
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		World world = player.worldObj;
		if(mod_EnchantChanger.LimitBreakFlag[0])
		{
			if(entity instanceof EntityLiving)
			{
				List EntityList = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(BoxSize, BoxSize, BoxSize));
				for (int i=0; i < EntityList.size();i++)
				{
					Entity entity1=(Entity) EntityList.get(i);
					if(entity1 instanceof EntityLiving)
					{
						entity1.attackEntityFrom(DamageSource.generic, this.getDamageVsEntity(entity1));
						//((EntityLiving)entity1).hurtTime=0;
					}
				}
			}
		}
		return false;
	}


	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1));
	}
}
