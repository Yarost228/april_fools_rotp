package com.github.standobyte.jojo.client.renderer.entity.damaging.projectile;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.model.entity.projectile.CDBloodCutterModel;
import com.github.standobyte.jojo.client.renderer.entity.SimpleEntityRenderer;
import com.github.standobyte.jojo.entity.damaging.projectile.CDBloodCutterEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CDBloodCutterRenderer extends SimpleEntityRenderer<CDBloodCutterEntity, CDBloodCutterModel> {

    public CDBloodCutterRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CDBloodCutterModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/cd_blood_cutter.png"));
    }

}
