package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;


public class EcContainerHugeMateria extends Container {

//	public IInventory materializeSource = new EcSlotMaterializer(this, "MaterializerSource", 9);
//	public IInventory materializeResult = new EcSlotResult(this, "MaterializerResult", 9);
	public int[] vanillaEnch = new int[]{0,1,2,3,4,5,6,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	public static int ResultSlotNum = 9;
	public static int SourceSlotNum = 9;
	protected EcTileEntityHugeMateria tileEntity;
	protected InventoryPlayer InvPlayer;
//	protected int materiamax = mod_EnchantChanger.materiamax ;
	private ArrayList<Integer> ItemEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> ItemEnchLvList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchList = new ArrayList<Integer>();
	private int maxlv = mod_EnchantChanger.MaxLv;
	private boolean Debug = mod_EnchantChanger.Debug;
	private World worldPointer;
	private Minecraft mc = mod_EnchantChanger.mc;
	private boolean materiadecLv = mod_EnchantChanger.DecMateriaLv;

	public EcContainerHugeMateria (World par1world, InventoryPlayer inventoryPlayer, EcTileEntityHugeMateria te){
		tileEntity = te;
		InvPlayer = inventoryPlayer;
		worldPointer = par1world;
		addSlot( new Slot(te, 0,26,17));
		addSlot( new Slot(te, 1,26,48));
		addSlot( new Slot(te, 2,53,48));
//		addSlot( new Slot(te, 3,98,6));
//		addSlot( new Slot(te, 4,134,6));
		addSlot( new Slot(te, 3,80,34));
//		addSlot( new Slot(te, 6,152,34));
//		addSlot( new Slot(te, 7,98,63));
//		addSlot( new Slot(te, 8,134,63));
		addSlot( new EcSlotMakeMateria(te, 4,116,34));
		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
//		this.onCraftMatrixChanged(this.materializeSource);
	}


	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
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
	@Override
	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{

	}
	public ItemStack transferStackInSlot(int par1)
	{
		ItemStack retitem = null;
		Slot slot = (Slot)this.inventorySlots.get(par1);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack = slot.getStack();
			retitem = itemstack.copy();

			if (par1 >=0 && par1 < 5)
			{
				if (!this.mergeItemStack(itemstack, 5, 5 + 36, true))
				{
					return null;
				}
			}
						else
			{
				if(itemstack.getItem() instanceof EcItemMasterMateria)
				{
					if (!this.mergeItemStack(itemstack, 0, 1, false))
					{
						return null;
					}
				}
				else if(itemstack.getItem() instanceof EcItemMateria && itemstack.getItemDamage() ==0)
				{
					if (!this.mergeItemStack(itemstack, 1, 2, false))
					{
						return null;
					}
				}
				else if(itemstack.getItem() instanceof Item && itemstack.getItem().shiftedIndex == Item.diamond.shiftedIndex)
				{
					if (!this.mergeItemStack(itemstack, 2, 3, false))
					{
						return null;
					}
				}
				else
				{
					if (!this.mergeItemStack(itemstack, 3, 4, false))
					{
						return null;
					}
				}
			}

			if (itemstack.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack.stackSize == retitem.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(itemstack);
		}

		return retitem;

		//return super.transferStackInSlot(par1EntityPlayer, par2);
	}
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	 @Override
	 public void onCraftMatrixChanged(IInventory par1IInventory)
	{

	}
	 /**
	  * Callback for when the crafting gui is closed.
	  */
	 @Override
	 public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	 {
		 super.onCraftGuiClosed(par1EntityPlayer);

	 }
}