package com.github.standobyte.jojo.client.renderer.entity.damaging.extending;

import com.github.standobyte.jojo.client.model.entity.ownerbound.repeating.HGStringModel;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.HGStringEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;

public class HGStringRenderer extends HGStringAbstractRenderer<HGStringEntity> {

    public HGStringRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HGStringModel<HGStringEntity>());
    }
}
