package com.github.standobyte.jojo.client.renderer.entity.damaging.projectile;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.model.entity.projectile.HamonBubbleModel;
import com.github.standobyte.jojo.client.renderer.entity.SimpleEntityRenderer;
import com.github.standobyte.jojo.entity.damaging.projectile.HamonBubbleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HamonBubbleRenderer extends SimpleEntityRenderer<HamonBubbleEntity, HamonBubbleModel<HamonBubbleEntity>> {

    public HamonBubbleRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HamonBubbleModel<>(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/hamon_bubble.png"));
    }

}
