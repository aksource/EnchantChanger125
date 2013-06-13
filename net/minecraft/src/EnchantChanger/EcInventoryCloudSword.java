package net.minecraft.src.EnchantChanger;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class EcInventoryCloudSword implements IInventory {
    private ItemStack CloudSword;
    private ItemStack CloudContents[];

    public EcInventoryCloudSword(ItemStack itemstack)
    {
        CloudSword = itemstack;
    }

    public void load() // 中身を読み込む
    {
        // ItemStackのNBTを取得、空の中身を作成しておく
        NBTTagCompound nbttagcompound = CloudSword.getTagCompound();
        CloudContents = new ItemStack[getSizeInventory()];

        // NBTが無ければ中身は空のままで
        if (nbttagcompound == null) {
            return;
        }

        // TileEntityChestのreadFromNBTの処理をほぼそのまま実装
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < CloudContents.length) {
                CloudContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void save() // 中身を保存
    {
        // TileEntityChestのWriteToNBTの処理をほぼそのまま実装
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < CloudContents.length; i++) {
            if (CloudContents[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                CloudContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        // ItemStackのNBTに中身を追記
        NBTTagCompound nbttagcompound = CloudSword.getTagCompound();
        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
        }
        nbttagcompound.setTag("Items", nbttaglist);
        CloudSword.setTagCompound(nbttagcompound);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != CloudSword) {
            return false;
        }
        return true;
    }

    @Override
    public void onInventoryChanged()
    {
        // インベントリに変化があれば保存
        save();
    }

    @Override
    public void openChest()
    {
        load();
    }

    @Override
    public void closeChest()
    {
        save(); // ここで保存しないと挙動がおかしくなる
    }

    // 以降はほぼTileEntityChestから必要な処理を抜き出しただけ //

    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return CloudContents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if (CloudContents[i] != null) {
            if (CloudContents[i].stackSize <= j) {
                ItemStack itemstack = CloudContents[i];
                CloudContents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = CloudContents[i].splitStack(j);
            if (CloudContents[i].stackSize == 0) {
                CloudContents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        CloudContents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    @Override
    public String getInvName()
    {
        return "Union Sword";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

}