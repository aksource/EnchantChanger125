package net.minecraft.src.EnchantChanger;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Slot;
public class EcSlotEnchantedItem extends Slot
{
	private final IInventory materializeSource;
    final EcContainerMaterializer container;
	private final IInventory materializeResult;

	public EcSlotEnchantedItem(EcContainerMaterializer par1ContainerMaterializer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
    {
        super(par3IInventory, par4, par5, par6);
        this.container = par1ContainerMaterializer;
        this.materializeSource = par2IInventory;
        this.materializeResult = par3IInventory;
    }
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
	public int getSlotStackLimit()
    {
        return 1;
    }
	@Override
	public void onPickupFromSlot(ItemStack par1ItemStack)
	{
		super.onPickupFromSlot(par1ItemStack);
		for(int i=0;i<EcContainerMaterializer.SourceSlotNum;i++)
		{
			this.materializeSource.decrStackSize(i, 1);
		}
		InventoryPlayer IP = ModLoader.getMinecraftInstance().thePlayer.inventory;
		for(int i=0;i<EcContainerMaterializer.ResultSlotNum;i++)
		{
			ItemStack SlotStack = this.materializeResult.getStackInSlot(i);
			if(SlotStack == null)
			{
				continue;
			}
			if(IP.addItemStackToInventory(SlotStack.copy()))
			{
				this.materializeResult.decrStackSize(i, 1);
			}
			else
			{
				ModLoader.getMinecraftInstance().thePlayer.dropPlayerItem(SlotStack.copy());
				this.materializeResult.decrStackSize(i, 1);
			}
		}
	}

}