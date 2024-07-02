package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;

public class HamonBreath extends HamonAction {

    public HamonBreath(Builder builder) {
        super(builder);
    }
    
    @Override
    public void playVoiceLine(LivingEntity user, INonStandPower power, ActionTarget target, boolean wasActive, boolean shift) {
        if (power.getEnergy() == 0) {
            SoundEvent shout = getShout(user, power, target, wasActive);
            if (shout != null) {
                JojoModUtil.sayVoiceLine(user, shout, null, 1.0F - 0.5F * power.getEnergy() / power.getMaxEnergy(), 1.0F, 0);
            }
        }
    }
    
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        if (user.getAirSupply() < user.getMaxAirSupply()) {
            return conditionMessage("no_air");
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public boolean isHeldSentToTracking() {
        return true;
    }
}
