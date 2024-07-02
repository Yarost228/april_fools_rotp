package com.github.standobyte.jojo.client.ui.screen.hamon;

import java.util.List;
import java.util.stream.Collectors;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.nonstand.type.hamon.skill.CharacterHamonTechnique;
import com.google.common.collect.Streams;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;

public class HamonCharacterTechniqueBox {
    private final CharacterHamonTechnique technique;
    private final List<IReorderingProcessor> name;
    private final List<HamonSkillElementTechniquePerk> perks;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    
    public HamonCharacterTechniqueBox(CharacterHamonTechnique technique, int y, List<IReorderingProcessor> name, FontRenderer font) {
        this.technique = technique;
        this.x = 8;
        this.y = y;
        this.name = name;
        this.perks = Streams.mapWithIndex(
                technique.getPerksOnPick().filter(perk -> perk != null), 
                (skill, i) -> new HamonSkillElementTechniquePerk(skill, this.x + (int) i * 18, this.y, font))
                .collect(Collectors.toList());
        this.width = HamonScreen.WINDOW_WIDTH - HamonScreen.WINDOW_THIN_BORDER * 2 - 16;
        this.height = Math.max(name.size() * 9 + 26, 36);
    }
    
    @SuppressWarnings("deprecation")
    public void render(MatrixStack matrixStack, HamonData hamon, int x, int y, int mouseX, int mouseY) {
        ClientUtil.drawTooltipRectangle(matrixStack, 
                this.x + x, this.y + y, width, height, 
                0x80100010, 0x505000FF, 0x5028007F, 0);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (renderPerks(hamon)) {
            for (HamonSkillElementTechniquePerk perk : perks) {
                if (perk.isVisible()) {
                    CharacterHamonTechnique userTechnique = hamon.getCharacterTechnique();
                    if (!(userTechnique == this.technique || perk.isMouseOver(x, y, mouseX, mouseY))) {
                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.4F);
                    }
                    perk.renderSkillIcon(matrixStack, x, y);
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
        RenderSystem.disableBlend();
    }
    
    public void drawText(MatrixStack matrixStack, FontRenderer font, HamonData hamon, int x, int y) {
        int perksCount = renderPerks(hamon) ? (int) perks.stream().filter(HamonSkillElementTechniquePerk::isVisible).count() : 0;
        for (int line = 0; line < name.size(); line++) {
            font.drawShadow(matrixStack, name.get(line), 
                    this.x + x + perksCount * 18 + 3, this.y + y + 1 + line * 9, 0xFFFFFF);
        }
    }
    
    public void drawTooltip(HamonScreen hamonScreen, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        x += this.x;
        y += this.y;

        if (renderPerks(hamonScreen.hamon)) {
            for (HamonSkillElementTechniquePerk perk : perks) {
                if (perk.isVisible() && perk.isMouseOver(x, y, this.x + mouseX, this.y + mouseY) ) {
                    perk.drawTooltip(hamonScreen, matrixStack, mouseX, mouseY);
                }
            }
        }
    }
    
    int getHeight() {
        return height;
    }
    
    private boolean renderPerks(HamonData hamon) {
        CharacterHamonTechnique userTechnique = hamon.getCharacterTechnique();
        return userTechnique == null || userTechnique == this.technique;
    }
    
    
    
}
