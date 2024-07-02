package com.github.standobyte.jojo.client.ui.hud;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.ui.hud.ActionsOverlayGui.Alignment;
import com.github.standobyte.jojo.client.ui.hud.ActionsOverlayGui.BarsOrientation;
import com.github.standobyte.jojo.client.ui.hud.ActionsOverlayGui.ElementTransparency;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.power.IPower.PowerClassification;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.NonStandPowerType;
import com.github.standobyte.jojo.power.stand.IStandPower;
import com.github.standobyte.jojo.power.stand.StandUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@SuppressWarnings("deprecation")
public abstract class BarsRenderer {
    static final int BARS_WIDTH_PX = 28;
    
    protected final AbstractGui gui;
    protected final Map<BarType, ElementTransparency> barTransparencies;
    
    protected int x;
    protected int y;
    
    BarsRenderer(AbstractGui gui, ElementTransparency energy, ElementTransparency stamina, ElementTransparency resolve) {
        this.gui = gui;
        barTransparencies = new EnumMap<>(BarType.class);
        barTransparencies.put(BarType.ENERGY, energy);
        barTransparencies.put(BarType.STAMINA, stamina);
        barTransparencies.put(BarType.RESOLVE, resolve);
    }

    void render(MatrixStack matrixStack, int x, int y, Alignment alignment, 
            @Nullable PowerClassification currentMode, INonStandPower nonStandPower, IStandPower standPower, 
            int tickCounter, float partialTick) {
        this.x = x;
        this.y = y;
        align(alignment);
        if (nonStandPower != null && nonStandPower.hasPower()) {
            float energy = MathHelper.lerp(partialTick, nonStandPower.getPrevTickEnergy(), nonStandPower.getEnergy());
            
            float translucentVal = nonStandPower
                    .getTypeSpecificData(ModHamon.HAMON.get()).map(hamon -> MathHelper.lerp(partialTick, hamon.getBreathStability(), hamon.getPrevBreathStability()))
                    .orElse(0F);
            
            float maxEnergy = nonStandPower
                    .getTypeSpecificData(ModHamon.HAMON.get()).map(hamon -> hamon.getMaxBreathStability())
                    .orElse(nonStandPower.getMaxEnergy());
            
            // FIXME ! (hamon energy) get energy costs
            // FIXME ! (hamon energy) bar render effect
            renderBarWithIcon(matrixStack, BarType.ENERGY, nonStandPower.getType(), 
                    currentMode == PowerClassification.NON_STAND, nonStandPower.getType().getColor(), 1, 
                    energy, maxEnergy, 
                    250f, 450f, translucentVal, 
                    1F, tickCounter, partialTick);
        }
        if (standPower != null && standPower.hasPower()) {
            if (standPower.usesStamina() && !standPower.isStaminaInfinite()) {
                float stamina = MathHelper.lerp(partialTick, standPower.getPrevTickStamina(), standPower.getStamina());
                // FIXME get stamina costs
                renderBarWithIcon(matrixStack, BarType.STAMINA, null, 
                        currentMode == PowerClassification.STAND, 0xFFFFFF, 1, 
                        stamina, standPower.getMaxStamina(), 
                        750f, 350f, 0, 
                        StandUtil.standIgnoresStaminaDebuff(standPower) ? 0.3F : 1F, tickCounter, partialTick);
            }
            if (standPower.usesResolve()) {
                float resolve = MathHelper.lerp(partialTick, 
                        Math.min(standPower.getPrevTickResolve(), standPower.getResolve()), 
                        Math.max(standPower.getPrevTickResolve(), standPower.getResolve()));
                int color = standPower.getType().getColor();
                renderBarWithIcon(matrixStack, BarType.RESOLVE, null, 
                        currentMode == PowerClassification.STAND, color, (float) standPower.getResolveLevel() / (float) standPower.getMaxResolveLevel(), 
                        resolve, standPower.getMaxResolve(), 
                        0, 0, standPower.getResolveCounter().getMaxAchievedValue(), 
                        1F, tickCounter, partialTick);
                setResolveBonusTextPos(x, y, alignment);
            }
        }
    }

    protected void setResolveBonusTextPos(int barsX, int barsY, Alignment barsAlignment) {}
    
    protected int resolveBonusX;
    protected int resolveBonusY;
    protected Alignment resolveBonusAlignment;
    void drawTextAfterRender(MatrixStack matrixStack, 
            @Nullable PowerClassification currentMode, INonStandPower nonStandPower, IStandPower standPower, 
            int tickCounter, float partialTick, FontRenderer font, ActionsOverlayGui hud) {
        if (currentMode == PowerClassification.STAND && standPower != null && standPower.hasPower() && standPower.usesResolve()) {
            float bonus = standPower.getResolveCounter().getBoostVisible(standPower.getUser());
            if (bonus > 1) {
                drawText(matrixStack, new StringTextComponent("x" + String.format("%.2f", bonus)), 
                        resolveBonusX, resolveBonusY, Alignment.RIGHT, standPower.getType().getColor(), partialTick, font, hud);
            }
        }
    }
    
    protected abstract void align(Alignment alignment);
    
    protected abstract void renderBarWithIcon(MatrixStack matrixStack, BarType barType, NonStandPowerType<?> powerType, 
            boolean highlight, int color, 
            float value, float maxValue, float iconFill, 
            float attackCostValue, float abilityCostValue, float tranclucentBarValue, 
            float alpha, int ticks, float partialTick);
    
    protected final void renderBar(MatrixStack matrixStack, int x, int y, Alignment alignment, 
            int texX, int texY, int width, int length, int fill, int barColor, float barAlpha, 
            int borderTexX, int borderTexY, int scaleTexX, int scaleTexY, 
            int tranclucentFill, int cost1Fill, int cost2Fill, float costAlpha) {
        if (barAlpha > 0) {
            float[] rgb = ClientUtil.rgb(barColor);
            if (tranclucentFill > 0) {
                RenderSystem.color4f(rgb[0], rgb[1], rgb[2], barAlpha * 0.4F);
                barFill(matrixStack, x, y, alignment, texX, texY, width, length, tranclucentFill);
            }
            RenderSystem.color4f(rgb[0], rgb[1], rgb[2], barAlpha);
            if (fill > 0) {
                barFill(matrixStack, x, y, alignment, texX, texY, width, length, fill);
            }
            // border
            drawBarElement(matrixStack, x, y, borderTexX, borderTexY, width, length + 2);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, barAlpha);
            // cost
            if (costAlpha > 0) {
                // FIXME cost
//              renderCost(matrixStack, attackCostValue, maxValue, fill, barHeight, 0, alpha);
//              renderCost(matrixStack, abilityCostValue, maxValue, fill, barHeight, 3, alpha);
            }
            // scale
            drawBarElement(matrixStack, x + 1, y + 1, scaleTexX, scaleTexY, width - 2, length);
            if (barAlpha != 1.0F) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }
    
    protected abstract void barFill(MatrixStack matrixStack, int x, int y, Alignment alignment, 
            int texX, int texY, int width, int length, int fill);
    
    protected void drawBarElement(MatrixStack matrixStack, int x, int y, int texX, int texY, int width, int length) {
        gui.blit(matrixStack, x, y, texX, texY, width, length);
    }
    
    protected static final int ICON_WIDTH = 12;
    protected static final int ICON_HEIGHT = 16;
    protected int[] getIconTex(BarType type, NonStandPowerType<?> powerType, BarsOrientation orientation) {
        switch (type) {
        case STAMINA:
            return new int[] {128, 0, ICON_WIDTH, ICON_HEIGHT, 1, 0, -7};
        case RESOLVE:
            switch (orientation) {
            case VERTICAL:
                return new int[] {128, 32, ICON_WIDTH, ICON_HEIGHT, 2, -999, -999};
            case HORIZONTAL:
                return new int[] {128, 64, ICON_HEIGHT + 1, ICON_WIDTH - 2, 2, -4, 0};
            }
        case ENERGY:
            int texY = 160;
            int horizontalYOffset = 0;
            if (powerType == ModVampirism.VAMPIRISM.get()) {
                texY = 0;
                horizontalYOffset = -3;
            }
            else if (powerType == ModHamon.HAMON.get()) {
                texY = 16;
                horizontalYOffset = -7;
            }
            return new int[] {240, texY, ICON_WIDTH, ICON_HEIGHT, 1, 0, horizontalYOffset};
        default:
            return new int[] {240, 160, ICON_WIDTH, ICON_HEIGHT, 1, 0, 0};
        }
    }
    
    protected final void renderIcon(MatrixStack matrixStack, int x, int y, 
            int texX, int texY, int width, int height, int scale) {
        if (scale > 1) {
            matrixStack.pushPose();
            matrixStack.scale(1F / scale, 1F / scale, 1F);
            matrixStack.translate(x * (scale - 1), y * (scale - 1), 0);
        }
        gui.blit(matrixStack, x, y, 
                texX, texY, width * scale, height * scale);
        if (scale > 1) {
            matrixStack.popPose();
        }
    }
    
    protected enum BarType {
        ENERGY,
        STAMINA,
        RESOLVE
    }
    
    

    protected void drawText(MatrixStack matrixStack, ITextComponent text, int x, int y, Alignment alignment, 
            int color, float partialTick, FontRenderer font, ActionsOverlayGui hud) {
        hud.drawBackdrop(matrixStack, x, y, font.width(text), alignment, null, partialTick);
        hud.drawString(matrixStack, font, text, x, y, alignment, color);
    }
}
