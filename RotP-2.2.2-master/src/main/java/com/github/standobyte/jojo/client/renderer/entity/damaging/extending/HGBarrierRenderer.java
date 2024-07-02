package com.github.standobyte.jojo.client.renderer.entity.damaging.extending;

import com.github.standobyte.jojo.client.model.entity.ownerbound.repeating.HGStringModel;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.HGBarrierEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;

public class HGBarrierRenderer extends HGStringAbstractRenderer<HGBarrierEntity> {

    public HGBarrierRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HGStringModel<HGBarrierEntity>());
    }
}
