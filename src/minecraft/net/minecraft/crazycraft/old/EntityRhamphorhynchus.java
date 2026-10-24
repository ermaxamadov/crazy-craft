package net.minecraft.crazycraft.old;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

/** Small, quick flyer that snatches small animals near the treetops. */
public class EntityRhamphorhynchus extends EntityPteranodon
{
    public EntityRhamphorhynchus(World world)
    {
        super(world);
        this.setSize(1.3F, 0.8F);
        this.experienceValue = 5;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
    }

    @Override
    protected boolean mayAttackPlayer() { return false; }

    @Override
    protected float diveDamage() { return 5.0F; }
}
