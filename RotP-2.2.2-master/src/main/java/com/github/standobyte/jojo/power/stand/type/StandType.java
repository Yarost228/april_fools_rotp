package com.github.standobyte.jojo.power.stand.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.advancements.ModCriteriaTriggers;
import com.github.standobyte.jojo.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.init.power.stand.ModStandActions;
import com.github.standobyte.jojo.power.IPowerType;
import com.github.standobyte.jojo.power.stand.IStandManifestation;
import com.github.standobyte.jojo.power.stand.IStandPower;
import com.github.standobyte.jojo.power.stand.stats.StandStats;
import com.github.standobyte.jojo.util.OstSoundList;
import com.github.standobyte.jojo.util.damage.IStandDamageSource;
import com.github.standobyte.jojo.util.data.StandStatsManager;
import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class StandType<T extends StandStats> extends ForgeRegistryEntry<StandType<?>> implements IPowerType<IStandPower, StandType<?>> {
    private final int color;
    private final StandAction[] attacks;
    private final StandAction[] abilities;
    private final StandAction defaultQuickAccess;
    private String translationKey;
    private ResourceLocation iconTexture;
    
    private final ITextComponent partName;
    private final T defaultStats;
    private final Class<T> statsClass;
    private final boolean canPlayerGet;
    private final Supplier<SoundEvent> summonShoutSupplier;
    private final OstSoundList ostSupplier;
    private final Map<Integer, List<ItemStack>> resolveLevelItems;
    
    public StandType(int color, ITextComponent partName, 
            StandAction[] attacks, StandAction[] abilities, StandAction defaultQuickAccess, 
            Class<T> statsClass, T defaultStats, @Nullable StandTypeOptionals additions) {
        this.color = color;
        this.partName = partName;
        this.attacks = attacks;
        this.abilities = abilities;
        this.defaultQuickAccess = defaultQuickAccess;
        this.statsClass = statsClass;
        this.defaultStats = defaultStats;
        
        if (additions == null) additions = new StandTypeOptionals();
        this.ostSupplier = additions.ostSupplier;
        this.summonShoutSupplier = additions.summonShoutSupplier;
        this.canPlayerGet = additions.canPlayerGet;
        this.resolveLevelItems = additions.resolveLevelItems;
    }
    
    @Override
    public boolean keepOnDeath(IStandPower power) {
        return JojoModConfig.getCommonConfigInstance(false).keepStandOnDeath.get();
    }
    
    public T getStats() {
    	StandStatsManager statsManager = StandStatsManager.getInstance();
        return statsManager != null ? statsManager.getStats(this) : getDefaultStats();
    }
    
    public T getDefaultStats() {
        return defaultStats;
    }
    
    public Class<T> getStatsClass() {
        return statsClass;
    }
    
    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean isReplaceableWith(StandType<?> newType) {
        return false;
    }

    @Override
    public void tickUser(LivingEntity user, IStandPower power) {
        if (!power.canUsePower()) {
            forceUnsummon(user, power);
        }
    }
    
    @Override
    public void onNewDay(LivingEntity user, IStandPower power, long prevDay, long day) {
    	getStats().onNewDay(user, power);
    }
    
    @Override
    public StandAction[] getAttacks() {
        return attacks;
    }

    @Override
    public StandAction[] getAbilities() {
        return abilities;
    }
    
    @Override
    public StandAction getDefaultQuickAccess() {
        return defaultQuickAccess;
    }
    
    public boolean usesStamina() {
        return false;
    }
    
    public float getMaxStamina(IStandPower power) {
        return 1000;
    }
    
    public float getStaminaRegen(IStandPower power) {
        return 3F;
    }
    
    public boolean usesResolve() {
        return false;
    }
    
    public int getMaxResolveLevel() {
        return 4;
    }
    
    public void onNewResolveLevel(IStandPower power) {
        LivingEntity user = power.getUser();
        if (!user.level.isClientSide()) {
            unlockNewActions(power);
            
            if (user instanceof PlayerEntity) {
                List<ItemStack> giveItems = resolveLevelItems.get(power.getResolveLevel());
                if (giveItems != null) {
                    PlayerEntity player = (PlayerEntity) user;
                    giveItems.forEach(item -> player.addItem(item.copy()));
                }
            }
        }
    }
    
    public void unlockNewActions(IStandPower power) {
        Stream.concat(Arrays.stream(attacks), Arrays.stream(abilities))
        .forEach(action -> {
            tryUnlock(action, power);
            if (action.hasShiftVariation()) {
                tryUnlock((StandAction) action.getShiftVariationIfPresent(), power);
            }
        });
    }
    
    private void tryUnlock(StandAction action, IStandPower power) {
        if (action.canBeUnlocked(power)) {
            power.unlockAction(action);
        }
    }
    
    public boolean usesStandComboMechanic() {
        return false;
    }
    
    @Override
    public float getTargetResolveMultiplier(IStandPower power, IStandPower attackingStand) {
    	float multiplier = getTier() + 1;
    	if (attackingStand.hasPower()) {
    		multiplier = Math.max(multiplier - attackingStand.getType().getTier(), 1);
    	}
        return multiplier;
    }
    
    @Override
    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.makeDescriptionId("stand", ModStandActions.STANDS.getRegistry().getKey(this));
        }
        return this.translationKey;
    }
    
    @Override
    public ResourceLocation getIconTexture() {
        if (iconTexture == null) {
            iconTexture = JojoModUtil.makeTextureLocation("power", getRegistryName().getNamespace(), getRegistryName().getPath());
        }
        return this.iconTexture;
    }
    
    public ITextComponent getPartName() {
        return partName;
    }

    public int getTier() {
        return getStats().getTier();
    }
    
    public void toggleSummon(IStandPower standPower) {
        if (!standPower.isActive()) {
            summon(standPower.getUser(), standPower, false);
        }
        else {
            unsummon(standPower.getUser(), standPower);
        }
    }
    
    protected void triggerAdvancement(IStandPower standPower, IStandManifestation stand) {
        if (standPower.getUser() instanceof ServerPlayerEntity) {
            ModCriteriaTriggers.SUMMON_STAND.get().trigger((ServerPlayerEntity) standPower.getUser(), standPower);
        }
    }

    public boolean summon(LivingEntity user, IStandPower standPower, boolean withoutNameVoiceLine) {
        if (!standPower.canUsePower()) {
            return false;
        }
        if (!withoutNameVoiceLine && !user.isShiftKeyDown()) {
            SoundEvent shout = summonShoutSupplier.get();
            if (shout != null) {
                JojoModUtil.sayVoiceLine(user, shout);
            }
        }
        triggerAdvancement(standPower, standPower.getStandManifestation());
        return true;
    }
    
    public boolean canBeManuallyControlled() {
        return false;
    }
    
    @Nullable
    public OstSoundList getOst() {
        return ostSupplier;
    }
    
    public abstract void unsummon(LivingEntity user, IStandPower standPower);
    
    public abstract void forceUnsummon(LivingEntity user, IStandPower standPower);
    
    public boolean isStandSummoned(LivingEntity user, IStandPower standPower) {
        return standPower.getStandManifestation() != null;
    }
    
    public static void onHurtByStand(DamageSource dmgSource, float dmgAmount, LivingEntity target) {
        if (dmgSource instanceof IStandDamageSource) {
            IStandDamageSource standDmgSource = (IStandDamageSource) dmgSource;
            IStandPower attackerStand = standDmgSource.getStandPower();
            target.getCapability(LivingUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                cap.setLastHurtByStand(attackerStand, dmgAmount, standDmgSource.getStandInvulTicks());
            });
        }
    }
    
    
    
    public static class StandTypeOptionals {
        private boolean canPlayerGet = true;
        private Supplier<SoundEvent> summonShoutSupplier = () -> null;
        private OstSoundList ostSupplier = null;
        private Map<Integer, List<ItemStack>> resolveLevelItems = new HashMap<>();
        
        public StandTypeOptionals addSummonShout(Supplier<SoundEvent> summonShoutSupplier) {
            if (summonShoutSupplier != null) {
                this.summonShoutSupplier = summonShoutSupplier;
            }
            return this;
        }
        
        public StandTypeOptionals addOst(OstSoundList ostSupplier) {
            if (ostSupplier != null) {
                this.ostSupplier = ostSupplier;
            }
            return this;
        }
        
        public StandTypeOptionals addItemOnResolveLevel(int resolveLevel, ItemStack item) {
            if (item != null && !item.isEmpty()) {
                resolveLevelItems.computeIfAbsent(resolveLevel, lvl -> new ArrayList<>()).add(item);
            }
            return this;
        }
        
        public StandTypeOptionals setPlayerAccess(boolean canPlayerGet) {
            this.canPlayerGet = canPlayerGet;
            return this;
        }
        
    }
}
