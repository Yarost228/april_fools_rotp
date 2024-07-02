package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.ZoomPunchEntity;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonData;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class HamonZoomPunch extends HamonAction {

    public HamonZoomPunch(HamonAction.Builder builder) {
        super(builder.emptyMainHand());
    }
    
    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            HamonData hamon = power.getTypeSpecificData(ModHamon.HAMON.get()).get();
            float hamonEfficiency = hamon.getHamonEfficiency(getEnergyCost(power));
            float length = (8 + hamon.getHamonControlLevel() * 0.1F);
            int duration = Math.max(getCooldownTechnical(power), 1);
            ZoomPunchEntity zoomPunch = new ZoomPunchEntity(world, user, 
                    length / (float) duration * (0.4F + 0.6F * hamonEfficiency), duration,
                    0.75F, getEnergyCost(power), 
                    getEnergyCost(power) * hamonEfficiency);
            world.addFreshEntity(zoomPunch);
        }
    }

}
