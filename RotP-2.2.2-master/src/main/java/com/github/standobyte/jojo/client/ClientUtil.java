package com.github.standobyte.jojo.client;

import java.util.Map;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ui.screen.hamon.HamonScreen;
import com.github.standobyte.jojo.client.ui.screen.mob.RockPaperScissorsScreen;
import com.github.standobyte.jojo.entity.mob.rps.RockPaperScissorsGame;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromclient.ClHamonWindowOpenedPacket;
import com.github.standobyte.jojo.util.utils.MathUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.gui.GuiUtils;

@SuppressWarnings( {"deprecation", "resource"} )
public class ClientUtil {
    public static final ResourceLocation ADDITIONAL_UI = new ResourceLocation(JojoMod.MOD_ID, "textures/gui/additional.png");
    public static final int MAX_MODEL_LIGHT = LightTexture.pack(15, 15);

    public static PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static World getClientWorld() {
        return Minecraft.getInstance().level;
    }
    
    public static boolean isLocalServer() {
        return Minecraft.getInstance().isLocalServer();
    }
    
    public static boolean isShiftPressed() {
    	return Screen.hasShiftDown();
    }
    
    public static boolean isDestroyingBlock() {
    	return Minecraft.getInstance().gameMode.isDestroying();
    }

    public static Entity getEntityById(int entityId) {
        return Minecraft.getInstance().level.getEntity(entityId);
    }
    
    public static Entity getCrosshairPickEntity() {
        return Minecraft.getInstance().crosshairPickEntity;
    }
    
    public static float getPartialTick() {
        return Minecraft.getInstance().getFrameTime();
    }
    
    public static void setCameraEntityPreventShaderSwitch(Minecraft mc, Entity entity) {
        mc.setCameraEntity(entity);
        if (mc.gameRenderer.currentEffect() == null) {
            ResourceLocation shader = ClientEventHandler.getInstance().getCurrentShader();
            if (shader != null) {
                mc.gameRenderer.loadEffect(shader);
            }
        }
    }
    
    public static void openScreen(Screen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
    
    public static void openRockPaperScissorsScreen(RockPaperScissorsGame game) {
        Minecraft.getInstance().setScreen(new RockPaperScissorsScreen(game));
    }
    
    public static void closeRockPaperScissorsScreen(RockPaperScissorsGame game) {
        if (Minecraft.getInstance().screen instanceof RockPaperScissorsScreen) {
            RockPaperScissorsScreen screen = (RockPaperScissorsScreen) Minecraft.getInstance().screen;
            if (screen.game == game) {
                Minecraft.getInstance().setScreen(null);
            }
        }
    }

    public static void openHamonTeacherUi() {
//        Minecraft.getInstance().setScreen(new HamonScreen());
        PacketManager.sendToServer(new ClHamonWindowOpenedPacket());
    }

    public static void drawRightAlignedString(MatrixStack matrixStack, FontRenderer font, String line, float x, float y, int color) {
        font.drawShadow(matrixStack, line, x - font.width(line), y, color);
    }

    public static void drawRightAlignedString(MatrixStack matrixStack, FontRenderer font, ITextComponent line, float x, float y, int color) {
        drawRightAlignedString(matrixStack, font, line.getVisualOrderText(), x, y, color);
    }

    public static void drawRightAlignedString(MatrixStack matrixStack, FontRenderer font, IReorderingProcessor line, float x, float y, int color) {
        font.drawShadow(matrixStack, line, x - font.width(line), y, color);
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer font, IReorderingProcessor line, float x, float y, int color) {
        font.drawShadow(matrixStack, line, x - font.width(line) / 2, y, color);
    }
    
    public static void drawTooltipRectangle(MatrixStack matrixStack, int x, int y, int width, int height) {
        drawTooltipRectangle(matrixStack, x, y, width, height, 
                GuiUtils.DEFAULT_BACKGROUND_COLOR, GuiUtils.DEFAULT_BORDER_COLOR_START, GuiUtils.DEFAULT_BORDER_COLOR_END, 400);
    }
    
    public static void drawTooltipRectangle(MatrixStack matrixStack, int x, int y, int width, int height, 
            int backgroundColor, int borderColorStart, int borderColorEnd, int zLevel) {
        RenderSystem.disableRescaleNormal();
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        matrixStack.pushPose();
        Matrix4f mat = matrixStack.last().pose();
        
        drawGradientRect(mat, zLevel, x - 3, y - 4, x + width + 3, y - 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, x - 3, y + height + 3, x + width + 3, y + height + 4, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, x - 3, y - 3, x + width + 3, y + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, x - 4, y - 3, x - 3, y + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, x + width + 3, y - 3, x + width + 4, y + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, borderColorStart, borderColorEnd);
        drawGradientRect(mat, zLevel, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, borderColorStart, borderColorEnd);
        drawGradientRect(mat, zLevel, x - 3, y - 3, x + width + 3, y - 3 + 1, borderColorStart, borderColorStart);
        drawGradientRect(mat, zLevel, x - 3, y + height + 2, x + width + 3, y + height + 3, borderColorEnd, borderColorEnd);

        matrixStack.popPose();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        RenderSystem.enableRescaleNormal();
    }
    
    private static void drawGradientRect(Matrix4f mat, int zLevel, int left, int top, int right, int bottom, int startColor, int endColor) {
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;
        float startRed   = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >>  8 & 255) / 255.0F;
        float startBlue  = (float)(startColor       & 255) / 255.0F;
        float endAlpha   = (float)(endColor   >> 24 & 255) / 255.0F;
        float endRed     = (float)(endColor   >> 16 & 255) / 255.0F;
        float endGreen   = (float)(endColor   >>  8 & 255) / 255.0F;
        float endBlue    = (float)(endColor         & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.vertex(mat, right,    top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.vertex(mat,  left,    top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.vertex(mat,  left, bottom, zLevel).color(  endRed,   endGreen,   endBlue,   endAlpha).endVertex();
        buffer.vertex(mat, right, bottom, zLevel).color(  endRed,   endGreen,   endBlue,   endAlpha).endVertex();
        tessellator.end();
    }
    
    public static void fillSingleRect(double x, double y, double width, double height, int red, int green, int blue, int alpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuilder();
        fillRect(bufferBuilder, x, y, width, height, red, green, blue, alpha);
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
    }
    
    public static void fillRect(BufferBuilder bufferBuilder, double x, double y, double width, double height, int red, int green, int blue, int alpha) {
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + 0 , y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(x + 0 , y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(x + width , y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(x + width , y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().end();
    }
    
    public static String getShortenedTranslationKey(String originalKey) {
        String shortenedKey = originalKey + ".shortened";
        return I18n.exists(shortenedKey) ? shortenedKey : originalKey;
    }
    
    public static int getFoliageColor(BlockState blockState, @Nullable IBlockDisplayReader world, BlockPos blockPos) {
        return Minecraft.getInstance().getBlockColors().getColor(blockState, world, blockPos, 0);
    }
    
    public static void playSoundAtClient(SoundEvent sound, SoundCategory category, BlockPos soundPos, float volume, float pitch) {
        ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (renderInfo.isInitialized()) {
            Vector3d clientPos = renderInfo.getPosition();
            Vector3d soundDir = Vector3d.atCenterOf(soundPos).subtract(clientPos);
            double dist = soundDir.length();
            if (dist > 0) {
                clientPos = clientPos.add(soundDir.scale(2 / dist));
            }
            ClientUtil.getClientWorld().playLocalSound(clientPos.x, clientPos.y, clientPos.z, 
                    sound, category, volume, pitch, false);
        }
    }
    
    public static void playMusic(SoundEvent sound, float volume, float pitch) {
        Minecraft.getInstance().getSoundManager().play(new SimpleSound(
                sound.getLocation(), 
                SoundCategory.RECORDS, 
                volume, pitch, false, 0, ISound.AttenuationType.NONE, 
                0, 0, 0, true));
    }
    
    public static void createHamonSparkParticles(double x, double y, double z, int particlesCount) {
        Minecraft.getInstance().getConnection().handleParticleEvent(new SSpawnParticlePacket(
                ModParticles.HAMON_SPARK.get(), false, x, y, z, 0.05F, 0.05F, 0.05F, 0.25F, particlesCount));
    }
    
    public static void createParticlesEmitter(Entity entity, IParticleData type, int ticks) {
        Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, type, ticks);
    }
    
    public static boolean decreasedParticlesSetting() {
        return Minecraft.getInstance().options.particles == ParticleStatus.DECREASED;
    }
    
    public static float[] rgb(int color) {
        int red = (color & 0xFF0000) >> 16;
        int green = (color & 0x00FF00) >> 8;
        int blue = color & 0x0000FF;
        return new float[] {
                (float) red / 255F,
                (float) green / 255F,
                (float) blue / 255F
        };
    }
    
    public static int discColor(int color) {
        return (((0xFFFFFF - color) & 0xFEFEFE) >> 1) + color;
    }
    
    public static void vertex(MatrixStack.Entry matrixEntry, IVertexBuilder vertexBuilder, 
            int packedLight, int packedOverlay, float red, float green, float blue, float alpha, 
            float x, float y, float z, float texU, float texV) {
        vertexBuilder
        .vertex(matrixEntry.pose(), x, y, z)
        .color(red, green, blue, alpha)
        .uv(texU, texV)
        .overlayCoords(packedOverlay)
        .uv2(packedLight)
        .normal(matrixEntry.normal(), 0.0F, 1.0F, 0.0F)
        .endVertex();
    }    
    
    public static void vertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, 
            int packedLight, int packedOverlay, float red, float green, float blue, float alpha, 
            float offsetX, float offsetY, float offsetZ, 
            float texU, float texV, 
            float normalX, float normalY, float normalZ) {
        vertexBuilder
        .vertex(matrix, offsetX, offsetY, offsetZ)
        .color(red, green, blue, alpha)
        .uv(texU, texV)
        .overlayCoords(packedOverlay)
        .uv2(packedLight)
        .normal(normals, normalX, normalZ, normalY)
        .endVertex();
    }

    public static float getHighlightAlpha(float ticks, float cycleTicks, float maxAlphaTicks, float minAlpha, float maxAlpha) {
        ticks %= cycleTicks;
        float coeff = maxAlpha / maxAlphaTicks;
        float alpha = ticks <= cycleTicks / 2 ? coeff * ticks : coeff * (cycleTicks - ticks);
        return Math.min(alpha, maxAlpha - minAlpha) + minAlpha;
    }
    
    public static ResourceLocation getPlayerSkin(GameProfile gameProfile) {
        Minecraft minecraft = Minecraft.getInstance();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);
        if (map.containsKey(Type.SKIN)) {
            return minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
        } else {
            return DefaultPlayerSkin.getDefaultSkin(PlayerEntity.createPlayerUUID(gameProfile));
        }
    }

    public static void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public static void setRotationAngleDegrees(ModelRenderer modelRenderer, float x, float y, float z) {
        setRotationAngle(modelRenderer, x * MathUtil.DEG_TO_RAD, y * MathUtil.DEG_TO_RAD, z * MathUtil.DEG_TO_RAD);
    }
    
    public static ITextComponent donoItemTooltip(String donoUsername) {
        return new TranslationTextComponent("item.jojo.dono_tooltip", donoUsername).withStyle(TextFormatting.DARK_GRAY);
    }
}
