package net.minecraft.src.MultiToolHolders;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;
import net.minecraft.src.mod_MultiToolHolders;

import org.lwjgl.opengl.GL11;

public class GuiToolHolder extends GuiContainer
{
	private int Num;
	public GuiToolHolder(InventoryPlayer inventoryPlayer, IInventory par2IInventory, int num)
	{
		super(new ContainerToolHolder(inventoryPlayer, par2IInventory, num));
		this.Num = num;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		fontRenderer.drawString(StatCollector.translateToLocal("container.toolholder"), 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 40, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		//draw your Gui here, only thing you need to change is the path
		int texture;
		if(this.Num == 3)
			texture = mc.renderEngine.getTexture(mod_MultiToolHolders.GuiToolHolder3);
		else if(this.Num == 5)
			texture = mc.renderEngine.getTexture(mod_MultiToolHolders.GuiToolHolder5);
		else if(this.Num == 9)
			texture = mc.renderEngine.getTexture(mod_MultiToolHolders.GuiToolHolder9);
		else
			texture = 0;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
