package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;

import net.minecraft.entity.LivingEntity;

public abstract class VampirismAction extends NonStandAction {
    
    public VampirismAction(NonStandAction.Builder builder) {
        super(builder);
    }
    
    @Override
    public ActionConditionResult checkConditions(LivingEntity user, INonStandPower power, ActionTarget target) {
        if (power.getTypeSpecificData(ModVampirism.VAMPIRISM.get()).get().getCuringStage() > maxCuringStage()) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkConditions(user, power, target);
    }
    
    protected int maxCuringStage() {
        return Integer.MAX_VALUE;
    }
}
