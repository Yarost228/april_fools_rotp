package com.github.standobyte.jojo.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.init.ModEntityTypes;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonCharge;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonPowerType;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class HamonBlockChargeEntity extends Entity {
    private static final DataParameter<Boolean> CACTUS_EXPLOSION = EntityDataManager.defineId(HamonBlockChargeEntity.class, DataSerializers.BOOLEAN);
    private BlockPos blockPos;
    private HamonCharge hamonCharge;
    
    public HamonBlockChargeEntity(World world, BlockPos blockPos) {
        this(ModEntityTypes.HAMON_BLOCK_CHARGE.get(), world);
        this.blockPos = blockPos;
        this.moveTo(Vector3d.atBottomCenterOf(blockPos));
    }

    public HamonBlockChargeEntity(EntityType<?> type, World world) {
        super(type, world);
        noPhysics = true;
        setNoGravity(true);
    }
    
    public void setCharge(float charge, int chargeTicks, @Nullable LivingEntity hamonUser, float energySpent) {
        this.hamonCharge = new HamonCharge(charge, chargeTicks, hamonUser, energySpent);
    }
    
    private static final int CACTUS_EXPLOSION_RANGE = 4;
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide()) {
            if (hamonCharge == null || hamonCharge.shouldBeRemoved() || blockPos == null || level.isEmptyBlock(blockPos)) {
                if (level.getBlockState(blockPos).getBlock() == Blocks.COBWEB) {
                    level.setBlock(blockPos, Blocks.TRIPWIRE.defaultBlockState(), 3);
                }
                remove();
                return;
            }
            Vector3d pos = Vector3d.atCenterOf(blockPos);
            hamonCharge.tick(null, blockPos, level, getBoundingBox().inflate(0.1D));
            if (tickCount == 60) {
                Block block = level.getBlockState(blockPos).getBlock();
                if (block == Blocks.CACTUS || block == Blocks.POTTED_CACTUS) {
                    int range = CACTUS_EXPLOSION_RANGE;
                    AxisAlignedBB aabb = new AxisAlignedBB(blockPos).inflate(range);
                    List<Entity> targets = level.getEntities(this, aabb);
                    targets.forEach(entity -> {
                        entity.hurt(DamageSource.CACTUS, 0.2F * (3F * range * range - (float) entity.distanceToSqr(pos)));
                    });
                    entityData.set(CACTUS_EXPLOSION, true);
                    level.destroyBlock(blockPos, false);
                }
            }
            float rng = random.nextFloat();
            if (rng <= 0.02F || rng <= 0.1F && level.getBlockState(blockPos).getBlock() != Blocks.COBWEB) {
                HamonPowerType.createHamonSparkParticlesEmitter(this, hamonCharge.getCharge() / 4F);
            }
        }
    }
    
    @Override
    public void onSyncedDataUpdated(DataParameter<?> parameter) {
        super.onSyncedDataUpdated(parameter);
        if (level.isClientSide() && CACTUS_EXPLOSION.equals(parameter) && entityData.get(CACTUS_EXPLOSION)) {
            for (int i = 0; i < 12; i++) {
                level.addParticle(ParticleTypes.SPLASH, 
                        getX() + random.nextDouble() - 0.5D, 
                        getY() + random.nextDouble(), 
                        getZ() + random.nextDouble() - 0.5D, 0.0D, 0.0D, 0.0D);
            }
            level.addParticle(ParticleTypes.EXPLOSION, getX(), getY() + 0.5, getZ(), 1.0D, 0.0D, 0.0D);
            level.playLocalSound(getX(), getY() + 0.5, getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 
                    0.5F, 1.35F + random.nextFloat() * 0.15F, false);
        }
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(CACTUS_EXPLOSION, false);
    }
    
    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        this.tickCount = nbt.getInt("Age");
        int[] posArray = nbt.getIntArray("BlockPos");
        if (posArray.length == 3) {
            blockPos = new BlockPos(posArray[0], posArray[1], posArray[2]);
        }
        if (nbt.contains("HamonCharge", 10)) {
            this.hamonCharge = new HamonCharge(nbt.getCompound("HamonCharge"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("Age", tickCount);
        if (blockPos != null) {
            nbt.putIntArray("BlockPos", new int[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() } );
        }
        if (hamonCharge != null) {
            nbt.put("HamonCharge", hamonCharge.writeNBT());
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
