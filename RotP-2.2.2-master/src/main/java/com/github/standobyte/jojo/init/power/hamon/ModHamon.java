package com.github.standobyte.jojo.init.power.hamon;

import static com.github.standobyte.jojo.init.power.ModCommonRegistries.ACTIONS;
import static com.github.standobyte.jojo.init.power.ModCommonRegistries.NON_STAND_POWERS;

import com.github.standobyte.jojo.action.non_stand.HamonAction;
import com.github.standobyte.jojo.action.non_stand.HamonBreath;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleBarrier;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleCutter;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.non_stand.HamonCutter;
import com.github.standobyte.jojo.action.non_stand.HamonDetector;
import com.github.standobyte.jojo.action.non_stand.HamonHealing;
import com.github.standobyte.jojo.action.non_stand.HamonLifeMagnetism;
import com.github.standobyte.jojo.action.non_stand.HamonOrganismInfusion;
import com.github.standobyte.jojo.action.non_stand.HamonOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonOverdriveBarrage;
import com.github.standobyte.jojo.action.non_stand.HamonPlantInfusion;
import com.github.standobyte.jojo.action.non_stand.HamonProjectileShield;
import com.github.standobyte.jojo.action.non_stand.HamonRepellingOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonScarletOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonSendoOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonSendoWaveKick;
import com.github.standobyte.jojo.action.non_stand.HamonSpeedBoost;
import com.github.standobyte.jojo.action.non_stand.HamonSunlightYellowOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonTornadoOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonTurquoiseBlueOverdrive;
import com.github.standobyte.jojo.action.non_stand.HamonWallClimbing;
import com.github.standobyte.jojo.action.non_stand.HamonZoomPunch;
import com.github.standobyte.jojo.entity.LeavesGliderEntity;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.nonstand.type.hamon.HamonPowerType;

import net.minecraftforge.fml.RegistryObject;

public class ModHamon {
    
    public static void loadRegistryObjects() {}

    // FIXME ! (hamon 2) rebalance hamon damage
    public static final RegistryObject<HamonOverdrive> HAMON_OVERDRIVE = ACTIONS.registerEntry("hamon_overdrive", 
            () -> new HamonOverdrive(new HamonAction.Builder().energyCost(750F)));
     
    public static final RegistryObject<HamonAction> HAMON_SUNLIGHT_YELLOW_OVERDRIVE = ACTIONS.registerEntry("hamon_sunlight_yellow_overdrive", 
            () -> new HamonSunlightYellowOverdrive(new HamonAction.Builder().heldSlowDownFactor(0.9999F).holdToFire(0, true).holdType(80)
                    .shout(ModHamonSkills.CHARACTER_JONATHAN, ModSounds.JONATHAN_SUNLIGHT_YELLOW_OVERDRIVE)
                    .shout(ModHamonSkills.CHARACTER_ZEPPELI, ModSounds.ZEPPELI_SUNLIGHT_YELLOW_OVERDRIVE)
                    .shout(ModHamonSkills.CHARACTER_JOSEPH, ModSounds.JOSEPH_SUNLIGHT_YELLOW_OVERDRIVE)
                    .shout(ModHamonSkills.CHARACTER_CAESAR, ModSounds.CAESAR_SUNLIGHT_YELLOW_OVERDRIVE)));
    
    public static final RegistryObject<HamonAction> HAMON_SENDO_OVERDRIVE = ACTIONS.registerEntry("hamon_sendo_overdrive", 
            () -> new HamonSendoOverdrive(new HamonAction.Builder().energyCost(1000F)
                    .emptyMainHand().swingHand()
                    .shout(ModHamonSkills.CHARACTER_JONATHAN, ModSounds.JONATHAN_SENDO_OVERDRIVE)));
    
    public static final RegistryObject<HamonAction> HAMON_TURQUOISE_BLUE_OVERDRIVE = ACTIONS.registerEntry("hamon_turquoise_blue_overdrive", 
            () -> new HamonTurquoiseBlueOverdrive(new HamonAction.Builder().energyCost(250F)
                    .emptyMainHand().swingHand()));
    
    public static final RegistryObject<HamonAction> HAMON_ZOOM_PUNCH = ACTIONS.registerEntry("hamon_zoom_punch", 
            () -> new HamonZoomPunch(new HamonAction.Builder().energyCost(150F).cooldown(14, 0)
                    .shout(ModHamonSkills.CHARACTER_JONATHAN, ModSounds.JONATHAN_ZOOM_PUNCH)
                    .shout(ModHamonSkills.CHARACTER_ZEPPELI, ModSounds.ZEPPELI_ZOOM_PUNCH)
                    .shout(ModHamonSkills.CHARACTER_JOSEPH, ModSounds.JOSEPH_ZOOM_PUNCH)));

    public static final RegistryObject<HamonAction> HAMON_SPEED_BOOST = ACTIONS.registerEntry("hamon_speed_boost", 
            () -> new HamonSpeedBoost(new HamonAction.Builder().energyCost(600F)));
    
    public static final RegistryObject<HamonAction> HAMON_PLANT_INFUSION = ACTIONS.registerEntry("hamon_plant_infusion", 
            () -> new HamonPlantInfusion(new HamonAction.Builder().energyCost(200F)
                    .emptyMainHand().swingHand()));
    
    public static final RegistryObject<HamonAction> HAMON_ORGANISM_INFUSION = ACTIONS.registerEntry("hamon_organism_infusion", 
            () -> new HamonOrganismInfusion(new HamonAction.Builder().energyCost(200F)
                    .emptyMainHand().swingHand()));
    
    public static final RegistryObject<HamonAction> HAMON_HEALING = ACTIONS.registerEntry("hamon_healing", 
            () -> new HamonHealing(new HamonAction.Builder().energyCost(670F)
                    .emptyMainHand().swingHand()));

    // FIXME ! (hamon) hamon breath icon
    public static final RegistryObject<HamonAction> HAMON_BREATH = ACTIONS.registerEntry("hamon_breath", 
            () -> new HamonBreath(new HamonAction.Builder().holdType().heldSlowDownFactor(0.0F)
                    .shout(ModSounds.BREATH_DEFAULT)
                    .shout(ModHamonSkills.CHARACTER_JONATHAN, ModSounds.BREATH_JONATHAN)
                    .shout(ModHamonSkills.CHARACTER_ZEPPELI, ModSounds.BREATH_ZEPPELI)
                    .shout(ModHamonSkills.CHARACTER_JOSEPH, ModSounds.BREATH_JOSEPH)
                    .shout(ModHamonSkills.CHARACTER_CAESAR, ModSounds.BREATH_CAESAR)
                    .shout(ModHamonSkills.CHARACTER_LISA_LISA, ModSounds.BREATH_LISA_LISA)));
    
    public static final RegistryObject<HamonAction> HAMON_WALL_CLIMBING = ACTIONS.registerEntry("hamon_wall_climbing", 
            () -> new HamonWallClimbing(new HamonAction.Builder().holdEnergyCost(10F)));
    
    public static final RegistryObject<HamonAction> HAMON_DETECTOR = ACTIONS.registerEntry("hamon_detector", 
            () -> new HamonDetector(new HamonAction.Builder().holdEnergyCost(5F).heldSlowDownFactor(0.5F)));
    
    public static final RegistryObject<HamonAction> HAMON_LIFE_MAGNETISM = ACTIONS.registerEntry("hamon_life_magnetism", 
            () -> new HamonLifeMagnetism(new HamonAction.Builder().energyCost(LeavesGliderEntity.MAX_ENERGY)
                    .shout(ModHamonSkills.CHARACTER_ZEPPELI, ModSounds.ZEPPELI_LIFE_MAGNETISM_OVERDRIVE)));
    
    public static final RegistryObject<HamonAction> HAMON_PROJECTILE_SHIELD = ACTIONS.registerEntry("hamon_projectile_shield", 
            () -> new HamonProjectileShield(new HamonAction.Builder().holdEnergyCost(15F).heldSlowDownFactor(0.5F)
                    .shout(ModHamonSkills.CHARACTER_JOSEPH, ModSounds.JOSEPH_BARRIER)));
    
    public static final RegistryObject<HamonAction> HAMON_REPELLING_OVERDRIVE = ACTIONS.registerEntry("hamon_repelling_overdrive", 
            () -> new HamonRepellingOverdrive(new HamonAction.Builder().energyCost(1000F)));
    
    

    public static final RegistryObject<HamonPowerType> HAMON = NON_STAND_POWERS.registerEntry("hamon", 
            () -> new HamonPowerType(
                    0xFFFF00, 
                    new HamonAction[] {
                            HAMON_SENDO_OVERDRIVE.get(), 
                            HAMON_SUNLIGHT_YELLOW_OVERDRIVE.get(), 
                            HAMON_PLANT_INFUSION.get(), 
                            HAMON_ZOOM_PUNCH.get(), 
                            HAMON_TURQUOISE_BLUE_OVERDRIVE.get()}, 
                    new HamonAction[] {
                            HAMON_BREATH.get(), 
                            HAMON_HEALING.get(), 
                            HAMON_SPEED_BOOST.get(), 
                            HAMON_WALL_CLIMBING.get(), 
                            HAMON_DETECTOR.get(), 
                            HAMON_LIFE_MAGNETISM.get(), 
                            HAMON_PROJECTILE_SHIELD.get(), 
                            HAMON_REPELLING_OVERDRIVE.get()}
                    ));
    
    
    
    public static final RegistryObject<HamonAction> JONATHAN_OVERDRIVE_BARRAGE = ACTIONS.registerEntry("jonathan_overdrive_barrage", 
            () -> new HamonOverdriveBarrage(new HamonAction.Builder().holdEnergyCost(50F).heldSlowDownFactor(0.5F).shout(ModSounds.JONATHAN_OVERDRIVE_BARRAGE)));

    public static final RegistryObject<HamonAction> JONATHAN_SCARLET_OVERDRIVE = ACTIONS.registerEntry("jonathan_scarlet_overdrive", 
            () -> new HamonScarletOverdrive(new HamonAction.Builder().energyCost(150F)
                    .emptyMainHand().swingHand().shout(ModSounds.JONATHAN_SCARLET_OVERDRIVE)));
    
    public static final RegistryObject<HamonAction> ZEPPELI_HAMON_CUTTER = ACTIONS.registerEntry("zeppeli_hamon_cutter", 
            () -> new HamonCutter(new HamonAction.Builder().energyCost(400F).shout(ModSounds.ZEPPELI_HAMON_CUTTER)));
    
    public static final RegistryObject<HamonAction> ZEPPELI_SENDO_WAVE_KICK = ACTIONS.registerEntry("zeppeli_sendo_wave_kick", 
            () -> new HamonSendoWaveKick(new HamonAction.Builder().energyCost(1000F).shout(ModSounds.ZEPPELI_SENDO_WAVE_KICK)));
    
    public static final RegistryObject<HamonAction> ZEPPELI_TORNADO_OVERDRIVE = ACTIONS.registerEntry("zeppeli_tornado_overdrive", 
            () -> new HamonTornadoOverdrive(new HamonAction.Builder().holdEnergyCost(50F).shout(ModSounds.ZEPPELI_TORNADO_OVERDRIVE), false));
    
    public static final RegistryObject<HamonAction> ZEPPELI_TORNADO_OVERDRIVE_HORIZONTAL = ACTIONS.registerEntry("zeppeli_tornado_overdrive_horizontal", 
            () -> new HamonTornadoOverdrive(new HamonAction.Builder().holdEnergyCost(50F).shout(ModSounds.ZEPPELI_TORNADO_OVERDRIVE), true));
    
    public static final RegistryObject<HamonAction> CAESAR_BUBBLE_LAUNCHER = ACTIONS.registerEntry("caesar_bubble_launcher", 
            () -> new HamonBubbleLauncher(new HamonAction.Builder().holdEnergyCost(30F).heldSlowDownFactor(0.3F).shout(ModSounds.CAESAR_BUBBLE_LAUNCHER)));
    
    public static final RegistryObject<HamonAction> CAESAR_BUBBLE_BARRIER = ACTIONS.registerEntry("caesar_bubble_barrier", 
            () -> new HamonBubbleBarrier(new HamonAction.Builder().holdToFire(20, false).holdEnergyCost(30F).heldSlowDownFactor(0.3F)
                    .shout(ModSounds.CAESAR_BUBBLE_BARRIER)));
    
    public static final RegistryObject<HamonAction> CAESAR_BUBBLE_CUTTER = ACTIONS.registerEntry("caesar_bubble_cutter", 
            () -> new HamonBubbleCutter(new HamonAction.Builder().energyCost(500F).swingHand().shout(ModSounds.CAESAR_BUBBLE_CUTTER)));
    
    public static final RegistryObject<HamonAction> CAESAR_BUBBLE_CUTTER_GLIDING = ACTIONS.registerEntry("caesar_bubble_cutter_gliding", 
            () -> new HamonBubbleCutter(new HamonAction.Builder().energyCost(600F).cooldown(40).swingHand()
                    .shout(ModSounds.CAESAR_BUBBLE_CUTTER_GLIDING).shiftVariationOf(CAESAR_BUBBLE_CUTTER)));
    
}
