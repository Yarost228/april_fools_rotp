package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonPowerType;
import com.github.standobyte.jojo.power.nonstand.type.hamon.skill.BaseHamonSkill.HamonStat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class HamonWallClimbing extends HamonAction {

    public HamonWallClimbing(HamonAction.Builder builder) {
        super(builder.holdType().emptyMainHand());
    }
    
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!user.horizontalCollision) {
            if (user.level.isClientSide()) {
                if (user instanceof PlayerEntity) {
                    return ActionConditionResult.NEGATIVE_CONTINUE_HOLD;
                }
            }
            else if (!(user instanceof PlayerEntity)) {
                return ActionConditionResult.NEGATIVE_CONTINUE_HOLD;
            }
        }
        return ActionConditionResult.POSITIVE;
    }
    
    @Override
    protected void holdTick(World world, LivingEntity user, INonStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (requirementsFulfilled) {
            HamonData hamon = power.getTypeSpecificData(ModHamon.HAMON.get()).get();
            double speed = 0.1D + (hamon.getBreathingLevel() * 0.0015 + hamon.getHamonControlLevel() * 0.0025) * hamon.getHamonEfficiency(getEnergyCost(power));
            Vector3d movement = user.getDeltaMovement();
            user.setDeltaMovement(movement.x, speed, movement.z);
            hamon.hamonPointsFromAction(HamonStat.CONTROL, getHeldTickEnergyCost(power));
        }
        if (ticksHeld % 4 == 0) {
            Vector3d sparkVec = user.getLookAngle().scale(0.25).add(user.getX(), user.getY(1.0), user.getZ());
            HamonPowerType.createHamonSparkParticles(world, user instanceof PlayerEntity ? (PlayerEntity) user : null, sparkVec, 0.1F);
        }
    }

}
