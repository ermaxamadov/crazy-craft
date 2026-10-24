package net.minecraft.crazycraft.old;

import net.minecraft.block.Block;
import net.minecraft.crazycraft.CrazycraftContentBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Species-specific stats and combat for the eight additional land dinosaurs in guide 1. */
public abstract class EntityCrazycraftSpecies extends EntityAnimal
{
    private static final DataParameter<Integer> STOMP_TICKS = EntityDataManager.createKey(EntityCrazycraftSpecies.class, DataSerializers.VARINT);
    private int stompCooldown;

    public enum Species
    {
        SPINOSAURUS("spinosaurus", 4.5F, 6.5F, 280, 22, 0.26D, true),
        GIGANOTOSAURUS("giganotosaurus", 4.2F, 6.5F, 290, 23, 0.25D, true),
        MAPUSAURUS("mapusaurus", 3.5F, 4.0F, 220, 18, 0.29D, true),
        CARNOTAURUS("carnotaurus", 2.4F, 3.5F, 180, 16, 0.35D, true),
        ALLOSAURUS("allosaurus", 3.0F, 4.0F, 200, 17, 0.31D, true),
        DILOPHOSAURUS("dilophosaurus", 1.2F, 2.0F, 90, 7, 0.30D, true),
        COMPSOGNATHUS("compsognathus", 0.55F, 0.6F, 35, 3, 0.34D, false),
        GALLIMIMUS("gallimimus", 1.8F, 2.5F, 80, 4, 0.38D, false),
        OVIRAPTOR("oviraptor", 1.0F, 1.5F, 65, 5, 0.31D, false),
        BRACHIOSAURUS("brachiosaurus", 5.0F, 15.5F, 400, 12, 0.14D, false),
        ANKYLOSAURUS("ankylosaurus", 3.1F, 3.0F, 300, 15, 0.17D, false),
        PARASAUROLOPHUS("parasaurolophus", 2.9F, 4.0F, 160, 7, 0.25D, false),
        IGUANODON("iguanodon", 2.7F, 3.5F, 170, 9, 0.25D, false);

        public final String id;
        public final float width;
        public final float height;
        public final int health;
        public final int damage;
        public final double speed;
        public final boolean predator;

        Species(String id, float width, float height, int health, int damage, double speed, boolean predator)
        {
            this.id = id;
            this.width = width;
            this.height = height;
            this.health = health;
            this.damage = damage;
            this.speed = speed;
            this.predator = predator;
        }
    }

    protected abstract Species species();

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(STOMP_TICKS, Integer.valueOf(0));
    }

    protected EntityCrazycraftSpecies(World world)
    {
        super(world);
        Species profile = this.species();
        this.setSize(profile.width, profile.height);
        this.experienceValue = profile.predator ? 10 : 7;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, this.species().predator ? 1.10D : 0.95D, true));
        this.tasks.addTask(5, new EntityAIWander(this, this.species().predator ? 0.82D : 0.62D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 13.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        if (this.species().predator)
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 24, true, false, null));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote)
        {
            if (this.stompCooldown > 0) --this.stompCooldown;
            int ticks = this.dataManager.get(STOMP_TICKS).intValue();
            if (ticks > 0)
            {
                --ticks;
                this.dataManager.set(STOMP_TICKS, Integer.valueOf(ticks));
                if (ticks == 4) this.performBrachiosaurusStomp();
            }
        }
    }

    public int getStompTicks()
    {
        return this.dataManager.get(STOMP_TICKS).intValue();
    }

    private void performBrachiosaurusStomp()
    {
        net.minecraft.util.math.Vec3d look = this.getLookVec();
        BlockPos impact = new BlockPos(this.posX + look.x * 2.5D, this.getEntityBoundingBox().minY - 0.01D,
            this.posZ + look.z * 2.5D);
        this.world.playSound(null, impact, SoundEvents.ENTITY_IRONGOLEM_STEP,
            net.minecraft.util.SoundCategory.HOSTILE, 2.0F, 0.5F);
        for (EntityLivingBase nearby : this.world.getEntitiesWithinAABB(EntityLivingBase.class,
            new net.minecraft.util.math.AxisAlignedBB(impact).grow(1.6D, 0.8D, 1.6D)))
        {
            if (nearby != this)
            {
                nearby.attackEntityFrom(DamageSource.causeMobDamage(this), this.species().damage);
                nearby.knockBack(this, 1.5F, this.posX - nearby.posX, this.posZ - nearby.posZ);
            }
        }
        BlockPos groundPos = impact.down();
        Block ground = this.world.getBlockState(groundPos).getBlock();
        if (ground == Blocks.GRASS || ground == Blocks.DIRT || ground == Blocks.SAND
            || ground == CrazycraftContentBlocks.OLD[0] || ground == CrazycraftContentBlocks.OLD[4]
            || ground == CrazycraftContentBlocks.OLD[7] || ground == CrazycraftContentBlocks.OLD[12]
            || ground == CrazycraftContentBlocks.OLD[15])
        {
            this.world.playEvent(2001, groundPos, Block.getStateId(this.world.getBlockState(groundPos)));
            this.world.setBlockToAir(groundPos);
        }
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        Species profile = this.species();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(profile.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(profile.speed);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(profile.predator ? 28.0D : 18.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(profile.damage);
    }

    @Override
    public boolean attackEntityAsMob(Entity victim)
    {
        float damage = this.species().damage;
        if (this.species() == Species.SPINOSAURUS && this.isInWater()) damage *= 1.25F;
        if (this.species() == Species.BRACHIOSAURUS)
        {
            if (this.stompCooldown == 0)
            {
                this.stompCooldown = 50;
                this.dataManager.set(STOMP_TICKS, Integer.valueOf(14));
            }
            return true;
        }
        boolean hit = victim.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if (hit && victim instanceof net.minecraft.entity.EntityLivingBase)
        {
            net.minecraft.entity.EntityLivingBase living = (net.minecraft.entity.EntityLivingBase)victim;
            if (this.species() == Species.DILOPHOSAURUS)
                living.addPotionEffect(new PotionEffect(MobEffects.POISON, 80, 0));
            if (this.species() == Species.ANKYLOSAURUS)
                living.knockBack(this, 0.9F, this.posX - victim.posX, this.posZ - victim.posZ);
        }
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
    public EntityAgeable createChild(EntityAgeable ageable) { return null; }

    @Override
    public boolean isBreedingItem(ItemStack stack) { return false; }

    @Override
    protected SoundEvent getAmbientSound() { return this.species().predator ? SoundEvents.ENTITY_POLAR_BEAR_AMBIENT : SoundEvents.ENTITY_COW_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damage) { return SoundEvents.ENTITY_POLAR_BEAR_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_POLAR_BEAR_DEATH; }

    public static final class Spinosaurus extends EntityCrazycraftSpecies
    { public Spinosaurus(World world) { super(world); } protected Species species() { return Species.SPINOSAURUS; } }
    public static final class Giganotosaurus extends EntityCrazycraftSpecies
    { public Giganotosaurus(World world) { super(world); } protected Species species() { return Species.GIGANOTOSAURUS; } }
    public static final class Mapusaurus extends EntityCrazycraftSpecies
    { public Mapusaurus(World world) { super(world); } protected Species species() { return Species.MAPUSAURUS; } }
    public static final class Carnotaurus extends EntityCrazycraftSpecies
    { public Carnotaurus(World world) { super(world); } protected Species species() { return Species.CARNOTAURUS; } }
    public static final class Allosaurus extends EntityCrazycraftSpecies
    { public Allosaurus(World world) { super(world); } protected Species species() { return Species.ALLOSAURUS; } }
    public static final class Dilophosaurus extends EntityCrazycraftSpecies
    { public Dilophosaurus(World world) { super(world); } protected Species species() { return Species.DILOPHOSAURUS; } }
    public static final class Compsognathus extends EntityCrazycraftSpecies
    { public Compsognathus(World world) { super(world); } protected Species species() { return Species.COMPSOGNATHUS; } }
    public static final class Gallimimus extends EntityCrazycraftSpecies
    { public Gallimimus(World world) { super(world); } protected Species species() { return Species.GALLIMIMUS; } }
    public static final class Oviraptor extends EntityCrazycraftSpecies
    { public Oviraptor(World world) { super(world); } protected Species species() { return Species.OVIRAPTOR; } }
    public static final class Brachiosaurus extends EntityCrazycraftSpecies
    { public Brachiosaurus(World world) { super(world); } protected Species species() { return Species.BRACHIOSAURUS; } }
    public static final class Ankylosaurus extends EntityCrazycraftSpecies
    { public Ankylosaurus(World world) { super(world); } protected Species species() { return Species.ANKYLOSAURUS; } }
    public static final class Parasaurolophus extends EntityCrazycraftSpecies
    { public Parasaurolophus(World world) { super(world); } protected Species species() { return Species.PARASAUROLOPHUS; } }
    public static final class Iguanodon extends EntityCrazycraftSpecies
    { public Iguanodon(World world) { super(world); } protected Species species() { return Species.IGUANODON; } }
}
