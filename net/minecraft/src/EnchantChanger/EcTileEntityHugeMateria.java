package net.minecraft.src.EnchantChanger;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_EnchantChanger;
public class EcTileEntityHugeMateria extends TileEntity implements IInventory {

	private ItemStack[] Hugeitemstacks = new ItemStack[5];
	public int MaterializingTime = 0;
	public float angle = 0;
	public EcTileEntityHugeMateria()
	{
	}
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

	/**
	 * Writes a tile entity to NBT.
	 */

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
		if(this.angle >360F)
		{
			this.angle = 0;
		}
		else
		{
			this.angle +=1.0F;
		}
		boolean var2 = false;

		if (!this.worldObj.isRemote)
		{
			if (this.canMakeMateria())
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
	public boolean canMakeMateria()
	{
		if(this.Hugeitemstacks[1] == null || (this.Hugeitemstacks[1] != null && !(this.Hugeitemstacks[1].getItem() instanceof EcItemMateria)))
			return false;
		else if(this.Hugeitemstacks[4] != null)
		{
			return false;
		}
		else if(this.Hugeitemstacks[3] != null && this.Hugeitemstacks[3].getItem() instanceof EcItemMateria)
		{
			return true;
		}
		else if(this.Hugeitemstacks[0] != null && this.Hugeitemstacks[0].getItem() instanceof EcItemMasterMateria)
		{
			if(this.Hugeitemstacks[0].getItemDamage() == 0)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Block.dragonEgg.blockID
						|| (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex && this.Hugeitemstacks[3].getItemDamage() == 1)
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.enderPearl.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.eyeOfEnder.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Block.blockGold.blockID
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bucketMilk.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bootsGold.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.netherStalkSeeds.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else if(this.Hugeitemstacks[0].getItemDamage() == 1)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.ingotIron.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazePowder.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.feather.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.gunpowder.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.arrow.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else if(this.Hugeitemstacks[0].getItemDamage() == 2)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.reed.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.pickaxeGold.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else if(this.Hugeitemstacks[0].getItemDamage() == 3)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.fireballCharge.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.flintAndSteel.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.spiderEye.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.slimeBall.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazeRod.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else if(this.Hugeitemstacks[0].getItemDamage() == 4)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.pickaxeGold.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.silk.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.ingotIron.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else if(this.Hugeitemstacks[0].getItemDamage() == 5)
			{
				if(this.Hugeitemstacks[3] != null && (this.Hugeitemstacks[3].getItem().shiftedIndex == Item.fireballCharge.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.slimeBall.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazeRod.shiftedIndex
						|| this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bow.shiftedIndex))
				{
					return true;
				}
				else
					return false;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public void makeMateria()
	{
		int var1 = 0;
		if(this.canMakeMateria())
		{
			if(this.Hugeitemstacks[2] != null && this.Hugeitemstacks[2].getItem().shiftedIndex == Item.diamond.shiftedIndex)
			{
				var1 = 9;
				this.Hugeitemstacks[2].stackSize--;
				if(this.Hugeitemstacks[2].stackSize <=0)
					this.Hugeitemstacks[2] = null;
			}
			if(this.Hugeitemstacks[0] != null)
			{
				if(this.Hugeitemstacks[0].getItemDamage() == 0)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Block.dragonEgg.blockID)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 21 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex && this.Hugeitemstacks[3].getItemDamage() == 1)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 22 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.enderPearl.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 23 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.eyeOfEnder.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 24 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Block.blockGold.blockID)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 25 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bucketMilk.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 26 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bootsGold.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 27 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.netherStalkSeeds.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1  + 28 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
				else if(this.Hugeitemstacks[0].getItemDamage() == 1)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.ingotIron.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 0 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazePowder.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 1 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.feather.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 2 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.gunpowder.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 3 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.arrow.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 4 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
				else if(this.Hugeitemstacks[0].getItemDamage() == 2)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.reed.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 5 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.pickaxeGold.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 6 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
				else if(this.Hugeitemstacks[0].getItemDamage() == 3)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.fireballCharge.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 7 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.flintAndSteel.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 8 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.spiderEye.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 9 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.slimeBall.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 10 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazeRod.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 11 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 12 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
				else if(this.Hugeitemstacks[0].getItemDamage() == 4)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.pickaxeGold.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 13 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.silk.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 14 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.ingotIron.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 15 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.appleGold.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 16 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
				else if(this.Hugeitemstacks[0].getItemDamage() == 5)
				{
					if(this.Hugeitemstacks[3] != null)
					{
						if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.fireballCharge.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 17 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.slimeBall.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 18 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.blazeRod.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 19 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == Item.bow.shiftedIndex)
						{
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, 1 + var1 + 20 * mod_EnchantChanger.MaxLv);
						}
						else if(this.Hugeitemstacks[3].getItem().shiftedIndex == mod_EnchantChanger.MateriaID)
						{
							int dmg = (this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + 1 + var1 > mod_EnchantChanger.MaxLv)? this.Hugeitemstacks[3].getItemDamage() -  this.Hugeitemstacks[3].getItemDamage() % mod_EnchantChanger.MaxLv + mod_EnchantChanger.MaxLv:this.Hugeitemstacks[3].getItemDamage() + 1 +var1;
							this.Hugeitemstacks[4] = new ItemStack(mod_EnchantChanger.MateriaID, 1, dmg);
						}
					}
				}
			}
			if(this.Hugeitemstacks[1] != null)
			{
				this.Hugeitemstacks[1].stackSize--;
				if(this.Hugeitemstacks[1].stackSize <=0)
					this.Hugeitemstacks[1] = null;
			}
			if(this.Hugeitemstacks[3] != null)
			{
				this.Hugeitemstacks[3].stackSize--;
				if(this.Hugeitemstacks[3].stackSize <=0)
					this.Hugeitemstacks[3] = null;
			}
		}
	}

}