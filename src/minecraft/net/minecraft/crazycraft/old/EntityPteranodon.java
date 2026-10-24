package net.minecraft.crazycraft.old;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/** A large flying reptile that circles above the prehistoric forest. */
public class EntityPteranodon extends EntityBat
{
    private EntityLivingBase diveTarget;
    private int diveTicks;
    private int huntCooldown = 400;

    public EntityPteranodon(World world)
    {
        super(world);
        this.setSize(2.8F, 1.6F);
        this.setIsBatHanging(false);
        this.experienceValue = 8;
    }

    @Override
    public void setIsBatHanging(boolean hanging)
    {
        super.setIsBatHanging(false);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (this.world.isRemote) return;
        if (this.huntCooldown > 0) --this.huntCooldown;
        if (this.diveTarget == null && this.huntCooldown == 0 && this.ticksExisted % 40 == 0)
        {
            for (EntityLivingBase candidate : this.world.getEntitiesWithinAABB(EntityLivingBase.class,
                    this.getEntityBoundingBox().grow(14.0D, 32.0D, 14.0D)))
            {
                if (candidate instanceof EntityChicken || candidate instanceof EntityRabbit
                    || candidate instanceof EntityPlayer && this.mayAttackPlayer()
                        && !((EntityPlayer)candidate).capabilities.isCreativeMode
                        && this.rand.nextInt(4) == 0)
                {
                    this.diveTarget = candidate;
                    this.diveTicks = 120;
                    break;
                }
            }
            if (this.diveTarget == null) this.huntCooldown = 200;
        }
        if (this.diveTarget != null)
        {
            if (!this.diveTarget.isEntityAlive() || --this.diveTicks <= 0)
            {
                this.diveTarget = null;
                this.huntCooldown = 600 + this.rand.nextInt(600);
            }
            else
            {
                double dx = this.diveTarget.posX - this.posX;
                double dy = this.diveTarget.posY + 1.0D - this.posY;
                double dz = this.diveTarget.posZ - this.posZ;
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                if (distance > 0.01D)
                {
                    this.motionX += dx / distance * 0.12D;
                    this.motionY += dy / distance * 0.09D;
                    this.motionZ += dz / distance * 0.12D;
                    this.motionX = Math.max(-0.65D, Math.min(0.65D, this.motionX));
                    this.motionZ = Math.max(-0.65D, Math.min(0.65D, this.motionZ));
                }
                if (distance < 2.6D)
                {
                    this.diveTarget.attackEntityFrom(DamageSource.causeMobDamage(this), this.diveDamage());
                    this.diveTarget.motionY += 0.25D;
                    this.diveTarget = null;
                    this.huntCooldown = 800 + this.rand.nextInt(600);
                    this.motionY = 0.5D;
                }
            }
        }
        else if (this.posY < this.world.getHeight(this.getPosition()).getY() + 7)
            this.motionY += 0.04D;
    }

    protected boolean mayAttackPlayer() { return true; }

    public boolean isDiving() { return this.diveTarget != null || this.motionY < -0.25D; }

    protected float diveDamage() { return 8.0F; }
}
