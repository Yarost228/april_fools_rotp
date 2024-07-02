package com.github.standobyte.jojo.network.packets.fromserver;

import java.util.function.Supplier;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TrVampirismDataPacket {
    private final int entityId;
    private final VampireField field;
    private final boolean valueBool;
    private final int valueInt;
    
    public static TrVampirismDataPacket wasHamonUser(int entityId, boolean value) {
        return new TrVampirismDataPacket(entityId, VampireField.WAS_HAMON_USER, value, 0);
    }
    
    public static TrVampirismDataPacket atFullPower(int entityId, boolean value) {
        return new TrVampirismDataPacket(entityId, VampireField.AT_FULL_POWER, value, 0);
    }
    
    public static TrVampirismDataPacket curingTicks(int entityId, int ticks) {
        return new TrVampirismDataPacket(entityId, VampireField.CURING_TICKS, false, ticks);
    }
    
    private TrVampirismDataPacket(int entityId, VampireField flag, boolean valueBool, int valueInt) {
        this.entityId = entityId;
        this.field = flag;
        this.valueBool = valueBool;
        this.valueInt = valueInt;
    }
    
    public static void encode(TrVampirismDataPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeEnum(msg.field);
        switch (msg.field) {
        case WAS_HAMON_USER: case AT_FULL_POWER:
            buf.writeBoolean(msg.valueBool);
            break;
        case CURING_TICKS:
            buf.writeInt(msg.valueInt);
            break;
        }
    }
    
    public static TrVampirismDataPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        VampireField field = buf.readEnum(VampireField.class);
        switch (field) {
        case WAS_HAMON_USER: case AT_FULL_POWER:
            return new TrVampirismDataPacket(entityId, field, buf.readBoolean(), 0);
        case CURING_TICKS:
            return new TrVampirismDataPacket(entityId, field, false, buf.readInt());
        }
        throw new IllegalStateException("Unknown JoJo vampirism field being sent!");
    }

    public static void handle(TrVampirismDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof LivingEntity) {
                INonStandPower.getNonStandPowerOptional((LivingEntity) entity).ifPresent(power -> {
                    switch(msg.field) {
                    case WAS_HAMON_USER:
                        power.getTypeSpecificData(ModVampirism.VAMPIRISM.get()).ifPresent(vampirism -> {
                            vampirism.setVampireHamonUser(msg.valueBool);
                        });
                        break;
                    case AT_FULL_POWER:
                        power.getTypeSpecificData(ModVampirism.VAMPIRISM.get()).ifPresent(vampirism -> {
                            vampirism.setVampireFullPower(msg.valueBool);
                        });
                        break;
                    case CURING_TICKS:
                        power.getTypeSpecificData(ModVampirism.VAMPIRISM.get()).ifPresent(vampirism -> {
                            vampirism.setCuringTicks(msg.valueInt);
                        });
                        break;
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
    
    private static enum VampireField {
        WAS_HAMON_USER,
        AT_FULL_POWER,
        CURING_TICKS
    }
}
