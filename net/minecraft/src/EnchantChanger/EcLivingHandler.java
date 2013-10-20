package net.minecraft.src.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Potion;
import net.minecraft.src.World;
import net.minecraft.src.mod_EnchantChanger;
import net.minecraft.src.forge.IEntityLivingHandler;

public class EcLivingHandler implements IEntityLivingHandler
{
	private boolean allowLevitatiton = false;
	private boolean isLevitation = false;
	private int flyToggleTimer = 0;
	private int sprintToggleTimer = 0;
	private int FlightMptime=20*3;
	private int mptimer = this.FlightMptime;
	@Override
	public boolean onEntityLivingSpawn(EntityLiving entity, World world,
			float x, float y, float z) {
		return false;
	}

	@Override
	public boolean onEntityLivingDeath(EntityLiving entity, DamageSource killer) {
		if(killer.getEntity() != null && killer.getEntity() instanceof EntityPlayer 
				&& ((EntityPlayer)killer.getEntity()).getCurrentEquippedItem() != null
				&& ((EntityPlayer)killer.getEntity()).getCurrentEquippedItem().isItemEnchanted()
				&& !entity.worldObj.isRemote)
		{
			int exp = mod_EnchantChanger.getExpValue(entity);
			entity.worldObj.spawnEntityInWorld(new EcEntityApOrb(entity.worldObj, entity.posX,entity.posY, entity.posZ, exp / 2));
		}
		return false;
	}

	@Override
	public void onEntityLivingSetAttackTarget(EntityLiving entity,
			EntityLiving target) {

	}

	@Override
	public boolean onEntityLivingAttacked(EntityLiving entity,
			DamageSource attack, int damage) {
		return false;
	}

	@Override
	public void onEntityLivingJump(EntityLiving entity) {

	}

	@Override
	public boolean onEntityLivingFall(EntityLiving entity, float distance) {
		return false;
	}

	@Override
	public boolean onEntityLivingUpdate(EntityLiving entity) {
		if(entity != null && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			this.flight(player);
		}
		return false;
	}

	private void flight(EntityPlayer player)
	{
		this.allowLevitatiton = mod_EnchantChanger.checkMagicIteminInv(player, 3) && !(player.capabilities.isCreativeMode || player.capabilities.allowFlying || (player.getFoodStats().getFoodLevel() < 0 && !mod_EnchantChanger.YouAreTera));
		if(!this.allowLevitatiton)
		{
			this.isLevitation = false;
			return;
		}
		player.fallDistance = 0.0f;
		boolean jump = ((EntityPlayerSP)player).movementInput.jump;
        float var2 = 0.8F;
        boolean var3 = ((EntityPlayerSP)player).movementInput.moveForward >= var2;
		((EntityPlayerSP)player).movementInput.updatePlayerMoveState();
		if (this.allowLevitatiton && !jump && ((EntityPlayerSP)player).movementInput.jump)
		{
			if (this.flyToggleTimer == 0)
			{
				this.flyToggleTimer = 7;
			}
			else
			{
				this.isLevitation = !this.isLevitation;
				this.flyToggleTimer = 0;
			}
		}
		boolean var4 = (float)((EntityPlayerSP)player).getFoodStats().getFoodLevel() > 6.0F;
		if (((EntityPlayerSP)player).onGround && !var3 && ((EntityPlayerSP)player).movementInput.moveForward >= var2 && !((EntityPlayerSP)player).isSprinting() && var4 && !((EntityPlayerSP)player).isUsingItem() && !((EntityPlayerSP)player).isPotionActive(Potion.blindness))
		{
			if (this.sprintToggleTimer == 0)
			{
				this.sprintToggleTimer = 7;
			}
			else
			{
				((EntityPlayerSP)player).setSprinting(true);
				this.sprintToggleTimer = 0;
			}
		}
		if (this.sprintToggleTimer > 0)
		{
			--this.sprintToggleTimer;
		}
		if (this.flyToggleTimer > 0)
		{
			--this.flyToggleTimer;
		}
		if (player.onGround && this.isLevitation)
		{
			this.isLevitation = false;
		}
		if (this.isLevitation)
		{
			if(this.mptimer ==0)
			{
				this.mptimer = this.FlightMptime;
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
			}
			else
				--this.mptimer;
			player.motionY = 0D;
			player.jumpMovementFactor = 0.1f;
			if (((EntityPlayerSP)player).movementInput.sneak)
			{
				player.motionY -= 0.4D;
			}

			if (((EntityPlayerSP)player).movementInput.jump)
			{
				player.motionY += 0.4D;
			}

		}
		else
			player.jumpMovementFactor = 0.02f;
		if (player.onGround && this.isLevitation)
		{
			this.isLevitation = false;
		}
	}
	@Override
	public int onEntityLivingHurt(EntityLiving entity, DamageSource source, int damage) {
		return damage;
	}

	@Override
	public void onEntityLivingDrops(EntityLiving entity, DamageSource source, ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit, int specialDropValue) {

	}

}