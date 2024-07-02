package com.github.standobyte.jojo.client.model.pose.anim.barrage;

import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.client.model.pose.IModelPose;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public abstract class ArmsBarrageAnimation<T extends LivingEntity, M extends EntityModel<T>> implements IBarrageAnimation<T, M> {
    protected final M model;
    private final IModelPose<T> loop;
    private final IModelPose<T> recovery;
    private float loopLen;

    public ArmsBarrageAnimation(M model, IModelPose<T> loop, IModelPose<T> recovery, float loopLen) {
        this.model = model;
        this.loop = loop;
        this.recovery = recovery;
        this.loopLen = loopLen;
    }

    @Override
    public void animate(Phase phase, float phaseCompletion, T entity, float ticks, 
            float yRotationOffset, float xRotation, HandSide side, boolean layer) {
        float loop = ticks / getLoopLen();
        side = getHandSide(phase, entity, ticks);
        
        switch (phase) {
        case PERFORM:
            animateSwing(entity, model, MathHelper.frac(loop), side, yRotationOffset, xRotation, 0);
            if (!layer) {
                BarrageSwingsHolder<T, M> swings = getBarrageSwingsHolder(entity);
                if (!swings.popSwingsPrevention()) {
                    float swingsToAdd = swingsToAdd(entity, loop, swings.getLoopCount());
                    if (swingsToAdd > 0) {
                        addSwings(entity, swings, side, swingsToAdd);
                    }
                }
                swings.setLoopCount(loop);
            }
            break;
        case RECOVERY:
            recovery.poseModel(phaseCompletion, entity, ticks, yRotationOffset, xRotation, side);
        default:
            break;
        }
    }

    protected abstract HandSide getHandSide(Phase phase, T entity, float ticks);
    protected abstract boolean switchesArms();
    
    @Override
    public void animateSwing(T entity, M model, float loopCompletion, HandSide side, 
            float yRotationOffset, float xRotation, float zRotationOffset) {
        loop.poseModel(loopCompletion, entity, 0, yRotationOffset, xRotation, side);
    }
    
    protected float getLoopLen() {
        return loopLen;
    }
    
    public abstract BarrageSwingsHolder<T, M> getBarrageSwingsHolder(T entity);

    @Override
    public void onAnimStart(T entity, float yRotationOffset, float xRotation, boolean layer) {
        getBarrageSwingsHolder(entity).preventNextFrameSwings();
    }

    protected void addSwings(T entity, BarrageSwingsHolder<T, M> swings, HandSide side, float hits) {
        int swingsToAdd = (int) hits;
        if (entity.getRandom().nextFloat() <= (float) (hits - swingsToAdd)) swingsToAdd++;
        double maxOffset = maxSwingOffset(entity);
        if (switchesArms() && entity.getRandom().nextBoolean()) side = side.getOpposite();
        
        for (int i = 0; i < swingsToAdd; i++) {
            float f = ((float) i / (float) swingsToAdd
                    + (entity.getRandom().nextFloat() - 0.5F) * 0.4F / hits)
                    * getLoopLen() * 0.5F;
            if (switchesArms()) side = side.getOpposite();
            addSwing(entity, swings, side, f, maxOffset);
        }
    }
    
    protected abstract float swingsToAdd(T entity, float loop, float lastLoop);
    protected abstract double maxSwingOffset(T entity);
    protected abstract void addSwing(T entity, BarrageSwingsHolder<T, M> swings, HandSide side, float f, double maxOffset);
    
    
    
    protected static float standEntitySwings(StandEntity entity, float loop, float lastLoop, float loopLen) {
        return StandStatFormulas.getBarrageHitsPerSecond(entity.getAttackSpeed()) / 20F * Math.min(loop - lastLoop, 1) * loopLen;
    }
    
    protected static double maxStandSwingOffset(StandEntity entity) {
        return 1 - entity.getPrecision() / 32;
    }
}
