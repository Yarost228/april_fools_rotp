package com.github.standobyte.jojo.init.power.stand;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.stands.CrazyDiamondEntity;
import com.github.standobyte.jojo.entity.stand.stands.HierophantGreenEntity;
import com.github.standobyte.jojo.entity.stand.stands.MagiciansRedEntity;
import com.github.standobyte.jojo.entity.stand.stands.SilverChariotEntity;
import com.github.standobyte.jojo.entity.stand.stands.StarPlatinumEntity;
import com.github.standobyte.jojo.entity.stand.stands.TheWorldEntity;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.stand.stats.StandStats;
import com.github.standobyte.jojo.power.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.stand.type.EntityStandType;

/* 
 * that's just for the sake of distinguishing between "stand types" and "stand objects" when writing code
 * while keeping the initialization of stand types and related stand actions in the same file
 * ("ModStands.STAR_PLATINUM" looks nicer, but stuff like 
 * "STAR_PLATINUM_HEAVY_PUNCH" intervening with the auto-complete makes it less convenient)
 * 
 * totally optional, also kinda cringe but k
 */
public class ModStands {

    public static final EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<StarPlatinumEntity>> 
    STAR_PLATINUM = new EntityStandSupplier<>(ModStandActions.STAND_STAR_PLATINUM);

    public static final EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<TheWorldEntity>> 
    THE_WORLD = new EntityStandSupplier<>(ModStandActions.STAND_THE_WORLD);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<HierophantGreenEntity>> 
    HIEROPHANT_GREEN = new EntityStandSupplier<>(ModStandActions.STAND_HIEROPHANT_GREEN);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<SilverChariotEntity>> 
    SILVER_CHARIOT = new EntityStandSupplier<>(ModStandActions.STAND_SILVER_CHARIOT);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<MagiciansRedEntity>> 
    MAGICIANS_RED = new EntityStandSupplier<>(ModStandActions.STAND_MAGICIANS_RED);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<CrazyDiamondEntity>> 
    CRAZY_DIAMOND = new EntityStandSupplier<>(ModStandActions.STAND_CRAZY_DIAMOND);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<StandEntity>> 
    BOY_II_MAN = new EntityStandSupplier<>(ModStandActions.STAND_BOY_II_MAN);
}
