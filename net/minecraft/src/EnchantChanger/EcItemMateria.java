package net.minecraft.src.EnchantChanger;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;

public class EcItemMateria extends Item
{
	public static final String[] MateriaMagicNames = new String[]{"Black","White","Teleport","Floating","Thunder","Despell","Haste","Absorption"};
	public static final String[] MateriaMagicJPNames = new String[]{"黒","白","瞬間移動","浮遊","雷","解呪","加速","吸収"};
	public static int MagicMateriaNum = MateriaMagicNames.length;
	public static int[] magicEnch = new int[]{mod_EnchantChanger.EnchantmentMeteoId, mod_EnchantChanger.EndhantmentHolyId, mod_EnchantChanger.EnchantmentTelepoId, mod_EnchantChanger.EnchantmentFloatId, mod_EnchantChanger.EnchantmentThunderId};
	public static double homeX = 0;
	public static double homeY = 512;
	public static double homeZ = 0;
	public static boolean GGEnable = false;
	public static boolean MagicMateriaInBar = false;

	private boolean LvCap = mod_EnchantChanger.LevelCap;
	private boolean Debug = mod_EnchantChanger.Debug;
	private boolean tera = mod_EnchantChanger.YouAreTera;
	private double BoxSize = 5D;
	public EcItemMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 64;
		setMaxDamage(0);
		this.setTextureFile(mod_EnchantChanger.EcSprites);
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize > 1)
		{
			return itemstack;
		}
		if(itemstack.getItemDamage() == 0 && itemstack.isItemEnchanted())
		{
			int EnchantmentKind = mod_EnchantChanger.getMateriaEnchKind(itemstack);
			int Lv = mod_EnchantChanger.getMateriaEnchLv(itemstack);
			if(entityplayer.isSneaking() && Lv > 1)
			{
//				entityplayer.removeExperience(-LevelUPEXP(itemstack, false));
				ItemStack expBottle;
				if(Lv > 5)
					expBottle = new ItemStack(mod_EnchantChanger.ItemExExpBottle);
				else
					expBottle = new ItemStack(Item.expBottle);
				entityplayer.dropPlayerItem(expBottle);
				this.addMateriaLv(itemstack, -1);
			}
			else if ((entityplayer.experienceLevel >= LevelUPEXP(itemstack,true) || entityplayer.capabilities.isCreativeMode) && Lv != 0)
			{
				entityplayer.removeExperience(LevelUPEXP(itemstack,true));
				this.addMateriaLv(itemstack, 1);
			}
		}
		else
		{
			switch(itemstack.getItemDamage())
			{
			case 1:Meteo(world,entityplayer);break;
			case 2:
				if(entityplayer.isSneaking())
				{
					GGEnable=!GGEnable;
					entityplayer.addChatMessage("Great Gospel Mode " + GGEnable);
				}
				else
				{
					Holy(world,entityplayer);
				}break;
			case 3:Teleport(itemstack,world,entityplayer);break;
			case 5:Thunder(world,entityplayer);break;
			case 6:Despell(entityplayer, entityplayer);break;
			case 7:Haste(entityplayer,entityplayer);break;
			default:;
			}
		}
		return itemstack;
	}
	/**
	 * Called when a player right clicks a entity with a item.
	 */
	public void useItemOnEntity(ItemStack itemstack, EntityLiving entityliving)
	{
		EntityPlayer entityplayer=ModLoader.getMinecraftInstance().thePlayer;
		this.MateriaPotionEffect(itemstack, entityliving, entityplayer);
	}
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)entity;
			this.MateriaPotionEffect(itemstack, entityliving, player);
			return false;
		}
		return false;
	}
	public void addMateriaLv(ItemStack item, int addLv)
	{
		int EnchantmentKind = mod_EnchantChanger.getMateriaEnchKind(item);
		int Lv = mod_EnchantChanger.getMateriaEnchLv(item);
		NBTTagCompound nbt = item.getTagCompound();
		mod_EnchantChanger.removeEnchTag(nbt, "ench");
		mod_EnchantChanger.addEnchantmentToItem(item, Enchantment.enchantmentsList[EnchantmentKind], Lv + addLv);
	}
	public void MateriaPotionEffect(ItemStack item, EntityLiving entity, EntityPlayer player)
	{
		if(item.getItemDamage() > 0)
		{
			switch(item.getItemDamage())
			{
			case 6:Despell(player,entity);return;
			case 7:Haste(player,entity);player.addChatMessage("Haste!");return;
			default:return;
			}
		}
		else
		{
			int EnchantmentKind = mod_EnchantChanger.getMateriaEnchKind(item);
			int Lv = mod_EnchantChanger.getMateriaEnchLv(item);
			if(EnchantmentKind != 256)
			{
				int potionNum;
				String Message;
				String EntityName = entity.getEntityData().getName();
				switch (EnchantmentKind)
				{
				case 1:potionNum = 12;Message="fire resistance";break;
				case 5:potionNum = 13;Message="more Oxygen";break;
				case 2:potionNum = 8;Message="high jump";break;
				case 0:potionNum = 11;Message="defence power";break;
				default:return;
				}
				if(player.experienceLevel > LevelUPEXP(item, false) || player.capabilities.isCreativeMode)
				{
					entity.addPotionEffect(new PotionEffect(potionNum,20*60*mod_EnchantChanger.MateriaPotionMinutes,Lv));
					player.removeExperience(LevelUPEXP(item, false));
					player.addChatMessage(EntityName + " gets "+ Message);
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-6);
					return;
				}
			}
		}
	}
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){}

	public String getItemNameIS(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() ==0)
		{
			return par1ItemStack.isItemEnchanted() ? "ItemMateria":"ItemMateria.Base";
		}
		else
		{
			int var3 = par1ItemStack.getItemDamage() - 1;
			return "ItemMateria."+MateriaMagicNames[var3];
		}
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1, 0));
		for(int i = 0; i < Enchantment.enchantmentsList.length;i++)
		{
			if(Enchantment.enchantmentsList[i] != null && !this.isMagicEnch(i))
			{
				ItemStack stack1 = new ItemStack(this, 1, 0);
				stack1.addEnchantment(Enchantment.enchantmentsList[i], 1);
				itemList.add(stack1);
				ItemStack stack2 = new ItemStack(this, 1, 0);
				stack2.addEnchantment(Enchantment.enchantmentsList[i], 10);
				itemList.add(stack2);
				if(mod_EnchantChanger.Debug)
				{
					ItemStack stack3 = new ItemStack(this, 1, 0);
					stack3.addEnchantment(Enchantment.enchantmentsList[i], 127);
					itemList.add(stack3);
				}
			}
		}
		for(int i=0;i < MagicMateriaNum; i++)
		{
			ItemStack magic = new ItemStack(this, 1,1 + i);
			if(i<this.magicEnch.length)
				magic.addEnchantment(Enchantment.enchantmentsList[this.magicEnch[i]], 1);
			itemList.add(magic);
		}
	}
	public boolean isMagicEnch(int enchID)
	{
		for(int i=0;i < this.magicEnch.length;i++)
			if(enchID == this.magicEnch[i])
				return true;
		return false;
	}
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItemDamage() > 0;
	}
	public EnumRarity getRarity(ItemStack item)
	{
		if(item.getItemDamage() > 0)
			return EnumRarity.rare;
		else
		{
			if(mod_EnchantChanger.getMateriaEnchKind(item) == 256 || mod_EnchantChanger.getMateriaEnchLv(item) < 6)
				return EnumRarity.common;
			else if(mod_EnchantChanger.getMateriaEnchLv(item) < 11)
				return EnumRarity.uncommon;
			else
				return EnumRarity.rare;
		}
	}
	public int LevelUPEXP(ItemStack item, boolean next)
	{
		int EnchantmentKind = mod_EnchantChanger.getMateriaEnchKind(item);
		int Lv = mod_EnchantChanger.getMateriaEnchLv(item);
		int nextLv = next ?1:0;
		if(EnchantmentKind == 256)
			return 0;
		if(Lv < 5 || mod_EnchantChanger.Difficulty == 0)
		{
			return Enchantment.enchantmentsList[EnchantmentKind].getMinEnchantability(Lv + nextLv);
		}
		else
		{
			return Enchantment.enchantmentsList[EnchantmentKind].getMaxEnchantability(Lv + nextLv);
		}
	}
	public static void Teleport(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		ChunkCoordinates Spawn;
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !mod_EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(entityplayer.getSpawnChunk() !=null)
		{
			Spawn = entityplayer.getSpawnChunk();
		}
		else
		{
			Spawn = world.getSpawnPoint();
		}
		double SpawnX = Spawn.posX;
		double SpawnY = Spawn.posY;
		double SpawnZ = Spawn.posZ;
		if(entityplayer.isSneaking() && entityplayer.dimension == 0)
		{

			entityplayer.setPositionAndUpdate(SpawnX, SpawnY, SpawnZ);
			if(!entityplayer.capabilities.isCreativeMode)
			{
				entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-20);
				entityplayer.fallDistance = 0.0F;
			}
			spawnPortalParticle(world, entityplayer);
		}
		else
		{
			if(setTeleportPoint(world,entityplayer) !=null)
			{
				entityplayer.setPositionAndUpdate(setTeleportPoint(world,entityplayer).xCoord, setTeleportPoint(world,entityplayer).yCoord, setTeleportPoint(world,entityplayer).zCoord);
				if(!entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-2);
					entityplayer.fallDistance = 0.0F;
				}
				spawnPortalParticle(world, entityplayer);
			}
		}
	}
	private static void spawnPortalParticle(World world, EntityPlayer entityplayer)
	{
		for (int var2 = 0; var2 < 32; ++var2)
		{
			world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + world.rand.nextDouble() * 2.0D, entityplayer.posZ, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
		}
	}
	public static Vec3D setTeleportPoint(World world, EntityPlayer entityplayer)
	{
		float var1=1F;
		double Dislimit = 150.0D;
		double viewX = entityplayer.getLookVec().xCoord;
		double viewY = entityplayer.getLookVec().yCoord;
		double viewZ = entityplayer.getLookVec().zCoord;
		double PlayerposX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)var1;
		double PlayerposY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)var1 + 1.62D - (double)entityplayer.yOffset;
		double PlayerposZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)var1;
		Vec3D PlayerPosition = Vec3D.createVector(PlayerposX, PlayerposY, PlayerposZ);
		Vec3D PlayerLookVec = PlayerPosition.addVector(viewX*Dislimit, viewY*Dislimit, viewZ*Dislimit);
		MovingObjectPosition MOP = world.rayTraceBlocks_do(PlayerPosition, PlayerLookVec, true);
		if(MOP !=null)
		{
			if (MOP.typeOfHit == EnumMovingObjectType.TILE)
			{
				int BlockposX = MOP.blockX;
				int BlockposY = MOP.blockY;
				int BlockposZ = MOP.blockZ;
				int Blockside = MOP.sideHit;
				switch(Blockside)
				{
				case 0:BlockposY -=2;break;
				case 1:BlockposY++;break;
				case 2:BlockposZ--;break;
				case 3:BlockposZ++;break;
				case 4:BlockposX--;break;
				case 5:BlockposX++;break;
				}
				Vec3D TelepoVec = Vec3D.createVector(BlockposX, BlockposY, BlockposZ);
				return TelepoVec;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	public static void Holy(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !mod_EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
			entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
		List EntityList = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(5D, 5D, 5D));
		for (int i=0; i < EntityList.size();i++)
		{
			Entity entity=(Entity) EntityList.get(i);
			if(entity instanceof EntitySkeleton || entity instanceof EntityZombie || entity instanceof EntityPigZombie)
			{
				int var1 = MathHelper.floor_float(((EntityLiving) entity).getMaxHealth()/2);
				entity.attackEntityFrom(DamageSource.magic, var1);
			}
		}
	}
	public static void Meteo(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !mod_EnchantChanger.YouAreTera &&!entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
			entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
		Vec3D EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null)
		{
			world.playSoundAtEntity(entityplayer, "ambient.cave9",1F, 1f);
			world.spawnEntityInWorld( new EcEntityMeteo(world, EndPoint.xCoord,(double)200,EndPoint.zCoord,0.0D,-1D,0D,0.0F,0.0F));
		}
	}
	public static void Thunder(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !mod_EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
			entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
		Vec3D EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null)
			world.spawnEntityInWorld( new EntityLightningBolt(world, EndPoint.xCoord, EndPoint.yCoord, EndPoint.zCoord));
	}
	public void Despell(EntityPlayer player,Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			((EntityLiving) entity).clearActivePotions();
			for (int i=0;i<33;i++)
			{
				((EntityLiving) entity).removePotionEffect(i);
				//System.out.println("Despell "+i);
			}
			if(!player.capabilities.isCreativeMode)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-2);
		}
	}
	public void Haste(EntityPlayer player,EntityLiving entityliving)
	{
		entityliving.addPotionEffect(new PotionEffect(1,20*60*5,1));
		if(!player.capabilities.isCreativeMode)
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-2);
	}
}
