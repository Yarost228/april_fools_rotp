package com.github.standobyte.jojo.client.ui.screen.hamon;

import com.github.standobyte.jojo.power.nonstand.type.hamon.skill.AbstractHamonSkill;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HamonSkillGuiElement {
    protected final AbstractHamonSkill skill;
    private final IFormattableTextComponent name;
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    
    public HamonSkillGuiElement(AbstractHamonSkill skill, 
            int x, int y, int width, int height) {
        this.skill = skill;
        this.name = new TranslationTextComponent("hamonSkill." + skill.getName() + ".name");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    IFormattableTextComponent getName() {
        return name;
    }
    
    public boolean isMouseOver(int scrollX, int scrollY, int mouseX, int mouseY) {
        double realX = scrollX + this.x;
        double realY = scrollY + this.y;
        return mouseX >= realX && mouseX < realX + width && mouseY >= realY && mouseY < realY + height;
    }
    
    public void renderSkillIcon(MatrixStack matrixStack, int x, int y) {
        HamonSkillsTabGui.renderHamonSkillIcon(matrixStack, skill, this.x + x, this.y + y);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    void drawTooltip(HamonScreen hamonScreen, MatrixStack matrixStack, int mouseX, int mouseY) {
        hamonScreen.renderTooltip(matrixStack, name, mouseX, mouseY);
    }
    
    public AbstractHamonSkill getHamonSkill() {
        return skill;
    }
}
