package net.minecraft.src;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.*;
import net.minecraft.src.EnchantChanger.*;
import net.minecraft.src.MultiToolHolders.CommonProxy;
import net.minecraft.src.MultiToolHolders.ItemMultiToolHolder;

import java.io.File;
import java.util.*;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.FMLRegistry;

public class mod_MultiToolHolders extends BaseMod
{
	@Override
	public String getVersion() {
		return "1.1";
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
		ModLoader.setInGameHook(this, true, true);
		mc = ModLoader.getMinecraftInstance();
		instance =this;
		MinecraftForgeClient.preloadTexture("/ak/MultiToolHolders/textures/items.png");
		MinecraftForge.setGuiHandler(this, new CommonProxy());
		ModLoader.registerKey(this, OpenKey, false);
		ModLoader.registerKey(this, NextKey, false);
		ModLoader.registerKey(this, PrevKey, false);


		//Definition Item & Block & Enchant
		ItemMultiToolHolder3 = (new ItemMultiToolHolder(ItemIDShift - 256, 3)).setItemName(this.TextureDomain + "Holder3").setIconIndex(0);
		ItemMultiToolHolder5 = (new ItemMultiToolHolder(ItemIDShift - 256 + 1, 5)).setItemName(this.TextureDomain + "Holder5").setIconIndex(3);
		ItemMultiToolHolder9 = (new ItemMultiToolHolder(ItemIDShift - 256 + 2, 9)).setItemName(this.TextureDomain + "Holder9").setIconIndex(2);
		ItemMultiToolHolder7 = (new ItemMultiToolHolder(ItemIDShift - 256 + 3, 7)).setItemName(this.TextureDomain + "Holder7").setIconIndex(1);

		//Register Name
		ModLoader.addName(ItemMultiToolHolder3, "3-Way Tool Holder");
		ModLoader.addName(ItemMultiToolHolder5, "5-Way Tool Holder");
		ModLoader.addName(ItemMultiToolHolder9, "9-Way Tool Holder");
		ModLoader.addName(ItemMultiToolHolder7, "7-Way Tool Holder");

		ModLoader.addName(ItemMultiToolHolder3, "ja_JP","3-Wayツールホルダー");
		ModLoader.addName(ItemMultiToolHolder5, "ja_JP","5-Wayツールホルダー");
		ModLoader.addName(ItemMultiToolHolder9, "ja_JP","9-Wayツールホルダー");
		ModLoader.addName(ItemMultiToolHolder7, "ja_JP","7-Wayツールホルダー");

		ModLoader.addLocalization("container.toolholder", "ToolHolder");
		ModLoader.addLocalization("container.toolholder", "ja_JP", "ツールホルダー");
		ModLoader.addLocalization("container.toolholder", "ToolHolder");
		ModLoader.addLocalization("container.toolholder", "ja_JP", "ツールホルダー");
		ModLoader.addLocalization("Key.openToolHolder", "Open ToolHolder");
		ModLoader.addLocalization("Key.openToolHolder", "ja_JP", "ツールホルダーを開く");
		ModLoader.addLocalization("Key.nextToolHolder", "ToolHolder Next Slot");
		ModLoader.addLocalization("Key.nextToolHolder", "ja_JP", "次のスロット");
		ModLoader.addLocalization("Key.prevToolHolder", "ToolHolder Previous Slot");
		ModLoader.addLocalization("Key.prevToolHolder", "ja_JP", "前のスロット");

		//register
		MinecraftForgeClient.registerItemRenderer(ItemIDShift, (IItemRenderer) ItemMultiToolHolder3);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 1, (IItemRenderer) ItemMultiToolHolder5);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 2, (IItemRenderer) ItemMultiToolHolder9);
		MinecraftForgeClient.registerItemRenderer(ItemIDShift + 3, (IItemRenderer) ItemMultiToolHolder7);

		//register recipes
		ModLoader.addRecipe(new ItemStack(ItemMultiToolHolder3), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotIron,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		ModLoader.addRecipe(new ItemStack(ItemMultiToolHolder7), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotGold,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		ModLoader.addRecipe(new ItemStack(ItemMultiToolHolder9), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.diamond,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		ModLoader.addRecipe(new ItemStack(ItemMultiToolHolder5), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), new ItemStack(Item.dyePowder,1,4), Character.valueOf('B'),Block.chest, Character.valueOf('C'),Item.silk});
		if(this.Debug)
		{
			this.debugsystem();
		}
	}
	@Override
    public void keyboardEvent(KeyBinding kb)
    {
		if(kb == OpenKey && kb.isPressed())
		{
			openKeydown = !openKeydown;
		}
		else if(kb == NextKey && kb.isPressed())
		{
			nextKeydown = !nextKeydown;
		}
		else if(kb == PrevKey && kb.isPressed())
		{
			prevKeydown = !prevKeydown;
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