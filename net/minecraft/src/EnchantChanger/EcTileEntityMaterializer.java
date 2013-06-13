package net.minecraft.src.EnchantChanger;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
public class EcTileEntityMaterializer extends TileEntity implements IInventory {

    private ItemStack[] materializerItemstacks;
    public EcTileEntityMaterializer()
    {
    	materializerItemstacks  = new ItemStack[7];
    }
    public int BoolToInt(boolean par1)
    {
    	return (par1) ? 1:0;
    }
    @Override
    public int getSizeInventory()
    {
            return materializerItemstacks.length;
    }


    @Override
    public ItemStack getStackInSlot(int slot)
    {
            return materializerItemstacks[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
    	materializerItemstacks[slot] = stack;
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
    /**
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.materializerItemstacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.materializerItemstacks.length)
            {
                this.materializerItemstacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
	*/
    /**
     * Writes a tile entity to NBT.
     */
    /**
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.materializerItemstacks.length; ++var3)
        {
            if (this.materializerItemstacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.materializerItemstacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }
	*/
	@Override
	public String getInvName() {
		return "container.materializer";
	}



}