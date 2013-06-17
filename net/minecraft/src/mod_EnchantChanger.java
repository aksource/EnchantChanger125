package net.minecraft.src;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.*;
import net.minecraft.src.EnchantChanger.*;
import java.io.File;
import java.util.*;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.FMLRegistry;

public class mod_EnchantChanger extends BaseMod
{
	@Override
	public String getVersion() {
		return "1.5o";
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
//	public static String ExtraEnchantName = "";
//	public static ArrayList<String> ExtraEnchantNameArray = new ArrayList<String>();
//	public static String ExtraEnchantIds = "";
//	public static ArrayList<Integer> ExtraEnchantIdArray = new ArrayList<Integer>();
	@MLProp(info="Item damege decrease MateriaLv")
	public static boolean DecMateriaLv = false;
	@MLProp(info="You are Tera in FF4")
	public static boolean YouAreTera = false;
	@MLProp(info="Materia Potion Minutes")
	public static int MateriaPotionMinutes = 10;
	@MLProp(info="Difficulty Ex:0=Easy,1=Normal,2=Hard")
	public static int Difficulty =1;
	public static int MaxLv = 127;
	public static int VanillaEnchNum = 21;
	public static int materiamax = VanillaEnchNum*MaxLv;

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

	ItemStack[] Magic = new ItemStack[EcItemMateria.MagicMateriaNum];
	public int[] VanillaEnchant = new int[]{0,1,2,3,4,5,6,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	public int[] Count= new int[]{0,0,0,0,0,0,0,0,0,0};
	public static int[] LimitBreakCount = new int[]{0,0,0};
	public static int[] LimitBreakCoolDownCount = new int[]{0,0,0};
	public static boolean[] LimitBreakFlag = new boolean[]{false,false,false};
	public int FlightMptime=20*3;
	public int GGMptime=20*1;
	public int HasteMptime=20*3;
	public int AbsorpMptime=20*3;
	public int AttackSeveralMpTime=20*1;
	public int AttackTime=0;
	public double AbsorpBoxSize=5D;
	public static Vec3D GateCoord =null;
	public boolean incompatible=false;
	public boolean ExtraEnchCheckbool = false;
	public static KeyBinding MagicKey = new KeyBinding("EcMagicKey",88);
	public static boolean MagicKeyDown = false;

	public static int MaterializerGuiId = 0;
	public static int PortableEnchantmentTableGuiId=1;
	public static int HugeMateriaGuiId=2;
//	public static int RenderHugeId;

	public static String EcSprites ="/mod_EnchantChanger/gui/items.png";
	public static String EcTerrain = "/mod_EnchantChanger/terrain.png";
	public static String EcZackSwordPNG ="/mod_EnchantChanger/item/ZackSword.png";
	public static String EcSephirothSwordPNG ="/mod_EnchantChanger/item/SephirothSword.png";
//	public static String EcSword2PNG ="/mod_EnchantChanger/item/Sword-3Dtrue.png";
//	public static String EcCloudSwordPNG ="/mod_EnchantChanger/item/CloudSword.png";
	public static String EcCloudSword2PNG ="/mod_EnchantChanger/item/CloudSword-3Dtrue.png";
//	public static String EcCloudSwordCorePNG ="/mod_EnchantChanger/item/CloudSwordCore.png";
	public static String EcCloudSwordCore2PNG ="/mod_EnchantChanger/item/CloudSwordCore-3Dtrue.png";
	public static String EcUltimateWeaponPNG ="/mod_EnchantChanger/item/UltimaWeapon.png";
	public static String EcGuiMaterializer ="/mod_EnchantChanger/gui/materializer.png";
	public static String EcGuiHuge = "/mod_EnchantChanger/gui/HugeMateriaContainer.png";
	public static String EcHugetex ="/mod_EnchantChanger/item/hugemateriatex.png";
//	public static String EcGuiMaterializer2 ="/mod_EnchantChanger/gui/materializer02.png";

	public static Minecraft mc;
	public static mod_EnchantChanger instance;

	private Entity entity;
	private boolean CSCmode;
    @Override
    public String getPriorities()
    {
        return "after:*";
    }
	public mod_EnchantChanger(){}
	public void load() {
		//initialize
		ModLoader.setInGameHook(this, true, true);
		mc = ModLoader.getMinecraftInstance();
		instance =this;
//		incompatible=checkCompatibility();
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/gui/items.png");
		MinecraftForgeClient.preloadTexture("/mod_EnchantChanger/terrain.png");
		MinecraftForge.setGuiHandler(this, new EcGuiHandler());
		MinecraftForge.registerEntityLivingHandler(new EcLivingHandler());
		ModLoader.registerKey(this, MagicKey, false);
//		RenderHugeId = ModLoader.getUniqueBlockModelID(this, true);

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
		BlockMat = (new EcBlockMaterializer(EnchantChangerID));
		HugeMateria = new EcBlockHugeMateria(HugeMateriaID);
		ItemHugeMateria = new EcItemHugeMateria(HugeMateria.blockID - 256).setIconIndex(26).setItemName("ItemhugeMateria");
		Meteo = new EcEnchantmentMeteo(this.EnchantmentMeteoId,0);
		Holy = new EcEnchantmentHoly(this.EndhantmentHolyId,0);
		Telepo = new EcEnchantmentTeleport(this.EnchantmentTelepoId,0);
		Float = new EcEnchantmentFloat(this.EnchantmentFloatId,0);
		Thunder = new EcEnchantmentThunder(this.EnchantmentThunderId,0);
//		AddExtraEnchantment(ExtraEnchantIdArray,ExtraEnchantNameArray);
//		this.forpsEnchant();
//		this.forTC2();
//		this.forRP2World();
		//Register Name
		ModLoader.addName(BlockMat, "Enchant Changer");
		ModLoader.addName(BlockMat, "ja_JP","エンチャントチェンジャー");
		ModLoader.addName(HugeMateria, "HugeMateria");
		ModLoader.addName(HugeMateria, "ja_JP","ヒュージマテリア");
		ModLoader.addName(ItemHugeMateria, "HugeMateria");
		ModLoader.addName(ItemHugeMateria, "ja_JP","ヒュージマテリア");
		ModLoader.addName(ItemMat, "ja_JP", "マテリア");
		ModLoader.addName(ItemMat, "en_US", "Materia");
		ModLoader.addName(MasterMateria, "ja_JP", "マスターマテリア");
		ModLoader.addName(MasterMateria, "en_US", "Master Materia");
		ModLoader.addName(ItemExExpBottle, "ja_JP", "エンチャントの瓶EX");
		ModLoader.addName(ItemExExpBottle, "en_US", "Ex Exp Bottle");
		ModLoader.addName(ItemZackSword, "Buster Sword");
		ModLoader.addName(ItemZackSword, "ja_JP","バスターソード");
		ModLoader.addName(ItemCloudSword, "Union Sword");
		ModLoader.addName(ItemCloudSword, "ja_JP","合体剣");
		ModLoader.addName(ItemCloudSwordCore, "FirstSword");
		ModLoader.addName(ItemCloudSwordCore,"ja_JP" ,"ファースト剣");
		ModLoader.addName(ItemSephirothSword, "Masamune Blade");
		ModLoader.addName(ItemSephirothSword,"ja_JP","正宗");
		ModLoader.addName(ItemUltimateWeapon, "Ultimate Weapon");
		ModLoader.addName(ItemUltimateWeapon,"ja_JP","究極剣");
		ModLoader.addName(ItemPortableEnchantChanger, "Portable Enchant Changer");
		ModLoader.addName(ItemPortableEnchantChanger, "ja_JP","携帯エンチャントチェンジャー");
		ModLoader.addName(ItemPortableEnchantmentTable, "Portable Enchantment Table");
		ModLoader.addName(ItemPortableEnchantmentTable, "ja_JP","携帯エンチャントテーブル");
		ModLoader.addLocalization("enchantment.Meteo", "Meteo");
		ModLoader.addLocalization("enchantment.Holy", "Holy");
		ModLoader.addLocalization("enchantment.Teleport", "Teleport");
		ModLoader.addLocalization("enchantment.Floating", "Floating");
		ModLoader.addLocalization("enchantment.Thunder", "Thunder");
		ModLoader.addLocalization("enchantment.arrowDamage","ja_JP", "矢ダメージ増加");
		ModLoader.addLocalization("ItemMateria.Base.name", "Inactive Materia");
		ModLoader.addLocalization("ItemMateria.Base.name", "ja_JP","不活性マテリア");
		ModLoader.addLocalization("container.materializer", "Enchant Changer");
		ModLoader.addLocalization("container.materializer", "ja_JP", "エンチャントチェンジャー");
		ModLoader.addLocalization("container.hugeMateria", "HugeMateria");
		ModLoader.addLocalization("container.hugeMateria", "ja_JP", "ヒュージマテリア");
//		for (int i = 1; i <= materiamax; i++)
//		{
//			int var1 = (i-1) / MaxLv;
//			int var2 = (i % MaxLv != 0)? i% MaxLv : MaxLv;
//			ModLoader.addLocalization("ItemMateria." + Enchantment.enchantmentsList[this.VanillaEnchant[var1]].getName()+"."+var2+".name", " Materia of "+EcItemMateria.MateriaNames[var1]+" Lv."+var2);
//			ModLoader.addLocalization("ItemMateria." + Enchantment.enchantmentsList[this.VanillaEnchant[var1]].getName()+"."+var2+".name", "ja_JP",EcItemMateria.MateriaJPNames[var1]+"マテリア Lv."+var2);
//		}
		for(int i=0;i < EcItemMateria.MagicMateriaNum;i++)
		{
			Magic[i]=new ItemStack(ItemMat, 1, materiamax + 1 +  i * MaxLv);
			ModLoader.addLocalization("ItemMateria." + EcItemMateria.MateriaMagicNames[i]+".name", EcItemMateria.MateriaMagicNames[i]+" Materia");
			ModLoader.addLocalization("ItemMateria." + EcItemMateria.MateriaMagicNames[i]+".name", "ja_JP",EcItemMateria.MateriaMagicJPNames[i]+"マテリア");
		}
//		for(int i=0; i < ExtraEnchantNameArray.size();i++)
//		{
//			for(int j=1;j<=MaxLv;j++)
//			{
//				ModLoader.addLocalization("ItemMateria."+ ExtraEnchantNameArray.get(i) +"."+ j + ".name", "Materia of "+ ExtraEnchantNameArray.get(i) + " Lv."+ j );
//			}
//		}
		for(int i = 0;i< EcItemMasterMateria.MasterMateriaNum;i++)
		{
			ModLoader.addLocalization("ItemMasterMateria." + i + ".name", "Master Materia of " + EcItemMasterMateria.MasterMateriaNames[i]);
			ModLoader.addLocalization("ItemMasterMateria." + i + ".name","ja_JP", EcItemMasterMateria.MasterMateriaJPNames[i] + "のマスターマテリア");
		}
		for(int i = 11;i<this.MaxLv + 1;i++)
		{
			ModLoader.addLocalization("enchantment.level."+i, i+"");
		}

		//register
		ModLoader.registerBlock(BlockMat);
//		ModLoader.registerBlock(HugeMateria, EcItemHugeMateria.class);
//		ModLoader.registerBlock(HugeMateria);
		ModLoader.registerTileEntity(EcTileEntityMaterializer.class, "container.materializer");
		ModLoader.registerTileEntity(EcTileEntityHugeMateria.class, "container.hugeMateria", new EcRenderHugeMateria());
		MinecraftForgeClient.registerItemRenderer(SephirothSwordItemID, (IItemRenderer) ItemSephirothSword);
		MinecraftForgeClient.registerItemRenderer(ZackSwordItemID, (IItemRenderer)ItemZackSword);
		MinecraftForgeClient.registerItemRenderer(FirstSwordItemID, (IItemRenderer)ItemCloudSwordCore);
		MinecraftForgeClient.registerItemRenderer(CloudSwordItemID, (IItemRenderer)ItemCloudSword);
		MinecraftForgeClient.registerItemRenderer(UltimateWeaponItemID, (IItemRenderer)ItemUltimateWeapon);
		MinecraftForgeClient.registerItemRenderer(MateriaID, (IItemRenderer) ItemMat);
		ModLoader.registerEntityID(EcEntityExExpBottle.class, "ItemExExpBottle", 500);
		ModLoader.registerEntityID(EcEntityMeteo.class, "Meteo", 501);
		ModLoader.registerEntityID(EcEntitySword.class, "EntitySword", 502);
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
		FMLRegistry.addRecipe(new EcMateriaRecipe());
		ModLoader.addShapelessRecipe(new ItemStack(ItemMat,1, 0), new Object[]{new ItemStack(Item.diamond, 1), new ItemStack(Item.enderPearl, 1)});
		ModLoader.addRecipe(new ItemStack(ItemZackSword, 1), new Object[]{" X","XX"," Y", Character.valueOf('X'),Block.blockSteel, Character.valueOf('Y'),Item.ingotIron});
		ModLoader.addRecipe(new ItemStack(ItemCloudSwordCore, 1), new Object[]{" X ","XYX"," Z ", Character.valueOf('X'), Block.blockSteel, Character.valueOf('Y'), new ItemStack(ItemMat, 1,0), Character.valueOf('Z'),Item.ingotIron});
//		ModLoader.addRecipe(new ItemStack(ItemCloudSword , 1), new Object[]{"BCB","DED", Character.valueOf('C'),new ItemStack(Item.swordSteel, 1,0), Character.valueOf('B'),new ItemStack(Item.swordGold, 1, 0),Character.valueOf('E'),ItemCloudSwordCore, Character.valueOf('D'),new ItemStack(Item.swordDiamond, 1, 0)});
		ModLoader.addRecipe(new ItemStack(ItemSephirothSword, 1), new Object[]{"  A"," B ","C  ",Character.valueOf('A'),Item.ingotIron, Character.valueOf('B'),new ItemStack(Item.swordDiamond, 1, 0), Character.valueOf('C'),Magic[0]});
		ModLoader.addRecipe(new ItemStack(ItemUltimateWeapon, 1), new Object[]{" A ","ABA"," C ", Character.valueOf('A'),Block.blockDiamond, Character.valueOf('B'), new ItemStack(MasterMateria, 1,-1), Character.valueOf('C'),Item.stick});
		ModLoader.addRecipe(new ItemStack(BlockMat, 1), new Object[]{"XYX","ZZZ", Character.valueOf('X'),Item.diamond, Character.valueOf('Y'),Block.blockGold, Character.valueOf('Z'),Block.obsidian});
		ModLoader.addRecipe(new ItemStack(HugeMateria), new Object[]{" A ","ABA"," A ",'A',Block.blockDiamond,'B',new ItemStack(MasterMateria,1,-1)});
		ModLoader.addShapelessRecipe(new ItemStack(ItemPortableEnchantChanger,1),  new Object[]{BlockMat});
		ModLoader.addShapelessRecipe(new ItemStack(ItemPortableEnchantmentTable,1),  new Object[]{Block.enchantmentTable});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,0), new Object[]{new ItemStack(MasterMateria,1,1), new ItemStack(MasterMateria,1,2), new ItemStack(MasterMateria,1,3), new ItemStack(MasterMateria,1,4), new ItemStack(MasterMateria,1,5)});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,1), new Object[]{new ItemStack(ItemMat,1, 0*this.MaxLv + 10),new ItemStack(ItemMat,1, 1*this.MaxLv + 10), new ItemStack(ItemMat,1, 2*this.MaxLv + 10), new ItemStack(ItemMat,1, 3*this.MaxLv + 10), new ItemStack(ItemMat,1, 4*this.MaxLv + 10)});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,2), new Object[]{new ItemStack(ItemMat,1, 5*this.MaxLv + 10),new ItemStack(ItemMat,1, 6*this.MaxLv + 10)});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,3), new Object[]{new ItemStack(ItemMat,1, 7*this.MaxLv + 10),new ItemStack(ItemMat,1, 8*this.MaxLv + 10), new ItemStack(ItemMat,1, 9*this.MaxLv + 10), new ItemStack(ItemMat,1, 10*this.MaxLv + 10), new ItemStack(ItemMat,1, 11*this.MaxLv + 10), new ItemStack(ItemMat,1, 12*this.MaxLv + 10)});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,4), new Object[]{new ItemStack(ItemMat,1, 13*this.MaxLv + 10),new ItemStack(ItemMat,1, 14*this.MaxLv + 10), new ItemStack(ItemMat,1, 15*this.MaxLv + 10), new ItemStack(ItemMat,1, 16*this.MaxLv + 10)});
		ModLoader.addShapelessRecipe(new ItemStack(MasterMateria,1,5), new Object[]{new ItemStack(ItemMat,1, 17*this.MaxLv + 10),new ItemStack(ItemMat,1, 18*this.MaxLv + 10), new ItemStack(ItemMat,1, 19*this.MaxLv + 10), new ItemStack(ItemMat,1, 20*this.MaxLv + 10)});

		ModLoader.addRecipe(new ItemStack(Item.expBottle, 8), new Object[]{"XXX","XYX","XXX", Character.valueOf('X'),new ItemStack(Item.potion, 1, 0), Character.valueOf('Y'), new ItemStack(Item.diamond, 1)});
		ModLoader.addRecipe(new ItemStack(ItemExExpBottle, 8), new Object[]{"XXX","XYX","XXX", Character.valueOf('X'),new ItemStack(Item.expBottle, 1, 0), Character.valueOf('Y'), new ItemStack(Block.blockDiamond, 1)});

		ModLoader.addRecipe(new ItemStack(Block.dragonEgg,1), new Object[]{"XXX","XYX","XXX",Character.valueOf('X'), Item.eyeOfEnder, Character.valueOf('Y'), new ItemStack(MasterMateria,1,-1)});

		if(this.Debug)
		{
			this.debugsystem();
		}
		DungeonLootItemResist();
	}
	public void addRenderer(Map map)
	{
		super.addRenderer(map);
		map.put(EcEntityExExpBottle.class, new EcRenderItemThrowable(ItemExExpBottle.getIconFromDamage(0),0.5F, false));
		map.put(EcEntityMeteo.class, new EcRenderItemThrowable(22,MeteoSize, false));
		map.put(EcEntitySword.class, new EcRenderItemThrowable(Item.swordDiamond.iconIndex, 0.5F,true));
	}
	public void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID)
	{

	}
	public boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID)
	{

		return super.renderWorldBlock(renderer, world, x, y, z, block, modelID);
	}
	public void debugsystem()
	{
		ModLoader.addShapelessRecipe(Magic[3], new Object[]{Block.dirt});
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
//	public void AddExtraEnchantment(ArrayList<Integer> IDList, ArrayList<String> NameList)
//	{
//		int[] vanilla = new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,32,33,34,35,48,49,50,51,this.EnchantmentMeteoId,this.EndhantmentHolyId,this.EnchantmentTelepoId,this.EnchantmentFloatId,this.EnchantmentThunderId};
//
//		boolean isvanillaenchid=false;
//		for (int i=0;i<256;i++)
//		{
//			if(Enchantment.enchantmentsList[i] != null)
//			{
//				for (int j=0;j<vanilla.length;j++)
//				{
//					if(i == vanilla[j])
//						isvanillaenchid=true;
//				}
//				if(! isvanillaenchid)
//				{
//					IDList.add(i);
//					String EnchName = StatCollector.translateToLocal(Enchantment.enchantmentsList[i].getName());
//					NameList.add(EnchName);
//				}
//				else
//				{
//					isvanillaenchid = false;
//				}
//			}
//		}
//	}
	@Override
	public boolean onTickInGame(float var1, Minecraft var2)
	{
		GreatGospel(var2.thePlayer);
		Absorption(var2.theWorld,var2.thePlayer);
		this.LimitBreak();
		this.LimitBreakCoolDown();
		this.MagicKey();
		if(this.MagicKeyDown)
		{
			this.checkMagic(var2.theWorld, var2.thePlayer);
		}
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
	public void checkMagic(World world, EntityPlayer player)
	{
		ItemStack itemstack = player.getHeldItem();
		if(itemstack != null && itemstack.getItem() instanceof EcItemSword)
		{
			EcItemSword.doMagic(itemstack, world, player);
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
					ModLoader.getMinecraftInstance().thePlayer.addChatMessage("LIMIT BREAK FINISH.");
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
			if(itemstack.getItem() instanceof EcItemMateria)
			{
				if(itemstack.getItemDamage() >= materiamax + 1 + magicNum*MaxLv && itemstack.getItemDamage() <= materiamax + 100 + magicNum*MaxLv)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else if(magicNum ==3)
			{
				return EcItemSword.hasFloat(itemstack);
			}
			else
			{
				return false;
			}
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
//	public boolean checkCompatibility()
//	{
//		return ModLoader.isModLoaded("mod_EE");
//	}
//	public void forpsEnchant()
//	{
//		List<BaseMod> Modslist=ModLoader.getLoadedMods();
//		for (int i=0 ; i < Modslist.size();i++)
//		{
//			//System.out.println(Modslist.get(i));
//			if(Modslist.get(i).getName().equals("mod_ThaumCraft"))
//			{
//				this.ExtraEnchantIdArray.add(7);
//				this.ExtraEnchantNameArray.add("Thorns");
//
//			}
//		}
//	}
//	public void forTC2()
//	{
//		if(ModLoader.isModLoaded("mod_ThaumCraft"))
//		{
//			this.ExtraEnchantIdArray.add(200);
//			this.ExtraEnchantNameArray.add("Vampiric");
//			this.ExtraEnchantIdArray.add(201);
//			this.ExtraEnchantNameArray.add("Soulstealer");
//			this.ExtraEnchantIdArray.add(205);
//			this.ExtraEnchantNameArray.add("Repair");
//			this.ExtraEnchantIdArray.add(206);
//			this.ExtraEnchantNameArray.add("Relic");
//			this.ExtraEnchantIdArray.add(208);
//			this.ExtraEnchantNameArray.add("Potency");
//		}
//	}
//	public void forRP2World()
//	{
//		if(ModLoader.isModLoaded("mod_RedPowerWorld"))
//		{
//			this.ExtraEnchantIdArray.add(79);
//			this.ExtraEnchantNameArray.add("disjunction");
//		}
//	}
	public void MagicKey()
	{
		this.MagicKeyDown = Keyboard.isKeyDown(this.MagicKey.keyCode) && !this.MagicKeyDown;
	}
	public void DungeonLootItemResist()
	{
		for (int i=0;i<this.Magic.length;i++)
		{
			MinecraftForge.addDungeonLoot(this.Magic[i], 0.01F);
		}
		for(int i =0;i<this.VanillaEnchNum;i++)
		{
			int lv = Enchantment.enchantmentsList[EcItemMateria.vanillaEnch[i]].getMaxLevel();
			MinecraftForge.addDungeonLoot(new ItemStack(ItemMat,1, i*MaxLv + lv) , 0.01F);
		}
	}
}