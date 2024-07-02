package com.github.standobyte.jojo.client.renderer.entity.stand;

import java.util.function.Supplier;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.model.entity.stand.MagiciansRedModel;
import com.github.standobyte.jojo.client.renderer.entity.stand.layer.MagiciansRedFlameLayer;
import com.github.standobyte.jojo.entity.stand.stands.MagiciansRedEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class MagiciansRedRenderer extends StandEntityRenderer<MagiciansRedEntity, MagiciansRedModel> {
    public static final Supplier<TextureAtlasSprite> FIRE_0 = () -> ModelBakery.FIRE_0.sprite();
    public static final Supplier<TextureAtlasSprite> FIRE_1 = () -> ModelBakery.FIRE_1.sprite();

    public MagiciansRedRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MagiciansRedModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/stand/magicians_red.png"), 0);
        addLayer(new MagiciansRedFlameLayer(this));
    }
}
