package com.github.standobyte.jojo.action.stand;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.stands.SilverChariotEntity;
import com.github.standobyte.jojo.power.stand.IStandPower;

import net.minecraft.util.SoundEvent;

public class SilverChariotLightAttack extends StandEntityLightAttack {
	private final Supplier<StandEntityLightAttack> noRapierAttack;

    public SilverChariotLightAttack(StandEntityLightAttack.Builder builder, Supplier<StandEntityLightAttack> noRapierAttack) {
        super(builder);
        this.noRapierAttack = noRapierAttack;
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof SilverChariotEntity && !((SilverChariotEntity) stand).hasRapier()) {
            return conditionMessage("chariot_rapier");
        }
        return super.checkStandConditions(stand, power, target);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power) {
    	return power.isActive() && power.getStandManifestation() instanceof SilverChariotEntity
    			&& !((SilverChariotEntity) power.getStandManifestation()).hasRapier() && noRapierAttack.get() != null
    			? noRapierAttack.get() : this;
    }
    
    @Nullable
    public List<Supplier<SoundEvent>> getSounds(StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task) {
        return standSounds.get(phase);
    }
}
