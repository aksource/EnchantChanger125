package net.minecraft.src.EnchantChanger;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
public class EcSlotMakeMateria extends Slot
{
	private final IInventory tileentity;

	public EcSlotMakeMateria(IInventory par1IInventory, int par2, int par3, int par4)
    {
        super(par1IInventory, par2, par3, par4);
        this.tileentity = par1IInventory;
    }
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
	public int getSlotStackLimit()
    {
        return 1;
    }
}