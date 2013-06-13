package net.minecraft.src.EnchantChanger;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
public class EcBlockHugeMateria extends BlockContainer //implements ITextureProvider
{
	private boolean isBottom;
	public EcBlockHugeMateria(int par1)
	{
		super(par1, 0, Material.rock);
		setHardness(5F);
		setResistance(2000.0F);
		setBlockName("HugeMateria");
        this.setLightOpacity(0);
        this.blockIndexInTexture = 49;
        this.setLightValue(1.0f);
	}
	public boolean renderAsNormalBlock()
    {
        return false;
    }
//	public String getTextureFile()
//	{
//		return mod_EnchantChanger.EcTerrain;
//	}
	public boolean isOpaqueCube()
    {
        return false;
    }

	public int quantityDropped(Random random)
    {
        return 1;
    }

//	public void addCreativeItems(ArrayList itemList)
//	{
//		itemList.add(new ItemStack(this, 1, 0));
//	}
	@Override
	public TileEntity getBlockEntity()
    {
        return new EcTileEntityHugeMateria();
    }
	@Override
	public TileEntity getBlockEntity(int meta)
    {
        return meta == 0 ?getBlockEntity():null;
    }
	@Override
    public int getRenderType()
	{
		return -1;
	}
	@Override
	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			if(par1World.getBlockId(par2, par3-1, par4) == this.blockID)
			{
				return this.blockActivated(par1World, par2, par3 -1 , par4, par5EntityPlayer);
			}
			else
			{
				par5EntityPlayer.openGui(mod_EnchantChanger.instance,mod_EnchantChanger.HugeMateriaGuiId,par1World,par2,par3,par4);
				return true;
			}
		}

    }
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if (var6 != 0)
		{
			if(var6 ==1)
			{
				if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID || par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
				{
					par1World.setBlockWithNotify(par2, par3, par4, 0);
				}
				if (par5 > 0 && par5 != this.blockID)
				{
					this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
					this.onNeighborBlockChange(par1World, par2, par3 + 1, par4, par5);
				}
			}
			else//var6 ==2
			{
				if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID)
				{
					par1World.setBlockWithNotify(par2, par3, par4, 0);
				}
//				if (par5 > 0 && par5 != this.blockID)
//				{
//					this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
//				}
			}
		}
		else
		{
			boolean var7 = false;

			if (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
			{
				par1World.setBlockWithNotify(par2, par3, par4, 0);
				var7 = true;
			}

//			if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1))
//			{
//				par1World.setBlockWithNotify(par2, par3, par4, 0);
//				var7 = true;
//
//				if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID)
//				{
//					par1World.setBlockWithNotify(par2, par3 + 1, par4, 0);
//				}
//			}

			if (var7)
			{
				if (!par1World.isRemote)
				{
					this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
				}
			}
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
    	   EcTileEntityHugeMateria t = (EcTileEntityHugeMateria)world.getBlockTileEntity(i, j, k);
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
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		switch(var1)
		{
		case 0:this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F);return;
		case 1:this.setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F);return;
		case 2:this.setBlockBounds(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F);return;
		}
//		if(par1IBlockAccess.getBlockId(par2, par3 - 1, par4) == this.blockID)
//			if(par1IBlockAccess.getBlockId(par2, par3 + 1, par4) == this.blockID)
//				this.setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F);
//			else if(var1 ==2)
//				this.setBlockBounds(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//		else
//			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F);
	}
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return par1  != 0 ? 0: mod_EnchantChanger.ItemHugeMateria.shiftedIndex;
	}
	public boolean isBottom()
	{
		return this.isBottom;
	}
	public void setBottom(boolean par1)
	{
		this.isBottom = par1;
	}
}