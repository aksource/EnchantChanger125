package net.minecraft.src.MultiToolHolders;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerToolHolder extends Container
{
	private IInventory holderInventory;
	private int HolderNum;

	public ContainerToolHolder(InventoryPlayer inventoryPlayer, IInventory par2IInventory, int num)
	{
		this.holderInventory = par2IInventory;
		this.HolderNum = num;
		par2IInventory.openChest();
		for (int k = 0; k < HolderNum; ++k)
		{
			this.addSlot(new SlotToolHolder(par2IInventory, k, 8 + k * 18, 18));
		}
		bindPlayerInventory(inventoryPlayer);
	}
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlot(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 50 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 108));
		}
	}
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return par1EntityPlayer.inventory.getCurrentItem() != null && par1EntityPlayer.inventory.getCurrentItem().getItem() instanceof ItemMultiToolHolder;
	}
	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{
	}
	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(int par1)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par1);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par1 < this.HolderNum)
			{
				if (!this.mergeItemStack(itemstack1, this.HolderNum, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(itemstack1.getItem() instanceof ItemMultiToolHolder || itemstack1.isStackable())
				return null;
			else if (!this.mergeItemStack(itemstack1, 0, this.HolderNum, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		this.holderInventory.closeChest();
	}
}
