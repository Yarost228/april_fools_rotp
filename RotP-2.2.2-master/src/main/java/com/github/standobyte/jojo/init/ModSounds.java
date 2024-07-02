package com.github.standobyte.jojo.init;

import java.util.function.Supplier;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.util.MultiSoundEvent;
import com.github.standobyte.jojo.util.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, JojoMod.MOD_ID);
    
    public static final RegistryObject<SoundEvent> COUGH_1 = SOUNDS.register("cough_1",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cough_1")));
    
    public static final RegistryObject<SoundEvent> COUGH_2 = SOUNDS.register("cough_2",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cough_2")));
    
    public static final RegistryObject<SoundEvent> COUGH_3 = SOUNDS.register("cough_3",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cough_3")));
    
    public static final RegistryObject<SoundEvent> STONE_MASK_ACTIVATION_ENTITY = SOUNDS.register("stone_mask_activation_entity",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_mask_activation_entity")));
    
    public static final RegistryObject<SoundEvent> STONE_MASK_ACTIVATION = SOUNDS.register("stone_mask_activation",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_mask_activation")));
    
    public static final RegistryObject<SoundEvent> STONE_MASK_DEACTIVATION = SOUNDS.register("stone_mask_deactivation",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_mask_deactivation")));
    
    public static final RegistryObject<SoundEvent> BLADE_HAT_THROW = SOUNDS.register("blade_hat_throw",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "blade_hat_throw")));
    
    public static final RegistryObject<SoundEvent> BLADE_HAT_SPINNING = SOUNDS.register("blade_hat_spinning",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "blade_hat_spinning")));
    
    public static final RegistryObject<SoundEvent> BLADE_HAT_ENTITY_HIT = SOUNDS.register("blade_hat_entity_hit",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "blade_hat_entity_hit")));
    
    public static final RegistryObject<SoundEvent> PILLAR_MAN_AWAKENING = SOUNDS.register("pillar_man_awakening",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "pillar_man_awakening")));
    
    public static final RegistryObject<SoundEvent> AJA_STONE_CHARGING = SOUNDS.register("aja_stone_charging",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "aja_stone_charging")));
    
    public static final RegistryObject<SoundEvent> AJA_STONE_BEAM = SOUNDS.register("aja_stone_beam",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "aja_stone_beam")));
    
    public static final RegistryObject<SoundEvent> CLACKERS = SOUNDS.register("clackers", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "clackers")));
    
    public static final RegistryObject<SoundEvent> TOMMY_GUN_SHOT = SOUNDS.register("tommy_gun_shot", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tommy_gun_shot")));
    
    public static final RegistryObject<SoundEvent> TOMMY_GUN_NO_AMMO = SOUNDS.register("tommy_gun_no_ammo", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tommy_gun_no_ammo")));
    
    public static final RegistryObject<SoundEvent> KNIFE_THROW = SOUNDS.register("knife_throw",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "knife_throw")));
    
    public static final RegistryObject<SoundEvent> KNIVES_THROW = SOUNDS.register("knives_throw",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "knives_throw")));
    
    public static final RegistryObject<SoundEvent> KNIFE_HIT = SOUNDS.register("knife_hit",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "knife_hit")));
    
    public static final RegistryObject<SoundEvent> WATER_SPLASH = SOUNDS.register("water_splash",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "water_splash")));
    
    public static final RegistryObject<SoundEvent> WALKMAN_REWIND = SOUNDS.register("walkman_rewind",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "walkman_rewind")));
    
    public static final RegistryObject<SoundEvent> CASSETTE_WHITE = SOUNDS.register("cassette_white", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_white")));
    public static final RegistryObject<SoundEvent> CASSETTE_ORANGE = SOUNDS.register("cassette_orange", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_orange")));
    public static final RegistryObject<SoundEvent> CASSETTE_MAGENTA = SOUNDS.register("cassette_magenta", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_magenta")));
    public static final RegistryObject<SoundEvent> CASSETTE_LIGHT_BLUE = SOUNDS.register("cassette_light_blue", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_light_blue")));
    public static final RegistryObject<SoundEvent> CASSETTE_YELLOW = SOUNDS.register("cassette_yellow", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_yellow")));
    public static final RegistryObject<SoundEvent> CASSETTE_LIME = SOUNDS.register("cassette_lime", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_lime")));
    public static final RegistryObject<SoundEvent> CASSETTE_PINK = SOUNDS.register("cassette_pink", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_pink")));
    public static final RegistryObject<SoundEvent> CASSETTE_GRAY = SOUNDS.register("cassette_gray", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_gray")));
    public static final RegistryObject<SoundEvent> CASSETTE_LIGHT_GRAY = SOUNDS.register("cassette_light_gray", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_light_gray")));
    public static final RegistryObject<SoundEvent> CASSETTE_CYAN = SOUNDS.register("cassette_cyan", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_cyan")));
    public static final RegistryObject<SoundEvent> CASSETTE_PURPLE = SOUNDS.register("cassette_purple", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_purple")));
    public static final RegistryObject<SoundEvent> CASSETTE_BLUE = SOUNDS.register("cassette_blue", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_blue")));
    public static final RegistryObject<SoundEvent> CASSETTE_BROWN = SOUNDS.register("cassette_brown", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_brown")));
    public static final RegistryObject<SoundEvent> CASSETTE_GREEN = SOUNDS.register("cassette_green", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_green")));
    public static final RegistryObject<SoundEvent> CASSETTE_RED = SOUNDS.register("cassette_red", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_red")));
    public static final RegistryObject<SoundEvent> CASSETTE_BLACK = SOUNDS.register("cassette_black", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cassette_black")));

    public static final RegistryObject<SoundEvent> VAMPIRE_BLOOD_DRAIN = SOUNDS.register("vampire_blood_drain",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "vampire_blood_drain")));
    
    public static final RegistryObject<SoundEvent> VAMPIRE_FREEZE = SOUNDS.register("vampire_freeze",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "vampire_freeze")));

    public static final RegistryObject<SoundEvent> VAMPIRE_EVIL_ATMOSPHERE = SOUNDS.register("vampire_dark_aura",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "vampire_dark_aura")));

    public static final RegistryObject<SoundEvent> VAMPIRE_CURE_START = SOUNDS.register("vampire_cure_start",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "vampire_cure_start")));

    public static final RegistryObject<SoundEvent> VAMPIRE_CURE_END = SOUNDS.register("vampire_cure_end",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "vampire_cure_end")));


    public static final RegistryObject<SoundEvent> HAMON_PICK_JONATHAN = SOUNDS.register("hamon_pick_jonathan",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_pick_jonathan")));
    public static final RegistryObject<SoundEvent> HAMON_PICK_ZEPPELI = SOUNDS.register("hamon_pick_zeppeli",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_pick_zeppeli")));
    public static final RegistryObject<SoundEvent> HAMON_PICK_JOSEPH = SOUNDS.register("hamon_pick_joseph",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_pick_joseph")));
    public static final RegistryObject<SoundEvent> HAMON_PICK_CAESAR = SOUNDS.register("hamon_pick_caesar",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_pick_caesar")));
    public static final RegistryObject<SoundEvent> HAMON_PICK_LISA_LISA = SOUNDS.register("hamon_pick_lisa_lisa",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_pick_lisa_lisa")));
    
    public static final RegistryObject<SoundEvent> HAMON_SPARK = SOUNDS.register("hamon_spark",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_spark")));
            
    public static final RegistryObject<SoundEvent> HAMON_SPARKS_LONG = SOUNDS.register("hamon_sparks_long",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_sparks_long")));
    
    public static final RegistryObject<SoundEvent> HAMON_SPARKS_LOOP = SOUNDS.register("hamon_sparks_loop",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_sparks_loop")));
    
    public static final RegistryObject<SoundEvent> HAMON_CONCENTRATION = SOUNDS.register("hamon_concentration",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_concentration")));
    
    public static final RegistryObject<SoundEvent> HAMON_SYO_CHARGE = SOUNDS.register("hamon_syo_charge",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_syo_charge")));
    
    public static final RegistryObject<SoundEvent> HAMON_SYO_PUNCH = SOUNDS.register("hamon_syo_punch",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hamon_syo_punch")));
    
    public static final RegistryObject<SoundEvent> BREATH_DEFAULT = SOUNDS.register("player_breath", 
            () -> new SoundEvent(new ResourceLocation("entity.player.breath")));
    
    public static final RegistryObject<SoundEvent> BREATH_JONATHAN = SOUNDS.register("jonathan_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_breath")));
    
    public static final RegistryObject<SoundEvent> BREATH_ZEPPELI = SOUNDS.register("zeppeli_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_breath")));
    
    public static final RegistryObject<SoundEvent> BREATH_JOSEPH = SOUNDS.register("joseph_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_breath")));
    
    public static final RegistryObject<SoundEvent> BREATH_CAESAR = SOUNDS.register("caesar_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_breath")));
    
    public static final RegistryObject<SoundEvent> BREATH_LISA_LISA = SOUNDS.register("lisa_lisa_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "lisa_lisa_breath")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_SENDO_OVERDRIVE = SOUNDS.register("jonathan_sendo_overdrive", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_sendo_overdrive")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_SUNLIGHT_YELLOW_OVERDRIVE = SOUNDS.register("jonathan_sunlight_yellow_overdrive", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_sunlight_yellow_overdrive")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_ZOOM_PUNCH = SOUNDS.register("jonathan_zoom_punch", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_zoom_punch")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_SUNLIGHT_YELLOW_OVERDRIVE = SOUNDS.register("zeppeli_sunlight_yellow_overdrive", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_sunlight_yellow_overdrive"), new ResourceLocation(JojoMod.MOD_ID, "zeppeli_hamon_of_the_sun"), 
                    new ResourceLocation(JojoMod.MOD_ID, "zeppeli_this_is_sendo"), new ResourceLocation(JojoMod.MOD_ID, "zeppeli_this_is_sendo_power")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_ZOOM_PUNCH = SOUNDS.register("zeppeli_zoom_punch", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_zoom_punch")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_LIFE_MAGNETISM_OVERDRIVE = SOUNDS.register("zeppeli_life_magnetism_overdrive", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_life_magnetism_overdrive")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_FORCE_BREATH = SOUNDS.register("zeppeli_force_breath", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_force_breath")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_SUNLIGHT_YELLOW_OVERDRIVE = SOUNDS.register("joseph_sunlight_yellow_overdrive", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_hamon_overdrive_beat"), 
                    new ResourceLocation(JojoMod.MOD_ID, "joseph_hamon_punch"), new ResourceLocation(JojoMod.MOD_ID, "joseph_rebuff_overdrive")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_ZOOM_PUNCH = SOUNDS.register("joseph_zoom_punch", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_zoom_punch")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_BARRIER = SOUNDS.register("joseph_barrier", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_barrier")));
    
    public static final RegistryObject<SoundEvent> CAESAR_SUNLIGHT_YELLOW_OVERDRIVE = SOUNDS.register("caesar_sunlight_yellow_overdrive", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_sun_vibration"), new ResourceLocation(JojoMod.MOD_ID, "caesar_hamon_of_the_sun"), 
                    new ResourceLocation(JojoMod.MOD_ID, "caesar_hamon_spark")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_OVERDRIVE_BARRAGE = SOUNDS.register("jonathan_overdrive_barrage", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_overdrive_barrage")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_SCARLET_OVERDRIVE = SOUNDS.register("jonathan_scarlet_overdrive", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_scarlet_overdrive"), new ResourceLocation(JojoMod.MOD_ID, "jonathan_hamon_of_flame")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_PLUCK_SWORD = SOUNDS.register("jonathan_pluck_sword", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_pluck_sword")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_HAMON_CUTTER = SOUNDS.register("zeppeli_hamon_cutter", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_hamon_cutter"), new ResourceLocation(JojoMod.MOD_ID, "zeppeli_popow_pow_pow")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_SENDO_WAVE_KICK = SOUNDS.register("zeppeli_sendo_wave_kick", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_sendo_wave_kick")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_TORNADO_OVERDRIVE = SOUNDS.register("zeppeli_tornado_overdrive", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_tornado_overdrive")));
    
    public static final RegistryObject<SoundEvent> ZEPPELI_DEEP_PASS = SOUNDS.register("zeppeli_deep_pass", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "zeppeli_deep_pass")));
    
    public static final RegistryObject<SoundEvent> JONATHAN_DEEP_PASS_REACTION = SOUNDS.register("jonathan_deep_pass_reaction", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_deep_pass_reaction")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_OH_NO = SOUNDS.register("joseph_oh_no", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_oh_no")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_CLACKER_VOLLEY = SOUNDS.register("joseph_clacker_volley", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_clacker_volley"), new ResourceLocation(JojoMod.MOD_ID, "joseph_hamon_clacker_volley")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_CLACKER_BOOMERANG = SOUNDS.register("joseph_clacker_boomerang", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_clacker_boomerang")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_GIGGLE = SOUNDS.register("joseph_giggle", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_giggle")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_RUN_AWAY = SOUNDS.register("joseph_run_away", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_run_away")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_SCREAM_SHOOTING = SOUNDS.register("joseph_scream", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_scream")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_WAR_DECLARATION = SOUNDS.register("joseph_war_declaration", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_war_declaration")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_SHOOT = SOUNDS.register("joseph_shoot", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_shoot")));
    
    public static final RegistryObject<SoundEvent> CAESAR_BUBBLE_LAUNCHER = SOUNDS.register("caesar_bubble_launcher", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_bubble_launcher"), new ResourceLocation(JojoMod.MOD_ID, "caesar_secret_hamon_bubble_launcher")));
    
    public static final RegistryObject<SoundEvent> CAESAR_BUBBLE_BARRIER = SOUNDS.register("caesar_bubble_barrier", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_bubble_barrier")));
    
    public static final RegistryObject<SoundEvent> CAESAR_BUBBLE_CUTTER = SOUNDS.register("caesar_bubble_cutter", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_bubble_cutter"), new ResourceLocation(JojoMod.MOD_ID, "caesar_disc_shaped_hamon_cutter")));
    
    public static final RegistryObject<SoundEvent> CAESAR_BUBBLE_CUTTER_GLIDING = SOUNDS.register("caesar_bubble_cutter_gliding", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_bubble_cutter_gliding")));
    
    public static final RegistryObject<SoundEvent> CAESAR_LAST_HAMON = SOUNDS.register("caesar_last_hamon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "caesar_last_hamon")));
    
    public static final RegistryObject<SoundEvent> JOSEPH_CRIMSON_BUBBLE_REACTION = SOUNDS.register("joseph_crimson_bubble_reaction", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "joseph_crimson_bubble_reaction")));
    
    public static final RegistryObject<SoundEvent> LISA_LISA_AJA_STONE = SOUNDS.register("lisa_lisa_aja_stone", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "lisa_lisa_aja_stone")));
    
    public static final RegistryObject<SoundEvent> LISA_LISA_SUPER_AJA = SOUNDS.register("lisa_lisa_super_aja", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "lisa_lisa_super_aja")));
    
    public static final RegistryObject<SoundEvent> LISA_LISA_SNAKE_MUFFLER = SOUNDS.register("lisa_lisa_snake_muffler", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "lisa_lisa_snake_muffler")));


    public static final RegistryObject<SoundEvent> STAND_SUMMON_DEFAULT = SOUNDS.register("stand_summon_default", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_summon_default")));
    
    public static final RegistryObject<SoundEvent> STAND_UNSUMMON_DEFAULT = SOUNDS.register("stand_unsummon_default", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_unsummon_default")));

    public static final RegistryObject<SoundEvent> STAND_DAMAGE_BLOCK = SOUNDS.register("stand_damage_block", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_damage_block")));
    
    public static final RegistryObject<SoundEvent> STAND_PUNCH_LIGHT = SOUNDS.register("stand_punch_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_punch_light")));
    
    public static final RegistryObject<SoundEvent> STAND_PUNCH_BARRAGE = SOUNDS.register("stand_punch_barrage", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_punch_barrage")));
    
    public static final RegistryObject<SoundEvent> STAND_PUNCH_HEAVY = SOUNDS.register("stand_punch_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_punch_heavy")));

    public static final RegistryObject<SoundEvent> STAND_PARRY = SOUNDS.register("stand_parry", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_parry")));

    public static final RegistryObject<SoundEvent> STAND_LEAP = SOUNDS.register("stand_leap", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stand_leap")));
    
    
    public static final RegistryObject<SoundEvent> JOTARO_STAR_PLATINUM = SOUNDS.register("jotaro_star_platinum", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jotaro_star_platinum")));

    public static final RegistryObject<SoundEvent> JOTARO_STAR_FINGER = SOUNDS.register("jotaro_star_finger", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jotaro_star_finger")));

    public static final RegistryObject<SoundEvent> JOTARO_STAR_PLATINUM_THE_WORLD = SOUNDS.register("jotaro_star_platinum_the_world", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jotaro_star_platinum_the_world")));

    public static final RegistryObject<SoundEvent> JOTARO_TIME_RESUMES = SOUNDS.register("jotaro_time_resumes", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jotaro_time_resumes_dasu"), new ResourceLocation(JojoMod.MOD_ID, "jotaro_time_resumes_hajimeta")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_SUMMON = SOUNDS.register("star_platinum_summon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_summon")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_UNSUMMON = SOUNDS.register("star_platinum_unsummon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_unsummon")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_ORA = SOUNDS.register("star_platinum_ora", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_ora")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_ORA_LONG = SOUNDS.register("star_platinum_ora_long", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_ora_long")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_ORA_ORA_ORA = SOUNDS.register("star_platinum_ora_ora_ora", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_ora_ora_ora")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_STAR_FINGER = SOUNDS.register("star_platinum_star_finger", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_star_finger")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_ZOOM = SOUNDS.register("star_platinum_zoom", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_zoom")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_ZOOM_CLICK = SOUNDS.register("star_platinum_zoom_click", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_zoom_click")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_INHALE = SOUNDS.register("star_platinum_inhale", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_inhale")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_TIME_STOP = SOUNDS.register("star_platinum_time_stop", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_time_stop")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_TIME_RESUME = SOUNDS.register("star_platinum_time_resume", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_time_resume")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_TIME_STOP_BLINK = SOUNDS.register("star_platinum_time_stop_blink", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_time_stop_blink")));
    
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_PUNCH_LIGHT = SOUNDS.register("star_platinum_punch_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_punch_light")));

    public static final RegistryObject<SoundEvent> STAR_PLATINUM_PUNCH_HEAVY = SOUNDS.register("star_platinum_punch_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_punch_heavy")));

    public static final Supplier<SoundEvent> STAR_PLATINUM_PUNCH_BARRAGE = SOUNDS.register("star_platinum_punch_barrage", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_punch_barrage")));
    
    public static final OstSoundList STAR_PLATINUM_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "star_platinum_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> KAKYOIN_HIEROPHANT_GREEN = SOUNDS.register("kakyoin_hierophant_green", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kakyoin_hierophant_green"), new ResourceLocation(JojoMod.MOD_ID, "kakyoin_hierophant")));

    public static final RegistryObject<SoundEvent> KAKYOIN_EMERALD_SPLASH = SOUNDS.register("kakyoin_emerald_splash", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kakyoin_emerald_splash")));
    
    public static final RegistryObject<SoundEvent> RERO = SOUNDS.register("rero", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "rero")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_SUMMON = SOUNDS.register("hierophant_green_summon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_summon")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_TENTACLES = SOUNDS.register("hierophant_green_tentacles", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_tentacles")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_EMERALD_SPLASH = SOUNDS.register("hierophant_green_emerald_splash", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_emerald_splash")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_BARRIER_PLACED = SOUNDS.register("hierophant_green_barrier_placed", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_barrier_placed")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_BARRIER_RIPPED = SOUNDS.register("hierophant_green_barrier_ripped", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_barrier_ripped")));
    
    public static final RegistryObject<SoundEvent> HIEROPHANT_GREEN_GRAPPLE_CATCH = SOUNDS.register("hierophant_green_grapple_catch", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_grapple_catch")));
    
    public static final OstSoundList HIEROPHANT_GREEN_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "hierophant_green_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> DIO_THE_WORLD = SOUNDS.register("dio_the_world", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_the_world")));
    
    public static final RegistryObject<SoundEvent> DIO_MUDA = SOUNDS.register("dio_muda", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_muda")));
    
    public static final RegistryObject<SoundEvent> DIO_MUDA_MUDA = SOUNDS.register("dio_muda_muda", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_muda_muda")));
    
    public static final RegistryObject<SoundEvent> DIO_WRY = SOUNDS.register("dio_wry", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_wry")));

    public static final RegistryObject<SoundEvent> DIO_DIE = SOUNDS.register("dio_die", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_die")));

    public static final RegistryObject<SoundEvent> DIO_THIS_IS_THE_WORLD = SOUNDS.register("dio_this_is_the_world", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_this_is_the_world")));

    public static final RegistryObject<SoundEvent> DIO_TIME_STOP = SOUNDS.register("dio_time_stop", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_toki_yo_tomare"), new ResourceLocation(JojoMod.MOD_ID, "dio_tomare_toki_yo")));

    public static final RegistryObject<SoundEvent> DIO_TIME_RESUMES = SOUNDS.register("dio_time_resumes", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_time_resumes")));

    public static final RegistryObject<SoundEvent> DIO_TIMES_UP = SOUNDS.register("dio_times_up", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_time_resumes"), new ResourceLocation(JojoMod.MOD_ID, "dio_times_up"), new ResourceLocation(JojoMod.MOD_ID, "dio_zero")));

    public static final RegistryObject<SoundEvent> DIO_5_SECONDS = SOUNDS.register("dio_5_seconds", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_5_seconds")));
    
    public static final RegistryObject<SoundEvent> DIO_ONE_MORE = SOUNDS.register("dio_one_more", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_one_more")));
    
    public static final RegistryObject<SoundEvent> DIO_CANT_MOVE = SOUNDS.register("dio_cant_move", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_cant_move")));

    public static final RegistryObject<SoundEvent> DIO_ROAD_ROLLER = SOUNDS.register("dio_road_roller", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dio_road_roller")));

    public static final RegistryObject<SoundEvent> JONATHAN_THE_WORLD = SOUNDS.register("jonathan_the_world", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jonathan_the_world")));

    public static final RegistryObject<SoundEvent> THE_WORLD_SUMMON = SOUNDS.register("the_world_summon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_summon")));
    
    public static final RegistryObject<SoundEvent> THE_WORLD_MUDA_MUDA_MUDA = SOUNDS.register("the_world_muda_muda_muda", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_muda_muda_muda")));

    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_STOP = SOUNDS.register("the_world_time_stop", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_time_stop")));

    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_RESUME = SOUNDS.register("the_world_time_resume", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_time_resume")));

    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_STOP_BLINK = SOUNDS.register("the_world_time_stop_blink", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_time_stop_blink")));

    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_STOP_UNREVEALED = SOUNDS.register("the_world_time_stop_unrevealed", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_time_stop_unrevealed")));
    
    public static final RegistryObject<SoundEvent> THE_WORLD_PUNCH_LIGHT = SOUNDS.register("the_world_punch_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_punch_light")));

    public static final RegistryObject<SoundEvent> THE_WORLD_PUNCH_HEAVY = SOUNDS.register("the_world_punch_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_punch_heavy")));

    public static final RegistryObject<SoundEvent> THE_WORLD_PUNCH_HEAVY_ENTITY = SOUNDS.register("the_world_punch_heavy_entity", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_punch_heavy_entity")));

    public static final RegistryObject<SoundEvent> THE_WORLD_PUNCH_HEAVY_TS_IMPACT = SOUNDS.register("the_world_punch_heavy_ts_impact", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_punch_heavy_ts_impact")));

    public static final RegistryObject<SoundEvent> THE_WORLD_KICK_HEAVY = SOUNDS.register("the_world_kick_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_world_kick_heavy")));

    public static final Supplier<SoundEvent> THE_WORLD_PUNCH_BARRAGE = THE_WORLD_PUNCH_LIGHT;

    public static final RegistryObject<SoundEvent> ROAD_ROLLER_HIT = SOUNDS.register("road_roller_hit", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "road_roller_hit")));

    public static final RegistryObject<SoundEvent> ROAD_ROLLER_LAND = SOUNDS.register("road_roller_land", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "road_roller_land")));
    
    public static final OstSoundList THE_WORLD_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "the_world_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> POLNAREFF_SILVER_CHARIOT = SOUNDS.register("polnareff_silver_chariot", 
            () -> new MultiSoundEvent(new ResourceLocation(JojoMod.MOD_ID, "polnareff_silver_chariot"), new ResourceLocation(JojoMod.MOD_ID, "polnareff_chariot")));

    public static final RegistryObject<SoundEvent> POLNAREFF_HORA_HORA_HORA = SOUNDS.register("polnareff_hora_hora_hora", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "polnareff_hora_hora_hora")));

    public static final RegistryObject<SoundEvent> POLNAREFF_FENCING = SOUNDS.register("polnareff_fencing", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "polnareff_fencing")));
    
    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_SUMMON = SOUNDS.register("silver_chariot_summon",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_summon")));
    
    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_UNSUMMON = SOUNDS.register("silver_chariot_unsummon",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_unsummon")));

    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_SWEEP_LIGHT = SOUNDS.register("silver_chariot_sweep_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_sweep_light")));

    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_BARRAGE = SOUNDS.register("silver_chariot_barrage", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_barrage")));

    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_DASH = SOUNDS.register("silver_chariot_dash", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_dash")));

    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_SWEEP_HEAVY = SOUNDS.register("silver_chariot_sweep_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_sweep_heavy")));

    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_BLOCK = SOUNDS.register("silver_chariot_block", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_block")));
    
    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_RAPIER_SHOT = SOUNDS.register("silver_chariot_rapier_shot",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_rapier_shot")));
    
    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_ARMOR_OFF = SOUNDS.register("silver_chariot_armor_off",
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_armor_off")));
    
    public static final OstSoundList SILVER_CHARIOT_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "silver_chariot_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> AVDOL_MAGICIANS_RED = SOUNDS.register("avdol_magicians_red", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "avdol_magicians_red")));

    public static final RegistryObject<SoundEvent> AVDOL_HELL_2_U = SOUNDS.register("avdol_hell_2_u", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "avdol_hell_2_u")));

    public static final RegistryObject<SoundEvent> AVDOL_CROSSFIRE_HURRICANE = SOUNDS.register("avdol_crossfire_hurricane", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "avdol_crossfire_hurricane")));

    public static final RegistryObject<SoundEvent> AVDOL_CROSSFIRE_HURRICANE_SPECIAL = SOUNDS.register("avdol_crossfire_hurricane_special", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "avdol_crossfire_hurricane_special")));

    public static final RegistryObject<SoundEvent> AVDOL_RED_BIND = SOUNDS.register("avdol_red_bind", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "avdol_red_bind")));
    
    public static final RegistryObject<SoundEvent> MAGICIANS_RED_SUMMON = SOUNDS.register("magicians_red_summon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_summon")));

    public static final RegistryObject<SoundEvent> MAGICIANS_RED_FIRE_BLAST = SOUNDS.register("magicians_red_fire_ability", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_fire_ability")));

    public static final Supplier<SoundEvent> MAGICIANS_RED_FIREBALL = () -> SoundEvents.FIRECHARGE_USE;
    
    public static final RegistryObject<SoundEvent> MAGICIANS_RED_CROSSFIRE_HURRICANE = MAGICIANS_RED_FIRE_BLAST;
    
    public static final RegistryObject<SoundEvent> MAGICIANS_RED_RED_BIND = MAGICIANS_RED_FIRE_BLAST;
    
    public static final RegistryObject<SoundEvent> MAGICIANS_RED_PUNCH_LIGHT = SOUNDS.register("magicians_red_punch_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_punch_light")));

    public static final RegistryObject<SoundEvent> MAGICIANS_RED_PUNCH_HEAVY = SOUNDS.register("magicians_red_punch_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_punch_heavy")));

    public static final RegistryObject<SoundEvent> MAGICIANS_RED_KICK_HEAVY = SOUNDS.register("magicians_red_kick_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_kick_heavy")));
    
    public static final OstSoundList MAGICIANS_RED_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "magicians_red_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> JOSUKE_CRAZY_DIAMOND = SOUNDS.register("josuke_crazy_diamond", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "josuke_crazy_diamond")));

    public static final RegistryObject<SoundEvent> JOSUKE_FIX = SOUNDS.register("josuke_fix", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "josuke_fix")));

    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_SUMMON = SOUNDS.register("crazy_diamond_summon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_summon")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_UNSUMMON = SOUNDS.register("crazy_diamond_unsummon", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_unsummon")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_PUNCH_LIGHT = SOUNDS.register("crazy_diamond_punch_light", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_punch_light")));

    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_PUNCH_HEAVY = SOUNDS.register("crazy_diamond_punch_heavy", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_punch_heavy")));

    public static final Supplier<SoundEvent> CRAZY_DIAMOND_PUNCH_BARRAGE = CRAZY_DIAMOND_PUNCH_LIGHT;
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_DORA = SOUNDS.register("crazy_diamond_dora", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_dora")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_DORA_LONG = SOUNDS.register("crazy_diamond_dora_long", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_dora_long")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_DORARARA = SOUNDS.register("crazy_diamond_dorarara", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_dorarara")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_FIX_STARTED = SOUNDS.register("crazy_diamond_fix_started", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_fix_started")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_FIX_LOOP = SOUNDS.register("crazy_diamond_fix_loop", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_fix_loop")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_FIX_ENDED = SOUNDS.register("crazy_diamond_fix_ended", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_fix_ended")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_BULLET_SHOT = SOUNDS.register("crazy_diamond_bullet_shot", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_bullet_shot")));
    
    public static final RegistryObject<SoundEvent> CRAZY_DIAMOND_BLOOD_CUTTER_SHOT = SOUNDS.register("crazy_diamond_blood_cutter_shot", 
            () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_blood_cutter_shot")));
    
    public static final OstSoundList CRAZY_DIAMOND_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "crazy_diamond_ost"), SOUNDS);
    
    
    

    
    public static final RegistryObject<SoundEvent> tennille_dark_blue_moon = SOUNDS.register("tennille_dark_blue_moon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tennille_dark_blue_moon")));
    public static final RegistryObject<SoundEvent> kira_killer_queen = SOUNDS.register("kira_killer_queen", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kira_killer_queen")));
    public static final RegistryObject<SoundEvent> okuyasu_the_hand = SOUNDS.register("okuyasu_the_hand", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "okuyasu_the_hand")));
    public static final RegistryObject<SoundEvent> koichi_echoes_act_3 = SOUNDS.register("koichi_echoes_act_3", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "koichi_echoes_act_3")));
    public static final RegistryObject<SoundEvent> rohan_heavens_door = SOUNDS.register("rohan_heavens_door", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "rohan_heavens_door")));
    public static final RegistryObject<SoundEvent> ken_boy_ii_man = SOUNDS.register("ken_boy_ii_man", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "ken_boy_ii_man")));
    public static final RegistryObject<SoundEvent> giorno_gold_experience = SOUNDS.register("giorno_gold_experience", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "giorno_gold_experience")));
    public static final RegistryObject<SoundEvent> giorno_gold_experience_requiem = SOUNDS.register("giorno_gold_experience_requiem", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "giorno_gold_experience_requiem")));
    public static final RegistryObject<SoundEvent> diavolo_king_crimson = SOUNDS.register("diavolo_king_crimson", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "diavolo_king_crimson")));
    public static final RegistryObject<SoundEvent> bruno_sticky_fingers = SOUNDS.register("bruno_sticky_fingers", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "bruno_sticky_fingers")));
    public static final RegistryObject<SoundEvent> fugo_purple_haze = SOUNDS.register("fugo_purple_haze", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "fugo_purple_haze")));
    public static final RegistryObject<SoundEvent> sale_kraft_work = SOUNDS.register("sale_kraft_work", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sale_kraft_work")));
    public static final RegistryObject<SoundEvent> polnareff_chariot_requiem = SOUNDS.register("polnareff_chariot_requiem", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "polnareff_chariot_requiem")));
    public static final RegistryObject<SoundEvent> risotto_metallica = SOUNDS.register("risotto_metallica", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "risotto_metallica")));
    public static final RegistryObject<SoundEvent> pesci_beach_boy = SOUNDS.register("pesci_beach_boy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "pesci_beach_boy")));
    public static final RegistryObject<SoundEvent> cioccolata_green_day = SOUNDS.register("cioccolata_green_day", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "cioccolata_green_day")));
    public static final RegistryObject<SoundEvent> jolyne_stone_free = SOUNDS.register("jolyne_stone_free", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jolyne_stone_free")));
    public static final RegistryObject<SoundEvent> pucci_whitesnake = SOUNDS.register("pucci_whitesnake", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "pucci_whitesnake")));
    public static final RegistryObject<SoundEvent> pucci_c_moon = SOUNDS.register("pucci_c_moon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "pucci_c_moon")));
    public static final RegistryObject<SoundEvent> pucci_made_in_heaven = SOUNDS.register("pucci_made_in_heaven", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "pucci_made_in_heaven")));
    public static final RegistryObject<SoundEvent> ermes_kiss = SOUNDS.register("ermes_kiss", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "ermes_kiss")));
    public static final RegistryObject<SoundEvent> wes_weather_report = SOUNDS.register("wes_weather_report", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "wes_weather_report")));
    public static final RegistryObject<SoundEvent> anasui_diver_down = SOUNDS.register("anasui_diver_down", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "anasui_diver_down")));
    public static final RegistryObject<SoundEvent> maxx_limp_bizkit = SOUNDS.register("maxx_limp_bizkit", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "maxx_limp_bizkit")));
    public static final RegistryObject<SoundEvent> lang_jumpin_jack_flash = SOUNDS.register("lang_jumpin_jack_flash", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "lang_jumpin_jack_flash")));
    public static final RegistryObject<SoundEvent> johnny_tusk_act_4 = SOUNDS.register("johnny_tusk_act_4", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "johnny_tusk_act_4")));
    public static final RegistryObject<SoundEvent> valentine_d4c = SOUNDS.register("valentine_d4c", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "valentine_d4c")));
    public static final RegistryObject<SoundEvent> josuke_soft_and_wet = SOUNDS.register("josuke_soft_and_wet", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "josuke_soft_and_wet")));
    public static final RegistryObject<SoundEvent> toru_wonder_of_u = SOUNDS.register("toru_wonder_of_u", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "toru_wonder_of_u")));

    public static final RegistryObject<SoundEvent> koichi_three_freeze = SOUNDS.register("koichi_three_freeze", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "koichi_three_freeze")));
    public static final RegistryObject<SoundEvent> rohan_safety_lock = SOUNDS.register("rohan_safety_lock", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "rohan_safety_lock")));
    public static final RegistryObject<SoundEvent> bruno_arrivederci = SOUNDS.register("bruno_arrivederci", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "bruno_arrivederci")));
    
    public static final RegistryObject<SoundEvent> gold_experience_muda_muda_muda = SOUNDS.register("gold_experience_muda_muda_muda", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_muda_muda_muda")));
    public static final RegistryObject<SoundEvent> gold_experience_muda = SOUNDS.register("gold_experience_muda", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_muda")));
    public static final RegistryObject<SoundEvent> sticky_fingers_ari_ari_ari = SOUNDS.register("sticky_fingers_ari_ari_ari", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_ari_ari_ari")));
    public static final RegistryObject<SoundEvent> stone_free_ora_ora_ora = SOUNDS.register("stone_free_ora_ora_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_ora_ora_ora")));
    public static final RegistryObject<SoundEvent> stone_free_ora = SOUNDS.register("stone_free_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_ora")));
    public static final RegistryObject<SoundEvent> tusk_act_4_ora_ora_ora = SOUNDS.register("tusk_act_4_ora_ora_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_ora_ora_ora")));
    public static final RegistryObject<SoundEvent> tusk_act_4_ora = SOUNDS.register("tusk_act_4_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_ora")));
    public static final RegistryObject<SoundEvent> soft_and_wet_ora_ora_ora = SOUNDS.register("soft_and_wet_ora_ora_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_ora_ora_ora")));
    public static final RegistryObject<SoundEvent> soft_and_wet_ora = SOUNDS.register("soft_and_wet_ora", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_ora")));
    
    public static final RegistryObject<SoundEvent> dark_blue_moon_punch_barrage = SOUNDS.register("dark_blue_moon_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dark_blue_moon_punch_barrage")));
    public static final RegistryObject<SoundEvent> killer_queen_punch_barrage = SOUNDS.register("killer_queen_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_punch_barrage")));
    public static final RegistryObject<SoundEvent> the_hand_punch_barrage = SOUNDS.register("the_hand_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_hand_punch_barrage")));
    public static final RegistryObject<SoundEvent> echoes_act_3_punch_barrage = SOUNDS.register("echoes_act_3_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "echoes_act_3_punch_barrage")));
    public static final RegistryObject<SoundEvent> heavens_door_punch_barrage = SOUNDS.register("heavens_door_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_punch_barrage")));
    public static final RegistryObject<SoundEvent> boy_ii_man_punch_barrage = SOUNDS.register("boy_ii_man_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "boy_ii_man_punch_barrage")));
    public static final RegistryObject<SoundEvent> gold_experience_punch_barrage = SOUNDS.register("gold_experience_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_punch_barrage")));
    public static final RegistryObject<SoundEvent> gold_experience_requiem_punch_barrage = SOUNDS.register("gold_experience_requiem_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_requiem_punch_barrage")));
    public static final RegistryObject<SoundEvent> king_crimson_punch_barrage = SOUNDS.register("king_crimson_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_punch_barrage")));
    public static final RegistryObject<SoundEvent> sticky_fingers_punch_barrage = SOUNDS.register("sticky_fingers_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_punch_barrage")));
    public static final RegistryObject<SoundEvent> purple_haze_punch_barrage = SOUNDS.register("purple_haze_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "purple_haze_punch_barrage")));
    public static final RegistryObject<SoundEvent> kraft_work_punch_barrage = SOUNDS.register("kraft_work_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kraft_work_punch_barrage")));
    public static final RegistryObject<SoundEvent> green_day_punch_barrage = SOUNDS.register("green_day_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "green_day_punch_barrage")));
    public static final RegistryObject<SoundEvent> stone_free_punch_barrage = SOUNDS.register("stone_free_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_punch_barrage")));
    public static final RegistryObject<SoundEvent> whitesnake_punch_barrage = SOUNDS.register("whitesnake_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "whitesnake_punch_barrage")));
    public static final RegistryObject<SoundEvent> kiss_punch_barrage = SOUNDS.register("kiss_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kiss_punch_barrage")));
    public static final RegistryObject<SoundEvent> weather_report_punch_barrage = SOUNDS.register("weather_report_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "weather_report_punch_barrage")));
    public static final RegistryObject<SoundEvent> diver_down_punch_barrage = SOUNDS.register("diver_down_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "diver_down_punch_barrage")));
    public static final RegistryObject<SoundEvent> tusk_act_4_punch_barrage = SOUNDS.register("tusk_act_4_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_punch_barrage")));
    public static final RegistryObject<SoundEvent> d4c_punch_barrage = SOUNDS.register("d4c_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "d4c_punch_barrage")));
    public static final RegistryObject<SoundEvent> soft_and_wet_punch_barrage = SOUNDS.register("soft_and_wet_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_punch_barrage")));
    public static final RegistryObject<SoundEvent> wonder_of_u_punch_barrage = SOUNDS.register("wonder_of_u_punch_barrage", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "wonder_of_u_punch_barrage")));
 
    public static final RegistryObject<SoundEvent> dark_blue_moon_punch_heavy = SOUNDS.register("dark_blue_moon_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dark_blue_moon_punch_heavy")));
    public static final RegistryObject<SoundEvent> killer_queen_punch_heavy = SOUNDS.register("killer_queen_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_punch_heavy")));
    public static final RegistryObject<SoundEvent> the_hand_punch_heavy = SOUNDS.register("the_hand_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_hand_punch_heavy")));
    public static final RegistryObject<SoundEvent> echoes_act_3_punch_heavy = SOUNDS.register("echoes_act_3_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "echoes_act_3_punch_heavy")));
    public static final RegistryObject<SoundEvent> heavens_door_punch_heavy = SOUNDS.register("heavens_door_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_punch_heavy")));
    public static final RegistryObject<SoundEvent> boy_ii_man_punch_heavy = SOUNDS.register("boy_ii_man_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "boy_ii_man_punch_heavy")));
    public static final RegistryObject<SoundEvent> gold_experience_punch_heavy = SOUNDS.register("gold_experience_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_punch_heavy")));
    public static final RegistryObject<SoundEvent> gold_experience_requiem_punch_heavy = SOUNDS.register("gold_experience_requiem_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_requiem_punch_heavy")));
    public static final RegistryObject<SoundEvent> king_crimson_punch_heavy = SOUNDS.register("king_crimson_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_punch_heavy")));
    public static final RegistryObject<SoundEvent> sticky_fingers_punch_heavy = SOUNDS.register("sticky_fingers_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_punch_heavy")));
    public static final RegistryObject<SoundEvent> purple_haze_punch_heavy = SOUNDS.register("purple_haze_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "purple_haze_punch_heavy")));
    public static final RegistryObject<SoundEvent> kraft_work_punch_heavy = SOUNDS.register("kraft_work_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kraft_work_punch_heavy")));
    public static final RegistryObject<SoundEvent> green_day_punch_heavy = SOUNDS.register("green_day_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "green_day_punch_heavy")));
    public static final RegistryObject<SoundEvent> stone_free_punch_heavy = SOUNDS.register("stone_free_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_punch_heavy")));
    public static final RegistryObject<SoundEvent> whitesnake_punch_heavy = SOUNDS.register("whitesnake_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "whitesnake_punch_heavy")));
    public static final RegistryObject<SoundEvent> c_moon_punch_light = SOUNDS.register("c_moon_punch_light", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "c_moon_punch_light")));
    public static final RegistryObject<SoundEvent> kiss_punch_heavy = SOUNDS.register("kiss_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kiss_punch_heavy")));
    public static final RegistryObject<SoundEvent> weather_report_punch_heavy = SOUNDS.register("weather_report_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "weather_report_punch_heavy")));
    public static final RegistryObject<SoundEvent> diver_down_punch_heavy = SOUNDS.register("diver_down_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "diver_down_punch_heavy")));
    public static final RegistryObject<SoundEvent> tusk_act_4_punch_heavy = SOUNDS.register("tusk_act_4_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_punch_heavy")));
    public static final RegistryObject<SoundEvent> d4c_punch_heavy = SOUNDS.register("d4c_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "d4c_punch_heavy")));
    public static final RegistryObject<SoundEvent> soft_and_wet_punch_heavy = SOUNDS.register("soft_and_wet_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_punch_heavy")));
    public static final RegistryObject<SoundEvent> wonder_of_u_punch_heavy = SOUNDS.register("wonder_of_u_punch_heavy", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "wonder_of_u_punch_heavy")));
    
    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_SUMMON = SOUNDS.register("dark_blue_moon_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dark_blue_moon_summon")));
    public static final RegistryObject<SoundEvent> KILLER_QUEEN_SUMMON = SOUNDS.register("killer_queen_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_summon")));
    public static final RegistryObject<SoundEvent> THE_HAND_SUMMON = SOUNDS.register("the_hand_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_hand_summon")));
    public static final RegistryObject<SoundEvent> ECHOES_ACT_3_SUMMON = SOUNDS.register("echoes_act_3_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "echoes_act_3_summon")));
    public static final RegistryObject<SoundEvent> HEAVENS_DOOR_SUMMON = SOUNDS.register("heavens_door_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_summon")));
    public static final RegistryObject<SoundEvent> BOY_II_MAN_SUMMON = SOUNDS.register("boy_ii_man_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "boy_ii_man_summon")));
    public static final RegistryObject<SoundEvent> GOLD_EXPERIENCE_SUMMON = SOUNDS.register("gold_experience_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_summon")));
    public static final RegistryObject<SoundEvent> GOLD_EXPERIENCE_REQUIEM_SUMMON = SOUNDS.register("gold_experience_requiem_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_requiem_summon")));
    public static final RegistryObject<SoundEvent> KING_CRIMSON_SUMMON = SOUNDS.register("king_crimson_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_summon")));
    public static final RegistryObject<SoundEvent> STICKY_FINGERS_SUMMON = SOUNDS.register("sticky_fingers_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_summon")));
    public static final RegistryObject<SoundEvent> PURPLE_HAZE_SUMMON = SOUNDS.register("purple_haze_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "purple_haze_summon")));
    public static final RegistryObject<SoundEvent> KRAFT_WORK_SUMMON = SOUNDS.register("kraft_work_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kraft_work_summon")));
    public static final RegistryObject<SoundEvent> CHARIOT_REQUIEM_SUMMON = SOUNDS.register("chariot_requiem_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "chariot_requiem_summon")));
    public static final RegistryObject<SoundEvent> BEACH_BOY_SUMMON = SOUNDS.register("beach_boy_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "beach_boy_summon")));
    public static final RegistryObject<SoundEvent> GREEN_DAY_SUMMON = SOUNDS.register("green_day_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "green_day_summon")));
    public static final RegistryObject<SoundEvent> STONE_FREE_SUMMON = SOUNDS.register("stone_free_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_summon")));
    public static final RegistryObject<SoundEvent> WHITESNAKE_SUMMON = SOUNDS.register("whitesnake_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "whitesnake_summon")));
    public static final RegistryObject<SoundEvent> C_MOON_SUMMON = SOUNDS.register("c_moon_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "c_moon_summon")));
    public static final RegistryObject<SoundEvent> MADE_IN_HEAVEN_SUMMON = SOUNDS.register("made_in_heaven_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "made_in_heaven_summon")));
    public static final RegistryObject<SoundEvent> KISS_SUMMON = SOUNDS.register("kiss_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kiss_summon")));
    public static final RegistryObject<SoundEvent> WEATHER_REPORT_SUMMON = SOUNDS.register("weather_report_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "weather_report_summon")));
    public static final RegistryObject<SoundEvent> DIVER_DOWN_SUMMON = SOUNDS.register("diver_down_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "diver_down_summon")));
    public static final RegistryObject<SoundEvent> JUMPIN_JACK_FLASH_SUMMON = SOUNDS.register("jumpin_jack_flash_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jumpin_jack_flash_summon")));
    public static final RegistryObject<SoundEvent> TUSK_ACT_4_SUMMON = SOUNDS.register("tusk_act_4_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_summon")));
    public static final RegistryObject<SoundEvent> D4C_SUMMON = SOUNDS.register("d4c_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "d4c_summon")));
    public static final RegistryObject<SoundEvent> SOFT_AND_WET_SUMMON = SOUNDS.register("soft_and_wet_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_summon")));
    public static final RegistryObject<SoundEvent> WONDER_OF_U_SUMMON = SOUNDS.register("wonder_of_u_summon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "wonder_of_u_summon")));
    
    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_UNSUMMON = SOUNDS.register("dark_blue_moon_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "dark_blue_moon_unsummon")));
    public static final RegistryObject<SoundEvent> KILLER_QUEEN_UNSUMMON = SOUNDS.register("killer_queen_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_unsummon")));
    public static final RegistryObject<SoundEvent> THE_HAND_UNSUMMON = SOUNDS.register("the_hand_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_hand_unsummon")));
    public static final RegistryObject<SoundEvent> ECHOES_ACT_3_UNSUMMON = SOUNDS.register("echoes_act_3_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "echoes_act_3_unsummon")));
    public static final RegistryObject<SoundEvent> HEAVENS_DOOR_UNSUMMON = SOUNDS.register("heavens_door_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_unsummon")));
    public static final RegistryObject<SoundEvent> BOY_II_MAN_UNSUMMON = SOUNDS.register("boy_ii_man_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "boy_ii_man_unsummon")));
    public static final RegistryObject<SoundEvent> GOLD_EXPERIENCE_UNSUMMON = SOUNDS.register("gold_experience_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_unsummon")));
    public static final RegistryObject<SoundEvent> GOLD_EXPERIENCE_REQUIEM_UNSUMMON = SOUNDS.register("gold_experience_requiem_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_requiem_unsummon")));
    public static final RegistryObject<SoundEvent> KING_CRIMSON_UNSUMMON = SOUNDS.register("king_crimson_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_unsummon")));
    public static final RegistryObject<SoundEvent> STICKY_FINGERS_UNSUMMON = SOUNDS.register("sticky_fingers_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_unsummon")));
    public static final RegistryObject<SoundEvent> PURPLE_HAZE_UNSUMMON = SOUNDS.register("purple_haze_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "purple_haze_unsummon")));
    public static final RegistryObject<SoundEvent> KRAFT_WORK_UNSUMMON = SOUNDS.register("kraft_work_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kraft_work_unsummon")));
    public static final RegistryObject<SoundEvent> CHARIOT_REQUIEM_UNSUMMON = SOUNDS.register("chariot_requiem_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "chariot_requiem_unsummon")));
    public static final RegistryObject<SoundEvent> BEACH_BOY_UNSUMMON = SOUNDS.register("beach_boy_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "beach_boy_unsummon")));
    public static final RegistryObject<SoundEvent> GREEN_DAY_UNSUMMON = SOUNDS.register("green_day_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "green_day_unsummon")));
    public static final RegistryObject<SoundEvent> STONE_FREE_UNSUMMON = SOUNDS.register("stone_free_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "stone_free_unsummon")));
    public static final RegistryObject<SoundEvent> WHITESNAKE_UNSUMMON = SOUNDS.register("whitesnake_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "whitesnake_unsummon")));
    public static final RegistryObject<SoundEvent> C_MOON_UNSUMMON = SOUNDS.register("c_moon_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "c_moon_unsummon")));
    public static final RegistryObject<SoundEvent> MADE_IN_HEAVEN_UNSUMMON = SOUNDS.register("made_in_heaven_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "made_in_heaven_unsummon")));
    public static final RegistryObject<SoundEvent> KISS_UNSUMMON = SOUNDS.register("kiss_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "kiss_unsummon")));
    public static final RegistryObject<SoundEvent> WEATHER_REPORT_UNSUMMON = SOUNDS.register("weather_report_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "weather_report_unsummon")));
    public static final RegistryObject<SoundEvent> DIVER_DOWN_UNSUMMON = SOUNDS.register("diver_down_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "diver_down_unsummon")));
    public static final RegistryObject<SoundEvent> JUMPIN_JACK_FLASH_UNSUMMON = SOUNDS.register("jumpin_jack_flash_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "jumpin_jack_flash_unsummon")));
    public static final RegistryObject<SoundEvent> TUSK_ACT_4_UNSUMMON = SOUNDS.register("tusk_act_4_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_unsummon")));
    public static final RegistryObject<SoundEvent> D4C_UNSUMMON = SOUNDS.register("d4c_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "d4c_unsummon")));
    public static final RegistryObject<SoundEvent> SOFT_AND_WET_UNSUMMON = SOUNDS.register("soft_and_wet_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_unsummon")));
    public static final RegistryObject<SoundEvent> WONDER_OF_U_UNSUMMON = SOUNDS.register("wonder_of_u_unsummon", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "wonder_of_u_unsummon")));
    
    public static final RegistryObject<SoundEvent> killer_queen_detonate = SOUNDS.register("killer_queen_detonate", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_detonate")));
    public static final RegistryObject<SoundEvent> the_hand_erase = SOUNDS.register("the_hand_erase", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "the_hand_erase")));
    public static final RegistryObject<SoundEvent> echoes_three_freeze = SOUNDS.register("echoes_three_freeze", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "echoes_three_freeze")));
    public static final RegistryObject<SoundEvent> heavens_door_safety_lock = SOUNDS.register("heavens_door_safety_lock", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_safety_lock")));
    public static final RegistryObject<SoundEvent> gold_experience_life_creation = SOUNDS.register("gold_experience_life_creation", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_life_creation")));
    public static final RegistryObject<SoundEvent> king_crimson_time_erase = SOUNDS.register("king_crimson_time_erase", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_time_erase")));
    public static final RegistryObject<SoundEvent> sticky_fingers_zipper = SOUNDS.register("sticky_fingers_zipper", () -> new SoundEvent(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_zipper")));

    
    
    
    
    
    public static final OstSoundList DARK_BLUE_MOON_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "dark_blue_moon_ost"), SOUNDS);
    public static final OstSoundList KILLER_QUEEN_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "killer_queen_ost"), SOUNDS);
    public static final OstSoundList THE_HAND_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "the_hand_ost"), SOUNDS);
    public static final OstSoundList ECHOES_ACT_3_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "echoes_act_3_ost"), SOUNDS);
    public static final OstSoundList HEAVENS_DOOR_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "heavens_door_ost"), SOUNDS);
    public static final OstSoundList BOY_II_MAN_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "boy_ii_man_ost"), SOUNDS);
    public static final OstSoundList GOLD_EXPERIENCE_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_ost"), SOUNDS);
    public static final OstSoundList GOLD_EXPERIENCE_REQUIEM_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "gold_experience_requiem_ost"), SOUNDS);
    public static final OstSoundList KING_CRIMSON_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "king_crimson_ost"), SOUNDS);
    public static final OstSoundList STICKY_FINGERS_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "sticky_fingers_ost"), SOUNDS);
    public static final OstSoundList PURPLE_HAZE_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "purple_haze_ost"), SOUNDS);
    public static final OstSoundList KRAFT_WORK_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "kraft_work_ost"), SOUNDS);
    public static final OstSoundList CHARIOT_REQUIEM_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "chariot_requiem_ost"), SOUNDS);
    public static final OstSoundList METALLICA_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "metallica_ost"), SOUNDS);
    public static final OstSoundList BEACH_BOY_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "beach_boy_ost"), SOUNDS);
    public static final OstSoundList GREEN_DAY_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "green_day_ost"), SOUNDS);
    public static final OstSoundList STONE_FREE_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "stone_free_ost"), SOUNDS);
    public static final OstSoundList WHITESNAKE_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "whitesnake_ost"), SOUNDS);
    public static final OstSoundList C_MOON_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "c_moon_ost"), SOUNDS);
    public static final OstSoundList MADE_IN_HEAVEN_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "made_in_heaven_ost"), SOUNDS);
    public static final OstSoundList KISS_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "kiss_ost"), SOUNDS);
    public static final OstSoundList WEATHER_REPORT_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "weather_report_ost"), SOUNDS);
    public static final OstSoundList DIVER_DOWN_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "diver_down_ost"), SOUNDS);
    public static final OstSoundList LIMP_BIZKIT_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "limp_bizkit_ost"), SOUNDS);
    public static final OstSoundList JUMPIN_JACK_FLASH_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "jumpin_jack_flash_ost"), SOUNDS);
    public static final OstSoundList TUSK_ACT_4_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "tusk_act_4_ost"), SOUNDS);
    public static final OstSoundList D4C_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "d4c_ost"), SOUNDS);
    public static final OstSoundList SOFT_AND_WET_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "soft_and_wet_ost"), SOUNDS);
    public static final OstSoundList WONDER_OF_U_OST = new OstSoundList(new ResourceLocation(JojoMod.MOD_ID, "wonder_of_u_ost"), SOUNDS);
    

}
