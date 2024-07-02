package com.github.standobyte.jojo.client.renderer.entity.stand;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.model.entity.stand.TheWorldModel;
import com.github.standobyte.jojo.entity.stand.stands.TheWorldEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TheWorldRenderer extends StandEntityRenderer<TheWorldEntity, TheWorldModel> {
    
    public TheWorldRenderer(EntityRendererManager renderManager) {
        super(renderManager, new TheWorldModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/stand/the_world.png"), 0);
    }
}
