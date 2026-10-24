package net.minecraft.crazycraft.old;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/** A sturdy herbivore that fights predators and defends itself from players. */
public class EntityTriceratops extends EntityAnimal
{
    public EntityTriceratops(World world)
    {
        super(world);
        this.setSize(3.2F, 4.0F);
        this.experienceValue = 8;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.05D, true));
        this.tasks.addTask(5, new EntityAIWander(this, 0.75D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(14.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity victim)
    {
        boolean hit = victim.attackEntityFrom(DamageSource.causeMobDamage(this),
            (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (hit) this.applyEnchantments(this, victim);
        return hit;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        Block ground = this.world.getBlockState(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ).down()).getBlock();
        if (ground != Blocks.GRASS && ground != Blocks.DIRT && ground != Blocks.SAND
            && ground != CrazycraftContentBlocks.OLD[0] && ground != CrazycraftContentBlocks.OLD[4]
            && ground != CrazycraftContentBlocks.OLD[7] && ground != CrazycraftContentBlocks.OLD[12]
            && ground != CrazycraftContentBlocks.OLD[15]) return false;
        this.spawnableBlock = ground;
        return super.getCanSpawnHere();
    }

    @Override
    public EntityTriceratops createChild(EntityAgeable ageable) { return new EntityTriceratops(this.world); }

    @Override
    public boolean isBreedingItem(ItemStack stack) { return false; }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_COW_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damage) { return SoundEvents.ENTITY_COW_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_COW_DEATH; }
}
