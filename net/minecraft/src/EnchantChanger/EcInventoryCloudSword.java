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

    public void load() // ���g��ǂݍ���
    {
        // ItemStack��NBT���擾�A��̒��g���쐬���Ă���
        NBTTagCompound nbttagcompound = CloudSword.getTagCompound();
        CloudContents = new ItemStack[getSizeInventory()];

        // NBT��������Β��g�͋�̂܂܂�
        if (nbttagcompound == null) {
            return;
        }

        // TileEntityChest��readFromNBT�̏������قڂ��̂܂܎���
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < CloudContents.length) {
                CloudContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void save() // ���g��ۑ�
    {
        // TileEntityChest��WriteToNBT�̏������قڂ��̂܂܎���
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < CloudContents.length; i++) {
            if (CloudContents[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                CloudContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        // ItemStack��NBT�ɒ��g��ǋL
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
        // �C���x���g���ɕω�������Εۑ�
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
        save(); // �����ŕۑ����Ȃ��Ƌ��������������Ȃ�
    }

    // �ȍ~�͂ق�TileEntityChest����K�v�ȏ����𔲂��o�������� //

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