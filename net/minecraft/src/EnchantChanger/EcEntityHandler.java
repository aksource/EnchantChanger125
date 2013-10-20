package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.World;
import net.minecraft.src.forge.IEntityLivingHandler;

public class EcEntityHandler implements IEntityLivingHandler
{
	public EcEntityHandler()
	{

	}

	@Override
	public boolean onEntityLivingSpawn(EntityLiving entity, World world, float x, float y, float z) {
		return false;
	}

	@Override
	public boolean onEntityLivingDeath(EntityLiving entity, DamageSource killer) {
		return false;
	}

	@Override
	public void onEntityLivingSetAttackTarget(EntityLiving entity, EntityLiving target) {

	}

	@Override
	public boolean onEntityLivingAttacked(EntityLiving entity,DamageSource attack, int damage) {
		return false;
	}

	@Override
	public void onEntityLivingJump(EntityLiving entity) {

	}

	@Override
	public boolean onEntityLivingFall(EntityLiving entity, float distance) {
		return false;
	}

	@Override
	public boolean onEntityLivingUpdate(EntityLiving entity) {
		return false;
	}

	@Override
	public int onEntityLivingHurt(EntityLiving entity, DamageSource source, int damage)
	{
		int var1 =damage;
		int type = 0;
		int amp = 0;;
		if(entity instanceof EntityPlayer)
		{
			InventoryPlayer IP =((EntityPlayer)entity).inventory;
			int var2 =0;
			if(source.fireDamage())
			{
				type=1;
				amp=2;
			}
			else if(source.getDamageType() =="fall")
			{
				type=2;
				amp=2;
			}
			else if(source.getDamageType() =="explosion")
			{
				type=3;
				amp=2;
			}
			else if(source.isProjectile())
			{
				type=4;
				amp=2;
			}
			else if(source.getDamageType() == "generic")
			{
				type=0;
				amp=1;
			}
			for(int i=0;i<IP.armorInventory.length;i++)
			{
				var2 += EnchantmentHelper.getEnchantmentLevel(type, IP.armorItemInSlot(i));
			}
			if(var2 > 10)
			{
				var1=damage - (damage * (var2 - 10) * amp)/100;
			}
		}
		return var1;
	}

	@Override
	public void onEntityLivingDrops(EntityLiving entity, DamageSource source, ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit, int specialDropValue) {

	}
}