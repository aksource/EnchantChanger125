package net.minecraft.src.EnchantChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.mod_EnchantChanger;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class EcModelZackSword extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    private Minecraft MC = mod_EnchantChanger.mc;

    public EcModelZackSword()
    {
        textureWidth = 128;
        textureHeight = 64;

          Shape1 = new ModelRenderer(this, 0, -1);
          Shape1.addBox(-8F, 1F, 0F, 17, 64, 1);
          Shape1.setRotationPoint(0F, 0F, 0F);
          Shape1.setTextureSize(128, 64);
          Shape1.mirror = false;
          setRotation(Shape1, 0F, 0F, 0F);
          Shape2 = new ModelRenderer(this, 36, 0);
          Shape2.addBox(-9F, 0F, -1F, 19, 1, 3);
          Shape2.setRotationPoint(0F, 0F, 0F);
          Shape2.setTextureSize(128, 64);
          Shape2.mirror = false;
          setRotation(Shape2, 0F, 0F, 0F);
          Shape3 = new ModelRenderer(this, 36, 4);
          Shape3.addBox(-8.5F, -3F, -0.5F, 18, 3, 2);
          Shape3.setRotationPoint(0F, 0F, 0F);
          Shape3.setTextureSize(128, 64);
          Shape3.mirror = false;
          setRotation(Shape3, 0F, 0F, 0F);
          Shape4 = new ModelRenderer(this, 36, 0);
          Shape4.addBox(-9F, -4F, -1F, 19, 1, 3);
          Shape4.setRotationPoint(0F, 0F, 0F);
          Shape4.setTextureSize(128, 64);
          Shape4.mirror = false;
          setRotation(Shape4, 0F, 0F, 0F);
          Shape5 = new ModelRenderer(this, 36, 9);
          Shape5.addBox(-0.5F, -22F, -0.5F, 2, 18, 2);
          Shape5.setRotationPoint(0F, 0F, 0F);
          Shape5.setTextureSize(128, 64);
          Shape5.mirror = false;
          setRotation(Shape5, 0F, 0F, 0F);
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
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.MC.renderEngine.getTexture(mod_EnchantChanger.EcZackSwordPNG));
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

	    Shape1.render(0.07F);
	    Shape2.render(0.07F);
	    Shape3.render(0.07F);
	    Shape4.render(0.07F);
	    Shape5.render(0.07F);
	    GL11.glPopMatrix();
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
