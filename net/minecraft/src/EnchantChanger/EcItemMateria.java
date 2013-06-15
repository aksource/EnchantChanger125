package net.minecraft.src.EnchantChanger;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemDye;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class EcItemMateria extends Item implements IItemRenderer
{
	public static final String[] MateriaNames = new String[] {"Protection", "Fire Protection", "Feather Falling", "Blast Protection", "Projectile Protection", "Respiration", "Aqua Affinity", "Sharpness", "Smite", "Bane of Arthropods", "Knockback", "Fire Aspect", "Looting", "Efficiency", "Silk Touch", "Unbreaking","Fortune","Power","Punch","Flame","Infinity"};
	public static final String[] MateriaJPNames = new String[]{"ダメージ軽減", "火炎耐性", "落下耐性", "爆発耐性", "飛び道具耐性", "水中呼吸", "水中採掘", "ダメージ増加", "アンデッド特効", "虫特効", "ノックバック", "火属性", "ドロップ増加", "効率強化", "シルクタッチ", "耐久力","幸運","矢ダメージ増加","パンチ","フレイム","無限"};
	public static final String[] MateriaMagicNames = new String[]{"Black","White","Teleport","Floating","Thunder","Despell","Haste","Absorption"};
	public static final String[] MateriaMagicJPNames = new String[]{"黒","白","瞬間移動","浮遊","雷","解呪","加速","吸収"};
	public static int MagicMateriaNum = MateriaMagicNames.length;
	public static int MagicMateriaNumMax = MagicMateriaNum*mod_EnchantChanger.MaxLv;
	public static int[] vanillaEnch = new int[]{0,1,2,3,4,5,6,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	public static double homeX = 0;
	public static double homeY = 512;
	public static double homeZ = 0;
	public static boolean GGEnable = false;
	public static boolean MagicMateriaInBar = false;
	private int ItemUseTime =0;
	private int materiamax = mod_EnchantChanger.materiamax;
	private int maxlv = mod_EnchantChanger.MaxLv;
	private int vanilla = mod_EnchantChanger.VanillaEnchNum;
	private boolean LvCap = mod_EnchantChanger.LevelCap;
	private boolean Debug = mod_EnchantChanger.Debug;
	private boolean tera = mod_EnchantChanger.YouAreTera;
	private Vec3D Gate = mod_EnchantChanger.GateCoord;
	protected static Random rand;
	private double BoxSize = 5D;
	private static double vert[];
	private static int  face[];
	private Minecraft mc;
	public EcItemMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 64;
		setMaxDamage(0);
		this.rand = new Random();
		this.setTextureFile(mod_EnchantChanger.EcSprites);
		this.mc = mod_EnchantChanger.mc;
	}
	public int getIconFromDamage(int par1)
	{
		int[] Icon = new int[]{8,8,8,8,8,4,4,13,13,13,14,1,9,5,7,3,9,13,14,1,12};
		if(par1 == 0)
		{
			return this.iconIndex;
		}
		else if(par1 <= materiamax)
		{
			int materiaId = (par1 - 1)/ maxlv;
			return Icon[materiaId];
		}
		else if(par1 <= materiamax + this.MagicMateriaNumMax)
		{
			int var1 = (par1 - materiamax - 1) / maxlv;
			switch(var1)
			{
			case 0 :return this.iconIndex;
			case 1 :return 15;
			case 2 :return 5;
			case 3 :return 9;
			case 4 :return 11;
			case 5 :return 7;
			case 6 :return 6;
			case 7 :return 10;
			default :return this.iconIndex;
			}
		}
		else if(mod_EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			return 10;
		}
		else
		{
			return this.iconIndex;
		}
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize>1)
		{
			return itemstack;
		}
		int enchLv = itemstack.getItemDamage() % maxlv;
		if(itemstack.getItemDamage() > 0 && itemstack.getItemDamage() <= materiamax)
		{
			int MateriaKind = (itemstack.getItemDamage()-1) / maxlv;
			if(entityplayer.isSneaking()&&LvCap && enchLv > 1)
			{
				for (int i=0; i< LevelUPEXP(MateriaKind,enchLv-1); i++)
					++entityplayer.experienceLevel;
//				entityplayer.addExperience(this.LevelUPEXP2(MateriaKind, enchLv-1));
				itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			}
			else if ((entityplayer.experienceLevel >= LevelUPEXP(MateriaKind,enchLv) || entityplayer.capabilities.isCreativeMode) && enchLv != 0)
			{
				entityplayer.removeExperience(LevelUPEXP(MateriaKind,enchLv));
//				entityplayer.addExperience(-this.LevelUPEXP2(MateriaKind, enchLv));
				itemstack.setItemDamage(itemstack.getItemDamage() + 1);
			}
		}
		else if(itemstack.getItemDamage() > materiamax && itemstack.getItemDamage() <= materiamax + MagicMateriaNumMax)
		{
			int var1 = (itemstack.getItemDamage() -  materiamax - 1) / maxlv;
			switch(var1)
			{
			case 0:Meteo(world,entityplayer);break;
			case 1:
				if(entityplayer.isSneaking())
				{
					GGEnable=!GGEnable;
					entityplayer.addChatMessage("Great Gospel Mode " + GGEnable);
				}
				else
				{
					Holy(world,entityplayer);
				}break;
			case 2:Teleport(itemstack,world,entityplayer);break;
			case 4:Thunder(world,entityplayer);break;
			case 5:Despell(entityplayer, entityplayer);break;
			case 6:Haste(entityplayer,entityplayer);break;
			default:;
			}
		}
		else if(itemstack.getItemDamage() > materiamax + MagicMateriaNumMax && mod_EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			int var2 = itemstack.getItemDamage() - (materiamax + MagicMateriaNumMax);
			int ExtraEnchLv = var2 % maxlv;
			if(entityplayer.isSneaking() && LvCap && ExtraEnchLv > 1)
			{
				for (int i=0; i< 15; i++)
					++entityplayer.experienceLevel;

//				entityplayer.addExperience(250);
				itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			}
			else if(ExtraEnchLv != 0 && (entityplayer.experienceLevel >= 15 || entityplayer.capabilities.isCreativeMode))
			{
				entityplayer.removeExperience(15);
//				entityplayer.addExperience(-250);
				itemstack.setItemDamage(itemstack.getItemDamage() + 1);
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
		if(itemstack.getItemDamage() >0  &&itemstack.getItemDamage() <= materiamax)
		{
			int MateriaKind = (itemstack.getItemDamage() - 1) / maxlv;
			int enchLv = itemstack.getItemDamage() % maxlv;
			int potionNum;
			String Message;
			String EntityName=entityliving.getEntityData().getName();
			switch (MateriaKind)
			{
			case 1:potionNum = 12;Message="fire resistance";break;
			case 5:potionNum = 13;Message="more Oxygen";break;
			case 2:potionNum = 8;Message="high jump";break;
			case 0:potionNum = 11;Message="defence power";break;
			default:return;
			}
			if(entityplayer.experienceLevel > LevelUPEXP(MateriaKind,enchLv) || entityplayer.capabilities.isCreativeMode)
			{
				entityplayer.addChatMessage(EntityName + " gets "+ Message);
				entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
				entityliving.addPotionEffect(new PotionEffect(potionNum,20*60*mod_EnchantChanger.MateriaPotionMinutes,enchLv));
				entityplayer.removeExperience(LevelUPEXP(MateriaKind,enchLv));
				return;
			}
		}
		else
		{
			int var1 = (itemstack.getItemDamage() -  materiamax - 1) / maxlv;
			switch(var1)
			{
			case 5:Despell(entityplayer,entityliving);return;
			case 6:Haste(entityplayer,entityliving);entityplayer.addChatMessage("Haste!");return;
			default:return;
			}
		}
	}
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)entity;
			if(itemstack.getItemDamage() >0  && itemstack.getItemDamage() <= materiamax)
			{
				int MateriaKind = (itemstack.getItemDamage() - 1) / maxlv;
				int enchLv = itemstack.getItemDamage() % maxlv;
				int potionNum;
				String Message;
				String EntityName=entityliving.getEntityData().getName();
				switch (MateriaKind)
				{
				case 1:potionNum = 12;Message="fire resistance";break;
				case 5:potionNum = 13;Message="more Oxygen";break;
				case 2:potionNum = 8;Message="high jump";break;
				case 0:potionNum = 11;Message="defence power";break;
				default:return false;
				}
				if(player.experienceLevel > LevelUPEXP(MateriaKind,enchLv) || player.capabilities.isCreativeMode)
				{
					entityliving.addPotionEffect(new PotionEffect(potionNum,20*60*mod_EnchantChanger.MateriaPotionMinutes,enchLv));
					player.removeExperience(LevelUPEXP(MateriaKind,enchLv));
					player.addChatMessage(EntityName + " gets "+ Message);
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-6);
					return true;
				}
			}
			else
			{
				int var1 = (itemstack.getItemDamage() -  materiamax - 1) / maxlv;
				switch(var1)
				{
				case 5:Despell(player,entityliving);return true;
				case 6:Haste(player,entityliving);player.addChatMessage("Haste!");return true;
				default:return false;
				}
			}
			return false;
		}
		return false;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){}

	public String getItemNameIS(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() ==0)
		{
			return "ItemMateria.Base";
		}
		else if(par1ItemStack.getItemDamage() <= materiamax)
		{
			int var1 = (par1ItemStack.getItemDamage()-1)/maxlv;
			int var2 = (par1ItemStack.getItemDamage() % maxlv != 0)? par1ItemStack.getItemDamage() % this.maxlv:this.maxlv;
			return "ItemMateria." + Enchantment.enchantmentsList[this.vanillaEnch[var1]].getName()+"."+var2;
		}
		else if(par1ItemStack.getItemDamage() <= materiamax + MagicMateriaNumMax)
		{
			int var3 =(par1ItemStack.getItemDamage()-materiamax -1)/maxlv;
			return "ItemMateria."+MateriaMagicNames[var3];
		}
		else if(mod_EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			int var4 = (par1ItemStack.getItemDamage() - (materiamax + MagicMateriaNumMax) -1) /maxlv;
			int var6 = (par1ItemStack.getItemDamage() - (materiamax + MagicMateriaNumMax)) % maxlv;
			if(var4 < mod_EnchantChanger.ExtraEnchantNameArray.size())
			{
				return "ItemMateria."+mod_EnchantChanger.ExtraEnchantNameArray.get(var4)+"."+var6;
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1, 0));
		for (int x = 0; x < this.vanilla; x++)
		{
			itemList.add(new ItemStack(this, 1, x*maxlv + 1 ));
			itemList.add(new ItemStack(this, 1, x*maxlv + 10 ));
			if(mod_EnchantChanger.Debug)
				itemList.add(new ItemStack(this, 1, x*maxlv + maxlv));
		}
		for(int i=0;i < MagicMateriaNum; i++)
		{
			itemList.add(new ItemStack(this, 1, materiamax + 1 + i*maxlv));
		}
		for(int i=0; i< mod_EnchantChanger.ExtraEnchantIdArray.size();i++)
		{
			itemList.add(new ItemStack(this, 1, materiamax+MagicMateriaNumMax + 1 + i*maxlv));
		}
	}

	public boolean hasEffect(ItemStack par1ItemStack)
	{
		int var1 =  par1ItemStack.getItemDamage();
		if(var1 <= materiamax)
			return false;
		else if(var1 <= materiamax + MagicMateriaNumMax)
			return true;
		else
			return false;
	}
	public EnumRarity getRarity(ItemStack var1)
	{
		if(var1.getItemDamage() ==0)
		{
			return EnumRarity.common;
		}
		else if(var1.getItemDamage() <= materiamax)
		{
			int var2 = var1.getItemDamage() % maxlv;
			switch(var2)
			{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return EnumRarity.common;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return EnumRarity.uncommon;
			default :
				return EnumRarity.epic;
			}
		}
		else if(var1.getItemDamage() <= materiamax + this.MagicMateriaNumMax)
		{
			return EnumRarity.rare;
		}
		else
		{
			return EnumRarity.common;
		}
	}
	public int LevelUPEXP(int materiakind, int materialv)
	{
		int[] LvUPBase = new int[]{10,5,5,5,5,10,20,10,5,5,5,5,10,10,20,10,10,10,5,5,20};
		int[] LvUPOver5 = new int[]{30,20,20,20,20,30,30,30,20,20,20,20,30,30,20,30,30,30,20,20,20};
		if(materialv < 5 || mod_EnchantChanger.Difficulty == 0)
		{
			return LvUPBase[materiakind];
		}
		else
		{
			return LvUPOver5[materiakind];
		}
	}
	public int LevelUPEXP2(int materiakind, int materialv)
	{
		int[] LvUPBase = new int[]{10*20,5*20,5*20,5*20,5*20,10*20,20*20,10*20,5*20,5*20,5*20,5*20,10*20,10*20,20*20,10*20,10*20,10*20,5*20,5*20,20*20};;
		int[] LvUPOver5 = new int[]{30*20,20*20,20*20,20*20,20*20,30*20,30*20,30*20,20*20,20*20,20*20,20*20,30*20,30*20,20*20,30*20,30*20,30*20,20*20,20*20,20*20};
		if(materialv < 5 || mod_EnchantChanger.Difficulty == 0)
		{
			return LvUPBase[materiakind];
		}
		else
		{
			return LvUPOver5[materiakind];
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
			for (int var2 = 0; var2 < 32; ++var2)
			{
				world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
			}
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
				for (int var2 = 0; var2 < 32; ++var2)
				{
					world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
				}
			}
		}
	}
	public void setHomePoint(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		homeX = entityplayer.posX;
		homeY = entityplayer.posY-1;
		homeZ = entityplayer.posZ;
		if(!world.isRemote)
			entityplayer.addChatMessage("Set HomePoint!!");
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
		//System.out.println("Meteo!");
		//System.out.println(entityplayer.rotationPitch);
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
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		float f0 = 0.3F;
		float f1 = 16F;
		if(type == ItemRenderType.EQUIPPED)
			this.renderMateria(item, 0.2f,0,0,f0, type);
		else if(type == ItemRenderType.INVENTORY)
			this.renderMateria(item, 0f, 0f, 0f, f1, type);
	}
	public void renderMateria(ItemStack item, float x, float y, float z, float size, ItemRenderType type)
	{
		GL11.glTranslatef(x, y, z);
		if(type == ItemRenderType.EQUIPPED && mc.gameSettings.thirdPersonView == 0)
			GL11.glTranslatef(0.3f, 0.2f, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/mod_EnchantChanger/gui/materia.png"));
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(100);
		float f = 1.0F;
		int color = ItemDye.dyeColors[15];
		float r = (float)(color >> 16 & 0xff) / 255F;
		float g = (float)(color >> 8 & 0xff) / 255F;
		float b = (float)(color & 0xff) / 255F;
//		if (EntityRenderer.anaglyphEnable)
//		{
//			float blightness = (r * 30F + g * 59F + b * 11F) / 100F;
//			float y1 = (r * 30F + g * 70F) / 100F;
//			float p = (r * 30F + b * 70F) / 100F;
//			r = blightness ;
//			g = y1;
//			b = p;
//		}
//		tessellator.setColorOpaque_F(f * r ,  f * g ,  f * b);

//		tessellator.draw();
		GL11.glScalef(size, size, size);
		tessellator.startDrawing(4);

		for (int v : face) {
			double d = Math.atan2(vert[v*3],vert[v*3+2])/Math.PI;
			if(d>0)
			{
				tessellator.addVertexWithUV(
						0 + (vert[v*3] + 1)/2,
						0 + (vert[v*3+1]+1)/2,
						0 + (vert[v*3+2]+1)/2,
						d/16D,
						(vert[v*3 +1]+1)/32D)	;
			}
			else
			{
				tessellator.addVertexWithUV(
						0 + (vert[v*3] + 1)/2,
						0 + (vert[v*3+1]+1)/2,
						0 + (vert[v*3+2]+1)/2,
						-d/16D,
						(vert[v*3 +1]+1)/32D)	;
			}

		}

		tessellator.draw();
		GL11.glTranslatef(-x, -y, -z);
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	static
	{
		vert = new double[]{
				 0.000000,1.000000,0.000000,
				 0.000000,-1.000000,0.000000,
				 0.873422,0.486965,0.000000,
				 -0.873422,-0.486965,0.000000,
				 0.269902,0.486965,-0.830673,
				 -0.269902,-0.486965,0.830673,
				 -0.706613,0.486965,-0.513384,
				 0.706613,-0.486965,0.513384,
				 -0.706613,0.486965,0.513385,
				 0.706613,-0.486965,-0.513385,
				 0.269902,0.486965,0.830673,
				 -0.269902,-0.486965,-0.830673,
				 0.506476,0.862254,0.000000,
				 0.666149,0.567453,-0.483986,
				 0.156510,0.862254,-0.481687,
				 -0.254446,0.567453,-0.783106,
				 -0.409748,0.862254,-0.297699,
				 -0.823406,0.567453,0.000000,
				 -0.409748,0.862254,0.297699,
				 -0.254446,0.567453,0.783106,
				 0.156510,0.862254,0.481687,
				 0.666149,0.567453,0.483986,
				 -0.666149,-0.567453,0.483986,
				 -0.506476,-0.862254,0.000000,
				 -0.156510,-0.862254,0.481687,
				 0.254446,-0.567453,0.783106,
				 0.409748,-0.862254,0.297699,
				 0.823406,-0.567453,-0.000000,
				 0.409748,-0.862254,-0.297699,
				 0.254446,-0.567453,-0.783106,
				 -0.156510,-0.862254,-0.481687,
				 -0.666149,-0.567453,-0.483986,
				 0.951056,0.000000,-0.309017,
				 0.587785,0.000000,-0.809017,
				 -0.000000,0.000000,-1.000000,
				 -0.587785,0.000000,-0.809017,
				 -0.951056,0.000000,-0.309017,
				 -0.951056,0.000000,0.309017,
				 -0.587785,0.000000,0.809017,
				 0.000000,0.000000,1.000000,
				 0.587785,0.000000,0.809017,
				 0.951056,0.000000,0.309017,
				 0.220950,0.699114,-0.680015,
				 0.430402,0.747999,-0.505224,
				 0.485560,0.546961,-0.681957,
				 0.262436,0.964949,0.000000,
				 0.347236,0.903206,-0.252282,
				 0.081097,0.964949,-0.249592,
				 0.798626,0.546961,-0.251059,
				 0.613498,0.747999,-0.253213,
				 0.715010,0.699114,0.000000,
				 -0.578456,0.699114,-0.420272,
				 -0.347495,0.747999,-0.565459,
				 -0.498533,0.546961,-0.672532,
				 -0.132633,0.903206,-0.408201,
				 -0.212316,0.964949,-0.154256,
				 0.008017,0.546961,-0.837120,
				 -0.051239,0.747999,-0.661719,
				 -0.578456,0.699114,0.420273,
				 -0.645166,0.747999,0.155751,
				 -0.793671,0.546961,0.266309,
				 -0.429208,0.903206,0.000000,
				 -0.212316,0.964949,0.154256,
				 -0.793671,0.546961,-0.266309,
				 -0.645166,0.747999,-0.155751,
				 0.220950,0.699114,0.680015,
				 -0.051239,0.747999,0.661719,
				 0.008017,0.546961,0.837120,
				 -0.132632,0.903206,0.408201,
				 0.081097,0.964949,0.249592,
				 -0.498533,0.546961,0.672532,
				 -0.347495,0.747999,0.565459,
				 0.613498,0.747999,0.253213,
				 0.798626,0.546961,0.251059,
				 0.347237,0.903206,0.252282,
				 0.485561,0.546961,0.681957,
				 0.430402,0.747999,0.505224,
				 -0.081097,-0.964949,0.249592,
				 -0.347236,-0.903206,0.252282,
				 -0.262436,-0.964949,0.000000,
				 -0.485560,-0.546961,0.681957,
				 -0.430402,-0.747999,0.505224,
				 -0.220950,-0.699114,0.680015,
				 -0.715010,-0.699114,0.000000,
				 -0.613498,-0.747999,0.253213,
				 -0.798626,-0.546961,0.251059,
				 0.212316,-0.964949,0.154256,
				 0.132633,-0.903206,0.408201,
				 0.498533,-0.546961,0.672532,
				 0.347495,-0.747999,0.565459,
				 0.578456,-0.699114,0.420272,
				 0.051239,-0.747999,0.661719,
				 -0.008017,-0.546961,0.837120,
				 0.212316,-0.964949,-0.154256,
				 0.429208,-0.903206,-0.000000,
				 0.793671,-0.546961,-0.266309,
				 0.645166,-0.747999,-0.155751,
				 0.578456,-0.699114,-0.420273,
				 0.645166,-0.747999,0.155751,
				 0.793671,-0.546961,0.266309,
				 -0.081097,-0.964949,-0.249592,
				 0.132632,-0.903206,-0.408201,
				 -0.008017,-0.546961,-0.837120,
				 0.051239,-0.747999,-0.661719,
				 -0.220950,-0.699114,-0.680015,
				 0.347495,-0.747999,-0.565459,
				 0.498533,-0.546961,-0.672532,
				 -0.347237,-0.903206,-0.252282,
				 -0.798626,-0.546961,-0.251059,
				 -0.613498,-0.747999,-0.253213,
				 -0.430402,-0.747999,-0.505224,
				 -0.485561,-0.546961,-0.681957,
				 0.676468,-0.254494,-0.691103,
				 0.809017,0.000000,-0.587785,
				 0.866318,-0.254494,-0.429797,
				 0.664005,0.300487,-0.684693,
				 0.448238,0.254494,-0.856922,
				 0.953495,0.254494,-0.161496,
				 0.856371,0.300487,-0.419925,
				 -0.448238,-0.254494,-0.856922,
				 -0.309017,0.000000,-0.951056,
				 -0.141054,-0.254494,-0.956732,
				 -0.445993,0.300487,-0.843088,
				 -0.676468,0.254494,-0.691103,
				 0.141054,0.254494,-0.956732,
				 -0.134739,0.300487,-0.944221,
				 -0.953495,-0.254494,0.161496,
				 -1.000000,0.000000,0.000000,
				 -0.953495,-0.254494,-0.161496,
				 -0.939644,0.300487,0.163636,
				 -0.866318,0.254494,0.429797,
				 -0.866318,0.254494,-0.429797,
				 -0.939644,0.300487,-0.163636,
				 -0.141054,-0.254494,0.956732,
				 -0.309017,0.000000,0.951057,
				 -0.448238,-0.254494,0.856922,
				 -0.134739,0.300487,0.944221,
				 0.141054,0.254494,0.956732,
				 -0.676468,0.254494,0.691103,
				 -0.445993,0.300487,0.843088,
				 0.866318,-0.254494,0.429797,
				 0.809017,0.000000,0.587785,
				 0.676468,-0.254494,0.691103,
				 0.856371,0.300487,0.419925,
				 0.953495,0.254494,0.161496,
				 0.448238,0.254494,0.856922,
				 0.664005,0.300487,0.684693,
				 -0.664005,-0.300487,0.684693,
				 -0.809017,0.000000,0.587785,
				 -0.856371,-0.300487,0.419925,
				 0.445993,-0.300487,0.843088,
				 0.309017,0.000000,0.951056,
				 0.134739,-0.300487,0.944221,
				 0.939644,-0.300487,-0.163636,
				 1.000000,0.000000,-0.000000,
				 0.939644,-0.300487,0.163636,
				 0.134739,-0.300487,-0.944221,
				 0.309017,0.000000,-0.951057,
				 0.445993,-0.300487,-0.843088,
				 -0.856371,-0.300487,-0.419925,
				 -0.809017,0.000000,-0.587785,
				 -0.664005,-0.300487,-0.684693
		};
		face = new int[]{
				13,44,43,
				4,42,44,
				14,43,42,
				42,43,44,
				14,47,46,
				0,45,47,
				12,46,45,
				45,46,47,
				12,50,49,
				2,48,50,
				13,49,48,
				48,49,50,
				14,46,43,
				12,49,46,
				13,43,49,
				49,43,46,
				15,53,52,
				6,51,53,
				16,52,51,
				51,52,53,
				16,55,54,
				0,47,55,
				14,54,47,
				47,54,55,
				14,42,57,
				4,56,42,
				15,57,56,
				56,57,42,
				16,54,52,
				14,57,54,
				15,52,57,
				57,52,54,
				17,60,59,
				8,58,60,
				18,59,58,
				58,59,60,
				18,62,61,
				0,55,62,
				16,61,55,
				55,61,62,
				16,51,64,
				6,63,51,
				17,64,63,
				63,64,51,
				18,61,59,
				16,64,61,
				17,59,64,
				64,59,61,
				19,67,66,
				10,65,67,
				20,66,65,
				65,66,67,
				20,69,68,
				0,62,69,
				18,68,62,
				62,68,69,
				18,58,71,
				8,70,58,
				19,71,70,
				70,71,58,
				20,68,66,
				18,71,68,
				19,66,71,
				71,66,68,
				21,73,72,
				2,50,73,
				12,72,50,
				50,72,73,
				12,45,74,
				0,69,45,
				20,74,69,
				69,74,45,
				20,65,76,
				10,75,65,
				21,76,75,
				75,76,65,
				12,74,72,
				20,76,74,
				21,72,76,
				76,72,74,
				23,79,78,
				1,77,79,
				24,78,77,
				77,78,79,
				24,82,81,
				5,80,82,
				22,81,80,
				80,81,82,
				22,85,84,
				3,83,85,
				23,84,83,
				83,84,85,
				24,81,78,
				22,84,81,
				23,78,84,
				84,78,81,
				24,77,87,
				1,86,77,
				26,87,86,
				86,87,77,
				26,90,89,
				7,88,90,
				25,89,88,
				88,89,90,
				25,92,91,
				5,82,92,
				24,91,82,
				82,91,92,
				26,89,87,
				25,91,89,
				24,87,91,
				91,87,89,
				26,86,94,
				1,93,86,
				28,94,93,
				93,94,86,
				28,97,96,
				9,95,97,
				27,96,95,
				95,96,97,
				27,99,98,
				7,90,99,
				26,98,90,
				90,98,99,
				28,96,94,
				27,98,96,
				26,94,98,
				98,94,96,
				28,93,101,
				1,100,93,
				30,101,100,
				100,101,93,
				30,104,103,
				11,102,104,
				29,103,102,
				102,103,104,
				29,106,105,
				9,97,106,
				28,105,97,
				97,105,106,
				30,103,101,
				29,105,103,
				28,101,105,
				105,101,103,
				30,100,107,
				1,79,100,
				23,107,79,
				79,107,100,
				23,83,109,
				3,108,83,
				31,109,108,
				108,109,83,
				31,111,110,
				11,104,111,
				30,110,104,
				104,110,111,
				23,109,107,
				31,110,109,
				30,107,110,
				110,107,109,
				32,114,113,
				9,112,114,
				33,113,112,
				112,113,114,
				33,116,115,
				4,44,116,
				13,115,44,
				44,115,116,
				13,48,118,
				2,117,48,
				32,118,117,
				117,118,48,
				33,115,113,
				13,118,115,
				32,113,118,
				118,113,115,
				34,121,120,
				11,119,121,
				35,120,119,
				119,120,121,
				35,123,122,
				6,53,123,
				15,122,53,
				53,122,123,
				15,56,125,
				4,124,56,
				34,125,124,
				124,125,56,
				35,122,120,
				15,125,122,
				34,120,125,
				125,120,122,
				36,128,127,
				3,126,128,
				37,127,126,
				126,127,128,
				37,130,129,
				8,60,130,
				17,129,60,
				60,129,130,
				17,63,132,
				6,131,63,
				36,132,131,
				131,132,63,
				37,129,127,
				17,132,129,
				36,127,132,
				132,127,129,
				38,135,134,
				5,133,135,
				39,134,133,
				133,134,135,
				39,137,136,
				10,67,137,
				19,136,67,
				67,136,137,
				19,70,139,
				8,138,70,
				38,139,138,
				138,139,70,
				39,136,134,
				19,139,136,
				38,134,139,
				139,134,136,
				40,142,141,
				7,140,142,
				41,141,140,
				140,141,142,
				41,144,143,
				2,73,144,
				21,143,73,
				73,143,144,
				21,75,146,
				10,145,75,
				40,146,145,
				145,146,75,
				41,143,141,
				21,146,143,
				40,141,146,
				146,141,143,
				22,80,147,
				5,135,80,
				38,147,135,
				135,147,80,
				38,138,148,
				8,130,138,
				37,148,130,
				130,148,138,
				37,126,149,
				3,85,126,
				22,149,85,
				85,149,126,
				38,148,147,
				37,149,148,
				22,147,149,
				149,147,148,
				25,88,150,
				7,142,88,
				40,150,142,
				142,150,88,
				40,145,151,
				10,137,145,
				39,151,137,
				137,151,145,
				39,133,152,
				5,92,133,
				25,152,92,
				92,152,133,
				40,151,150,
				39,152,151,
				25,150,152,
				152,150,151,
				27,95,153,
				9,114,95,
				32,153,114,
				114,153,95,
				32,117,154,
				2,144,117,
				41,154,144,
				144,154,117,
				41,140,155,
				7,99,140,
				27,155,99,
				99,155,140,
				32,154,153,
				41,155,154,
				27,153,155,
				155,153,154,
				29,102,156,
				11,121,102,
				34,156,121,
				121,156,102,
				34,124,157,
				4,116,124,
				33,157,116,
				116,157,124,
				33,112,158,
				9,106,112,
				29,158,106,
				106,158,112,
				34,157,156,
				33,158,157,
				29,156,158,
				158,156,157,
				31,108,159,
				3,128,108,
				36,159,128,
				128,159,108,
				36,131,160,
				6,123,131,
				35,160,123,
				123,160,131,
				35,119,161,
				11,111,119,
				31,161,111,
				111,161,119,
				36,160,159,
				35,161,160,
				31,159,161,
				161,159,160
		};

	}
}
