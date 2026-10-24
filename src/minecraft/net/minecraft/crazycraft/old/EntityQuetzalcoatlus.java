package net.minecraft.crazycraft.old;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

/** Rare giant flyer that hunts small prey and avoids unprovoked player attacks. */
public class EntityQuetzalcoatlus extends EntityPteranodon
{
    public EntityQuetzalcoatlus(World world)
    {
        super(world);
        this.setSize(4.0F, 3.3F);
        this.experienceValue = 12;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    }

    @Override
    protected boolean mayAttackPlayer() { return false; }

    @Override
    protected float diveDamage() { return 12.0F; }
}
