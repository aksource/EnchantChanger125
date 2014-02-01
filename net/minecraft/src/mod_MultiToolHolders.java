package net.minecraft.src;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MultiToolHolders.CommonProxy;
import net.minecraft.src.MultiToolHolders.ItemMultiToolHolder;
import net.minecraft.src.forge.IItemRenderer;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.registry.FMLRegistry;

public class mod_MultiToolHolders extends BaseMod
{
	@Override
	public String getVersion() {
		return "1.2b";
	}
	@MLProp
	public static int ItemIDShift = 7000;
	public static  Item ItemMultiToolHolder3;
	public static  Item ItemMultiToolHolder5;
	public static  Item ItemMultiToolHolder7;
	public static  Item ItemMultiToolHolder9;
	@MLProp
	public static boolean Debug = false;

	@MLProp
	public static int textureSize = 16;

	public static String GuiToolHolder3 ="/mod_MultiToolHolders/textures/gui/ToolHolder3.png";
	public static String GuiToolHolder5 ="/mod_MultiToolHolders/textures/gui/ToolHolder5.png";
	public static String GuiToolHolder7 ="/mod_MultiToolHolders/textures/gui/ToolHolder7.png";
	public static String GuiToolHolder9 ="/mod_MultiToolHolders/textures/gui/ToolHolder9.png";
	public static String TextureDomain = "ak/MultiToolHolders:";
	public static String itemTexture = "/mod_MultiToolHolders/textures/items.png";

	public static KeyBinding OpenKey = new KeyBinding("Key.openToolHolder",Keyboard.KEY_F);
	public static KeyBinding NextKey = new KeyBinding("Key.nextToolHolder",Keyboard.KEY_T);
	public static KeyBinding PrevKey = new KeyBinding("Key.prevToolHolder",Keyboard.KEY_R);
	public static KeyBinding[] Keys = new KeyBinding[]{OpenKey, NextKey,PrevKey};

	public static boolean openKeydown = false;
	public static boolean nextKeydown = false;
	public static boolean prevKeydown = false;

	public static int guiIdHolder3 = 0;
	public static int guiIdHolder5 = 1;
	public static int guiIdHolder7 = 3;
	public static int guiIdHolder9 = 2;
	public static Minecraft mc;
	public static mod_MultiToolHolders instance;

//    @Override
//    public String getPriorities()
//    {
//        return "after:*";
//    }
	public mod_MultiToolHolders(){}
	public void load() {
		//initialize
		ModLoaderHelper.updateStandardTicks(this, true, true);
		mc = FMLClientHandler.instance().getClient();
		instance =this;
		MinecraftForgeClient.preloadTexture("/ak/MultiToolHolders/textures/items.png");
		MinecraftForge.setGuiHandler(this, new CommonProxy());
		FMLClientHandler.instance().registerKeyHandler(this, OpenKey, false);
		FMLClientHandler.instance().registerKeyHandler(this, NextKey, false);
		FMLClientHandler.instance().registerKeyHandler(this, PrevKey, false);


		//Definition Item & Block & Enchant
		ItemMultiToolHolder3 = (new ItemMultiToolHolder(ItemIDShift - 256, 3)).setItemName(this.TextureDomain + "Holder3").setIconIndex(0);
		ItemMultiToolHolder5 = (new ItemMultiToolHolder(ItemIDShift - 256 + 1, 5)).setItemName(this.TextureDomain + "Holder5").setIconIndex(3);
		ItemMultiToolHolder9 = (new ItemMultiToolHolder(ItemIDShift - 256 + 2, 9)).setItemName(this.TextureDomain + "Holder9").setIconIndex(2);
		ItemMultiToolHolder7 = (new ItemMultiToolHolder(ItemIDShift - 256 + 3, 7)).setItemName(this.TextureDomain + "Holder7").setIconIndex(1);

		//Register Name
		addName(ItemMultiToolHolder3, "3-Way Tool Holder","3-Wayツールホルダー");
		addName(ItemMultiToolHolder5, "5-Way Tool Holder","5-Wayツールホルダー");
		addName(ItemMultiToolHolder9, "9-Way Tool Holder","9-Wayツールホルダー");
		addName(ItemMultiToolHolder7, "7-Way Tool Holder","7-Wayツールホルダー");

		addLocalization("container.toolholder", "ToolHolder", "ツールホルダー");
		addLocalization("Key.openToolHolder", "Open ToolHolder", "ツールホルダーを開く");
		addLocalization("Key.nextToolHolder", "ToolHolder Next Slot", "次のスロット");
		addLocalization("Key.prevToolHolder", "ToolHolder Previous Slot", "前のスロット");

		//register
		MinecraftForgeClient.registerItemRenderer(ItemIDShift, (IItemRenderer) ItemMultiToolHolder3);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 1, (IItemRenderer) ItemMultiToolHolder5);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 2, (IItemRenderer) ItemMultiToolHolder9);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 3, (IItemRenderer) ItemMultiToolHolder7);

		//register recipes
		FMLRegistry.addRecipe(new ItemStack(ItemMultiToolHolder3), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotIron,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		FMLRegistry.addRecipe(new ItemStack(ItemMultiToolHolder7), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotGold,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		FMLRegistry.addRecipe(new ItemStack(ItemMultiToolHolder9), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.diamond,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		FMLRegistry.addRecipe(new ItemStack(ItemMultiToolHolder5), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), new ItemStack(Item.dyePowder,1,4), Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		if(this.Debug)
		{
			this.debugsystem();
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
	@Override
    public void keyboardEvent(KeyBinding kb)
    {
		if(kb == OpenKey)
		{
			openKeydown = kb.pressed;
		}
		else if(kb == NextKey)
		{
			nextKeydown = kb.pressed;
		}
		else if(kb == PrevKey)
		{
			prevKeydown = kb.pressed;
		}
    }
	public void debugsystem()
	{
	}
	@Override
	public boolean onTickInGame(float var1, Minecraft var2)
	{
		return true;
	}
}