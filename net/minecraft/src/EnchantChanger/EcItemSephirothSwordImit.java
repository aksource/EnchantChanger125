package net.minecraft.src.EnchantChanger;

import net.minecraft.src.EnumToolMaterial;

public class EcItemSephirothSwordImit extends EcItemSword
{
	public EcItemSephirothSwordImit(int id)
	{
		super(id, EnumToolMaterial.IRON);
		this.setMaxDamage(EnumToolMaterial.IRON.getMaxUses());
	}
}