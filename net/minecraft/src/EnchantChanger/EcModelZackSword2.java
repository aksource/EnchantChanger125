package net.minecraft.src.EnchantChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.mod_EnchantChanger;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
public class EcModelZackSword2 extends ModelBase
{
  //fields
    ModelRenderer Sword;
    private Minecraft MC = mod_EnchantChanger.mc;

    public EcModelZackSword2()
    {
        textureWidth = 64;
        textureHeight = 32;
        setTextureOffset("Sword.edge1", 20, 0);
        setTextureOffset("Sword.edge2", 20, 2);
        setTextureOffset("Sword.edge3", 20, 4);
        setTextureOffset("Sword.edge4", 20, 6);
        setTextureOffset("Sword.edge5", 20, 8);
        setTextureOffset("Sword.edge6", 20, 10);
        setTextureOffset("Sword.edge7", 20, 10);
        setTextureOffset("Sword.edge8", 20, 10);
        setTextureOffset("Sword.tsuba", 8, 21);
        setTextureOffset("Sword.tsuka", 6, 24);

        Sword = new ModelRenderer(this, "Sword");
        Sword.setRotationPoint(8F, -3F, -0.5F);
        setRotation(Sword, 0F, 0F, 0F);
        Sword.mirror = false;
          Sword.addBox("edge1", -2.5F, 23F, 0F, 1, 1, 1);
          Sword.addBox("edge2", -2.5F, 22F, 0F, 2, 1, 1);
          Sword.addBox("edge3", -2.5F, 21F, 0F, 3, 1, 1);
          Sword.addBox("edge4", -2.5F, 20F, 0F, 4, 1, 1);
          Sword.addBox("edge5", -2.5F, 19F, 0F, 5, 1, 1);
          Sword.addBox("edge6", -2.5F, 13F, 0F, 6, 6, 1);
          Sword.addBox("edge7", -2.5F, 7F, 0F, 6, 6, 1);
          Sword.addBox("edge8", -2.5F, 1F, 0F, 6, 6, 1);
          Sword.addBox("tsuba", -3F, 0F, -0.5F, 7, 1, 2);
          Sword.addBox("tsuka", 0F, -6F, 0F, 1, 6, 1);
      }
/**
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  Minecraft mc = mod_EnchantChanger.mc;
	  GL11.glPushMatrix();
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/mod_EnchantChanger/SephirothSword.png"));
	  super.render(entity, f, f1, f2, f3, f4, f5);
	  setRotationAngles(f, f1, f2, f3, f4, f5);
      Sword.render(f5);
  }
*/
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
	  super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }
	public void renderItem(ItemStack pitem, EntityLiving pentity) {

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.MC.renderEngine.getTexture(mod_EnchantChanger.EcSword2PNG));

/**
	    if (pentity instanceof EntityPlayer && ((EntityPlayer)pentity).isUsingItem()) {
			//Guard
	    	//GL11.glTranslatef(-1.0F, 0.1F, 0.3F);
			GL11.glRotatef(25.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
			//ViewChange
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1.3F, 0.0F, 0.5F);
				GL11.glRotatef(70F, 0F, 0F, 1F);
			}
		} else {
			//ViewChange
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1F, -1F, 0.5F);
				GL11.glRotatef(90F, 0F, 1F, 0F);
				GL11.glRotatef(20F, 0F, 0F, 1F);
			}
		}
*/
	    GL11.glTranslatef(0F, 0F, 0F);
	    GL11.glScalef(1F, 1F, 0.4F);
	    GL11.glRotatef(0F, 1.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(50.0F, 0.0F, 0.0F, 1.0F);

	    Sword.render(0.07F);
	    GL11.glPopMatrix();
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
