package com.github.standobyte.jojo.client.renderer.entity;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.model.entity.LeavesGliderModel;
import com.github.standobyte.jojo.entity.LeavesGliderEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class LeavesGliderRenderer extends SimpleEntityRenderer<LeavesGliderEntity, LeavesGliderModel> {

    public LeavesGliderRenderer(EntityRendererManager renderManager) {
        super(renderManager, new LeavesGliderModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/leaves_glider.png"));
    }
    
    @Override
    protected void renderModel(LeavesGliderEntity entity, LeavesGliderModel model, float partialTick, 
            MatrixStack matrixStack, IVertexBuilder vertexBuilder, int packedLight) {
        matrixStack.pushPose();
        matrixStack.translate(0, -entity.getBbHeight(), 0);
        float[] rgb = ClientUtil.rgb(entity.getFoliageColor());
        model.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 
                rgb[0], rgb[1], rgb[2], 1.0F);
        matrixStack.popPose();
    }

}
