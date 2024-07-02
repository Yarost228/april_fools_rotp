package com.github.standobyte.jojo.action.non_stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.init.power.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonPowerType;
import com.github.standobyte.jojo.power.nonstand.type.hamon.skill.BaseHamonSkill.HamonStat;
import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class HamonHealing extends HamonAction {

    public HamonHealing(HamonAction.Builder builder) {
        super(builder);
    }
    
    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        HamonData hamon = power.getTypeSpecificData(ModHamon.HAMON.get()).get();
        float hamonEfficiency = hamon.getHamonEfficiency(getEnergyCost(power));
        float hamonControl = hamon.getHamonControlLevelRatio();
        if (!world.isClientSide()) {
            Entity targetEntity = target.getType() == TargetType.ENTITY && hamon.isSkillLearned(ModHamonSkills.HEALING_TOUCH.get()) ? target.getEntity() : null;
            LivingEntity targetLiving = targetEntity instanceof LivingEntity ? (LivingEntity) targetEntity : null;
            LivingEntity entityToHeal = targetEntity != null && canBeHealed(targetLiving, user) ? targetLiving : user;
            int regenDuration = (int) ((50F + hamonEfficiency * 50F) * (1 + hamonControl));
            int regenLvl = MathHelper.clamp((int) ((hamonControl - 0.0001F) * 3 + (hamonEfficiency - 0.25F) * 4F/3F - 1), 0, 2);
//            if (entityToHeal.getHealth() < entityToHeal.getMaxHealth()) {
                hamon.hamonPointsFromAction(HamonStat.CONTROL, getEnergyCost(power) * hamonEfficiency);
//            }
            entityToHeal.addEffect(new EffectInstance(Effects.REGENERATION, regenDuration, regenLvl));
            if (hamon.isSkillLearned(ModHamonSkills.EXPEL_VENOM.get())) {
                entityToHeal.removeEffect(Effects.POISON);
                entityToHeal.removeEffect(Effects.WITHER);
                entityToHeal.removeEffect(Effects.HUNGER);
                entityToHeal.removeEffect(Effects.CONFUSION);
            }
            if (hamon.isSkillLearned(ModHamonSkills.PLANTS_GROWTH.get()) && user instanceof PlayerEntity && target.getType() == TargetType.BLOCK) {
                Direction face = target.getType() == TargetType.BLOCK ? target.getFace() : Direction.UP;
                bonemealEffect(user.level, (PlayerEntity) user, target.getBlockPos(), face);
            }
            Vector3d sparksPos = new Vector3d(entityToHeal.getX(), entityToHeal.getY(0.5), entityToHeal.getZ());
            HamonPowerType.createHamonSparkParticles(world, null, sparksPos, Math.max(0.5F * hamonControl * hamonEfficiency, 0.1F));
        }
    }
    
    private boolean canBeHealed(LivingEntity targetEntity, LivingEntity user) {
        return user.isShiftKeyDown() && !JojoModUtil.isUndead(targetEntity);
    }

    public static boolean bonemealEffect(World world, PlayerEntity applyingPlayer, BlockPos pos, Direction face) {
        if (BoneMealItem.applyBonemeal(ItemStack.EMPTY, world, pos, applyingPlayer)) {
            if (!world.isClientSide()) {
                world.levelEvent(2005, pos, 0);
            }
            return true;
        } else {
            BlockPos posOffset = pos.relative(face);
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isFaceSturdy(world, pos, face) && BoneMealItem.growWaterPlant(new ItemStack(null), world, posOffset, face)) {
                if (!world.isClientSide()) {
                    world.levelEvent(2005, posOffset, 0);
                }
                return true;
            } else {
                return false;
            }
        }
    }
    
    @Override
    public String getTranslationKey(INonStandPower power, ActionTarget target) {
        String key = super.getTranslationKey(power, target);
        if (power.getTypeSpecificData(ModHamon.HAMON.get())
                .map(hamon -> hamon.isSkillLearned(ModHamonSkills.HEALING_TOUCH.get())).orElse(false)
                && target.getType() == TargetType.ENTITY) {
            Entity targetEntity = target.getEntity();
            if (targetEntity instanceof LivingEntity && canBeHealed((LivingEntity) targetEntity, power.getUser())) {
                key += "_touch";
            }
        }
        return key;
    }
}
