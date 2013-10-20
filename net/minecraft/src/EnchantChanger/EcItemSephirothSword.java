package net.minecraft.src.EnchantChanger;

import java.util.List;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;

public class EcItemSephirothSword extends EcItemSword //implements IItemRenderer
{
	public double BoxSize = 5D;

	public EcItemSephirothSword(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
//		super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		if(par3EntityPlayer.isSneaking())
		{
			if(!par3EntityPlayer.capabilities.isCreativeMode)
				par3EntityPlayer.heal(-par3EntityPlayer.getHealth() + 1);
			//SpeedUp
			par3EntityPlayer.addPotionEffect(new PotionEffect(1,20*60,3));
			//DigSpeedUp
			par3EntityPlayer.addPotionEffect(new PotionEffect(3,20*60,3));
			//DmageUp
			par3EntityPlayer.addPotionEffect(new PotionEffect(5,20*60,3));
			//High Jump
			par3EntityPlayer.addPotionEffect(new PotionEffect(8,20*60,3));
			//Resistance
			par3EntityPlayer.addPotionEffect(new PotionEffect(11,20*60,3));
			//Fire resistance
			par3EntityPlayer.addPotionEffect(new PotionEffect(12,20*60,3));
			//Water Breathing
			par3EntityPlayer.addPotionEffect(new PotionEffect(13,20*60,3));
			//NightView
			par3EntityPlayer.addPotionEffect(new PotionEffect(16,20*60,3));
		}
		return par1ItemStack;
	}

	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		World world = player.worldObj;
		if(player.isSprinting())
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
					}
				}
			}
		}
		return false;
	}
}
