package com.github.standobyte.jojo.util.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCap;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.client.InputHandler;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.item.ClothesSet;
import com.github.standobyte.jojo.network.NetworkUtil;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.PlayVoiceLinePacket;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.NonStandPowerType;
import com.github.standobyte.jojo.power.stand.StandUtil;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.INPC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.EndNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SSpawnMovingSoundEffectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;

public class JojoModUtil {
    public static final IFormattableTextComponent EMPTY_TEXT = new StringTextComponent("");
    private static final ImmutableMap<Class<? extends INBT>, Integer> NBT_ID = new ImmutableMap.Builder<Class<? extends INBT>, Integer>()
            .put(EndNBT.class, 0)
            .put(ByteNBT.class, 1)
            .put(ShortNBT.class, 2)
            .put(IntNBT.class, 3)
            .put(LongNBT.class, 4)
            .put(FloatNBT.class, 5)
            .put(DoubleNBT.class, 6)
            .put(ByteArrayNBT.class, 7)
            .put(StringNBT.class, 8)
            .put(ListNBT.class, 9)
            .put(CompoundNBT.class, 10)
            .put(IntArrayNBT.class, 11)
            .put(LongArrayNBT.class, 12)
            .build();
    
    public static int getNbtId(Class<? extends INBT> clazz) {
        return NBT_ID.getOrDefault(clazz, -1);
    }
    
    public static CompoundNBT replaceNbtValues(CompoundNBT original, CompoundNBT replacedEntries, CompoundNBT replacingEntries) {
        int compoundId = getNbtId(CompoundNBT.class);
        for (String key : replacedEntries.getAllKeys()) {
            if (replacedEntries.contains(key) && original.contains(key) && replacedEntries.contains(key)) {
                INBT originalValue = original.get(key);
                INBT replacedValue = replacedEntries.get(key);
                INBT replacingValue = replacingEntries.get(key);
                if (originalValue.getId() == compoundId) {
                    if (replacedValue.getId() == compoundId && replacingValue.getId() == compoundId) {
                        replaceNbtValues((CompoundNBT) originalValue, (CompoundNBT) replacedValue, (CompoundNBT) replacingValue);
                    }
                }
                else if (originalValue.equals(replacedValue)) {
                    original.put(key, replacingValue.copy());
                }
            }
        }
        return original;
    }
    
    
    
    @Nullable
    public static <E> E getOrLast(List<E> list, int index) {
        return list.isEmpty() ? null : list.get(Math.min(index, list.size() - 1));
    }
    
    @Nullable
    public static <T extends Enum<T>> T enumValueOfNullable(Class<T> enumType, @Nonnull String name) {
        try {
            return Enum.valueOf(enumType, name);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static <T> boolean booleanMapOrFalse(Optional<T> optional, Function<T, Boolean> action) {
        return optional.map(action).orElse(false);
    }
    
    
    
    public static Vector3d getEntityPosition(Entity entity, float partialTick) {
    	return partialTick == 1.0F ? entity.position() : entity.getPosition(partialTick);
    }
    
    public static void giveItemTo(LivingEntity entity, ItemStack item, boolean drop) {
        if (!entity.level.isClientSide() && !item.isEmpty()) {
            if (entity instanceof PlayerEntity) {
                drop = !(((PlayerEntity) entity).inventory.add(item) && item.isEmpty());
            }
            if (drop) {
                entity.level.addFreshEntity(dropAt(entity, item));
            }
        }
    }
    
    public static ItemEntity dropAt(LivingEntity entity, ItemStack item) {
        if (item.isEmpty()) {
            return null;
        }
        else {
            ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getEyeY() - 0.3, entity.getZ(), item);
            itemEntity.setNoPickUpDelay();
            itemEntity.setOwner(entity.getUUID());
            return itemEntity;
        }
    }
    
    
    
    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter) {
        return rayTrace(entity, reachDistance, entityFilter, 0);
    }

    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, 
            double rayTraceInflate) {
        return rayTrace(entity, reachDistance, entityFilter, rayTraceInflate, 0);
    }

    public static RayTraceResult rayTrace(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, 
            double rayTraceInflate, double standPrecision) {
        return rayTraceMultipleEntities(entity, reachDistance, entityFilter, rayTraceInflate, standPrecision)[0];
    }
    
    public static RayTraceResult[] rayTraceMultipleEntities(Entity entity, double reachDistance, @Nullable Predicate<Entity> entityFilter, 
            double rayTraceInflate, double standPrecision) {
    	return rayTraceMultipleEntities(entity.getEyePosition(1.0F), entity.getViewVector(1.0F), reachDistance, 
    			entity.level, entity, entityFilter, rayTraceInflate, standPrecision);
    }
    
    public static RayTraceResult[] rayTraceMultipleEntities(Vector3d startPos, Vector3d rayVec, double distance, 
    		World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, 
    		double rayTraceInflate, double standPrecision) {
    	Vector3d rtVec = rayVec.normalize().scale(distance);
    	Vector3d endPos = startPos.add(rtVec);
    	AxisAlignedBB aabb = entity.getBoundingBox().expandTowards(rtVec).inflate(1.0D);
    	return rayTraceMultipleEntities(startPos, endPos, aabb, distance, entity.level, entity, entityFilter, rayTraceInflate, standPrecision);
    }

    public static RayTraceResult rayTrace(Vector3d startPos, Vector3d rayVec, double distance, 
    		World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, 
    		double rayTraceInflate, double standPrecision) {
    	return rayTraceMultipleEntities(startPos, rayVec, distance, world, entity, entityFilter, rayTraceInflate, standPrecision)[0];
    }

    public static RayTraceResult[] rayTraceMultipleEntities(Vector3d startPos, Vector3d endPos, AxisAlignedBB aabb, 
            double minDistance, World world, @Nullable Entity entity, @Nullable Predicate<Entity> entityFilter, 
            double rayTraceInflate, double standPrecision) {
        aabb.inflate(rayTraceInflate);
        double minDistanceSqr = minDistance * minDistance;
        Map<EntityRayTraceResult, Double> rayTracedWithDistance = new HashMap<>();
        for (Entity potentialTarget : world.getEntities(entity, aabb, e -> !e.isSpectator() && e.isPickable() && (entityFilter == null || entityFilter.test(e)))) {
            AxisAlignedBB targetCollisionAABB = potentialTarget.getBoundingBox().inflate((double) potentialTarget.getPickRadius() + rayTraceInflate);
            targetCollisionAABB = standPrecisionTargetHitbox(targetCollisionAABB, standPrecision);
            Optional<Vector3d> clipOptional = targetCollisionAABB.clip(startPos, endPos);
            if (targetCollisionAABB.contains(startPos)) {
                if (minDistanceSqr >= 0.0D) {
                    rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, clipOptional.orElse(startPos)), 0.0);
                }
            } else if (clipOptional.isPresent()) {
                Vector3d clipVec = clipOptional.get();
                double clipDistanceSqr = startPos.distanceToSqr(clipVec);
                if (clipDistanceSqr < minDistanceSqr || minDistanceSqr == 0.0D) {
                    if (entity != null && potentialTarget.getRootVehicle() == entity.getRootVehicle() && !potentialTarget.canRiderInteract()) {
                        if (minDistanceSqr == 0.0D) {
                            rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, clipVec), 0.0);
                        }
                    } else {
                        rayTracedWithDistance.put(new EntityRayTraceResult(potentialTarget, clipVec), clipDistanceSqr);
                    }
                }
            }
        }
        if (rayTracedWithDistance.isEmpty()) {
            return new RayTraceResult[] { 
                    world.clip(new RayTraceContext(startPos, endPos, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
                    };
        }
        return rayTracedWithDistance.entrySet().stream()
        .sorted(Comparator.comparingDouble(Map.Entry::getValue))
        .map(Map.Entry::getKey)
        .toArray(EntityRayTraceResult[]::new);
        
//        return new EntityRayTraceResult(targetEntity, targetEntityPos);
    }
    
    private static AxisAlignedBB standPrecisionTargetHitbox(AxisAlignedBB aabb, double precision) {
        if (precision > 0) {
            double smallAabbAddFraction = Math.min(precision, 16) / 16;
            aabb.inflate(
                    Math.max(1.0 - aabb.getXsize(), 0) * smallAabbAddFraction, 
                    Math.max(2.5 - aabb.getYsize(), 0) * smallAabbAddFraction, 
                    Math.max(1.0 - aabb.getZsize(), 0) * smallAabbAddFraction);
            aabb = aabb.inflate(precision / 20);
        }
        return aabb;
    }

    public static RayTraceResult getHitResult(Entity projectile, @Nullable Predicate<Entity> targetPredicate, RayTraceContext.BlockMode blockMode) {
        World world = projectile.level;
        Vector3d pos = projectile.position();
        Vector3d nextPos = pos.add(projectile.getDeltaMovement());
        RayTraceResult rayTraceResult = world.clip(new RayTraceContext(pos, nextPos, blockMode, RayTraceContext.FluidMode.NONE, projectile));
        if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
            nextPos = rayTraceResult.getLocation();
        }
        RayTraceResult entityRayTraceResult = ProjectileHelper.getEntityHitResult(world, projectile, pos, nextPos, 
                projectile.getBoundingBox().expandTowards(projectile.getDeltaMovement()).inflate(1.0D), targetPredicate);
        if (entityRayTraceResult != null) {
            rayTraceResult = entityRayTraceResult;
        }
        return rayTraceResult;
    }

    public static boolean isAnotherEntityTargeted(RayTraceResult rayTraceResult, Entity targettingEntity) {
        return (rayTraceResult.getType() == RayTraceResult.Type.ENTITY
                && !((EntityRayTraceResult) rayTraceResult).getEntity().is(targettingEntity));
    }

    public static double getDistance(Entity entity, AxisAlignedBB targetAabb) {
        Vector3d startPos = entity.getEyePosition(1.0F);
        if (targetAabb.contains(startPos)) {
            return 0;
        }
        Vector3d endPos = new Vector3d(
                MathHelper.lerp(0.5D, targetAabb.minX, targetAabb.maxX), 
                MathHelper.lerp(entity.getBbHeight() == 0 ? 0 : 
                    entity.getEyeHeight() / entity.getBbHeight(), targetAabb.minY, targetAabb.maxY), 
                MathHelper.lerp(0.5D, targetAabb.minZ, targetAabb.maxZ));
        Optional<Vector3d> clipOptional = targetAabb.clip(startPos, endPos);
        return clipOptional.map(clipVec -> startPos.distanceTo(clipVec) - entity.getBbWidth() / 2).orElse(-1D);
    }

    public static Iterable<Entity> getAllEntities(World world) {
        return world.isClientSide() ? ((ClientWorld) world).entitiesForRendering() : ((ServerWorld) world).getAllEntities();
    }

    public static void rotateTowards(Entity entity, Vector3d targetPos, float maxAngle) {
        Vector3d targetVec = targetPos.subtract(entity.getEyePosition(1.0F));

        float yRot = MathUtil.yRotDegFromVec(targetVec);
        float xRot = MathUtil.xRotDegFromVec(targetVec);

        yRot = entity.yRot + MathHelper.clamp(MathHelper.degreesDifference(entity.yRot, yRot), -maxAngle, maxAngle);
        xRot = entity.xRot + MathHelper.clamp(MathHelper.degreesDifference(entity.xRot, xRot), -maxAngle, maxAngle);

        entity.yRot = yRot % 360.0F;
        entity.xRot = xRot % 360.0F;
        entity.setYHeadRot(yRot);
    }

    public static boolean dispenseOnNearbyEntity(IBlockSource blockSource, ItemStack itemStack, Predicate<LivingEntity> action, boolean shrinkStack) {
        BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> entities = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(blockPos), EntityPredicates.NO_SPECTATORS);
        for (LivingEntity entity : entities) {
            if (action.test(entity)) {
                if (shrinkStack) {
                    itemStack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }



    public static boolean canEntityDestroy(ServerWorld world, BlockPos blockPos, BlockState blockState, LivingEntity entity) {
        if (JojoModConfig.getCommonConfigInstance(world.isClientSide()).abilitiesBreakBlocks.get()
                && blockState.canEntityDestroy(world, blockPos, entity)
                && ForgeEventFactory.onEntityDestroyBlock(entity, blockPos, blockState)) {
        	PlayerEntity player = null;
        	if (entity instanceof PlayerEntity) {
        		player = (PlayerEntity) entity;
        	}
        	else if (entity instanceof StandEntity) {
        		LivingEntity standUser = ((StandEntity) entity).getUser();
            	if (standUser instanceof PlayerEntity) {
            		player = (PlayerEntity) standUser;
            	}
        	}
        	return player == null || world.mayInteract(player, blockPos);
        }
        return false;
    }



    public static ResourceLocation makeTextureLocation(String folderName, String namespace, String path) {
        return new ResourceLocation(namespace, "textures/"+ folderName + "/" + path + ".png");
    }
    
    
    
    public static boolean playerHasClientInput(PlayerEntity player) {
        return player.level.isClientSide() ? InputHandler.getInstance().hasInput
                : player.getCapability(PlayerUtilCapProvider.CAPABILITY).map(PlayerUtilCap::hasClientInput).orElse(false);
    }
    


    public static <T extends Entity> List<T> entitiesAround(Class<? extends T> clazz, Entity centerEntity, double radius, boolean includeSelf, @Nullable Predicate<? super T> filter) {
        Vector3d centerPos = centerEntity.getBoundingBox().getCenter();
        AxisAlignedBB aabb = new AxisAlignedBB(centerPos.subtract(radius, radius, radius), centerPos.add(radius, radius, radius));
        return centerEntity.level.getEntitiesOfClass(clazz, aabb, entity -> (includeSelf || entity != centerEntity) && (filter == null || filter.test(entity)));
    }

    public static boolean isUndead(LivingEntity entity) {
        if (entity.getMobType() == CreatureAttribute.UNDEAD) {
            return true;
        }
        if (entity instanceof PlayerEntity) {
            return isPlayerUndead((PlayerEntity) entity);
        }
        return false;
    }

    public static boolean isPlayerUndead(PlayerEntity player) {
        return INonStandPower.getNonStandPowerOptional(player).map(power -> {
            NonStandPowerType<?> powerType = power.getType();
            return powerType == ModVampirism.VAMPIRISM.get();
        }).orElse(false); 
    }

    public static boolean canBleed(LivingEntity entity) {
        return entity.getMobType() != CreatureAttribute.UNDEAD && 
                (entity instanceof PlayerEntity || entity instanceof AgeableEntity || entity instanceof INPC || entity instanceof AbstractIllagerEntity || entity instanceof WaterMobEntity);
    }

    public static void extinguishFieryStandEntity(Entity entity, ServerWorld world) {
        JojoModUtil.playSound(world, null, entity.getX(), entity.getY(), entity.getZ(), 
                SoundEvents.FIRE_EXTINGUISH, entity.getSoundSource(), 1.0F, 1.0F, StandUtil::shouldHearStands);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY(), entity.getZ(), 
                8, entity.getBbWidth() / 2F, entity.getBbHeight() / 2F, entity.getBbWidth() / 2F, 0);
        entity.remove();
    }
    
    public static void deflectProjectile(Entity projectile, @Nullable Vector3d deflectVec) {
        projectile.setDeltaMovement(deflectVec != null ? 
                deflectVec.scale(Math.sqrt(projectile.getDeltaMovement().lengthSqr() / deflectVec.lengthSqr()))
                : projectile.getDeltaMovement().reverse());
        projectile.move(MoverType.SELF, projectile.getDeltaMovement());
        if (projectile instanceof ModdedProjectileEntity) {
            ((ModdedProjectileEntity) projectile).setIsDeflected();
        }
    }
    
    public static boolean removeEffectInstance(LivingEntity entity, EffectInstance effectInstance) {
        if (entity.getActiveEffectsMap().get(effectInstance.getEffect()) == effectInstance) {
            return entity.removeEffect(effectInstance.getEffect());
        }
        return false;
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent voiceLine) {
        sayVoiceLine(entity, voiceLine, null);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent voiceLine, @Nullable ClothesSet character) {
        sayVoiceLine(entity, voiceLine, character, 1.0F, 1.0F);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent sound, @Nullable ClothesSet character, float volume, float pitch) {
        sayVoiceLine(entity, sound, character, volume, pitch, 200);
    }

    public static void sayVoiceLine(LivingEntity entity, SoundEvent sound, @Nullable ClothesSet character, float volume, float pitch, int voiceLineDelay) {
        if (entity.level.isClientSide() || entity.hasEffect(Effects.INVISIBILITY) ||
                character != null && character != ClothesSet.getClothesSet(entity)) {
            return;
        }
        SoundCategory category = SoundCategory.VOICE;
        if (entity instanceof PlayerEntity) {
            PlayVoiceLinePacket packet;
            if (!canPlayVoiceLine((PlayerEntity) entity, sound, voiceLineDelay)) {
                packet = PlayVoiceLinePacket.notTriggered(entity.getId());
            }
            else {
                PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity(null, sound, category, volume, pitch);
                if (event.isCanceled() || event.getSound() == null) {
                    packet = PlayVoiceLinePacket.notTriggered(entity.getId());
                }
                else {
                    sound = event.getSound();
                    category = event.getCategory();
                    volume = event.getVolume();
                    packet = new PlayVoiceLinePacket(sound, category, entity.getId(), volume, pitch);
                }
            }
            PacketManager.sendToNearby(packet, null, entity.getX(), entity.getY(), entity.getZ(), 
                    volume > 1.0F ? (double) (16.0F * volume) : 16.0D, entity.level.dimension());
        }
        else {
            entity.level.playSound(null, entity, sound, category, volume, pitch);
        }
    }

    private static boolean canPlayVoiceLine(PlayerEntity entity, SoundEvent voiceLine, int voiceLineDelay) {
        return entity.getCapability(PlayerUtilCapProvider.CAPABILITY)
                .map(cap -> cap.checkNotRepeatingVoiceLine(voiceLine, voiceLineDelay)).orElse(true);
    }

    public static void playSound(World world, @Nullable PlayerEntity clientHandled, BlockPos blockPos, 
            SoundEvent sound, SoundCategory category, float volume, float pitch, Predicate<PlayerEntity> condition) {
        playSound(world, clientHandled, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, 
                sound, category, volume, pitch, condition);
    }

    public static void playSound(World world, @Nullable PlayerEntity clientHandled, double x, double y, double z, 
            SoundEvent sound, SoundCategory category, float volume, float pitch, Predicate<PlayerEntity> condition) {
        if (!world.isClientSide()) {
            PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity(null, sound, category, volume, pitch);
            if (event.isCanceled() || event.getSound() == null) return;
            sound = event.getSound();
            category = event.getCategory();
            volume = event.getVolume();
            pitch = event.getPitch();
            NetworkUtil.broadcastWithCondition(((ServerWorld) world).getServer().getPlayerList().getPlayers(), clientHandled, 
                    x, y, z, volume > 1.0F ? (double)(16.0F * volume) : 16.0D, world.dimension(), 
                            new SPlaySoundEffectPacket(sound, category, x, y, z, volume, pitch), condition);
        }
        else if (clientHandled != null && condition.test(clientHandled)) {
            world.playSound(clientHandled, x, y, z, sound, category, volume, pitch);
        }
    }

    public static void playEitherSound(World world, @Nullable PlayerEntity clientHandled, double x, double y, double z, 
            Predicate<PlayerEntity> predicate, SoundEvent soundTrue, SoundEvent soundFalse, SoundCategory category, float volume, float pitch) {
        if (soundTrue != null) playSound(world, clientHandled, x, y, z, soundTrue, category, volume, pitch, predicate);
        if (soundFalse != null) playSound(world, clientHandled, x, y, z, soundFalse, category, volume, pitch, predicate.negate());
    }

    public static void playSound(World world, @Nullable PlayerEntity clientHandled, Entity entity, 
            SoundEvent sound, SoundCategory category, float volume, float pitch, Predicate<PlayerEntity> condition) {
        if (!world.isClientSide()) {
            PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity(entity, sound, category, volume, pitch);
            if (event.isCanceled() || event.getSound() == null) return;
            sound = event.getSound();
            category = event.getCategory();
            volume = event.getVolume();
            pitch = event.getPitch();
            NetworkUtil.broadcastWithCondition(((ServerWorld) world).getServer().getPlayerList().getPlayers(), clientHandled, 
                    entity.getX(), entity.getY(), entity.getZ(), volume > 1.0F ? (double)(16.0F * volume) : 16.0D, world.dimension(), 
                            new SSpawnMovingSoundEffectPacket(sound, category, entity, volume, pitch), condition);
        }
        else if (clientHandled != null && condition.test(clientHandled)) {
            world.playSound(clientHandled, entity, sound, category, volume, pitch);
        }
    }
    
    
    
    public static int doFractionTimes(Runnable action, double times) {
    	return doFractionTimes(action, times, null);
    }
    
    public static int doFractionTimes(Runnable action, double times, @Nullable Supplier<Boolean> breakCondition) {
    	if (times < 0) {
    		return 0;
    	}
    	int timesInt = MathUtil.fractionRandomInc(times);
    	for (int i = 0; i < timesInt; i++) {
    		if (breakCondition != null && breakCondition.get()) {
    			return i;
    		}
    		action.run();
    	}
    	return timesInt;
    }

    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<? super T> action, Runnable emptyAction) {
        if (optional.isPresent()) {
            action.accept(optional.get());
        } else {
            emptyAction.run();
        }
    }
    
    public static <T> boolean orElseFalse(LazyOptional<T> optional, Predicate<T> predicate) {
        return orElseFalse(optional.resolve(), predicate);
    }
    
    public static <T> boolean orElseFalse(Optional<T> optional, Predicate<T> predicate) {
        return optional.map(element -> predicate.test(element)).orElse(false);
    }
    
    public static <T> LinkedHashMap<Predicate<T>, List<T>> groupByPredicatesOrdered(Stream<T> elements, List<Predicate<T>> predicates, 
            @Nullable Predicate<T> commonCondition, boolean elementRepeats) {
        LinkedHashMap<Predicate<T>, List<T>> map = Util.make(new LinkedHashMap<>(), m -> {
            predicates.forEach(key -> m.put(key, new ArrayList<>()));
        });
        elements.forEach(element -> {
            if (commonCondition == null || commonCondition.test(element)) {
                for (Predicate<T> predicate : predicates) {
                    if (predicate.test(element)) {
                        map.get(predicate).add(element);
                        if (!elementRepeats) {
                            break;
                        }
                    }
                }
            }
        });
        return map;
    }
    
    
    public static Vector3d collide(Entity entity, Vector3d offsetVec) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox();
        ISelectionContext iselectioncontext = ISelectionContext.of(entity);
        VoxelShape voxelshape = entity.level.getWorldBorder().getCollisionShape();
        Stream<VoxelShape> stream = VoxelShapes.joinIsNotEmpty(voxelshape, VoxelShapes.create(axisalignedbb.deflate(1.0E-7D)), IBooleanFunction.AND) ? Stream.empty() : Stream.of(voxelshape);
        Stream<VoxelShape> stream1 = entity.level.getEntityCollisions(entity, axisalignedbb.expandTowards(offsetVec), (p_233561_0_) -> {
            return true;
        });
        ReuseableStream<VoxelShape> reuseablestream = new ReuseableStream<>(Stream.concat(stream1, stream));
        Vector3d vector3d = offsetVec.lengthSqr() == 0.0D ? offsetVec : Entity.collideBoundingBoxHeuristically(entity, offsetVec, axisalignedbb, entity.level, iselectioncontext, reuseablestream);
        boolean flag = offsetVec.x != vector3d.x;
        boolean flag1 = offsetVec.y != vector3d.y;
        boolean flag2 = offsetVec.z != vector3d.z;
        boolean flag3 = entity.isOnGround() || flag1 && offsetVec.y < 0.0D;
        if (entity.maxUpStep > 0.0F && flag3 && (flag || flag2)) {
            Vector3d vector3d1 = Entity.collideBoundingBoxHeuristically(entity, new Vector3d(offsetVec.x, (double)entity.maxUpStep, offsetVec.z), axisalignedbb, entity.level, iselectioncontext, reuseablestream);
            Vector3d vector3d2 = Entity.collideBoundingBoxHeuristically(entity, new Vector3d(0.0D, (double)entity.maxUpStep, 0.0D), axisalignedbb.expandTowards(offsetVec.x, 0.0D, offsetVec.z), entity.level, iselectioncontext, reuseablestream);
            if (vector3d2.y < (double)entity.maxUpStep) {
                Vector3d vector3d3 = Entity.collideBoundingBoxHeuristically(entity, new Vector3d(offsetVec.x, 0.0D, offsetVec.z), axisalignedbb.move(vector3d2), entity.level, iselectioncontext, reuseablestream).add(vector3d2);
                if (Entity.getHorizontalDistanceSqr(vector3d3) > Entity.getHorizontalDistanceSqr(vector3d1)) {
                    vector3d1 = vector3d3;
                }
            }

            if (Entity.getHorizontalDistanceSqr(vector3d1) > Entity.getHorizontalDistanceSqr(vector3d)) {
                return vector3d1.add(Entity.collideBoundingBoxHeuristically(entity, new Vector3d(0.0D, -vector3d1.y + offsetVec.y, 0.0D), axisalignedbb.move(vector3d1), entity.level, iselectioncontext, reuseablestream));
            }
        }

        return vector3d;
    }
}
