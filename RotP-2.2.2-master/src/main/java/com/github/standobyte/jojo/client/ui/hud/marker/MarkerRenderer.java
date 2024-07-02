package com.github.standobyte.jojo.client.ui.hud.marker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.util.utils.MathUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@SuppressWarnings("deprecation")
public abstract class MarkerRenderer {
    private final int color;
    private final ResourceLocation iconTexture;
    private final List<MarkerInstance> positions = new ArrayList<>();
    protected final Minecraft mc;
    
    public MarkerRenderer(int color, ResourceLocation iconTexture, Minecraft mc) {
        this.color = color;
        this.iconTexture = iconTexture;
        this.mc = mc;
    }

    protected void render(MatrixStack matrixStack, ActiveRenderInfo camera, float partialTick) {
        if (shouldRender()) {
            positions.clear();
            updatePositions(positions, partialTick);
            if (!positions.isEmpty()) {
                matrixStack.pushPose();
                matrixStack.mulPose(camera.rotation());
                
                float[] rgb = ClientUtil.rgb(getColor());
                positions.forEach(marker -> {
                    renderAt(marker.getPos(), matrixStack, camera, partialTick, rgb, marker.isOutlined());
                });
                
                matrixStack.popPose();
            }
        }
    }
    
    protected void renderAt(Vector3d worldPos, MatrixStack matrixStack, ActiveRenderInfo camera, float partialTick, float[] rgb, boolean outline) {
        matrixStack.pushPose();
        Vector3d diff = worldPos.subtract(camera.getPosition())
                .yRot(camera.getYRot() * MathUtil.DEG_TO_RAD)
                .xRot(camera.getXRot() * MathUtil.DEG_TO_RAD);
        
        double distance = diff.length();
        if (distance > 256) return;
        
        float scale = Math.min((float) Math.pow(2, (16 - Math.min(distance, 32)) / 16) * (float) distance / 256, 1);
        
        matrixStack.translate(diff.x, diff.y, diff.z);
        matrixStack.scale(-scale, -scale, 1);
        
        mc.getTextureManager().bind(getIcon());
        AbstractGui.blit(matrixStack, -8, -28, 0, 0, 16, 16, 16, 16);

        mc.getTextureManager().bind(ClientUtil.ADDITIONAL_UI);
        RenderSystem.color4f(rgb[0], rgb[1], rgb[2], 1.0F);
        AbstractGui.blit(matrixStack, -16, -32, 0, 0, 32, 32, 256, 256);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (outline) {
            AbstractGui.blit(matrixStack, -16, -32, 32, 0, 64, 32, 256, 256);
        }

        matrixStack.popPose();
    }
    
    protected abstract boolean shouldRender();
    protected abstract void updatePositions(List<MarkerInstance> list, float partialTick);
    
    protected int getColor() {
        return color;
    }
    
    protected ResourceLocation getIcon() {
        return iconTexture;
    }

    @EventBusSubscriber(modid = JojoMod.MOD_ID, value = Dist.CLIENT)
    public static class Handler {
        private static Collection<MarkerRenderer> RENDERERS = new ArrayList<>();
        
        public static void addRenderer(MarkerRenderer markerRenderer) {
            RENDERERS.add(markerRenderer);
        }

        @SubscribeEvent
        public static void renderMarkers(RenderWorldLastEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (!mc.options.hideGui) {
                RenderSystem.disableDepthTest();

                MatrixStack matrixStack = event.getMatrixStack();
                RENDERERS.forEach(marker -> marker.render(matrixStack, mc.gameRenderer.getMainCamera(), event.getPartialTicks()));
                
                RenderSystem.enableDepthTest();
            }
        }
    }
    
    
    
    protected static class MarkerInstance {
        private final Vector3d pos;
        private final boolean outlined;
        
        protected MarkerInstance(Vector3d pos, boolean outlined) {
            this.pos = pos;
            this.outlined = outlined;
        }
        
        protected Vector3d getPos() {
            return pos;
        }
        
        protected boolean isOutlined() {
            return outlined;
        }
    }
}
