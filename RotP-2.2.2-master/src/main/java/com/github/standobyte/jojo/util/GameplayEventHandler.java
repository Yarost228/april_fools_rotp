package com.github.standobyte.jojo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.JojoModConfig.Common;
import com.github.standobyte.jojo.action.non_stand.HamonSendoWaveKick;
import com.github.standobyte.jojo.action.non_stand.VampirismFreeze;
import com.github.standobyte.jojo.action.stand.CrazyDiamondRestoreTerrain;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.effect.BoyIIManStandPartTakenEffect;
import com.github.standobyte.jojo.action.stand.effect.DriedBloodDrops;
import com.github.standobyte.jojo.advancements.ModCriteriaTriggers;
import com.github.standobyte.jojo.block.StoneMaskBlock;
import com.github.standobyte.jojo.block.WoodenCoffinBlock;
import com.github.standobyte.jojo.capability.chunk.ChunkCapProvider;
import com.github.standobyte.jojo.capability.entity.ClientPlayerUtilCapProvider;
import com.github.standobyte.jojo.capability.entity.EntityUtilCap;
import com.github.standobyte.jojo.capability.entity.EntityUtilCapProvider;
import com.github.standobyte.jojo.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.capability.entity.ProjectileHamonChargeCapProvider;
import com.github.standobyte.jojo.client.sound.ClientTickingSoundsHelper;
import com.github.standobyte.jojo.entity.SoulEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.CDBloodCutterEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.MRCrossfireHurricaneEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModBlocks;
import com.github.standobyte.jojo.init.ModEffects;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.init.ModPaintings;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.init.power.hamon.ModHamonSkills;
import com.github.standobyte.jojo.init.power.stand.ModStandActions;
import com.github.standobyte.jojo.init.power.stand.ModStandEffects;
import com.github.standobyte.jojo.init.power.stand.ModStands;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.item.StandDiscItem;
import com.github.standobyte.jojo.item.StoneMaskItem;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.BloodParticlesPacket;
import com.github.standobyte.jojo.network.packets.fromserver.ResolveEffectStartPacket;
import com.github.standobyte.jojo.potion.IApplicableEffect;
import com.github.standobyte.jojo.power.IPower;
import com.github.standobyte.jojo.power.IPower.PowerClassification;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonPowerType;
import com.github.standobyte.jojo.power.nonstand.type.vampirism.VampirismData;
import com.github.standobyte.jojo.power.nonstand.type.vampirism.VampirismPowerType;
import com.github.standobyte.jojo.power.stand.IStandPower;
import com.github.standobyte.jojo.power.stand.StandEffectsTracker;
import com.github.standobyte.jojo.power.stand.StandInstance;
import com.github.standobyte.jojo.power.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.stand.StandUtil;
import com.github.standobyte.jojo.power.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.stand.type.StandType;
import com.github.standobyte.jojo.tileentity.StoneMaskTileEntity;
import com.github.standobyte.jojo.util.damage.DamageUtil;
import com.github.standobyte.jojo.util.damage.IModdedDamageSource;
import com.github.standobyte.jojo.util.damage.IStandDamageSource;
import com.github.standobyte.jojo.util.damage.StandLinkDamageSource;
import com.github.standobyte.jojo.util.reflection.CommonReflection;
import com.github.standobyte.jojo.util.utils.JojoModUtil;
import com.github.standobyte.jojo.util.utils.MathUtil;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SRemoveEntityEffectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

//FIXME move all event handlers to their respective classes, leave the method links here
@EventBusSubscriber(modid = JojoMod.MOD_ID)
public class GameplayEventHandler {
	
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingTick(LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.level.isClientSide() && entity.invulnerableTime <= 10) {
            float sunDamage = getSunDamage(entity);
            if (    
                    sunDamage > 0
                    && DamageUtil.dealUltravioletDamage(entity, sunDamage, null, null, true)
                    && entity instanceof PlayerEntity) {
                EffectInstance weaknessEffect = entity.getEffect(ModEffects.VAMPIRE_SUN_WEAKNESS.get());
                if (weaknessEffect == null) {
                    entity.addEffect(new EffectInstance(ModEffects.VAMPIRE_SUN_WEAKNESS.get(), 60, 0, false, false, true));
                }
                else {
                    int difficulty = Math.max(entity.level.getDifficulty().getId(), 1);
                    int duration = weaknessEffect.getDuration() + 60 / difficulty;
                    int amplifier = duration / 60;
                    entity.addEffect(new EffectInstance(ModEffects.VAMPIRE_SUN_WEAKNESS.get(), duration, amplifier, false, false, true));
                }
            }
        }
        entity.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> {
            cap.tick();
        });
    }

    private static final int AFK_PARTICLE_SECONDS = 30;
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        PlayerEntity player = event.player;
        if (ModEffects.isStunned(player)) {
            player.setSprinting(false);
        }
        if (event.side == LogicalSide.SERVER) {
            player.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                cap.tick();
            });
            if (player.tickCount % 60 == 0 && !player.isInvisible() && player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                long timeNotActive = Util.getMillis() - serverPlayer.getLastActionTime();
                if (timeNotActive > AFK_PARTICLE_SECONDS * 1000 &&
                        serverPlayer.getCapability(PlayerUtilCapProvider.CAPABILITY).map(cap -> cap.getNoClientInputTimer() > AFK_PARTICLE_SECONDS * 20).orElse(true)) {
                    ((ServerWorld) player.level).sendParticles(ModParticles.MENACING.get(), player.getX(), player.getEyeY(), player.getZ(), 
                            0, MathHelper.cos(player.yRot * MathUtil.DEG_TO_RAD), 0.5D, MathHelper.sin(player.yRot * MathUtil.DEG_TO_RAD), 0.005D);
                    ModCriteriaTriggers.AFK.get().trigger(serverPlayer);
                }
            }
        }
        // FIXME ! (liquid walking) sound & sparks for tracking players
        // FIXME ! (liquid walking) double shift
        // FIXME ! (liquid walking) energy cost
        // FIXME ! (liquid walking) camera bobbing
        boolean liquidWalking = checkLiquidWalking(player);
        if (!liquidWalking) {
            player.getCapability(ClientPlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.isWalkingOnLiquid = liquidWalking);
        }
        
        INonStandPower.getNonStandPowerOptional(player).ifPresent(power -> {
            power.tick();
        });
        IStandPower.getStandPowerOptional(player).ifPresent(power -> {
            power.tick();
        }); 
    }
    
    private static boolean checkLiquidWalking(PlayerEntity player) {
        // FIXME !!! (liquid walking) fix not being able to move on shift (PlayerEntity#maybeBackOffFromEdge (989))
        // FIXME !!!! (liquid walking) double-shift
        if (/*!player.isShiftKeyDown() && */!player.abilities.flying && !player.isInWater()) {
            return INonStandPower.getNonStandPowerOptional(player).map(power -> {
                return power.getTypeSpecificData(ModHamon.HAMON.get()).map(hamon -> {
                    boolean waterWalking = hamon.isSkillLearned(ModHamonSkills.WATER_WALKING.get());
                    boolean anyLiquidWalking = hamon.isSkillLearned(ModHamonSkills.LAVA_WALKING.get());
                    if (anyLiquidWalking || waterWalking) {
                        BlockPos blockPos = new BlockPos(player.position().add(0, -0.3, 0));
                        FluidState fluidBelow = player.level.getBlockState(blockPos).getFluidState();
                        Fluid fluidType = fluidBelow.getType();
                        if (!fluidBelow.isEmpty() && 
                                !(fluidType.is(FluidTags.WATER) && player.isOnFire()) && 
                                (fluidType.is(FluidTags.WATER) || anyLiquidWalking)) {
                            player.setOnGround(true);
                            if (player.level.isClientSide()) {
                                if (player.isLocalPlayer()) {
//                                    InputHandler input = InputHandler.getInstance();
//                                    if (input.pressedDoubleShift) {
//                                        input.cancelingLiquidWalking = true;
//                                    }
//                                    if (input.cancelingLiquidWalking) {
//                                        return false;
//                                    }
                                    Vector3d deltaMovement = player.getDeltaMovement();
                                    if (player.isShiftKeyDown()) {
                                        deltaMovement = new Vector3d(deltaMovement.x, 0, deltaMovement.z);
                                    }
                                    else {
                                        deltaMovement = new Vector3d(deltaMovement.x, Math.max(deltaMovement.y, 0), deltaMovement.z);
                                    }
                                    player.setDeltaMovement(deltaMovement);
                                    player.fallDistance = 0;
                                }
                                
                                boolean doSound = player.getCapability(ClientPlayerUtilCapProvider.CAPABILITY).map(cap -> {
                                    if (!cap.isWalkingOnLiquid) {
                                        cap.isWalkingOnLiquid = true;
                                        ClientTickingSoundsHelper.playHamonSparksLoopSound(player, 
                                                entity -> !cap.isWalkingOnLiquid, 
                                                1.0F);
                                        return true;
                                    }
                                    return false;
                                }).orElse(true);
                                if (doSound) {
                                    HamonPowerType.createHamonSparkParticles(player.level, player, 
                                            player.getRandomX(0.5), player.getY(), player.getRandomZ(0.5), 0.05F);
                                }
                                else {
                                    HamonPowerType.createHamonSparkParticles(player.level, player, 
                                            player.getRandomX(0.5), player.getY(), player.getRandomZ(0.5), 0.1F, null);
                                }
                            }
                            else {
                                if (fluidType.is(FluidTags.LAVA) 
                                        && !player.fireImmune() && !EnchantmentHelper.hasFrostWalker(player)) {
                                    player.hurt(DamageSource.HOT_FLOOR, 1.0F);
                                }
                            }
                            return true;
                        }
                    }
                    return false;
                }).orElse(false);
            }).orElse(false);
        }
        return false;
    }

//    private static final float MAX_SUN_DAMAGE = 10;
//    private static final float MIN_SUN_DAMAGE = 2;
    private static float getSunDamage(LivingEntity entity) {
        if (entity.hasEffect(ModEffects.SUN_RESISTANCE.get())
                || !(entity instanceof PlayerEntity || JojoModConfig.getCommonConfigInstance(false).undeadMobsSunDamage.get())
                || entity.isSleeping() && entity.getSleepingPos().map(sleepingPos -> {
                    BlockState blockState = entity.level.getBlockState(sleepingPos);
                    return blockState.getBlock() instanceof WoodenCoffinBlock && blockState.getValue(WoodenCoffinBlock.CLOSED);
                }).orElse(false)) {
            return 0;
        }
        World world = entity.level;
        if (world.isDay()) {
            float brightness = entity.getBrightness();
            BlockPos blockPos = entity.getVehicle() instanceof BoatEntity ? 
                    (new BlockPos(entity.getX(), (double)Math.round(entity.getY(1.0)), entity.getZ())).above()
                    : new BlockPos(entity.getX(), (double)Math.round(entity.getY(1.0)), entity.getZ());
            if (brightness > 0.5F && world.canSeeSky(blockPos)) {
                return 4;
//                int time = (int) (world.getDayTime() % 24000L);
//                float damage = MAX_SUN_DAMAGE;
//                float diff = MAX_SUN_DAMAGE - MIN_SUN_DAMAGE;
//                
//                // sunrise
//                if (time > 23460) { 
//                    time -= 24000;
//                }
//                if (time <= 60) {
//                    damage -= diff * (1F - (float) (time + 540) / 600F);
//                }
//                
//                // sunset
//                else if (time > 11940 && time <= 12540) {
//                    damage -= diff * (float) (time - 11940) / 600F;
//                }
//                
//                return damage;
            }
        }
        return 0;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void replaceStrayArrow(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ArrowEntity) {
            ArrowEntity arrow = (ArrowEntity) event.getEntity();
            if (arrow.getOwner() instanceof StrayEntity) {
                arrow.setEffectsFromItem(new ItemStack(Items.ARROW));
                arrow.addEffect(new EffectInstance(ModEffects.FREEZE.get(), 300));
            }
        }
        
        else if (!event.getWorld().isClientSide()) {
            if (event.getEntity() instanceof ChickenEntity) {
//                List<EggEntity> eggList = event.getWorld().getEntitiesOfClass(EggEntity.class, event.getEntity().getBoundingBox());
//                if (!eggList.isEmpty()) {
//                    eggList.stream()
//                    .map(egg -> egg.getCapability(ProjectileHamonChargeCapProvider.CAPABILITY).resolve())
//                    .filter(chargeOptional -> chargeOptional.map(charge -> charge.hamonBaseDmg > 0).orElse(false))
//                    .findAny().ifPresent(chargeOptional -> {
//                        event.getEntity().getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> {
//                            ProjectileHamonChargeCap eggCharge = chargeOptional.get();
//                            Entity eggThrower = ((EggEntity) eggCharge.projectile).getOwner();
//                            LivingEntity throwerLiving = eggThrower instanceof LivingEntity ? (LivingEntity) eggThrower : null;
//                            Optional<HamonData> userHamon = throwerLiving != null ? INonStandPower.getNonStandPowerOptional(throwerLiving)
//                                    .map(power -> power.getTypeSpecificData(ModNonStandPowers.HAMON.get())).map(Optional::get)
//                                    : Optional.empty();
//                            cap.setHamonCharge(
//                                    eggCharge.hamonBaseDmg * userHamon.map(hamon -> hamon.getHamonDamageMultiplier() * hamon.getBloodstreamEfficiency()).orElse(1F), 
//                                    100 + userHamon.map(hamon -> MathHelper.floor((float) (1100 * hamon.getHamonStrengthLevel())
//                                            / (float) HamonData.MAX_STAT_LEVEL * hamon.getBloodstreamEfficiency() * hamon.getBloodstreamEfficiency())).orElse(0), throwerLiving, 0);
//                        });
//                    });
//                }
            }
        }
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            ((ServerWorld) event.world).getAllEntities().forEach(entity -> {
                entity.getCapability(ProjectileHamonChargeCapProvider.CAPABILITY).ifPresent(cap -> {
                    if (cap.hamonBaseDmg > 0 && entity.canUpdate()) {
                        HamonPowerType.createHamonSparkParticles(entity.level, null, 
                                entity.getX(), entity.getY(0.5), entity.getZ(), cap.getHamonDamage());
                    }
                });
            });

            ((ServerWorld) event.world).getChunkSource().chunkMap.getChunks().forEach(chunkHolder -> {
                Chunk chunk = chunkHolder.getTickingChunk();
                if (chunk != null) {
                    chunk.getCapability(ChunkCapProvider.CAPABILITY).ifPresent(cap -> cap.tick());
                }
            });
        }
    }
    
    @SubscribeEvent
    public static void onChunkLoad(ChunkWatchEvent.Watch event) {
        Chunk chunk = event.getWorld().getChunkSource().getChunk(event.getPos().x, event.getPos().z, false);
        if (chunk != null) {
            chunk.getCapability(ChunkCapProvider.CAPABILITY).ifPresent(cap -> cap.onChunkLoad(event.getPlayer()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void cancelStunnedPlayerInteraction(PlayerInteractEvent event) {
        if (event.isCancelable() && ModEffects.isStunned(event.getPlayer())) {
            event.setCanceled(true);
            event.setCancellationResult(ActionResultType.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemPickup(EntityItemPickupEvent event) {
        if (ModEffects.isStunned(event.getPlayer())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addEntityDrops(LivingDropsEvent event) {
    	Common config = JojoModConfig.getCommonConfigInstance(false);
    	if (config.dropStandDisc.get() && !config.keepStandOnDeath.get()) {
        	LivingEntity entity = event.getEntityLiving();
    		IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
    			if (power.hasPower()) {
    				ItemStack disc = StandDiscItem.withStand(new ItemStack(ModItems.STAND_DISC.get()), power.getStandInstance().get());
    				event.getDrops().add(new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), disc));
    			}
    		});
    	}
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide() && entity instanceof MobEntity) {
            MobEntity mob = (MobEntity) entity;
            if (entity.getClassification(false) == EntityClassification.MONSTER) {
                makeMobNeutralToVampirePlayers(mob);
            }
            else if (entity instanceof IronGolemEntity) {
                mob.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, PlayerEntity.class, 5, false, false, 
                        target -> target instanceof PlayerEntity && JojoModUtil.isPlayerUndead((PlayerEntity) target)));
            }
        }
//        else if (entity.getType() == EntityType.PAINTING) {
//            cutOutHands((PaintingEntity) event.getEntity());
//        }
    }

    private static void makeMobNeutralToVampirePlayers(MobEntity mob) {
        Predicate<LivingEntity> undeadPredicate = null;
        
        if (JojoModConfig.getCommonConfigInstance(false).vampiresAggroMobs.get()) {
            undeadPredicate = target -> 
            target instanceof PlayerEntity && !(
//                  JojoModUtil.isPlayerUndead((PlayerEntity) target) &&
                  INonStandPower.getNonStandPowerOptional(target).map(
                          power -> power.getTypeSpecificData(ModVampirism.VAMPIRISM.get())
                          .map(vampirism -> vampirism.getCuringStage() < 3).orElse(false)).orElse(false));
        }
        if (mob instanceof ZombieEntity) {
            Predicate<LivingEntity> limpBizkitPredicate = target -> target instanceof PlayerEntity && 
                    !IStandPower.getStandPowerOptional(target).map(power -> power.getType() == ModStandActions.STAND_LIMP_BIZKIT.get()).orElse(false);
            
            if (undeadPredicate == null) undeadPredicate = limpBizkitPredicate;
            else undeadPredicate = undeadPredicate.and(limpBizkitPredicate);
        }
        if (undeadPredicate == null) return;
        
        Set<PrioritizedGoal> goals = CommonReflection.getGoalsSet(mob.targetSelector);
        for (PrioritizedGoal prGoal : goals) {
            Goal goal = prGoal.getGoal();
            if (goal instanceof NearestAttackableTargetGoal) {
                NearestAttackableTargetGoal<?> targetGoal = (NearestAttackableTargetGoal<?>) goal;
                Class<? extends LivingEntity> targetClass = CommonReflection.getTargetClass(targetGoal);
                
                if (targetClass == PlayerEntity.class) {
                    EntityPredicate selector = CommonReflection.getTargetConditions(targetGoal);
                    if (selector != null) {
                        Predicate<LivingEntity> oldPredicate = CommonReflection.getTargetSelector(selector);
                            
                        CommonReflection.setTargetConditions(targetGoal, new EntityPredicate().range(CommonReflection.getTargetDistance(targetGoal)).selector(
                                oldPredicate != null ? oldPredicate.and(undeadPredicate) : undeadPredicate));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            VampirismData.onEnchantedGoldenAppleEaten(event.getEntityLiving());
        }
    }
    
    private static void cutOutHands(PaintingEntity painting) {
        if (!painting.level.isClientSide()) {
            boolean monaLisaFull = painting.motive == ModPaintings.MONA_LISA.get();
            boolean monaLisaHands = painting.motive == ModPaintings.MONA_LISA_HANDS.get();
            if (monaLisaFull || monaLisaHands) {
                List<LivingEntity> KQUsers = painting.level.getEntitiesOfClass(
                        LivingEntity.class, painting.getBoundingBox().expandTowards(painting.getLookAngle().scale(3)).inflate(1), 
                            entity -> IStandPower.getStandPowerOptional(entity).map(
                                    stand -> stand.hasPower() && stand.getType() == ModStandActions.STAND_KILLER_QUEEN.getStandType())
                            .orElse(false));
                if (!KQUsers.isEmpty()) {
                    if (monaLisaFull) {
                        painting.motive = ModPaintings.MONA_LISA_HANDS.get();
                        double x = painting.getX();
                        double z = painting.getZ();
                        if (x - (int) x != 0 && (int) (x + 0.04) != (int) x) {
                            z -= 1;
                        }
                        if (z - (int) z != 0 && (int) (z - 0.04) != (int) z) {
                            x -= 1;
                        }
                        painting.setPos(x, painting.getY(), z);
                    }
                }
                else if (monaLisaHands) {
                    painting.motive = PaintingType.KEBAB;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void cancelLivingHeal(LivingHealEvent event) {
        if (event.getEntityLiving().hasEffect(ModEffects.VAMPIRE_SUN_WEAKNESS.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHeal(LivingHealEvent event) {
        VampirismPowerType.consumeEnergyOnHeal(event);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void releaseStun(LivingConversionEvent.Post event) {
        if (event.getOutcome() instanceof MobEntity) {
            LivingEntity pre = event.getEntityLiving();
            MobEntity converted = (MobEntity) event.getOutcome();
            if (converted.isNoAi() && ModEffects.isStunned(pre) && !ModEffects.isStunned(converted)) {
                converted.setNoAi(false);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurtStart(LivingAttackEvent event) {
        DamageSource dmgSource = event.getSource();
        LivingEntity target = event.getEntityLiving();
        Entity attacker = dmgSource.getEntity();
        
        if (target.getType() == ModStandActions.STAND_GOLD_EXPERIENCE_REQUIEM.getEntityType()
                || IStandPower.getStandPowerOptional(target).map(power -> power.getType() == ModStandActions.STAND_GOLD_EXPERIENCE_REQUIEM.getStandType()).orElse(false)) {
            event.setCanceled(true);
            return;
        }
        
        if (attacker != null && attacker.is(dmgSource.getDirectEntity()) && attacker instanceof LivingEntity) {
            IStandPower.getStandPowerOptional((LivingEntity) attacker).ifPresent(attackerStand -> {
                IStandPower.getStandPowerOptional(target).ifPresent(boyIIManStand -> {
                    StandEffectsTracker standEffects = boyIIManStand.getContinuousEffects();
                    if (!standEffects.getEffects(effect -> {
                        if (effect.effectType == ModStandEffects.BOY_II_MAN_PART_TAKE.get() && attacker.is(effect.getTarget())) {
                            StandInstance partsTaken = ((BoyIIManStandPartTakenEffect) effect).getPartsTaken();
                            return partsTaken.getType() == attackerStand.getType() && partsTaken.hasPart(StandPart.ARMS);
                        }
                        return false;
                    }).isEmpty()) {
                        attacker.hurt(dmgSource, event.getAmount());
                        event.setCanceled(true);
                        return;
                    }
                });
            });
        }
        
        if (target.invulnerableTime > 0 && dmgSource instanceof IModdedDamageSource && 
                ((IModdedDamageSource) dmgSource).bypassInvulTicks()) {
            event.setCanceled(true);
            DamageUtil.hurtThroughInvulTicks(target, dmgSource, event.getAmount());
            return;
        }
        
        standBlockUserAttack(dmgSource, target, stand -> {
            if (!stand.isInvulnerableTo(dmgSource)) {
                stand.hurt(dmgSource, event.getAmount());
                event.setCanceled(true);
            };
        });
        
        HamonPowerType.cancelCactusDamage(event);
        if (VampirismFreeze.onUserAttacked(event)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public static void addResolveOnAttack(LivingAttackEvent event) {
        LivingEntity target = event.getEntityLiving();
        if (!target.level.isClientSide()) {
	        DamageSource damageSrc = event.getSource();
	        if (target.is(damageSrc.getEntity())) return;
	        float points = Math.min(event.getAmount(), target.getHealth());
	        
	        if (damageSrc instanceof IStandDamageSource) {
	            IStandDamageSource standDamageSrc = (IStandDamageSource) damageSrc;
	            IStandPower attackerStand = standDamageSrc.getStandPower();
	            StandUtil.addResolve(attackerStand, target, points);
	        }
	        
	        else if (damageSrc.getEntity() instanceof LivingEntity) {
	            IStandPower.getStandPowerOptional(StandUtil.getStandUser((LivingEntity) damageSrc.getEntity())).ifPresent(attackerStand -> {
	                if (attackerStand.isActive()) {
	                    StandUtil.addResolve(attackerStand, target, points * 0.5F);
	                }
	            });
	        }
        }
    }
    
    // TODO unsummoned stand auto-block
    @SuppressWarnings("unused")
    private double getAttackSpeed(DamageSource damageSrc) {
        Entity entity = damageSrc.getDirectEntity();
        if (entity == null) {
            return -1;
        }
        if (entity instanceof ProjectileEntity) {
            double velocity = entity.getDeltaMovement().length();
            //
        }
        if (entity instanceof LivingEntity) {
            LivingEntity entityLiving = (LivingEntity) entity;
            if (entity instanceof StandEntity) {
                StandEntity entityStand = ((StandEntity) entity);
                Optional<StandEntityAction> attack = entityStand.getCurrentTaskActionOptional();
                if (attack.isPresent()) {
                    //
                }
            }
            ModifiableAttributeInstance attackSpeedAttribute = entityLiving.getAttribute(Attributes.ATTACK_SPEED);
            if (attackSpeedAttribute != null) {
                double attackSpeed = ((LivingEntity) entity).getAttributeValue(Attributes.ATTACK_SPEED);
                //
            }
            //
        }
        //
        return 1234;
    }

    // FIXME ! (hamon 2) separate action
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void cancelLivingAttack(LivingAttackEvent event) {
        if (HamonSendoWaveKick.protectFromMeleeAttackInKick(event.getEntityLiving(), event.getSource(), event.getAmount())
                || HamonPowerType.snakeMuffler(event.getEntityLiving(), event.getSource(), event.getAmount())) 
            event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void inCaseOfExplosionCancel(LivingAttackEvent event) {
        if (event.isCanceled() && event.getSource().isExplosion()) {
            event.getEntityLiving().getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(util -> util.popLatestExplosionPos());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(LivingHurtEvent event) {
    	LivingEntity target = event.getEntityLiving();
    	if (!target.canUpdate() && target.getCapability(EntityUtilCapProvider.CAPABILITY)
    			.map(cap -> cap.wasStoppedInTime()).orElse(false)) {
    		event.setAmount(event.getAmount() * JojoModConfig.getCommonConfigInstance(false)
    				.timeStopDamageMultiplier.get().floatValue());
    	}
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void blockDamageWithStand(LivingHurtEvent event) {
        DamageSource dmgSource = event.getSource();
        LivingEntity target = event.getEntityLiving();
        if (dmgSource.isExplosion()) {
            target.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(util -> {
                Vector3d explosionPos = util.popLatestExplosionPos();
                if (explosionPos != null) {
                    StandEntity stand = getTargetStand(target);
                    if (stand != null && stand.isFollowingUser() && stand.isStandBlocking()) {
                        double standDurability = stand.getDurability();
                        if (standDurability > 4) {
                            double cos = explosionPos.subtract(target.position()).normalize().dot(stand.getLookAngle());
                            if (cos > 0) {
                                float multiplier = Math.max((1F - (float) cos), 4F / (float) standDurability);
                                event.setAmount(event.getAmount() * multiplier);
                            }
                        }
                    }
                }
            });
        }
        else {
            standBlockUserAttack(dmgSource, target, stand -> {
                if (stand.isInvulnerableTo(dmgSource)) {
                    double standDurability = stand.getDurability();
                    if (standDurability > 0) {
                        event.setAmount(Math.max(event.getAmount() - (float) standDurability / 2F, 0));
                    }
                }
            });
        }
    }

    @Nullable
    private static StandEntity getTargetStand(LivingEntity target) {
        return IStandPower.getStandPowerOptional(target).map(stand -> {
            return Optional.ofNullable(stand.getStandManifestation() instanceof StandEntity ? (StandEntity) stand.getStandManifestation() : null);
        }).orElse(Optional.empty()).orElse(null);
    }
    
    private static void standBlockUserAttack(DamageSource dmgSource, LivingEntity target, Consumer<StandEntity> standBehavior) {
        if (dmgSource.getDirectEntity() != null && dmgSource.getSourcePosition() != null) {
            StandEntity stand = getTargetStand(target);
            if (stand != null && stand.isFollowingUser() && stand.isStandBlocking()
                    && stand.canBlockDamage(dmgSource) && stand.canBlockOrParryFromAngle(dmgSource.getSourcePosition())) {
                standBehavior.accept(stand);
            }
        }
    }
    

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void resolveOnTakingDamage(LivingDamageEvent event) {
        IStandPower.getStandPowerOptional(event.getEntityLiving()).ifPresent(stand -> {
            if (stand.usesResolve()) {
                stand.getResolveCounter().onGettingAttacked(event.getSource(), event.getAmount(), event.getEntityLiving());
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void reduceDamageFromResolve(LivingDamageEvent event) {
        if (event.getSource() == DamageSource.OUT_OF_WORLD) {
            return;
        }
        float dmgReduction = IStandPower.getStandPowerOptional(event.getEntityLiving()).map(stand -> {
            return stand.getResolveDmgReduction();
        }).orElse(0F);
        if (dmgReduction > 0F) {
            event.setAmount(event.getAmount() * (1 - dmgReduction));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        bleed(event.getSource(), event.getAmount(), event.getEntityLiving());
        StandType.onHurtByStand(event.getSource(), event.getAmount(), event.getEntityLiving());
        
        for (PowerClassification powerClassification : PowerClassification.values()) {
            IPower.getPowerOptional(event.getEntityLiving(), powerClassification).ifPresent(power -> 
            power.onUserGettingAttacked(event.getSource(), event.getAmount()));
        }
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void prepareToReduceKnockback(LivingDamageEvent event) {
        float knockbackReduction = DamageUtil.knockbackReduction(event.getSource());
        if (knockbackReduction >= 0 && knockbackReduction < 1) {
            event.getEntityLiving().getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(util -> {
                util.setFutureKnockbackFactor(knockbackReduction);
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityKnockback(LivingKnockBackEvent event) {
        event.getEntityLiving().getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(util -> {
            if (util.shouldReduceKnockback()) {
                float factor = util.getKnockbackFactorOneTime();
                event.setStrength(event.getStrength() * factor);
            }
        });
    }

    private static void bleed(DamageSource dmgSource, float dmgAmount, LivingEntity target) {
        if (dmgSource instanceof StandLinkDamageSource) {
            dmgSource = ((StandLinkDamageSource) dmgSource).getOriginalDamageSource();
        }
        World world = target.level;
        if (world.isClientSide()
                || dmgAmount < 0.98F
                || dmgSource.isBypassArmor() && dmgSource != DamageSource.FALL
                || dmgSource.isFire()
                || dmgSource.isMagic()
                || dmgSource.isBypassMagic()
                || dmgSource.getMsgId().startsWith(DamageUtil.PILLAR_MAN_ABSORPTION.getMsgId())
                || !JojoModUtil.canBleed(target)) return;

        
        IStandPower.getStandPowerOptional(target).ifPresent(power -> {
            if (ModStandActions.CRAZY_DIAMOND_BLOOD_CUTTER.get().isUnlocked(power)) {
                power.setCooldownTimer(ModStandActions.CRAZY_DIAMOND_BLOOD_CUTTER.get(), 0);
            }
        });
        
        splashBlood(world, target.getBoundingBox().getCenter(), 2, dmgAmount, Optional.of(target));
    }
    
    public static boolean splashBlood(World world, Vector3d splashPos, double radius, float bleedAmount, Optional<LivingEntity> ownerEntity) {
        if (world.isClientSide()) {
            return false;
        }

        AxisAlignedBB aabb = new AxisAlignedBB(splashPos.subtract(radius, radius, radius), splashPos.add(radius, radius, radius));
        List<Vector3d> particlePos = new ArrayList<>();
        List<LivingEntity> entitiesAround = world.getEntitiesOfClass(LivingEntity.class, aabb, 
                EntityPredicates.ENTITY_STILL_ALIVE.and(EntityPredicates.NO_SPECTATORS)
                .and(entity -> {
                    return world.clip(new RayTraceContext(splashPos, entity.getBoundingBox().getCenter(), 
                          RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
                          .getType() == RayTraceResult.Type.MISS;
                }));
        for (LivingEntity entity : entitiesAround) {
            if (dropBloodOnEntity(ownerEntity, entity, bleedAmount)) {
                particlePos.add(entity.getEyePosition(1.0F));
            }
        }

        BlockPos blockPos = new BlockPos(splashPos);
        BlockPos.betweenClosedStream(blockPos.offset(-radius, -radius, -radius), blockPos.offset(radius, radius, radius))
        .filter(pos -> world.getBlockState(pos).getBlock() == ModBlocks.STONE_MASK.get())
        .forEach(pos -> {
            BlockState blockState = world.getBlockState(pos);
            world.playSound(null, pos, ModSounds.STONE_MASK_ACTIVATION.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            switch (blockState.getValue(HorizontalFaceBlock.FACE)) {
            case FLOOR:
                TileEntity tileEntity = world.getBlockEntity(pos);
                if (tileEntity instanceof StoneMaskTileEntity) {
                    ((StoneMaskTileEntity) tileEntity).activate();
                }
                particlePos.add(Vector3d.atBottomCenterOf(pos));
                break;
            default:
                Block.popResource(world, pos, StoneMaskBlock.getItemFromBlock(world, pos, blockState));
                world.removeBlock(pos, false);
                particlePos.add(Vector3d.atCenterOf(pos));
                break;
            }
        });

        if (!particlePos.isEmpty()) {
            int count = Math.min((int) (bleedAmount * 5), 50);
            particlePos.forEach(posTo -> {
                PacketManager.sendToTrackingChunk(new BloodParticlesPacket(splashPos, posTo, count, ownerEntity.map(Entity::getId).orElse(-1)), world.getChunkAt(blockPos));
            });
        }
        
        return !particlePos.isEmpty();
    }
    
    private static boolean dropBloodOnEntity(Optional<LivingEntity> bleedingEntity, LivingEntity nearbyEntity, float bleedAmount) {
        boolean dropped = false;
        
        ItemStack headArmor = nearbyEntity.getItemBySlot(EquipmentSlotType.HEAD);
        if (headArmor.getItem() instanceof StoneMaskItem && applyStoneMask(nearbyEntity, headArmor)) {
            dropped = true;
        }

        dropped |= JojoModUtil.orElseFalse(bleedingEntity, entity -> {
            return nearbyEntity.getRandom().nextFloat() < bleedAmount / 5 && 
                    JojoModUtil.orElseFalse(IStandPower.getStandPowerOptional(entity), (IStandPower power) -> {
                        if (ModStandActions.CRAZY_DIAMOND_BLOOD_CUTTER.get().isUnlocked(power) && CDBloodCutterEntity.canHaveBloodDropsOn(nearbyEntity, power)) {
                            DriedBloodDrops bloodDrops = power.getContinuousEffects().getOrCreateEffect(ModStandEffects.DRIED_BLOOD_DROPS.get(), nearbyEntity);
                            return bloodDrops.tickCount > 0;
                        }
                        return false;
                    });
        });
        
        return dropped;
    }

    public static boolean applyStoneMask(LivingEntity entity, ItemStack headStack) {
        if (entity.level.getDifficulty() == Difficulty.PEACEFUL) {
            if (entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) entity).displayClientMessage(
                        new TranslationTextComponent("jojo.chat.message.stone_mask_peaceful"), true);
            }
            return false;
        }
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            VampirismPowerType vampirism = ModVampirism.VAMPIRISM.get();
            return INonStandPower.getNonStandPowerOptional(player).map(power -> {
                if (power.getTypeSpecificData(vampirism).map(vamp -> !vamp.isVampireAtFullPower()).orElse(false) || power.givePower(vampirism)) {
                    entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.STONE_MASK_ACTIVATION_ENTITY.get(), entity.getSoundSource(), 1.0F, 1.0F);
                    power.getTypeSpecificData(vampirism).get().setVampireFullPower(true);
                    StoneMaskItem.setActivatedArmorTexture(headStack); // TODO light beams on stone mask activation
                    headStack.hurtAndBreak(1, entity, stack -> {});
                    return true;
                }
                return false;
            }).orElse(false);
        }
        return false;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionApply(PotionApplicableEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Effect effect = event.getPotionEffect().getEffect();
        if ((effect == Effects.POISON || effect == Effects.HUNGER || effect == Effects.REGENERATION)
                && entity instanceof PlayerEntity && JojoModUtil.isPlayerUndead((PlayerEntity) entity)) {
            event.setResult(Result.DENY);
        }
        else if (effect instanceof IApplicableEffect && !((IApplicableEffect) effect).isApplicable(entity)) {
            event.setResult(Result.DENY);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionAdded(PotionAddedEvent event) {
        EntityStandType.giveEffectSharedWithStand(event.getEntityLiving(), event.getPotionEffect());
        
        Entity entity = event.getEntity();
        EffectInstance effectInstance = event.getPotionEffect();
        if (!entity.level.isClientSide()) {
            if (ModEffects.isEffectTracked(effectInstance.getEffect())) {
                ((ServerChunkProvider) entity.getCommandSenderWorld().getChunkSource()).broadcast(entity, 
                        new SPlayEntityEffectPacket(entity.getId(), effectInstance));
            }
            if (effectInstance.getEffect() == ModEffects.RESOLVE.get() && entity instanceof ServerPlayerEntity) {
                PacketManager.sendToClient(new ResolveEffectStartPacket(effectInstance.getAmplifier()), (ServerPlayerEntity) entity);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPotionRemoved(PotionRemoveEvent event) {
        VampirismPowerType.cancelVampiricEffectRemoval(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void trackedPotionRemoved(PotionRemoveEvent event) {
        EntityStandType.removeEffectSharedWithStand(event.getEntityLiving(), event.getPotion());
        
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide() && event.getPotionEffect() != null && ModEffects.isEffectTracked(event.getPotionEffect().getEffect())) {
            ((ServerChunkProvider) entity.getCommandSenderWorld().getChunkSource()).broadcast(entity, 
                    new SRemoveEntityEffectPacket(entity.getId(), event.getPotion()));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void trackedPotionExpired(PotionExpiryEvent event) {
        EntityStandType.removeEffectSharedWithStand(event.getEntityLiving(), event.getPotionEffect().getEffect());
        
        Entity entity = event.getEntity();
        if (!entity.level.isClientSide() && ModEffects.isEffectTracked(event.getPotionEffect().getEffect())) {
            ((ServerChunkProvider) entity.getCommandSenderWorld().getChunkSource()).broadcast(entity, 
                    new SRemoveEntityEffectPacket(entity.getId(), event.getPotionEffect().getEffect()));
        }
    }
    
    @SubscribeEvent
    public static void syncTrackedEffects(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof LivingEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            LivingEntity tracked = (LivingEntity) event.getTarget();
            for (Map.Entry<Effect, EffectInstance> effectEntry : tracked.getActiveEffectsMap().entrySet()) {
                if (ModEffects.isEffectTracked(effectEntry.getKey())) {
                    player.connection.send(new SPlayEntityEffectPacket(tracked.getId(), effectEntry.getValue()));
                }
            }
        }
    }
    
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onPlayerAttack(AttackEntityEvent event) {
//        if (event.getTarget() instanceof LivingEntity) {
//            INonStandPower.getNonStandPowerOptional(event.getPlayer()).ifPresent(power -> {
//                if (power.getHeldAction() == null) {
//                    power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
//                        HamonPowerType.overdriveAttack(event.getPlayer(), (LivingEntity) event.getTarget(), power, hamon);
//                    });
//                }
//            });
//        }
//    }
//    
//    @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
//    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
//        if (event.getCancellationResult() == ActionResultType.PASS && event.getHand() == Hand.MAIN_HAND && !event.getPlayer().isShiftKeyDown()) {
//            Entity target = event.getTarget();
//            if (target instanceof PlayerEntity) {
//                PlayerEntity targetPlayer = (PlayerEntity) target;
//                INonStandPower targetPower = INonStandPower.getNonStandPowerOptional(targetPlayer).orElse(null);
//                INonStandPower playerPower = INonStandPower.getNonStandPowerOptional(event.getPlayer()).orElse(null);
//                if (targetPower != null && playerPower != null && 
//                        targetPower.getType() == ModHamon.HAMON.get()
//                        && (!playerPower.hasPower() || playerPower.getType().isReplaceableWith(ModHamon.HAMON.get()))) {
//                    HamonPowerType.interactWithHamonTeacher(target.level, event.getPlayer(), targetPlayer, 
//                            targetPower.getTypeSpecificData(ModHamon.HAMON.get()).get());
//                    event.setCanceled(true);
//                    event.setCancellationResult(ActionResultType.sidedSuccess(target.level.isClientSide));
//                }
//                else {
//                    playerPower.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
//                        hamon.interactWithNewLearner(targetPlayer);
//                        event.setCanceled(true);
//                        event.setCancellationResult(ActionResultType.sidedSuccess(target.level.isClientSide));
//                    });
//                }
//            }
//        }
//    }
    
//    // FIXME ! (hamon 2) separate action
//    @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
//    public static void tripwireInteract(PlayerInteractEvent.RightClickBlock event) {
//        if (event.getHand() == Hand.MAIN_HAND && event.getUseBlock() != Event.Result.DENY) {
//            PlayerEntity player = event.getPlayer();
//            if (!player.isSpectator() && player.getMainHandItem().isEmpty()) {
//                World world = player.level;
//                BlockPos pos = event.getHitVec().getBlockPos();
//                BlockState blockState = world.getBlockState(pos);
//                if (blockState.getBlock() == Blocks.TRIPWIRE) {
//                    INonStandPower.getNonStandPowerOptional(event.getPlayer()).ifPresent(power -> {
//                        power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
//                            if (hamon.isSkillLearned(ModHamonSkills.ROPE_TRAP.get())) {
//                                event.setCanceled(true);
//                                event.setCancellationResult(ActionResultType.SUCCESS);
//                                if (!world.isClientSide()) {
//                                    HamonPowerType.ropeTrap(player, pos, blockState, world, power, hamon);
//                                }
//                            }
//                        });
//                    });
//                }
//            }
//        }
//    }
    
    private static final int LIT_TICKS = 12000;
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void furnaceInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() == Hand.MAIN_HAND && event.getUseBlock() != Event.Result.DENY) {
            PlayerEntity player = event.getPlayer();
            if (!player.isSpectator()) {
                World world = player.level;
                BlockPos pos = event.getHitVec().getBlockPos();
                BlockState blockState = world.getBlockState(pos);
                if (blockState.getBlock() instanceof AbstractFurnaceBlock) {
                    IStandPower.getStandPowerOptional(event.getPlayer()).ifPresent(power -> {
                        if (power.isActive() && power.getType() == ModStands.MAGICIANS_RED.getStandType()) {
                            TileEntity tileEntity = world.getBlockEntity(pos);
                            if (tileEntity instanceof AbstractFurnaceTileEntity) {
                                AbstractFurnaceTileEntity furnace = (AbstractFurnaceTileEntity) tileEntity;
                                int timeLeft = CommonReflection.getFurnaceLitTime(furnace);
                                if (timeLeft < LIT_TICKS) {
                                    CommonReflection.setFurnaceLitTime(furnace, LIT_TICKS);
                                    CommonReflection.setFurnaceLitDuration(furnace, LIT_TICKS);
                                    StandEntity magiciansRed = (StandEntity) power.getStandManifestation();
                                    magiciansRed.playSound(ModSounds.MAGICIANS_RED_FIRE_BLAST.get(), 1.0F, 1.0F, player);
                                    world.setBlock(pos, blockState.setValue(AbstractFurnaceBlock.LIT, true), 3);
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerLogout(PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        IStandPower.getStandPowerOptional(player).ifPresent(stand -> {
            stand.getContinuousEffects().onStandUserLogout((ServerPlayerEntity) player);
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntityLiving();
        DamageSource dmgSource = event.getSource();
        if (!dead.level.isClientSide()) {
            HamonPowerType.hamonPerksOnDeath(dead);
            Entity killer = dmgSource.getEntity();
            if (killer instanceof StandEntity) {
                StandEntity killerStand = (StandEntity) killer;
                if (killerStand.getUser() != null) {
                    killer = killerStand.getUser();
                }
            }
            if (!dead.is(killer)) {
                if (killer instanceof ServerPlayerEntity) {
                    ModCriteriaTriggers.PLAYER_KILLED_ENTITY.get().trigger((ServerPlayerEntity) killer, dead, dmgSource);
                }
                if (dead instanceof ServerPlayerEntity && killer != null) {
                    ModCriteriaTriggers.ENTITY_KILLED_PLAYER.get().trigger((ServerPlayerEntity) dead, killer, dmgSource);
                }
            }
            LazyOptional<IStandPower> standOptional = IStandPower.getStandPowerOptional(dead);
            standOptional.ifPresent(stand -> {
                stand.getContinuousEffects().onStandUserDeath(dead);
                summonSoul(stand, dead, dmgSource);
            });
        }
    }
    
    private static void summonSoul(IStandPower stand, LivingEntity user, DamageSource dmgSource) {
        int ticks = getSoulAscensionTicks(user, stand);
        
        if (ticks > 0) {
            boolean resolveCanLvlUp = user.level.getLevelData().isHardcore()
                    || !JojoModConfig.getCommonConfigInstance(user.level.isClientSide()).keepStandOnDeath.get();
            
            EntityUtilCap.queueOnTimeResume(user, () -> {
                if (user instanceof ServerPlayerEntity) {
                    ModCriteriaTriggers.SOUL_ASCENSION.get().trigger((ServerPlayerEntity) user, stand, ticks);
                }
                SoulEntity soulEntity = new SoulEntity(user.level, user, ticks, resolveCanLvlUp);
                user.level.addFreshEntity(soulEntity);
            });
        }
    }
    
    public static int getSoulAscensionTicks(LivingEntity user, IStandPower stand) {
        if (    !stand.usesResolve() || 
                stand.getResolveLevel() == 0 ||
                !JojoModConfig.getCommonConfigInstance(user.level.isClientSide()).soulAscension.get() || 
                JojoModUtil.isUndead(user) || 
                user instanceof PlayerEntity && user.level.getGameRules().getBoolean(GameRules.RULE_DO_IMMEDIATE_RESPAWN)) {
            return 0;
        }

        float resolveRatio = user.hasEffect(ModEffects.RESOLVE.get()) ? 1 : stand.getResolveRatio();
        int ticks = (int) (60 * (stand.getResolveLevel() + resolveRatio));
        if (user.level.getLevelData().isHardcore()) {
            ticks += ticks / 2;
        }
        
        return ticks;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void handleCheatDeath(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            cheatDeath(event);
        }
    }

    private static void cheatDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntityLiving();
        if (dead.hasEffect(ModEffects.CHEAT_DEATH.get())) {
            event.setCanceled(true);
            dead.setHealth(dead.getMaxHealth() / 2F);
            dead.removeEffect(ModEffects.CHEAT_DEATH.get());
            dead.clearFire();
            ((ServerWorld) dead.level).sendParticles(ParticleTypes.POOF, dead.getX(), dead.getY(), dead.getZ(), 
                    20, (double) dead.getBbWidth() * 2D - 1D, (double) dead.getBbHeight(), (double) dead.getBbWidth() * 2D - 1D, 0.02D);
            dead.addEffect(new EffectInstance(Effects.INVISIBILITY, 200, 0, false, false, true));
            chorusFruitTeleport(dead);
            dead.level.getEntitiesOfClass(MobEntity.class, dead.getBoundingBox().inflate(8), 
                    mob -> mob.getTarget() == dead).forEach(mob -> mob.setTarget(null));
            INonStandPower.getNonStandPowerOptional(dead).ifPresent(power -> {
                power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
                    if (hamon.characterIs(ModHamonSkills.CHARACTER_JOSEPH.get())) {
                        if (dead instanceof ServerPlayerEntity) {
                            ServerPlayerEntity joseph = (ServerPlayerEntity) dead;
                            sendMemeDeathMessage(joseph, event.getSource().getLocalizedDeathMessage(dead));
                            sendSoundToOnePlayer(joseph, ModSounds.JOSEPH_GIGGLE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                        }
                    }
                });
            });
        }
    }

    private static void chorusFruitTeleport(LivingEntity entity) {
        Random random = entity.getRandom();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        for (int i = 0; i < 16; ++i) {
            double xRandom = x + (random.nextDouble() - 0.5D) * 16.0D;
            double yRandom = MathHelper.clamp(y + (double) (random.nextInt(16) - 8), 0.0D, (double)(entity.level.getHeight() - 1));
            double zRandom = z + (random.nextDouble() - 0.5D) * 16.0D;
            if (entity.isPassenger()) {
                entity.stopRiding();
            }
            if (entity.randomTeleport(xRandom, yRandom, zRandom, false)) {
                break;
            }
        }
    }

    private static void sendMemeDeathMessage(ServerPlayerEntity player, ITextComponent deathMessage) {
        if (player.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES)) {
            Team team = player.getTeam();
            if (team != null && team.getDeathMessageVisibility() != Team.Visible.ALWAYS) {
                if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OTHER_TEAMS) {
                    player.server.getPlayerList().broadcastToTeam(player, deathMessage);
                }
                else if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OWN_TEAM) {
                    player.server.getPlayerList().broadcastToAllExceptTeam(player, deathMessage);
                }
            } else {
                player.server.getPlayerList().broadcastMessage(deathMessage, ChatType.CHAT, player.getUUID());
            }
        }
    }

    private static void sendSoundToOnePlayer(ServerPlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity(player, sound, category, volume, pitch);
        if (event.isCanceled() || event.getSound() == null) return;
        sound = event.getSound();
        category = event.getCategory();
        volume = event.getVolume();
        player.connection.send(new SPlaySoundEffectPacket(sound, category, player.getX(), player.getY(), player.getZ(), volume, pitch));
    }
    
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onProjectileShot(EntityJoinWorldEvent event) {
//        Entity entity = event.getEntity();
//        if (!entity.level.isClientSide()) {
//            entity.getCapability(ProjectileHamonChargeCapProvider.CAPABILITY).ifPresent(projCap -> {
//                if (entity instanceof ProjectileEntity) {
//                    ProjectileEntity projectile = (ProjectileEntity) entity;
//                    Entity shooter = projectile.getOwner();
//                    if (shooter instanceof LivingEntity) {
//                        INonStandPower.getNonStandPowerOptional((LivingEntity) shooter).ifPresent(power -> {
//                            power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
//                                float energyCost = -1;
//                                AbstractHamonSkill requiredSkill;
//                                float hamonBaseDmg = 0;
//                                int maxChargeTicks = 0;
//                                boolean water = false;
//                                if (projectile instanceof AbstractArrowEntity && !alreadyHasHamon(projectile)) {
//                                    requiredSkill = ModHamonSkills.ARROW_INFUSION.get();
//                                    energyCost = 1000;
//                                    hamonBaseDmg = 0.25F;
//                                    maxChargeTicks = 10;
//                                    water = projectile.getType() == EntityType.ARROW && ((ArrowEntity) projectile).getColor() != -1;
//                                }
//                                else {
//                                    EntityType<?> type = projectile.getType();
//                                    requiredSkill = ModHamonSkills.THROWABLES_INFUSION.get();
//                                    if (type == EntityType.SNOWBALL) {
//                                        energyCost = 600;
//                                        hamonBaseDmg = 0.125F;
//                                        maxChargeTicks = 25;
//                                        water = true;
//                                    }
//                                    else if (type == EntityType.EGG) {
//                                        energyCost = 600;
//                                        hamonBaseDmg = 0.125F;
//                                        maxChargeTicks = Integer.MAX_VALUE;
//                                    }
//                                    else if (type == EntityType.POTION) {
//                                        energyCost = 800;
//                                        hamonBaseDmg = 0.15F; 
//                                        maxChargeTicks = 20;
//                                        water = true;
//                                    }
//                                }
//                                if (energyCost > -1 && hamon.isSkillLearned(requiredSkill) && power.consumeEnergy(energyCost)) {
//                                    projCap.hamonBaseDmg = hamonBaseDmg;
//                                    projCap.maxChargeTicks = maxChargeTicks;
//                                    projCap.water = water;
//                                    projCap.spentEnergy = energyCost;
//                                }
//                            });
//                        });
//                    }
//                }
//            });
//        }
//    }
//    
//    public static boolean projectileCanBeChargedWithHamon(Entity entity) {
//        return entity instanceof AbstractArrowEntity && !alreadyHasHamon(entity) || 
//                entity.getType() == EntityType.SNOWBALL || entity.getType() == EntityType.EGG || entity.getType() == EntityType.POTION;
//    }
//    
//    private static boolean alreadyHasHamon(Entity projectile) {
//        return projectile.getType() == ModEntityTypes.CLACKERS.get();
//    }
    
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onProjectileHit(ProjectileImpactEvent event) {
//        RayTraceResult rayTrace = event.getRayTraceResult();
//        if (rayTrace.getType() == RayTraceResult.Type.ENTITY) {
//            Entity entity = event.getEntity();
//            entity.getCapability(ProjectileHamonChargeCapProvider.CAPABILITY).ifPresent(cap -> {
//                if (cap.hamonBaseDmg > 0 && entity instanceof ProjectileEntity) {
//                    ProjectileEntity projectile = (ProjectileEntity) entity;
//                    addHamonDamageToProjectile((EntityRayTraceResult) rayTrace, 
//                            projectile.getOwner(), projectile, cap.getHamonDamage(), cap.water, cap.spentEnergy);
//                }
//            });
//        }
//    }
//    
//    private static void addHamonDamageToProjectile(EntityRayTraceResult rayTrace, Entity thrower, Entity thrown, 
//            float damage, boolean waterProjectile, float spentEnergy) {
//        if (thrown.level.isClientSide() || damage <= 0 || !(thrower instanceof LivingEntity)) {
//            return;
//        }
//        INonStandPower.getNonStandPowerOptional((LivingEntity) thrower).ifPresent(power -> {
//            power.getTypeSpecificData(ModHamon.HAMON.get()).ifPresent(hamon -> {
//                float dmgCheckWater = damage;
////                if (waterProjectile && hamon.isSkillLearned(HamonSkill.TURQUOISE_BLUE_OVERDRIVE)) {
////                    dmgCheckWater *= 1.25;
////                }
//                DamageUtil.dealHamonDamage(rayTrace.getEntity(), dmgCheckWater, thrown, thrower);
//                hamon.hamonPointsFromAction(HamonStat.STRENGTH, spentEnergy);
//            });
//        });
//    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getDistance() > 3) {
            LivingEntity entity = event.getEntityLiving();
            float leapStrength = Math.max(
                    IStandPower.getStandPowerOptional(entity).map(power -> 
                    power.hasPower() && power.isLeapUnlocked() ? power.leapStrength() : 0).orElse(0F), 
                    INonStandPower.getNonStandPowerOptional(entity).map(power -> 
                    power.hasPower() && power.isLeapUnlocked() ? power.leapStrength() : 0).orElse(0F));
            if (leapStrength > 0) {
                event.setDistance(Math.max(event.getDistance() - (leapStrength + 5) * 3, 0));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onChatMessage(ServerChatEvent event) {
        List<ServerPlayerEntity> josephTechniqueUsers = JojoModUtil.entitiesAround(ServerPlayerEntity.class, event.getPlayer(), 8, false, 
                pl -> INonStandPower.getNonStandPowerOptional(pl).map(power -> 
                power.getTypeSpecificData(ModHamon.HAMON.get()).map(hamon -> 
                hamon.characterIs(ModHamonSkills.CHARACTER_JOSEPH.get())).orElse(false)).orElse(false));
        for (ServerPlayerEntity joseph : josephTechniqueUsers) {
            if (joseph.getChatVisibility() != ChatVisibility.HIDDEN && joseph.getRandom().nextFloat() < 0.05F) {
                String tlKey = "jojo.chat.joseph.next_line." + (joseph.getRandom().nextInt(3) + 1);
                ITextComponent message = new TranslationTextComponent("chat.type.text", joseph.getDisplayName(), 
                        new TranslationTextComponent(tlKey, event.getMessage()));
                LanguageMap map = LanguageMap.getInstance();
                if (map != null) {
                    message = ForgeHooks.onServerChatEvent(joseph.connection, String.format(map.getOrDefault(tlKey), event.getMessage()), message);
                }
                if (message != null) {
                    JojoModUtil.sayVoiceLine(joseph, ModSounds.JOSEPH_GIGGLE.get());
                    joseph.server.getPlayerList().broadcastMessage(message, ChatType.CHAT, joseph.getUUID());
                }
            }
        }
        
        IStandPower.getStandPowerOptional(event.getPlayer()).ifPresent(stand -> stand.getResolveCounter().onChatMessage(event.getMessage()));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void explosionFilterEntities(ExplosionEvent.Detonate event) {
        Explosion explosion = event.getExplosion();
        
        if (explosion.getExploder() instanceof MRCrossfireHurricaneEntity) {
            ((MRCrossfireHurricaneEntity) explosion.getExploder())
            .explosionFilterEntities(event.getAffectedEntities());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        Explosion explosion = event.getExplosion();
        
        event.getAffectedEntities().forEach(entity -> {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(util -> {
                    util.setLatestExplosionPos(explosion.getPosition());
                });
            }
        });
        
        if (explosion.getExploder() instanceof MRCrossfireHurricaneEntity) {
            ((MRCrossfireHurricaneEntity) explosion.getExploder())
            .onExplode(event.getAffectedEntities(), event.getAffectedBlocks());
        }
    }
    
    @SubscribeEvent
    public static void onWakeUp(PlayerWakeUpEvent event) {
        if (!event.wakeImmediately() && !event.updateWorld()) {
            IStandPower.getStandPowerOptional(event.getPlayer()).ifPresent(stand -> {
                if (stand.hasPower()) {
                    stand.setStamina(stand.getMaxStamina());
                }
            });
        }
        VampirismData.finishCuringOnWakingUp(event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isClientSide() && event.getPlayer().abilities.instabuild) {
            CrazyDiamondRestoreTerrain.rememberBrokenBlock((World) event.getWorld(), 
                    event.getPos(), event.getState(), Optional.ofNullable(event.getWorld().getBlockEntity(event.getPos())), 
                    Collections.emptyList());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (event.getNewGameMode() == GameType.CREATIVE) {
            INonStandPower.getNonStandPowerOptional(event.getPlayer()).ifPresent(power -> power.resetCooldowns());
            IStandPower.getStandPowerOptional(event.getPlayer()).ifPresent(stand -> stand.resetCooldowns());
        }
    }
}
