package net.minecraft.src.EnchantChanger;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class EcItemUltimateWeapon extends EcItemSword //implements IItemRenderer
{
	public static final EcModelUltimateWeapon UModel = new EcModelUltimateWeapon();
	private int ultimateWeaponDamage;

	public EcItemUltimateWeapon(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
		this.setMaxDamage(EnumToolMaterial.IRON.getMaxUses() * 14);
		this.ultimateWeaponDamage =  0;
	}

	public int getDamageVsEntity(Entity par1Entity)
	{
		int playerhealth = ModLoader.getMinecraftInstance().thePlayer.getHealth();
		int playermaxhealth =  ModLoader.getMinecraftInstance().thePlayer.getMaxHealth();
		float healthratio = playerhealth / playermaxhealth;
		int mobmaxhealth = 0;
		if(par1Entity instanceof EntityLiving)
		{
			mobmaxhealth = MathHelper.floor_float(((EntityLiving)par1Entity).getMaxHealth()/3)+1;
		}
		if(healthratio >= 1 && mobmaxhealth > this.ultimateWeaponDamage+WeaponDamagefromHP())
			return mobmaxhealth;
		else
			return this.ultimateWeaponDamage+WeaponDamagefromHP();
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
//		super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	public int WeaponDamagefromHP()
	{
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		int nowHP = player.getHealth();
		float damageratio;
		switch(nowHP)
		{
		case 20:
		case 19:
		case 18:
		case 17:
			damageratio = 1;break;
		case 16:
		case 15:
		case 14:
		case 13:
		case 12:
		case 11:
			damageratio = 0.7f;break;
		case 10:
		case 9:
		case 8:
		case 7:
		case 6:
		case 5:
			damageratio = 0.5f;break;
		case 4:
		case 3:
		case 2:
		case 1:
			damageratio = 0.3f;break;
		default :damageratio = 0;
		}
		int EXPLv = player.experienceLevel;
		return MathHelper.floor_float((10 + EXPLv/5)*damageratio);

	}
}
