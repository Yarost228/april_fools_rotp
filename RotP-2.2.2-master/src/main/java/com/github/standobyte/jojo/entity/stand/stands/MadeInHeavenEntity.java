package com.github.standobyte.jojo.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.ModStandActions;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MadeInHeavenEntity extends StandEntity {
    private static boolean warned = false;
    private static final DataParameter<Integer> DED_TICK = EntityDataManager.defineId(MadeInHeavenEntity.class, DataSerializers.INT);
    
    public MadeInHeavenEntity(StandEntityType<? extends StandEntity> type, World world) {
        super(type, world);
    }
    
    @Override
    public void tick() {
        super.tick();
        LivingEntity user = getUser();
        if (!level.isClientSide() && user != null) {
            user.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10, 4, false, false, true));
            user.addEffect(new EffectInstance(Effects.DIG_SPEED, 10, 9, false, false, true));
        }

        long ticks = (long) Math.exp(Math.min((tickCount - entityData.get(DED_TICK)) / 512.0, 144));
        
        if (!level.isClientSide() && ticks >= 15 && !warned) {
            ((ServerWorld) level).players().forEach(player -> {
                player.sendMessage(new StringTextComponent("Epipelsy warning: leaving Made in Heaven summoned for too long will create flashing lights")
                        .withStyle(TextFormatting.UNDERLINE), Util.NIL_UUID);
            });
            warned = true;
        }
        
        if (getCurrentTaskAction() != ModStandActions.UNSUMMON_STAND_ENTITY.get()) {
            if (level.dayTime() + ticks >= (1 << 62)) {
                if (!level.isClientSide()) {
                    ((ServerWorld) level).players().forEach(player -> {
                        if (player != user) {
                            player.kill();
                            entityData.set(DED_TICK, tickCount);
                            ((ServerWorld) level).setDayTime(0);
                        }
                    });
                }
            }
            else {
                if (level.isClientSide()) {
                    ((ClientWorld) level).setDayTime(level.dayTime() + ticks);
                }
                else {
                    ((ServerWorld) level).setDayTime(level.dayTime() + ticks);
                }
            }
        }
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DED_TICK, 0);
    }

}
