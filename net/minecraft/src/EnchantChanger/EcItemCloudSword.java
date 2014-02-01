package net.minecraft.src.EnchantChanger;

import java.util.List;
import java.util.Random;

import net.minecraft.src.AchievementList;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Potion;
import net.minecraft.src.StatList;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.ForgeHooks;

public class EcItemCloudSword extends EcItemSword //implements IItemRenderer
{
	public static final EcModelCloudSword2 CModel = new EcModelCloudSword2();
	private int SlotNum = 5;
	private EcCloudSwordData SwordData;
	private Random rand = new Random();

	public EcItemCloudSword(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
		this.setMaxDamage(EnumToolMaterial.IRON.getMaxUses() * 14);
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par2EntityLiving.heartsLife = 0;
		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	}
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(this.SlotNum == 5)
			return false;
		else if(this.SwordData.getStackInSlot(SlotNum) != null)
		{
			this.attackTargetEntityWithTheItem(entity, player, this.SwordData.getStackInSlot(SlotNum));
			return true;
		}
		else
			return false;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking())
		{
			this.doCastOffSwords(par3EntityPlayer);
			return this.makeCloudSwordCore(par1ItemStack);
		}
		else
		{
//			super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			if(this.SlotNum == 5)
				this.SlotNum = 0;
			else
				this.SlotNum++;
			if(this.SlotNum != 5)
				par3EntityPlayer.addChatMessage((String) this.SwordData.getStackInSlot(SlotNum).getItemNameandInformation().get(0));
			return par1ItemStack;
		}
	}
	public ItemStack makeCloudSwordCore(ItemStack stack)
	{
    	int EnchNum;
    	int EnchLv;
    	ItemStack ChangeSwordCore = new ItemStack(mod_EnchantChanger.ItemCloudSwordCore, 1);
		NBTTagList enchOnItem = stack.getEnchantmentTagList();
		if(enchOnItem !=null)
		{
			for (int i = 0; i < enchOnItem.tagCount(); ++i)
			{
				if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
				{
					EnchNum = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id");
					EnchLv = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl");
					ChangeSwordCore.addEnchantment(Enchantment.enchantmentsList[EnchNum], EnchLv);
				}
			}
		}
		return ChangeSwordCore;
	}
	public void doCastOffSwords(EntityPlayer player)
	{
		for(int i=0;i<5;i++)
		{
			int j;
			for(j=0;j<9;j++)
			{
				if(player.inventory.getStackInSlot(j) ==null)
				{
					player.inventory.setInventorySlotContents(j, SwordData.getStackInSlot(i));
					break;
				}
			}
			if(j == 9)
				player.dropPlayerItem(SwordData.getStackInSlot(i));
			SwordData.setInventorySlotContents(i, null);
		}
	}
	public void addInformation(ItemStack par1ItemStack, List par2List) {
		List slotItem;
		if(this.SlotNum != 5 && this.SwordData.getStackInSlot(SlotNum) != null)
		{
			slotItem = this.SwordData.getStackInSlot(SlotNum).getItemNameandInformation();
			par2List.addAll(slotItem);
		}
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if (!par2World.isRemote && par3Entity instanceof EntityPlayer)
		{
			this.SwordData = this.getSwordData(par1ItemStack, par2World);
			this.SwordData.onUpdate(par2World, (EntityPlayer) par3Entity);
		}
	}
	private EcCloudSwordData getSwordData(ItemStack var1, World var2)
	{
		String var3 = "swords";
		EcCloudSwordData var4 = (EcCloudSwordData)var2.loadItemData(EcCloudSwordData.class, var3);

		if (var4 == null)
		{
			var4 = new EcCloudSwordData(var3);
			var4.markDirty();
			var2.setItemData(var3, var4);
		}

		return var4;
    }
	public void attackTargetEntityWithTheItem(Entity par1Entity, EntityPlayer player,ItemStack stack)
	{
		if (!ForgeHooks.onEntityInteract(player, par1Entity, true))
		{
			return;
		}
		if (stack != null && stack.getItem().onLeftClickEntity(stack, player, par1Entity))
		{
			return;
		}
		if (par1Entity.canAttackWithItem())
		{
			int var2 = stack.getDamageVsEntity(par1Entity);

			if (player.isPotionActive(Potion.damageBoost))
			{
				var2 += 3 << player.getActivePotionEffect(Potion.damageBoost).getAmplifier();
			}

			if (player.isPotionActive(Potion.weakness))
			{
				var2 -= 2 << player.getActivePotionEffect(Potion.weakness).getAmplifier();
			}

			int var3 = 0;
			int var4 = 0;

			if (par1Entity instanceof EntityLiving)
			{
				var4 = this.getEnchantmentModifierLiving(stack, player, (EntityLiving)par1Entity);
				var3 += EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
			}

			if (player.isSprinting())
			{
				++var3;
			}

			if (var2 > 0 || var4 > 0)
			{
				boolean var5 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && par1Entity instanceof EntityLiving;

				if (var5)
				{
					var2 += this.rand.nextInt(var2 / 2 + 2);
				}

				var2 += var4;
				boolean var6 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(player), var2);

				if (var6)
				{
					if (var3 > 0)
					{
						par1Entity.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F));
						player.motionX *= 0.6D;
						player.motionZ *= 0.6D;
						player.setSprinting(false);
					}

					if (var5)
					{
						player.onCriticalHit(par1Entity);
					}

					if (var4 > 0)
					{
						player.onEnchantmentCritical(par1Entity);
					}

					if (var2 >= 18)
					{
						player.triggerAchievement(AchievementList.overkill);
					}

					player.setLastAttackingEntity(par1Entity);
				}

				ItemStack var7 = stack;

				if (var7 != null && par1Entity instanceof EntityLiving)
				{
					var7.hitEntity((EntityLiving)par1Entity, player);

					if (var7.stackSize <= 0)
					{
						var7.onItemDestroyedByUse(player);
						this.SwordData.setInventorySlotContents(this.SlotNum, (ItemStack)null);
						this.doCastOffSwords(player);
						player.inventory.setInventorySlotContents(player.inventory.currentItem, this.makeCloudSwordCore(player.getCurrentEquippedItem()));
					}
				}

				if (par1Entity instanceof EntityLiving)
				{
					player.addStat(StatList.damageDealtStat, var2);
					int var8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);

					if (var8 > 0)
					{
						par1Entity.setFire(var8 * 4);
					}
				}

				player.addExhaustion(0.3F);
			}
		}
	}
	public int getEnchantmentModifierLiving(ItemStack stack, EntityLiving attacker, EntityLiving enemy)
	{
		int calc = 0;
		if (stack != null)
		{
			NBTTagList nbttaglist = stack.getEnchantmentTagList();

			if (nbttaglist != null)
			{
				for (int i = 0; i < nbttaglist.tagCount(); ++i)
				{
					short short1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
					short short2 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

					if (Enchantment.enchantmentsList[short1] != null)
					{
						calc += Enchantment.enchantmentsList[short1].calcModifierLiving(short2, enemy);
					}
				}
			}
		}
		return calc > 0 ? 1 + rand.nextInt(calc) : 0;
	}
//	public Item setNoRepair()
//	{
//		canRepair = false;
//		return this;
//	}
//	@Override
//	public void addCreativeItems(ArrayList itemList)
//	{
//		itemList.add(new ItemStack(this, 1));
//	}

}