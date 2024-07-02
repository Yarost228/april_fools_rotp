package com.github.standobyte.jojo.potion;

import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.AttackDamageEffect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VampireWeaknessEffect extends AttackDamageEffect implements IApplicableEffect {

    public VampireWeaknessEffect() {
        super(EffectType.HARMFUL, Effects.WEAKNESS.getColor(), -4.0D);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "3888afcd-2fa5-448d-9bd5-626dd52fc32f", 0.0D, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public String getDescriptionId() {
        return Effects.WEAKNESS.getDescriptionId();
    }
    
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        World world = entity.level;
        if (world.isClientSide() && world.isDay()) {
            float brightness = entity.getBrightness();
            BlockPos blockPos = entity.getVehicle() instanceof BoatEntity ? 
                    (new BlockPos(entity.getX(), (double)Math.round(entity.getY(1.0)), entity.getZ())).above()
                    : new BlockPos(entity.getX(), (double)Math.round(entity.getY(1.0)), entity.getZ());
            if (brightness > 0.5F && world.canSeeSky(blockPos)) {
                double x = entity.getX() + (Math.random() - 0.5) * entity.getBbWidth();
                double y = entity.getY() + Math.random() * entity.getBbHeight();
                double z = entity.getZ() + (Math.random() - 0.5) * entity.getBbWidth();
                if (amplifier < 2) {
                    int n = (amplifier + 1) * 2;
                    for (int i = 0; i < n; i++) {
                        entity.level.addParticle(ParticleTypes.SMOKE, x, y, z, 
                                0, 0, 0);
                    }
                }
                else {
                    int n = (Math.min(amplifier, 5) + 1) / 2;
                    for (int i = 0; i < n; i++) {
                        entity.level.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 
                                0, 0, 0);
                    }
                }
                if (amplifier >= 4) {
                    x = entity.getX() + (Math.random() - 0.5) * entity.getBbWidth();
                    y = entity.getY() + Math.random() * entity.getBbHeight();
                    z = entity.getZ() + (Math.random() - 0.5) * entity.getBbWidth();
                    entity.level.addParticle(ParticleTypes.LAVA, x, y, z, 
                            0, 0, 0);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isApplicable(LivingEntity entity) {
        return JojoModUtil.isUndead(entity);
    }
}
