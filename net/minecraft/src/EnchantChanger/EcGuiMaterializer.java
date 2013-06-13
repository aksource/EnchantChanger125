package net.minecraft.src.EnchantChanger;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;

import org.lwjgl.opengl.GL11;

public class EcGuiMaterializer extends GuiContainer {

	public EcGuiMaterializer (World world, InventoryPlayer inventoryPlayer)
	{
		//the container is instanciated and passed to the superclass for handling
		super(new EcContainerMaterializer(world, inventoryPlayer));
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		fontRenderer.drawString(StatCollector.translateToLocal("container.materializer"), 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int texture;
		//draw your Gui here, only thing you need to change is the path
		texture = mc.renderEngine.getTexture(mod_EnchantChanger.EcGuiMaterializer);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
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