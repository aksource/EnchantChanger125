package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.ITextureProvider;
//public class BlockMaterialize extends Block implements ITextureProvider
public class EcBlockMaterializer extends BlockContainer implements ITextureProvider
{
	public EcBlockMaterializer(int par1)
	{
		super(par1, 0, Material.rock);
		setHardness(5F);
		setResistance(2000.0F);
		setBlockName("EnchantChanger");
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
	}
	public int idDropped(int i, Random random, int j)
    {
        return this.blockID;
    }
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	public String getTextureFile()
    {
            return mod_EnchantChanger.EcTerrain;
    }
	public boolean isOpaqueCube()
    {
        return false;
    }

	public int quantityDropped(Random random)
    {
        return 1;
    }

	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.getBlockTextureFromSide(par1);
    }
	public int getBlockTextureFromSide(int par1)
    {
        return par1 == 0 ? this.blockIndexInTexture + 17 : (par1 == 1 ? this.blockIndexInTexture : this.blockIndexInTexture + 16);
    }
	public void addCreativeItems(ArrayList itemList)
    {
            itemList.add(new ItemStack(this, 1, 0));
    }

	public TileEntity getBlockEntity()
    {
        return new EcTileEntityMaterializer();
    }

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			par5EntityPlayer.openGui(mod_EnchantChanger.instance,mod_EnchantChanger.MaterializerGuiId,par1World,par2,par3,par4);
			return true;
		}

    }
	/**
     * Called whenever the block is removed.
     */
	@Override
	public void onBlockRemoval(World world, int i, int j, int k)
    {
       if(world.getBlockTileEntity(i, j, k) != null)
       {
    	   EcTileEntityMaterializer t = (EcTileEntityMaterializer)world.getBlockTileEntity(i, j, k);
          for(int l = 0; l < t.getSizeInventory(); l++)
          {
             ItemStack ist = t.getStackInSlot(l);
             if(ist == null || ist.stackSize <= 0)
             {
                continue;
             }
             EntityItem eit = new EntityItem(world, (double)i+0.5D, (double)j+0.5D, (double)k+0.5D, ist);
             world.spawnEntityInWorld(eit);
          }
       }
       super.onBlockRemoval(world, i, j, k);
    }
}