package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_EnchantChanger;
public class EcTileEntityHugeMateria extends TileEntity implements IInventory {
	private static int[][] EnchArray;
	private static ItemStack[] MaterialArray;
	private static ArrayList<Integer> magicArray;
	private ItemStack result = null;
	private ItemStack[] Hugeitemstacks = new ItemStack[5];
	public int MaterializingTime = 0;
	public float angle = 0;
	public int BoolToInt(boolean par1)
	{
		return (par1) ? 1:0;
	}
	@Override
	public int getSizeInventory()
	{
		return Hugeitemstacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return Hugeitemstacks[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		Hugeitemstacks[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}
	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}


	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	/**
	 * Reads a tile entity from NBT.
	 */
	 public int getMaterializingProgressScaled(int par1)
	{
		return this.MaterializingTime * par1/200;
	}
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.Hugeitemstacks = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.Hugeitemstacks.length)
			{
				this.Hugeitemstacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		this.MaterializingTime = par1NBTTagCompound.getShort("MaterializingTime");
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("MaterializingTime", (short)this.MaterializingTime);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.Hugeitemstacks.length; ++var3)
		{
			if (this.Hugeitemstacks[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.Hugeitemstacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}
	public void updateEntity()
	{
		//回転させようとしたけど、面倒だった。
//		if(this.angle >360F)
//		{
//			this.angle = 0;
//		}
//		else
//		{
//			this.angle +=1.0F;
//		}
		boolean var2 = false;

		if (!this.worldObj.isRemote)
		{
			if (this.canMake())
			{
				++this.MaterializingTime;

				if (this.MaterializingTime == 200)
				{
					this.MaterializingTime = 0;
					this.makeMateria();
					var2 = true;
				}
			}
			else
			{
				this.MaterializingTime = 0;
			}
		}

		if (var2)
		{
			this.onInventoryChanged();
		}
	}
	@Override
	public String getInvName() {
		return "container.hugeMateria";
	}
	public boolean isMaterializing()
	{
		return this.MaterializingTime >0;
	}
	public boolean canMake()
	{
		ItemStack hMateria = this.getStackInSlot(0);
		ItemStack base = this.getStackInSlot(1);
		ItemStack diamond = this.getStackInSlot(2);
		ItemStack material = this.getStackInSlot(3);
		ItemStack resultItem = this.getStackInSlot(4);
		int lvPlus = 0;
		if(diamond != null && diamond.itemID == Item.diamond.shiftedIndex)
			lvPlus = 9;
		if(base == null || !(base .getItem() instanceof EcItemMateria) || resultItem != null || material == null || (diamond!= null && diamond.itemID != Item.diamond.shiftedIndex))
			return false;
		else
		{
			if(hMateria != null && hMateria.getItem() instanceof EcItemMasterMateria)
			{
				int dmg = hMateria.getItemDamage();
				if(dmg >=0 && dmg < 6)
					return makeResult(material, dmg, lvPlus);
				else
					return false;
			}
			return materiaLvUp(material,lvPlus);
		}
	}
	private boolean makeResult(ItemStack material, int dmg, int lvPlus)
	{
		for(int i= 0;i<EnchArray[dmg].length;i++)
		{
			if(EnchArray[dmg][i] != -1 && MaterialArray[EnchArray[dmg][i]].isItemEqual(material))
			{
				if(magicArray.contains(EnchArray[dmg][i]))
					result = new ItemStack(mod_EnchantChanger.ItemMat,1,magicArray.indexOf(EnchArray[dmg][i]) + 1);
				else
					result = new ItemStack(mod_EnchantChanger.ItemMat,1,0);
				if(EnchArray[dmg][i] >= 0 && EnchArray[dmg][i] < Enchantment.enchantmentsList.length){
					int lv;
					if(Enchantment.enchantmentsList[EnchArray[dmg][i]].getMaxLevel() == 1)
						lv = 1;
					else
						lv = 1 + lvPlus;
					mod_EnchantChanger.addEnchantmentToItem(result, Enchantment.enchantmentsList[EnchArray[dmg][i]], lv);
				}
				return true;
			}
		}
		return false;
	}
	private boolean materiaLvUp(ItemStack materia, int lvPlus)
	{
		if(materia.getItem() instanceof EcItemMateria && materia.getItemDamage() > 0 && materia.isItemEnchanted())
		{
			result = materia.copy();
			NBTTagCompound nbt = result.getTagCompound();
			mod_EnchantChanger.removeEnchTag(nbt, "ench");
			mod_EnchantChanger.addEnchantmentToItem(materia, Enchantment.enchantmentsList[mod_EnchantChanger.getMateriaEnchKind(materia)], mod_EnchantChanger.getMateriaEnchLv(materia) + 1 + lvPlus);
			return true;
		}
		else
			return false;
	}
	public boolean makeMateria()
	{
		for(int i=1;i < 4;i++)
		{
			if(this.getStackInSlot(i) != null)
				this.decrStackSize(i, 1);
		}
		this.setInventorySlotContents(4, result);
		return true;
	}
	static{
		EnchArray = new int[EcItemMasterMateria.MasterMateriaNum - 1][10];
		MaterialArray = new ItemStack[Enchantment.enchantmentsList.length + 3];
		magicArray = new ArrayList<Integer>(EcItemMateria.MagicMateriaNum);
		int i,j;
		for(i = 0;i<EnchArray.length;i++){
			for(j = 0;j<EnchArray[i].length;j++){
				EnchArray[i][j] = -1;
			}
		}
		for(i=0;i<256 + 3;i++){
			MaterialArray[i]=null;
		}
		EnchArray[0][0] = mod_EnchantChanger.EnchantmentMeteoId;
		EnchArray[0][1] = mod_EnchantChanger.EndhantmentHolyId;
		EnchArray[0][2] = mod_EnchantChanger.EnchantmentTelepoId;
		EnchArray[0][3] = mod_EnchantChanger.EnchantmentFloatId;
		EnchArray[0][4] = mod_EnchantChanger.EnchantmentThunderId;
		EnchArray[0][5] = Enchantment.enchantmentsList.length;
		EnchArray[0][6] = Enchantment.enchantmentsList.length + 1;
		EnchArray[0][7] = Enchantment.enchantmentsList.length + 2;
		EnchArray[1][0] = Enchantment.protection.effectId;
		EnchArray[1][1] = Enchantment.fireProtection.effectId;
		EnchArray[1][2] = Enchantment.featherFalling.effectId;
		EnchArray[1][3] = Enchantment.blastProtection.effectId;
		EnchArray[1][4] = Enchantment.projectileProtection.effectId;
//		EnchArray[1][5] = Enchantment.thorns.effectId;
		EnchArray[2][0] = Enchantment.respiration.effectId;
		EnchArray[2][1] = Enchantment.aquaAffinity.effectId;
		EnchArray[3][0] = Enchantment.sharpness.effectId;
		EnchArray[3][1] = Enchantment.smite.effectId;
		EnchArray[3][2] = Enchantment.baneOfArthropods.effectId;
		EnchArray[3][3] = Enchantment.knockback.effectId;
		EnchArray[3][4] = Enchantment.fireAspect.effectId;
		EnchArray[3][5] = Enchantment.looting.effectId;
		EnchArray[4][0] = Enchantment.efficiency.effectId;
		EnchArray[4][1] = Enchantment.silkTouch.effectId;
		EnchArray[4][2] = Enchantment.unbreaking.effectId;
		EnchArray[4][3] = Enchantment.fortune.effectId;
		EnchArray[5][0] = Enchantment.power.effectId;
		EnchArray[5][1] = Enchantment.punch.effectId;
		EnchArray[5][2] = Enchantment.flame.effectId;
		EnchArray[5][3] = Enchantment.infinity.effectId;
		MaterialArray[0] = new ItemStack(Item.ingotIron);
		MaterialArray[1] = new ItemStack(Item.blazePowder);
		MaterialArray[2] = new ItemStack(Item.feather);
		MaterialArray[3] = new ItemStack(Item.gunpowder);
		MaterialArray[4] = new ItemStack(Item.arrow);
		MaterialArray[5] = new ItemStack(Item.reed);
		MaterialArray[6] = new ItemStack(Item.pickaxeGold);
		MaterialArray[7] = new ItemStack(Block.cactus);
		MaterialArray[16] = new ItemStack(Item.fireballCharge);
		MaterialArray[17] = new ItemStack(Item.flintAndSteel);
		MaterialArray[18] = new ItemStack(Item.spiderEye);
		MaterialArray[19] = new ItemStack(Item.slimeBall);
		MaterialArray[20] = new ItemStack(Item.blazeRod);
		MaterialArray[21] = new ItemStack(Item.appleGold);
		MaterialArray[32] = new ItemStack(Item.pickaxeGold);
		MaterialArray[33] = new ItemStack(Item.silk);
		MaterialArray[34] = new ItemStack(Item.ingotIron);
		MaterialArray[35] = new ItemStack(Item.appleGold);
		MaterialArray[48] = new ItemStack(Item.fireballCharge);
		MaterialArray[49] = new ItemStack(Item.slimeBall);
		MaterialArray[50] = new ItemStack(Item.blazeRod);
		MaterialArray[51] = new ItemStack(Item.bow);
		MaterialArray[mod_EnchantChanger.EnchantmentMeteoId] = new ItemStack(Block.dragonEgg);
		MaterialArray[mod_EnchantChanger.EndhantmentHolyId] = new ItemStack(Item.appleGold, 1, 1);
		MaterialArray[mod_EnchantChanger.EnchantmentTelepoId] = new ItemStack(Item.enderPearl);
		MaterialArray[mod_EnchantChanger.EnchantmentFloatId] = new ItemStack(Item.eyeOfEnder);
		MaterialArray[mod_EnchantChanger.EnchantmentThunderId] = new ItemStack(Block.blockGold);
		MaterialArray[Enchantment.enchantmentsList.length] = new ItemStack(Item.bucketMilk);
		MaterialArray[Enchantment.enchantmentsList.length + 1] = new ItemStack(Item.bootsGold);
		MaterialArray[Enchantment.enchantmentsList.length + 2] = new ItemStack(Item.netherStalkSeeds);
		magicArray.add(mod_EnchantChanger.EnchantmentMeteoId);
		magicArray.add(mod_EnchantChanger.EndhantmentHolyId);
		magicArray.add(mod_EnchantChanger.EnchantmentTelepoId);
		magicArray.add(mod_EnchantChanger.EnchantmentFloatId);
		magicArray.add(mod_EnchantChanger.EnchantmentThunderId);
		magicArray.add(Enchantment.enchantmentsList.length);
		magicArray.add(Enchantment.enchantmentsList.length + 1);
		magicArray.add(Enchantment.enchantmentsList.length + 2);
	}
}