package net.minecraft.src.EnchantChanger;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.World;

public class EcContainerCloudSword extends Container{
	public IInventory CloudSwordInv;
	public ItemStack CloudSword;
	protected InventoryPlayer InvPlayer;
	private World worldPointer;
    private ItemStack CloudContents[];
    //public EcContainerCloudSword (World par1world, InventoryPlayer inventoryPlayer, IInventory Inv, ItemStack itemstack)
    public EcContainerCloudSword (World par1world, InventoryPlayer inventoryPlayer,ItemStack itemstack)
	{
		CloudSwordInv = new EcInventoryCloudSword(itemstack);
        InvPlayer = inventoryPlayer;
        worldPointer = par1world;
        CloudSword = itemstack;
        addSlot(new Slot(CloudSwordInv, 0, 35, 17));
        addSlot(new Slot(CloudSwordInv, 1, 125, 17));
        addSlot(new Slot(CloudSwordInv, 2, 35, 52));
        addSlot(new Slot(CloudSwordInv, 3, 125, 52));
        addSlot(new Slot(CloudSwordInv, 4, 80, 37));
        //commonly used vanilla code that adds the player's inventory
        bindPlayerInventory(inventoryPlayer);
	 }
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		ItemStack itemstack = var1.getCurrentEquippedItem();
        if (itemstack != CloudSword) {
            return false;
        }
        return true;
	}
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlot(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
	}
	public ItemStack transferStackInSlot(int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();


            if (!this.mergeItemStack(var4, 1, 37, true))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }

}