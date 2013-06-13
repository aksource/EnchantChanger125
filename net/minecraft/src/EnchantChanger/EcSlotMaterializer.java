package net.minecraft.src.EnchantChanger;
import net.minecraft.src.InventoryBasic;
class EcSlotMaterializer extends InventoryBasic
{
    /** The brewing stand this slot belongs to. */
    final EcContainerMaterializer container;

    EcSlotMaterializer(EcContainerMaterializer par1ContainerMaterializer, String par2Str, int par3)
    {
        super(par2Str, par3);
        this.container = par1ContainerMaterializer;
    }
    public int getInventoryStackLimit()
    {
        return 1;
    }
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        this.container.onCraftMatrixChanged(this);
    }


}
