package net.minecraft.src.EnchantChanger;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
public class EcSlotItemMateria extends Slot
{
	final EcContainerMaterializer container;
	final IInventory materializeSource;
	final IInventory materializeResult;
	public EcSlotItemMateria(EcContainerMaterializer par1ContainerMaterializer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
    {
        super(par3IInventory, par4, par5, par6);
        this.container = par1ContainerMaterializer;
        this.materializeResult = par2IInventory;
        this.materializeSource = par3IInventory;
    }
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return true;
    }
    public int getSlotStackLimit()
    {
        return 1;
    }

	@Override
	public void onPickupFromSlot(ItemStack par1ItemStack)
	{
		super.onPickupFromSlot(par1ItemStack);
		this.materializeResult.onInventoryChanged();
		this.materializeSource.onInventoryChanged();
	}
}