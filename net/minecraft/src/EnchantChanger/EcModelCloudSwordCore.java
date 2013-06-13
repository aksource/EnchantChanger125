package net.minecraft.src.EnchantChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import net.minecraft.src.mod_EnchantChanger;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class EcModelCloudSwordCore extends ModelBase
{
	//fields
    ModelRenderer Blade1;
    ModelRenderer Blade2;
    ModelRenderer Core;
    ModelRenderer tsuka;
    private Minecraft MC = mod_EnchantChanger.mc;

    public EcModelCloudSwordCore()
    {
    	textureWidth = 64;
        textureHeight = 32;
        setTextureOffset("Blade1.edge1-1", 0, 0);
        setTextureOffset("Blade1.edge1-2", 0, 2);
        setTextureOffset("Blade1.edge1-3", 0, 0);
        setTextureOffset("Blade1.edge1-4", 0, 0);
        setTextureOffset("Blade1.edge1-5", 0, 0);
        setTextureOffset("Blade1.edge1-6", 0, 0);
        setTextureOffset("Blade1.edge1-7", 0, 0);
        setTextureOffset("Blade1.edge1-8", 0, 0);
        setTextureOffset("Blade1.edge1-9", 0, 0);
        setTextureOffset("Blade1.edge1-10", 0, 0);
        setTextureOffset("Blade1.edge1-11", 0, 0);
        setTextureOffset("Blade2.edge2-1", 0, 0);
        setTextureOffset("Blade2.edge2-2", 0, 0);
        setTextureOffset("Blade2.edge2-3", 0, 0);
        setTextureOffset("Blade2.edge2-4", 0, 0);
        setTextureOffset("Blade2.edge2-5", 0, 0);
        setTextureOffset("Blade2.edge2-6", 0, 0);
        setTextureOffset("Blade2.edge2-7", 0, 0);
        setTextureOffset("Blade2.edge2-8", 0, 0);
        setTextureOffset("Blade2.edge2-9", 0, 0);
        setTextureOffset("Blade2.edge2-10", 0, 0);
        setTextureOffset("Blade2.edge2-11", 0, 0);
        setTextureOffset("Core.core1", 8, 0);
        setTextureOffset("Core.core2", 8, 0);
        setTextureOffset("Core.core3", 8, 0);
        setTextureOffset("Core.core4", 8, 0);
        setTextureOffset("Core.core5", 8, 0);
        setTextureOffset("Core.core6", 8, 0);
        setTextureOffset("Core.core7", 8, 0);
        setTextureOffset("Core.core8", 8, 0);
        setTextureOffset("Core.core9", 8, 0);
        setTextureOffset("Core.core10", 8, 0);
        setTextureOffset("Core.core11", 8, 0);
        setTextureOffset("Core.core12", 8, 0);
        setTextureOffset("Core.core13", 8, 0);
        setTextureOffset("Core.core14", 8, 0);
        setTextureOffset("Core.core15", 8, 0);
        setTextureOffset("Core.core16", 15, 4);
        setTextureOffset("Core.core17", 16, 8);
        setTextureOffset("tsuka.tsuka1", 12, 0);
        setTextureOffset("tsuka.tsuka2", 12, 16);

        Blade1 = new ModelRenderer(this, "Blade1");
        Blade1.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Blade1, 0F, 0F, 0F);
        Blade1.mirror = true;
          Blade1.addBox("edge1-1", 0F, 23F, -1F, 1, 1, 1);
          Blade1.addBox("edge1-2", 0F, 22F, -2F, 1, 1, 2);
          Blade1.addBox("edge1-3", -0.5F, 12F, -1F, 2, 10, 1);
          Blade1.addBox("edge1-4", -0.5F, 8F, -2F, 2, 14, 1);
          Blade1.addBox("edge1-5", 0F, 7F, -3F, 1, 15, 1);
          Blade1.addBox("edge1-6", -1F, 5F, -1F, 3, 4, 1);
          Blade1.addBox("edge1-7", -1F, 0F, -1F, 3, 2, 1);
          Blade1.addBox("edge1-8", -1F, -2F, -2F, 3, 10, 1);
          Blade1.addBox("edge1-9", -0.5F, -7.9F, -3F, 2, 15, 1);
          Blade1.addBox("edge1-10", -1F, -2F, -1F, 3, 1, 1);
          Blade1.addBox("edge1-11", 0F, -7.9F, -4F, 1, 14, 1);
        Blade2 = new ModelRenderer(this, "Blade2");
        Blade2.setRotationPoint(6F, 13F, -2.5F);
        setRotation(Blade2, 0F, 0F, 0F);
        Blade2.mirror = true;
          Blade2.addBox("edge2-1", 0F, 23F, 1F, 1, 1, 1);
          Blade2.addBox("edge2-2", 0F, 22F, 1F, 1, 1, 2);
          Blade2.addBox("edge2-3", -0.5F, 12F, 1F, 2, 10, 1);
          Blade2.addBox("edge2-4", -0.5F, 8F, 2F, 2, 14, 1);
          Blade2.addBox("edge2-5", 0F, 7F, 3F, 1, 15, 1);
          Blade2.addBox("edge2-6", -1F, 5F, 1F, 3, 4, 1);
          Blade2.addBox("edge2-7", -1F, 0F, 1F, 3, 2, 1);
          Blade2.addBox("edge2-8", -1F, -2F, 2F, 3, 10, 1);
          Blade2.addBox("edge2-9", -0.5F, -7.9F, 3F, 2, 15, 1);
          Blade2.addBox("edge2-10", -1F, -2F, 1F, 3, 1, 1);
          Blade2.addBox("edge2-11", 0F, -7.9F, 4F, 1, 14, 1);
        Core = new ModelRenderer(this, "Core");
        Core.setRotationPoint(6F, 13F, -2F);
        setRotation(Core, 0F, 0F, 0F);
        Core.mirror = true;
          Core.addBox("core1", 0F, 20F, 0F, 1, 2, 1);
          Core.addBox("core2", 0F, -7.9F, -1F, 1, 30, 1);
          Core.addBox("core3", 0F, -7.9F, 1F, 1, 30, 1);
          Core.addBox("core4", 0F, 18F, 0F, 1, 1, 1);
          Core.addBox("core5", 0F, 16F, 0F, 1, 1, 1);
          Core.addBox("core6", 0F, 14F, 0F, 1, 1, 1);
          Core.addBox("core7", 0F, 12F, 0F, 1, 1, 1);
          Core.addBox("core8", 0F, 10F, 0F, 1, 1, 1);
          Core.addBox("core9", 0F, 8F, 0F, 1, 1, 1);
          Core.addBox("core10", 0F, 5F, 0F, 1, 2, 1);
          Core.addBox("core11", 0F, 3F, 0F, 1, 1, 1);
          Core.addBox("core12", 0F, 1F, 0F, 1, 1, 1);
          Core.addBox("core13", 0F, -2F, 0F, 1, 2, 1);
          Core.addBox("core14", 0F, -4F, 0F, 1, 1, 1);
          Core.addBox("core15", 0F, -7.9F, 0F, 1, 3, 1);
          Core.addBox("core16", -0.5F, -7F, -2F, 2, 5, 1);
          Core.addBox("core17", -0.5F, -7F, 2F, 2, 5, 1);
        tsuka = new ModelRenderer(this, "tsuka");
        tsuka.setRotationPoint(6F, 13F, -2F);
        setRotation(tsuka, 0F, 0F, 0F);
        tsuka.mirror = true;
          tsuka.addBox("tsuka1", -1.5F, -8F, -6F, 4, 3, 13);
          tsuka.addBox("tsuka2", -0.5F, -22F, -0.5F, 2, 14, 2);

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
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.MC.renderEngine.getTexture("/mod_EnchantChanger/item/CloudSwordCore.png"));


	    if (pentity instanceof EntityPlayer && ((EntityPlayer)pentity).isUsingItem()) {
			// ガード
	    	GL11.glTranslatef(0F, 0.2F, 0F);
			GL11.glRotatef(25.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
			// 視点補正
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1.3F, 0.0F, 0.5F);
				GL11.glRotatef(70F, 0F, 0F, 1F);
			}
		} else {
			// 視点補正
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1F, -1F, 0.5F);
				GL11.glRotatef(90F, 0F, 1F, 0F);
				GL11.glRotatef(20F, 0F, 0F, 1F);
			}
		}
	    if(EcItemCloudSwordCore.ActiveMode)
	    {
	    	Blade1.setRotationPoint(6F, 13F, -2.5F);
	    	Blade2.setRotationPoint(6F, 13F, -1.5F);
	    }
	    else
	    {
	    	Blade1.setRotationPoint(6F, 13F, -1.5F);
	    	Blade2.setRotationPoint(6F, 13F, -2.5F);
	    }



	    GL11.glTranslatef(0F, 0F, 0F);
	    GL11.glScalef(0.12F, 0.17F, 0.17F);
	    GL11.glRotatef(0.0F, 1.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    //GL11.glTranslatef(0.05F, 0.0F, 0.0F);

	    Blade1.render(0.5f);
	    Blade2.render(0.5f);
	    Core.render(0.5f);
	    tsuka.render(0.5f);
	    GL11.glPopMatrix();
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
