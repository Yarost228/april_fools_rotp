package com.github.standobyte.jojo.client.model.pose;

import java.util.function.UnaryOperator;

import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;

public class ModelPoseTransition<T extends Entity> implements IModelPose<T> {
    private final IModelPose<T> pose1;
    private final IModelPose<T> pose2;
    private UnaryOperator<Float> easingFunc = x -> x;
    
    public ModelPoseTransition(IModelPose<T> pose1, IModelPose<T> pose2) {
        this.pose1 = pose1;
        this.pose2 = pose2;
    }

    @Override
    public void poseModel(float transition, T entity, float ticks, float yRotationOffset, float xRotation, HandSide side) {
        pose1.poseModel(1.0F, entity, ticks, yRotationOffset, xRotation, side);
        pose2.poseModel(easingFunc.apply(transition), entity, ticks, yRotationOffset, xRotation, side);
    }

    @Override
    public ModelPoseTransition<T> setEasing(UnaryOperator<Float> function) {
        this.easingFunc = function;
        return this;
    }

}
