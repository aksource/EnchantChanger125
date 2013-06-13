package net.minecraft.src.EnchantChanger;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.IGuiHandler;
public class EcGuiHandler implements IGuiHandler
{
	public Object getGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
	{
		if(var1 == mod_EnchantChanger.MaterializerGuiId)
		{
			return new EcGuiMaterializer(var3, var2.inventory);
		}
		else if(var1 == mod_EnchantChanger.PortableEnchantmentTableGuiId)
		{
			return new EcGuiPortableEnchantment(var2.inventory, var3, var4, var5, var6);
		}
		else if(var1 == mod_EnchantChanger.HugeMateriaGuiId)
		{
			TileEntity t = var3.getBlockTileEntity(var4, var5, var6);
			return new EcGuiHugeMateria(var3, var2.inventory, (EcTileEntityHugeMateria)t);
		}
		else
		{
			return null;
		}
	}
}