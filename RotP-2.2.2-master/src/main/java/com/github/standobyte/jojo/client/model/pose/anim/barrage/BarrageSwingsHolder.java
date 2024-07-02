package com.github.standobyte.jojo.client.model.pose.anim.barrage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;

public class BarrageSwingsHolder<T extends Entity, M extends EntityModel<T>> {
    private List<AdditionalBarrageSwing<T, M>> barrageSwings = new LinkedList<>();
    private float loopLast = -1;

    public void addSwing(AdditionalBarrageSwing<T, M> swing) {
        barrageSwings.add(swing);
    }
    
    public void updateSwings(Minecraft mc) {
        if (!mc.isPaused() && !barrageSwings.isEmpty()) {
            float timeDelta = mc.getDeltaFrameTime();
            Iterator<AdditionalBarrageSwing<T, M>> iter = barrageSwings.iterator();
            while (iter.hasNext()) {
                AdditionalBarrageSwing<T, M> swing = iter.next();
                swing.addDelta(timeDelta);
                if (swing.removeSwing()) {
                    iter.remove();
                }
            }
        }
    }

    public void renderBarrageSwings(M model, T entity, MatrixStack matrixStack, IVertexBuilder buffer, 
            int packedLight, int packedOverlay, float yRotation, float xRotation, float red, float green, float blue, float alpha) {
        if (!barrageSwings.isEmpty()) {
            for (AdditionalBarrageSwing<T, M> swing : barrageSwings) {
                swing.poseAndRender(entity, model, matrixStack, buffer, yRotation, xRotation, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }
    }
    
    public void setLoopCount(float loopCount) {
        this.loopLast = loopCount;
    }
    
    public float getLoopCount() {
        return loopLast;
    }
    
    private boolean preventNextFrameSwings = false;
    public void preventNextFrameSwings() {
        preventNextFrameSwings = true;
    }
    
    public boolean popSwingsPrevention() {
        boolean noSwings = preventNextFrameSwings;
        preventNextFrameSwings = false;
        return noSwings;
    }
}
