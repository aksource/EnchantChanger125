package net.minecraft.src.EnchantChanger;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;

import org.lwjgl.opengl.GL11;

public class EcGuiHugeMateria extends GuiContainer {

	private EcTileEntityHugeMateria tileentity;
	public EcGuiHugeMateria (World world, InventoryPlayer inventoryPlayer, EcTileEntityHugeMateria te)
	{
		//the container is instanciated and passed to the superclass for handling
		super(new EcContainerHugeMateria(world, inventoryPlayer, te));
		this.tileentity = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		fontRenderer.drawString(StatCollector.translateToLocal("container.hugeMateria"), 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int texture;
		//draw your Gui here, only thing you need to change is the path
		texture = mc.renderEngine.getTexture(mod_EnchantChanger.EcGuiHuge);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		int var1;

		var1 = this.tileentity.getMaterializingProgressScaled(18);
		this.drawTexturedModalRect(x + 97, y + 34, 176, 0, var1 + 1, 16);
//		this.drawTexturedModalRect(x + 115, y + 15, 176, 16, 18, var1 + 1);
//		this.drawTexturedModalRect(x + 113 + 18 - var1, y + 34, 176 + 18 - var1, 34, var1 + 1, 16);
//		this.drawTexturedModalRect(x + 115, y + 51 + 18 - var1, 176, 50 + 18 - var1, 18, var1 + 1);

	}
	protected void keyTyped(char c, int keycode)
	{
		if (keycode == 1 || keycode == mc.gameSettings.keyBindInventory.keyCode)
		{
			mc.thePlayer.closeScreen();
		}
	}

	public void updateScreen()
	{
		super.updateScreen();
		if (!mc.thePlayer.isEntityAlive() || mc.thePlayer.isDead)
		{
			mc.thePlayer.closeScreen();
		}
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

}