package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AchievementList;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Potion;
import net.minecraft.src.StatList;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.ForgeHooks;
import net.minecraft.src.forge.IItemRenderer;
public class EcItemCloudSwordCore extends EcItemSword //implements IItemRenderer
{
	public static final EcModelCloudSwordCore2 CCModel = new EcModelCloudSwordCore2();
	public static boolean ActiveMode=false;
	public static int InactiveIcon = 17;
	public static int ActiveIcon = 18;
	public static Entity Attackentity = null;
	public ItemStack[] swords = new ItemStack[5];
	private EcCloudSwordData SwordData;
	private int mode = 0;
	private Random rand = new Random();

	public EcItemCloudSwordCore(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
//		this.setMaxDamage(-1);
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
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
//		CCModel.renderItem(item, (EntityLiving)data[1], this.ActiveMode);
//	}

	public int getDamageVsEntity(Entity par1Entity)
	{
		return (ActiveMode)? 7: 6;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking())
		{
			setIconIndex((ActiveMode)?InactiveIcon : ActiveIcon);
			ActiveMode=!ActiveMode;
			return par1ItemStack;
		}
		else
		{
			if(!ActiveMode)
			{
				if(canUnion2(par3EntityPlayer))
				{
					UnionSword2(par3EntityPlayer);
					EcCloudSwordData data = this.getSwordData(par1ItemStack, par2World);
					makeSwordData(data, swords);
					return this.makeCloudSword(par1ItemStack);
				}
				else
				{
					super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
					par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
					return par1ItemStack;
				}
			}
			else
			{
				super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				return par1ItemStack;
			}
		}
	}
	public ItemStack makeCloudSword(ItemStack stack)
	{
    	int EnchNum;
    	int EnchLv;
    	ItemStack ChangeSword = new ItemStack(mod_EnchantChanger.ItemCloudSword, 1);
		NBTTagList enchOnItem = stack.getEnchantmentTagList();
		if(enchOnItem !=null)
		{
			for (int i = 0; i < enchOnItem.tagCount(); ++i)
			{
				if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
				{
					EnchNum = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id");
					EnchLv = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl");
					ChangeSword.addEnchantment(Enchantment.enchantmentsList[EnchNum], EnchLv);
				}
			}
		}
		return ChangeSword;
	}
//	public Item setNoRepair()
//	{
//		canRepair = false;
//		return this;
//	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		Attackentity = entity;
		if(ActiveMode)
			this.attackTargetEntityWithInventoryItem(entity, player);
		return false;
	}
	public boolean canUnion2(EntityPlayer player)
	{
		int Index = 0;
		int CurrentSlot = player.inventory.currentItem;
		ItemStack sword;
		for(int i = 0; i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword)
			{
				Index++;
			}
		}
		return Index >= 5;
	}
	public boolean canUnion(EntityPlayer par1)
	{
		int DiamondSwordNum=0;
		int GoldSwordNum=0;
		int SteelSwordNum=0;
		for (int i = 0;i<9;i++)
		{
			if(par1.inventory.getStackInSlot(i)!=null)
			{
				if(par1.inventory.getStackInSlot(i).getItem()==Item.swordDiamond)
					DiamondSwordNum++;
				if(par1.inventory.getStackInSlot(i).getItem()==Item.swordGold)
					GoldSwordNum++;
				if(par1.inventory.getStackInSlot(i).getItem()==Item.swordSteel)
					SteelSwordNum++;
			}
		}
		if(DiamondSwordNum>1&&GoldSwordNum>1&&SteelSwordNum>0)
			return true;
		else
			return false;
	}
	public static int SwordNuminInventoryslot(EntityPlayer par1)
	{
		int Sword=0;
		for (int i = 0;i<9;i++)
		{
			ItemStack SlotItem=par1.inventory.getStackInSlot(i);
			if(SlotItem!=null)
				if(SlotItem.getItem()==Item.swordDiamond||SlotItem.getItem()==Item.swordGold||SlotItem.getItem()==Item.swordSteel)
					Sword++;
		}
		return Sword;
	}
	public boolean checkmode(int mode)
	{
		return mode != 0;
	}
	public void attackTargetEntityWithInventoryItem(Entity par1Entity, EntityPlayer player)
	{
		ItemStack sword;
		int CurrentSlot = player.inventory.currentItem;
		for(int i=0;i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword && !(sword.getItem() instanceof EcItemSword))
			{
				this.attackTargetEntityWithTheItem(par1Entity, player, sword);
			}
		}
	}
	public void UnionSword2(EntityPlayer player)
	{
		int Index = 0;
		int CurrentSlot = player.inventory.currentItem;
		ItemStack sword;
		for(int i = 0; i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword && !(sword.getItem() instanceof EcItemSword) && Index < 5)
			{
				this.swords[Index] = sword;
				this.swords[Index].setTagCompound(sword.getTagCompound());
				player.inventory.setInventorySlotContents(i, null);
				Index++;
			}
		}
	}
	public void UnionSword(EntityPlayer entityplayer)
	{
		int DS=2;
		int GS=2;
		int IS=1;
		int Index = 0;
		ItemStack sword;
		for (int i = 0;i<9;i++)
		{
			sword = entityplayer.inventory.getStackInSlot(i);
			if(sword!=null)
			{
				if(sword.getItem()==Item.swordDiamond)
				{
					DS--;
					if(DS>=0)
					{
						this.swords[Index] = sword;
						Index++;
						entityplayer.inventory.setInventorySlotContents(i, null);
					}
				}
				else if(sword.getItem()==Item.swordGold)
				{
					GS--;
					if(GS>=0)
					{
						this.swords[Index] = sword;
						Index++;
						entityplayer.inventory.setInventorySlotContents(i, null);
					}
				}
				else if(sword.getItem()==Item.swordSteel)
				{
					IS--;
					if(IS>=0)
					{
						this.swords[Index] = sword;
						Index++;
						entityplayer.inventory.setInventorySlotContents(i, null);
					}
				}
			}
		}
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
			int var2 = player.inventory.getDamageVsEntity(par1Entity);

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
					par1Entity.heartsLife = 0;
					if (var7.stackSize <= 0)
					{
						var7.onItemDestroyedByUse(player);
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
	public void makeSwordData(EcCloudSwordData data, ItemStack[] items)
	{
		for(int i=0;i<5;i++)
		{
			data.setInventorySlotContents(i, items[i]);
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
//	@Override
//	public void addCreativeItems(ArrayList itemList)
//	{
//		itemList.add(new ItemStack(this, 1));
//	}
}