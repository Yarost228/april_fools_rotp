package com.github.standobyte.jojo.action.non_stand;

import java.util.Optional;
import java.util.Set;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.entity.HamonBlockChargeEntity;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.util.utils.JojoModUtil;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class HamonOrganismInfusion extends HamonAction {

    public HamonOrganismInfusion(HamonAction.Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkTarget(ActionTarget target, LivingEntity user, INonStandPower power) {
        switch (target.getType()) {
        case ENTITY:
            Entity entity = target.getEntity();
            if (    !(entity instanceof LivingEntity) 
                    || JojoModUtil.isUndead((LivingEntity) entity)
                    || !(entity instanceof MobEntity || entity instanceof PlayerEntity)
                    || entity instanceof GolemEntity) {
                return conditionMessage("living_entity");
            }
            break;
        case BLOCK:
            BlockPos blockPos = target.getBlockPos();
            BlockState blockState = user.level.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (!(isBlockLiving(blockState) || block instanceof FlowerPotBlock && blockState.getBlock() != Blocks.FLOWER_POT)) {
                return conditionMessage("living_plant");
            }
            break;
        default:
            break;
        }
        return ActionConditionResult.POSITIVE;
    }
    
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ANY;
    }
    
    @Override
    public ActionTarget targetBeforePerform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (target.getType() == TargetType.BLOCK) {
            BlockPos blockPos = target.getBlockPos();
            Optional<Entity> entityInside = world.getEntities(null, world.getBlockState(blockPos).getShape(world, blockPos).bounds().move(blockPos))
                    .stream()
                    .filter(entity -> (entity instanceof AnimalEntity || entity instanceof AmbientEntity)
                            && ((LivingEntity) entity).getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> !cap.hasHamonCharge()).orElse(false))
                    .findAny();
            if (entityInside.isPresent()) {
                return new ActionTarget(entityInside.get());
            }
        }
        return super.targetBeforePerform(world, user, power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, INonStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            HamonData hamon = power.getTypeSpecificData(ModHamon.HAMON.get()).get();
            int chargeTicks = 100 + MathHelper.floor((float) (1100 * hamon.getHamonStrengthLevel())
                    / (float) HamonData.MAX_STAT_LEVEL * hamon.getHamonEfficiency(getEnergyCost(power)) * hamon.getHamonEfficiency(getEnergyCost(power)));
            switch(target.getType()) {
            case BLOCK:
                BlockPos blockPos = target.getBlockPos();
                world.getEntitiesOfClass(HamonBlockChargeEntity.class, 
                        new AxisAlignedBB(Vector3d.atCenterOf(blockPos), Vector3d.atCenterOf(blockPos))).forEach(Entity::remove);
                HamonBlockChargeEntity charge = new HamonBlockChargeEntity(world, target.getBlockPos());
                charge.setCharge(0.02F * hamon.getHamonDamageMultiplier() * hamon.getHamonEfficiency(getEnergyCost(power)), chargeTicks, user, getEnergyCost(power));
                world.addFreshEntity(charge);
                break;
            case ENTITY:
                LivingEntity entity = (LivingEntity) target.getEntity();
                entity.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> 
                cap.setHamonCharge(0.2F * hamon.getHamonDamageMultiplier() * hamon.getHamonEfficiency(getEnergyCost(power)), chargeTicks, user, getEnergyCost(power)));
                break;
            default:
                break;
            }
        }
    }

    private static final Set<Material> LIVING_MATERIALS = ImmutableSet.<Material>builder().add(
            Material.PLANT,
            Material.WATER_PLANT,
            Material.REPLACEABLE_PLANT,
            Material.REPLACEABLE_FIREPROOF_PLANT,
            Material.REPLACEABLE_WATER_PLANT,
            Material.GRASS,
            Material.BAMBOO_SAPLING,
            Material.BAMBOO,
            Material.LEAVES,
            Material.CACTUS,
            Material.CORAL,
            Material.VEGETABLE,
            Material.EGG
            ).build();
    public static boolean isBlockLiving(BlockState blockState) {
        return LIVING_MATERIALS.contains(blockState.getMaterial());
    }

}
