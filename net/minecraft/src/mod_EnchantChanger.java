package net.minecraft.src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EnchantChanger.EcBlockHugeMateria;
import net.minecraft.src.EnchantChanger.EcBlockMaterializer;
import net.minecraft.src.EnchantChanger.EcEnchantmentFloat;
import net.minecraft.src.EnchantChanger.EcEnchantmentHoly;
import net.minecraft.src.EnchantChanger.EcEnchantmentMeteo;
import net.minecraft.src.EnchantChanger.EcEnchantmentTeleport;
import net.minecraft.src.EnchantChanger.EcEnchantmentThunder;
import net.minecraft.src.EnchantChanger.EcEntityApOrb;
import net.minecraft.src.EnchantChanger.EcEntityExExpBottle;
import net.minecraft.src.EnchantChanger.EcEntityMeteo;
import net.minecraft.src.EnchantChanger.EcEntitySword;
import net.minecraft.src.EnchantChanger.EcGuiHandler;
import net.minecraft.src.EnchantChanger.EcItemCloudSword;
import net.minecraft.src.EnchantChanger.EcItemCloudSwordCore;
import net.minecraft.src.EnchantChanger.EcItemEnchantmentTable;
import net.minecraft.src.EnchantChanger.EcItemExExpBottle;
import net.minecraft.src.EnchantChanger.EcItemHugeMateria;
import net.minecraft.src.EnchantChanger.EcItemMasterMateria;
import net.minecraft.src.EnchantChanger.EcItemMateria;
import net.minecraft.src.EnchantChanger.EcItemMaterializer;
import net.minecraft.src.EnchantChanger.EcItemSephirothSword;
import net.minecraft.src.EnchantChanger.EcItemSephirothSwordImit;
import net.minecraft.src.EnchantChanger.EcItemSword;
import net.minecraft.src.EnchantChanger.EcItemUltimateWeapon;
import net.minecraft.src.EnchantChanger.EcItemZackSword;
import net.minecraft.src.EnchantChanger.EcLivingHandler;
import net.minecraft.src.EnchantChanger.EcMasterMateriaRecipe;
import net.minecraft.src.EnchantChanger.EcMateriaRecipe;
import net.minecraft.src.EnchantChanger.EcRenderApOrb;
import net.minecraft.src.EnchantChanger.EcRenderHugeMateria;
import net.minecraft.src.EnchantChanger.EcRenderItemThrowable;
import net.minecraft.src.EnchantChanger.EcRenderMateria;
import net.minecraft.src.EnchantChanger.EcTileEntityHugeMateria;
import net.minecraft.src.EnchantChanger.EcTileEntityMaterializer;
import net.minecraft.src.forge.IItemRenderer;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.forge.NetworkMod;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.registry.FMLRegistry;

public class mod_EnchantChanger extends NetworkMod
{
	@Override
	public String getVersion() {
		return "1.6q";
	}

	@MLProp(info="ExExpBottleID", min = 4096, max = 32000)
	public static int ExExpBottleID = 4999;
	public static  Item ItemExExpBottle ;
	@MLProp(info="MateriaID", min = 4096, max = 32000)
	public static int MateriaID = 5000;
	public static  Item ItemMat ;
	@MLProp(info="ZackSwordItemID", min = 4096, max = 32000)
	public static int ZackSwordItemID = 5001;
	public static  Item ItemZackSword ;
	@MLProp(info="CloudSwordItemID", min = 4096, max = 32000)
	public static int CloudSwordItemID = 5002;
	public static  Item ItemCloudSword ;
	@MLProp(info="FirstSwordItemID", min = 4096, max = 32000)
	public static int FirstSwordItemID = 5003;
	public static  Item ItemCloudSwordCore ;
	@MLProp(info="SephirothSwordItemID", min = 4096, max = 32000)
	public static int SephirothSwordItemID = 5004;
	public static  Item ItemSephirothSword ;
	@MLProp(info="UltimateWeaponItemID", min = 4096, max = 32000)
	public static int UltimateWeaponItemID = 5005;
	public static  Item ItemUltimateWeapon ;
	@MLProp(info="PortableEnchantChangerID", min = 4096, max = 32000)
	public static int PortableEnchantChangerID = 5006;
	public static  Item ItemPortableEnchantChanger ;
	@MLProp(info="PortableEnchantmentTableID", min = 4096, max = 32000)
	public static int PortableEnchantmentTableID = 5007;
	public static  Item ItemPortableEnchantmentTable ;
	@MLProp(info="MasterMateriaID", min = 4096, max = 32000)
	public static int MasterMateriaID = 5008;
	public static  Item MasterMateria ;
	@MLProp(info="ImitateSephirothSword", min = 4096, max = 32000)
	public static int ImitateSephSwordID = 5009;
	public static Item ItemImitateSephirothSword;
	@MLProp(info="EnchantChangerID")
	public static int EnchantChangerID = 2000;
	public static Block BlockMat;
	@MLProp(info="HugeMateriaID")
	public static int HugeMateriaID = 2001;
	public static Block HugeMateria;
	public static Item ItemHugeMateria;
	@MLProp(info="LevelCap")
	public static boolean LevelCap = false;
	@MLProp(info="Debug")
	public static boolean Debug = false;
	@MLProp(info="Meteo Power")
	public static float MeteoPower = 10.0F;
	@MLProp(info="Meteo Size")
	public static float MeteoSize = 10.0F;
	@MLProp(info="Extra Sword Ids(test)")
	public static String SwordIds = "";
	public static ArrayList<Integer> SwordIdArray = new ArrayList<Integer>();
	@MLProp(info="Extra Tool Ids(test)")
	public static String ToolIds = "";
	public static ArrayList<Integer> ToolIdArray = new ArrayList<Integer>();
	@MLProp(info="Extra Bow Ids(test)")
	public static String BowIds = "";
	public static ArrayList<Integer> BowIdArray = new ArrayList<Integer>();
	@MLProp(info="Extra Armor Ids(test)")
	public static String ArmorIds = "";
	public static ArrayList<Integer> ArmorIdArray = new ArrayList<Integer>();
	@MLProp(info="Item damege decrease MateriaLv")
	public static boolean DecMateriaLv = false;
	@MLProp(info="You are Tera in FF4")
	public static boolean YouAreTera = false;
	@MLProp(info="Materia Potion Minutes")
	public static int MateriaPotionMinutes = 10;
	@MLProp(info="Difficulty Ex:0=Easy,1=Normal,2=Hard")
	public static int Difficulty =1;
	@MLProp(info="false:Not Spawn ApOrb")
	public static boolean enableAPSystem = true;
	@MLProp(info ="false: materia is not in dungeon loot chest")
	public static boolean enableDungeonLoot = true;
	@MLProp(info="BasePoint of Next Ehcnantment Level. it is 100 in 1.6l")
	public static int aPBasePoint = 200;
	@MLProp(info="Set Enchantmets Level Limit for AP System Format EnchantmentID:LimitLv(LimitLv = 0 > DefaultMaxLevel")
	public static String enchantmentLevelLimits = "0:10,1:10,2:10,3:10,4:10,32:0,34:30";
	public static HashMap<Integer, Integer> levelLimitMap = new HashMap<Integer, Integer>();
	public static int MaxLv = 127;


	public static int EnchantmentMeteoId =240;
	public static Enchantment Meteo;
	public static int EndhantmentHolyId=241;
	public static Enchantment Holy;
	public static int EnchantmentTelepoId=242;
	public static Enchantment Telepo;
	public static int EnchantmentFloatId=243;
	public static Enchantment Float;
	public static int EnchantmentThunderId=244;
	public static Enchantment Thunder;

	public int[] Count= new int[]{0,0,0,0,0,0,0,0,0,0};
	public static int[] LimitBreakCount = new int[]{0,0,0};
	public static int[] LimitBreakCoolDownCount = new int[]{0,0,0};
	public static boolean[] LimitBreakFlag = new boolean[]{false,false,false};
	public static HashMap<Integer, Integer> apLimit = new HashMap();
	public static HashSet<Integer> magicEnchantment = new HashSet();
	public int GGMptime=20*1;
	public int AbsorpMptime=20*3;
	public double AbsorpBoxSize=5D;

	public static KeyBinding MagicKey = new KeyBinding("Key.EcMagic",Keyboard.KEY_V);
	public static boolean MagicKeyDown = false;

	public static int MaterializerGuiId = 0;
	public static int PortableEnchantmentTableGuiId=1;
	public static int HugeMateriaGuiId=2;

	public static String EcSprites ="/mod_EnchantChanger/gui/items.png";
	public static String EcTerrain = "/mod_EnchantChanger/terrain.png";
	public static String EcZackSwordPNG ="/mod_EnchantChanger/item/ZackSword.png";
	public static String EcSephirothSwordPNG ="/mod_EnchantChanger/item/SephirothSword.png";
	public static String EcCloudSword2PNG ="/mod_EnchantChanger/item/CloudSword-3Dtrue.png";
	public static String EcCloudSwordCore2PNG ="/mod_EnchantChanger/item/CloudSwordCore-3Dtrue.png";
	public static String EcUltimateWeaponPNG ="/mod_EnchantChanger/item/UltimaWeapon.png";
	public static String EcGuiMaterializer ="/mod_EnchantChanger/gui/materializer.png";
	public static String EcGuiHuge = "/mod_EnchantChanger/gui/HugeMateriaContainer.png";
	public static String EcHugetex ="/mod_EnchantChanger/item/hugemateriatex.png";

	public static Minecraft mc;
	public static mod_EnchantChanger instance;

	private Entity entity;
	private boolean CSCmode;
	public static boolean loadMTH = false;
    @Override
    public String getPriorities()
    {
        return "after:*";
    }
	public mod_EnchantChanger(){}
    public void keyboardEvent(KeyBinding event)
    {
    	if(event == this.MagicKey)
    	{
    		this.MagicKeyDown = event.isPressed();
    	}
    }
	public void load() {
		//initialize
		ModLoaderHelper.updateStandardTicks(this, true, true);
		mc = FMLClientHandler.instance().getClient();
		instance =this;
		this.initMaps();
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/items.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/terrain.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia0.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia1.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia3.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia4.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia5.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia6.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia7.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia8.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia9.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia10.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia11.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia12.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia13.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia14.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materia15.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/HugeMateriaContainer.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/materializer.png");
		MinecraftForge.setGuiHandler(this, new EcGuiHandler());
		MinecraftForge.registerEntityLivingHandler(new EcLivingHandler());
		ModLoader.registerKey(this, MagicKey, false);

		StringtoInt(SwordIds,SwordIdArray);
		StringtoInt(ToolIds,ToolIdArray);
		StringtoInt(BowIds,BowIdArray);
		StringtoInt(ArmorIds,ArmorIdArray);

		//Definition Item & Block & Enchant
		ItemMat = (new EcItemMateria(MateriaID-256)).setItemName("ItemMateria");
		ItemExExpBottle = (new EcItemExExpBottle(ExExpBottleID-256)).setItemName("ItemExExpBottle").setIconIndex(21);
		ItemZackSword = (new EcItemZackSword(ZackSwordItemID-256)).setItemName("ItemZackSword").setIconIndex(16);
		ItemCloudSwordCore = (new EcItemCloudSwordCore(FirstSwordItemID-256)).setItemName("ItemCloudSwordCore").setIconIndex(17);
		ItemCloudSword = (new EcItemCloudSword(CloudSwordItemID-256)).setItemName("ItemCloudSword").setIconIndex(19);
		ItemSephirothSword = (new EcItemSephirothSword(SephirothSwordItemID-256)).setItemName("ItemSephirothSword").setIconIndex(20);
		ItemUltimateWeapon = (new EcItemUltimateWeapon(UltimateWeaponItemID - 256)).setItemName("ItemUltimateWeapon").setIconIndex(23);
		ItemPortableEnchantChanger = (new EcItemMaterializer(PortableEnchantChangerID - 256)).setItemName("ItemPortableEnchantChanger").setIconIndex(24);
		ItemPortableEnchantmentTable = (new EcItemEnchantmentTable(PortableEnchantmentTableID - 256)).setItemName("ItemPortableEnchantmentTable").setIconIndex(25);
		MasterMateria = new EcItemMasterMateria(MasterMateriaID - 256).setItemName("ItemMasterMateria").setIconIndex(10);
		ItemImitateSephirothSword = (new EcItemSephirothSwordImit(ImitateSephSwordID-256)).setItemName("ItemSephirothSwordImit").setIconIndex(20);
		BlockMat = (new EcBlockMaterializer(EnchantChangerID));
		HugeMateria = new EcBlockHugeMateria(HugeMateriaID);
		ItemHugeMateria = new EcItemHugeMateria(HugeMateria.blockID - 256).setIconIndex(26).setItemName("ItemhugeMateria");
		Meteo = new EcEnchantmentMeteo(this.EnchantmentMeteoId,0);
		Holy = new EcEnchantmentHoly(this.EndhantmentHolyId,0);
		Telepo = new EcEnchantmentTeleport(this.EnchantmentTelepoId,0);
		Float = new EcEnchantmentFloat(this.EnchantmentFloatId,0);
		Thunder = new EcEnchantmentThunder(this.EnchantmentThunderId,0);

		//register
		FMLRegistry.registerBlock(BlockMat);
		FMLRegistry.registerTileEntity(EcTileEntityMaterializer.class, "container.materializer");
		ClientRegistry.instance().registerTileEntity(EcTileEntityHugeMateria.class, "container.hugeMateria", new EcRenderHugeMateria());
		MinecraftForgeClient.registerItemRenderer(SephirothSwordItemID, (IItemRenderer) ItemSephirothSword);
		MinecraftForgeClient.registerItemRenderer(ZackSwordItemID, (IItemRenderer)ItemZackSword);
		MinecraftForgeClient.registerItemRenderer(FirstSwordItemID, (IItemRenderer)ItemCloudSwordCore);
		MinecraftForgeClient.registerItemRenderer(CloudSwordItemID, (IItemRenderer)ItemCloudSword);
		MinecraftForgeClient.registerItemRenderer(UltimateWeaponItemID, (IItemRenderer)ItemUltimateWeapon);
		MinecraftForgeClient.registerItemRenderer(ImitateSephSwordID, (IItemRenderer)ItemImitateSephirothSword);
		MinecraftForgeClient.registerItemRenderer(MateriaID, new EcRenderMateria());
		MinecraftForgeClient.registerItemRenderer(MasterMateriaID, new EcRenderMateria());
//		ModLoader.registerEntityID(EcEntityExExpBottle.class, "ItemExExpBottle", 500);
//		ModLoader.registerEntityID(EcEntityMeteo.class, "Meteo", 501);
//		ModLoader.registerEntityID(EcEntitySword.class, "EntitySword", 502);
//		ModLoader.registerEntityID(EcEntityApOrb.class, "ApOrb", 503);
		MinecraftForge.registerEntity(EcEntityExExpBottle.class, this, 0, 32, 0, false);
		MinecraftForge.registerEntity(EcEntityMeteo.class, this, 1, 256, 0, false);
		MinecraftForge.registerEntity(EcEntitySword.class, this, 2, 32, 0, false);
		MinecraftForge.registerEntity(EcEntityApOrb.class, this, 3, 32, 0, false);
		
		MinecraftForge.setToolClass(ItemSephirothSword, "FF7", 0);
		Block[] pickeff =
			{
				Block.cobblestone, Block.stairDouble,
				Block.stairSingle, Block.stone,
				Block.sandStone,   Block.cobblestoneMossy,
				Block.oreCoal,     Block.ice,
				Block.netherrack,  Block.oreLapis,
				Block.blockLapis,  Block.oreRedstone,
				Block.obsidian,    Block.oreRedstoneGlowing
			};
		for (Block block : pickeff)
		{
			MinecraftForge.removeBlockEffectiveness(block, "FF7");
			MinecraftForge.setBlockHarvestLevel(block, "FF7", 0);
		}



		//register recipes
		if(this.Difficulty < 2)
			FMLRegistry.addRecipe(new EcMateriaRecipe());
		FMLRegistry.addRecipe(new EcMasterMateriaRecipe());
		FMLRegistry.addShapelessRecipe(new ItemStack(ItemMat,1, 0), new Object[]{new ItemStack(Item.diamond, 1), new ItemStack(Item.enderPearl, 1)});
		FMLRegistry.addRecipe(new ItemStack(ItemZackSword, 1), new Object[]{" X","XX"," Y", Character.valueOf('X'),Block.blockSteel, Character.valueOf('Y'),Item.ingotIron});
		FMLRegistry.addRecipe(new ItemStack(ItemCloudSwordCore, 1), new Object[]{" X ","XYX"," Z ", Character.valueOf('X'), Block.blockSteel, Character.valueOf('Y'), new ItemStack(ItemMat, 1,0), Character.valueOf('Z'),Item.ingotIron});
		FMLRegistry.addRecipe(new ItemStack(ItemImitateSephirothSword), "  A"," A ", "B  ", 'A', Item.ingotIron, 'B', Item.swordSteel);
		FMLRegistry.addRecipe(new ItemStack(ItemSephirothSword, 1), new Object[]{"  A"," B ","C  ",Character.valueOf('A'),Item.ingotIron, Character.valueOf('B'),new ItemStack(Item.swordDiamond, 1, 0), Character.valueOf('C'),new ItemStack(ItemMat, 1, 1)});
		FMLRegistry.addRecipe(new ItemStack(ItemUltimateWeapon, 1), new Object[]{" A ","ABA"," C ", Character.valueOf('A'),Block.blockDiamond, Character.valueOf('B'), new ItemStack(MasterMateria, 1,-1), Character.valueOf('C'),Item.stick});
		FMLRegistry.addRecipe(new ItemStack(BlockMat, 1), new Object[]{"XYX","ZZZ", Character.valueOf('X'),Item.diamond, Character.valueOf('Y'),Block.blockGold, Character.valueOf('Z'),Block.obsidian});
		FMLRegistry.addRecipe(new ItemStack(HugeMateria), new Object[]{" A ","ABA"," A ",'A',Block.blockDiamond,'B',new ItemStack(MasterMateria,1,-1)});
		FMLRegistry.addShapelessRecipe(new ItemStack(ItemPortableEnchantChanger,1),  new Object[]{BlockMat});
		FMLRegistry.addShapelessRecipe(new ItemStack(ItemPortableEnchantmentTable,1),  new Object[]{Block.enchantmentTable});

		FMLRegistry.addShapelessRecipe(new ItemStack(MasterMateria,1,0), new Object[]{new ItemStack(MasterMateria,1,1), new ItemStack(MasterMateria,1,2), new ItemStack(MasterMateria,1,3), new ItemStack(MasterMateria,1,4), new ItemStack(MasterMateria,1,5)});
		if(this.Difficulty == 0)
			FMLRegistry.addRecipe(new ItemStack(Item.expBottle, 8), new Object[]{"XXX","XYX","XXX", Character.valueOf('X'),new ItemStack(Item.potion, 1, 0), Character.valueOf('Y'), new ItemStack(Item.diamond, 1)});
		FMLRegistry.addRecipe(new ItemStack(ItemExExpBottle, 8), new Object[]{"XXX","XYX","XXX", Character.valueOf('X'),new ItemStack(Item.expBottle, 1, 0), Character.valueOf('Y'), new ItemStack(Block.blockDiamond, 1)});

		FMLRegistry.addRecipe(new ItemStack(Block.dragonEgg,1), new Object[]{"XXX","XYX","XXX",Character.valueOf('X'), Item.eyeOfEnder, Character.valueOf('Y'), new ItemStack(MasterMateria,1,-1)});

		if(this.enableDungeonLoot)
			DungeonLootItemResist();
	}
	@Override
	public void modsLoaded()
	{
		addName();
		this.loadMTH = Loader.isModLoaded("mod_MultiToolHolders");
	}
	public void addRenderer(Map map)
	{
		super.addRenderer(map);
		map.put(EcEntityExExpBottle.class, new EcRenderItemThrowable(ItemExExpBottle.getIconFromDamage(0),0.5F, false));
		map.put(EcEntityMeteo.class, new EcRenderItemThrowable(22,MeteoSize, false));
		map.put(EcEntitySword.class, new EcRenderItemThrowable(Item.swordDiamond.iconIndex, 0.5F,true));
		map.put(EcEntityApOrb.class, new EcRenderApOrb());
	}
	private void addName()
	{
		//Register Name
		addName(BlockMat, "Enchant Changer", "エンチャントチェンジャー");
		addName(HugeMateria, "HugeMateria", "ヒュージマテリア");
		addName(ItemHugeMateria, "HugeMateria", "ヒュージマテリア");
		addName(ItemMat, "Materia", "マテリア");
		addName(MasterMateria, "Master Materia", "マスターマテリア");
		addName(ItemExExpBottle, "Ex Exp Bottle", "エンチャントの瓶EX");
		addName(ItemZackSword, "Buster Sword", "バスターソード");
		addName(ItemCloudSword, "Union Sword","合体剣");
		addName(ItemCloudSwordCore, "FirstSword","ファースト剣");
		addName(ItemSephirothSword, "Masamune Blade","正宗");
		addName(ItemUltimateWeapon, "Ultimate Weapon","究極剣");
		addName(ItemPortableEnchantChanger, "Portable Enchant Changer","携帯エンチャントチェンジャー");
		addName(ItemPortableEnchantmentTable, "Portable Enchantment Table","携帯エンチャントテーブル");
		addName(ItemImitateSephirothSword, "1/1 Masamune Blade(Imitation)","1/1 マサムネブレード");
		addLocalization("enchantment.Meteo", "Meteo", "メテオ");
		addLocalization("enchantment.Holy", "Holy", "ホーリー");
		addLocalization("enchantment.Teleport", "Teleport", "テレポ");
		addLocalization("enchantment.Floating", "Floating", "レビテト");
		addLocalization("enchantment.Thunder", "Thunder", "サンダー");
		addLocalization("ItemMateria.Base.name", "Inactive Materia","不活性マテリア");
		addLocalization("ItemMateria.name", "Materia","マテリア");
		addLocalization("container.materializer", "Enchant Changer", "エンチャントチェンジャー");
		addLocalization("container.hugeMateria", "HugeMateria", "ヒュージマテリア");
		addLocalization("Key.EcMagic", "Magic Key", "魔法キー");

		for(int i=0;i < EcItemMateria.MagicMateriaNum;i++)
		{
			addLocalization("ItemMateria." + EcItemMateria.MateriaMagicNames[i]+".name", EcItemMateria.MateriaMagicNames[i]+" Materia",EcItemMateria.MateriaMagicJPNames[i]+"マテリア");
		}
		for(int i = 0;i< EcItemMasterMateria.MasterMateriaNum;i++)
		{
			addLocalization("ItemMasterMateria." + i + ".name", "Master Materia of " + EcItemMasterMateria.MasterMateriaNames[i], EcItemMasterMateria.MasterMateriaJPNames[i] + "のマスターマテリア");
		}
		for(int i = 11;i<this.MaxLv + 1;i++)
		{
			addLocalization("enchantment.level."+i, Integer.toString(i), Integer.toString(i));
		}
	}
	private void addName(Object obj, String en, String ja)
	{
		FMLCommonHandler.instance().addNameForObject(obj, "en_US", en);
		FMLCommonHandler.instance().addNameForObject(obj, "ja_JP", ja);
	}
	private void addLocalization(String key, String en, String ja)
	{
		FMLCommonHandler.instance().addStringLocalization(key, "en_US", en);
		FMLCommonHandler.instance().addStringLocalization(key, "ja_JP", ja);
	}
	private void initMaps()
	{
		this.magicEnchantment.add(this.EnchantmentMeteoId);
		this.magicEnchantment.add(this.EnchantmentFloatId);
		this.magicEnchantment.add(this.EndhantmentHolyId);
		this.magicEnchantment.add(this.EnchantmentTelepoId);
		this.magicEnchantment.add(this.EnchantmentThunderId);
		this.apLimit.put(0, 2*aPBasePoint);
		this.apLimit.put(1, 1*aPBasePoint);
		this.apLimit.put(2, 1*aPBasePoint);
		this.apLimit.put(3, 1*aPBasePoint);
		this.apLimit.put(4, 1*aPBasePoint);
		this.apLimit.put(5, 1*aPBasePoint);
		this.apLimit.put(6, 1*aPBasePoint);
		this.apLimit.put(7, 1*aPBasePoint);
		this.apLimit.put(16, 2*aPBasePoint);
		this.apLimit.put(17, 1*aPBasePoint);
		this.apLimit.put(18, 1*aPBasePoint);
		this.apLimit.put(19, 1*aPBasePoint);
		this.apLimit.put(20, 1*aPBasePoint);
		this.apLimit.put(21, 3*aPBasePoint);
		this.apLimit.put(32, 1*aPBasePoint);
		this.apLimit.put(33, 1*aPBasePoint);
		this.apLimit.put(34, 1*aPBasePoint);
		this.apLimit.put(35, 2*aPBasePoint);
		this.apLimit.put(48, 2*aPBasePoint);
		this.apLimit.put(49, 1*aPBasePoint);
		this.apLimit.put(50, 1*aPBasePoint);
		this.apLimit.put(51, 1*aPBasePoint);
		String[] enchLevelLimits = this.enchantmentLevelLimits.split(",");
		String[] idAndLimit;
		for(int i = 0;i<enchLevelLimits.length;i++){
			idAndLimit = enchLevelLimits[i].trim().split(":");
			this.levelLimitMap.put(Integer.valueOf(idAndLimit[0]), Integer.valueOf(idAndLimit[1]));
		}
	}
	public static int getExpValue(EntityLiving entity)
	{
		return entity.experienceValue;
	}
	public static boolean isApLimit(int Id, int Lv, int ap)
	{
		if(mod_EnchantChanger.getApLimit(Id, Lv) < ap)
			return true;
		else
			return false;
	}
	public static int getApLimit(int Id, int Lv)
	{
		if(mod_EnchantChanger.apLimit.containsKey(Id))
		{
			return ((int)mod_EnchantChanger.apLimit.get(Id)) * (Lv / 5 + 1);
		}
		else
			return 150*(Lv / 5 + 1);
	}
	public static void addEnchantmentToItem(ItemStack item, Enchantment enchantment, int Lv)
	{
		if (item.stackTagCompound == null)
		{
			item.setTagCompound(new NBTTagCompound());
		}

		if (!item.stackTagCompound.hasKey("ench"))
		{
			item.stackTagCompound.setTag("ench", new NBTTagList("ench"));
		}

		NBTTagList var3 = (NBTTagList)item.stackTagCompound.getTag("ench");
		NBTTagCompound var4 = new NBTTagCompound();
		var4.setShort("id", (short)enchantment.effectId);
		var4.setShort("lvl", (short)(Lv));
		var3.appendTag(var4);
	}
	public static void removeEnchTag(NBTTagCompound nbt, String string)
	{
		try
		{
			HashMap map = ModLoader.getPrivateValue(NBTTagCompound.class, nbt, 0);
			map.remove(string);
		}
		catch (Exception e){}
	}
	public static int getMateriaEnchKind(ItemStack item)
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
		return EnchantmentKind;
	}
	public static int getMateriaEnchLv(ItemStack item)
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
	public void StringtoInt(String ExtraIDs, ArrayList<Integer> IDList)
	{
		if(!ExtraIDs.isEmpty())
		{
			StringBuffer IDNum=new StringBuffer();
			int shift=0;
			for(int i=0; i < ExtraIDs.length(); i++)
			{
				if(ExtraIDs.charAt(i) == ',')
				{
					for(int j=shift; j < i;j++)
					{
						IDNum.append(ExtraIDs.charAt(j));
					}
					//System.out.println(IDNum);
					IDList.add(Integer.valueOf(IDNum.toString()));
					shift = i+1;
					IDNum = new StringBuffer();
				}
			}
			for(int j=shift; j < ExtraIDs.length();j++)
			{
				IDNum.append(ExtraIDs.charAt(j));
			}
			//System.out.println(IDNum);
			IDList.add(Integer.valueOf(IDNum.toString()));
		}
		else
		{
			IDList.add(Integer.valueOf(0));
		}
	}
	public void StringtoArray(String ExtraNames, ArrayList<String> NameList)
	{
		if(!ExtraNames.isEmpty())
		{
			int shift=0;
			for(int i=0; i < ExtraNames.length(); i++)
			{
				if(ExtraNames.charAt(i) == ',')
				{
					NameList.add(ExtraNames.substring(shift,i));
					shift = i+1;
				}
			}
			NameList.add(ExtraNames.substring(shift));
		}
		else
		{
			NameList.add("");
		}
	}
	@Override
	public boolean onTickInGame(float var1, Minecraft var2)
	{
		GreatGospel(var2.thePlayer);
		Absorption(var2.theWorld,var2.thePlayer);
		this.LimitBreak();
		this.LimitBreakCoolDown();
		return true;
	}
	public void GreatGospel(EntityPlayer player)
	{
		if(player.capabilities.isCreativeMode)
		{
			return;
		}
		if((player.getFoodStats().getFoodLevel() < 0 && !YouAreTera) || !EcItemMateria.GGEnable)
		{
			player.capabilities.disableDamage = false;
			return;
		}
		if(this.checkMagicIteminInv(player,1))
		{
			player.capabilities.disableDamage = true;
			if(MpCount(1,GGMptime)&& !player.capabilities.isCreativeMode)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
			//System.out.println("GG");
		}
		else
		{
			player.capabilities.disableDamage = false;
		}
	}
	public void Absorption(World world,EntityPlayer player)
	{
		if(player.foodStats.getFoodLevel() < 20)
		{
			if(!MpCount(3,AbsorpMptime))
			{
				return;
			}
			if(this.checkMagicIteminInv(player,7))
			{
				List EntityList = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(AbsorpBoxSize, AbsorpBoxSize, AbsorpBoxSize));
				for (int i=0; i < EntityList.size();i++)
				{
					Entity entity=(Entity) EntityList.get(i);
					if(entity instanceof EntityLiving)
					{
						((EntityLiving) entity).attackEntityFrom(DamageSource.generic, 1);
						player.foodStats.setFoodLevel(player.foodStats.getFoodLevel()+1);
						System.out.println("Absorp!");
					}
					else
					{
						//entity.setDead();
					}
				}
			}
		}
	}
	public void LimitBreak()
	{
		for(int i=0;i < this.LimitBreakFlag.length;i++)
		{
			if(this.LimitBreakCount[i] <= 0)
			{
				if(this.LimitBreakFlag[i])
				{
					FMLClientHandler.instance().getClient().thePlayer.addChatMessage("LIMIT BREAK FINISH.");
				}
				this.LimitBreakFlag[i]=false;
				this.LimitBreakCount[i] = 0;
			}
			else
			{
				this.LimitBreakCount[i]--;
			}
		}
	}
	public void LimitBreakCoolDown()
	{
		for(int i=0;i < this.LimitBreakFlag.length;i++)
		{
			if(this.LimitBreakCoolDownCount[i]>0)
			{
				this.LimitBreakCoolDownCount[i]--;
			}
		}
	}
	public static boolean checkMagicItem(ItemStack itemstack, int magicNum)
	{
		if(itemstack == null)
		{
			return false;
		}
		else if(itemstack.getItem() instanceof EcItemMateria || itemstack.getItem() instanceof EcItemSword)
		{
			if(magicNum < 5)
				return EnchantmentHelper.getEnchantmentLevel(EcItemMateria.magicEnch[magicNum], itemstack) > 0;
			else if(itemstack.getItem() instanceof EcItemMateria)
			{
				return itemstack.getItemDamage()-1 == magicNum;
			}
			else return false;
		}
		else
		{
			return false;
		}
	}
	public static boolean checkMagicIteminInv(EntityPlayer entityplayer,int magicNum)
	{
		boolean ret=false;
		for(int i=0;i<9;i++)
		{
			ItemStack var1 = entityplayer.inventory.getStackInSlot(i);
			if(checkMagicItem(var1,magicNum))
				ret=checkMagicItem(var1,magicNum);
		}
		return ret;
	}
	public boolean MpCount(int par1, int par2)
	{
		Count[par1]++;
		if(Count[par1] > par2)
		{
			Count[par1] =0;
			//System.out.println("MPDecrease");
			return true;
		}
		else
		{
			return false;
		}
	}
	public void DungeonLootItemResist()
	{
		for(int i=0;i < EcItemMateria.MagicMateriaNum; i++)
		{
			ItemStack magic = new ItemStack(this.MateriaID, 1,1 + i);
			if(i<EcItemMateria.magicEnch.length)
				magic.addEnchantment(Enchantment.enchantmentsList[EcItemMateria.magicEnch[i]], 1);
			MinecraftForge.addDungeonLoot(magic, 0.01F);
		}
	}
}