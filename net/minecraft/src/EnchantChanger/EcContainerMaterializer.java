package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Container;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentDamage;
import net.minecraft.src.EnchantmentDigging;
import net.minecraft.src.EnchantmentDurability;
import net.minecraft.src.EnchantmentFireAspect;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.EnchantmentLootBonus;
import net.minecraft.src.EnchantmentUntouching;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemTool;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Slot;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.MultiToolHolders.ItemMultiToolHolder;


public class EcContainerMaterializer extends Container {

	public IInventory materializeSource = new EcSlotMaterializer(this, "MaterializerSource", 9);
	public IInventory materializeResult = new EcSlotResult(this, "MaterializerResult", 9);
	public static int ResultSlotNum = 9;
	public static int SourceSlotNum = 9;
	protected EcTileEntityMaterializer tileEntity;
	protected InventoryPlayer InvPlayer;
	private ArrayList<Integer> ItemEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> ItemEnchLvList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchLvList = new ArrayList<Integer>();
	private boolean Debug = mod_EnchantChanger.Debug;
	private World worldPointer;
	private Minecraft mc = mod_EnchantChanger.mc;
	private boolean materiadecLv = mod_EnchantChanger.DecMateriaLv;

	//public EcContainerMaterializer (World par1world, InventoryPlayer inventoryPlayer, EcTileEntityMaterializer te){
	public EcContainerMaterializer (World par1world, InventoryPlayer inventoryPlayer){
		//tileEntity = te;
		InvPlayer = inventoryPlayer;
		worldPointer = par1world;
		//the Slot constructor takes the IInventory and the slot number in that it binds to
		//and the x-y coordinates it resides on-screen
		addSlot(new EcSlotItemToEnchant(this, this.materializeResult, this.materializeSource, 0, 35, 17));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 1, 8, 36));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 2, 26, 36));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 3, 44, 36));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 4, 62, 36));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 5, 8, 54));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 6, 26, 54));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 7, 44, 54));
		addSlot(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 8, 62, 54));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 0, 125, 17));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 1, 98, 36));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 2, 116, 36));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 3, 134, 36));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 4, 152, 36));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 5, 98, 54));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 6, 116, 54));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 7, 134, 54));
		addSlot(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 8, 152, 54));
		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
		this.onCraftMatrixChanged(this.materializeSource);
	}


	@Override
	public boolean canInteractWith(EntityPlayer player) {
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

			if (par1 >=0 && par1 < ResultSlotNum + SourceSlotNum)
			{
				if (!this.mergeItemStack(itemstack, ResultSlotNum + SourceSlotNum, ResultSlotNum + SourceSlotNum + 36, true))
				{
					return null;
				}
			}
			else
			{
				if(itemstack.getItem() instanceof EcItemMateria)
				{
					for(int i=1;i<this.SourceSlotNum;i++)
					{
						if(!((Slot)this.inventorySlots.get(i)).getHasStack())
						{
							((Slot)this.inventorySlots.get(i)).putStack(itemstack.copy());
							itemstack.stackSize--;
							i=this.SourceSlotNum;
						}
					}
				}
				else if (((Slot)this.inventorySlots.get(0)).getHasStack() || !(itemstack.getItem() instanceof Item))
				{
					return null;
				}
				else if (itemstack.hasTagCompound() && itemstack.stackSize == 1)
				{
					((Slot)this.inventorySlots.get(0)).putStack(itemstack.copy());
					itemstack.stackSize = 0;
				}
				else if (itemstack.stackSize >= 1)
				{
					((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
					--itemstack.stackSize;
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
		ItemStack enchitem = this.materializeSource.getStackInSlot(0);
		if (enchitem != null)
		{
			if(! (enchitem.getItem() instanceof Item) || (mod_EnchantChanger.loadMTH && enchitem.getItem() instanceof ItemMultiToolHolder))
			{
				return;
			}
			NBTTagList enchOnItem = enchitem.getEnchantmentTagList();
			int itemdmg = enchitem.getItemDamage();
			float dmgratio = (enchitem.getMaxDamage() == 0)? 1: (enchitem.getMaxDamage() - itemdmg) / enchitem.getMaxDamage();
			int itemID = enchitem.itemID;
			ItemStack Result = enchitem.copy();
			if(Result.hasTagCompound())
			{
				mod_EnchantChanger.removeEnchTag(Result.getTagCompound(), "ench");
				mod_EnchantChanger.removeEnchTag(Result.getTagCompound(), "ApList");
			}
			if(enchOnItem != null)
			{
				for (int i = 0; i < enchOnItem.tagCount(); ++i)
				{
					if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
					{
						 ((NBTTagCompound)enchOnItem.tagAt(i)).setInteger("ap", 0);
						this.ItemEnchList.add((int) ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id"));
						this.ItemEnchLvList.add((int) ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl"));
						if(i >=8)
						{
							Result.addEnchantment(Enchantment.enchantmentsList[this.ItemEnchList.get(i)], this.ItemEnchLvList.get(i));
						}
					}
				}
			}
			if(this.checkMateriafromSlot(materializeSource))
			{
				for(int i=1;i<this.materializeSource.getSizeInventory();i++)
				{
					ItemStack materiaitem = this.materializeSource.getStackInSlot(i);
					if(materiaitem == null)
					{
						continue;
					}
					int enchLv = this.EnchLv(materiaitem);
					Enchantment enchKind = this.EnchKind(materiaitem);

					if(!this.func_92037_a(enchKind, enchitem) || !this.CheckLvCap(materiaitem))
					{
						for(int i1= 0 ; i1< this.ResultSlotNum;i1++)
						{
							this.materializeResult.setInventorySlotContents(i1,null);
						}
						this.ItemEnchList.clear();
						this.ItemEnchLvList.clear();
						this.MateriaEnchList.clear();
						this.MateriaEnchLvList.clear();
						return;
					}
					if (this.ItemEnchList.size() >0)
					{
						boolean flag = false;
						for(int i2=0;i2 < this.ItemEnchList.size();i2++)
						{
							if(!Enchantment.enchantmentsList[this.ItemEnchList.get(i2)].canApplyTogether(enchKind))
							{
								this.ItemEnchList.remove(i2);
								this.ItemEnchLvList.remove(i2);
							}
						}
					}
					if(! this.MateriaEnchList.contains(enchKind.effectId))
					{
						this.MateriaEnchList.add(enchKind.effectId);
						this.MateriaEnchLvList.add(enchLv);
					}
				}
				for(int i2=0;i2 < this.ItemEnchList.size();i2++)
				{
					mod_EnchantChanger.addEnchantmentToItem(Result, Enchantment.enchantmentsList[this.ItemEnchList.get(i2)], this.ItemEnchLvList.get(i2));
				}
				for(int i2=0;i2 < this.MateriaEnchList.size();i2++)
				{
					mod_EnchantChanger.addEnchantmentToItem(Result, Enchantment.enchantmentsList[this.MateriaEnchList.get(i2)], this.MateriaEnchLvList.get(i2));
				}
				this.materializeResult.setInventorySlotContents(0, Result);
				this.ItemEnchList.clear();
				this.ItemEnchLvList.clear();
				this.MateriaEnchList.clear();
				this.MateriaEnchLvList.clear();
				for(int i=1;i < ResultSlotNum;i++)
				{
					this.materializeResult.setInventorySlotContents(i, null);
				}
			}
			else if(enchOnItem != null)//extract enchantment from Item
			{
				int var1Int = 0;
				for(int i=0;i<8;i++)
				{
					if(i < this.ItemEnchList.size())
					{
						int declv = (!materiadecLv)?0:(dmgratio > 0.5F)?0:(dmgratio > 0.25F)?1:2;
						int decreasedLv=(this.ItemEnchLvList.get(i)-declv <0)?0:this.ItemEnchLvList.get(i)-declv;
						int damage = this.setMateriaDmgfromEnch(this.ItemEnchList.get(i));
						ItemStack materia = new ItemStack(mod_EnchantChanger.MateriaID, 1, damage);
						mod_EnchantChanger.addEnchantmentToItem(materia, Enchantment.enchantmentsList[this.ItemEnchList.get(i)], this.ItemEnchLvList.get(i));
						this.materializeResult.setInventorySlotContents(i+1, materia);
					}
					else
						break;
				}
				this.materializeResult.setInventorySlotContents(0, Result);
				this.ItemEnchList.clear();
				this.ItemEnchLvList.clear();
				this.MateriaEnchList.clear();
				this.MateriaEnchLvList.clear();
			}
			else
			{
				for(int i=0;i < ResultSlotNum;i++)
				{
					this.materializeResult.setInventorySlotContents(i, null);
				}
				this.ItemEnchList.clear();
				this.ItemEnchLvList.clear();
				this.MateriaEnchList.clear();
				this.MateriaEnchLvList.clear();
			}
		}
	}
	public int setMateriaDmgfromEnch(int enchID)
	{
		if(enchID == mod_EnchantChanger.EnchantmentMeteoId)
			return 1;
		else if(enchID == mod_EnchantChanger.EndhantmentHolyId)
			return 2;
		else if(enchID == mod_EnchantChanger.EnchantmentTelepoId)
			return 3;
		else if(enchID == mod_EnchantChanger.EnchantmentFloatId)
			return 4;
		else if(enchID == mod_EnchantChanger.EnchantmentThunderId)
			return 5;
		else
			return 0;
	}
	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);

		if (!this.worldPointer.isRemote)
		{
			//System.out.println("client");
			if (!this.ItemSourceLeft())
			{
				for (int var4 = 0; var4 < ResultSlotNum; ++var4)
				{
					//System.out.println(var2);
					ItemStack var5 = this.materializeResult.getStackInSlotOnClosing(var4);
					if (var5 != null)
					{
						par1EntityPlayer.dropPlayerItem(var5);
					}
				}
			}
			for (int var2 = 0; var2 < this.SourceSlotNum; ++var2)
			{
				//System.out.println(var2);
				ItemStack var3 = this.materializeSource.getStackInSlotOnClosing(var2);
				if (var3 != null)
				{
					par1EntityPlayer.dropPlayerItem(var3);
				}
			}

		}
	}
	public Enchantment EnchKind(ItemStack item)
	{
		int EnchantmentKind = 256;
		for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
		{
			if(EnchantmentHelper.getEnchantmentLevel(i, item) > 0)
			{
				EnchantmentKind = i;
				break;
			}
		}
		return EnchantmentKind != 256 ? Enchantment.enchantmentsList[EnchantmentKind]:Enchantment.enchantmentsList[0];
	}
	public int EnchLv(ItemStack item)
	{
		int Lv = 0;
		for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
		{
			if(EnchantmentHelper.getEnchantmentLevel(i, item) > 0)
			{
				Lv = EnchantmentHelper.getEnchantmentLevel(i, item);
				break;
			}
		}
		return Lv;
	}
	public boolean checkMateriafromSlot(IInventory Source)
	{
		boolean ret=false;
		for (int i=0;i<Source.getSizeInventory();i++)
		{
			if(Source.getStackInSlot(i) !=null && Source.getStackInSlot(i).getItem() instanceof EcItemMateria)
				ret = true;
		}
		return ret;
	}
//	public boolean ItemResultLeft()
//	{
//		boolean ret=false;
//		for (int i=0;i<ResultSlotNum;i++)
//		{
//			if(this.materializeResult.getStackInSlot(i) !=null)
//				ret = true;
//		}
//		return ret;
//	}
	public boolean ItemSourceLeft()
	{
		if(this.materializeSource.getStackInSlot(0) != null)
			return true;
		else
			return false;
	}
//	public int MateriaKindFromEnch(int par1)
//	{
//		switch(par1)
//		{
//		case 0:return 0;
//		case 1:return 1;
//		case 2:return 2;
//		case 3:return 3;
//		case 4:return 4;
//		case 5:return 5;
//		case 6:return 6;
//		case 16:return 7;
//		case 17:return 8;
//		case 18:return 9;
//		case 19:return 10;
//		case 20:return 11;
//		case 21:return 12;
//		case 32:return 13;
//		case 33:return 14;
//		case 34:return 15;
//		case 35:return 16;
//		case 48:return 17;
//		case 49:return 18;
//		case 50:return 19;
//		case 51:return 20;
//		default:;
//		}
//		if(par1 == mod_EnchantChanger.EnchantmentMeteoId)
//		{
//			return 21;
//		}
//		else if(par1 == mod_EnchantChanger.EndhantmentHolyId)
//		{
//			return 22;
//		}
//		else if(par1 == mod_EnchantChanger.EnchantmentTelepoId)
//		{
//			return 23;
//		}
//		else if(par1 == mod_EnchantChanger.EnchantmentFloatId)
//		{
//			return 24;
//		}
//		else if(par1 == mod_EnchantChanger.EnchantmentThunderId)
//		{
//			return 25;
//		}
//		else
//		{
//			for(int i=0;i < mod_EnchantChanger.ExtraEnchantIdArray.size();i++)
//			{
//				if(par1 == mod_EnchantChanger.ExtraEnchantIdArray.get(i))
//				{
//					return 29 + i;
//				}
//			}
//			return 1000;
//		}
//	}
	public static int ExtraItemCheck(ItemStack par1ItemStack)
	{
		int var1 = par1ItemStack.itemID;
		System.out.println(var1);
		for(int i=0;i<mod_EnchantChanger.SwordIdArray.size();i++)
		{
			if(var1 == mod_EnchantChanger.SwordIdArray.get(i))
				return 1;
		}
		for(int i=0;i<mod_EnchantChanger.BowIdArray.size();i++)
		{
			if(var1 == mod_EnchantChanger.BowIdArray.get(i))
				return 2;
		}
		for(int i=0;i<mod_EnchantChanger.ToolIdArray.size();i++)
		{
			if(var1 == mod_EnchantChanger.ToolIdArray.get(i))
				return 3;
		}
		for(int i=0;i<mod_EnchantChanger.ArmorIdArray.size();i++)
		{
			if(var1 == mod_EnchantChanger.ArmorIdArray.get(i))
				return 6;
		}
		return 0;
	}
	public static boolean func_92037_a(Enchantment ench, ItemStack par1ItemStack)
	{
		if(ench == null)
		{
			return false;
		}
		else if(ench instanceof EnchantmentDurability)
		{
			return par1ItemStack.isItemStackDamageable() ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
		}
		else if(ench instanceof EnchantmentDigging)
		{
			return par1ItemStack.getItem().shiftedIndex == Item.shears.shiftedIndex ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
		}
		else if(ench instanceof EnchantmentDamage || ench instanceof EnchantmentLootBonus || ench instanceof EnchantmentFireAspect)
		{
			return par1ItemStack.getItem() instanceof ItemTool ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
		}/**
        else if(ench instanceof EnchantmentThorns)
        {
        	return par1ItemStack.getItem() instanceof ItemArmor ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
        }*/
		else if(ench instanceof EnchantmentUntouching)
		{
			return par1ItemStack.getItem().shiftedIndex == Item.shears.shiftedIndex ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
		}
		else if(ench instanceof EcEnchantmentMeteo || ench instanceof EcEnchantmentHoly || ench instanceof EcEnchantmentTeleport || ench instanceof EcEnchantmentFloat || ench instanceof EcEnchantmentThunder)
		{
			return par1ItemStack.getItem() instanceof EcItemSword;
		}
		else
		{
			return ench.type.canEnchantItem(par1ItemStack.getItem());
		}
	}
	public boolean CheckLvCap(ItemStack materia)
	{
		if(mod_EnchantChanger.LevelCap)
		{
			int ench = mod_EnchantChanger.getMateriaEnchKind(materia);
			int lv = mod_EnchantChanger.getMateriaEnchLv(materia);
			if(Enchantment.enchantmentsList[ench].getMaxLevel() < lv)
				return false;
			else return true;
		}
		else return true;
	}
}