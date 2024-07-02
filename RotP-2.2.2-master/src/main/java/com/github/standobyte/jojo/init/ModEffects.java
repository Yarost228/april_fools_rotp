package com.github.standobyte.jojo.init;

import java.util.Set;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.potion.CMoonInversionEffect;
import com.github.standobyte.jojo.potion.FreezeEffect;
import com.github.standobyte.jojo.potion.HamonShockEffect;
import com.github.standobyte.jojo.potion.HamonSpreadEffect;
import com.github.standobyte.jojo.potion.InfiniteSpinEffect;
import com.github.standobyte.jojo.potion.MoldEffect;
import com.github.standobyte.jojo.potion.ResolveEffect;
import com.github.standobyte.jojo.potion.StaminaRegenEffect;
import com.github.standobyte.jojo.potion.StatusEffect;
import com.github.standobyte.jojo.potion.StunEffect;
import com.github.standobyte.jojo.potion.UncurableEffect;
import com.github.standobyte.jojo.potion.UndeadRegenerationEffect;
import com.github.standobyte.jojo.potion.VampireWeaknessEffect;
import com.github.standobyte.jojo.power.nonstand.type.vampirism.VampirismPowerType;
import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = JojoMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, JojoMod.MOD_ID);
    
    public static final RegistryObject<FreezeEffect> FREEZE = EFFECTS.register("freeze", 
            () -> new FreezeEffect(EffectType.HARMFUL, 0xD6D6FF));
    
    public static final RegistryObject<UndeadRegenerationEffect> UNDEAD_REGENERATION = EFFECTS.register("undead_regeneration", 
            () -> new UndeadRegenerationEffect(EffectType.BENEFICIAL, Effects.REGENERATION.getColor()));
    
    public static final RegistryObject<HamonSpreadEffect> HAMON_SPREAD = EFFECTS.register("hamon_spread", 
            () -> new HamonSpreadEffect(EffectType.HARMFUL, 0xFFC10A));
    
    public static final RegistryObject<VampireWeaknessEffect> VAMPIRE_SUN_WEAKNESS = EFFECTS.register("vampire_sun_weakness", 
            () -> new VampireWeaknessEffect());
    
    public static final RegistryObject<StunEffect> STUN = EFFECTS.register("stun", 
            () -> new StunEffect(EffectType.HARMFUL, 0x404040));
    
    public static final RegistryObject<UncurableEffect> MEDITATION = EFFECTS.register("meditation", 
            () -> new UncurableEffect(EffectType.NEUTRAL, 0xDD6000));
    
    public static final RegistryObject<HamonShockEffect> HAMON_SHOCK = EFFECTS.register("hamon_shock", 
            () -> new HamonShockEffect(EffectType.HARMFUL, 0xFFC10A));
    
    public static final RegistryObject<UncurableEffect> CHEAT_DEATH = EFFECTS.register("cheat_death", 
            () -> new UncurableEffect(EffectType.BENEFICIAL, 0xEADB84));
    
    public static final RegistryObject<StaminaRegenEffect> STAMINA_REGEN = EFFECTS.register("stamina_regen", 
            () -> new StaminaRegenEffect(EffectType.BENEFICIAL, 0x149900));

    public static final RegistryObject<UncurableEffect> TIME_STOP = EFFECTS.register("time_stop", 
            () -> new UncurableEffect(EffectType.BENEFICIAL, 0x707070));
    
    public static final RegistryObject<UncurableEffect> RESOLVE = EFFECTS.register("resolve", 
            () -> new ResolveEffect(EffectType.BENEFICIAL, 0xC6151F));
    
    public static final RegistryObject<Effect> SUN_RESISTANCE = EFFECTS.register("sun_resistance", 
            () -> new StatusEffect(EffectType.BENEFICIAL, 0xFFD54A));
    
    public static final RegistryObject<Effect> SPIRIT_VISION = EFFECTS.register("spirit_vision", 
            () -> new StatusEffect(EffectType.BENEFICIAL, 0x8E45FF));
    
    public static final RegistryObject<Effect> MISSHAPEN_FACE = EFFECTS.register("misshapen_face", 
            () -> new StatusEffect(EffectType.HARMFUL, 0x808080));

    public static final RegistryObject<Effect> MISSHAPEN_ARMS = EFFECTS.register("misshapen_arms", 
            () -> new StatusEffect(EffectType.HARMFUL, 0x808080));
    
    public static final RegistryObject<Effect> MISSHAPEN_LEGS = EFFECTS.register("misshapen_legs", 
            () -> new StatusEffect(EffectType.HARMFUL, 0x808080));
    
    public static final RegistryObject<Effect> MOLD = EFFECTS.register("mold", 
            () -> new MoldEffect());
    
    public static final RegistryObject<Effect> INVERSION = EFFECTS.register("inversion", 
            () -> new CMoonInversionEffect());
    
    public static final RegistryObject<Effect> INFINITE_SPIN = EFFECTS.register("infinite_spin", 
            () -> new InfiniteSpinEffect());
    
//    public static final RegistryObject<Effect> STAND_SEALING = EFFECTS.register("stand_sealing", 
//            () -> new StatusEffect(EffectType.HARMFUL, 0xCACAD8)); // TODO Stand Sealing effect
    
    private static Set<Effect> TRACKED_EFFECTS;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static final void afterEffectsRegister(RegistryEvent.Register<Effect> event) {
        VampirismPowerType.initVampiricEffects();
        StandEntity.addSharedEffects(TIME_STOP.get(), Effects.BLINDNESS);
        TRACKED_EFFECTS = ImmutableSet.of(RESOLVE.get(), TIME_STOP.get(), STUN.get(), HAMON_SHOCK.get(), VAMPIRE_SUN_WEAKNESS.get(), INFINITE_SPIN.get());
    }
    
    public static boolean isEffectTracked(Effect effect) {
        return TRACKED_EFFECTS.contains(effect);
    }
    
    
    
    public static boolean isStunned(LivingEntity entity) {
        return entity.hasEffect(STUN.get()) || entity.hasEffect(HAMON_SHOCK.get());
    }
}
