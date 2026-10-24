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
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/** Small, quick hunter that leaves larger dinosaurs alone and hunts occasionally. */
public class EntityVelociraptor extends EntityAnimal
{
    private int hungerTicks = 1400;

    public EntityVelociraptor(World world)
    {
        super(world);
        this.setSize(1.1F, 1.7F);
        this.hungerTicks += this.rand.nextInt(1800);
        this.experienceValue = 6;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.15D, true));
        this.tasks.addTask(5, new EntityAIWander(this, 0.95D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new HungryTarget<EntityChicken>(EntityChicken.class, 12));
        this.targetTasks.addTask(3, new HungryTarget<EntityRabbit>(EntityRabbit.class, 12));
        this.targetTasks.addTask(4, new HungryTarget<EntityPlayer>(EntityPlayer.class, 28));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(70.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.36D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && this.hungerTicks > 0) --this.hungerTicks;
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
    public boolean attackEntityAsMob(Entity victim)
    {
        boolean hit = victim.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
        if (hit && !victim.isEntityAlive())
        {
            this.hungerTicks = 1800 + this.rand.nextInt(1800);
            this.setAttackTarget(null);
        }
        return hit;
    }

    @Override
    public EntityVelociraptor createChild(EntityAgeable ageable) { return new EntityVelociraptor(this.world); }

    @Override
    public boolean isBreedingItem(ItemStack stack) { return false; }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PARROT_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damage) { return SoundEvents.ENTITY_PARROT_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PARROT_DEATH; }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        tag.setInteger("CrazycraftHunger", this.hungerTicks);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        if (tag.hasKey("CrazycraftHunger")) this.hungerTicks = tag.getInteger("CrazycraftHunger");
    }

    private class HungryTarget<T extends net.minecraft.entity.EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        HungryTarget(Class<T> target, int chance)
        {
            super(EntityVelociraptor.this, target, chance, true, false, null);
        }

        @Override
        public boolean shouldExecute()
        {
            return EntityVelociraptor.this.hungerTicks <= 0 && !EntityVelociraptor.this.isChild() && super.shouldExecute();
        }
    }
}
