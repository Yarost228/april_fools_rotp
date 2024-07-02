package com.github.standobyte.jojo.entity.damaging.projectile;

import com.github.standobyte.jojo.init.ModEntityTypes;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.skill.BaseHamonSkill.HamonStat;
import com.github.standobyte.jojo.util.damage.DamageUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class HamonBubbleEntity extends ModdedProjectileEntity {
    
    public HamonBubbleEntity(LivingEntity shooter, World world) {
        super(ModEntityTypes.HAMON_BUBBLE.get(), shooter, world);
    }

    public HamonBubbleEntity(EntityType<? extends HamonBubbleEntity> type, World world) {
        super(type, world);
    }
    
    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner) {
        return DamageUtil.dealHamonDamage(target, 0.015F, this, owner);
    }

    @Override
    protected void afterEntityHit(EntityRayTraceResult entityRayTraceResult, boolean entityHurt) {
        if (entityHurt) {
            LivingEntity owner = getOwner();
            if (owner != null) {
                INonStandPower.getNonStandPowerOptional(owner).ifPresent(power -> {
                    power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
                        hamon.hamonPointsFromAction(HamonStat.STRENGTH, ModHamon.CAESAR_BUBBLE_LAUNCHER.get().getHeldTickEnergyCost(power) / 4F);
                    });
                });
            }
        }
    }

    @Override
    public boolean standDamage() {
        return false;
    }
    
    @Override
    public float getBaseDamage() {
        return 0;
    }
    
    @Override
    protected float getMaxHardnessBreakable() {
        return 0.0F;
    }
    
    @Override
	public int ticksLifespan() {
        return 100;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
    }
}
