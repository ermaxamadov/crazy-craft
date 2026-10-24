package net.minecraft.crazycraft;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/** Neutral roaming worker machine for the Future Dimension. */
public class EntityCrazycraftWorkerBot extends EntityCreature
{
    public EntityCrazycraftWorkerBot(World world)
    {
        super(world);
        this.setSize(1.0F, 2.4F);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(45.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(18.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_IRONGOLEM_STEP; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damage) { return SoundEvents.ENTITY_IRONGOLEM_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_IRONGOLEM_DEATH; }
}
