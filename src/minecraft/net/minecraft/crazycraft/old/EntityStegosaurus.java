package net.minecraft.crazycraft.old;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/** A large, calm herbivore; it fights only when attacked. */
public class EntityStegosaurus extends EntityTriceratops
{
    public EntityStegosaurus(World world)
    {
        super(world);
        this.setSize(3.4F, 4.2F);
        this.experienceValue = 9;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(220.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    }

    @Override
    public EntityStegosaurus createChild(EntityAgeable ageable) { return new EntityStegosaurus(this.world); }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_HORSE_AMBIENT; }
}
