package net.minecraft.crazycraft.old;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/** A roaming predator that hunts when hungry and retaliates when attacked. */
public class EntityTRex extends EntityAnimal
{
    private int hungerTicks = 1800;

    public EntityTRex(World world)
    {
        super(world);
        this.setSize(3.5F, 6.0F);
        this.hungerTicks += this.rand.nextInt(1800);
        this.experienceValue = 12;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.15D, true));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 14.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new HungryTarget<EntityPlayer>(EntityPlayer.class, 10, null));
        this.targetTasks.addTask(3, new HungryTarget<EntityAnimal>(EntityAnimal.class, 10,
            new Predicate<EntityAnimal>()
            {
                public boolean apply(@Nullable EntityAnimal animal)
                {
                    return animal != null && !(animal instanceof EntityTRex);
                }
            }));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(28.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(25.0D);
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
        boolean hit = victim.attackEntityFrom(DamageSource.causeMobDamage(this),
            (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (hit)
        {
            this.applyEnchantments(this, victim);
            if (!victim.isEntityAlive())
            {
                this.hungerTicks = 2400 + this.rand.nextInt(2400);
                this.setAttackTarget(null);
            }
        }
        return hit;
    }

    @Override
    public EntityTRex createChild(EntityAgeable ageable) { return new EntityTRex(this.world); }

    @Override
    public boolean isBreedingItem(ItemStack stack) { return false; }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_POLAR_BEAR_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damage) { return SoundEvents.ENTITY_POLAR_BEAR_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_POLAR_BEAR_DEATH; }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        tag.setInteger("CrazycraftHunger", this.hungerTicks);
        tag.setBoolean("CrazycraftHungerV2", true);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        if (tag.hasKey("CrazycraftHunger"))
        {
            this.hungerTicks = tag.getInteger("CrazycraftHunger");
            if (!tag.getBoolean("CrazycraftHungerV2")) this.hungerTicks = Math.max(1800, this.hungerTicks);
        }
    }

    private class HungryTarget<T extends net.minecraft.entity.EntityLivingBase> extends EntityAINearestAttackableTarget<T>
    {
        HungryTarget(Class<T> target, int chance, @Nullable Predicate<? super T> filter)
        {
            super(EntityTRex.this, target, chance, true, false, filter);
        }

        @Override
        public boolean shouldExecute()
        {
            return EntityTRex.this.hungerTicks <= 0 && !EntityTRex.this.isChild() && super.shouldExecute();
        }
    }
}
