package com.github.standobyte.jojo.capability.item.cassette;

import com.github.standobyte.jojo.init.power.stand.ModStandActions;
import com.github.standobyte.jojo.power.stand.type.StandType;
import com.github.standobyte.jojo.util.OstSoundList;
import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TrackSourceStandDisc extends TrackSource {
    private final StandType<?> standType;
    
    public TrackSourceStandDisc(StandType<?> standType) {
        super(TrackSourceType.STAND_DISC);
        this.standType = standType;
    }
    
    static TrackSource fromNBT(CompoundNBT nbt) {
        if (nbt.contains("Stand", JojoModUtil.getNbtId(StringNBT.class))) {
            StandType<?> standType = ModStandActions.STANDS.getRegistry().getValue(new ResourceLocation(nbt.getString("Stand")));
            if (standType != null) {
                return new TrackSourceStandDisc(standType);
            }
        }
        
        return BROKEN_CASSETTE;
    }
    
    @Override
    protected CompoundNBT toNBT() {
        CompoundNBT nbt = super.toNBT();
        nbt.putString("Stand", standType.getRegistryName().toString());
        return nbt;
    }

    @Override
    public SoundEvent getSoundEvent() {
        OstSoundList ost = standType.getOst();
        return ost != null ? ost.getForCassette() : null;
    }

    @Override
    protected String getTranslationKey(ResourceLocation trackId) {
        return trackId.getNamespace() + "." + trackId.getPath().replace("/", ".") + ".name";
    }

}
